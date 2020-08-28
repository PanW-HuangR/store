package cn.tedu.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerAspect {
	
	@Around("execution(* cn.tedu.store.service.impl.*.*(..))")
	public Object a(ProceedingJoinPoint point) throws Throwable {
		// 记录开始时间
	    long start = System.currentTimeMillis();

	    // 相当于执行了Service中的注册或登录或其它方法
	    // 调用的proceed()会抛出异常，必须继续抛出
	    Object result = point.proceed();

	    // 记录结束时间
	    long end = System.currentTimeMillis();

	    // 计算耗时
	    System.err.println("耗时：" + (end - start) + "ms.");

	    return result;
	}
	
}
