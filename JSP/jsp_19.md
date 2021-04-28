# 게시판 만들기 2 (이어서)
* 현재 글쓰기 기능을 구현했고 커낵션 풀로 DB 연결을 해놓은 상태다.

## 글 목록 페이지
#### BoardDTO.java
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
  
#### boardList.jsp
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

#### index.jsp
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

		
![image](https://user-images.githubusercontent.com/79209568/116396051-d98a2180-a85f-11eb-8288-5e2206ad2121.png)

## 페이지 번호 출력
#### BoardDAO.java
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

#### boardList.jsp
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
![image](https://user-images.githubusercontent.com/79209568/116399818-4bfd0080-a864-11eb-966b-566ecddb1fd8.png)
![image](https://user-images.githubusercontent.com/79209568/116399831-4e5f5a80-a864-11eb-9b84-32860526592c.png)
![image](https://user-images.githubusercontent.com/79209568/116399836-50c1b480-a864-11eb-80dd-55c16fd56a9b.png)

## 글 내용 보기
#### boardView.jsp
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
#### BoardDAO.jsp
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
#### boardList.jsp
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
* 글을 클릭한다. (현재 조회수 0)
  ![image](https://user-images.githubusercontent.com/79209568/116406164-57075f00-a86b-11eb-8cdd-9071dd65c59c.png)
* 글의 내용이 나온다. (조회수 +1) 글 목록 버튼을 클릭하면 글 목록으로 돌아간다.
  ![image](https://user-images.githubusercontent.com/79209568/116406311-7b633b80-a86b-11eb-979e-b336185f5210.png)

## 글 수정 삭제
#### boardView.jsp
* 현재 로그인 중인 사람이 글을 쓴 사람이어야 수정과 삭제가 가능하도록 한다.
  ```html
<%if (session.getAttribute("memberId").equals(boardDTO.getId())) {%>
		<input type="button" value="글 수정" onclick="location.href='boardModifyForm.jsp?pg=<%=pg %>&seq=<%=seq %>'">
		<input type="button" value="글 삭제" onclick="location.href='boardDelet.jsp?seq=<%=seq %>'">
<%} %>
  ```
