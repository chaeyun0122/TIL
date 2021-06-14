package aop01;

// 반복문을 이용한 팩토리얼이 정의된 Calculator 구현체
public class CalculatorImpl implements Calculator {
	
	@Override
	public long factorial(long n) {
		long result = 1;
		for(int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
	}
}
