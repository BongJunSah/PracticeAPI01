package com.kt.userapi.dto;

import com.kt.userapi.domain.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private int userAge;
    private LocalDateTime createdDate; // 생성일
    private LocalDateTime modifiedDate; // 수정일

    public UserResponseDto(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.userName = userEntity.getUserName();
        this.userPassword = userEntity.getUserPassword();
        this.userEmail = userEntity.getUserEmail();
        this.userAge = userEntity.getUserAge();
        this.createdDate = userEntity.getCreatedDate();
        this.modifiedDate = userEntity.getModifiedDate();
    }

}

