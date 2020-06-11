package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.MessageRepository;
import com.example.demo.model.User;
import com.example.demo.model.UserRepository;
import com.example.demo.model.WsMessage;
import com.example.demo.service.AsyncService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/imserver/{userId}")
@Component
public class WebSocketServer {

    static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 保存用户会话
     */
    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
    private static UserRepository userRepository;
    private static MessageRepository messageRepository;
    private static AsyncService asyncService;
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        sessions.put(userId, session);
        pustUserList();
        log.info("用户连接:{}", userId);

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            session.close();
            sessions.remove(userId);

        } catch (IOException e) {
            log.error("exception:{}", e.getMessage());
        }
        pustUserList();
        log.info("用户退出:{}", userId);
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        WsMessage msg = JSONObject.parseObject(message, WsMessage.class);
        asyncService.saveMessage(msg);
        sendMessage(msg);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("连接错误:{},message:{}", this.userId, error.getMessage());
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public static void sendMessage(WsMessage message) {
        String type = message.getType();
        if (StringUtils.isBlank(type)) {
            log.error("消息类型为空");
            return;
        }
        if (WsMessage.TypeEnum.USERLIST.getType().equals(type)) {
            notice(message);
            return;
        }
        if (WsMessage.TypeEnum.CLIENT.getType().equals(type)) {
            String toUserId = message.getToUserId();
            if (StringUtils.isBlank(toUserId)) {
                log.error("消息接收者为空");
                return;
            }
            sendMessage(toUserId, JSON.toJSONString(message));
        }

    }

    /**
     * 通知消息，群发用户
     * @param message
     */
    public static void notice(WsMessage message) {
        Set<String> set = sessions.keySet();
        for (String userId : set) {
            sendMessage(userId, JSONObject.toJSONString(message));
        }
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (StringUtils.isNotBlank(userId) && sessions.contains(userId)) {
            sendMessage(userId, message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    /**
     * 向指定用户发送消息
     */
    public static void sendMessage(String userId, String message) {
        try {
            Session session=sessions.get(userId);
            if(session==null){
                // TODO 用户离线，保存离线消息
                return;
            }
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息异常:{}", e.getMessage());
        }
    }

    public static void pustUserList() {
        //推送用户列表
        WsMessage wsMessage = new WsMessage();
        wsMessage.setType(WsMessage.TypeEnum.USERLIST.getType());
        Map map = new HashMap<>();
        map.put("users", userRepository);
        wsMessage.setMap(map);
        notice(wsMessage);
    }

    public static void setUserRepository(UserRepository userRepository) {
        WebSocketServer.userRepository = userRepository;
    }

    public static List<User> memberList() {
        return userRepository.getUsers();
    }

}
