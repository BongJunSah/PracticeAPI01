package com.kt.userapi.dto;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.PostEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long postId;
    private String postName;
    private String postContent;
    private LocalDateTime createdDate; // 생성일
    private LocalDateTime modifiedDate; // 수정일
    private BlogResponseDto blogResponseDto;

    public PostResponseDto(PostEntity postEntity) {
        this.postId = postEntity.getPostId();
        this.postName = postEntity.getPostName();
        this.postContent = postEntity.getPostContent();
        this.createdDate = postEntity.getCreatedDate();
        this.modifiedDate = postEntity.getModifiedDate();
        if (postEntity.getBlogEntity() != null) {
            this.blogResponseDto = new BlogResponseDto(postEntity.getBlogEntity());
        }
    }
}
