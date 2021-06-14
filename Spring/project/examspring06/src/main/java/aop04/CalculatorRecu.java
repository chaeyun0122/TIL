package aop04;

// 재귀를 이용하는 팩토리얼이 정의된 Calculator 구현체
public class CalculatorRecu  {
	
	public long factorial(long n) {
		if(n == 0) {
			return 1;
		}
		else {
			return n * factorial(n - 1);
		}
	}
}
