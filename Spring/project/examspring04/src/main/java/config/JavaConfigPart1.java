package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;

@Configuration
public class JavaConfigPart1 {
	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	} // <bean id = "memberDao" class="spring.MemberDao"/> 와 같은 개념
	// 생성된 memberDao가 있다면 새로 만들지 않고 한 번만 만든다.
	
	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao()); //명시적 의존 주입
	}
}
