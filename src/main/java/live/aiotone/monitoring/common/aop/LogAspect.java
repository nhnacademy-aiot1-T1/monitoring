package live.aiotone.monitoring.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

/**
 * 로깅 Aspect.
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

  /**
   * 스테레오 타입을 가진 빈들을 대상으로 한다.
   */
  @Pointcut("within(*..*Controller) || "
      + "within(@org.springframework.stereotype.Service *) || "
      + "execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
  public void stereoTypePointCut() {
    // 스테레오 타입 포인트 컷
  }


  /**
   * 타겟 메서드의 실행시간을 로깅.
   *
   * @param joinPoint : 타겟 메서드
   * @return Object : 타겟 메서드의 리턴값
   * @throws Throwable : 타겟 메서드의 예외
   */
  @Around("stereoTypePointCut()")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long endTime = System.currentTimeMillis();

    long executionTime = endTime - startTime;
    String className = getRealClassName(joinPoint);
    String methodName = joinPoint.getSignature().getName();

    log.info("[{}] - {} executionTime : {} ms", className, methodName, executionTime);
    return result;
  }

  /**
   * 타겟 메서드의 인자값을 로깅.
   *
   * @param joinPoint : 타겟 메서드
   */
  @Before("stereoTypePointCut()")
  public void logArguments(JoinPoint joinPoint) {

    String className = getRealClassName(joinPoint);
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();

    log.debug("[{}] - call {} args({}) :", className, methodName, args.length);
    for (int i = 0; i < args.length; i++) {
      log.debug("==================== args {} : ( Type={}, Value={} ) ", i + 1,
          args[i].getClass().getSimpleName(), args[i]);
    }
  }

  /**
   * 타겟 메서드의 리턴값을 로깅.
   *
   * @param joinPoint : 타겟 메서드
   * @param result    : 타겟 메서드의 리턴값
   */
  @AfterReturning(value = "stereoTypePointCut()", returning = "result")
  public void logReturnValue(JoinPoint joinPoint, Object result) {
    String className = getRealClassName(joinPoint);
    String methodName = joinPoint.getSignature().getName();
    Class<?> returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();

    if (returnType.isPrimitive()) {
      log.debug("[{}] - {} return({}) : {}", className, methodName, returnType.getSimpleName(),
          result);
      return;
    }
    log.debug("[{}] - {} return({}) : {}", className, methodName, returnType.getSimpleName(),
        result.getClass().getSimpleName());
  }

  private String getRealClassName(JoinPoint joinPoint) {
    if (AopUtils.isAopProxy(joinPoint.getTarget())) {
      Class<?> realClass = AopProxyUtils.proxiedUserInterfaces(joinPoint.getTarget())[0];
      return realClass.getSimpleName();
    } else {
      return joinPoint.getTarget().getClass().getSimpleName();
    }
  }
}