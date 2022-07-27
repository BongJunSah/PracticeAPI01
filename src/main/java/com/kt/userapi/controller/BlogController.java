package com.kt.userapi.controller;

import com.kt.userapi.dto.BlogRequestDto;
import com.kt.userapi.dto.BlogResponseDto;
import com.kt.userapi.dto.UserRequestDto;
import com.kt.userapi.dto.UserResponseDto;
import com.kt.userapi.service.BlogService;
import com.kt.userapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/blogs")
@AllArgsConstructor
@RestController
public class BlogController {

    private BlogService blogService;

    @PostMapping("/create")
    public String createBlog(@Validated @RequestBody BlogRequestDto blogRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("Blog request body validated error in createBlog.");
            return "Create Fail : Not Null Exception.";
        }

        Long id = blogService.createBlog(blogRequestDto);
        if(id == (long)-1) {
            log.info("Creating fail. Same name or email already exists.");
            return "Creating fail. Same name or email already exists.";
        }
        return "Create Success with id : " + id;
    }

    @GetMapping("/retrieve/name/{name}")
    public BlogResponseDto retrieveBlogByName(@PathVariable String name) {
        return blogService.findBlogByName(name);
    }

    @GetMapping("/retrieve/all")
    public List<BlogResponseDto> retrieveAllBlog() {
        return blogService.findAllBlog();
    }

    @PutMapping("/update")
    public String updateBlog(@RequestParam("blogId") Long blogId, @Validated @RequestBody final BlogRequestDto blogRequestDto, BindingResult bindingResult) {
        //log.info("Start updating user by UserRequestDto : {}", userRequestDto);
        if(bindingResult.hasErrors()) {
            log.error("Blog request body validated error in updateBlog.");
            return "Update Fail : Not Null Exception.";
        }
        Long id = blogService.updateBlog(blogId, blogRequestDto);

        if(id == (long)-1) {
            log.info("Updating fail. Same name already exists.");
            return "Updating fail. Same name already exists.";
        }

        return "Updating Success with id : " + blogId;
    }

    @DeleteMapping("/delete")
    public String deleteBlog(@RequestParam("id") Long id) {
        blogService.deleteBlog(id);
        return "Delete Success with id : " + id;
    }

    @DeleteMapping("/delete/all")
    public String deleteAllBlogs() {
        blogService.deleteAllBlog();
        return "Deleting all blogs success.";
    }
}
