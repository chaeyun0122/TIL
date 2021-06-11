# Spring Life Cycle
- 객체가 생성되고 사라지는 과정을 본다.
> - 프로젝트명 : examspring05 ([👉project file]())
> - 패키지명 : spring 추가
> - 클래스 : Client, Client2
> - 리소스 : appctx.xml

#### Client.java
- `InitializingBean`과 `DisposableBean`을 implement
![image](https://user-images.githubusercontent.com/79209568/121629499-424af780-cab6-11eb-9822-9d9536421d03.png)

#### appctx.xml
![image](https://user-images.githubusercontent.com/79209568/121630330-e5504100-cab7-11eb-9f86-de6b0c534a45.png)

#### Main.java
![image](https://user-images.githubusercontent.com/79209568/121630353-eed9a900-cab7-11eb-82f7-9ac674a0063c.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121631916-f8b0db80-caba-11eb-91c3-8e7a8ef2bc3f.png)

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

## Java로 해보기
- 패키지 : conf
- 클래스명 : JavaConfig.java


#### JavaConfig.java
- Bean Annotation에 초기화, 종료 메서드를 설정해준다.
![image](https://user-images.githubusercontent.com/79209568/121634296-3f083980-cabf-11eb-8c1b-69637257e16b.png)

#### Main
![image](https://user-images.githubusercontent.com/79209568/121634532-b3db7380-cabf-11eb-936c-1464fea1e17d.png)

#### 결과 
- 위와 같음
![image](https://user-images.githubusercontent.com/79209568/121634563-be960880-cabf-11eb-8ba4-dcee5e5bbe48.png)

## Close(), Shutdown() 메서드
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
