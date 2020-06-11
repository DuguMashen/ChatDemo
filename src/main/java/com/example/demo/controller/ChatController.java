package com.example.demo.controller;

import com.example.demo.model.MessageRepository;
import com.example.demo.model.WsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jinhq
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageRepository messageRepository;

    /**
     * 拉取聊天记录
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getChatRecord")
    public List<WsMessage> getChatRecord(String userId) {
        return messageRepository.getMsgs(userId);
    }


}
