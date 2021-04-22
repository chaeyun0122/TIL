# JSP 내장 객체 (Implicit Object)
* 웹 컨테이너는 JSP 페이지에서 상황에 따라 필수적으로 사용되는 9개의 객체를 객체 생성 없이 바로 사용할 수 있게 제공한다.

## 1. Request
* 웹 브라우저의 요청 정보를 저장하고 있는 객체  
* 데이터를 전송할 페이지에서 action 옵션에 전송 받을 페이지를 입력하고,
전송 받을 페이지에서 request 객체를 통해 데이터를 받는다.
* ex) `String stuNum = request.getParameter("stuNum");`
#### ex01_request.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Request </title>
</head>
<body>
	<h1> 학번, 이름, 학년, 선택과목 </h1>
	<form action="ex01_requestPro.jsp" method="post">
		학번 : <input type="text" name="stuNum"><br>
		이름 : <input type="text" name="name"><br>
		학년 :
		<input type="radio" name="grade" value="1학년"> 1학년
		<input type="radio" name="grade" value="2학년"> 2학년
		<input type="radio" name="grade" value="3학년"> 3학년
		<input type="radio" name="grade" value="4학년"> 4학년<br>
		선택 과목 : 
		<select name="subject">
			<option value="JAVA"> Java </option>
			<option value="JSP"> JSP </option>
			<option value="CPP"> Cpp </option>
			<option value="PYTHON"> Python </option>
		</select>
		<br><br>
		<input type="submit" value="입력 완료">
	</form>
</body>
</html>
```
#### ex01_requestPro.jsp
```jsp
<%-- ex01_requestPro.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String stuNum = request.getParameter("stuNum");
String name = request.getParameter("name");
String grade = request.getParameter("grade");
String subject = request.getParameter("subject");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Request 결과 </title>
</head>
<body>
	<table>
		<tr>
			<td> 학 번 </td><td> <%=stuNum %></td>
		</tr>
		<tr>
			<td> 이름 </td><td> <%=name %></td>
		</tr>
		<tr>
			<td> 학 년 </td><td> <%=grade %></td>
		</tr>
		<tr>
			<td> 선택 과목 </td><td> <%=subject %></td>
		</tr>
	</table>
</body>
</html>
```  
#### 결과  
> * 값 입력 후 `입력 완료` 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/115542357-28bdd880-a2db-11eb-8715-e198a4c5e09d.png)
> * 결과가 `ex01_requestPro.jsp` 페이지로 넘어간다.  
>   
> ![image](https://user-images.githubusercontent.com/79209568/115542448-3ffcc600-a2db-11eb-8954-ef34089c8f58.png)

## 2. Response
* 웹 브라우저의 요청에 대한 응답 정보를 저장하고 있는 객체
### forward
* forward방식은 데이터를 함께 보낼 수 있다.
#### forward.jsp
* `onclick="location.href='forwardProc.jsp'"`을 통해 forwardProc.jsp로 이동하도록 설정한다.
```jsp
<%-- forward.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> forward </title>
</head>
<body>
	<h1> forward </h1>
	<br>
	<p>
		forward.jsp -> forwardProc.jsp -> forwardResult.jsp 페이지로 이동합니다. <br>
		forward로 이동하면 데이터를 공유합니다. <br>
		주소는 forwardProc.jsp로 보이지만 결과는 forwardResult.jsp가 나옵니다. 
	</p>
	<br>
	<input type="button" value="forward" onclick="location.href='forwardProc.jsp'">
</body>
</html>
```
#### forwardProc.jsp
* `request.setAttribute("apple", "사과");` : apple 속성을 사과라고 설정
* `RequestDispatcher dispatcher = request.getRequestDispatcher("forwardResult.jsp");` : RequestDispatcher 객체를 사용해서 이동할 페이지를 설정
* `dispatcher.forward(request, response);` : forward를 통해 데이터를 함께 이동시킴
```jsp
<%-- forwardProc.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setAttribute("apple", "사과");
RequestDispatcher dispatcher = request.getRequestDispatcher("forwardResult.jsp");
dispatcher.forward(request, response);
%>
```
#### forwardResult.jsp
```jsp
<%-- forwardResult.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String apple = (String)request.getAttribute("apple");
%>

결과 = <%= apple %>
```
> #### 결과
> * forward 버튼을 누르면 onclick메서드를 통해 forwardProc.jsp로 이동한다.
>   ![image](https://user-images.githubusercontent.com/79209568/115550768-324c3e00-a2e5-11eb-9142-d2ed77f7538f.png)
> * url에서는 forwardProc.jsp라고 나오지만 결과 화면은 forwardResult.jsp의 화면이다.
> * 값이 함께 전달된 것을 확인할 수 있다.
>   ![image](https://user-images.githubusercontent.com/79209568/115550934-5f005580-a2e5-11eb-87e7-df8b0e52d3cc.png)

### redirect
* redirect방식은 데이터를 함께 이동할 수 없고 페이지만 이동 가능하다.
#### redirect.jsp
`onclick="location.href='sendProc.jsp'"`을 통해 sendProc.jsp로 이동하도록 설정한다.
```JSP
<%-- redirect.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> redirect </title>
</head>
<body>
	<h1> redirect </h1>
	<br>
	<p>
		redirect.jsp -> sendProc.jsp -> sendResult.jsp 페이지로 이동합니다. <br>
		sendRedirect로 이동하면 데이터는 공유되지 않습니다.
	</p>
	<br>
	<input type="button" value="redirect" onclick="location.href='sendProc.jsp'">
</body>
</html>
```
#### sendProc.jsp
* `request.setAttribute("apple", "사과");` : apple 속성을 사과라고 설정
* `response.sendRedirect("sendResult.jsp");` : sendRedirect를 통해 이동할 페이지 설정
```jsp
<%-- sendProc.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("apple", "사과");
response.sendRedirect("sendResult.jsp");
%>
```
#### sendResult.jsp
```jsp
<%-- sendResult.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String apple = (String)request.getAttribute("apple");
%>

결과 : <%=apple %>
```
> #### 결과
> * redirect 버튼을 누르면 onclick메서드를 통해 sendProc.jsp로 이동한다.  
>   ![image](https://user-images.githubusercontent.com/79209568/115553793-c79d0180-a2e8-11eb-9ddd-0d70a695f030.png)
> * url이 sendResult.jsp로 이동했다.
> * 값이 함께 전달되지 않은 것을 확인할 수 있다.
>   ![image](https://user-images.githubusercontent.com/79209568/115554324-4c881b00-a2e9-11eb-8eae-1060344f69d4.png)

## 3. Session
* 하나의 웹 브라우저 내에서 정보를 유지하기 위한 세션 정보를 저장하고 있는 객체
* `session.setMaxInactiveInterval(time)` :  세션을 유지할 시간을 설정 (초 단위)
* `session.isNew()` : 새로운 클라이언트인지 확인. 새로운 세션이면 true, 아니면 false 반환
* `session.getCreationTime()` : 세션의 생성된 시각을 1/1000초단위로 반환 (1970년 1월 1일 0시 0분 0초 GMT 기준)
* `session.getLastAccessedTime()` : 웹 브라우저의 요청이 마지막으로 시도된 시간을 1/1000초 단위로 반환
* `session.getId()` : 해당 세션의 ID 를 반환
#### ex03_session.jsp
```jsp
<%-- ex03_session.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// session유지시간
session.setMaxInactiveInterval(10); //10초
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> session </title>
</head>
<body>
	<h1> session </h1>
	<br>
	<p> isNew() : <%=session.isNew() %></p>
	<p> 생성 시간 : <%=session.getCreationTime() %></p>
	<p> 최종 접속 시간 : <%=session.getLastAccessedTime() %></p>
	<p> 세션 ID : <%=session.getId() %></p>
</body>
</html>
```
> #### 결과
> * 웹페이지 요청이 시작되면서 세션이 생성되었다. 세션 유지시간인 10초 뒤에 새로고침을 눌러본다.  
>   
> ![image](https://user-images.githubusercontent.com/79209568/115549558-bd2c3900-a2e3-11eb-827e-d6d345fecf24.png)
> * 새로운 세션이 시작되는 것을 확인할 수 있다.  
>   
> ![image](https://user-images.githubusercontent.com/79209568/115549593-c74e3780-a2e3-11eb-8fb1-e57ddba85aa7.png)

## 4. Application
* 웹 어플리케이션 Context의 정보를 저장하고 있는 객체
* `application.getMajorVersion()` : 현재 지원하는 서블릿 스펙의 major 버전을 리턴
* `application.getServerInfo()` : 현재 동작하고 있는 서버 정보를 리턴
* `application.getRealPath("/")` : webapp폴더까지의 경로
#### ex04_application.jsp
```jsp
<%-- ex04_application.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> application </title>
</head>
<body>
	<table border="1">
		<tr>
			<td> JSP 버전 </td>
			<td><%=application.getMajorVersion() %>, <%=application.getMinorVersion() %></td>
		</tr>
		<tr>
			<td> 컨테이너 정보 </td>
			<td><%=application.getServerInfo() %></td>
		</tr>
		<tr>
			<td> 웹 어플리케이션의 파일시스템 정보</td>
			<td><%=application.getRealPath("/") %></td>
		</tr>
	</table>
</body>
</html>
```
> #### 결과
> ![image](https://user-images.githubusercontent.com/79209568/115557825-0df45f80-a2ed-11eb-99d1-ecc22568f48d.png)

## 5. Out
* JSP 페이지의 출력 할 내용을 가지고 있는 출력 스트림 객체
#### out.jsp
```jsp
<%-- out.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> out </title>
</head>
<body>
	<form action="outProc.jsp" method="post">
		<label> 이 름 : </label><input type="text" name="name"><br>
		<label> 나 이 : </label><input type="text" name="age">
		<br><br>
		<input type="submit" value="전송">
	</form>
</body>
</html>
```
#### outProc.jsp
```jsp
<%-- outProc.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String name = request.getParameter("name");
String age = request.getParameter("age");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> outProc </title>
</head>
<body>
	<h1> 데이터 출력 </h1>
	<br>
	<% if (name.length() != 0 && age.length() != 0) { %>
		<p>
			 이름 : <%=name %> - 나이 : <%=age %>
		</p>
	<%} else {%>
		<p> 이름 or 나이가 입력되지 않았습니다. </p>
	<%} %>
	<br><br>
	
	<% if (name.length() != 0 && age.length() != 0) { %>
		<% 
		out.println("이름 : " + name + " - 나이 : " + age); 
		%>
	<%} else {%>
		<% out.println("이름 or 나이가 입력되지 않았습니다."); %>
	<%} %>
</body>
</html>
```
> #### 결과
> * 값을 입력 후 `전송` 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/115555334-58c0a800-a2ea-11eb-995a-553aa72c4474.png)
> * p 태그로 출력한 것과 out 메서드로 출력한 결과가 같음  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/115555461-7e4db180-a2ea-11eb-9da9-71bee9ee411d.png)


## 6. PageContext
* JSP 페이지에 대한 정보를 저장하고 있는 객체
* `pageContext.forward("page_path");` : 지정한 상대경로 페이지로 이동
* `pageContext.include("page_path");` : 지정한 상대경로 페이지를 현재 JSP 페이지에 포함

#### pageContext1.jsp
* forward를 통해 pageContext2.jsp로 이동한다.
```jsp
<%-- pageContext1.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
pageContext.forward("pageContext2.jsp");
%>
```
#### pageContext2.jsp
* include를 통해 pageContext3.jsp의 내용을 현재 페이지에 포함 시키고 현재 페이지의 h2태그 내용을 출력한다. 
```jsp
<%-- pageContext2.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> pageContext2 </title>
</head>
<body>
	<%
	pageContext.include("pageContext3.jsp");
	%>
	<h2> pageContext1의 forward 메서드로 포워딩 된 페이지 입니다.</h2>
</body>
</html>
```
#### pageContext3.jsp
```jsp
<%-- pageContext3.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h2> include 되는 pageContext3.jsp 페이지 입니다.</h2>
<hr>
```
> #### 결과
> * pageContext1.jsp에서 실행되므로 url은 `pageContext1`이고, forward로 pageContext2.jsp로 이동했기 때문에 title과 페이지 내용은 `pageContext2`의 것이다.
>   ![image](https://user-images.githubusercontent.com/79209568/115557492-b7872100-a2ec-11eb-9161-d0b54376c694.png)

## 7. Cofig
* JSP 페이지에 대한 설정정보를 저장하고 있는 객체
```jsp
<%-- ex07_config.jsp --%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>config</title>
</head>
<body>
	<h1> config </h1>
	<br>
	<table border="1">
		<tr><td> 초기 파라미터 이름 </td><td> 초기 파라미터 값 </td></tr>
	<%
	Enumeration e = config.getInitParameterNames();
	while (e.hasMoreElements()) {
		String init_paramName = (String)e.nextElement();
	%>	
		<tr>
			<td><%=init_paramName %></td>
			<td><%=config.getInitParameter(init_paramName) %></td>
		</tr>
	<%} %>
	</table>
</body>
</html>
```
> #### 결과
> ![image](https://user-images.githubusercontent.com/79209568/115555811-ebf9dd80-a2ea-11eb-85f9-8ec2f75d2e84.png)

## 8. Exception
* JSP 페이지에서 예외가 발생한 경우에 사용되는 객체
#### add.jsp
```jsp
<%-- add.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> add </title>
</head>
<body>
	<h1> 1 ~ n 까지의 합</h1>
	<br>
	<form action="addProc.jsp" method="get">
		1 부터 <input type="text" name="last"> 까지의 합
		<br><br>
		<input type="submit" value="계산">
	</form>
	
</body>
</html>
```
#### addProc.jsp
```jsp
<%-- add.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> add </title>
</head>
<body>
	<h1> 1 ~ n 까지의 합</h1>
	<br>
	<form action="addProc.jsp" method="get">
		1 부터 <input type="text" name="last"> 까지의 합
		<br><br>
		<input type="submit" value="계산">
	</form>
	
</body>
</html>
```
> #### 결과
> ![image](https://user-images.githubusercontent.com/79209568/115698509-a814e000-a39f-11eb-9f8c-20a8b700ad7a.png)  
>   
> ![image](https://user-images.githubusercontent.com/79209568/115698542-b3680b80-a39f-11eb-94cd-4d9f1f33c731.png)  
>  

### 하지만 숫자가 아닌 다른 값을 넣을 경우 에러가 발생한다
* addProc.jsp의 page영역에 `errorPage="addException.jsp"`를 추가하여 에러가 나면 addException.jsp페이지로 넘어가도록 설정한다.
* addException.jsp의 page영역에 `isErrorPage="true"`를 추가하여 현재 페이지가 예외 처리 페이지라는 것을 명시하고 exception 객체를 이용할 수 있게되어 예외에 대한 정보를 얻을 수 있다.
#### addProc.jsp
```jsp
<%-- page부분에 errorPage="addException.jsp" 코드 추가 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    errorPage="addException.jsp"%>
<%
```
#### addException.jsp 
```jsp
<%-- addException.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> addException </title>
</head>
<body>
	<h1> 에러 페이지 </h1>
	<br>
	<h1><%=exception.getMessage() %></h1>
</body>
</html>
```

> #### 결과
> ![image](https://user-images.githubusercontent.com/79209568/115700530-b8c65580-a3a1-11eb-9f7d-6c072abbd7fd.png)  
>   
> ![image](https://user-images.githubusercontent.com/79209568/115700559-c085fa00-a3a1-11eb-85f5-d9fec7c2d4f8.png)


## 9. Page
* JSP 페이지를 구현한 자바 클래스 객체



