# AOP
- aspect oriented programming (관점 지향 프로그래밍)

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






