package spring;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoPrinter {
	
	@Resource
	private MemberDao memberDao;
	private MemberPrinter printer;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Resource(name="printer1") // <bean id="printer" 를 찾는다.
	// @Resource // name 속성을 지정하지 않으면 같은 타입의 객체를 찾음
	// @Qualifier(value = "sysout1") // 현재 객체가 두 개라 오류나므로 Qualifier로 찾도록한다.
	public void setMemberPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
	
	public void printMemberInfo(String email) {
		Member member = memberDao.selectByEmail(email);
		if(member == null) {
			System.out.println("정보가 존재하지 않습니다");
			return;
		}
		printer.print(member);
		System.out.println();
	}
}
