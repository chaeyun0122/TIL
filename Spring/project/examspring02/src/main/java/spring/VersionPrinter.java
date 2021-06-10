package spring;

public class VersionPrinter {
	private int majorVersion;
	private int minorVersion;
	
	public void setMajorVersion(int mv) {
		this.majorVersion = mv;
	}
	
	public void setMinorVersion(int mv) {
		this.minorVersion = mv;
	}
	
	public void print() {
		System.out.printf("프로그램 버전:%d.%d\n", majorVersion, minorVersion);
	}
}
