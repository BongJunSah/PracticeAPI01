package com.kt.userapi.controller;

import com.kt.userapi.service.BlogService;
import com.kt.userapi.service.UserBlogService;
import com.kt.userapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/connect")
@AllArgsConstructor
@RestController
public class UserBlogController {

    UserBlogService userBlogService;

    @PutMapping("/blog/{blogId}/user/{userId}")
    public String connectBlogToUser (@PathVariable Long blogId, @PathVariable Long userId) {
        userBlogService.connectBlogToUser(blogId, userId);
        return "Connecting Blog(" + blogId + ") to User(" + userId + ") is success.";
    }

    @PutMapping("/blog/user")
    public String connectBlogToUserRequest (@RequestParam("blogId") Long blogId, @RequestParam("userId") Long userId) {
        userBlogService.connectBlogToUser(blogId, userId);
        return "Connecting Blog(" + blogId + ") to User(" + userId + ") is success.";
    }
}
