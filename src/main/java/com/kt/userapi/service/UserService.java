package com.kt.userapi.service;

import com.kt.userapi.domain.UserEntity;
import com.kt.userapi.exception.CustomException;
import com.kt.userapi.exception.ErrorCode;
import com.kt.userapi.repository.UserRepository;
import com.kt.userapi.dto.UserRequestDto;
import com.kt.userapi.dto.UserResponseDto;
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
public class UserService {
    // final 선언의 이유
    // 불변의 의미를 담고 있다.
    private final UserRepository userRepository;

    // DI Injection중 field 주입 방식. 별로 비추천 하는 방식이다.
    //@Autowired private final MemberRepository getMemberRepository;

    // 내가 직접 new 하지 않고 외부에서 넣어준다. 이런것을 Dependency Injection
    // Spring Container에서 자동 DI
    //@Autowired
    // 현재는 DI의 3가지 방법중 Contructor DI Injection를 통해 하는 것을 하였다.
    /*
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    */

    // 회원 생성.
    // 실행(begin), 종료(commit), 예외(rollback)을 자동으로 처리한다.
    //@Transactional
    public Long createAccount(UserRequestDto userRequestDto) {
        //이름과 이메일이 중복되는 회원은 안된다.
        if (userRepository.existsByUserName(userRequestDto.getUserName())
                || userRepository.existsByUserEmail(userRequestDto.getUserEmail()))
            return (long)-1;
        //Exception 처리
        UserEntity userEntity = userRepository.save(userRequestDto.toEntity());
        return userEntity.getUserId();
    }

    // 전체 리스트 조회.
    public List<UserResponseDto> findAllAccount() {
        Sort sort = Sort.by(Sort.Direction.DESC, "userId", "createdDate");
        List<UserEntity> userEntityList = userRepository.findAll(sort);
        return userEntityList.stream().map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    // 이름 사용 회원 조회
    public UserResponseDto findAccountByName(String name) {
        UserEntity userEntity = userRepository.findByUserName(name).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));// 파라미터가 없을 때 () 람다
        return new UserResponseDto(userEntity);
    }
    // email 사용 회원 조회
    public UserResponseDto findAccountByEmail(String email) {
        UserEntity userEntity = userRepository.findByUserEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        return new UserResponseDto(userEntity);
    }

    // Age 사용 회원 조회
    public List<UserResponseDto> findAllAccountByAge(int age) {
        List<UserEntity> userEntityList = userRepository.findAllByUserAge(age);
        return userEntityList.stream().map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    // *영속성 컨텍스트 : Entity를 영구히 저장하는 환경.
    // API와 DB사이에서 객체를 보관하는 가상 영역이다.
    // JPA의 EntityManager도 Entity생성, 조회 시점에 영속성 컨텍스트에 Entity를 보관 및 관리한다.
    // *더티 체킹(Dirty Checking)
    // Entity 조회 -> 영속성 컨텍스트에 저장. -> Entity 값이 바뀌면 -> Transaction이 종료되는 시점에 update 쿼리 시작.
    // 회원 정보 변경
    //@Transactional
    public Long updateAccount(final Long userId, final UserRequestDto userRequestDto) {
        // orElseThrow
        // userEntity가 null 값일 경우 NOT_FOUND 에러를 보낸다.
        // 조회를 하여 영속성 컨텍스트에 Entity가 저장된다.
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        //이름과 이메일이 중복되는 변경은 안된다.
        if (userRepository.existsByUserName(userRequestDto.getUserName())
                || userRepository.existsByUserEmail(userRequestDto.getUserEmail()))
            return (long)-1;
        //Entity를 변경하면 update쿼리가 자동 실행된다.
        userEntity.update(userRequestDto.getUserName(), userRequestDto.getUserPassword(), userRequestDto.getUserEmail(), userRequestDto.getUserAge());
        return userId;
    }

    // 회원 탈퇴.
    public void deleteAccount(Long id) {
        //회원가입이 안되어 있는 경우.
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        userRepository.delete(userEntity);
    }

    // 전체 회원 삭제.
    public void deleteAllAccount() {
        userRepository.deleteAll();
    }

    // refactoring : ctrl + T
    // option + command + M : Method 추출
    // 만약 이름을 가지고 검색을 하였는데 있는 경우 Exception을 생성한다.
    /*
    private void validateDuplicateUser(UserEntity userEntity) {
        userRepository.findByName(userEntity.getName())
                .ifPresent(tempUserEntity -> {
                    log.error("The User Already Exists Error in validateDuplicateUser.");
                    throw new IllegalStateException("The User Already Exists.");
                });
    }
     */
}