# 표준 태그 라이브러리 ( JSTL : JSP Standard Tag Library ) 
> 커스텀 태그 중 가장 많이 사용되는 태그를 표준화하여 라이브러리로 제공한다.

## JSTL 라이브러리 다운로드
* http://tomcat.apache.org/download-taglibs.cgi 에서 4개의 jar 파일을 다운로드 한다.
  * Impl, Spec, EL, Compat  
  ![image](https://user-images.githubusercontent.com/79209568/116872239-8691df00-ac50-11eb-8600-090dd29febe5.png)
* 프로젝트의 `WEB-INF > lib`에 넣는다.  
  ![image](https://user-images.githubusercontent.com/79209568/116872405-c9ec4d80-ac50-11eb-8b54-1fc3b86a0196.png)

## Core 태그 라이브러리 기능
* 자바로 구현한 변수 선언, 조건식, 반복문 기능 등을 태그로 대체할 수 있다.
* 사용 전 반드시 taglib 디렉티브 태그를 선언해야 한다.  
`<%@ taglib prefix="c" urㅑ="http://java.suㅜ.com/jsp/jstl/core" %>`

# 실습
## 변수 설정
* `<c:set>` : 변수 설정 (변수는 기본적으로 page 영역의 속성으로 저장된다.)
* `<c:remove>` : 변수 (속성) 삭제
```jsp
<%-- JSTL/ex01_var.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- core 태그 라이브러리를 사용하기 위해서 반드시 선언해야한다.--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> JSTL 변수 설정 </title>
</head>
<body>
	<h2> 변수 설정 </h2>
	<c:set var="num1" value="1"/>
	<c:set var="num2"> 10 </c:set>
	<c:set var="num3" value="${30 }" /> <%--EL선언도 가능 --%>
	<c:set var="num4" value="40" scope="request"/> 
	<h3>
	num1 : ${ num1 }<br>
	num2 : ${ num2 }<br>
	num3 : ${ num3 }<br>
	num4 : ${ num4 }<br>
	</h3>
	<br>
	
	<h2> 변수 삭제 </h2>
	<c:set var="name" value="test"/>
	<h3>name : ${ name }</h3>

	<c:remove var="name"/>
	<h3>name : ${ name }</h3>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/116875004-2cdfe380-ac55-11eb-9e11-be5b5d5c7131.png)

## 조건식
### if문, choose 문
* `<c:if>` : if 문
* `<c:choose>` : switch 문
  * `<c:when>` : choose안에만 사용하는 case 문
  * `<c:otherwise>` : choose안에서만 사용하는 default 문
#### form.jsp
```jsp
<%-- JSTL/ex02_form.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 입력 정보 </title>
</head>
<body>
	<form action="ex02_resif.jsp" method="get">
		이름 : <input type="text" name="userName"><br>
		나이 : <input type="text" name="userAge"><br>
		<input type="submit" value="전송">
	</form>
</body>
</html>
```
#### ex02_resif.jsp
```jsp
<%-- JSTL/ex02_resif.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 입력 결과 </title>
</head>
<body>
	<h2> if </h2>
	<c:set var="name">${param.userName }</c:set>
	<c:set var="age">${param.userAge }</c:set>
	
	<h3>${name} 님의 나이 : ${age }<br></h3>
	
	<%-- <c:if> --%>
	<c:if test="${age >= 20 }">
		<h3> 성인 입니다. </h3>
	</c:if>
	<c:if test="${age < 20 }">
		<h3> 미성년자 입니다. </h3>
	</c:if>
	
	<%-- <c:choose> --%>
	<c:choose>
		<c:when test="${age >= 20 }">
			<h3> 성인 or 대학생 </h3>
		</c:when>
		<c:when test="${age >= 14 }">
			<h3> 중/고등학생 </h3>
		</c:when>
		<c:when test="${age >= 8 }">
			<h3> 초등학생 </h3>
		</c:when>
		<c:otherwise>
			<h3> 미취학 아동 </h3>
		</c:otherwise>
	</c:choose>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/116876966-446c9b80-ac58-11eb-98dc-3040bc24f327.png)
> ![image](https://user-images.githubusercontent.com/79209568/116876975-48002280-ac58-11eb-98b2-c80fea30a81f.png)
> ![image](https://user-images.githubusercontent.com/79209568/116876980-4afb1300-ac58-11eb-804f-f01034765935.png)
> ![image](https://user-images.githubusercontent.com/79209568/116876985-4d5d6d00-ac58-11eb-86bb-e84791a611c8.png)

### for 문
```
<c:forEach var="변수이름"
       items="반복 객체 이름"
       begin="시작값" end="종료값"
       step="증가값">
</c:forEach>
```
#### ex03_for.jsp
```jsp
<%-- JSTL/ex03_for.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> for each </title>
</head>
<body>
	<h2> for each </h2>
	<%--
		<c:forEach> : for 문
		
		<c:forEach var="변수이름"
				   items="반복 객체 이름"
				   begin="시작값" end="종료값"
				   step="증가값">
		</c:forEach>
	 --%>
	 <c:forEach var="name" items="유재석, 박명수, 하하, 정준하">
	 	<h3> ${name }</h3>
	 </c:forEach>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/116877954-9a8e0e80-ac59-11eb-943d-02f764bfc28d.png)
