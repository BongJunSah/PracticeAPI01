package com.kt.userapi.controller;

import com.kt.userapi.dto.UserRequestDto;
import com.kt.userapi.dto.UserResponseDto;
import com.kt.userapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserMVCController {

    private UserService userService;

    @GetMapping("/menu")
    public String hello() {
        return "menu-template";
    }

    @GetMapping("/new")
    public String createForm() {
        return "users/createUserForm";
    }

    @PostMapping("/create")
    public String create(@Validated UserForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("User request body validated error in createUser.");
            return "redirect:/";
        }
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name(form.getUserId())
                .password(form.getPassword())
                .email(form.getEmail())
                .age(form.getAge())
                .build();
        Long id = userService.createAccount(userRequestDto);
        if(id == (long)-1) {
            log.info("Creating fail. Same name or email already exists.");
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/retrieve")
    public String list(Model model) {
        List<UserResponseDto> userResponseDtoList = userService.findAllAccount();
        model.addAttribute("users", userResponseDtoList);
        return "users/userList";
    }

}
