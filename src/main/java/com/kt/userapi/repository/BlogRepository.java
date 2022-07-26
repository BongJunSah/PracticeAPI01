package com.kt.userapi.repository;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {

    Optional<BlogEntity> findByBlogName(String blogName);

    //포함여부를 확인하도록 다시 구성해야한다.
    //Optional<BlogEntity> findByBlogContent(String blogContent);

    boolean existsByBlogName(String blogName);

}
