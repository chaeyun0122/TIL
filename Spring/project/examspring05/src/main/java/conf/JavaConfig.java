package conf;

import org.springframework.context.annotation.Bean;

import spring.Client;
import spring.Client2;

public class JavaConfig {
	
	@Bean
	public Client client() {
		Client client = new Client();
		client.setHost("개똥이에게");
		return client;
	}
	
	@Bean(initMethod = "startMethod", destroyMethod = "endMethod")
	public Client2 client2() {
		Client2 client2 = new Client2();
		client2.setHost("말똥이에게");
		return client2;
	}
}
