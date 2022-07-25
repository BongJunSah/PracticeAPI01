package com.kt.userapi.dto;

import com.kt.userapi.domain.UserEntity;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
// public String getId() 자동 생성.
@Setter
// public void setId(String id) 자동 생성.
@ToString(exclude = "password")
// toString() Method 자동 생성.
@NoArgsConstructor
public class UserRequestDto {
    //@NotBlank : 입력폼이 비어있는 상태로 요청을 보내면 에러
    //@Size(min = 2, max = 10, message = "Size Error!") : 입력 값의 사이즈 지정.
    //@Email : 이메일 형식만 입력 가능.
    //@Pattern(regexp = "")
    //ex)
    //@Pattern(regexp="^[a-zA-Z0-9]{4.8}$", message = "아이디 영문 대/소문자, 숫자 4-8자")
    //@Pattern(regexp="[가-힣]{2,5}", message = "이름 한글 2~5자")
    //@Pattern(regexp="^(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$")
    //@UniqueElements : Collections 내의 요소들이 중복되지 않을 것.
    //@Digits : 숫자인지 판별
    //@CreditCardNumber : 신용카드 번호
    //@Future[OrPresent], @Past[OrPresent] : 각각 미래 또는 이전 시간이거나 같을 것. Data Calender...
    //@AssertTrue, @AssertFalse
    //@DateTimeFormat(pattern = "yyyy/MM/dd(E)")
    //@NumberFormat(style = style.NUMBER)
    @NotNull(message = "Name can not be null.")
    private String name;
    @NotNull(message = "Password can not be null.")
    private String password;
    @Email(message = "Email can not be null.")
    private String email;
    @NotNull(message = "Age can not be null.")
    private int age;

    @Builder
    public UserRequestDto(String name, String password, String email, int age) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public UserEntity toEntity(){
        return UserEntity.builder()
                .name(name)
                .password(password)
                .email(email)
                .age(age)
                .build();
    }
}
