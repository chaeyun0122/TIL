# MVC
- **Model** – 비즈니스 영역의 상태 정보를 처리  
  
  ![image](https://user-images.githubusercontent.com/79209568/123109182-fb75de00-d475-11eb-9e55-77a7377c23ce.png)
- **View** – 사용자에게 보여질 결과 화면 처리
- **Controller** – 사용자의 입력 및 흐름제어  
  
  ![image](https://user-images.githubusercontent.com/79209568/123109110-ec8f2b80-d475-11eb-8a13-98d4d9ea0fd6.png)

## 실습
> 프로젝트 : examJsp02 (Dynamic Web Project) ([👉project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/examJsp02))

### 간단한 컨트롤러 생성
#### SimpleController.java
- controller 패키지의 SimpleController 클래스 생성
- 클라이언트의 요청을 받아서 분석하고 처리함
- 처리된 결과는 JSP 페이지를 선택하여 forwarding으로 응답(redirect도 가능함)
- **1. 요청을 받음**
  - get이나 post 어떤 요청이 들어와도 processRequest 함수로 넘어간다.  
    ![image](https://user-images.githubusercontent.com/79209568/123110901-78558780-d477-11eb-9887-79e656379891.png)
- **2. 요청 처리 : processRequest**
  - 2-1. 요청 분석 : type 파라미터가 무엇인지에 따라 어떻게 처리할지 결정한다
  - 2-2. type에 따른 요청 처리 결과를 준비(처리하는 로직 부분이기 때문에 모델 부분)
    - type이 null이거나 "greeting"이면 처리 결과는 "안녕하세요"
    - type이 "date"이면 처리 결과는 현재 시각이다.
    - type이 그 외의 값이면 처리 결과는 "Invalid Type!!"
  - 2-3. 처리 결과를 request 또는 session과 같은 객체에 속성으로 담기(뷰에 전달할 데이터)
    - 전달할 값이 없는 경우 안할 수 도 있고, redirect응답을 줄 수도 있음
    - request 객체의 setAttribute로 요청에 속성으로 저장한다.
  - 2-4. 적절한 뷰로 JSP파일을 선택하여 forwarding
    - redirect응답을 줄 수도 있음  
  
  ![image](https://user-images.githubusercontent.com/79209568/123112864-0c741e80-d479-11eb-9361-570fa49a15b6.png)

### 뷰 생성
- WebContent \> WEB-INF에 뷰들을 생성할 `views` 폴더를 생성한다.
#### simpleView.jsp
- 결과를 표현해주는 역할  
  ![image](https://user-images.githubusercontent.com/79209568/123113549-9ae8a000-d479-11eb-896a-32e46d3cce5e.png)

### 서블릿 등록
#### web.xml
- `<servlet>` : 웹 컨테이너에 서블릿 생성 선언(서블릿 등록)
  - `<servlet-name>` : 서블릿의 이름
  - `<servlet-class>` : 서블릿 클래스 (controller 패키지의 SimpleController 클래스)
- `<servlet-mapping>` : 요청 URL과 처리할 서블릿을 매핑 
  - `<servlet-name>` : 등록한 서블릿의 이름
  - `<url-pattern>` : 해당 url 요청이 들어왔을 때 해당 서블릿을 사용한다.(/simple url이 들어오면 SimpleController를 사용)
![image](https://user-images.githubusercontent.com/79209568/123114302-2eba6c00-d47a-11eb-951d-d6d4d4a27082.png)

### 서버 실행 결과
#### `/simple`로 요청 된 경우
![image](https://user-images.githubusercontent.com/79209568/123115518-20208480-d47b-11eb-945f-f86cd8b2e851.png)

#### `/simple?type=greeting`로 요청 된 경우
![image](https://user-images.githubusercontent.com/79209568/123115717-43e3ca80-d47b-11eb-9e68-5865d9806439.png)

#### `/simple?type=date`로 요청 된 경우
![image](https://user-images.githubusercontent.com/79209568/123124933-0daa4900-d483-11eb-801b-441395a74540.png)

#### `/simple?type=test`로 요청 된 경우
![image](https://user-images.githubusercontent.com/79209568/123125305-5a8e1f80-d483-11eb-9d21-bac58857237c.png)

## 객체를 사용하는 컨트롤러 생성
### 인터페이스 생성
#### CommandHander.java
```java
package cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandHandler {
	public String process(HttpServletRequest req, HttpServletResponse resp)
	throws Throwable;
}
```

### 컨트롤러 생성
#### ControllerUsingInstance.java
- **핸들러 객체를 담는 컬렉션** (컨테이너 같은 개념)  
  
  ![image](https://user-images.githubusercontent.com/79209568/123140831-b6ac7000-d492-11eb-857b-9c27f2250355.png)

- **서블릿 생성 시 초기화 메서드 활용**
  - `init()` : 서블릿(객체)가 생성되면 첫 번째로 Tomcat이 호출하는 함수
  - 명령에 따라 동작할 핸들러들을 등록  
    
    ![image](https://user-images.githubusercontent.com/79209568/123140790-a98f8100-d492-11eb-83f8-e66177eb64f7.png)

- **1. 요청을 받음**
  - get이나 post 어떤 요청이 들어와도 processRequest 함수로 넘어간다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/123140920-ce83f400-d492-11eb-9b9e-ac7b7b7d66d7.png)
- **2. 요청을 처리 - processRequest**
  - 요청 분석 : cmd 값에 따라 명령이 달라진다.  
   
    ![image](https://user-images.githubusercontent.com/79209568/123142417-5dddd700-d494-11eb-86af-35b0a7a48b4b.png)

  - 명령에 동작할 핸들러 객체 가져오기  
    
    ![image](https://user-images.githubusercontent.com/79209568/123142446-68986c00-d494-11eb-9ed8-c6c4eb30db92.png)

  - 핸들러 객체가 null값이면 NullHandler 연결, 핸들러 객체의 값이 있을 경우 해당 값의 비즈니스 로직을 처리(Model 사용)  
    
    ![image](https://user-images.githubusercontent.com/79209568/123142493-74842e00-d494-11eb-8a2e-03fc88395c5c.png)

  - 선택된 뷰로 포워드하여 응답
    
    ![image](https://user-images.githubusercontent.com/79209568/123142520-7c43d280-d494-11eb-98a0-d2c4fa538370.png)

  - processRequest 전체 코드  
    
    ![image](https://user-images.githubusercontent.com/79209568/123142639-9f6e8200-d494-11eb-8abf-3110eef96112.png)
    
### 모델 생성
#### NullHandler.java
- handler 객체가 null일 경우 해당 모델을 실행
  ```java
  package cmd;

  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;

  public class NullHandler implements CommandHandler{

    @Override
    public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable     {
      return "/WEB-INF/views/null.jsp";
    }
  }
  ```
#### HelloHandler.java
```java
package cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cmd.CommandHandler;

public class HelloHandler implements CommandHandler{
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		req.setAttribute("hello", "안녕하세요");
		return "/WEB-INF/views/hello.jsp";
	}
	
}
```

### 뷰 생성
#### null.jsp
![image](https://user-images.githubusercontent.com/79209568/123144416-9e3e5480-d496-11eb-8a7b-abf4f0cad690.png)

#### hello.jsp
![image](https://user-images.githubusercontent.com/79209568/123144508-b0b88e00-d496-11eb-8f56-5f625b9c49e8.png)

### 서블릿 등록
![image](https://user-images.githubusercontent.com/79209568/123144246-73540080-d496-11eb-9fe8-9a00c45290c5.png)

### 서버 실행 결과
#### `/conui`로 요청 된 경우
- NullHander 객체를 통해 null.jsp 응답  
  
  ![image](https://user-images.githubusercontent.com/79209568/123144691-e9f0fe00-d496-11eb-8075-5526d3d06ad2.png)

#### `/conui?cmd=`로 요청 된 경우
- NullHander 객체를 통해 null.jsp 응답  
  
  ![image](https://user-images.githubusercontent.com/79209568/123144809-0ee57100-d497-11eb-8b95-d0006e0a44b4.png)

#### `conui?cmd=hello`로 요청 된 경우
- HelloHandler 객체를 통해 hello.jsp 응답
  
  ![image](https://user-images.githubusercontent.com/79209568/123145005-49e7a480-d497-11eb-95af-e682345b4105.png)

## 실습 2 - 사칙연산
### 컨트롤러
- 핸들러 등록  

![image](https://user-images.githubusercontent.com/79209568/123146448-d5ae0080-d498-11eb-90ff-454da3ead930.png)

### 객체(모델)
#### AddHandler.java
- method가 GET일 때 addForm.jsp로 뷰 출력
- method가 POST일 때 n1, n2 파라미터를 받아와서 더한 후 setAttribute로 request 속성을 설정해준다. 그리고 addResult.jsp로 뷰 출력 (다른 jsp 파일로 넘기려면 setAttribute를 해줘야한다.)  

![image](https://user-images.githubusercontent.com/79209568/123145354-b662a380-d497-11eb-933c-8b4a0e475682.png)

### 뷰
#### addForm.jsp
![image](https://user-images.githubusercontent.com/79209568/123146202-98497300-d498-11eb-82e0-d27218bab681.png)

#### addResult.jsp
![image](https://user-images.githubusercontent.com/79209568/123146276-a8f9e900-d498-11eb-8e14-f8086421cdaf.png)

### 서버 실행 결과
![image](https://user-images.githubusercontent.com/79209568/123146552-fc6c3700-d498-11eb-843d-6429d89272c4.png)
![image](https://user-images.githubusercontent.com/79209568/123146596-068e3580-d499-11eb-90b5-d71db5100a74.png)

### 뺄셈, 나눗셈, 곱셈도 마찬가지 (프로젝트 코드 참조)
