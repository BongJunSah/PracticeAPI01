package com.kt.userapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//Unchecked Exception인 Runtime Exception을 꼭 상속 받아야 한다.
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}