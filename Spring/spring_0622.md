# MVC
- Model – 비즈니스 영역의 상태 정보를 처리  
  ![image](https://user-images.githubusercontent.com/79209568/123109182-fb75de00-d475-11eb-9e55-77a7377c23ce.png)
- View – 사용자에게 보여질 결과 화면 처리
- Controller – 사용자의 입력 및 흐름제어  
  ![image](https://user-images.githubusercontent.com/79209568/123109110-ec8f2b80-d475-11eb-8a13-98d4d9ea0fd6.png)

## 실습
> 프로젝트 : examJsp02 (Dynamic Web Project)

### 컨트롤러 생성
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

#### CommandHandler.java
- 인터페이스 생성
![image](https://user-images.githubusercontent.com/79209568/123106758-e009d380-d473-11eb-8243-1345ff3a71b3.png)

