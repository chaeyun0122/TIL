## 국제화 기능 추가
- : 언어를 선택하면 해당언어로 변경
- resources에 `message > label.properties` 생성  
  ![image](https://user-images.githubusercontent.com/79209568/123368043-02a40580-d5b6-11eb-8b21-8412c463a1d4.png)

- label.properties의 인코딩을 utf-8로 변경해야 한글이 꺠지지 않는다. (파일 우클릭 \> Properties메뉴)  
  ![image](https://user-images.githubusercontent.com/79209568/123370262-531d6200-d5ba-11eb-8214-b26e3f35e75e.png)

- 하드코딩된 문자들을 식별할 수 있도록 label.properies에 적어준다.  
  ![image](https://user-images.githubusercontent.com/79209568/123370275-5add0680-d5ba-11eb-80b6-e662ed2921c4.png)

- spring이 식별할 수 있도록 bean등록을 해준다.  
  - ResourceBundleLMessageSource 클래스에 있다.
  - basenames라는 이름으로 property를 의존주입해준다. list 내에 value값은 여러 개 작성할 수 있다. (언어마다 다르게 작성 가능)
  - 생성해 둔 message 폴더의 label.properties를 value로 등록한다.
  - 인코딩 방식을 utf-8로 세팅해서 적용한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/123370295-64ff0500-d5ba-11eb-87eb-b6d6570fc26b.png)

- 하드코딩된 것들을 모두 spring:message로 바꿔준다.
  - step1  
    
    ![image](https://user-images.githubusercontent.com/79209568/123370647-1e5dda80-d5bb-11eb-9801-1a28fe99185a.png)
  - step2  
    
    ![image](https://user-images.githubusercontent.com/79209568/123370718-42212080-d5bb-11eb-9265-62d44ca4e2e6.png)

  - step3  
    - register.done의 경우 형식 내에 argument가 있어야하므로 arguments 속성에 값을 써줘야한다. (argument는 더 있을 경우 뒤에 콤마로 이어나가면 된다.)
      
      > ![image](https://user-images.githubusercontent.com/79209568/123370880-988e5f00-d5bb-11eb-9bb5-72de28a94f2d.png)

    ![image](https://user-images.githubusercontent.com/79209568/123370756-57964a80-d5bb-11eb-9336-ca302eda79ee.png)
- **서버 실행 결과**
  - spring:message로 설정한 대로 잘 출력 된다.  
    
    ![image](https://user-images.githubusercontent.com/79209568/123371094-0044aa00-d5bc-11eb-82ed-48a5d5514dc3.png)
  - label.properties의 약관을 Term으로 바꿔보고 실행해본다.  
    
    ![image](https://user-images.githubusercontent.com/79209568/123371123-13577a00-d5bc-11eb-984a-3c5e736a6b7e.png)
    ![image](https://user-images.githubusercontent.com/79209568/123371129-15b9d400-d5bc-11eb-8007-844e27b0e28f.png)

## 데이터 검증
- 회원 정보들을 맞게 입력했는지 확인하는 코드를 입력 : **Java Script**
  - JS로 검증하는 것은 변조가 가능하다. 1차로 간단하게 검증하는 용도로 사용
- 서버에 데이터 전송이 되면 유효한 정보인지 확인하는 코드 입력 : **Spring**
  - 2차 검증

<hr>
### 이메일 유효성 검증 코드 추가
- : 현재 이메일 형식이 맞지 않아도 등록이 잘 된다. 유효성 검사를 하는 코드를 추가해주도록 한다.
- controller 패키지에 `RegisterRequestValidator` 클래스 추가
#### RegisterRequestValidator.java
- `implements Validator` 해주고, unimplements method 추가 (validate, supports 메서드 추가 됨)
- 이메일 검증식을 작성한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/123375529-dbeccb80-d5c3-11eb-93e3-75935f2e9b9e.png)
  - 해당 검증식 시각화  
    
    ![image](https://user-images.githubusercontent.com/79209568/123375740-44d44380-d5c4-11eb-8a0d-8b9f32461209.png)

  > #### 정규식
  > : 무수히 많은 문자열 중에 특정 문자열을 찾아내도록 표현하는 식
  > - `^` : 행의 시작
  > - `[ ] : or 연산 문자 (안 쪽 문자 중에 하나)
  > - `A-Z` : 대문자 A 부터 Z 까지의 문자 범위 (a-z, 0-9는 소문자, 숫자 문자범위)

- 검증 기능을 구현한다. 추가된 메서드 중 validate에서 검증 한다.
- `target` : 검사 대상 객체(여기서는 RegisterRequest가 됨)
- `errors` : 검사 결과 에러코드를 저장하는 객체
- 1. RegisterRequest의 객체 regReq를 target에 다운캐스팅 한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/123376485-81546f00-d5c5-11eb-9ff9-b26ee1340feb.png)

- 2. 만약 값이 입력되지 않았다면, email 필드는 required 코드로 에러라는 것을 등록한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/123376539-97622f80-d5c5-11eb-97f3-9ea278252f3f.png)

- 3. 만약 값이 입력되었다면, 
  - 3-1. Matcher 클래스를 통해 이메일 형식을 검증한다.  
    
    ![image](https://user-images.githubusercontent.com/79209568/123376571-a8ab3c00-d5c5-11eb-8221-69ac56550b7b.png)

  - 3-2. 이메일 형식 검증이 실패했다면, (matches()는 검증 결과를 boolean값으로 리턴)
    - 콘솔에 메시지를 띄우며, email 필드는 bad 코드로 에러라는 것을 등록한다.  
    
    ![image](https://user-images.githubusercontent.com/79209568/123376611-bb257580-d5c5-11eb-9859-ff3d89d21bc6.png)

  - 3-3. 이메일 형식 검증이 성공했다면, 콘솔에 메시지를 띄운다.  
    
    ![image](https://user-images.githubusercontent.com/79209568/123376628-c4164700-d5c5-11eb-9ce6-00166c8904a7.png)
- **validate 전체 코드**  
  
  ![image](https://user-images.githubusercontent.com/79209568/123376756-f7f16c80-d5c5-11eb-91f4-93c191941825.png)

#### RegisterController.java
![image](https://user-images.githubusercontent.com/79209568/123379228-c75f0200-d5c8-11eb-94c8-62fd022fdae4.png)


- **서버 실행 결과**
  - 이메일 형식이 맞지 않도록 입력하고 다음단계 클릭  
    
    ![image](https://user-images.githubusercontent.com/79209568/123375064-1dc94200-d5c3-11eb-9d7d-1b42b4e7ecfb.png)
  - 콘솔에 유효하지 않은 이메일 형식이라는 메시지가 뜨면서 step2 화면이 다시 뜬다.  
    
    ![image](https://user-images.githubusercontent.com/79209568/123375231-63860a80-d5c3-11eb-9720-6bf05c0f59c9.png)

  - 이메일 형식에 맞도록 다시 입력 후 다음단계 클릭  
    
    ![image](https://user-images.githubusercontent.com/79209568/123375243-67199180-d5c3-11eb-8fb9-b19dc775c531.png)

  - 유효한 이메일 형식이라고 뜨면서 step3 화면으로 넘어간다.  
    
    ![image](https://user-images.githubusercontent.com/79209568/123375255-6b45af00-d5c3-11eb-9201-f02d0263bc04.png)
  - 비밀번호는 아직 유효성 검증 코드를 입력하지 않았기 때문에 쓰지 않고 다음단계로 넘어간다.

## 에러코드 활용
### 에러코드 형식

