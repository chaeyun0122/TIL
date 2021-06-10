## 실습1 - Singleton
- spring01 패키지 복사해서 spring02로 이름 변경
- resource에 applicationContext복사해서 applicationContext2로 변경
- applicationContext2의 bean의 클래스를 spring02로 바꿈
- spring02의 main에 가서 applicationContext를 applicationContext2로 바꿈
- Greeter greeter1, Greeter greeter2으로 만든다. 객체를 두개 생성한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/121506125-6a871780-ca1e-11eb-99d4-8ae28f743089.png)

- system out으로 greeter1 == greeter2  둘의 동일성을 확인한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/121506210-7a066080-ca1e-11eb-9bd5-7eae6bbd4450.png)
- Greeter 클래스에서 생성자 함수를 만든다. 객체가 생성되면 `"Greeter생성"`이라는 문장을 출력한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/121506718-f00ac780-ca1e-11eb-8d7f-cd6883b01c57.png)

- 결과
  - `Greeter생성` : 하나만 출력되었으므로 객체는 하나만 생성되었다.
  - `true` : greeter1과 greeter2는 같은 객체다.  

  ![image](https://user-images.githubusercontent.com/79209568/121506431-a6ba7800-ca1e-11eb-9c31-86fa15c2e747.png)

  - greeter라는 아이디는 고유한 식별값이기 때문에 getBean을 하면 참조값만을 반환하는 것이다. (싱글톤:단일객체를 만든다)


## 실습2 - 다른 객체 생성
- spring03, applicationContext3
- beean선언에 `scope="prototype"` 설정을 넣어주면 getBean할 때마다 객체가 새로 생성된다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/121507776-ea61b180-ca1f-11eb-863f-542f4aebd6ea.png)

- 결과  
  - `Greeter생성` : 두 개가 출력되었으므로 객체는 두 개 생성되었다.
  - `false` : greeter1과 greeter2는 다른 객체다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/121507706-da49d200-ca1f-11eb-9b5b-739b478ef448.png)

> 👉 [**spring01** project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/spring01) 
<hr>

# 의존
> 의존이란? 변경에 의해 영향을 받는 관계

## 의존 객체 만드는 방법
### 1. 직접 생성
- 의존을 필요로하는 클래스에서 의존대상을 **직접 생성**하는 방식
- 의존을 필요로하는 클래스를 생성하면 의존 객체도 자동으로 생성된다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121509031-32350880-ca21-11eb-8e07-5c630ca04ad1.png)

### DI를 통하는 방법
- 의존을 필요로하는 클래스에 의존 대상을 **주입**해주는 방식
- 의존 객체 변경이 유연하다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121509079-3e20ca80-ca21-11eb-9a75-e32e6b90e277.png)

## Assembler(조립기)를 통해 의존 객체 주입
> ### 회원가입 시스템
> - 프로젝트명 : examspring01 (👉[project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/examspring01))
> - 패키지명 : spring

#### Member.java
- 회원 클래스
- id, email, password, name, registerDate
- changePassword : 비밀번호 변경 매서드
  - `IdPasswordNotMatchingException()` : 현재 비밀번호가 틀릴 경우 발생하는 exception
```java
package spring;

import java.util.Date;

public class Member {
	private Long id;
	private String email;
	private String password;
	private String name;
	private Date registerDate;
	
	public Member(String email, String password, String name, Date registerDate) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.registerDate = registerDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		if (!this.password.contentEquals(oldPassword)) {
			throw new IdPasswordNotMatchingException();
		}
		this.password = newPassword;
	}
}
```
#### RegisterRequest.java
- 회원 등록 클래스
- email, password, confirmPassword, name
- `isPasswordEqualToConfirmPassword()` : 비밀번호와 비밀번호확인이 같은지 확인하는 매서드
```java
package spring;

public class RegisterRequest {
	private String email;
	private String password;
	private String confirmPassword;
	private String name;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isPasswordEqualToConfirmPassword() {
		return this.password.equals(confirmPassword);
	}
}
```

#### MemberDao.java
- 회원 DB 역할 클래스
- `db` : Map에 회원 정보를 저장
- `insert`, `selectByEmail`, `update` 매서드로 삽입, 조회, 수정
```java
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
```

#### MemberRegisterService.java
- 회원 등록 동작 클래스
- `private MemberDao memberDao;` : MemberDao 의존 객체 직접 생성
- `regist` : 회원을 등록시키는 매서드
  - `AlreadyExistingMemberException` : 이미 존재하는 회원의 이메일이면 발생하는 exception
    ![image](https://user-images.githubusercontent.com/79209568/121512717-fbf98800-ca24-11eb-868d-4037ba3489e4.png)
```java
package spring;

import java.util.Date;

public class MemberRegisterService {
	private MemberDao memberDao;
	
	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new AlreadyExistingMemberException("dup email:" + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(),
				req.getPassword(),
				req.getName(),
				new Date());
		memberDao.insert(newMember);
	}
}
```

#### ChangePasswordService.java
- 비밀번호 변경 클래스
- `changePassword` : email, oldPwd, newPwd를 인자로 받아서 비밀번호를 변경하는 매서드
  -  `MemberNotFoundException` : 입력받은 이메일이 존재하지 않다면 발생하는 exception
```java
package spring;

public class ChangePasswordService {
	private MemberDao memberDao;
	
	public ChangePasswordService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null) {
			throw new MemberNotFoundException();
		}
		member.changePassword(oldPwd, newPwd);
		memberDao.update(member);
	}
}
```

#### Assembler.java
- 조립기 클래스
- MemberDao, MemberRegisterService, ChangePasswordService 클래스들을 조립해준다.
```java
package spring;

public class Assembler {
	private MemberDao memberDao;
	private MemberRegisterService regSvc;
	private ChangePasswordService pwdSvc;
	
	public Assembler() {
		memberDao = new MemberDao();
		regSvc = new MemberRegisterService(memberDao);
		pwdSvc = new ChangePasswordService(memberDao);
	}
	
	public MemberDao getMemberDao() {
		return memberDao;
	}
	
	public MemberRegisterService getMemberRegisterService() {
		return regSvc;
	}

	public ChangePasswordService getChangePasswordService() {
		return pwdSvc;
	}
}
```

#### MainForAssembler.java
- Main 클래스
- 등록할 회원의 정보를 RegisterRequest의 req 객체에 담아서 regist를 진행한다.
```java
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
```
