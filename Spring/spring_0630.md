> - 현재 로그인이 되어있지 않은데 비밀번호 변경이 가능한 상태다.
> - ChnagePwdController에서 GET요청, POST 요청에 대한 session 정보가 존재하는지 확인하는 코드를 각각 써줘야한다.
> - 특정 시점에서 요청을 가로채서 확인 후 처리를 하는 인터셉터를 이용해서 /edit이후의 요청은 모두 세션 확인을 하도록 한다.

# Interceptor
## Interceptor 정의
- interceptor패키지 AuthCheckInterceptor 클래스 생성

> ### Adapter pattern
> - 문법상 정의는 하지만 내용은 없으므로 객체 생성하지 않아도 되는 추상 클래스로 정의하고 preHandle만 정의해서 쓰면 코드르 편하게 쓸 수 있다.
> - 원래 코드는 HandlerInterceptor를 implements 해준 추상 클래스를 만들어서 문법상 정의를 모두 해준 후 객체 생성해서 사용할 핸들러만 따로 빼서 사용  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/123903784-53e33900-d9aa-11eb-8de6-e61b9686f35f.png)
> - 이 역할을 해주는 HandlerIntercepterAdapter를 extend 해준다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/123903855-6e1d1700-d9aa-11eb-80e0-c99d2b9b7d21.png)

#### AuthCheckInterceptor
- request.getSession으로 세션을 받아온다. (false를 인자로 넣으면 세션이 없는 경우 null을 반환한다.)
- 만약 세션이 존재하면 authInfo를 받아오고 authInfo가 존재하면 preHandler를 true로 리턴한다.
- 만약 세션이 존재하지 않으면 preHandler를 false로 리턴하고 `/login`으로 redirect 시킨다. (비밀번호 변경 페이지로 넘어가지 못함)
![image](https://user-images.githubusercontent.com/79209568/123904092-dd930680-d9aa-11eb-8533-283a9881950e.png)


## Interceptor 등록
#### spring-mvc.xml
![image](https://user-images.githubusercontent.com/79209568/123904057-ce13bd80-d9aa-11eb-911a-b38210cf9347.png)

## 서버 실행
- 실행하자마자 `/edit/changePassword` 경로로 이동하도록 한다. (로그인되지 않은 상태)  
  
  ![image](https://user-images.githubusercontent.com/79209568/123904226-192dd080-d9ab-11eb-9b74-2d8f958f04bc.png)
- `/edit`하위 경로에 세션 유무를 확인하는 인터셉트가 걸려있다. 세션이 존재하지 않으므로 preHandler가 false를 리턴하면서 `/login`으로 redirect한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/123904315-3cf11680-d9ab-11eb-9982-3b986dd7ad48.png)
