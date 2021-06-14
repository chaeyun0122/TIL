# AOP
- aspect oriented programming (관점 지향 프로그래밍)

> - 프로젝트명 : examspring06 ([👉project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/examspring06))
> - [aop00](#Java에서-확인하기) : 기본 calculator 동작 구현
> - [aop01](#직접-기능-수정) : main에서 직접 기능 수정
> - [aop02](#클래스에서-기능-수정) : 핵심 기능 클래스에 수정
> - [aop03](#공통적인-기능을-하는-클래스-구현) : 공통 기능 클래스 추가(프록시 패턴)

## Java에서 확인하기
#### Calculator
- 인터페이스  
![image](https://user-images.githubusercontent.com/79209568/121836673-e929d080-cd0e-11eb-806f-9628d583876f.png)

#### CalculatorImpl
- 반복문  
![image](https://user-images.githubusercontent.com/79209568/121836823-327a2000-cd0f-11eb-9e92-99663bfb7e4e.png)

#### CalculatorRecu
- 재귀함수  
![image](https://user-images.githubusercontent.com/79209568/121836843-3dcd4b80-cd0f-11eb-8a37-f030284fe1cf.png)

#### Main
![image](https://user-images.githubusercontent.com/79209568/121836853-4756b380-cd0f-11eb-84e6-c1bb153b8353.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121836874-50478500-cd0f-11eb-890f-0fbd697f5a29.png)

## 기능에 수행 시간을 구하는 기능을 추가한다면?
### 직접 기능 수정
- main에서 직접 넣어준다.
- 하나하나 다 해야하는 번거로움이 있다.  
![image](https://user-images.githubusercontent.com/79209568/121837221-04e1a680-cd10-11eb-9222-de7cef53a344.png)

### 클래스에서 기능 수정
- class에서 직접 수정해준다.
- 반복문의 경우 함수 안에서 시간 체크가 끝나기때문에 start, end 변수로 시간 체크가 가능한다.  
  ![image](https://user-images.githubusercontent.com/79209568/121837620-08c1f880-cd11-11eb-9bc1-1beaca2f71b2.png)
- 재귀함수의 시간 체크 경우는 함수가 돌아갈 때마다 시간체크를 다시 하기 때문에 구현하기가 불편하다.
- **반복문에서 구현하는 기능과 재귀에서 구현하는 기능이 동일함에도 구현이 불편함**

### 공통적인 기능을 하는 클래스 구현
- 실행 시간을 구하는 클래스를 따로 구현한다.
- **공통적인 기능 클래스를 핵심 기능 클래스를 실행 할 때마다 불러와서 실행하도록 한다.**
- 이러한 동작 구현 방식을 **프록시 패턴**이라고 한다.
#### ExecTimeCalculator
![image](https://user-images.githubusercontent.com/79209568/121838512-fa74dc00-cd12-11eb-8b4d-6774b05f0d50.png)
#### Main & 결과
![image](https://user-images.githubusercontent.com/79209568/121839166-82a7b100-cd14-11eb-93e3-4b58ae3b470f.png)


## Spring에서 AOP 기능 확인
### XML
#### pom.xml
- AOP 관련 라이브러리를 dependency에 표함한다.
- maven의 라이브러리 검색은 https://mvnrepository.com/ 에서 한다.
  - aspectj-weaver 검색
  - 1.8.2 버전 선택 (https://mvnrepository.com/artifact/org.aspectj/aspectjweaver/1.8.2)
  - maven꺼로 복사해서 dependency에 추가
    ![image](https://user-images.githubusercontent.com/79209568/121841501-5d697180-cd19-11eb-8efb-c2b7cf364621.png)

#### ExecTimeCalculator
- POJO : Plain Old Java Object
  - 아무것도 의존하지 않고, 또 무엇도 의존 받지 않은 깔끔한 클래스 객체
![image](https://user-images.githubusercontent.com/79209568/121845497-7d506380-cd20-11eb-9f20-035cb3a6dbf6.png)

#### appctx.xml
- aop 설정을 해준다 
  - 어떤 기능이 동작할 때 그 기능이 동작되기 전, 후 등에 동작할 메서드 지정
  - ex) factorial 동작하기 전에 시간 구하는 동작을 먼저 수행해라

![image](https://user-images.githubusercontent.com/79209568/121845121-f00d0f00-cd1f-11eb-9db9-b10a5053ea81.png)

- `<aop:pointcut>` : 스프링에 알려주는 자르고 들어갈 지점.
- `<aop:around>` : pointcut 지점 전 후에 유연하게 사용
  
  ![image](https://user-images.githubusercontent.com/79209568/121844907-a3293880-cd1f-11eb-8975-f375769de76c.png)
  
- public으로 반환자료형 아무거나, aop04 패키지의 어떤 클래스 메서드 상관없이 long타입의 반환되는 함수(timeMethod)가 호출이 될 때 measure 메서드가 around로 동작되도록한다.
  ```java
  <aop:pointcut expression="execution(public * aop04..*(long))" id="timeMethod"/>
  <aop:around pointcut-ref="timeMethod" method="measure"/>
  ```
#### CalculatorImpl, CalculatorRecu
- 인터페이스 implements 삭제
  
  ![image](https://user-images.githubusercontent.com/79209568/121845311-37939b00-cd20-11eb-87ce-325e298a1740.png)

#### main
![image](https://user-images.githubusercontent.com/79209568/121845416-5b56e100-cd20-11eb-9c96-a4a7ea51b976.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121845442-6578df80-cd20-11eb-9b87-fb00a70596a7.png)

### Annotation
#### ExecTimeCalculator
![image](https://user-images.githubusercontent.com/79209568/121846236-ae7d6380-cd21-11eb-8424-8108b2cec6e3.png)

#### Main & 결과
![image](https://user-images.githubusercontent.com/79209568/121846556-251a6100-cd22-11eb-9e49-36739f83b6cc.png)

### Java
#### JavaConfig
![image](https://user-images.githubusercontent.com/79209568/121846796-8d694280-cd22-11eb-90ad-0170cdd98b4f.png)

#### ExecTimeCalculator
![image](https://user-images.githubusercontent.com/79209568/121846834-9a863180-cd22-11eb-9f90-f020a6bdadcf.png)

#### Main & 결과
![image](https://user-images.githubusercontent.com/79209568/121846897-b4277900-cd22-11eb-870e-c2ee8a96d313.png)
