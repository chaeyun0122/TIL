package spring;

public class MainForAssembler {
	public static void main(String[] args) {
		Assembler assm = new Assembler();
		
		MemberRegisterService regSvc = assm.getMemberRegisterService();
		RegisterRequest req = new RegisterRequest();
		req.setEmail("aaa@aaa.com");
		req.setName("홍길동");
		req.setPassword("1234");
		req.setConfirmPassword("1234");
		regSvc.regist(req);
	}
}
