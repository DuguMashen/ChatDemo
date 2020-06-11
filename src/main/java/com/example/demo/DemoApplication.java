package com.example.demo;


import com.example.demo.controller.WebSocketServer;
import com.example.demo.model.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        ApplicationContext applicationContext = springApplication.run(args);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        WebSocketServer.setUserRepository(userRepository);
    }

}
