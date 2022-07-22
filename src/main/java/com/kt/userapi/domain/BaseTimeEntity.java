package com.kt.userapi.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// 모든 Entity에 있어서 최상위 클래스가 된다.
// 상속할 경우 @CreateDate, @LastModifiedDate도 Column으로 받게한다.
@MappedSuperclass
// JPA Auditing : 자동으로 JPA에서 값을 넣어주는 기능을 활성화한다.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // Entity 생성 시간이 자동 저장된다.
    @CreatedDate
    private LocalDateTime createdDate;

    // Entity 변경 시간이 자동 저장된다.
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}