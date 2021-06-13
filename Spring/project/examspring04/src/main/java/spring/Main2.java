package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.JavaConfig;
import config.JavaConfig2;

public class Main2 {
	public static void main(String[] args) {
		// xml이 아니라 java 코드를 사용할 것이기 때문에 Annotation~을 사용한다.
		ApplicationContext ctx = 
				new AnnotationConfigApplicationContext(JavaConfig2.class);
		MemberRegisterService regSvc = 
				ctx.getBean("memberRegSvc", MemberRegisterService.class);
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

