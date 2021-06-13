
> - 프로젝트명 : examspring05 ([👉project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/examspring05))
> - 패키지명 : spring
>   - 클래스 : Client, Client2, Client3, Client4, Main, Main2, Main3
> - 패키지명 : conf
>   - 클래스 : JavaConfig, JavaConfig2
> - 리소스 : appctx.xml
# Spring Life Cycle
- 객체가 생성되고 사라지는 과정을 본다.

#### Client.java
- `InitializingBean`과 `DisposableBean`을 implement
  
  ![image](https://user-images.githubusercontent.com/79209568/121629499-424af780-cab6-11eb-9822-9d9536421d03.png)

#### appctx.xml
  
  ![image](https://user-images.githubusercontent.com/79209568/121630330-e5504100-cab7-11eb-9f86-de6b0c534a45.png)

#### Main.java
  
  ![image](https://user-images.githubusercontent.com/79209568/121630353-eed9a900-cab7-11eb-82f7-9ac674a0063c.png)

#### 결과
  
  ![image](https://user-images.githubusercontent.com/79209568/121635631-868fc500-cac1-11eb-84bd-a58a1a94146b.png)

## 클래스에 직접 초기화, 종료 메서드 지정
#### Client2.java
- startMethod, endMethod 생성
  
  ![image](https://user-images.githubusercontent.com/79209568/121633940-b4273f00-cabe-11eb-8e7c-3830cc3bbc83.png)

#### Main
  
  ![image](https://user-images.githubusercontent.com/79209568/121634011-d0c37700-cabe-11eb-8e27-fc0e96f5cc42.png)

#### appctx.xml
- 초기화 메서드, 종료메서드를 Client2 Bean에 지정한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121634053-e5a00a80-cabe-11eb-914d-7960dbae57d9.png)

#### 결과
  
  ![image](https://user-images.githubusercontent.com/79209568/121634118-010b1580-cabf-11eb-8689-8d72e9b330b1.png)

## Java코드 설정 파일
> - 패키지 : conf
> - 클래스명 : JavaConfig.java


#### JavaConfig.java
- Bean Annotation에 초기화, 종료 메서드를 설정해준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121634296-3f083980-cabf-11eb-8c1b-69637257e16b.png)

#### Main
  
  ![image](https://user-images.githubusercontent.com/79209568/121634532-b3db7380-cabf-11eb-936c-1464fea1e17d.png)

#### 결과 
- 위와 같음
  
  ![image](https://user-images.githubusercontent.com/79209568/121634563-be960880-cabf-11eb-8ba4-dcee5e5bbe48.png)

## Close(), Shutdown() 메서드
> - 패키지 : conf
> - 클래스 : JavaConfig2
> - 실행 : spring/Main3

- 따로 종료 메서드라는 설정을 하지 않아도 close, shutdown이라는 이름의 메서드는 종료 메서드로 인식한다.
#### Client3.java, Client4.java
- Client3, Client4를 생성 후 종료 메서드의 이름을 각각 close(), shutdown()로 지정한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121634673-f69d4b80-cabf-11eb-99ca-265078a0d817.png)
  
  ![image](https://user-images.githubusercontent.com/79209568/121634680-f9983c00-cabf-11eb-9f6c-3aeca1577f12.png)

#### JavaConfig2.java
- Bean Annotation에 초기화 메서드와 종료 메서드를 입력하지 않고 Bean을 설정한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121635322-023d4200-cac1-11eb-8736-244f30469d6e.png)


#### 결과
  
  ![image](https://user-images.githubusercontent.com/79209568/121635369-16813f00-cac1-11eb-9be2-27d08a4f98d5.png)

- 자원을 해제하는 것이 중요하기 때문에 종료 메서드를 따로 지원한다.

## 객체의 범위
- 기본적으로 Spring 컨테이너는 Bean객체를 하나만 생성한다(싱글톤)
- 필요에 따라 Prototype의 Bean을 생성해서 객체를 구할 때 마다 계속 생성하게 할 수 있다.

### Java
> - 패키지 : conf
> - 클래스 : JavaConfigPrototype
> - 실행 : spring/Main4

#### JavaConfigPrototype
- `@scope("prototype")` annotition을 붙이면 getBean을 할 때마다 계속 bean을 생성한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121639747-f739e000-cac7-11eb-98b8-2cf4e3852ccb.png)
#### Main4
- main에서 두 번의 getBean을 실행한다. 두 객체가 같은 것인지 확인한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121639781-06209280-cac8-11eb-9802-f0be42d04f81.png)

#### 결과
- 두 bean 객체가 생성되고 둘은 각각 다른 객체다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121810710-d70f4a00-cc9c-11eb-9c7a-f4067f8a5f06.png)


> - **scope 지웠을 경우**
>   
>   ![image](https://user-images.githubusercontent.com/79209568/121639827-120c5480-cac8-11eb-9a49-c61071586735.png)
> - 객체를 하나 생성하며 main에서 만들어진 client2_1과 client2_2는 같은 객체다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/121639834-146eae80-cac8-11eb-9b07-787da50abe43.png)

#### 소멸자 메서드
- client2를 client로 다 바꿔준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121639867-22bcca80-cac8-11eb-999f-9faea47881e1.png)
  
  ![image](https://user-images.githubusercontent.com/79209568/121639888-281a1500-cac8-11eb-8a61-937c3b820c7e.png)
- 결과를 보면 소멸자를 호출하지 않는다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121639903-2cdec900-cac8-11eb-9fd8-3a006aa70739.png)
- prototype으로 할 경우 **destroy를 직접 호출해줘야한다.**
  
  ![image](https://user-images.githubusercontent.com/79209568/121639952-43852000-cac8-11eb-92cb-a23600586c59.png)
- 결과
  
  ![image](https://user-images.githubusercontent.com/79209568/121639958-46801080-cac8-11eb-8937-66a1cfefc2ed.png)

### xml
- Bean 설정에 `scope="prototype"` 설정을 추가해준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/121639974-4da71e80-cac8-11eb-8213-321dde43c66c.png)
- 위와 같음
  
  ![image](https://user-images.githubusercontent.com/79209568/121639986-539cff80-cac8-11eb-9dee-02be3b755f7f.png)

> proxy pattern
