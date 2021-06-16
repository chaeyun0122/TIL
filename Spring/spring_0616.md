> - 프로젝트명 : examspring06 ([👉project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/examspring06))
> - [aop001](#) : 전체 작업
> - [aop002](#) : Before - annotation
> - [aop003](#) : Before - POJO class & XML
> - [aop004](#) : Before, After - POJO class & XML
> - [aop005](#) : Before, After - annotation
> - [aop006](#) : @Pointcut - annotation
> - [aop007](#) : @Pointcut - POJO class & XML
> - [aop008](#) : Around - POJO class & XML
> - [aop009](#) : AfterReturning - POJO class & XML
> - [aop010](#) : AfterThrowing - POJO class & XML
> - [aop011](#) : Around - annotation
> - [aop012](#) : AfterReturning - annotation
> - [aop013](#) : AfterThrowing - annotation

## Around
#### MyAspect
![image](https://user-images.githubusercontent.com/79209568/122160740-6ee28300-ceab-11eb-953e-c45da258a265.png)

#### aop008.xml
![image](https://user-images.githubusercontent.com/79209568/122160990-db5d8200-ceab-11eb-9323-65b1f7886e8b.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/122156002-10190b80-cea3-11eb-88ad-7b768e4af902.png)

## AfterReturning
#### MyAspect
- 아래 추가  
![image](https://user-images.githubusercontent.com/79209568/122161050-f8925080-ceab-11eb-8dcd-be56c163fc6d.png)

#### aop009.xml
![image](https://user-images.githubusercontent.com/79209568/122161105-0fd13e00-ceac-11eb-8715-7a0dba2118b0.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/122156425-e7454600-cea3-11eb-90f5-ec4b06d2ff11.png)

## AfterThrowing
#### MyAspect
- 아래 추가  
![image](https://user-images.githubusercontent.com/79209568/122161158-29728580-ceac-11eb-844a-622f6796c5d7.png)

#### Boy
- 예외 추가  
![image](https://user-images.githubusercontent.com/79209568/122161191-3d1dec00-ceac-11eb-8a73-748f6d7d6cb8.png)

#### aop010.xml
![image](https://user-images.githubusercontent.com/79209568/122161226-4d35cb80-ceac-11eb-957a-7be376e0e5e6.png)

#### 결과
![image](https://user-images.githubusercontent.com/79209568/122157074-2d4ed980-cea5-11eb-8e57-b5b0f15e47c1.png)

## 결국 around에 다 적용할 수 있다.
![image](https://user-images.githubusercontent.com/79209568/122157277-8f0f4380-cea5-11eb-82a3-7e854814a479.png)
![image](https://user-images.githubusercontent.com/79209568/122157318-9c2c3280-cea5-11eb-826d-d6268ba5ac6a.png)

## Annotation으로 변경
> ### Annotation으로 변경시 우선순위가 xml과 달라서 순서가 다를 수 있다.
- around  
  ![image](https://user-images.githubusercontent.com/79209568/122163233-c84cb100-ceaf-11eb-9e05-ec2b5599a227.png)
- after-returning  
  ![image](https://user-images.githubusercontent.com/79209568/122163310-efa37e00-ceaf-11eb-8c84-b324a1d21e0c.png)
- after-throwing  
  ![image](https://user-images.githubusercontent.com/79209568/122163517-4b6e0700-ceb0-11eb-98e8-5e67f6142e56.png)

## Aspect 적용 순서 - @Order(정수) (aop015)
- 정수 값이 작을 수록 aspect의 우선순위가 높다.
- 핵심 기능을 감싸는 개념으로 우선순위가 정해진다.
