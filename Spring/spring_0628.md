# 로그인
## 인증처리
#### AuthInfo.java
- 인증 정보를 저장할 클래스  
![image](https://user-images.githubusercontent.com/79209568/123582776-6417de80-d819-11eb-93f2-ed9c77b2da24.png)

#### AuthService.java
- 인증을 처리할 클래스  
![image](https://user-images.githubusercontent.com/79209568/123582690-36329a00-d819-11eb-8041-94fb03d39aea.png)

#### Member.java
- Member클래스에 패스워드가 맞는지 확인하는 matchPassword 메서드 추가  
  
  ![image](https://user-images.githubusercontent.com/79209568/123582658-2450f700-d819-11eb-951f-b6749be013dc.png)
  
#### LoginCommand.java
- 로그인 요청에 사용 될 커맨드 객체  
![image](https://user-images.githubusercontent.com/79209568/123864609-ead9d200-d965-11eb-81cf-78f051a923bc.png)

## 검증처리
#### LoginCommandValidatitor.java
![image](https://user-images.githubusercontent.com/79209568/123583672-fcfb2980-d81a-11eb-85ec-8fbb529fa3c5.png)

## 컨트롤러
#### LoginController.java
![image](https://user-images.githubusercontent.com/79209568/123584536-79dad300-d81c-11eb-9820-5543e76988af.png)

## 빈등록
#### spring-controller.xml
![image](https://user-images.githubusercontent.com/79209568/123586092-30d84e00-d81f-11eb-96dd-cf34c9e9144f.png)

## 뷰 생성
#### loginForm.jsp
- 로그인 폼  
![image](https://user-images.githubusercontent.com/79209568/123587580-73028f00-d821-11eb-9bfb-9c436db5d4a8.png)

#### loginSuccess.jsp
- 로그인 성공 폼  
![image](https://user-images.githubusercontent.com/79209568/123587601-77c74300-d821-11eb-9ad1-bdd855aef9b6.png)

#### label.properties
![image](https://user-images.githubusercontent.com/79209568/123864760-165cbc80-d966-11eb-8fd5-cac80bbe2309.png)


## 서버 실행 결과
![image](https://user-images.githubusercontent.com/79209568/123587651-8c0b4000-d821-11eb-8186-6266852a049c.png)
![image](https://user-images.githubusercontent.com/79209568/123587666-9299b780-d821-11eb-83e6-fa600da3e31c.png)

<hr>

# 세션 유지
#### LoginController.java
![image](https://user-images.githubusercontent.com/79209568/123588494-cd501f80-d822-11eb-80e5-dc2e128bb6c9.png)

# 로그아웃
## 컨트롤러
#### logoutController.java
![image](https://user-images.githubusercontent.com/79209568/123588619-fec8eb00-d822-11eb-845d-6fbd9c0fcd2b.png)

## 빈 등록
#### spring-controller.xml
![image](https://user-images.githubusercontent.com/79209568/123588652-0be5da00-d823-11eb-9e08-4a04024522c6.png)

## 메인 수정
#### main.jsp
- authInfo에 값이 없다면 = 로그인하지 않은 상태인 경우 **회원가입과 로그인 메뉴**
- authInfo에 값이 있다면 = 로그인 한 상태인 경우 **로그아웃 메뉴**  
  
  ![image](https://user-images.githubusercontent.com/79209568/123864474-c41b9b80-d965-11eb-9b67-50a2b3279ac1.png)
