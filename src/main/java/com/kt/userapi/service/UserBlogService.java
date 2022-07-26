package com.kt.userapi.service;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.UserEntity;
import com.kt.userapi.exception.CustomException;
import com.kt.userapi.exception.ErrorCode;
import com.kt.userapi.repository.BlogRepository;
import com.kt.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class UserBlogService {

    private final BlogRepository blogRepository;

    private final UserRepository userRepository;

    public void connectBlogToUser(final Long blogId, final Long userId)
    {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        blogEntity.changeUserEntity(userEntity);
    }
}
