package spring03;

public class Greeter {
	private String format;
	
	public Greeter() {
		System.out.println("Greeter생성");
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String greet(String guest) {
		return String.format(format, guest);
	}
}
