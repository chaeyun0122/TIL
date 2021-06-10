# 명시적 의존 주입
## spring

### ctx 생성
![image](https://user-images.githubusercontent.com/79209568/121299354-40efc280-c930-11eb-84ff-7bf75ef83c20.png)

### Assembler를 getBean으로 수정
![image](https://user-images.githubusercontent.com/79209568/121299430-61b81800-c930-11eb-83bb-2dc9f30543bf.png)
![image](https://user-images.githubusercontent.com/79209568/121299467-6ed50700-c930-11eb-8642-54fe60d72a54.png)

### bean 설정
```
<bean id="memberDao" class="spring.MemberDao"></bean>
	
<bean id="memberRegSvc" class="spring.MemberRegisterService"></bean>

<bean id="changePwdSvc" class="spring.ChangePasswordService"></bean>
```
![image](https://user-images.githubusercontent.com/79209568/121300061-47cb0500-c931-11eb-9677-4ac9a3d468ad.png)

> ### 오류남
> * 디폴트가 없다고 함
> ![image](https://user-images.githubusercontent.com/79209568/121303568-3b957680-c936-11eb-9e69-83f84ce7c1c4.png)
> * 생성자 만들어줌
> ![image](https://user-images.githubusercontent.com/79209568/121303600-43551b00-c936-11eb-957a-372480b6c8da.png)
> * 널포인트 에러남
> ![image](https://user-images.githubusercontent.com/79209568/121303636-4c45ec80-c936-11eb-9865-7075ab536184.png)
> * 참조를 시켜줌
> ![image](https://user-images.githubusercontent.com/79209568/121303692-58ca4500-c936-11eb-8880-b0504e226308.png)
> * 오류 안남

<hr>

- MemberRegisterService 생성자 함수 만들기
- ChangePasswordService 생성자 함수 만들기
- applicationContext에서 생성자 함수를 선택하도록 constructor-arg 태그 추가
  - constructor-arg : 생성자의 인자로 객체를 전달하는 XML 태그

<hr>


#### setter를 설정했을 경우 property를 사용한다.
 ![image](https://user-images.githubusercontent.com/79209568/121300779-3df5d180-c932-11eb-97c8-9664e1fafb01.png)
memberDao를 써주면 알아서 spring이 setter를 찾는다
![image](https://user-images.githubusercontent.com/79209568/121300920-6a115280-c932-11eb-95dc-b28be4ef3579.png)



#### 참조변수가 아니므로 기본자료형의 세팅일때는 ref말고 value 자체 값을 넣음
![image](https://user-images.githubusercontent.com/79209568/121302458-afcf1a80-c934-11eb-8748-c0aaf99a1955.png)
![image](https://user-images.githubusercontent.com/79209568/121302684-03416880-c935-11eb-9d92-c5e462138480.png)

![image](https://user-images.githubusercontent.com/79209568/121302645-f6bd1000-c934-11eb-8de4-10fe4c552bed.png)

- value값이 쌍따옴표로 적었으므로 string인데 setter에 int이므로 알아서 파싱해준다. (
