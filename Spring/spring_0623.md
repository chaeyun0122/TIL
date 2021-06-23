# Spring MVC
## 프로젝트 구조 설정
### 폴더 생성
- main폴더에 webapp \> WEB-INF \> views 폴더를 생성  
  ![image](https://user-images.githubusercontent.com/79209568/123032828-a14b2d80-d421-11eb-9738-48f72ad0579f.png)

### pom.xml
- `<packaging>`을 **war**로 설정을 해서 웹프로젝트로 실행된 다는 것을 설정한다.
- dependency의 spring-framework를 **spring-webmvc**로 변경한다. 
- 서블릿 라이브러리와 jsb 라이브러리를 추가한다.
![image](https://user-images.githubusercontent.com/79209568/123037577-d65b7e00-d429-11eb-9166-d77b1c348e1f.png)

> ### 오류
> - 1. Window \> Show View \> Others..의 **Problems** 추가  
>   ![image](https://user-images.githubusercontent.com/79209568/123037831-54b82000-d42a-11eb-8f0b-f74afcbca6cb.png)
> - 2. 오류 확인 : 프로젝트 새로 고침을 해야한다.   
>   ![image](https://user-images.githubusercontent.com/79209568/123037900-71ecee80-d42a-11eb-8997-5c9133cd36d7.png)
> - 3. 프로젝트 오른쪽 클릭 \> Maven \> Update Project로 **프로젝트 새로고침** (웬만한 오류는 이렇게 해결 가능)  
>   ![image](https://user-images.githubusercontent.com/79209568/123038049-a95b9b00-d42a-11eb-961a-5956b27f6138.png)
> - 4. 두번째 오류 : web.xml이 없으므로 생성해줘야한다.  
>   ![image](https://user-images.githubusercontent.com/79209568/123038118-c7c19680-d42a-11eb-9662-cfae425dd829.png)
> - 5. 프로젝트 오른쪽 클릭 \> Java EE Tools \> Generate Deployment Descriptor Stub으로 **web.xml 생성**  
>   ![image](https://user-images.githubusercontent.com/79209568/123038211-ede73680-d42a-11eb-8e9a-75134a4918ed.png)


### 톰캣 연결
- build path에서 Add Library > Server Runtime   
  ![image](https://user-images.githubusercontent.com/79209568/123033271-73b2b400-d422-11eb-9ac6-86050e5355f5.png)
- 이전에 추가했던 서버 선택  
  ![image](https://user-images.githubusercontent.com/79209568/123038270-08211480-d42b-11eb-96b0-4026b069dbb2.png)


### spring-mvc.xml
- mvc 설정 태그
  - name space와 name space가 있는 곳
  ![image](https://user-images.githubusercontent.com/79209568/123033694-1ff49a80-d423-11eb-9d60-9e89623d93eb.png)
- mvc관련 어노테이션 식별  
  ![image](https://user-images.githubusercontent.com/79209568/123034223-16b7fd80-d424-11eb-8ef1-60f052d9c1de.png)

- 컨트롤러에서 선택된 jsp파일(뷰)를  찾아주는 역할 
  - 컨트롤러가 뷰에 해당되는 문자열을 반환하면 `prefix+반환문자열+suffix`
  - ex) return "hello";  → /WEB-INF/views/hello.jsp 이 jsp 파일을 찾음  
  ![image](https://user-images.githubusercontent.com/79209568/123034245-21729280-d424-11eb-9532-35f8738f1e6e.png)

### web.xml
- 프론트 컨트롤러 선언
  - 초기화할 때 읽어들일 설정 파일들을 지정
- 모든 요청을 프론트 컨트롤러로 매핑
- 요청과 응답에 적용될 필터를 등록 (해당 프로젝트에서는 utf-8로 바꿔주는 필터를 생성)
  > - filter
  >   - 서블릿과 동일한 개념이지만 동작하는 시점이 다르다.
  >   - 필터는 요청이 들어올 때 동작한다, 그리고 처리가 끝나서 응답을 할 때 동작한다.
- 필터를 적용할 요청 URL 매핑 
![image](https://user-images.githubusercontent.com/79209568/123036633-55e84d80-d428-11eb-8f70-0bc1ae956140.png)

### Spring-controller.xml
- spring 빈 생성할 xml 파일 준비
  ![image](https://user-images.githubusercontent.com/79209568/123038534-8978a700-d42b-11eb-8e0a-b5deed918a51.png)

## 기본 동작 실행 확인
#### HelloController.java
- exam 패키지에 HelloController 클래스 생성  
  
  ![image](https://user-images.githubusercontent.com/79209568/123038934-3a7f4180-d42c-11eb-9b4b-24be42f2270e.png)
#### hello.jsp
- views 폴더에 hello.jsp 파일 생성  
  
  ![image](https://user-images.githubusercontent.com/79209568/123038984-571b7980-d42c-11eb-8e02-0e60c264eee4.png)
#### spring-controller.xml
- spring-controller.xml에 빈 등록  
  
  ![image](https://user-images.githubusercontent.com/79209568/123039063-7ca88300-d42c-11eb-85c7-3e55f2942016.png)
#### 실행
- Run On Server로 실행  
  
  ![image](https://user-images.githubusercontent.com/79209568/123039150-93e77080-d42c-11eb-8c4c-8b2d4c3b9224.png)

## Method 처리
- method가 GET 혹은 POST일 때 처리하는 방법
### 기본 실습 - get, post 신경 X
#### ExamController.java
- exam 패키지에 ExamController 클래스 생성
- **exam1()**
  - exam1.jsp를 출력  

  ![image](https://user-images.githubusercontent.com/79209568/123148443-33dbe300-d49b-11eb-8310-bbc956a83b50.png)
- **exam2()**
  - `Model` : jsp 파일에 값을 전달하기 위해 잠시 저장한다.
  - exam2.jsp를 출력
  
  ![image](https://user-images.githubusercontent.com/79209568/123149258-093e5a00-d49c-11eb-863c-bab0075ba3f3.png)

#### exam1.jsp
- name, age 값이 POST로 /ex/exam2 url로 넘어감  
  
  ![image](https://user-images.githubusercontent.com/79209568/123148414-2b83a800-d49b-11eb-8f19-88db38166282.png)

#### exam2.jsp
- name, age 값을 출력  
  
  ![image](https://user-images.githubusercontent.com/79209568/123149532-54586d00-d49c-11eb-8f77-f357cb590631.png)

#### 실행
- `/ex/exam1` 요청을 하면 GET으로 요청이 들어온다. name과 age를 입력 후 전송
  
  ![image](https://user-images.githubusercontent.com/79209568/123149742-8cf84680-d49c-11eb-9560-c25d94186c02.png)
- 전송을 누르면 `/ex/exam2`로 POST 요청을 하고, model에 설정한 name, age 속성 값들을 출력한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/123149970-d0eb4b80-d49c-11eb-961e-cb9acfcf9686.png)

### method 설정으로 같은 요청 다른 처리
- exam1.jsp, exam2.jsp를 똑같이 복사한 후 exam3.jsp, exam4.jsp로 이름 변경
- ExamController.java의 exam1(), exam2() 메서드 또한 복사후 exam3(), exam4()로 이름 변경

#### ExamController.java
- 매핑을 같은 /exam3로 변경해보자.  
  
  ![image](https://user-images.githubusercontent.com/79209568/123150479-625abd80-d49d-11eb-9bbc-a70f6dc27716.png)
- 오류가 난다. 하나의 요청에 두 가지 메서드가 매핑되었기 때문에 서블릿이 어떤 메서드를 선택해야할 지 모르는 상황
  
  ![image](https://user-images.githubusercontent.com/79209568/123150639-91712f00-d49d-11eb-99c0-76499c116600.png)
- RequestMapping 어노테이션에 value는 요청 url, method에는 요청 방식을 넣어주면 하나의 요청에 두 메서드를 매핑시킬 수 있다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/123150905-dbf2ab80-d49d-11eb-87d9-04e0cdb288be.png)
#### 실행
- `/ex/exam3` 요청을 하면 GET으로 요청이 들어온다.
  
  ![image](https://user-images.githubusercontent.com/79209568/123151009-f9277a00-d49d-11eb-9139-e837925dd63f.png)
- 같은 `/ex/exam3` url로 넘어가지만 method가 POST인 요청이 들어왔기 때문에 exam4 메서드가 실행된다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/123151238-3986f800-d49e-11eb-8279-3b40012d0521.png)
