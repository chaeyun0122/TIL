# Servlet
* 자바 언어를 기반으로 하는 웹 프로그래밍 기술
* 자바 클래스 안에 HTML 태그를 포함할 수 있다.
* (반대로 HTML코드 안에 자바 코드를 쓰는 것이 JSP)

# Servlet 생성 과정
1. 사용자 정의 서블릿 클래스 만들기
2. 서블릿 생명주기 메서드 구현
3. 서블릿 매핑 작업
4. 웹브라우저에서 서블릿 매핑 이름으로 요청하기

## 사용자 정의 서블릿 클래스 만들기
* 프로젝트 추가 시 (next로 넘어가서) Web Module 에서 `Generate web.xml deployment descriptor` 항목을 체크한 후 생성한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/115226273-034d9500-a14a-11eb-83ca-1714597f93df.png)
* `Java Resouces > src`에서 왼쪽 클릭 후 `New > Suvlet`을 눌러 패키지와 클래스 이름을 작성한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/115226467-3d1e9b80-a14a-11eb-8d16-c4f60ca18486.png)
* doGet, doPost 메서드만 남기고 모두 지워준다.

> ## tomcat의 servlet-api.jar 설정
> * 서블릿 API는 톰캣의 servlet-api.jar 라이브러리로 제공되므로 클래스 패스를 설정해야 사용할 수 있다.
> * 프로젝트 왼쪽 클릭 : `build path > Configure build path > Library 탭 > Add External jar`
> * servlet-api.jar의 위치 : tomcat 설치 폴더 `/lib/servlet-api.jar`

## 서블릿 생명주기 메서드 구현
* init(), destroy()는 생략가능하나 doGet(). doPost() 반드시 구현 해야한다.
### 초기화 : init()
- 서블릿 요청 시 맨 처음 한 번만 호출된다.

### 작업 수행 : doGet(), doPost()
  - 서블릿 요칭 시 매번 호출된다.
  - 실제로 클라이언트가 요청하는 작업을 수행한다.
#### 서블릿 요청과 응답 수행 API
  - 요청 : HttpServletRequest request
    - 웹 브라우저에서 전송한 정보를 tomcat 컨테이너가 HttpServletRequest 객체를 생성한 후 doGet() 으로 넘겨 준다.
  - 응답 : HttpServletResponse response

### 종료 : destroy()
  - java 코드가 수정이 되었을 때 서블릿의 마무리 작업을 수행한다.

### 전체 코드
```java
public class LifeCycle extends HttpServlet {
  
	// 최초 요청시 한번만 실행
	public void init(ServletConfig config) throws SecurityException {
		System.out.println("- init() run -");
	}
	
	// 파일이 수정되면 실행되고, 다시 처음부터 동작
	public void destroy() {
		System.out.println("- destroy() run -");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("- doget() run -");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
```

## 서블릿 매핑 작업
* 프로젝트 web.xml 에서 설정
* \<servlet> 태그와 \<servlet-mapping> 태그를 이용 
### \<servlet> 태그
* 브라우저에서 요청하는 매핑 이름에 대해서 실제로 실행하는 서블릿 클래스를 설정하는 태그
* \<servlet> 항목을 보면 컨테이너가 관리하는 웹 애플리케이션에는 어떤 것들이 있는지 알 수 있다.

#### \<servlet-name> 태그
* servlet-name 태그는 servlet 태그와 servlet-mapping 태그를 연결시켜 준다.
* servlet-mapping 태그의 servlet-name 태그와 값이 동일하다.

#### \<servlet-class> 태그
* 브라우저에서 요청하는 매핑 이름에 대해서 실제로 기능을 수행하는 서블릿 클래스를 설정한다.

### \<servlet-mapping> 태그
* 런타임 시 요청이 들어오면 컨테이너는 <serlet-mapping> 항목을 검색한다.

#### \<servlet-name> 태그
#### \<url-pattern> 태그
* 웹 브라우저에서 요청하는 매핑 이름
```xml
<servlet>
	<servlet-name>ex01</servlet-name>
	<servlet-class>ex01.LifeCycle</servlet-class>
</servlet>

<servlet-mapping>
  	<servlet-name>ex01</servlet-name>
  	<url-pattern>/life</url-pattern>
</servlet-mapping>
```

## Servlet 실행
1. 서버 실행
2. 브라우저에 url 추가 후 확인
    - `http://주소:port/프로젝트명/서블릿url`
    - ex) `http://localhost:8080/P06_Servlet/life`
  ![image](https://user-images.githubusercontent.com/79209568/115235495-3a757380-a155-11eb-960d-683167a77d41.png)


# 실습
## Parameter
### WebContent에 html 파일 생성
![image](https://user-images.githubusercontent.com/79209568/115236465-5d545780-a156-11eb-95f5-fa83a0e55bca.png)
```html
<!-- parameter.html -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>parameter</title>
</head>
<body>
	<!-- 
		보내기 버튼 클릭 시 <form> 태그의 action 속성에 지정한 servlet 이나 jsp로 name=value를 전송한다.
		입력된 데이터를 서블릿 매핑 이름 paramServlet인 서블릿으로 전송
	 -->
	<form action="http://localhost:8080/P06_Servlet/ParamServlet" method="get">
		<table border="1">
			<tr>
				<td width="700px" align="center"> 이 름 </td>
				<td><input type="text" id="name" name="username"></td><br>
			</tr>
			<tr>
				<td width="700px" align="center"> 나 이 </td>
				<td><input type="text" id="age" name="userage"></td><br>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="보내기"> &nbsp; <input type="reset" value="취소">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
```

### src에 새 서블릿 생성
![image](https://user-images.githubusercontent.com/79209568/115239313-87f3df80-a159-11eb-9bcd-171105d48ffb.png)
#### doGet메서드에 작성
#### 1. 인코딩 설정
```java
request.setCharacterEncoding("utf-8");
response.setContentType("text/html; charset=utf-8");
```
#### 2. 데이터
```java
String name = request.getParameter("username"); // getPrameter() 메서드를 사용해서 <input> 태그의 name 속성값으로 전송된 value를 가져온다.
String strAge = request.getParameter("userage");
int age = Integer.parseInt(strAge);
// int age = Integer.parseInt(request.getParameter("userage")); // 한번에도 가능
```
#### 3. 응답
```java
PrintWriter out = response.getWriter();
out.println("<html>");
out.println("<head>");
out.println("<title>PramServlet</title>");
out.println("</head>");
out.println("<body>");
out.println("<br><br>");
out.println(name + "님의 나이 : " + age + "세 <br><br>");
if (age > 19)
	out.println("성인");
else
	out.println("미성년자");
out.println("</body>");
out.println("</html>");
```
#### 전체 코드
```java
package ex03;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParamServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 인코딩 설정
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		// 1. 데이터
		String name = request.getParameter("username");
		String strAge = request.getParameter("userage");
		int age = Integer.parseInt(strAge);
		// int age = Integer.parseInt(request.getParameter("userage"));
		
		// 2. 응답
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>PramServlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<br><br>");
		out.println(name + "님의 나이 : " + age + "세 <br><br>");
		if (age > 19)
			out.println("성인");
		else
			out.println("미성년자");
		out.println("</body>");
		out.println("</html>");
	}
}
```
### 실행
* 이름과 나이를 적고 보내기 클릭  
  
  ![image](https://user-images.githubusercontent.com/79209568/115240325-98588a00-a15a-11eb-8e95-fe9e041f83fa.png)
* 해당 페이지로 이동하여 화면에 출력됨
  
  ![image](https://user-images.githubusercontent.com/79209568/115240475-bc1bd000-a15a-11eb-93d6-671b7575418d.png)

## Member
### WebContent에 html 파일 생성
```html
<!-- member.html -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<br>
	<form>
		<table border="1">
			<tr>
				<td> 이 름 </td>
				<td><input type="text" name="name" size="20"></td>
			</tr>
			<tr>
				<td> 성 별 </td>
				<td>
					<input type="radio" name="gender" value="남성" checked> 남
					<input type="radio" name="gender" value="여성"> 여
				</td>
			</tr>
			<tr>
				<td> 취 미 </td>
				<td>
					<input type="checkbox" name="hobby" value="독서"> 독서
					<input type="checkbox" name="hobby" value="영화"> 영화
					<input type="checkbox" name="hobby" value="음악"> 음악
					<input type="checkbox" name="hobby" value="게임"> 게임
					<input type="checkbox" name="hobby" value="쇼핑"> 쇼핑
				</td>
			</tr>
			<tr>
				<td> 과 목 </td>
				<td>
					<select name="subject" size="5" multiple>
						<option value="JAVA"> JAVA </option>
						<option value="JSP"> JSP </option>
						<option value="Spring"> Spring </option>
						<option value="JQuery"> JQuery </option>
						<option value="Servlet"> Servlet </option>
					</select>
				</td>
			</tr>
			<tr>
				<td> 배경색 </td>
				<td>
					<select name="color">
						<option value="red"> 빨 강 </option>
						<option value="green"> 초 록 </option>
						<option value="blue"> 파 랑 </option>
						<option value="magenta"> 보 라 </option>
						<option value="cyan"> 하 늘 </option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="보내기"> &nbsp; <input type="reset" value="취소">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
```

### src에 새 서블릿 생성
![image](https://user-images.githubusercontent.com/79209568/115237563-a9ec6280-a157-11eb-90a3-165045b6920c.png)  
  
#### 매핑
* html에서 form의 action에 작성한 url 명(memberServlet)을 클래스 위에 `@WebServlet`으로 작성해주면 따로 매핑 작업을 하지 않아도 된다.
```java
@WebServlet("/memberServlet") // 매핑 과정을 거치지 않아도 실행 가능
```
#### 1. 데이터
```java
String name = request.getParameter("name");
String gender = request.getParameter("gender");
String[] aHobby = request.getParameterValues("hobby");  // 여러 개의 값이 넘어오므로 배열로 처리, getParameterValues로 받음
String[] aSubject = request.getParameterValues("subject");
String coloer = request.getParameter("color");

// 배열의 원소들을 하나의 문자열로 이어 붙이기
String hobby = "";
if (aHobby != null) {
	for (int i=0; i<aHobby.length; i++) {
		if (aHobby[i] != null) {
			hobby += aHobby[i] + " ";
		}
	}
}

String subject = "";
if (aSubject != null) {
	for (int i=0; i<aSubject.length; i++) {
		if (aSubject[i] != null) {
			subject += aSubject[i] + " ";
		}
	}
}
```

#### 2. 응답
```java
response.setContentType("text/html; charset=utf-8");
PrintWriter out = response.getWriter();
out.println("<html>");
out.println("<head>");
out.println("<title> Member </title>");
out.println("<style>");
out.println("body { background-color: " + color + "};");
out.println("li { font-size : 20px }");
out.println("</style>");
out.println("</head>");
out.println("<body>");
out.println("<ul>");
out.println("<li> 이 름 : " + name + "</li>");
out.println("<li> 성 별 : " + gender + "</li>");
out.println("<li> 취 미 : " + hobby + "</li>");
out.println("<li> 과 목 : " + subject + "</li>");
out.println("<li> 색 상 : " + color + "</li>");
out.println("</ul>");
out.println("<a href='javascript:history.back();'> 뒤로 </a>");

out.println("<br><br>");
out.println("</body>");
out.println("</html>");
```
#### 전체 코드
```java
package ex04;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/memberServlet") // 매핑 과정을 거치지 않아도 실행 가능
public class Member extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 데이터
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String[] aHobby = request.getParameterValues("hobby");  // 여러 개의 값이 넘어오므로 배열로 처리, getParameterValues로 받음
		String[] aSubject = request.getParameterValues("subject");
		String color = request.getParameter("color");
		
		// 배열의 원소들을 하나의 문자열로 이어 붙이기
		String hobby = "";
		if (aHobby != null) {
			for (int i=0; i<aHobby.length; i++) {
				if (aHobby[i] != null) {
					hobby += aHobby[i] + " ";
				}
			}
		}
		
		String subject = "";
		if (aSubject != null) {
			for (int i=0; i<aSubject.length; i++) {
				if (aSubject[i] != null) {
					subject += aSubject[i] + " ";
				}
			}
		}
		
		// 2. 응답
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Member </title>");
		out.println("<style>");
		out.println("body { background-color: " + color + "};");
		out.println("li { font-size : 20px }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<ul>");
		out.println("<li> 이 름 : " + name + "</li>");
		out.println("<li> 성 별 : " + gender + "</li>");
		out.println("<li> 취 미 : " + hobby + "</li>");
		out.println("<li> 과 목 : " + subject + "</li>");
		out.println("<li> 색 상 : " + color + "</li>");
		out.println("</ul>");
		out.println("<a href='javascript:history.back();'> 뒤로 </a>");
		
		out.println("<br><br>");
		out.println("</body>");
		out.println("</html>");
		
	}

}

```
### 실행
* 정보를 입력 후 보내기 클릭  
  
  ![image](https://user-images.githubusercontent.com/79209568/115382509-c350e580-a20f-11eb-8464-899b7b52b62b.png)
* 해당 페이지로 이동하여 화면에 출력됨
  
  ![image](https://user-images.githubusercontent.com/79209568/115382539-cb108a00-a20f-11eb-8a7c-2fb569f31d34.png)

