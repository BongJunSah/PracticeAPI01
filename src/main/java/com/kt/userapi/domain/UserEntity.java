package com.kt.userapi.domain;

import lombok.*;

import javax.persistence.*;

//Private : 본인 class
//Default : 본 Package
//Protected 제한 : class와 상속받은 class의 package 내부 허용.
//Public : 제한 없음
//access = AccessLevel.PROTECTED -> Entity 클래스를 프로젝트 코드상에서 기본 생성자로 생성하는 것은 막고, JPA에서는 허용한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
// @Setter
// Setter의 경우 언제, 어디서 바뀌어야 하는지 명확하게 이해를 하고 사용하는 것이 바람직하다.
// 테이블과 링크될 클래스임을 나타낸다.
// SalesManeger.java -> sales_maneger table 처럼 _ 언더바를 사용해 매칭한다.
// @Annotation javax 활용. hibernate은 deprecated : 사용 X
// Domain : 직접 DB에 저장되는 형태. 절대 dto와 병행해서 사용해서는 안된다.
@Entity
@Table(name = "user_entity")
public class UserEntity extends BaseTimeEntity{
    // 해당 Table의 Primary Key 임을 나타낸다.
    // 생성 규칙으로서 기본값 AUTO : auto_increment 정수형 값이 된다. long
    @Id
    //@GeneratedValue
    //AUTO : 자동 설정
    //IDENTITY : identity column 활용, 사용자 직접 설정.
    //SEQUENCE : sequence column 활용
    //TABLE : 유일성이 보장된다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    // @Column
    // name : 필드와 매핑할 테이블의 컬럼 이름을 지정.
    // nullable : DDL 생성 시 null 값의 허용 여부. -> primitive 의 경우 null이 불가 false 설정 해줄것.
    // unique : 중복 허용 여부 -> @Table의 uniqueConstraints 사용이 권장.
    // length : String type에서 문자열 길이 정보
    // precision : 소수점을 포함한 전체 자리수 설정
    // columnDefinition : 데이터베이스 컬럼 정보 설정 : ex) varchar (100) default 'EMPTY'
    // scale : 소수의 자리수 설정.
    //@Column(length = 500, nullable = false)
    //Char VS Varchar : Char 은 사이즈 지정. VarChar은 그 크기만큼만 보여준다.
    @Column(name = "user_name",length = 500, nullable = false)
    private String userName;

    @Column(name = "user_password", columnDefinition = "TEXT", nullable = false)
    private String userPassword;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_age")
    private int userAge;

    @OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private BlogEntity blogEntity;

    //0. 점증적 생성자 패턴
    //필수로 받아야할 인자(title, content, author)들이 있고, 선택적으로 받아야 하는 인자(id)가 있다.
    //-> 선택적 인자를 받기 위해 생성자가 계속해서 생성된다. 현재 2개.
    //1. 빌더 패턴.
    //불필요한 생성자 제거, 데이터 순서 상관 없이 객체 생성 가능
    //한번에 객체를 생성하므로 일관성이 깨지지 않는다.
    //ex) Posts post = Posts.builder()
    //      .title("title1")
    //      .content("content1")
    //      .author("author1")
    //      .build();
    //기본적으로 값 입력은 생성자를 통해 진행.
    //변경은 public method를 만들어 제공한다.
    @Builder
    public UserEntity(String userName, String userPassword, String userEmail, int userAge) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userAge = userAge;
    }

    public void setBlogEntity(BlogEntity blogEntity) {
        this.blogEntity = blogEntity;
    }

    // Entity를 update하는 함수.
    public void update(String userName, String userPassword, String userEmail, int userAge) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userAge = userAge;
    }
}
