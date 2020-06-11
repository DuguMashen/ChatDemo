package com.example.demo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟消息库，保存离线消息
 *
 * @author jinhq
 */
public class MessageRepository {
    private Map<String, List<WsMessage>> userMessage;

    public MessageRepository() {
        this.userMessage = new HashMap<>();
    }

    /**
     * 取出用户消息
     * @param userId
     * @return
     */
    public List<WsMessage> getMsgs(String userId) {
        return userMessage.get(userId);
    }
    /**
     * 保存用户消息
     * @param userId
     * @return
     */
    public void putMessage(String userId, WsMessage wsMessage) {
        List<WsMessage> messages = userMessage.get(userId);
        if (messages == null) {
            messages = new ArrayList<>();
            messages.add(wsMessage);
            return;
        }
        messages.add(wsMessage);
    }


}
