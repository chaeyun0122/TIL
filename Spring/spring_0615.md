# AOP 예제 2

> - 프로젝트명 : examspring06 ([👉project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/examspring06))
> - [aop001](#전체) : 전체 작업
> - [aop002](#Advice-Annotation) : Before - annotation
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
  
![image](https://user-images.githubusercontent.com/79209568/121994824-eeefe680-cde0-11eb-9a9a-e5609239cf8d.png)
  
#### aop002.xml
![image](https://user-images.githubusercontent.com/79209568/121994460-45a8f080-cde0-11eb-8000-388cd375f525.png)

#### Main
![image](https://user-images.githubusercontent.com/79209568/121994499-5a858400-cde0-11eb-944f-376e690a55c4.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121994712-bea84800-cde0-11eb-835c-dcc4cdb2bada.png)

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


