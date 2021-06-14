package aop01;

public class Main {
	public static void main(String[] args) {
		Calculator calc1 = new CalculatorImpl();
		Calculator calc2 = new CalculatorRecu();
		
		// 이 기능에 수행 시간을 구하는 기능을 추가하고 싶다면?
		// 직접 기능 수정1
		long start = 0;
		long end = 0;
		
		start = System.nanoTime();
		long result = calc1.factorial(4);
		end = System.nanoTime();
		System.out.printf("%d:수행시간:%d\n", result, end-start);
		
		start = System.nanoTime();
		result = calc2.factorial(4);
		end = System.nanoTime();
		System.out.printf("%d:수행시간:%d\n", result, end-start);
		
		System.out.println();
	}
}
