# 비밀번호 변경
## 커맨드 객체
#### ChangePwdCommand
- 비밀번호 변경에 사용될 커맨드 객체  
![image](https://user-images.githubusercontent.com/79209568/123865649-2628d080-d967-11eb-95ea-f2c036b26e35.png)

## 검증처리
#### ChangePwdCommandValidator
- 검증 클래스  
![image](https://user-images.githubusercontent.com/79209568/123866120-a818f980-d967-11eb-8ee4-42d5b99c2e58.png)

## 컨트롤러
#### ChangePwdController
- ChangePasswordService 객체를 의존 주입
- 변경 성공하면 edit/changePwdSuccess로 이동
- 변경 실패하면 다시 edit/changePwdForm으로 이동
  - 입력 칸이 비었을 경우 실패
  - 현재 비밀번호가 틀렸을 경우 실패
  - 현재 비밀번호와 새로운 비밀번호를 같은 값을 입력하면 실패
![image](https://user-images.githubusercontent.com/79209568/123876057-ac4c1380-d975-11eb-9113-4b46efa57aae.png)

## 빈 등록
#### spring-controller.xml
![image](https://user-images.githubusercontent.com/79209568/123876470-5e83db00-d976-11eb-94c7-c24bcf66005d.png)

## 뷰 생성
#### changePwdForm
![image](https://user-images.githubusercontent.com/79209568/123876557-807d5d80-d976-11eb-87f7-595e093f3db5.png)

#### changePwdSuccess
![image](https://user-images.githubusercontent.com/79209568/123876598-912dd380-d976-11eb-8f3e-22459796aea8.png)

## 메시지 등록
![image](https://user-images.githubusercontent.com/79209568/123876662-ac98de80-d976-11eb-9d1d-dbf5038ec79c.png)

## 메인 수정
#### main
![image](https://user-images.githubusercontent.com/79209568/123876703-c20e0880-d976-11eb-9201-7a29681803a2.png)

## 동작
- 로그인하지 않은 상태로 `/main`  
  
  ![image](https://user-images.githubusercontent.com/79209568/123876792-e9fd6c00-d976-11eb-9b1b-ab33723e123d.png)
  - 로그인 클릭 시 `/login`으로 이동
    - 이메일과 비밀번호 입력 후 로그인하기 클릭  
    
    ![image](https://user-images.githubusercontent.com/79209568/123876852-0ac5c180-d977-11eb-98fa-f862494bdb0f.png)
    - 로그인 성공  
    
    ![image](https://user-images.githubusercontent.com/79209568/123876904-2761f980-d977-11eb-8d09-69a4cfa6d769.png)
    - 로그인 실패  
    
    ![image](https://user-images.githubusercontent.com/79209568/123876939-3648ac00-d977-11eb-9e05-522b4d001e2d.png)
- 로그인 한 상태로 `/main`
  - 비밀번호 변경 클릭 시 `/edit/changePassword`로 이동
    - 비밀번호 변경 성공  
    
    ![image](https://user-images.githubusercontent.com/79209568/123877230-c4249700-d977-11eb-9305-ffda1153c9b7.png)
    - 빈 상태로 버튼 클릭 : `필수 입력` 메시지 나옴  
    
    ![image](https://user-images.githubusercontent.com/79209568/123877071-7871ed80-d977-11eb-8265-c24722539c8e.png)
    - 현재 비밀번호가 틀렸을 경우 : `현재 비밀번호가 맞지 않음` 메시지 나옴
    
    ![image](https://user-images.githubusercontent.com/79209568/123877116-93446200-d977-11eb-8690-92b3cd1795ca.png)
    - 현재 비밀번호와 같은 새로운 비밀번호를 입력했을 경우 : `현재 비밀번호와 다른 암호를 사용하세요` 메시지 나옴
    
    ![image](https://user-images.githubusercontent.com/79209568/123877175-afe09a00-d977-11eb-8370-11623ec368cd.png)
  - 로그아웃 클릭 시 로그아웃 됨
