package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import conf.JavaConfig;
import conf.JavaConfig2;

public class Main3 {
	public static void main(String[] args) {
		// 1. 스프링컨텍스트 생성(컨테이너 역할)
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(JavaConfig2.class);
		
		System.out.println("-------------생성 후------------");
		// 2. 스프링으로부터 빈을 제공 받음
		Client client = ctx.getBean("client", Client.class);
		
		// 3. 빈 사용
		client.send();
		
		// 4. 컨테이너 종료
		ctx.close();
	}
}
