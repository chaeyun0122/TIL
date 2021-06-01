> ## 새 프로젝트
> 1. 프로젝트 명 : project08
> 2. 앱 추가 : acccount
> 3. admin 계정 생성 : admin/1234

# 계정 설정
## admin 페이지
* 사용자(들) 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/120264030-e1623900-c2d7-11eb-9d88-16ce1a51a8b1.png)
* admin 계정
  
  ![image](https://user-images.githubusercontent.com/79209568/120264054-f048eb80-c2d7-11eb-89b9-0c85d592544c.png)

  > * **암호화 != 해싱**
  > * **암호화**
  >   - 암호화는 복호화가 가능
  > * **해싱**
  >   - 해싱된 결과는 원래 데이터를 알아볼 수 없게한다.
  >   - 해시함수(일방향 함수)를 통해 만든다. 
  >   - 원래의 데이터로 복호화할 수 없다. (거의 불가능 - 레인보우 테이블로 알아낼 수는 있다.)
  >   - 길이는 항상 일정하다.
  > * 해싱을 통해 만든 암호는 레인보우 테이블(모든 해싱값들을 적어놓은 테이블)을 통해 복호화가 가능하다.
  > * **솔트**
  >   - 해싱된 값에 랜덤 값의 솔트를 포함하여 암호화한다. 
- 암호화/복호화
- 해싱함수/해시테이블
- 솔트
- 레인보우테이블

### 새 사용자 추가
* admin 페이지 사용자(들)에서 사용자 추가 클릭
  ![image](https://user-images.githubusercontent.com/79209568/120266045-16708a80-c2dc-11eb-9f08-08d98302edaa.png)
* 사용자 이름을 tester, 비밀번호를 test@1234로 지정한다.
  ![image](https://user-images.githubusercontent.com/79209568/120266465-ec6b9800-c2dc-11eb-88ae-2fa03c45a0d3.png)
* 생성된 사용자 계정은 스태프 권한이 없다.
  ![image](https://user-images.githubusercontent.com/79209568/120266491-fa211d80-c2dc-11eb-954d-6ca1dcf60b9c.png)
* tester 사용자를 확인해보면 비밀번호의 솔트와 해시값이 admin과 다른 것을 확인할 수 있다.
  ![image](https://user-images.githubusercontent.com/79209568/120266518-0efdb100-c2dd-11eb-88fd-50b061a3f5b6.png)
* python manage.py를 통해 비밀번호 변경 가능
  ```
  python manage.py changepassword tester
  ```
  ![image](https://user-images.githubusercontent.com/79209568/120267071-2e490e00-c2de-11eb-8236-72e88740fd6c.png)

## Shell
* contrib.auth.models의 User를 import 해준다.
  ```
  >>> from django.contrib.auth.models import User
  ```
* 방금 생성한 tester를 가져와서 user변수에 넣어준다.
  ```
  >>> user = User.objects.get(username='tester') 
  >>> user
  <User: tester>
  ```
* username, userpassword, useremail 확인 가능
  ```
  >>> user.username
  'tester'
  >>> user.password
  'pbkdf2_sha256$150000$ZAyLdejtWzTA$UOiMIYTZmhTjPw6B0tSMXblhgq1REahBurB3OBdEVRM='
  >>> user.email
  ''
  ```
  * userpassword 확인
    * `pbkdf2_sha256` : 해당 알고리즘을 사용
    * `150000`: 반복 횟수
    * `ZAyLdejtWzTA` : 솔트 값
    * `UOiMIYTZmhTjPw6B0tSMXblhgq1REahBurB3OBdEVRM=` : 해싱 값
    * sha256 해시 알고리즘을 통해 설정했던 123@test' 암호를 솔트 값과 합쳐서 해싱을 150000번 반복한 값이 해싱 값이다. 
* username 변경 가능
  ```
  >>> user.username='tester1'
  >>> user.username
  'tester1'
  >>> user.save()
  ```
  

