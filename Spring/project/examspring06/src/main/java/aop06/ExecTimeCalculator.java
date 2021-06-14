package aop06;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

// 공통 기능 구현
@Aspect
public class ExecTimeCalculator {
	
	@Around("execution(public * aop06..*(long))")
	public Object measure(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.nanoTime();
		
		try {
			Object result = pjp.proceed(); // 핵심기능 호출
			return result;
		} finally {
			long end = System.nanoTime();
			Signature sig = pjp.getSignature(); // Signature : 함수의 정보를 가지고있음
			System.out.printf("%s.factorial(%s):수행시간%d\n", 
									pjp.getClass().getSimpleName(),
									Arrays.toString(pjp.getArgs()),
									(end-start));
		}
	}
}
