package com.kt.userapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.sound.midi.SysexMessage;

@Aspect
@Slf4j
// AOP 동작 UserController -> Proxy(UserService) -> joinPoint.proceed() -> Real(UserService)
// Spring Container : Proxy로 임시를 생성하여 DI를 통해서 Dependency를 설정한다.
// AOP는 정형화 되지 않은 특수 상황이 많으므로 bean으로 관리하는 것이 좋다.
@Component
public class TimeTraceAop {
    // com.kt.userapi 하위 모든 것에 적용
    // @Before("${pattern}") : 이전에 진행된다. 메소드의 반환값은 void 이어야 한다.
    // @After("${pattern}") : 이후에 진행된다. 메소드의 반환값은 void 이어야 한다.
    // @Around("{pattern}") : * (모든것) .. (인자가 0개 이상)
    // 현재 모든 반환값 userapi 아래의 산하 0개 이상의 디렉토리에서 모든 인자 0개 이상 메소드
    // execution pattern : 특정 메소드 지정가능. 반환 타입, 패키지.패키지....클래스.메소드(인자)
    // within : 특정 클래스 안에 있는 메서드들을 모두 지정. 패키지.패키지..클래스
    // @within : 특정 어노테이션에 대해 진행.
    // this / target(상위 객체 타입) : 하위 객체에 대해서 진행된다. retention이 class여야 한다.
    // @target : retention이 런타임 이어야 한다.
    // @annotation : 특정 어노테이션이 붙은 객체에 대해 적용.
    @Around("execution(* com.kt.userapi..*(..))")
    public Object execute (ProceedingJoinPoint joinPoint) throws Throwable {
        long start= System.currentTimeMillis();
        log.info("START : {}.", joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("End : {} {} ms.", joinPoint, timeMs);
        }
    }
}
