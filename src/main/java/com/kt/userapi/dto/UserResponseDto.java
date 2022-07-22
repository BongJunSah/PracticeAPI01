package com.kt.userapi.dto;

import com.kt.userapi.domain.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String password;
    private String email;
    private int age;
    private LocalDateTime createdDate; // 생성일
    private LocalDateTime modifiedDate; // 수정일

    public UserResponseDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.password = userEntity.getEmail();
        this.email = userEntity.getEmail();
        this.age = userEntity.getAge();
        this.createdDate = userEntity.getCreatedDate();
        this.modifiedDate = userEntity.getModifiedDate();
    }

}

