package com.example.demo.service;

import com.example.demo.model.MessageRepository;
import com.example.demo.model.WsMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * @author jinhq
 */
@Async
public class AsyncService {
    private final Logger logger = LoggerFactory.getLogger(AsyncService.class);
    @Autowired
    private MessageRepository messageRepository;

    /**
     * 保存消息
     *
     * @param message
     */
    public void saveMessage(WsMessage message) {
        String type = message.getType();
        String fromUserId = message.getFromUserId();
        String toUserId = message.getToUserId();
        logger.info("type:{},fromUserId:{},toUserId:{}", type, fromUserId, toUserId);
        //非用户消息不保存
        if (StringUtils.isBlank(type) || WsMessage.TypeEnum.CLIENT.getType().equals(type)) {
            return;
        }
        if (StringUtils.isNotBlank(fromUserId)) {
            messageRepository.putMessage(fromUserId, message);
        }
        if (StringUtils.isNotBlank(toUserId)) {
            messageRepository.putMessage(toUserId, message);
        }
    }

}
