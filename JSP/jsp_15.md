# Cookie & Session
* 정보를 어디서 관리하느냐의 차이가 있다.

# 쿠키 (cookie)
* HTTP 프로토콜은 웹 브라우저의 요청에 대한 응답을 하고 나면 클라이언트와의 연결을 지속하지 않는다.
* 상태가 없는 프로토콜을 위해 상태를 지속시키기 위한 방법이다.
* 정보를 웹 브라우저에 저장한다. 클라이언트 측에서 관리 된다.
* 파일로 저장되기 때문에 브라우저가 종료되어도 생존기간 동안 데이터가 유지된다.
* 보안에 취약하기 때문에 중요한 정보는 session으로 처리 된다.

## 쿠키 사용
### 1. 쿠키 생성
> `new Cookie (String name, String value)`
### 2. request 객체에 실려 온 쿠키를 읽어올 때
> `Cookie[] cookie = request.getCookie()`
### 3. 쿠키를 response 객체에 추가
> `response.addCookie(cookie)`

## 쿠키 사용 순서
1. Cookie 생성
2. Cookie 설정
3. 웹 브라우저에 생성된 Cookie 전송

## 웹 브라우저에 저장된 Cookie를 사용하는 절차
1. 웹 브라우저의 요청에서 Cookie 얻기
2. Cookie는 이름, 값의 쌍으로 된 배열 형태로 반환된다.
3. Cookie 이름을 통해서 Cookie에 설정된 값을 가져온다.

## 쿠키 생성 실습
#### cookieMake.jsp
```jsp
<%-- cookieMake.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String cookieName = "id";
Cookie cookie = new Cookie(cookieName, "testCookie"); // 쿠키 객체 생성
cookie.setMaxAge(30);  // 쿠키 생존 시간 : 초단위로 지정 (0:즉시 삭제, -1:브라우저 종료 시 삭제)
response.addCookie(cookie);  // 클라이언트에게 보내기

%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠키 생성</title>
</head>
<body>
	<h2> 쿠키 생성 페이지 </h2>
	<br>
	<p> <%=cookieName %> 쿠키가 생성되었습니다.</p>
	<form action="cookieUse.jsp" method="post">
		<input type="submit" value="쿠키 확인">
	</form>
</body>
</html>
```
#### cookieUse.jsp
```jsp
<%-- cookieUse.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 쿠키 확인 </title>
</head>
<body>
<%
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (int i=0; i<cookies.length; i++) {
			if (cookies[i].getName().equals("id")) {
%>
				쿠키 이름 : <%=cookies[i].getName() %><br>
				쿠키 값    : <%=cookies[i].getValue() %>
<%
			}
		}
	}
%>
</body>
</html>
```
> #### 결과
> ![image](https://user-images.githubusercontent.com/79209568/116073333-3dc7ac80-a6cb-11eb-92e5-c335a7d2b5bc.png)
>   
> ![image](https://user-images.githubusercontent.com/79209568/116073358-44eeba80-a6cb-11eb-9133-3154d96dcf64.png)

## 로그인 : 쿠키 생성
* 이 전에 실습했던 member 프로젝트의 로그인을 쿠키를 통해서 이루어지도록 변경해본다.
#### login.jsp
* loginForm.jsp로부터 받아온 id와 pwd를 통해 memberDAO의 login()함수를 실행시켜 회원의 이름을 return받아 name변수에 저장한다.
* 이름과 id 쿠키를 생성한다.  
  
```jsp
<%-- member/login.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="memberDAO.MemberDAO"%>
<%@ page import="java.net.URLEncoder"%>
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
	// Cookie를 이용한 페이지 이동
	if(name != null) {
		Cookie cookieName = new Cookie("memberName", URLEncoder.encode(name, "utf-8"));
		cookieName.setMaxAge(3);
		response.addCookie(cookieName);
		
		Cookie cookieId = new Cookie("memeberId", id);
		cookieId.setMaxAge(3);
		response.addCookie(cookieId);
		
		response.sendRedirect("loginOk.jsp");
	}
	else
		response.sendRedirect("loginFail.jsp");
	%>
	
</body>
</html>
```
#### loginOk.jsp
* cookie로 전달되 데이터를 받아서 로그인 성공
```jsp
<%-- loginOk.jsp --%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// Cookie로 전달된 데이터 받기
String name = null;
String id = null;

Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (int i=0; i<cookies.length; i++) {
		if (cookies[i].getName().equals("memberName"))
			name = URLDecoder.decode(cookies[i].getValue(), "utf-8");
		else if (cookies[i].getName().equals("memberId"))
			id = cookies[i].getValue();
	}
}
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
> #### 결과화면
> * 메인 화면에서 로그인 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116078873-1d4f2080-a6d2-11eb-8b34-d8e02d97bbf0.png)
> * 아이디 비밀번호 입력 후 로그인 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116078942-348e0e00-a6d2-11eb-9686-66ea1f90c3d3.png)
> * 로그인 성공
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116079023-4ec7ec00-a6d2-11eb-9651-979add676803.png)

## 로그아웃 : 쿠키 삭제
* `loginOk.jsp`에서 로그아웃 버튼을 클릭하면 쿠키를 삭제하여 로그아웃이 진행되도록한다.
* `logout.jsp` 생성
#### logout.jsp
```jsp
<%-- logout.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// Cookie 삭제
Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (int i=0; i<cookies.length; i++) {
		if (cookies[i].getName().equals("memberName")) {
			cookies[i].setMaxAge(0); // cookie 삭제
			response.addCookie(cookies[i]);
		}
		else if (cookies[i].getName().equals("memberId")) {
			cookies[i].setMaxAge(0); // cookie 삭제
			response.addCookie(cookies[i]);
		}
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 로그 아웃 </title>
<script type="text/javascript">
	// logout.jsp 가 호출되면서 바로 실행된다.
	window.onload=function() {
		alert("로그아웃");
		location.href="../main/index.jsp"; // 확인 버튼을 누르면 이동하는 페이지
	}
</script>
</head>
<body>

</body>
</html>
```
> #### 결과
> * 로그아웃 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116079557-e3cae500-a6d2-11eb-860e-acb91061476b.png)
> * 로그아웃이라는 alert 화면이 뜨면서 main화면으로 이동  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116079604-f7764b80-a6d2-11eb-99ae-93a28807414b.png)

