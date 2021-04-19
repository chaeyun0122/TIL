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
#### \<url-pattern>
* 웹 브라우저에서 요청하는 매핑 이름


## Servlet 실행
1. 서버 실행
2. 브라우저에 url 추가 후 확인
    - `http://주소:port/프로젝트명/서블릿url`
    - ex) `http://localhost:8080/P06_Servlet/life`



클라이언트에서 http request를 통해 요청이 들어옴 tomcap서버안에 web container가 있음 url주소 안에 클래스명을 못씀 url에서 사용할 이름을 작성해줘야하는데 맵핑을 통해 해당 url로 이동할 수 있도록 이름을 
