package aop00;

public class Main {
	public static void main(String[] args) {
		Calculator calc1 = new CalculatorImpl();
		Calculator calc2 = new CalculatorRecu();
		
		System.out.println(calc1.factorial(4));
		System.out.println(calc2.factorial(4));
	}
}
