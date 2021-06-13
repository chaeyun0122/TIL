package spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
/*
 * 호스트에게 메시지를 전달하는 기능 예시로 작성
 */
public class Client implements InitializingBean, DisposableBean {

	private String host;
	
	public void setHost(String host) {
		this.host = host;
		System.out.println("Client.setHost(String)");
	}
	
	public void send() {
		System.out.println("Client.send() to " + host);
	}
	
	public Client() {
		System.out.println("Client()");
	}
	
	// afterPropertiesSet : InitializingBean 클래스를 통해 초기화될 때 제일 먼저 실행되는 함수
	// (생성자가 아니다.)
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Client.afterPropertiesSet()");
	}
	
	@Override
	public void destroy() throws Exception {
		System.out.println("Client.destroy()");
	}
}
