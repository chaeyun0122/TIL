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
  * 전 후의 솔트값과 해시값이 다르다.  
    ![image](https://user-images.githubusercontent.com/79209568/120268189-589bcb00-c2e0-11eb-9948-92fafbe83960.png)  
    ![image](https://user-images.githubusercontent.com/79209568/120268196-5c2f5200-c2e0-11eb-8dd1-d1f23320df04.png)


    

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
  ![image](https://user-images.githubusercontent.com/79209568/120267539-1aea7280-c2df-11eb-9d3e-9d9509e04f3d.png)
* userpassword 는 모델을 통해 직접 변경하면 안된다.
  ```
  >>> # user.password='1234' 이렇게 하면 잘못된 비밀번호 형식이거나 알 수 없는 해싱 알고리즘 입니다 오류남
  ```

## 메인페이지 생성
#### views.py
* `request.user`에 현재 로그인한 user에 대한 정보가 있다.
* `is_authenticated` 함수를 통해 현재 로그인 되어있는지 확인 가능하다.
```python
def main(request):
    username = request.user
    print('request:user:%s' % request.user)
    print('is_auth:', request.user.is_authenticated)
    context = {
        'username' : username
    }

    return render(request, 'account/main.html', context)
```

#### login_main.html
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원관리메인</title>
</head>
<body>
    <h2>메인 페이지 입니다.</h2>
    {{ username }}으로 로그인 되었습니다.
</body>
</html>
```
#### 결과
> ![image](https://user-images.githubusercontent.com/79209568/120268509-e8da1000-c2e0-11eb-918a-960a3981a726.png)

## 회원가입
#### views.py
* `signup_form` : django에서 제공하는 `UserCreationForm`을 통해 회원가입 폼을 생성한다.
* `signup` : 회원가입 동작을 하는 함수를 생성한다.
  * signup_form을 통해 받은 값들이 유효하면 `signup success!`를 프린트하고 메인으로 돌아간다.
  * 회원가입이 실패하면 입력했던 값들을 그대로 받아서 다시 signup_form을 렌더한다.
```python
# 회원가입 폼 만들기
from django.contrib.auth.forms import UserCreationForm

def signup_form(request):
    context = {
        'signup_form' : UserCreationForm(),
    }

    return render(request, 'account/signup_form.html', context)

# 회원가입 동작
from django.shortcuts import redirect # HttpRedirect와 같은 기능

def signup(request):
    signup_form = UserCreationForm(request.POST)
    print(signup_form)

    if signup_form.is_valid():
        signup_form.save()
        print('signup success!')
        return redirect('account:main')
    else:
        print('signup failed!')
        context = {
            'signup_form' : signup_form,
        }
        return render('account/signup_form.html', context)
```
#### signup_form.html
* 테이블 형식으로 signup_form을 보인다.
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
</head>
<body>
    <form action="{% url 'account:signup' %}" method="post">
        {% csrf_token %}
        <table border="1">
            {{ signup_form.as_table }}
        </table><br>
        <input type="submit" value="가입">
    </form>
</body>
</html>
```
#### main.html
* 회원가입 페이지로 넘어가는 버튼을 생성한다.
```html
<a href="{% url 'account:signup_form' %}"><button>회원가입</button></a>
```

#### 결과
> * 메인에서 회원가입 버튼 클릭
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120270072-ecbb6180-c2e3-11eb-8858-9393f081ce32.png)
> * 사용자 이름과 비밀번호 입력. tester2/test@1234
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120270131-0492e580-c2e4-11eb-9063-75ea57d50d6a.png)
> * admin 사이트에서 tester2가 생성된 것을 확인
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120270171-17a5b580-c2e4-11eb-9ede-729df1e0ee81.png)

## 로그인
#### views.py
```python
# 로그인 폼 만들기
from django.contrib.auth.forms import AuthenticationForm

def login_form(request):
    context = {
        'login_form' : AuthenticationForm(),
    }

    return render(request, 'account/login_form.html', context)

# 로그인 동작
## django에서 지원하는 모듈의 이름과 정의한 함수의 이름이 같을 때 모듈에 별칭을 지정해서 구분해줄 수 있다.
from django.contrib.auth import login as auth_login

def login(request):
    login_form = AuthenticationForm(request, request.POST)
    print(login_form)

    if login_form.is_valid():
        # 로그인 상태로 저장
        auth_login(request, login_form.get_user())
        print('login success!')
        return redirect('account:main')
    else:
        print('login failed!')
        context = {
            'login_form' : login_form,
        }
        return render(request, 'account/login_form.html', context)
```

#### login_form.html
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
</head>
<body>
    <form action="{% url account:login %}" method="post">
        {% csrf_token %}
        {{ login_form.as_p }}
        <input type="submit" value="로그인">
    </form>
</body>
</html>
```

#### main.html
* 로그인 페이지로 넘어가는 버튼을 생성한다.
```html
<a href="{% url 'account:login_form' %}"><button>로그인</button></a>
```

#### 결과
> * 로그인 버튼 클릭. 현재 로그인 되어있는 계정이 없으므로 익명사용자로 로그인 되어있다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120273780-e8924280-c2e9-11eb-96c7-97b0a7385175.png)
> * tester2/test@1234 로그인
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120273812-f47e0480-c2e9-11eb-9cf8-5db35fa62436.png)
> * 메인페이지로 넘어가며 tester2로 로그인 된 것을 확인할 수 있다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120273854-05c71100-c2ea-11eb-87fa-560ad71ff0ae.png)

## 로그아웃
#### views.py
```python
from django.contrib.auth import logout as auth_logout

def logout(request):
    auth_logout(request)
    return redirect('account:main')
```

#### main.html
* 로그아웃을 하는 넘어가는 버튼을 생성한다.
```html
<a href="{% url 'account:logout' %}"><button>로그아웃</button></a>
```

> * ![image](https://user-images.githubusercontent.com/79209568/120274441-d1078980-c2ea-11eb-9b82-11a3b39edb8c.png)
> * ![image](https://user-images.githubusercontent.com/79209568/120274427-ccdb6c00-c2ea-11eb-9709-0d70912438ce.png)

## 메인 화면에 로그인 상태에 따라 메뉴 설정
* 로그인 된 상태에선 **로그아웃**을 뜨게한다.
* 로그인 되지 않은 상태에선 **로그인**과 **회원가입**을 뜨게한다.

#### views.py
* `main` 함수에 익명 계정의 경우 username의 값을 빈 값으로 설정하는 코드를 추가한다.
```python
if username.is_anonymous: # 익명 계정일 경우
        username = None
```

#### main.html
* html body를 해당 내용으로 수정한다.
* username이 존재할 경우(로그인 된 상태일 경우) username과 로그아웃 버튼을 띄운다.
* username이 존재하지 않을 경우(로그인 되지 않은 상태일 경우) 회원가입과 로그인 버튼을 띄운다.
```html
<h2>메인 페이지 입니다.</h2>
{% if username %}
    {{ username }}으로 로그인 되었습니다.<br><br>
    <a href="{% url 'account:logout' %}"><button>로그아웃</button></a>
{% else %}
    <a href="{% url 'account:signup_form' %}"><button>회원가입</button></a>
    <a href="{% url 'account:login_form' %}"><button>로그인</button></a>
{% endif %}
```

## 마이페이지
* 로그인 된 상태에서 마이페이지가 보이도록한다.
### is_authenticated 사용
#### views.py
```python
# 마이페이지1
def mypage1(request):
    if request.user.is_authenticated:
        context = {
            'username' : request.user,
        }
        return render(request, 'account/mypage1.html', context)
    
    return redirect('account:main')
```
### 데코레이터 사용
#### views.py
```python
# 마이페이지2
## 하나하나 코드에 적었던 인증하는 개념을 데코레이터로 적을 수 있다.
from django.contrib.auth.decorators import login_required

@login_required(login_url='account:login_form') # 인증이 실패했을 때 지정된 페이지로 이동하게 만듦
def mypage2(request):
    context = {
            'username' : request.user,
        }
    return render(request, 'account/mypage1.html', context)
```

#### main.html
```html
<h2>메인 페이지 입니다.</h2>
{% if username %}
    {{ username }}으로 로그인 되었습니다.<br><br>
    <a href="{% url 'account:mypage1' %}"><button>마이페이지1</button></a>
    <a href="{% url 'account:mypage2' %}"><button>마이페이지2</button></a>
    <a href="{% url 'account:logout' %}"><button>로그아웃</button></a>
{% else %}
    <a href="{% url 'account:signup_form' %}"><button>회원가입</button></a>
    <a href="{% url 'account:login_form' %}"><button>로그인</button></a>
    <a href="{% url 'account:mypage1' %}"><button>마이페이지1(불가)</button></a>
    <a href="{% url 'account:mypage2' %}"><button>마이페이지2(불가)</button></a>
{% endif %}
```

#### mypage1.html
* mypage2.html도 같음
```html
마이페이지1<br>
{{ username }}의 정보<br>
<a href="{% url 'account:logout' %}"><button>로그아웃</button></a>
```

#### 결과
> #### 마이페이지1(is_authenticated 사용)
> * 로그인 되지 않은 상태
>   ![image](https://user-images.githubusercontent.com/79209568/120277010-6f491e80-c2ee-11eb-87b2-2639a8fe3972.png)
>   * 다시 메인페이지로 돌아온다.
> * 로그인 된 상태
>   ![image](https://user-images.githubusercontent.com/79209568/120277182-b0d9c980-c2ee-11eb-81f9-6f9a233fdfa8.png)
>   * 마이페이지 누르면 username과 로그아웃 버튼이 나옴
>     ![image](https://user-images.githubusercontent.com/79209568/120277222-bdf6b880-c2ee-11eb-8c23-5f388cccfbfd.png)
>  
> #### 마이페이지2(데코레이터 사용)
> * 로그인 되지 않은 상태
>   ![image](https://user-images.githubusercontent.com/79209568/120277336-e383c200-c2ee-11eb-9128-ad702669201f.png)
>   * login_form으로 돌아간다.
> * 로그인 된 상태
>   * 마이페이지1과 동일하다.
