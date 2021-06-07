# Spring Framework
- 의존주입(DI) 지원 : 객체가 다른 객체를 참조하게 하는 것을 직접 지원해준다.
- 제어의 역전 : 모든 제어를 스프링이 하도록 한다.
# 초기 환경 설정
## Eclipse 설치
* https://www.eclipse.org/downloads/packages/release/2020-06/r : 2020-06 버전을 설치  
  
  ![image](https://user-images.githubusercontent.com/79209568/120957988-b1141200-c791-11eb-8da9-449e376d0f30.png)
* 해당 경로로 설치 완료  
  
  ![image](https://user-images.githubusercontent.com/79209568/120958047-d1dc6780-c791-11eb-93fd-f25c280f2b4c.png)
* 작업 폴더를 지정  
  
  ![image](https://user-images.githubusercontent.com/79209568/120958097-ee789f80-c791-11eb-933d-f47b15c96add.png)
* 한글 설정
  * `Window > preperences > General > Workspace` : text file encoding을 UTF-8
    ![image](https://user-images.githubusercontent.com/79209568/120963483-a7dc7280-c79c-11eb-9219-16e100faf31f.png)
  * `Web` CSS Files, HTML files, JSP filef : UTF-8
## 라이브러리 생성 및 확인
* Perspective를 통해 이클립스가 동작할 환경을 선택할 수 있다.
* java perspective를 추가한다.  
  ![image](https://user-images.githubusercontent.com/79209568/120960631-3c43d680-c797-11eb-96d2-4be3833157d1.png)
* 프로젝트 추가
 - 프로젝트 이름 : MySpring
 - 패키지 : org.myspring.context
 - 클래스 : MySpringContext
   ![image](https://user-images.githubusercontent.com/79209568/120967854-00167300-c7a3-11eb-8167-439481e51b5f.png)
* 해당 프로젝트를 jar파일로 export해서 라이브러리 파일로 만든다.
  ![image](https://user-images.githubusercontent.com/79209568/120967932-20dec880-c7a3-11eb-8eed-5ba4c6aadf9c.png)
  ![image](https://user-images.githubusercontent.com/79209568/120967936-24724f80-c7a3-11eb-86ad-0981cdd2170e.png)
* 해당 프로젝트는 라이브러리로 생성 했으니 삭제해도 된다. 그 후 라이브러리를 테스트 할 새로운 프로젝트 생성
  - 프로젝트 이름 : myspringtest
  - 패키지 : myspringtest
  - 클래스 : TestMain
* 프로젝트 오른쪽 클릭 `Build Path > Labraries`에 Add JAR로 jar파일 추가(myspring jar파일은 해당 프로젝트 내에 있어야한다.)
   ![image](https://user-images.githubusercontent.com/79209568/120968513-ecb7d780-c7a3-11eb-874f-1f9ca0e0be66.png)

* TestMain에 해당 코드 작성으로 import되는 것을 확인한다.
  ![image](https://user-images.githubusercontent.com/79209568/120968557-fb05f380-c7a3-11eb-978c-9c625fc03e17.png)

  
## maven project
* 직접 만들어 본다. maven은 빌드해주는 도구다.
### 작업 path
* 작업 폴더에 `spring01` 폴더 생성 : 프로젝트 폴더
* 안에 `spring01\src\main` 폴더 생성
* `main` 폴더 안에 `java`, `resources` 폴더를 넣는다.
### pom.xml
* pom.xml을 src와 같은 레벨에 넣어준다.
  ![image](https://user-images.githubusercontent.com/79209568/120962777-567fb380-c79b-11eb-9dc5-20b76759efb6.png)
* pom.xml 파일에 atifactId에 프로젝트 명을 적어준다.
  * `dependencies`는 maven이 빌드할 때 추가할 jar파일을 적어준다.
  * 이 파일은 해당 경로에 존재하는 라이브러리를 추가해준다.
    ![image](https://user-images.githubusercontent.com/79209568/120964650-98f6bf80-c79e-11eb-8929-f0dd42c74964.png)
* 이클립스에서 import > Maven > Existing Maven Project로 현재 프로젝트를 추가한다.
  ![image](https://user-images.githubusercontent.com/79209568/120964764-c7749a80-c79e-11eb-82c7-c3e7ef23b0a5.png)
* applicationContext.xml 파일을 resources에 생성한다.
  * bean은 스프링이 동작할 때 필요한 객체를 생성하라는 명령어다. id는 greeter이고, Greeter라는 클래스를 통해 만들어진ㄷ.
    ![image](https://user-images.githubusercontent.com/79209568/120964916-0e629000-c79f-11eb-820f-cd1f50b268e8.png)
* Greeter라는 클래스를 생성해준다.
  ![image](https://user-images.githubusercontent.com/79209568/120964991-29350480-c79f-11eb-862a-11d06ccaeca5.png)
* Main클래스를 생성해서 실행해준다.
  * `GenericXmlApplicationContext`을 통해 아까 생성한 applicationContext.xml에서 객체를 생성할 수 있도록 접근한다.
    ![image](https://user-images.githubusercontent.com/79209568/120965337-ba0be000-c79f-11eb-9804-05703524c685.png)
* 실행 결과 
  ![image](https://user-images.githubusercontent.com/79209568/120965384-c859fc00-c79f-11eb-8ecd-ca77455b70bf.png)

* bean에 property를 추가해서 format을 설정해주면 객체를 생성하면서 행위를 한다.  
 ![image](https://user-images.githubusercontent.com/79209568/120967438-677ff300-c7a2-11eb-9b16-d9dab7161178.png)
* main에서 setFormat을 주석처리 후 실행해보면 bean에 설정한대로 출력된다.  
  ![image](https://user-images.githubusercontent.com/79209568/120967550-8c746600-c7a2-11eb-86bc-2039d9fae257.png)


> ### 추가 공부할 개념
> * 자바 Class 클래스
> * 자바 Reflection 개념
