package spring01;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext("classpath:applicationContext.xml");
		
		Greeter greeter = ctx.getBean("greeter", Greeter.class); //
		// greeter.setFormat("%s님 안녕하세요");
		String result = greeter.greet("홍길동");
		System.out.println(result);
		ctx.close();
	}
}
