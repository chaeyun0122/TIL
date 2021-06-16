# AOP 예제 2 _이어서
> - 프로젝트명 : examspring06 ([👉project file](https://github.com/Clary0122/TIL/blob/main/Spring/project/examspring06.zip))
> - [aop008](#Around) : Around - POJO class & XML
> - [aop009](#AfterReturning) : AfterReturning - POJO class & XML
> - [aop010](#AfterThrowing) : AfterThrowing - POJO class & XML
> - [aop011](#Annotation으로-변경) : Around - annotation
> - [aop012](#Annotation으로-변경) : AfterReturning - annotation
> - [aop013](#Annotation으로-변경) : AfterThrowing - annotation
> - aop014 : Around로 통합
> - [aop015](#Aspect-적용-순서) : Order - 다중 aspect의 순서

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

## Aspect 적용 순서
- 두 개 이상의 Aspect가 존재할 경우 어떤 Aspect가 우선 적용될 것인가?
> #### '문'을 통과한 후 '중문'을 통과하고 '핵심기능' 수행한다고 설계해보자.

#### MyAspect
- `문`을 통과하는 Aspect  
![image](https://user-images.githubusercontent.com/79209568/122209009-fc8b9600-cede-11eb-8e11-00b45311cbae.png)

#### MyAspect2
- `중문`을 통과하는 Aspect   
![image](https://user-images.githubusercontent.com/79209568/122209093-15944700-cedf-11eb-9ebf-fad25c620d2b.png)

#### aop015.xml
- MyAspect, MyAspect2 빈 등록
![image](https://user-images.githubusercontent.com/79209568/122209240-483e3f80-cedf-11eb-87f4-002a0650a71e.png)

#### 결과
- `중문`의 우선순위가 `문`의 우선순위보다 높다.
- **xml에 등록한 Bean 순서로 우선순위가 결정되는 것을 확인할 수 있다.**
![image](https://user-images.githubusercontent.com/79209568/122209857-f518bc80-cedf-11eb-8e7b-263011fe57a3.png)

### 우선순위 직접 지정
#### annotation
- `@Order(정수)` 어노테이션을 설정한다. 정수의 숫자가 작을수록 우선순위가 높다.  
  
![image](https://user-images.githubusercontent.com/79209568/122210683-dc5cd680-cee0-11eb-9dca-bad1d4114d1f.png)

#### XML
- `<aop:aspect>`의 `order` 속성으로 우선순위를 설정한다.  
  
![image](https://user-images.githubusercontent.com/79209568/122212377-b89a9000-cee2-11eb-910c-424417142e4d.png)

#### 다중 Aspect 우선순위를 정할 때 어드바이스가 around인 경우 순서가 뒤죽박죽되는 경우가 많다. (특히 어노테이션의 경우) 그러므로 around를 가장 많이 사용하지만 항상 around가 좋은 것은 아니다.
