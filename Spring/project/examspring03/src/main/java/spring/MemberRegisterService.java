package spring;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberRegisterService {
	
	@Resource(name = "memberDao") //<bean id = "memberDao" 를 찾는다.
	private MemberDao memberDao;
	
	//@Resource는 생성자에는 적용 불가
	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public MemberRegisterService() {}
	
	public void regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new AlreadyExistingMemberException("dup email:" + req.getEmail());
		}
		// 새로운 멤버 정보 생성
		Member newMember = new Member(
				req.getEmail(),
				req.getPassword(),
				req.getName(),
				new Date());

		// 새로운 멤버 등록
		memberDao.insert(newMember);
	}
}
