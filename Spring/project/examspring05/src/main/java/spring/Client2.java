package spring;

/*
 * 직접 인터페이스를 구현하지 못하는 클래스를 스프링 빈으로 사용하고자 한다면
 * 클래스에 정의된 특정 메서드를 초기화 메서드로 지정
 * 클래스에 정의된 특정 메서드를 종료 메서드로 지정
 */
public class Client2 {

	private String host;
	
	public void setHost(String host) {
		this.host = host;
		System.out.println("Client2.setHost(String)");
	}
	
	public void send() {
		System.out.println("Client2.send() to " + host);
	}
	
	public Client2() {
		System.out.println("Client2()");
	}
	
	public void startMethod() throws Exception {
		System.out.println("Client2.afterPropertiesSet()");
	}
	
	public void endMethod() throws Exception {
		System.out.println("Client2.destroy()");
	}
}
