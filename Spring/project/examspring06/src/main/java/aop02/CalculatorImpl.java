package aop02;

// 반복문을 이용한 팩토리얼이 정의된 Calculator 구현체
/*
 * 까다로우므로 구현하기가 어렵다.
 * 반복문에서 구현하는 기능과 재귀에서 구현하는 기능이 동일함에도 구현이 불편함
 */
public class CalculatorImpl implements Calculator {
	
	private long calcTime = 0;
	private boolean isStart;
	private boolean isFalse;
	long start = System.nanoTime();
	
	@Override
	public long factorial(long n) {
		if(!isStart) {
			isStart = true;
		}
		if (isStart) {
			start = System.nanoTime();
		}
		if(n == 0) {
			return 1;
		}
		else {
			
		}
		
		long result = 1;
		for(int i = 1; i <= n; i++) {
			result *= i;
		}
		long end = System.nanoTime();
		calcTime = end - start;
		return result;
	}
	
	public long getCalcTime() {
		return calcTime;
	}
}
