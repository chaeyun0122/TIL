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
