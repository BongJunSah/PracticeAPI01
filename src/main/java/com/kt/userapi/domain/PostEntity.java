package com.kt.userapi.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "post_entity")
public class PostEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id", updatable = false, insertable = false)
    private Long postId;

    @Column(name = "post_name",length = 255, nullable = false)
    private String postName;

    @Column(name = "post_content", length = 500)
    private String postContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private BlogEntity blogEntity;

    @Builder
    public PostEntity(String postName, String postContent) {
        this.postName = postName;
        this.postContent = postContent;
    }

    public void setBlogEntity(BlogEntity blogEntity) {
        this.blogEntity = blogEntity;
    }

    public void changeBlogEntity(BlogEntity blogEntity) {
        // 만약 blog가 세팅되어있는 경우, 세팅되어있는 blogEntity에 접근해서 제거하고 추가해야한다.
        if (this.getBlogEntity() != null) {
            this.blogEntity.getPostEntities().remove(this);
        }
        this.setBlogEntity(blogEntity);
        blogEntity.addPostEntity(this);
    }

    public void update(String postName, String postContent) {
        this.postName = postName;
        this.postContent = postContent;
    }
}
