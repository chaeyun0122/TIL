# 표현 언어 (EL : Expression Language)
> java 코드가 들어가는 표현식을 좀 더 편리하게 사용하기 위해 JSP 2.0 부터 도입된 데이터 출력 기능이다.

## 표현 언어의 특징
* 기존 표현식보다 편리하게 값을 출력할 수 있다.
* 변수와 여러가지 연산자를 포함할 수 있다.
* JSP의 내장 객체에 저장된 속성 및 자바의 bean 속성도 표현 언어에서 출력할 수 있다.

## 표현 언어의 형식
### `$(표현식 or 값)`

## 표현 언어에서 제공하는 내장 객체
### scope
* **pageScope** : JSP의 page와 같은 기능을 하고, page 영역에 바인딩 된 객체를 참조한다.
* **requestScope** : JSP의 request와 같은 기능을 하고, request에 바인딩 된 객체를 참조한다.
* **sessionScope** : JSP의 session과 같은 기능을 하고, session에 바인딩 된 객체를 참조한다.
* **applicationScope** : JSP의 application 같은 기능을 하고, application에 바인딩 된 객체를 참조한다.
### 요청 매개변수
* param : request.getParameter() 메서드를 호출한 것과 같으며 한 개의 값을 전달하는 요청 매개변수를 처리한다.
* paramValues : request.getParameterValues() 메서드를 호출한 것과 같으며 여러 개의 값을 전달하는 요청 매개변수를 처리한다.
* 
