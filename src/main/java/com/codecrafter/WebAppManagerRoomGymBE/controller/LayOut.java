package com.codecrafter.WebAppManagerRoomGymBE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayOut {
 @GetMapping("/send-email")
    public String doGetEmail() {
        return "user/index";
    }
}
