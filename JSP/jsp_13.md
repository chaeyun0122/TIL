# JSP 내장 객체 (Implicit Object)
* 웹 컨테이너는 JSP 페이지에서 상황에 따라 필수적으로 사용되는 9개의 객체를 객체 생성 없이 바로 사용할 수 있게 제공한다.

## 1. Request
* 웹 브라우저의 요청 정보를 저장하고 있는 객체  
  
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

## 3. Out
* JSP 페이지의 출력 할 내용을 가지고 있는 출력 스트림 객체

## 4. Session
* 하나의 웹 브라우저 내에서 정보를 유지하기 위한 세션 정보를 저장하고 있는 객체

## 5. Application
* 웹 어플리케이션 Context의 정보를 저장하고 있는 객체

## 6. PageContext
* JSP 페이지에 대한 정보를 저장하고 있는 객체

## 7. Page
* JSP 페이지를 구현한 자바 클래스 객체

## 8. Cofig
* JSP 페이지에 대한 설정정보를 저장하고 있는 객체

## 9. Exception
* JSP 페이지에서 예외가 발생한 경우에 사용되는 객체

