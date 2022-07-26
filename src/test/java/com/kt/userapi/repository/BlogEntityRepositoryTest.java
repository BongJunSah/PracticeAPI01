package com.kt.userapi.repository;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BlogEntityRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogRepository blogRepository;

    @Test
    void 블로그_저장하고_불러오기(){
        blogRepository.save(BlogEntity.builder()
                .blogName("bjsah's blog")
                .blogIntro("welcome to bjsah's blog")
                .build());
        BlogEntity blogEntity = blogRepository.findByBlogName("bjsah's blog").get();
        assertThat(blogEntity.getBlogName()).isEqualTo("bjsah's blog");
    }

    @Test
    void 블로그에유저등록하기() {
        BlogEntity createdBlogEntity = blogRepository.save(BlogEntity.builder()
                .blogName("bjsah's blog")
                .blogIntro("welcome to bjsah's blog")
                .build());
        UserEntity createdUserEntity = userRepository.save(UserEntity.builder()
                .userName("time_test")
                .userPassword("secret")
                .userEmail("time_test@gmail.com")
                .userAge(28)
                .build());

        //현재 저장이후 각 바꿔 주면 Context 영속성에 의해 값이 DB에서도 변경된다.
        createdUserEntity.changeBlogEntity(createdBlogEntity);

        BlogEntity searchedBlogEntity = blogRepository.findByBlogName("bjsah's blog").get();
        UserEntity searchedUserEntity = userRepository.findByUserName("time_test").get();

        //찾은 BlogEntity에서 접근한 UserId와 찾은 UserEntity의 UserId가 동일한지 체크
        assertThat(searchedBlogEntity.getUserEntity().getUserId()).isEqualTo(searchedUserEntity.getUserId());
        //찾은 UserEntity에서 접근한 BlogId와 찾은 BlogEntity의 BlogId가 동일한지 체크
        assertThat(searchedUserEntity.getBlogEntity().getBlogId()).isEqualTo(searchedBlogEntity.getBlogId());
    }
}