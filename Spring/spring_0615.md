# AOP 예제 2

> - 프로젝트명 : examspring06 ([👉project file](https://github.com/Clary0122/TIL/blob/main/Spring/project/examspring06.zip))
> - [aop001](#전체) : 전체 작업
> - aop002
>     - [1 : Before - annotation](#Advice-Annotation)
>     - [2 : JoinPoint](#JoinPoint)
> - [aop003](#POJO클래스와-XML을-이용) : Before - POJO class & XML
> - [aop004](#POJO클래스와-XML을-이용) : Before, After - POJO class & XML
> - aop005 : Before, After - annotation
> - [aop006](#PointCut-반복-줄이기) : @Pointcut - annotation
> - [aop007](#annotation) : @Pointcut - POJO class & XML



## 전체
#### Boy
![image](https://user-images.githubusercontent.com/79209568/121993402-5e180b80-cdde-11eb-9d01-71254a0ef5c4.png)

#### Girl
![image](https://user-images.githubusercontent.com/79209568/121993417-66704680-cdde-11eb-939e-cf9271c1ece6.png)

#### Main
![image](https://user-images.githubusercontent.com/79209568/121993438-6e2feb00-cdde-11eb-81ec-0adf1756f7f8.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121993448-75ef8f80-cdde-11eb-8062-d8ba12bff68f.png)

## Advice Annotation
- 횡단 관심을 핵심 관심의 어느 시점에 실행시킬 것인지 정의
- Before, After, Around 등이 있다.

### Before
#### Person
> **인터페이스**
- 스프링은 인터페이스 기반으로 AOP가 동작한다.  
  
![image](https://user-images.githubusercontent.com/79209568/121993972-61f85d80-cddf-11eb-82c1-87112ec1d8db.png)

#### Boy, Girl
- Person 인터페이스를 Implements 해준다.
- 인터페이스를 구현하는데 횡단 관심은 제거하고 핵심 관심만 정의한다.  
  
![image](https://user-images.githubusercontent.com/79209568/121994108-a7b52600-cddf-11eb-8925-810386a3168c.png)

#### MyAspect
- @Aspect를 지정하는 MyAspect 클래스를 생성
- @Before어노테이션이 있는 beforeMethod를 생성  
  - `@Before("execution(public void aop002.Boy.runSomething()) || execution(public void aop002.Girl.runSomething())")`
  - `@Before("execution(public void aop002.*.runSomething())")`
  - `@Before("execution(public void aop002..runSomething())")`

  ![image](https://user-images.githubusercontent.com/79209568/121994824-eeefe680-cde0-11eb-9a9a-e5609239cf8d.png)
  
> ### 포인트 컷 지시자(표현식)
> ```
> execution([접근 제한자] 반환자료형 [패키지명.][클래스명].메서드명(파라미터타입))
> ```
> * \* : 모든 값
> * .. : 0 개 이상

#### aop002.xml
![image](https://user-images.githubusercontent.com/79209568/121994460-45a8f080-cde0-11eb-8000-388cd375f525.png)

#### Main
![image](https://user-images.githubusercontent.com/79209568/121994499-5a858400-cde0-11eb-944f-376e690a55c4.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121994712-bea84800-cde0-11eb-835c-dcc4cdb2bada.png)

## JoinPoint
- JoinPoint란 핵심 기능 함수가 실행하기 **전, 중간, 후** 등의 포인트다.
- PointCut은 그 JoinPoint들 중 공통 기능 함수가 자르고 들어갈 곳이다.
- Advice는 그 PoinCut을 기준으로 어느 시점에 실행 될 것인지 나타낸다. (여기서는 @Before이므로 이전)
- JoinPoint를 인자로 받는 메서드는 그 당시의 JoinPoint 정보를 받는다.
  - get으로 시작한다.
    
    ![image](https://user-images.githubusercontent.com/79209568/122204862-8f760180-ceda-11eb-8a6c-c0b797bf6f81.png)

- 만약 main에서 PointCut에 충족하는 메서드(핵심 기능 메서드)가 실행될 때 받는 인자가 있을 경우, JoinPoint를 인자로 받으면 getArgs()를 통해 그 때의 인자를 곧통 기능 함수에서 받아올 수 있다.
#### Person
- 인터페이스에 정수와 실수를 인자로 받는 runSomething 함수를 정의해준다.  
  
![image](https://user-images.githubusercontent.com/79209568/122205336-1925cf00-cedb-11eb-8692-c60e42c404f1.png)

#### Girl, Boy
- Person의 runSomething()을 오버라이드하는 함수를 정의해준다.  
  
![image](https://user-images.githubusercontent.com/79209568/122205535-4ffbe500-cedb-11eb-9e78-bdcbbef50f3a.png)

#### MyAspect
- before 공통 기능 메서드가 JoinPoint를 인자로 받는다.
- getArgs()로 받은 값을 하나하나 출력해주도록 해준다.  
  
![image](https://user-images.githubusercontent.com/79209568/122205929-c26cc500-cedb-11eb-89ea-572cc7996d55.png)

#### Main & 결과
![image](https://user-images.githubusercontent.com/79209568/122206151-03fd7000-cedc-11eb-9161-2cf5da569b03.png)

## POJO클래스와 XML을 이용
### Before
#### MyAspect
- 핵심 기능 이전에 동작할 함수 생성  
  
![image](https://user-images.githubusercontent.com/79209568/121998897-8bb58280-cde7-11eb-9fe3-3e36e68e5499.png)

#### aop003.xml
- aop:before  
  
![image](https://user-images.githubusercontent.com/79209568/121999207-f961ae80-cde7-11eb-9431-b737d8e2d2ea.png)

#### Boy, Person
![image](https://user-images.githubusercontent.com/79209568/121999268-0da5ab80-cde8-11eb-9731-09dd6399bb69.png)

#### Main & 결과
![image](https://user-images.githubusercontent.com/79209568/121999318-1f874e80-cde8-11eb-8df7-c204308a8a08.png)

### After
- aop003 패키지 복사해서 aop004로
#### MyAspect
- 핵심 기능 이후에 동작할 함수를 생성  
  
![image](https://user-images.githubusercontent.com/79209568/122199390-f55f8a80-ced4-11eb-9356-2e448e4a19fe.png)

#### aop004.xml
- aop:after  
  
![image](https://user-images.githubusercontent.com/79209568/122199605-2d66cd80-ced5-11eb-9170-b4a90625f5b1.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/122199890-7cacfe00-ced5-11eb-88fe-5cd127b0ef85.png)

## PointCut 반복 줄이기
- XML 혹은 annotation에 반복적으로 쓰는 PointCut을 따로 빼서 반복을 줄일 수 있다.  
  
![image](https://user-images.githubusercontent.com/79209568/122201005-8b47e500-ced6-11eb-85b2-c686d92075bf.png)
### XML
- `<aop:pointcut>` 태그
- pointcut-ref 속성으로 선언한 포인트컷을 참조한다.  
  
![image](https://user-images.githubusercontent.com/79209568/122201188-bf230a80-ced6-11eb-869a-4ef607f69d3c.png)
### annotation
- `@Pointcut` 어노테이션에 포인트컷을 적고 함수 생성
- 함수를 어드바이스 어노테이션의 포인트컷 위치에 적어주면 참조한다.  
  
![image](https://user-images.githubusercontent.com/79209568/122201448-001b1f00-ced7-11eb-8431-9cc572c06552.png)


