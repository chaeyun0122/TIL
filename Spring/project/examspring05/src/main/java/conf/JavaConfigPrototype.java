package conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import spring.Client;
import spring.Client2;

@Configuration
public class JavaConfigPrototype {
	
	@Bean
	@Scope("prototype") // getBean을 할 때 마다 계속 생성
	public Client client() {
		Client client = new Client();
		client.setHost("개똥");
		return client;
	}
}
