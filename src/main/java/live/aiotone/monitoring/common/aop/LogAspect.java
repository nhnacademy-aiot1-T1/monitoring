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
   * 스테레오 타입 빈 대상.
   */
  @Pointcut("bean(*Controller) || " + "bean(*Service*) ||" + "bean(*Repository)")
  public void stereoTypePointcut() {
    // 스테레오 타입 빈 네이밍 규칙으로 지정
  }


  /**
   * 타겟 메서드의 실행시간을 로깅.
   *
   * @param joinPoint : 타겟 메서드
   * @return Object : 타겟 메서드의 리턴값
   * @throws Throwable : 타겟 메서드의 예외
   */
  @Around("stereoTypePointcut()")
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
  @Before("stereoTypePointcut()")
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
  @AfterReturning(value = "stereoTypePointcut()", returning = "result")
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