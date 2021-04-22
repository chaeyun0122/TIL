# 회원 가입 페이지
## 회원 데이터베이스 테이블 생성
* sqlDeveloper에서 DB에 member테이블을 생성한다.  
  
  ```sql
   create table member(
  	 name varchar2(30) not null,
  	 id varchar2(30) primary key,
  	 pwd varchar2(30) not null,
  	 gender varchar2(3),
  	 email varchar2(20),
  	 domain varchar2(20),
  	 tel varchar2(13),
  	 addr varchar2(100),
  	 logtime date
   );
  ```

## 회원 가입 페이지 생성
### wirteForm JSP 페이지
* 프로젝트의 WebContent에 `member폴더 > writeFrom.jsp` 파일 생성
* form의 틀을 만든다.
  
  ```html
  <form action="write.jsp" name="writeForm" method="post">
		<table border="1" cellpadding="4">
			<tr>
				<td width="120" align="center"> 이 름 </td>
				<td width="300"><input type="text" name="name"></td>
			</tr>
			<tr>
				<td align="center"> 아이디 </td>
				<td>
					<input type="text" name="id">
					<input type="button" value="중복체크" onclick="checkId()">
				</td>
			</tr>
			<tr>
				<td align="center"> 비밀번호 </td>
				<td><input type="password" name="pwd"></td>
			</tr>
			<tr>
				<td align="center"> 비밀번호 확인 </td>
				<td><input type="password" name="repwd"></td>
			</tr>
			<tr>
				<td align="center"> 성 별</td>
				<td>
					<input type="radio" name="gender" id="genderM" value="M" checked>
					<label for="genderM"> 남 </label>
					<input type="radio" name="gender" id="genderF" value="F">
					<label for="genderF"> 여 </label>
				</td>
			</tr>
			<tr>
				<td align="center"> E-mail </td>
				<td>
					<input type="text" name="email" size="10"> @
					<select name="domain">
						<option value="naver.com"> naver </option>
						<option value="gmail.com"> gmail </option>
						<option value="daum.net"> daum </option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="center"> 핸드폰 </td>
				<td>
					<input type="text" name="tel1" size="3" maxlength="3"> -
					<input type="text" name="tel2" size="3" maxlength="4"> -
					<input type="text" name="tel3" size="3" maxlength="5">
				</td>
			</tr>
			<tr>
				<td align="center"> 주 소 </td>
				<td><input type="text" name="addr" size="50"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="회원가입" onclick="checkWrite()"> &nbsp;
					<input type="reset" value="다시작성">
				</td>
			</tr>
		</table>
	</form>
  ```
  > ![image](https://user-images.githubusercontent.com/79209568/115703688-64bd7000-a3a5-11eb-961d-2d738b155272.png)

### memberScript JS 파일
* 프로젝트의 WebContent에 `script폴더 > memberScript.js` 파일 생성
* wirteForm.jsp 페이지에서 버튼 onclick 함수들을 정의해준다.
```java script
function checkWrite(){
	if(document.writeForm.name.value==""){
		alert("이름을 입력하세요!");
		document.writeForm.name.focus();
	}
	else if(document.writeForm.id.value==""){
		alert("아이디를 입력하세요!");
		document.writeForm.id.focus();
	}
	else if(document.writeForm.pwd.value==""){
		alert("비밀번호를 입력하세요!");
		document.writeForm.tel1.focus();
	}
	else if(document.writeForm.pwd.value!=document.writeForm.repwd.value){
		alert("비밀번호가 틀립니다!");
		document.writeForm.repwd.focus();
	}
	else if(document.writeForm.email.value==""){
		alert("이메일을 입력하세요!");
		document.writeForm.email.focus();
	}
	else if(document.writeForm.tel1.value==""){
		alert("전화번호를 입력하세요!");
		document.writeForm.tel1.focus();
	}
	else if(document.writeForm.tel2.value==""){
		alert("전화번호를 입력하세요!");
		document.writeForm.tel2.focus();
	}
	else if(document.writeForm.tel3.value==""){
		alert("전화번호를 입력하세요!");
		document.writeForm.tel3.focus();
	}
	else if(document.writeForm.addr.value==""){
		alert("주소를 입력하세요!");
		document.writeForm.addr.focus();
	}
	else
		document.writeForm.submit();
}

function checkId(){
	var sId = document.writeForm.id.value;
	
	if(sId == ""){
		alert("아이디를 입력하세요!");
	} else{
		window.open("./checkId.jsp?id=" + sId , "", "width=350 height=100 left=500 top=200");
	}
}
```
### DTO 생성
```java
package memberDTO;

public class MemberDTO {
	private String name;
	private String id;
	private String pwd;
	private String gender;
	private String email;
	private String domain;
	private String tel;
	private String addr;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
}

```

### DAO 생성
```java
package memberDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import memberDTO.MemberDTO;

public class MemberDAO {
	private String driver = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "c##dbtest";
	private String pwd = "a1234";
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet res;
	
	public MemberDAO() {
		try {
			Class.forName(driver);
			System.out.println("로딩 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("로딩 실패");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("연결성공");
		} catch (Exception e) {
			System.out.println("연결실패");
			e.printStackTrace();
		}
		return con;
	}
	
	// 회원가입 진행하는 메서드
	public int write(MemberDTO memberDTO) {
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
		int num = 0;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getName());
			pstmt.setString(2, memberDTO.getId());
			pstmt.setString(3, memberDTO.getPwd());
			pstmt.setString(4, memberDTO.getGender());
			pstmt.setString(5, memberDTO.getEmail());
			pstmt.setString(6, memberDTO.getDomain());
			pstmt.setString(7, memberDTO.getTel());
			pstmt.setString(8, memberDTO.getAddr());
			num = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return num;
	}
}
```

### write JSP 페이지
* memberDTO와 memberDAO를 사용하기 위해 import 해준다.
  ```jsp
  <%@ page import="memberDTO.MemberDTO" %>
  <%@ page import="memberDAO.MemberDAO" %>
  ```
* writeForm.jsp 페이지로부터 넘어온 데이터를 각 변수에 저장한다.
  ```jsp
  request.setCharacterEncoding("utf-8");
  String name = request.getParameter("name");
  String id = request.getParameter("id");
  String pwd = request.getParameter("pwd");
  String gender = request.getParameter("gender");
  String email = request.getParameter("email");
  String domain = request.getParameter("domain");
  String tel = (request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
  String addr = request.getParameter("addr");
  ```
* DTO의 setter을 통해 값을 채우고 DAO를 통해 DB와 연결 및 레코드 추가를 한다.
  ```jsp
  MemberDTO memberDTO = new MemberDTO();
  memberDTO.setName(name);
  memberDTO.setId(id);
  memberDTO.setPwd(pwd);
  memberDTO.setGender(gender);
  memberDTO.setEmail(email);
  memberDTO.setDomain(domain);
  memberDTO.setTel(tel);
  memberDTO.setAddr(addr);
  
  MemberDAO memberDAO = new MemberDAO();
  int res = memberDAO.write(memberDTO);
  ```
* html \<body>에 성공 실패여부를 확인 하는 코드를 작성한다.
  ```jsp
  <%if (res != 0) {%>
  	회원가입 성공!
  <%} else { %>
  	회원가입 실패
  <%} %>
  ```
* **write.jsp 전체 코드**
  ```jsp
  <%-- member/write.jsp --%>
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
  <%@ page import="memberDTO.MemberDTO" %> <%-- DTO를 사용하기 위해 import --%>
  <%@ page import="memberDAO.MemberDAO" %> <%-- DAO를 사용하기 위해 import --%>
  <%
  request.setCharacterEncoding("utf-8");
  String name = request.getParameter("name");
  String id = request.getParameter("id");
  String pwd = request.getParameter("pwd");
  String gender = request.getParameter("gender");
  String email = request.getParameter("email");
  String domain = request.getParameter("domain");
  String tel = (request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
  String addr = request.getParameter("addr");
  
  MemberDTO memberDTO = new MemberDTO();
  memberDTO.setName(name);
  memberDTO.setId(id);
  memberDTO.setPwd(pwd);
  memberDTO.setGender(gender);
  memberDTO.setEmail(email);
  memberDTO.setDomain(domain);
  memberDTO.setTel(tel);
  memberDTO.setAddr(addr);
  
  MemberDAO memberDAO = new MemberDAO();
  int res = memberDAO.write(memberDTO);
  %>

  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title> 회원가입 확인 </title>
  </head>
  <body>
  	<%if (res != 0) {%>
  		회원가입 성공!
  	<%} else { %>
  		회원가입 실패
  	<%} %>
  </body>
  </html>
  ```

# 회원가입 실행 해보기
* 값을 입력하고 `회원가입` 클릭  
  
  ![image](https://user-images.githubusercontent.com/79209568/115712259-81f73c00-a3af-11eb-98e7-7d6b116d557d.png)
* `회원가입 성공!` 확인  
  
  ![image](https://user-images.githubusercontent.com/79209568/115712303-92a7b200-a3af-11eb-9788-28b476268226.png)
* DB에도 잘 추가되었는지 확인
  ```sql
  select * from member;
  ```
   
  ![image](https://user-images.githubusercontent.com/79209568/115712407-b0751700-a3af-11eb-94f8-753a2a8e52bc.png)
