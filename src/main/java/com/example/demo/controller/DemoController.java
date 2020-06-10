package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class DemoController {
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
    public List<?> memberList(){
        ConcurrentHashMap<String, WebSocketServer> map = WebSocketServer.memberList();
        ArrayList<String> list = new ArrayList<>();
        for (Iterator<String> iterator = map.keySet().iterator();iterator.hasNext();) {
            String key = (String) iterator.next();
            list.add(key);

        }

        return list;
    }
}
