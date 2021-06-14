package aop06;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy // xml의 <aop:aspectj-autoproxy> 역할
public class JavaConfig {
	
	@Bean
	public ExecTimeCalculator execTimeCalculator() {
		return new ExecTimeCalculator();
	}
	
	@Bean
	public CalculatorImpl calcImpl() {
		return new CalculatorImpl();
	}
	
	@Bean
	public CalculatorRecu calcRecu() {
		return new CalculatorRecu();
	}
}
