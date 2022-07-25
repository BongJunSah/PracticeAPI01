package com.kt.userapi.repository;

import com.kt.userapi.domain.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserEntityRepositoryTest {

    @Autowired
    UserRepository userRepository;
    /*
    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }
    */

    @Test
    public void 유저저장_불러오기() {
        //given : 생성자(Builder)를 사용해 Posts 객체를 생성하고 저장한다.
        userRepository.save(UserEntity.builder()
                .userName("sylee")
                .userPassword("secret")
                .userEmail("sylee@gmail.com")
                .userAge(17)
                .build());

        //when : 이후 findAll(); 메소드를 통해 List로 받아온다.
        UserEntity userEntity = userRepository.findByUserEmail("sylee@gmail.com").get();

        //then :
        assertThat(userEntity.getUserName()).isEqualTo(("sylee"));
        assertThat(userEntity.getUserPassword()).isEqualTo("secret");
        assertThat(userEntity.getUserEmail()).isEqualTo("sylee@gmail.com");
        assertThat(userEntity.getUserAge()).isEqualTo(17);
    }

    @Test
    public void 시간_등록 () {
        //given : 시작 시간과 저장을 하여 시간을 등록한다.
        LocalDateTime now = LocalDateTime.now();
        userRepository.save(UserEntity.builder()
                .userName("time_test")
                .userPassword("secret")
                .userEmail("time_test@gmail.com")
                .userAge(28)
                .build());
        //when : 조회를 하였을 때
        UserEntity userEntity = userRepository.findByUserName("time_test").get();

        //then : 입력된 시간이 해당 method 시작 시간보다 이후인지 체크한다.
        assertThat(userEntity.getCreatedDate()).isAfter(now);
        assertThat(userEntity.getModifiedDate()).isAfter(now);
    }
}