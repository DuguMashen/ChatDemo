package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wrr")
public class WrrController {
    @RequestMapping("/dufu")
    public String dufu(){
        String dufu="杜甫，字子美，京兆杜陵人，自称“杜陵布衣”， 又自称\n" +
                "“少陵野老”。其人有“诗圣”之称，其诗有“诗史”之称。\n" +
                "与李白合称为“李杜";
        return dufu;
    }
}
