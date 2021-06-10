package spring02;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext("classpath:applicationContext2.xml");
		
		Greeter greeter1 = ctx.getBean("greeter", Greeter.class); 
		Greeter greeter2 = ctx.getBean("greeter", Greeter.class);
		
		System.out.println(greeter1 == greeter2);
		ctx.close();
	}
}
