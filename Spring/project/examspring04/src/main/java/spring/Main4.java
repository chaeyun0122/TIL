package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.JavaConfig;
import config.JavaConfig2;
import config.JavaConfigPart1;
import config.JavaConfigPart2;
import config.JavaConfigPartMain;

public class Main4 {
	public static void main(String[] args) {

		ApplicationContext ctx = 
				new AnnotationConfigApplicationContext(JavaConfigPartMain.class);
		
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

