package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.JavaConfig;
import config.JavaConfig2;
import config.JavaConfigPart1;
import config.JavaConfigPart2;
import config.JavaConfigPartMain;
import config.JavaMainConfig;

public class Main5 {
	public static void main(String[] args) {
		// xml이 아니라 java 코드를 사용할 것이기 때문에 Annotation~을 사용한다.
		ApplicationContext ctx = 
				new AnnotationConfigApplicationContext(JavaMainConfig.class);
		MemberRegisterService regSvc = 
				ctx.getBean("memberRegisterService", MemberRegisterService.class);
		MemberInfoPrinter infoPrinter = 
				ctx.getBean("infoPrinter", MemberInfoPrinter.class);
		
		RegisterRequest regReq = new RegisterRequest();
		regReq.setEmail("aaa@aaa.com");
		regReq.setName("홍길동");
		regReq.setPassword("1234");
		regReq.setConfirmPassword("1234");

		regSvc.regist(regReq); //등록
		
		infoPrinter.printMemberInfo("aaa@aaa.com");
	}
}

