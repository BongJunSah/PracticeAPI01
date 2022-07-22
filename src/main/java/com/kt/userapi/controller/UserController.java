package com.kt.userapi.controller;

import com.kt.userapi.dto.UserRequestDto;
import com.kt.userapi.dto.UserResponseDto;
import com.kt.userapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// DI(Dependency Injection)의 역할을 수행한다.
// 모든 파라미터를 입력받는 생성자를 만든다.
@AllArgsConstructor
@Slf4j
// Spring 지원 Annotation
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    //bean : spring container에 의해 생성된다.
    //Autowired : Spring Container 에있는 userService를 연결을 시켜준다.
    //Component Scan 방식 : @Component는 spring이 올라갈 때, 객체를 하나씩 자동 생성한다.
    //Autowired에 의해 처음 생성되었던 객체가 자동으로 연결된다.
    //Singleton : 빈당 오로지 하나의 객체만 등록한다. 같은 spring bean이면 모두 같은 인스턴스이다.
    //Spring Container에 올라가는 것만 @Autowired가 먹는다.
    /*
    public UserController(UserService userService) {
        this.userService = userService;
    }
     */

    //Setter DI
    /*
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
     */
    // public 하게 노출이 되어 spring container가 올라가는 과정 뿐만 아니라 중간에 사용이 가능하다. -> 조립시점에 사용 되도록 하게 해야한다.
    // 결론 : Constructor DI를 사용해라.

    // RequestBody
    // Body에 담아서 보낸다.
    // RestController를 사용중이기 때문에 자동으로 JSON 변환을 하여 반환한다.
    // User 객체를 그대로 반환이 가능하다.
    // ResponseBody는 RequestBody의 형식을 그대로 가져간다. -> exclude 설정을 RequestBody에 대해 가능하다.
    @PostMapping("/create")
    public String createUser(@Validated @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult) {
        //log.info("Start creating user by UserRequestDto : {}", userRequestDto);
        if(bindingResult.hasErrors()) {
            log.error("User request body validated error in createUser.");
            return "Create Fail : Not Null Exception.";
        }
        Long id = userService.createAccount(userRequestDto);
        //현재 @ToString() 에서 exclude로 password를 제외시켜 놓아 로그에 적히지 않는다.
        if(id == (long)-1) {
            log.info("Creating fail. Same name or email already exists.");
            return "Creating fail. Same name or email already exists.";
        }
        //log.info("Creating success in id : {}", id);
        return "Create Success with id : " + id.toString();
    }

    // Path Variable
    // GET /api/user/10153191
    // 특정 선정에 사용된다.
    @GetMapping("/retrieve/name/{name}")
    public UserResponseDto retrieveUserByName(@PathVariable String name) {
        //log.info("Start retrieving user by name, {}.", name);
        UserResponseDto userResponseDto = userService.findAccountByName(name);
        //log.info("Result of retrieving user by name, {}.", userResponseDto);
        return userResponseDto;
    }
    // RequestParam Test
    // GET /api/user?email=sylee@gamil.com 과 연동된다.
    // 필터링에 사용된다.
    @GetMapping("/retrieve/email")
    public UserResponseDto retrieveUserByEmail(@RequestParam("email") String email) {
        //log.info("Start retrieving user by email, {}.", email);
        UserResponseDto userResponseDto = userService.findAccountByEmail(email);
        //log.info("Result of retrieving user by email, {}.", userResponseDto);
        return userResponseDto;
    }


    @GetMapping("/retrieve/age")
    public List<UserResponseDto> retrieveUserByAge(@RequestParam("age") int age) {
        //log.info("Start retrieving user by age, {}.", age);
        List<UserResponseDto> userResponseDtoList = userService.findAllAccountByAge(age);
        //log.info("Result of retrieving user by age, {}.", userResponseDtoList);
        return userResponseDtoList;
    }

    @GetMapping("/retrieve/all")
    public List<UserResponseDto> retriveAllUser()
    {
        //log.info("Start retrieving all user.");
        List<UserResponseDto> userResponseDtoList = userService.findAllAccount();
        //log.info("Retrieving all user success.");
        return userResponseDtoList;
    }
    /*
    @GetMapping("/test/error")
    public String testError() {
        throw new CustomException(ErrorCode.POSTS_NOT_FOUND);
    }
     */

    @PutMapping("/update")
    public String updateUser(@RequestParam("id") Long id, @Validated @RequestBody final UserRequestDto userRequestDto, BindingResult bindingResult) {
        //log.info("Start updating user by UserRequestDto : {}", userRequestDto);
        if(bindingResult.hasErrors()) {
            log.error("User request body validated error in updateUser.");
            return "Update Fail : Not Null Exception.";
        }
        userService.updateAccount(id, userRequestDto);
        //현재 @ToString() 에서 exclude로 password를 제외시켜 놓아 로그에 적히지 않는다.
        //log.info("Updating success in id : {}", id);
        return "Updating Success with id : " + id.toString();
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        //log.info("Start deleting user by id : {}", id);
        userService.deleteAccount(id);
        //log.info("Deleting success in id : {}", id);
        return "Delete Success with id : " + id.toString();
    }

    @DeleteMapping("/delete/all")
    public String deleteAllUsers() {
        //log.info("Start deleting all users.");
        userService.deleteAllAccount();
        //log.info("Deleting all users success.");
        return "Deleting all users success.";
    }
}
