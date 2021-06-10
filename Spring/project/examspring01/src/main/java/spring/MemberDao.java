package spring;

import java.util.HashMap;
import java.util.Map;

public class MemberDao {
	private static long nextId = 0;
	
	private Map<String, Member> db = new HashMap<>();	// DB 역할
	// <email, Member>
	
	// 삽입(c)
	public void insert(Member member) {
		member.setId(++nextId);
		db.put(member.getEmail(), member);
	}
	
	// 조회(R)
	public Member selectByEmail(String email) {
		return db.get(email);
	}
	
	// 수정(U)
	public void update(Member member) {
		db.put(member.getEmail(), member);
	}
}
