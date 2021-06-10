## 참조를 Spring이 알아서 찾게 해준다.
- bean의 참조를 삭제
  
  ![image](https://user-images.githubusercontent.com/79209568/121461086-23326400-c9e9-11eb-8886-660c72423391.png)
- Autowired의 annotation(동작하는 주석)을 넣어준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121461228-668cd280-c9e9-11eb-95b1-22c135ba7b8e.png)
  ![image](https://user-images.githubusercontent.com/79209568/121462905-44e11a80-c9ec-11eb-968d-8b6255d375ca.png)
  - 스프링이 인터페이스 상의 속성값들을 매칭시켜놓고 annotation이 있으면 해당 동작을 하겠다는 것을 나타낸다.
  - Autowired가 붙어있으면 해당 타입(MemberDao 타입, printer 타입)을 찾고 bean에 그 타입이 있으면 주입을 해준다.
- spring이 annotation을 읽으라는 설정을 넣어줘야한다.
  - context 선언을 해준다(외울 필요 X)
  - annotation-config 설정을 넣어준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121462510-a8b71380-c9eb-11eb-86ec-2aea8634bfdc.png)
