package aop04;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext("classpath:appctx4.xml");
		
		CalculatorImpl calc1 = ctx.getBean("calcImpl", CalculatorImpl.class);
		CalculatorRecu calc2 = ctx.getBean("calcRecu", CalculatorRecu.class);
		
		System.out.println(calc1.factorial(4));
		System.out.println(calc2.factorial(4));
	}
}
