# Spring의 의존객체 주입
> - 프로젝트명 : examspring02 [👉project file] (https://github.com/Clary0122/TIL/tree/main/Spring/project/examspring02)
> - 패키지명 : spring
> - 리소스 : applicationContext.xml 추가
## 명시적 의존 주입
> ### <constructor-arg ref=>
> - 생성자의 인자로 객체를 전달하는 XML 태그
#### ctx 생성
![image](https://user-images.githubusercontent.com/79209568/121299354-40efc280-c930-11eb-84ff-7bf75ef83c20.png)

#### Assembler를 getBean으로 수정
![image](https://user-images.githubusercontent.com/79209568/121518023-f9019600-ca2a-11eb-90bb-03d853bb2943.png)

![image](https://user-images.githubusercontent.com/79209568/121299467-6ed50700-c930-11eb-8642-54fe60d72a54.png)

#### Bean 설정
![image](https://user-images.githubusercontent.com/79209568/121519279-7e397a80-ca2c-11eb-8027-b1c15d5364cd.png)
```
<bean id="memberDao" class="spring.MemberDao"></bean>
	
<bean id="memberRegSvc" class="spring.MemberRegisterService"></bean>

<bean id="changePwdSvc" class="spring.ChangePasswordService"></bean>
```
#### 결과
![image](https://user-images.githubusercontent.com/79209568/121519461-b3de6380-ca2c-11eb-87ec-a7a80cdd5793.png)
> ## 참조를 하지 않았을 경우에는?
> #### Bean 설정
> ```
> <bean id="memberDao" class="spring.MemberDao"></bean>
>  
> <bean id="memberRegSvc" class="spring.MemberRegisterService"></bean>
>   
> <bean id="changePwdSvc" class="spring.ChangePasswordService"></bean>
> ```
> 
> ### 오류남
> * 디폴트가 없다고 함
> 
>   ![image](https://user-images.githubusercontent.com/79209568/121303568-3b957680-c936-11eb-9e69-83f84ce7c1c4.png)
> * 빈 생성자를 만들어줌  
> 
>   ![image](https://user-images.githubusercontent.com/79209568/121303600-43551b00-c936-11eb-957a-372480b6c8da.png)
> * 하지만 널포인트 에러남  
> 
>   ![image](https://user-images.githubusercontent.com/79209568/121303636-4c45ec80-c936-11eb-9865-7075ab536184.png)
> * MemberDao를 constructor-arg 태그를 통해 참조 시켜주면 위에 처럼 오류나지 않음 

<hr>

> ### \<property ref\>
> - setter가 존재하는 경우 property 태그로 의존 객체를 주입받는다.

#### MemberInfoPrinter.java
- 특정 회원의 정보를 출력하는 클래스
- 객체와 setter가 있다.  
  ![image](https://user-images.githubusercontent.com/79209568/121521177-b477f980-ca2e-11eb-88e2-496042c2c636.png)

#### Main
![image](https://user-images.githubusercontent.com/79209568/121523257-f9049480-ca30-11eb-83e6-05f010599ea9.png)

#### Bean 설정
- setter를 설정했을 경우 property를 사용한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/121521230-c5c10600-ca2e-11eb-925a-5a55b90b1bc6.png)
- spring이 알아서 setter를 찾는다.  

  ![image](https://user-images.githubusercontent.com/79209568/121300920-6a115280-c932-11eb-95dc-b28be4ef3579.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121521871-847d2600-ca2f-11eb-8521-111c574fafcf.png)

<hr>

> ### \<property value\>
> - 기본 자료형을 의존 객체로 주입

#### VersionPrinter.java
- 버전 정보를 출력해주는 클래스
- 기본자료형과 setter가 있다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/121522802-724fb780-ca30-11eb-9045-ea08130f5813.png)
#### Main
![image](https://user-images.githubusercontent.com/79209568/121523338-10dc1880-ca31-11eb-898d-ce1c386fa32c.png)

#### Bean 설정
- setter를 설정하고 기본 자료형을 참조할 때는 property 태그의 ref 옵션말고 **value**옵션을 넣어준다.
- value값이 쌍따옴표로 적었으므로 string인데 setter에 int이므로 알아서 파싱해준다.    
  
  ![image](https://user-images.githubusercontent.com/79209568/121302458-afcf1a80-c934-11eb-8748-c0aaf99a1955.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121302645-f6bd1000-c934-11eb-8de4-10fe4c552bed.png)

