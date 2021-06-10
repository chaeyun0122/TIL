# Spring의 의존객체 주입
## 명시적 의존 주입
> ### <constructor-arg ref=>
> - 생성자의 인자로 객체를 전달하는 XML 태그
#### ctx 생성
![image](https://user-images.githubusercontent.com/79209568/121299354-40efc280-c930-11eb-84ff-7bf75ef83c20.png)

#### Assembler를 getBean으로 수정
![image](https://user-images.githubusercontent.com/79209568/121518023-f9019600-ca2a-11eb-90bb-03d853bb2943.png)

![image](https://user-images.githubusercontent.com/79209568/121299467-6ed50700-c930-11eb-8642-54fe60d72a54.png)

#### bean 설정
```
<bean id="memberDao" class="spring.MemberDao"></bean>
	
<bean id="memberRegSvc" class="spring.MemberRegisterService"></bean>

<bean id="changePwdSvc" class="spring.ChangePasswordService"></bean>
```
### 결과
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
> * MemberDao를 constructor-arg 태그를 통해 참조 시켜줌  
> 
>   ![image](https://user-images.githubusercontent.com/79209568/121519279-7e397a80-ca2c-11eb-8027-b1c15d5364cd.png)
> * 오류 안남
> 
>   ![image](https://user-images.githubusercontent.com/79209568/121519461-b3de6380-ca2c-11eb-87ec-a7a80cdd5793.png)


<hr>

> ### \<property ref\>
> - setter가 존재하는 경우 property 태그로 의존 객체를 주입받는다.

#### MemberInfoPrinter.java
- 특정 회원의 정보를 출력하는 클래스
- 객체와 setter가 있다.
  ![image](https://user-images.githubusercontent.com/79209568/121521177-b477f980-ca2e-11eb-88e2-496042c2c636.png)

#### setter를 설정했을 경우 property를 사용한다.
![image](https://user-images.githubusercontent.com/79209568/121521230-c5c10600-ca2e-11eb-925a-5a55b90b1bc6.png)

-spring이 알아서 setter를 찾는다
![image](https://user-images.githubusercontent.com/79209568/121300920-6a115280-c932-11eb-95dc-b28be4ef3579.png)

<hr>

> ### \<property value\>
> - 
#### 참조변수가 아니므로 기본자료형의 세팅일때는 ref말고 value 자체 값을 넣음
![image](https://user-images.githubusercontent.com/79209568/121302458-afcf1a80-c934-11eb-8748-c0aaf99a1955.png)
![image](https://user-images.githubusercontent.com/79209568/121302684-03416880-c935-11eb-9d92-c5e462138480.png)

![image](https://user-images.githubusercontent.com/79209568/121302645-f6bd1000-c934-11eb-8de4-10fe4c552bed.png)

- value값이 쌍따옴표로 적었으므로 string인데 setter에 int이므로 알아서 파싱해준다. (
