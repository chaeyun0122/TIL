package aop02;

public class Main {
	public static void main(String[] args) {
		CalculatorImpl calc1 = new CalculatorImpl();
		Calculator calc2 = new CalculatorRecu();
		
		// 이 기능에 수행 시간을 구하는 기능을 추가하고 싶다면?
		long result = calc1.factorial(4);
		System.out.printf("%d:수행시간:%d\n", result, calc1.getCalcTime());
		
		System.out.println();
	}
}
