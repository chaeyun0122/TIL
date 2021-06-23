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

## 실행
- exam 패키지에 HelloController 클래스 생성  
  ![image](https://user-images.githubusercontent.com/79209568/123038934-3a7f4180-d42c-11eb-9b4b-24be42f2270e.png)
- views 폴더에 hello.jsp 파일 생성  
  ![image](https://user-images.githubusercontent.com/79209568/123038984-571b7980-d42c-11eb-8e02-0e60c264eee4.png)
- spring-controller.xml에 빈 등록  
  ![image](https://user-images.githubusercontent.com/79209568/123039063-7ca88300-d42c-11eb-85c7-3e55f2942016.png)
- Run On Server로 실행  
  ![image](https://user-images.githubusercontent.com/79209568/123039150-93e77080-d42c-11eb-8c4c-8b2d4c3b9224.png)

