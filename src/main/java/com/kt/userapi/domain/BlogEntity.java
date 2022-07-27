package com.kt.userapi.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "blog_entity")
public class BlogEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "blog_id", updatable = false, insertable = false)
    private Long blogId;

    @Column(name = "blog_name",length = 255, nullable = false)
    private String blogName;

    @Column(name = "blog_intro", length = 500)
    private String blogIntro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "blogEntity", fetch = FetchType.LAZY)
    private List<PostEntity> postEntities;

    @Builder
    public BlogEntity(String blogName, String blogIntro) {
        this.blogName = blogName;
        this.blogIntro = blogIntro;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void addPostEntity(PostEntity postEntity) {
        this.postEntities.add(postEntity);
    }

    public void changeUserEntity(UserEntity userEntity) {
        this.setUserEntity(userEntity);
        this.userEntity.setBlogEntity(this);
    }

    public void update(String blogName, String blogIntro) {
        this.blogName = blogName;
        this.blogIntro = blogIntro;
    }
}
