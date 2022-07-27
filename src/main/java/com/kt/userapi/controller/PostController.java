package com.kt.userapi.controller;

import com.kt.userapi.dto.BlogRequestDto;
import com.kt.userapi.dto.BlogResponseDto;
import com.kt.userapi.dto.PostRequestDto;
import com.kt.userapi.dto.PostResponseDto;
import com.kt.userapi.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/posts")
@AllArgsConstructor
@RestController
public class PostController {
    private PostService postService;

    @PostMapping("/create")
    public String createPost(@RequestParam("blogId") long blogId, @Validated @RequestBody PostRequestDto postRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("Post request body validated error in createPost.");
            return "Create Fail : Not Null Exception.";
        }

        Long id = postService.createPost(postRequestDto, blogId);
        if(id == (long)-1) {
            log.info("Creating fail. Same name already exists.");
            return "Creating fail. Same name already exists.";
        }
        return "Create Success with id : " + id;
    }

    @GetMapping("/retrieve/name/{name}")
    public PostResponseDto retrievePostByName(@PathVariable String name) {
        return postService.findPostByName(name);
    }

    @GetMapping("/retrieve/all")
    public List<PostResponseDto> retrieveAllPost() {
        return postService.findAllPost();
    }

    @PutMapping("/update")
    public String updatePost(@RequestParam("postId") Long postId, @Validated @RequestBody final PostRequestDto postRequestDto, BindingResult bindingResult) {
        //log.info("Start updating user by UserRequestDto : {}", userRequestDto);
        if(bindingResult.hasErrors()) {
            log.error("Post request body validated error in updatePost.");
            return "Update Fail : Not Null Exception.";
        }
        Long id = postService.updatePost(postId, postRequestDto);

        if(id == (long)-1) {
            log.info("Updating fail. Same name already exists.");
            return "Updating fail. Same name already exists.";
        }

        return "Updating Success with id : " + postId;
    }

    @DeleteMapping("/delete")
    public String deletePost(@RequestParam("id") Long id) {
        postService.deletePost(id);
        return "Delete Success with id : " + id;
    }

    @DeleteMapping("/delete/all")
    public String deleteAllPosts() {
        postService.deleteAllPost();
        return "Deleting all posts success.";
    }
}
