package com.kt.userapi.dto;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.UserEntity;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BlogRequestDto {
    @NotNull(message = "Blog name can not be null.")
    private String blogName;
    @NotNull(message = "Blog intro can not be null.")
    private String blogIntro;

    @Builder
    public BlogRequestDto(String blogName, String blogIntro) {
        this.blogName = blogName;
        this.blogIntro = blogIntro;
    }

    public BlogEntity toEntity(){
        return BlogEntity.builder()
                .blogName(blogName)
                .blogIntro(blogIntro)
                .build();
    }
}
