# 세션 (Session)
* 웹 서버 쪽의 웹 컨테이너에 상태를 유지하기 위한 정보를 저장한다.
  * 웹 브라우저당 1개씩 생성되어 웹 컨테이너에 저장한다.
* 웹 브라우저와 웹 서버의 상태 유지가 안정적이고 보안상의 문제도 해결된다.
* 웹 서버는 각각의 웹 브라우저로부터 발생한 요청에 대해서 특별한 식별자를 부여한다. 이 식별자들을 사용해서 session을 구분해서 유지한다.
* 내장 객체이므로 객체를 따로 만들지 않아도 된다.

## 세션 사용
### 1. session 속성 결정
> session의 `setAttribute()` 메서드를 사용
### 2. 설정된 session의 속성을 사용해서 정보를 유지
> session 객체의 `getAttribute()` 메서드를 사용
### 3. session 속성 삭제
> session 객체의 `removeAttribute()` 메서드를 사용
### 4. session 무효화
> session 객체의 `invalidate()` 메서드를 사용
 
## 영역과 속성
### 영역 (scope)
* 데이터의 공유 범위
* 내장 객체를 뜻하고, 내장 객체의 유지 범위에 따라 달라진다.

#### 1. page 영역 - pageContext
* 해당 페이지에서만 사용되는 객체
#### 2. request 영역
* 1:1 페이지 영역
* 클라이언트의 요청이 처리되는 동안 유효한 범위
#### 3. session 영역
* session이 유지되는 동안 처리되는 범위. 웹 브라우저가 실행되는 동안 유지된다.
#### 4. application 영역
* 웹 서버가 유지되는 동안 유효한 범위. 웹 서버당 하나만 생상된다.

## 세션 실습
#### sessionForm.jsp
```jsp
<%-- sessionForm.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> session form </title>
</head>
<body>
	<form action="sessionPro.jsp" method="post">
		<label> ID : </label><input type="text" name="id"><br>
		<label> PW : </label><input type="password" name="pwd">
		<br><br>
		<input type="submit" value="세션확인">
	</form>
</body>
</html>
```

#### sessionPro.jsp
```jsp
<%-- sessionPro.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String userid = request.getParameter("id");
String userpw = request.getParameter("pwd");

session.setAttribute("id", userid);
session.setAttribute("pwd", userpw);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>session 확인</title>
<style type="text/css">
p { font-size:20px }
</style>
</head>
<body>
	<h2> session 속성 설정 및 사용 </h2>
	<br>
	<p> ID, PW session 속성을 설정했습니다. </p>
	<p> userid 속성 값 : <%=(String)session.getAttribute("id") %></p>
	<p> userpw 속성 값 : <%=(String)session.getAttribute("pwd") %></p>
</body>
</html>
```

> #### 결과
> * ID, PW 입력 후 세션 확인 클릭
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116084039-512d4480-a6d8-11eb-9de9-103dc08b7de3.png)
> * 세션 값 확인
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116084129-6904c880-a6d8-11eb-839a-97c426bec8c1.png)

## 로그인 : 세션 생성
* 이 전에 실습했던 member 프로젝트의 로그인을 쿠키를 통해서 이루어지도록 변경해본다.

#### login.jsp
* `session.setAttribute()`로 세션의 속성을 설정한다.  
  
```jsp
<%-- member/login.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="memberDAO.MemberDAO"%>
<%
String id = request.getParameter("id");
String pwd = request.getParameter("pwd");

MemberDAO memberDAO = new MemberDAO();
String name = memberDAO.login(id, pwd);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 로그인 확인 </title>
<style>
p { font-size: 20px; }
</style>
</head>
<body>
	<%
	// sesssion 속성 설정
	if(name != null) {
		session.setAttribute("memberName", name);
		session.setAttribute("memberId", id);
		
		response.sendRedirect("loginOk.jsp");
	}
	else
		response.sendRedirect("loginFail.jsp");
	%>
	
</body>
</html>
```

#### loginOk.jsp
* session으로 받은 데이터로 로그인
```jsp
<%-- loginOk.jsp --%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// session으로 데이터 받기
String name = (String)session.getAttribute("memberName");
String id = (String)session.getAttribute("memberId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 로그인 성공 </title>
<style>
p { font-size: 20px; }
</style>
</head>
<body> 
	<img src="../image/home.jpg" width="300" height="200" 
			onclick="location.href='../main/index.jsp'" style="cursor:pointer;">
	<br><br>
	<p><%=name %>님 안녕하세요~</p>
	<input type="button" value="main" onclick="location.href='../main/index.jsp'">
	<input type="button" value="로그아웃" onclick="location.href='logout.jsp'">
</body>
</html>
```
#### index.jsp
* 메인 페이지에서 로그인이 되어있지 않으면 **회원가입, 로그인** 메뉴가 뜨도록하고, 로그인이 되어있으면 **로그아웃** 메뉴가 뜨도록한다.
  
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
		<a href="../member/logout.jsp"> 로그아웃 </a>
	<%} %>
</body>
</html>
```
> #### 결과
> * 메인 화면에서 **로그인** 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116085673-03194080-a6da-11eb-9ca7-8a23be2ce869.png)
> * 아이디 비밀번호 입력 후 로그인 버튼 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116085721-0f050280-a6da-11eb-9dbf-a28a4a157bfb.png)
> * 로그인이 정상적으로 작동한다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116085835-3065ee80-a6da-11eb-8ad4-668be5d5ec0e.png)
> * main으로 돌아오면 **로그아웃** 메뉴만 뜬다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116085919-4378be80-a6da-11eb-8ba1-00ecc364b67d.png)

## 로그아웃 : 세션 삭제

