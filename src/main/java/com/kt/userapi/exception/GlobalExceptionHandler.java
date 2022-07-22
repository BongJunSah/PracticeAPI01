package com.kt.userapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ControllerAdvice는 컨트롤러 전역에서 발생할 수 있는 예외를 잡아 Throw 해주고,
//@ExceptionHandler는 특정 클래스에서 발생할 수 있는 예외를 잡아 Throw 합니다.
//일반적으로 @ExceptionHandler는 @ControllerAdvice가 선언된 클래스에 포함된 메서드에 선언합니다.
//@RestControllerAdvice = @ResponseBody + @ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //ResponseEntity<ErrorResponse>
    //ResponseEntity<T>는 HTTP Request에 대한 응답 데이터를 포함하는 클래스로,
    //<Type>에 해당하는 데이터와 HTTP 상태 코드를 함께 리턴할 수 있습니다.
    //우리는 예외가 발생했을 때, ErrorResponse 형식으로 예외 정보를 Response로 내려주게 됩니다.
    /*
     * Developer Custom Exception
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        log.error("handleCustomException: {}", e.getErrorCode());
        return ResponseEntity
                .status(e.getErrorCode().getStatus().value())
                .body(new ErrorResponse(e.getErrorCode()));
    }

    /*
     * HTTP 405 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value())
                .body(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED));
    }

    /*
     * HTTP 500 Exception
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error("handleException: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}