package com.kt.userapi.dto;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.PostEntity;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostRequestDto {

    @NotNull(message = "Post name can not be null.")
    private String postName;

    @NotNull(message = "Post content can not be null.")
    private String postContent;

    @Builder
    public PostRequestDto(String postName, String postContent) {
        this.postName = postName;
        this.postContent = postContent;
    }

    public PostEntity toEntity(){
        return PostEntity.builder()
                .postName(postName)
                .postContent(postContent)
                .build();
    }
}
