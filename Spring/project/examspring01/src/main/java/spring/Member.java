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
