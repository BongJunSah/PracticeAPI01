package com.kt.userapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserMVCController {
    @GetMapping("/menu")
    public String hello(Model model) {
        model.addAttribute("name", "bjsah");
        return "menu-template";
    }
}
