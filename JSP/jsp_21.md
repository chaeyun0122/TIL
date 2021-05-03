# 표현 언어 (EL : Expression Language)
> java 코드가 들어가는 표현식을 좀 더 편리하게 사용하기 위해 JSP 2.0 부터 도입된 데이터 출력 기능이다.

## 표현 언어의 특징
* 기존 표현식보다 편리하게 값을 출력할 수 있다.
* 변수와 여러가지 연산자를 포함할 수 있다.
* JSP의 내장 객체에 저장된 속성 및 자바의 bean 속성도 표현 언어에서 출력할 수 있다.
* page 디렉티브 태그에서는 반드시 `isELIgnored = "false"`로 설정해야 한다.

## 표현 언어의 형식
### `$(표현식 or 값)`

## 표현 언어에서 제공하는 내장 객체
### scope
* **pageScope** : JSP의 page와 같은 기능을 하고, page 영역에 바인딩 된 객체를 참조한다.
* **requestScope** : JSP의 request와 같은 기능을 하고, request에 바인딩 된 객체를 참조한다.
* **sessionScope** : JSP의 session과 같은 기능을 하고, session에 바인딩 된 객체를 참조한다.
* **applicationScope** : JSP의 application 같은 기능을 하고, application에 바인딩 된 객체를 참조한다.

### 요청 매개변수
* **param** : request.getParameter() 메서드를 호출한 것과 같으며 한 개의 값을 전달하는 요청 매개변수를 처리한다.
* **paramValues** : request.getParameterValues() 메서드를 호출한 것과 같으며 여러 개의 값을 전달하는 요청 매개변수를 처리한다.

### 헤더 값
* **header** : request.getHeader() 메서드를 호출한 것과 같으며 요청 헤더 이름의 정보를 단일 값으로 반환한다.

### JSP 내용
* **pageContext** : pageContext 객체를 참조할 때 사용한다.

## 표현언어로 여러가지 데이터 출력하기
```jsp
<%-- ELtest_01.jsp --%>
<%-- isELIgnored = "false" 표현 언어 기능 활성화 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 표현 언어 데이터 </title>
</head>
<body>
	<h1> 표현 언어로 여러가지 데이터 출력하기 </h1>
	<br>
	<h3>
	<%-- 숫자 출력 --%>
	\${ 100 } : ${ 100 }<br>
	
	<%-- 문자 출력  --%>
	\${ "안녕하세요" } : ${ "안녕하세요" }<br>
	
	<%-- 연산도 가능  --%>
	\${ 10 + 1 } : ${ 10 + 1 }<br>
	\${ "10" + 1 } : ${ "10" + 1 } <!-- 숫자 타입의 문자열과 숫자를 더하면 문자열이 자동으로 숫자로 변환된다. -->
	
	<%-- 에러:문자열과 숫자는 더할 수 없다. --%
	<%-- \${ "백" + 11 } : ${ "백" + 11 } --%>
	
	<%-- 에러:문자열끼리는 더할 수 없다. --%>
	<%-- \${ "hello" + "EL" } : ${ "hello" + "EL" } --%>
	</h3>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/116867870-bb019d00-ac48-11eb-8eab-f68f69022675.png)

## 산술 연산자 
```jsp
<%-- ELtest_02.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 표현 언어 연산자 </title>
</head>
<body>
	<h1> 표현 언어로 산술 연산자 </h1>
	<br>
	<h3>
	\${ 10 + 10 } : ${ 10 + 10 }<br>
	\${ 20 - 10 } : ${ 20 - 10 }<br>
	\${ 10 * 10 } : ${ 10 * 10 }<br>
	\${ 10 / 3 } : ${ 10 / 3 }<br>
	\${ 10 div 3 } : ${ 10 div 3 }<br>
	\${ 10 % 4 } : ${ 10 % 4 }<br>
	\${ 10 mod 4 } : ${ 10 mod 4 }<br>
	</h3>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/116867923-d2d92100-ac48-11eb-9d9e-9201d924bf71.png)

## 비교 연산자
```jsp
<%-- ELtest_03.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 표현 언어 연산자 </title>
</head>
<body>
	<h1> 표현 언어로 비교 연산자 </h1>
	<br>
	<h3>
	\${ 10 == 10 } : ${ 10 == 10 }<br>
	\${ 10 eq 10 } : ${ 10 eq 10 }<br>
	\${ "hello" == "hello" } : ${ "hello" == "hello" }<br>
	\${ "hello" eq "hello"} : ${ "hello" eq "hello"}<br><br>
	
	\${ 20 != 10 } : ${ 20 != 10 }<br>
	\${ 20 ne 10 } : ${ 20 ne 10}<br><br>
	
	\${ 10 < 10 } : ${ 10 < 10 }<br>
	\${ 10 lt 10 } : ${ 10 lt 10 }<br><br>
	
	\${ 20 > 3 } : ${ 20 > 3 }<br>
	\${ 20 gt 3 } : ${ 20 gt 3 }<br><br>
	
	\${ 10 <= 3 } : ${ 10 <= 3 }<br>
	\${ 10 le 3 } : ${ 10 le 3 }<br><br>
	
	\${ 10 >= 4 } : ${ 10 >= 4 }<br>
	\${ 10 ge 4 } : ${ 10 ge 4 }<br><br>
	</h3>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/116867981-ee442c00-ac48-11eb-8f04-18c774f69fb7.png)

## 논리 연산자
```jsp
<%-- ELtest_04.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 표현 언어 연산자 </title>
</head>
<body>
	<h1> 표현 언어로 논리 연산자 </h1>
	<br>
	<h3>
	\${ (10==10) && (20==20) } : ${ (10==10) && (20==20) }<br>
	\${ (10==10) and (20==20) } : ${ (10==10) and (20==20) }<br><br>
	
	\${ (10==10) || (20!=30) } : ${ (10==10) || (20!=30) }<br>
	\${ (10==10) or (20!=30) } : ${ (10==10) or (20!=30) }<br><br>
	
	\${ !(20==10) } : ${ !(20==10) }<br>
	\${ not(20==10) } : ${ not(20==10) }<br>
	</h3>
</body>
</html>
```
> ![image](https://user-images.githubusercontent.com/79209568/116868387-a8d42e80-ac49-11eb-8f71-0cc5c7a19457.png)
