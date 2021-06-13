package conf;

import org.springframework.context.annotation.Bean;

import spring.Client;
import spring.Client2;
import spring.Client3;
import spring.Client4;

public class JavaConfig2 {
	
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
	
	@Bean
	public Client3 client3() {
		Client3 client3 = new Client3();
		client3.setHost("소똥이에게");
		return client3;
	}
	
	@Bean
	public Client4 client4() {
		Client4 client4 = new Client4();
		client4.setHost("똥이에게");
		return client4;
	}
}
