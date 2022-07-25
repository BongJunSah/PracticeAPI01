package com.kt.userapi.repository;

import com.kt.userapi.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//JpaRepository<data, key>의 형태로 interface를 받아온다.
//구현체는 jpa에서 자동 생성된다. 자동 DI
//repository : DAO 로서 DB에 역할을 수행한다.
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserEmail(String userEmail);
    List<UserEntity> findAllByUserAge(int userAge);
    boolean existsByUserName(String userName);
    boolean existsByUserEmail(String userEmail);
}
