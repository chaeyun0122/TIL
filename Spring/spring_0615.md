# AOP 예제 2

> - 프로젝트명 : examspring06 ([👉project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/examspring06))
> - [aop001](#) : 전체 작업
> - [aop002](#) : Before

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
  - 
![image](https://user-images.githubusercontent.com/79209568/121994824-eeefe680-cde0-11eb-9a9a-e5609239cf8d.png)
  
#### aop002.xml
![image](https://user-images.githubusercontent.com/79209568/121994460-45a8f080-cde0-11eb-8000-388cd375f525.png)

#### Main
![image](https://user-images.githubusercontent.com/79209568/121994499-5a858400-cde0-11eb-944f-376e690a55c4.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/121994712-bea84800-cde0-11eb-835c-dcc4cdb2bada.png)
