package com.kt.userapi;

import com.kt.userapi.aop.TimeTraceAop;
import com.kt.userapi.repository.UserRepository;
import com.kt.userapi.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class UserConfig {
    // Spring이 올라올 때 Bean으로서 생성된 것을 userRespository가 들어온다.
    // Controller는 안된다.
    // xml 등록도 하였지만 지금은 잘 안한다.
    // 직접 등록시의 장점
    // DI 해주는 Annotation
    /*private EntityManager em;

    @Autowired
    public UserConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    // UserReposiotory : Interface
    // JdbcUserRepository : 구현
    // 현재는 별도의 implements를 구현하지 않았다.
    @Bean
    public UserRepository userRepository() {
        return new UserRepository(em);
    }
     */

    // AOP는 정형화 되지 않은 특수 상황이 많으므로 bean으로 관리하는 것이 좋다.
    /*@Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
     */
}
