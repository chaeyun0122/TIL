/*
 * DB 역할
 */

package spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {
	private static long nextId = 0;
	
	private Map<String, Member> db = new HashMap<>();	// DB 역할
	// <email, Member>
	
	// 삽입(C)
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
	
	// 전체 목록
	public Collection<Member> selectAll() {
		return db.values();
	}
}
