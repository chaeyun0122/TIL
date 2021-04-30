# 게시판 만들기 2 (이어서)
* 현재 글쓰기 기능을 구현했고 커낵션 풀로 DB 연결을 해놓은 상태다.

## 글 목록 페이지
#### src/BoardDTO.java
* 글 목록 리스트를 리턴하는 `boardList` 메서드 추가
  ```java
  public ArrayList<BoardDTO> boardList(int start, int end) {
		String sql = "select seq, id, name, subject, content, hit, to_char(logtime, 'YYYY.MM.DD') as logtime from"
				+ "(select rownum rn, tt.* from"
				+ "(select * from board order by seq desc) tt)"
				+ "where rn>=? and rn<=?";
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		BoardDTO boardDTO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			res = pstmt.executeQuery();
			
			// 리스트에 담아준다
			while (res.next()) {
				boardDTO = new BoardDTO();
				boardDTO.setSeq(res.getInt("seq"));
				boardDTO.setId(res.getString("id"));
				boardDTO.setName(res.getString("name"));
				boardDTO.setSubject(res.getString("subject"));
				boardDTO.setContent(res.getString("content"));
				boardDTO.setHit(res.getInt("hit"));
				boardDTO.setLogtime(res.getString("logtime"));
				
				list.add(boardDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) res.close();
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} return list;
	}
  ```
* SQL문
  ```sql
  SELECT seq, id, name, subject, content, hit, to_char(logtime, 'YYYY.MM.DD') AS logtime
  FROM    (SELECT rownum rn, tt.*
           FROM (select * from board order by seq desc) tt)
  WHERE rn>=? and rn<=?
  ```
  * **seq로 내림차순한 board 테이블의 사본 tt를 rownum을 이용해 번호를 붙인 후, rn이 startNum과 endNum사이의 값인 seq, id, name, subject, content, hit, to_char(logtime, 'YYYY.MM.DD') AS logtime 열의 값을 출력하라는 의미의 구문이다.**
  
#### board/boardList.jsp
* 글 목록을 출력하는 페이지
```jsp
<%
//한 페이지 글 다섯 개
//pg = 1, 1~5
//pg = 2. 6~10
int pg = Integer.parseInt(request.getParameter("pg"));
int article = 2;		// 페이지 당 글수
int currentPage = pg;	// 현재 페이지
int startNum = (currentPage - 1) * article + 1;	// 해당 페이지 시작 번호
int endNum = startNum + article - 1;	// 해당 페이지 마지막 번호

BoardDAO boardDAO = new BoardDAO();
ArrayList<BoardDTO> list = boardDAO.boardList(startNum, endNum);
%>
```
```html
<body>
	<!-- 글 목록 -->
	<table border="1" width="800" cellpadding="5">
		<tr>
			<td width="50" align="center"> 글 번호 </td>
			<td width="300" align="center"> 제 목 </td>
			<td width="100" align="center"> 작성자 </td>
			<td width="100" align="center"> 작성일 </td>
			<td width="50" align="center"> 조회수 </td>
		</tr>
	<%for (BoardDTO boardDTO : list) { %>
		<tr>
			<td align="center"><%=boardDTO.getSeq() %></td>
			<td align="center"><%=boardDTO.getSubject() %></td>
			<td align="center"><%=boardDTO.getName() %></td>
			<td align="center"><%=boardDTO.getLogtime() %></td>
			<td align="center"><%=boardDTO.getHit() %></td>
		</tr>
	<%} %>
		
	</table>
</body>
```

#### main/index.jsp
* 글 목록 페이지로 이동하는 a 태그 추가
  ```html
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
  	<a href="../board/boardList.jsp?pg=1"> 글 목록 </a>
  </body>
  ```

> #### 결과		
> ![image](https://user-images.githubusercontent.com/79209568/116396051-d98a2180-a85f-11eb-8288-5e2206ad2121.png)

## 페이지 번호 출력
#### src/BoardDAO.java
* 전체 글 수를 리턴하는 `getTotalArticle` 메서드 추가
```java
public int getTotalArticle() {
String sql = "select count(*) from board";
int total = 0;

try {
	con = ds.getConnection();
	pstmt = con.prepareStatement(sql);
	res = pstmt.executeQuery();

	if (res.next()) {
		total = res.getInt(1);
	}
} catch (SQLException e) {
	e.printStackTrace();
} finally {
	try {
		if (res != null) res.close();
		if (pstmt != null) pstmt.close();
		if (con != null) con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}

} return total;
} //getTotalArticle() end
```

#### board/boardList.jsp
* 글 목록의 페이지 번호를 출력 하는 코드 추가
```jsp
<%
// 글목록 페이징 (블록 3개)
int totalArticle = boardDAO.getTotalArticle(); // 전체 글 수
int totalPage = (totalArticle - 1) / article + 1; // 전체 페이지 수 = (전체 글 수 - 1)/ 글 목록 수 + 1

int block = 3; // 페이지 블록
int startPage = (currentPage - 1) / block * block + 1;
int endPage = startPage + block - 1;

if (endPage > totalPage) endPage = totalPage;
%>
```
```html
<style type="text/css">
#paging_block {
	width: 800px;
	float: center;
	text-align: center;
}
#paging {
	color: black;
	text-decoration: none;
}
#currentPaging {
	color: red;
	text-decoration: underline;
}
</style>
```
```html
<p> 글 수 : <%=totalArticle %> </p>
<p> 페이지 수 : <%=totalPage %> </p>
<p> start : <%=startPage %> - end : <%=endPage %></p>
<!-- 페이지 번호 -->
<body>
	<div id="paging_block">
	<!-- '이전' 글자 표시 -->
	<%if (startPage > block) {%>
		[ <a href="boardList.jsp?pg=<%=startPage-1%>" id="paging"> 이전 </a> ]
	<%} %>

	<!-- 번호 표시 -->
	<%for (int i=startPage; i<=endPage; i++) {%>
		<!-- 현재 누른 페이지 표시 -->
		<%if (i == pg) {%>
			[ <a href="boardList.jsp?pg=<%=i%>" id="currentPaging"> <%=i %> </a> ]
		<%} else { %>
			[ <a href="boardList.jsp?pg=<%=i%>" id="Paging"> <%=i %> </a> ]
		<%} %>
	<%} %>

	<!-- '다음' 글자 표시 -->
	<%if (endPage < totalPage) {%>
		[ <a href="boardList.jsp?pg=<%=endPage+1%>" id="paging"> 다음 </a> ]
	<%} %>
	</div>
	<br><br>
	<input type="button" value="main" onclick="location.href='../main/index.jsp'">
</body>
```
> #### 결과
> ![image](https://user-images.githubusercontent.com/79209568/116399818-4bfd0080-a864-11eb-966b-566ecddb1fd8.png)  
>   
> ![image](https://user-images.githubusercontent.com/79209568/116399831-4e5f5a80-a864-11eb-9b84-32860526592c.png)  
>   
> ![image](https://user-images.githubusercontent.com/79209568/116399836-50c1b480-a864-11eb-80dd-55c16fd56a9b.png)

## 글 내용 보기
#### board/boardView.jsp
* 글 내용을 확인하는 페이지
```jsp
<%-- boardView.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="boardDAO.BoardDAO" %>
<%@ page import="boardDTO.BoardDTO" %>
<%
int seq = Integer.parseInt(request.getParameter("seq"));
int pg = Integer.parseInt(request.getParameter("pg"));

BoardDAO boardDAO = new BoardDAO();
boardDAO.updateHit(seq);	// 조회수 증가
BoardDTO boardDTO = boardDAO.boardView(seq);	//글에 대한 정보 가져오기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1" cellpadding="4">
		<tr>
			<td colspan="3"><%=boardDTO.getSubject() %> </td>
		</tr>
		<tr>
			<td width="150" align="center"> 글번호 : <%=boardDTO.getSeq() %></td>
			<td width="150" align="center"> 작성자 : <%=boardDTO.getName() %></td>
			<td width="150" align="center"> 조회수 : <%=boardDTO.getHit() %></td>
		</tr>
		<tr>
			<td colspan="3" height="200" valign="top"><pre><%=boardDTO.getContent() %></pre></td>
		</tr>
	</table>
	<br>
	<form action="#" name="boardView" method="get">
		<input type="button" value="글 목록" onclick="location.href='boardList.jsp?pg=<%=pg %>'">
	</form>
</body>
</html>
```
#### src/BoardDAO.jsp
* 글을 클릭하면 조회수가 오르도록하는 `updateHit` 메서드를 추가
```java
public int updateHit(int seq) {
	String sql = "update board set hit=hit+1 where seq=?";
	int num = 0;

	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, seq);
		num = pstmt.executeUpdate();

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (res != null) res.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	} return num;
}
```
* 글을 클릭하면 글에 대한 내용을 boardDTO 객체에 담아서 리턴하는 `boardView` 메서드 추가
```java
// 글 내용 가져오기
public BoardDTO boardView(int seq) {
	String sql = "select * from board where seq=?";
	BoardDTO boardDTO = new BoardDTO();

	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, seq);
		res = pstmt.executeQuery();

		// 리스트에 담아준다
		if (res.next()) {
			boardDTO = new BoardDTO();
			boardDTO.setSeq(res.getInt("seq"));
			boardDTO.setId(res.getString("id"));
			boardDTO.setName(res.getString("name"));
			boardDTO.setSubject(res.getString("subject"));
			boardDTO.setContent(res.getString("content"));
			boardDTO.setHit(res.getInt("hit"));
			boardDTO.setLogtime(res.getString("logtime"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (res != null) res.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	} return boardDTO;
}
```
#### board/boardList.jsp
* 글 목록의 제목을 클릭하면 onclick을 통해 자바스크립트의 isLogin 메서드를 실행 하도록 한다.
```html
<td align="center">
<a href="#" id="subjectArticle" onclick="isLogin(<%=boardDTO.getSeq()%>)">
<%=boardDTO.getSubject() %></a>
</td>
```
* 자바스크립트
  ```js
	<script type="text/javascript">
	function isLogin(seq) {
		<%if (session.getAttribute("memberId") == null) {%>
			alert("로그인이 필요한 작업입니다.")
		<%} else { %>
			location.href="boardView.jsp?seq=" + seq + "&pg=" + <%=pg %>;
		<%} %>
	}
	</script>
  ```
> #### 결과
> * 글을 클릭한다. (현재 조회수 0)  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116406164-57075f00-a86b-11eb-8cdd-9071dd65c59c.png)  
> * 글의 내용이 나온다. (조회수 +1) 글 목록 버튼을 클릭하면 글 목록으로 돌아간다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116406311-7b633b80-a86b-11eb-979e-b336185f5210.png)

## 글 수정 삭제
### 수정
#### board/boardView.jsp
* 현재 로그인 중인 사람이 글을 쓴 사람이어야 수정과 삭제가 가능하도록 한다.
```html
<%if (session.getAttribute("memberId").equals(boardDTO.getId())) {%>
	<input type="button" value="글 수정" onclick="location.href='boardModifyForm.jsp?pg=<%=pg %>&seq=<%=seq %>'">
	<input type="button" value="글 삭제" onclick="location.href='boardDelet.jsp?seq=<%=seq %>'">
<%} %>
```
#### board/boardModifyForm.jsp
* 글을 수정하는 form
* 제목과 내용을 DTO의 getter를 통해 가져와서 보여준다.
* 수정완료 버튼을 누르면 `checkBoardModify()` 메서드를 통해 빈 내용 없는지 체크 후 submit한다.
```jsp
<%-- boardModifyForm.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="boardDAO.BoardDAO" %>
<%@ page import="boardDTO.BoardDTO" %>
<%
int pg = Integer.parseInt(request.getParameter("pg"));
int seq = Integer.parseInt(request.getParameter("seq"));

BoardDAO boardDAO = new BoardDAO();
BoardDTO boardDTO = boardDAO.boardView(seq);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 글 수정 </title>
<script type="text/javascript">
function checkBoardModify() {
	if (document.boardModifyform.subject.value == "") {
		alert("제목을 입력하세요.");
		document.boardModifyform.subject.focus();	//해당 칸으로 포커스 이동
	}
	else if (document.boardModifyform.content.value == "") {
		alert("내용을 입력하세요.");
		document.boardModifyform.content.focus();	//해당 칸으로 포커스 이동
	}
	else {
		document.boardModifyform.submit();	// 다 들어갔으면 전송
	}
	
}
</script>
</head>
<body>
	<form action="boardModify.jsp" name="boardModifyform" method="post">
		<input type="hidden" name="seq" value="<%=seq %>">
		<input type="hidden" name="pg" value="<%=pg %>">
		<table border="1" cellpadding="4">
			<tr align="center">
				<td width="50"> 제 목 </td>
				<td>
					<input type="text" id="subject" name="subject" value="<%=boardDTO.getSubject() %>" style="width:98%">
				</td>
			</tr>
			<tr align="center">
				<td> 내 용 </td>
				<td>
					<textarea rows="20" cols="60" id="content" name="content" style="resize:none;">
						<%=boardDTO.getContent() %>
					</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="수정 완료" onclick="checkBoardModify()"> &nbsp;
					<input type="reset" value="다시 작성">
				</td>
			</tr>
		</table>
	</form>
	<br>
	<input type="button" value="main" onclick="location.href='../main/index.jsp'">
</body>
</html>
```

#### board/boardModify.jsp
* 수정된 내용을 DB에 업데이트 시키는 페이지
* DAO의 `boardModify` 함수를 통해 업데이트 된다.
```jsp
<%-- boardModify.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="boardDAO.BoardDAO" %>
<%@ page import="boardDTO.BoardDTO" %>
<%
request.setCharacterEncoding("utf-8");
int pg = Integer.parseInt(request.getParameter("pg"));
int seq = Integer.parseInt(request.getParameter("seq"));
String subject = request.getParameter("subject");
String content = request.getParameter("content");

BoardDTO boardDTO = new BoardDTO();
boardDTO.setSeq(seq);
boardDTO.setSubject(subject);
boardDTO.setContent(content);

BoardDAO boardDAO = new BoardDAO();
int num = boardDAO.boardModify(boardDTO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 글 수정 결과 </title>
<script type="text/javascript">
window.onload=function() {
	<%if (num > 0) {%>
		alert("수정 되었습니다.");
		location.href="boardList.jsp?pg=<%=pg %>";
	<%} else {%>
		alert("수정을 실패했습니다.")
		history.back(-1);
	<%} %>
}
</script>
</head>
<body>

</body>
</html>
```

#### src/BoardDAO.java
* 글을 수정하는 `boardModify` 메서드를 추가한다.
```java
public int boardModify(BoardDTO boardDTO) {
	String sql = "update board set subject=?, content=? where seq=?";
	int num = 0;

	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, boardDTO.getSubject());
		pstmt.setString(2, boardDTO.getContent());
		pstmt.setInt(3, boardDTO.getSeq());
		num = pstmt.executeUpdate();

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (res != null) res.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	} return num;
}
```
> #### 결과
> * 자신이 쓴 글만 글 수정 메뉴가 뜬다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116541672-b8d8cf00-a926-11eb-908f-6c3cc5857bb1.png)  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116541703-c4c49100-a926-11eb-83bc-b2b32a02fc9b.png)
> * 글 수정을 누르면 수정하는 페이지로 넘어가서 제목과 내용을 수정할 수 있다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116541794-de65d880-a926-11eb-903d-6238e883172a.png)  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116541862-f2a9d580-a926-11eb-9bb7-8c755f5c0e69.png)  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116541890-fa697a00-a926-11eb-9580-8d909ef7aea1.png)
  
### 삭제
#### board/boardDelete.jsp
* 글을 삭제하는 페이지
```jsp
<%-- boardDelete.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="boardDAO.BoardDAO" %>
<%
int seq = Integer.parseInt(request.getParameter("seq"));

BoardDAO boardDAO = new BoardDAO();
boardDAO.boardDelete(seq);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 글 삭제 확인 </title>
<script type="text/javascript">
window.onload=function() {
	alert("삭제를 완료했습니다.");
	location.href="boardList.jsp?pg=1";
}
</script>
</head>
<body>

</body>
</html>
```

#### src/BoardDAO.java
* 글을 삭제하는` boardDelete` 메서드를 추가한다.
```java
public int boardDelete(int seq) {
	String sql = "delete board where seq=?";
	int num = 0;

	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, seq);
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
	} return num;
}
```

> #### 결과
> * 글 작성자가 해당 글을 클릭한다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116552431-f859e800-a933-11eb-9fdc-7434e4d37435.png)
> * 글 삭제 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116552479-0576d700-a934-11eb-982e-5edf3b957af9.png)  
> * 글 삭제 완료  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116552494-0c054e80-a934-11eb-94da-2181a38e41dc.png)



## 회원정보 수정
#### main/index.jsp
* 로그인 했을 때 회원 정보를 수정하는 페이지로 이동하는 a 태그를 추가
```jsp
<body>
	<h1> Main </h1>
	<br>
	<%if (id == null) {%>
		<a href="../member/writeForm.jsp"> 회원가입 </a>
		<a href="../member/loginForm.jsp"> 로그인 </a>
	<%} else { %>
		<a href="../board/boardWriteForm.jsp"> 글쓰기 </a>
		<a href="../member/modifyForm.jsp?id" <%=session.getAttribute("memberId") %>> 회원정보 수정 </a>
		<a href="../member/logout.jsp"> 로그아웃 </a>
	<%} %>
	<a href="../board/boardList.jsp?pg=1"> 글 목록 </a>
</body>
```

#### member/modifyForm.jsp
* 회원 정보를 수정하는 폼이다.
* 아이디는 변경할 수 없다.
* 성별은 값을 불러와서 M일 경우와
* 회원 수정 완료를 클릭 시 `checkModify()` 메서드를 실행 후 오류가 없으면 submit하여 `modify.jsp`로 이동한다.
```jsp
<%-- modifyForm.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="memberDTO.MemberDTO" %>
<%@ page import="memberDAO.MemberDAO" %>
<%
request.setCharacterEncoding("utf-8");
String id = request.getParameter("id");
MemberDAO memberDAO = new MemberDAO();
MemberDTO memberDTO = memberDAO.getMember(id);

String tel = memberDTO.getTel();
String tel1 = tel.substring(0, 3);
String tel2 = tel.substring(4, 8);
String tel3 = tel.substring(9);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 회원 정보 수정 </title>
<script type="text/javascript">
function checkModify(){
	if(document.modifyForm.name.value==""){
		alert("이름을 입력하세요!");
		document.modifyForm.name.focus();
	}
	else if(document.modifyForm.pwd.value==""){
		alert("비밀번호를 입력하세요!");
		document.modifyForm.pwd.focus();
	}
	else if(document.modifyForm.pwd.value!=document.modifyForm.repwd.value){
		alert("비밀번호가 틀립니다!");
		document.modifyForm.repwd.focus();
	}
	else
		document.modifyForm.submit();
}
</script>
</head>
<body>
	<form action="modify.jsp" name="modifyForm" method="post">
		<table border="1" cellpadding="4">
			<tr>
				<td width="120" align="center"> 이 름 </td>
				<td width="300"><input type="text" name="name" value="<%=memberDTO.getName()%>"></td>
			</tr>
			<tr>
				<td align="center"> 아이디 </td>
				<td>
					<input type="text" name="id" value="<%=memberDTO.getId()%>" readonly>
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
				<td align="center"> 성 별 </td>
				<td>
				<%if (memberDTO.getGender().equals("M")) {%>
					<input type="radio" name="gender" value="M" checked><label for="genderM"> 남 </label>
					<input type="radio" name="gender" value="F"><label for="genderF"> 여 </label>
				<%} else { %>
					<input type="radio" name="gender" value="M"><label for="genderM"> 남 </label>
					<input type="radio" name="gender" value="F" checked><label for="genderF"> 여 </label>
				<%} %>
				</td>
			</tr>
			<tr>
				<td align="center"> E-mail </td>
				<td>
					<input type="text" name="email" value="<%=memberDTO.getEmail()%>" size="10"> @
					<input type="text" name="domain" value="<%=memberDTO.getDomain()%>" size="10">
				</td>
			</tr>
			<tr>
				<td align="center"> 핸드폰 </td>
				<td>
					<input type="text" name="tel1" value=<%=tel1 %> size="3" maxlength="3"> - 
					<input type="text" name="tel2" value=<%=tel2 %> size="3" maxlength="4"> - 
					<input type="text" name="tel3" value=<%=tel3 %> size="3" maxlength="4">
				</td>
			</tr>
			<tr>
				<td align="center"> 주 소 </td>
				<td><input type="text" name="addr" value="<%=memberDTO.getAddr()%>" size="50"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="회원 수정 완료" onclick="checkModify()"> &nbsp;
					<input type="reset" value="다시작성">
				</td>
			</tr>
		</table>
	</form>
	<br>
	<input type="button" value="main" onclick="location.href='../main/index.jsp'">
</body>
</html>
```

#### member/modify.jsp
* 수정한 회원 정보를 DB에 저장하는 페이지
```jsp
<%-- modify.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="memberDTO.MemberDTO" %>
<%@ page import="memberDAO.MemberDAO" %>
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
int res = memberDAO.modify(memberDTO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 회원 정보 수정 확인 </title>
</head>
<body>
	<br>
	<%if (res > 0) {%>
		<p> 회원 정보 수정을 성공했습니다.</p>
	<%} else { %>
		<p> 회원 정보 수정을 실패했습니다.</p>
	<%} %>
	<br>
	<input type="button" value="main" onclick="location.href='../main/index.jsp'">
</body>
</html>
```

#### src/MemberDAO.java
* 회원 정보를 가져오는 `getMember` 메서드를 추가한다.
```java
public MemberDTO getMember(String id) {
	String sql = "select * from member where id=?";
	MemberDTO memberDTO = null;

	try {

		con = getConnection();
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		res = pstmt.executeQuery();
		if (res.next()) {
			memberDTO = new MemberDTO();
			memberDTO.setName(res.getString("name"));
			memberDTO.setId(res.getString("id"));
			memberDTO.setPwd(res.getString("pwd"));
			memberDTO.setGender(res.getString("gender"));
			memberDTO.setTel(res.getString("tel"));
			memberDTO.setEmail(res.getString("email"));
			memberDTO.setDomain(res.getString("domain"));
			memberDTO.setAddr(res.getString("addr"));
		}

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (res != null)
				res.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return memberDTO;
}
```
* 회원 정보를 수정하는 `modify` 메서드를 추가한다.
```java
public int modify(MemberDTO memberDTO) {
	String sql = "update member "
			+ "set name=?, pwd=?, gender=?, email=?, domain=?, tel=?, addr=? "
			+ "where id=?";
	int num = 0;

	try {
		con = getConnection();
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, memberDTO.getName());
		pstmt.setString(2, memberDTO.getPwd());
		pstmt.setString(3, memberDTO.getGender());
		pstmt.setString(4, memberDTO.getEmail());
		pstmt.setString(5, memberDTO.getDomain());
		pstmt.setString(6, memberDTO.getTel());
		pstmt.setString(7, memberDTO.getAddr());
		pstmt.setString(8, memberDTO.getId());
		num = pstmt.executeUpdate();

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return num;
}
```
> #### 결과
> * 회원 정보 수정을 클릭한다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116552124-a7e28a80-a933-11eb-9e01-1934b94788d0.png)
> * 이름을 `테스터 > 테스터1`로 변경, 주소도 변경한다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116552193-baf55a80-a933-11eb-8785-5893c99cdba1.png)
> * 수정 성공  
>   
> ![image](https://user-images.githubusercontent.com/79209568/116552233-c6488600-a933-11eb-9678-156ec14fb2ee.png)
