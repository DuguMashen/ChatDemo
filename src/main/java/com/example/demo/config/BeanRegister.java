package com.example.demo.config;

import com.example.demo.model.MessageRepository;
import com.example.demo.model.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 */
@Configuration
public class BeanRegister {

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public MessageRepository messageRepository() {
        return new MessageRepository();
    }

    ;
}
