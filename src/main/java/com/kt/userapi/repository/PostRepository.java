package com.kt.userapi.repository;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByPostName(String postName);

    //포함여부를 확인하도록 다시 구성해야한다.
    //Optional<BlogEntity> findByBlogContent(String blogContent);

    boolean existsByPostName(String postName);
}
