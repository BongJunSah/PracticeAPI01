package com.kt.userapi.dto;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDto {
    private Long blogId;
    private String blogName;
    private String blogIntro;
    private LocalDateTime createdDate; // 생성일
    private LocalDateTime modifiedDate; // 수정일
    private UserResponseDto userResponseDto;

    public BlogResponseDto(BlogEntity blogEntity) {
        this.blogId = blogEntity.getBlogId();
        this.blogName = blogEntity.getBlogName();
        this.blogIntro = blogEntity.getBlogIntro();
        this.createdDate = blogEntity.getCreatedDate();
        this.modifiedDate = blogEntity.getModifiedDate();
        if (blogEntity.getUserEntity() != null) {
            this.userResponseDto = new UserResponseDto(blogEntity.getUserEntity());
        }
    }
}
