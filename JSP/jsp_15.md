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
