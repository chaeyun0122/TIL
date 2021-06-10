package spring;

import java.util.Date;

public class MemberRegisterService {
	private MemberDao memberDao;
	// ↓ 의존 주입
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
