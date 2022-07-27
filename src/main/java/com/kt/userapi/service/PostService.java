package com.kt.userapi.service;

import com.kt.userapi.domain.BlogEntity;
import com.kt.userapi.domain.PostEntity;
import com.kt.userapi.dto.BlogRequestDto;
import com.kt.userapi.dto.BlogResponseDto;
import com.kt.userapi.dto.PostRequestDto;
import com.kt.userapi.dto.PostResponseDto;
import com.kt.userapi.exception.CustomException;
import com.kt.userapi.exception.ErrorCode;
import com.kt.userapi.repository.BlogRepository;
import com.kt.userapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    private final BlogRepository blogRepository;

    // 포스트 생성.
    // 블로그를 미리 연결해서 저장한다.
    // 실행(begin), 종료(commit), 예외(rollback)을 자동으로 처리한다.
    //@Transactional
    public Long createPost(PostRequestDto postRequestDto, Long blogId) {
        //이름이 중복되는 블로그는 안된다.
        if (postRepository.existsByPostName(postRequestDto.getPostName()))
            return (long)-1;
        //Exception 처리
        PostEntity postEntity = postRequestDto.toEntity();
        BlogEntity blogEntity = blogRepository.findById(blogId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND)
        );
        postEntity.changeBlogEntity(blogEntity);
        postEntity = postRepository.save(postEntity);
        return postEntity.getPostId();
    }

    // 전체 포스트 리스트 조회.
    public List<PostResponseDto> findAllPost() {
        Sort sort = Sort.by(Sort.Direction.DESC, "postId", "createdDate");
        List<PostEntity> postEntityList = postRepository.findAll(sort);
        return postEntityList.stream().map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 포스트 이름 사용 포스트 조회
    public PostResponseDto findPostByName(String postName) {
        PostEntity postEntity = postRepository.findByPostName(postName).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));// 파라미터가 없을 때 () 람다
        return new PostResponseDto(postEntity);
    }

    //@Transactional
    //포스트 갱신
    public Long updatePost(final Long postId, final PostRequestDto postRequestDto) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        //이름이 중복되는 변경은 안된다.
        if (postRepository.existsByPostName(postRequestDto.getPostName()))
            return (long)-1;
        //Entity를 변경하면 update쿼리가 자동 실행된다.
        postEntity.update(postRequestDto.getPostName(), postRequestDto.getPostContent());
        return postId;
    }

    // 포스트 제거
    public void deletePost(Long postId) {
        //회원가입이 안되어 있는 경우.
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        postRepository.delete(postEntity);
    }

    // 전체 블로그 삭제.
    public void deleteAllPost() {
        postRepository.deleteAll();
    }

    // 이름으로 중복 검사.
    public boolean checkNameDuplicate(String name) {
        return postRepository.existsByPostName(name);
    }
}
