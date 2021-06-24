## 준비
- pom.xml에 spring-jdbc, c3p0, common-dbcp, ojdbc6, jstl 라이브러리 추가  
  ![image](https://user-images.githubusercontent.com/79209568/123199692-53e6c300-d4ea-11eb-84fb-876cff1ad563.png)
  ![image](https://user-images.githubusercontent.com/79209568/123199703-5a753a80-d4ea-11eb-8db5-e7bb91195914.png)
- spring-mvc.xml을 복사해서 spring-member로 만들어 줌  
  ![image](https://user-images.githubusercontent.com/79209568/123200057-ff901300-d4ea-11eb-932f-5d82dd20177d.png)
- web.xml에 spring-member.xml 설정파일 추가  
  ![image](https://user-images.githubusercontent.com/79209568/123200141-26e6e000-d4eb-11eb-979d-3f87cea2898e.png)

## 이제 mvc가 어떻게 돌아가는지 알아보자
### handleStep1()
- **step1 함수 : 약관 동의 페이지를 리턴** 
#### RegisterController.java
- controller 패키지의 RegisterController 클래스
  
  ![image](https://user-images.githubusercontent.com/79209568/123200618-0a977300-d4ec-11eb-93e6-ac08e0c3ac68.png)
#### step1.jsp : 뷰생성
  ![image](https://user-images.githubusercontent.com/79209568/123200739-4cc0b480-d4ec-11eb-877d-7cda2ed3003c.png)
#### spring-member.xml - 빈 등록
  ![image](https://user-images.githubusercontent.com/79209568/123200792-62ce7500-d4ec-11eb-8794-0aa7c95d3358.png)
#### 실행  
- /register/step1 요청  
  
  ![image](https://user-images.githubusercontent.com/79209568/123200827-737eeb00-d4ec-11eb-9477-2f6564710628.png)
- 약관 동의를 누르고 전송을 누르면, /register/step2로 넘어가고(아직 페이지 없어서 404오류) agree 값이 true로 넘어간다.
  
  ![image](https://user-images.githubusercontent.com/79209568/123200983-cb1d5680-d4ec-11eb-9d07-ef1b118ed97c.png)

### handleStep2()
- **step2 함수 : step1.jsp에서 약관 동의(agree=true)면 step2.jsp-회원 정보 입력 페이지를 응답,  
 동의 하지 않으면(agree=null) step1.jsp를 리다이렉트**
#### RegisterController.java
#### 방법 1
- method를 POST로 설정해서 GET으로 /register/step2 요청이 들어오면 에러가 나도록 한다.  

  ![image](https://user-images.githubusercontent.com/79209568/123205258-85648c00-d4f4-11eb-925f-16c60e61235c.png)

  > - GET으로 요청이 들어오면 에러화면이 뜨는 게 아닌 /register/step1으로 redirect 하도록 step2 함수를 오버로딩한다.  
  >  
  >  ![image](https://user-images.githubusercontent.com/79209568/123205305-9a411f80-d4f4-11eb-890c-9c8ea1718832.png)

#### 방법 2 : @RequestParam 사용
> #### @@RequestParam 속성
> - value: 요청 파라미터의 이름
> - defaultValue: 요청 파라미터가 없는 경우 사용할 기본 값
> - required="true | false": 파라미터 값의 필수 여부  
  
- agree 파라미터의 값을 받아와서 Boolean의 agree 에 넣는다. 만약 값이 없으면 false를 디폴트 값으로 한다는 뜻
  - agree 값이 없으면 step1.jsp, 있으면 step2.jsp 요청  
  
  ![image](https://user-images.githubusercontent.com/79209568/123205711-5b5f9980-d4f5-11eb-80f4-9d10334d463e.png)

#### step2.jsp : 뷰 생성
  ![image](https://user-images.githubusercontent.com/79209568/123201578-e76dc300-d4ed-11eb-8eee-c750cda77eb9.png)

### handleStep3()
- **step3 함수 : 회원 등록하는 함수. 등록이 완료되면 회원 가입을 축하한다는 페이지를 리턴**

#### RegisterController.java
##### 의존 객체 선언
![image](https://user-images.githubusercontent.com/79209568/123206205-446d7700-d4f6-11eb-972e-d570475d18ef.png)

##### 커맨드 객체를 사용한다. 
> - Command 객체 : 명령에 사용될 값이 저장되는 객체
>  - 파라미터를 일일히 선언하는 것은 불편하다.
>  - 요청 파라미터에서 파라미터 이름에 해당되는 setter를 찾아 값을 설정한다.  
>    
>  ```
>  ex) 파라미터 이름 : name
>  커맨드객체.setName(req.getParameter("name")) 과 같은 동작
>     = 파라미터 이름이 name인 것을 가져와서 커맨드 객체에 set 시켜준다.
>  ```

- RegisterRequest는 가입할 때의 요청에 필요한 정보들을 담아주는 클래스다. 이것으로 만든 객체가 **커맨드 객체**다. 
- 예를 들어 step2에서 해당 폼 데이터가 넘어왔을 때,  
  
  ![image](https://user-images.githubusercontent.com/79209568/123207510-74b61500-d4f8-11eb-9ae2-4ca8a4914245.png)
- 그리고 이 폼데이터와 함께 **/register/step3의 요청이 POST로 들어올 때**, 각각 email, name, password, confirmPassword의 이름의 **setter**를 알아서 찾는다.
  - setEmail, setName, setPassword, setConfirmPassword 
- RegisterRequest 클래스의 커맨드 객체 regReq에 set해준다.
- 세팅 된 regReq를 regist해주면서 step3.jsp를 응답한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/123208171-91068180-d4f9-11eb-8a7c-79a21001358e.png)

#### step3.jsp
![image](https://user-images.githubusercontent.com/79209568/123208489-26097a80-d4fa-11eb-9524-ef92891df7f7.png)

> #### 알 수 없는 에러
> - 전체 선택, 잘라내기, 저장, 붙여넣기, 저장
