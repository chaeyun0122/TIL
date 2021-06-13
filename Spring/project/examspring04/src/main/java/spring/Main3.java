package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.JavaConfig;
import config.JavaConfig2;
import config.JavaConfigPart1;
import config.JavaConfigPart2;
import config.JavaConfigPart2_2;

public class Main3 {
	public static void main(String[] args) {
		// 두 개 이상의 자바 설정파일을 포함
		ApplicationContext ctx = 
				new AnnotationConfigApplicationContext(JavaConfigPart1.class, JavaConfigPart2.class);
		
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

