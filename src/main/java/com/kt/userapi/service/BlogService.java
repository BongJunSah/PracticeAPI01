package com.kt.userapi.service;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.UserEntity;
import com.kt.userapi.dto.*;
import com.kt.userapi.exception.CustomException;
import com.kt.userapi.exception.ErrorCode;
import com.kt.userapi.repository.BlogRepository;
import com.kt.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 생성.
    // 실행(begin), 종료(commit), 예외(rollback)을 자동으로 처리한다.
    //@Transactional
    public Long createBlog(BlogRequestDto blogRequestDto) {
        //이름이 중복되는 블로그는 안된다.
        if (blogRepository.existsByBlogName(blogRequestDto.getBlogName()))
            return (long)-1;
        //Exception 처리
        BlogEntity blogEntity = blogRepository.save(blogRequestDto.toEntity());
        return blogEntity.getBlogId();
    }

    // 전체 블로그 리스트 조회.
    public List<BlogResponseDto> findAllBlog() {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogId", "createdDate");
        List<BlogEntity> blogEntityList = blogRepository.findAll(sort);
        return blogEntityList.stream().map(BlogResponseDto::new)
                .collect(Collectors.toList());
    }

    // 블로그 이름 사용 블로그 조회
    public BlogResponseDto findBlogByName(String blogName) {
        BlogEntity blogEntity = blogRepository.findByBlogName(blogName).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));// 파라미터가 없을 때 () 람다
        return new BlogResponseDto(blogEntity);
    }

    public List<PostResponseDto> findPostsByBlogId(Long blogId) {
        BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        return blogEntity.getPostEntities().stream().map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
    //@Transactional
    //블로그 갱신
    public Long updateBlog(final Long blogId, final BlogRequestDto blogRequestDto) {
        BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        //이름이 중복되는 변경은 안된다.
        if (blogRepository.existsByBlogName(blogRequestDto.getBlogName()))
            return (long)-1;
        //Entity를 변경하면 update쿼리가 자동 실행된다.
        blogEntity.update(blogRequestDto.getBlogName(), blogRequestDto.getBlogIntro());
        return blogId;
    }

    // 블로그 탈퇴
    public void deleteBlog(Long blogId) {
        //회원가입이 안되어 있는 경우.
        BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        blogRepository.delete(blogEntity);
    }

    // 전체 블로그 삭제.
    public void deleteAllBlog() {
        blogRepository.deleteAll();
    }

    // 이름으로 중복 검사.
    public boolean checkNameDuplicate(String name) {
        return blogRepository.existsByBlogName(name);
    }
}
