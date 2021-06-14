package aop03;

// 프록시 패턴 (Proxy pattern) : 객체 지향 디자인 패턴
public class Main {
	public static void main(String[] args) {
		ExecTimeCalculator calc1 = new ExecTimeCalculator(new CalculatorImpl());
		ExecTimeCalculator calc2 = new ExecTimeCalculator(new CalculatorRecu());
		
		System.out.println(calc1.factorial(4));
		System.out.println(calc2.factorial(4));
	}
}
