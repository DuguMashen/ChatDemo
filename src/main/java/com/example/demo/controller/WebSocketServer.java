package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.User;
import com.example.demo.model.UserRepository;
import com.example.demo.model.WsMessage;
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
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/imserver/{userId}")
@Component
public class WebSocketServer {

    static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
    private static UserRepository userRepository;
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
        //推送用户列表
        WsMessage wsMessage = new WsMessage();
        Map map = new HashMap<>();
        map.put("users", userRepository);
        wsMessage.setMap(map);
        sendMessage(wsMessage);
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
        log.info("用户退出:{}", userId);
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        WsMessage msg = JSONObject.parseObject(message, WsMessage.class);
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

    public static void sendMessage(WsMessage message) {
        String toUserId = message.getToUserId();
        try {
            sendMessage(toUserId, JSON.toJSONString(message));
        } catch (IOException e) {
            log.error("发送消息异常:{}", e.getMessage());
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
    public static void sendMessage(String userId, String message) throws IOException {
        sessions.get(userId).getBasicRemote().sendText(message);
    }

    public static void setUserRepository(UserRepository userRepository) {
        WebSocketServer.userRepository = userRepository;
    }

    public static List<User> memberList() {
        return userRepository.getUsers();
    }

}
