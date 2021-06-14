package aop06;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(JavaConfig.class);
		
		CalculatorImpl calc1 = ctx.getBean("calcImpl", CalculatorImpl.class);
		CalculatorRecu calc2 = ctx.getBean("calcRecu", CalculatorRecu.class);
		
		System.out.println(calc1.factorial(4));
		System.out.println(calc2.factorial(4));
	}
}
