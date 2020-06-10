package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class DemoController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("index")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("请求成功");
    }

    @GetMapping("page")
    public ModelAndView page() {
        return new ModelAndView("websocket");
    }

    @RequestMapping("/push/{toUserId}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message, toUserId);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    @RequestMapping("/memberList")
    public List<?> memberList() {
        List<User> users = WebSocketServer.memberList();
        return users;
    }

    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String acc = UUID.randomUUID().toString().replace("-", "");
        user.setAcc(acc);
        userRepository.addUser(user);
        return ResponseEntity.ok("SUCCESS");
    }
}
