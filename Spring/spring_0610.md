## 참조를 Spring이 알아서 찾게 해준다.
- bean의 참조를 삭제
  
  ![image](https://user-images.githubusercontent.com/79209568/121461086-23326400-c9e9-11eb-8886-660c72423391.png)
- Autowired의 annotation(동작하는 주석)을 넣어준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121461228-668cd280-c9e9-11eb-95b1-22c135ba7b8e.png)
  ![image](https://user-images.githubusercontent.com/79209568/121464664-39dbb980-c9ef-11eb-99fd-8e1bc9363470.png)


  - 스프링이 인터페이스 상의 속성값들을 매칭시켜놓고 annotation이 있으면 해당 동작을 하겠다는 것을 나타낸다.
  - Autowired가 붙어있으면 해당 타입(MemberDao 타입, printer 타입)을 찾고 bean에 그 타입이 있으면 주입을 해준다.
- spring이 annotation을 읽으라는 설정을 넣어줘야한다.
  - context 선언을 해준다(외울 필요 X)
  - annotation-config 설정을 넣어준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121462510-a8b71380-c9eb-11eb-86ec-2aea8634bfdc.png)
- 실행결과
  
  ![image](https://user-images.githubusercontent.com/79209568/121463258-b325dd00-c9ec-11eb-87ee-470924fc4e66.png)

## 동일 타입 객체가 두 개 이상 존재
### Qualifier
![image](https://user-images.githubusercontent.com/79209568/121463694-79090b00-c9ed-11eb-89d1-c45f3ccc5c41.png)

- infoPrinter에서 에러난다.
  ```
  Error creating bean with name 'infoPrinter': Injection of autowired dependencies failed; 
  nested exception is org.springframework.beans.factory.BeanCreationException: Could not autowire field: private spring.MemberPrinter spring.MemberInfoPrinter.printer; 
  nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type [spring.MemberPrinter] is defined: expected single matching bean but found 2: printer1,printer2
  ```
- 어떤 객체를 주입할지 선택해주도록 써 줘야한다.
  - qualifier 태그를 통해 타입으로 구분되지 않던 객체들에 이름을 지정해준다.
    ![image](https://user-images.githubusercontent.com/79209568/121464015-0fd5c780-c9ee-11eb-9c91-fc787f007606.png)
- qualifier annotation을 지정해준다.
  ![image](https://user-images.githubusercontent.com/79209568/121464635-2b8d9d80-c9ef-11eb-8766-a85a017e0e2f.png)
- 만약 `Autowired(required = false)`를 하면 꼭 필수로 주입시키지 않는다는 뜻이다. 즉 때문에 오류가 나지 않고 실행은 된다.
  ![image](https://user-images.githubusercontent.com/79209568/121466168-ed45ad80-c9f1-11eb-980b-51c72d4b6319.png)

### Resource
- resource annotation 생성
  ![image](https://user-images.githubusercontent.com/79209568/121466748-e53a3d80-c9f2-11eb-87ed-15b412c19986.png)
  ![image](https://user-images.githubusercontent.com/79209568/121466860-16b30900-c9f3-11eb-870e-9190205ae58f.png)
