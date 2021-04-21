# JSP (Java Server Page)
* HTML 문서 안에 java 코드를 삽입해서 웹 서버에 동적으로 웹페이지를 생성하여 클라이언트에 전달한다.
* JSP는 서블릿 기술을 바탕으로 해서, 웹 응용프로그램을 작성할 수 있다.

## Servlet 변환 과정
* jsp파일을 java파일로 변경하고 클래스 파일로 작성된 후 서버에서 실행하게 된다.
* jsp → java : 파일을 컨테이너가 서블릿 소스코드(.java)로 변환
* java → class : 컴파일
* `test.jsp → test_jsp.java → test_jsp.class → test_jsp Sevlet 실행`

## JSP 지시자 :  \<%@   %>
* 페이지가 실행될 때 필요한 정보를 정의하는 역할이다.
* **page 지시자** : 해당 페이지에 대한 정보 및 설정을 하는 지시자
  * laguage : 페이지 내에서 사용할 프로그래밍 언어
  * contentType : 페이지 내에서 사용할 언어와 문자 형식 지정
  * pageEncoding ; 서버 내에서 작업할 때 사용할 문자 형식 지정
  * import : 사용하려는 class 지정
  * session : 세션을 사용할 지를 결정
* **include 지시자** : 현재 페이지에 포함된 코드나 문서를 정의하는 지시자
* **taglib 지시자** : JSP 페이지 내에서 사용되는 별도의 표현 언어를 사용하기 위해 쓰이는 지시자

## 스크립트릿 (Scriplets)
* **<%!   %>** : 선언부
  - 전역변수 및 메서드 선언
* **<% %>** : 스크립트릿
  - 일반적인 코드를 작성하는 영역
* **<%= %>** : 표현식
  - 데이터를 표현하는 부분

# 실습
## 날짜 출력
* java코드를 import해보고 변수를 사용해본다.
```jsp
<%-- ex02.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String today = sdf.format(date);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Import </title>
<style>
p { font-size:20px }
</style>
</head>
<body>
	<h1> 오늘 날짜 </h1>
	<br>
	<p> 오늘은 <%=today %> 입니다. </p>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/115388653-17ab9380-a217-11eb-94c9-c9ab2ef90f3a.png)

## 더하기
* 값을 보내고 받아본다.
#### ex03.jsp
* form의 데이터를 `ex03Pro.jsp`로 전송한다.
```jsp
<%-- ex03.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 더하기 </title>
<style type="text/css">
#data { font-size:30px; text-align:center }
b { font-size:30px }
</style>
</head>
<body>
	<h1> 숫자 입력 </h1>
	<br>
	<form action="ex03Pro.jsp" method="post">
		<input type="text" id="data" name="num1"> <b>+</b> <input type="text" id="data" name="num2"><br>
		<br>
		<input type="submit" value="완료"> &nbsp; <input type="reset" value="다시작성">
	</form>
</body>
</html>
```
#### ex03Pro.jsp
```jsp
<%-- ex03Pro.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 불러오는 건 문자이기 때문에 int로 바꿔줘야한다.
	int num1 = Integer.parseInt(request.getParameter("num1"));
	int num2 = Integer.parseInt(request.getParameter("num2"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 결과 </title>
<style type="text/css">
p { font-size:30px }
</style>
</head>
<body>
	<h1> 두 수의 합 </h1>
	<br>
	<p> <%=num1 %> + <%=num2 %> = <%=num1 + num2 %></p>
</body>
</html>
```
> * 입력 후 완료 클릭  
>   ![image](https://user-images.githubusercontent.com/79209568/115389967-b258a200-a218-11eb-8ef5-2f676844a587.png)
>   
> * ex03Pro 페이지로 넘어가서 결과 출력  
>   ![image](https://user-images.githubusercontent.com/79209568/115390028-c1d7eb00-a218-11eb-9458-144df80e1ab9.png)

## 성인, 미성년자 확인
* 값을 보내고 받아보며 조건문도 써본다.
#### ex04.jsp
```jsp
<%-- ex04.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Control </title>
<style type="text/css">
td { font-size:20px }
.datain { 
	font-size:20px; 
} <%-- 클래스는 .클래스명 으로 접근한다 --%>
</style>
</head>
<body>
	<h1> 이름, 나이를 입력하세요 </h1>
	<br>
	<form action="ex04Pro.jsp" method="post">
		<table border="1">
			<tr>
			<td> 이 름 : </td><td><input type="text" class="datain" name="name"></td>
			</tr>
			<tr>
			<td> 나 이 : </td><td><input type="text" class="datain" name="age"></td>
			</tr>
			<tr>
			<td colspan="2" align="center">
				<input type="submit" class="datain" value="입력완료">
			</td> 
			</tr>
		</table>
	</form>
</body>
</html>
```
#### ex04Pro.jsp
* 데이터를 받을 때 get 방식은 한글이 깨지지 않지만, post 방식은 깨진다.
* `request.setCharacterEncoding("utf-8");`를 써줘야 깨지지 않는다.
```jsp
<%-- ex04Pro.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String name = request.getParameter("name");
int age = Integer.parseInt(request.getParameter("age"));
String adult = "";
if (age > 19)
	adult = "성인";
else
	adult = "미성년자";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
p { font-size:20px }
</style>
</head>
<body>
	<h1> 성인, 미성년자 </h1>
	<br>
	<p> 이 름 : <%=name %></p>
	<p> 나 이 : <%=age %></p>
	<p> 당신은 <%=adult %> 입니다. </p>
</body>
</html>
```

> * 값을 입력 후 입력완료 클릭  
>   ![image](https://user-images.githubusercontent.com/79209568/115394025-547a8900-a21d-11eb-9194-ba306f7a9106.png)
>   
> * 결과 출력  
>   ![image](https://user-images.githubusercontent.com/79209568/115394048-58a6a680-a21d-11eb-84a6-f3eb62c6c7e6.png)

## 성적 출력하기
* html \<body> 안에서도 스크립트릿 사용 가능하다.
* jsp 스크립트릿에서는 out 함수가 내장 되어 있다. (JSP 내장객체 종류와 설명은 이 글 참조 → [here](JSP/jsp_13.md))
#### ex05.jsp
```jsp
<%-- ex05.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 성 적</title>
</head>
<body>
	<h1> 이름, 성적 입력 </h1>
	<br>
	<form action="ex05Pro.jsp" method="post">
		이름 : <input type="text" name="name"><br><br>
		CPP : <input type="text" name="cpp"><br><br>
		JAVA : <input type="text" name="java"><br><br>
		PYTHON : <input type="text" name="python"><br><br>
		<br><br>
		<input type="submit" value="전송">
	</form>
</body>
</html>
```
#### ex05Pro.jsp
```jsp
<%-- ex04Pro.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 성적 결과 </title>
</head>
<body>
	<h1> 성 적 </h1>
	<br>
	<div>
		A : 100 ~ 90 <br>
		B : 89 ~ 80 <br>
		C : 79 ~ 70 <br>
		D : 69 ~ 60 <br>
		F : 59 ~ 0
	</div>
	<hr>
	<%
	// body 안에 써도 상관 없다
	String name = request.getParameter("name");
	float cpp = Float.parseFloat(request.getParameter("cpp"));
	float java = Float.parseFloat(request.getParameter("java"));
	float python = Float.parseFloat(request.getParameter("python"));
	float total = cpp + java + python;
	float avg = total / 3;
	String grade = "F";
	if (avg >= 90) grade = "A";
	else if (avg >= 80) grade = "B";
	else if (avg >= 70) grade = "C";
	else if (avg >= 60) grade = "D";
	// out 객체가 내장되어 있다.
	out.println(name + "님의 성적 : " + grade);
	%>
</body>
</html>
```
> * 값을 입력 후 전송 클릭  
> ![image](https://user-images.githubusercontent.com/79209568/115397095-a8d33800-a220-11eb-9989-d7dd23ab95ce.png)
>  
> * 결과 출력  
> ![image](https://user-images.githubusercontent.com/79209568/115397125-b5579080-a220-11eb-82d0-cd68b40eee6b.png)

## 계산기
#### ex06.jsp
```jsp
<%-- ex06.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계산기</title>
<style type="text/css">
#calc { font-size:20px; text-align:center }
</style>
</head>
<body>
	<h1> 계산기 </h1>
	<br>
	<form action="ex06Pro.jsp" method="post">
		<input type="text" id="calc" name="data1">
		<select name="opt" id="calc">
			<option value="+"> + </option>
			<option value="-"> - </option>
			<option value="*"> * </option>
			<option value="/"> / </option>
			<option value="%"> % </option>
		</select>
		<input type="text" id="calc" name="data2">
		<input type="submit" id="calc" value="계산">
	</form>
</body>
</html>
```
#### ex06Pro.jsp
```jsp
<%-- ex06Pro.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 계산 결과 </title>
<style type="text/css">
p { font-size:30px }
</style>
</head>
<body>
	<%
	double data1 = Double.parseDouble(request.getParameter("data1"));
	double data2 = Double.parseDouble(request.getParameter("data2"));
	String opt = request.getParameter("opt");
	double result = 0;
	
	switch(opt) {
	case "+":
		result = data1 + data2; break;
	case "-":
		result = data1 - data2; break;
	case "*":
		result = data1 * data2; break;
	case "/":
		result = data1 / data2; break;
	case "%":
		result = data1 % data2; break;
	}
	%>
	<p><%=data1 %> <%=opt %> <%=data2 %> = <%=result %></p>
</body>
</html>
```

> * 숫자 입력과 select 옵션 선택 후 계산 클릭    
> ![image](https://user-images.githubusercontent.com/79209568/115398260-f308e900-a221-11eb-898d-119e87ec54df.png)
>   
> * 결과 출력  
> ![image](https://user-images.githubusercontent.com/79209568/115398347-061bb900-a222-11eb-8aa0-c4498504b157.png)

## 구구단
* 자바코드와 html코드를 함께 사용할 수 있다.
* 여는 태그와 닫는 태그를 항상 맞춰서 **먼저 작성해놓아야** 헷갈리지 않는다.
#### ex07.jsp
```jsp
<%-- ex07.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 구구단 </title>
</head>
<body>
	<table border="1">
		<% for (int i=1; i<10; i++) { %>
		<tr align="center">
			<% for (int j=2; j<10; j++) { %>
				<td> <%=j %> * <%=i %> = <%=j*i %> </td>
			<%} %>
		</tr>
		<%} %>
	</table>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/115538204-9b788500-a2d6-11eb-829c-b7d902cd637d.png)


