package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import conf.JavaConfigPrototype;

public class Main4 {
	public static void main(String[] args) {
		// 1. 스프링컨텍스트 생성(컨테이너 역할)
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(JavaConfigPrototype.class);
		
		System.out.println("-------------생성 후------------");
		// 2. 스프링으로부터 빈을 제공 받음
		Client client1 = ctx.getBean("client", Client.class);
		Client client2 = ctx.getBean("client", Client.class);
		
		// 동일성 확인
		System.out.println(client1 != client2);
		
		// 3. 빈 사용
		client1.send();
		client2.send();
		
		// 4. 컨테이너 종료
		// scope가 prototype이라면 컨테이너 소멸 전 destroy 메서드 직접 호출 필요
		try {
			client1.destroy();
			client2.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ctx.close();
	}
}
