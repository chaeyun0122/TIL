package spring03;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext("classpath:applicationContext3.xml");
		
		Greeter greeter1 = ctx.getBean("greeter", Greeter.class);
		Greeter greeter2 = ctx.getBean("greeter", Greeter.class);
		// applicationContext3의 bean 생성 옵션 scope="prototype" 입력하면
		// bean을 생성할 때 서로 다른 객체로 만들 수 있다.
		System.out.println(greeter1 == greeter2);
		ctx.close();
	}
}
