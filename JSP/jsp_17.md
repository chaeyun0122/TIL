# 게시판 만들기
## 게시판 테이블 생성
  > sql developer에서 생성
  ```sql
  create table board (
      seq number not null,                -- 글 번호
      id varchar2(30) not null,           -- 아이디
      name varchar2(30) not null,         -- 이름
      subject varchar2(255) not null,     -- 제목
      content varchar2(4000) not null,    -- 내용
      hit number default 0,               -- 조회수
      logtime date default sysdate        -- 작성일
  );
  ```
* 시퀀스 객체 생성
  ```sql
  create sequence board_seq nocache nocycle;
  
  -- drop sequence borad_seq; 시퀀스 삭제
  ```
* board 테스트
  ```sql
  insert into board values (board_seq.nextval, 'testid', '미상', '게시판 테스트', '첫 번째 글', 0, sysdate);
  ```
  > ![image](https://user-images.githubusercontent.com/79209568/116228405-34ecde80-a790-11eb-9978-a97ad26ecb41.png)

## DTO 생성
```java
package boardDTO;

public class BoardDTO {
	
	private int seq;
	private String id;
	private String name;
	private String subject;
	private String content;
	private int hit;
	private String logtime;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
}

```
## DAO 생성
```java
package boardDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import boardDTO.BoardDTO;

public class BoardDAO {
	private String driver = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "c##dbtest";
	private String pwd = "a1234";

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet res;

	public BoardDAO() {
		try {
			Class.forName(driver);
			//System.out.println("로딩 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("로딩 실패");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			//System.out.println("연결성공");
		} catch (Exception e) {
			System.out.println("연결실패");
			e.printStackTrace();
		}
		return con;
	}
	
	// 글쓰기
	public int boardWrite(BoardDTO boardDTO) {
		String sql = "insert into board values (board_seq.nextval, ?, ?, ?, ?, 0, sysdate)";
		int num = 0;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardDTO.getId());
			pstmt.setString(2, boardDTO.getName());
			pstmt.setString(3, boardDTO.getSubject());
			pstmt.setString(4, boardDTO.getContent());
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
			return num;
		}
	} // boardWrite() end
}

```

## board 페이지들 생성
* board 폴더를 만들어서 jsp 파일을 만든다.
#### boardWriteForm.jsp
* 게시판에 글을 작성하는 페이지
```jsp
<%-- boardWriteForm.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 글쓰기 </title>
<script type="text/javascript" src="../script/boardScript.js"></script>
<style>
textarea { resize:none }
</style>
</head>
<body>
	<form action="boardWrite.jsp" name="boardWriteForm" method="post">
		<table border="1">
			<tr>
				<td width="50" align="center"> 제 목 </td>
				<td><input type="text" name="subject" size="60" placeholder="제목을  작성하세요"></td>
			</tr>
			<tr>
				<td width="50" align="center"> 내 용 </td>
				<td><textarea name="content" rows="20" cols="62" placeholder="내용을  작성하세요"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="글쓰기" onclick="checkBoardWrite()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
```
#### boardWrite.jsp
* 게시판에 글 작성 후 DB에 처리하는 페이지 
```jsp
<%-- boardWrite.jsp --%>
<%@page import="boardDTO.BoardDTO"%>
<%@page import="boardDAO.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String subject = request.getParameter("subject");
String content = request.getParameter("content");

// session으로 데이터 처리
String name = (String)session.getAttribute("memberName");
String id = (String)session.getAttribute("memberId");

BoardDTO boardDTO = new BoardDTO();
boardDTO.setName(name);
boardDTO.setId(id);
boardDTO.setSubject(subject);
boardDTO.setContent(content);

BoardDAO boardDAO = new BoardDAO();
int num = boardDAO.boardWrite(boardDTO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 글쓰기 확인 </title>
<style type="text/css">
p { font-size:20px }
</style>
</head>
<body>
	<%if (num > 0) {%>
		<p> 작성하신 글이 저장되었습니다. </p>
	<%} else { %>
		<p> 저장에 실패했습니다. </p>
	<%} %>
	<input type="button" value="main" onclick="location.href='../main/index.jsp'">
</body>
</html>
```

#### index.jsp
* 로그인 되어 있을 때 글쓰기 페이지로 넘어가도록 코드 추가
* `<a href="../board/boardWriteForm.jsp"> 글쓰기 </a>`
```jsp
<%-- main/index.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// session으로 전달된 데이터 받기
String id = null;
id = (String)session.getAttribute("memberId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> main </title>
<style>
a { 
	display: block;
	font-size: 20px; 
	padding: 2px;
}
</style>
</head>
<body>
	<h1> Main </h1>
	<br>
	<%if (id == null) {%>
		<a href="../member/writeForm.jsp"> 회원가입 </a>
		<a href="../member/loginForm.jsp"> 로그인 </a>
	<%} else { %>
		<a href="../board/boardWriteForm.jsp"> 글쓰기 </a>
		<a href="../member/logout.jsp"> 로그아웃 </a>
	<%} %>
</body>
</html>
```

> #### 결과
> * **로그인** 클릭
>   
> ![image](https://user-images.githubusercontent.com/79209568/116235597-06bfcc80-a799-11eb-89f7-bbf5f9f8aeb2.png)
>   
> * 아이디, 비밀번호 작성 후 로그인 클릭
>   
> ![image](https://user-images.githubusercontent.com/79209568/116235610-0c1d1700-a799-11eb-86d8-a1de26b6e23f.png)
>   
> * 로그인 성공 후 main화면으로 돌아가기
>   
> ![image](https://user-images.githubusercontent.com/79209568/116235625-0f180780-a799-11eb-916d-0e60ca1ae82f.png)
>   
> * **글쓰기** 메뉴 생김. 클릭
>   
> ![image](https://user-images.githubusercontent.com/79209568/116235638-12ab8e80-a799-11eb-9fe4-5914df64ee23.png)
>   
> * 제목과 내용 작성 후 글쓰기 버튼 클릭
>   
> ![image](https://user-images.githubusercontent.com/79209568/116235646-150de880-a799-11eb-94c9-19f3f5e45ed0.png)
>   
> * 저장 완료 된 것을 확인
>   
> ![image](https://user-images.githubusercontent.com/79209568/116235658-18a16f80-a799-11eb-93eb-e606c48dc519.png)
>   
> * sql developer에서 저장된 것을 확인
>   
> ![image](https://user-images.githubusercontent.com/79209568/116235704-2656f500-a799-11eb-881f-fe027b8352d5.png)

