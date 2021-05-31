# 쿠기(cookie)
- 서버에서 생성하고 응답해주면 브라우저에 저장되는 값
- ex) 비회원 장바구니 / 아이디 기억하기 등에 활용
- 세션쿠키 : 세션은 서버에서 브라우저를 구분하기 위해 존재하는 정보
- 유효기간이 정해진 쿠키 : 유지 시간을 지정해서 쿠키가 유지되는 기간을 정해놓은 쿠키

> ## 새 프로젝트
> 1. 프로젝트 명 : project07
> 2. 앱 추가 : exam_cookie
> 3. admin 계정 생성 : admin/1234

## set cookie
```python
# views.py
from django.shortcuts import render

# Create your views here.
def index(request):
    print(request.COOKIES)
    cookie_name = 'sessioncookie'
    cookie_val = 'sessioncookievalue'
    response = render(request, 'exam_cookie/index.html')
    response.set_cookie(cookie_name, cookie_val)
    
    return response
```
- response에 `set_cookie`함수로 쿠키 설정을 한다.

> * 처음 요청을 했을 때 최상위 경로일 때마다 요청한다는 sessioncookievalue의 값을 가진 이름이 sessioncookie인 쿠키가 생성된다.  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120140177-ee6a2400-c214-11eb-8e34-37e0a9c0e598.png)  
>   ![image](https://user-images.githubusercontent.com/79209568/120140290-2ec9a200-c215-11eb-8519-22858c70ffb4.png)
> * 첫 번째 요청에서는 쿠키 값이 프린트되지 않는다. (쿠키 생성)  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120140379-68021200-c215-11eb-833d-c8a3a5941c16.png)  
> * 두 번째 요청에서 설정한대로 쿠키가 요청되므로 쿠키 값이 프린트 된다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120140475-98e24700-c215-11eb-88cc-c7251e0a59c6.png)

## 로그인 동작
#### login.html
* 로그인 html을 만든다.
* 아이디, 비밀번호, 아이디 기억하기 체크박스를 만든다.
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
</head>
<body>
    <form method="POST">
        {% csrf_token %}
        <input type="text" name="id" placeholder="아이디" value="{{ userid }}"><br>
        <input type="password" name="pw" placeholder="비밀번호"><br>
        <input type="checkbox" name="idCheck" value="1" {% if userid %}checked{% endif %}>아이디 기억하기<br>
        <input type="submit" value="로그인"><br>
    </form>
</body>
</html>
```
#### views.py
* login 동작 함수를 정의한다.
* 만약 POST 방식으로 들어온다면, id, pw, idCheck를 받아온다. 제대로 받아온다면 `로그인완료`를 리스폰한다.
  * idCheck를 체크해서 value값이 있다면, response에 id 쿠키를 생성한다.
  * idCheck를 체크하지 않아서 value값이 없다면, id쿠키를 삭제하도록한다.
  ```python
    userid = request.POST['id']
    userpw = request.POST['pw']
    # idCheck = request.POST['idCheck']
    idCheck = request.POST.get('idCheck')
    print(userid)
    print(userpw)
    print(idCheck)
    resp = response.HttpResponse('로그인완료')

    # 쿠키 만들기
    if idCheck != None:
        resp.set_cookie('userid', userid)
    # 쿠키 제거하기
    else:
        resp.delete_cookie('userid')

    return resp
  ```
* 만약 GET 방식으로 들어온다면, userid라는 쿠키를 받아온다. 그리고 login.html으로 userid의 값을 넘긴다.
  ```python
    userid = request.COOKIES.get('userid', '')
    print(userid)
    return render(request, 'exam_cookie/login.html', {'userid':userid})
  ```
* 전체 코드
    ```python
    def login(request):
        if request.method == 'GET':
            userid = request.COOKIES.get('userid', '')
            print(userid)
            return render(request, 'exam_cookie/login.html', {'userid':userid})
        else:
            userid = request.POST['id']
            userpw = request.POST['pw']
            # idCheck = request.POST['idCheck']
            idCheck = request.POST.get('idCheck')
            print(userid)
            print(userpw)
            print(idCheck)
            resp = response.HttpResponse('로그인완료')

            # 쿠키 만들기
            if idCheck != None:
                resp.set_cookie('userid', userid)
            # 쿠키 제거하기
            else:
                resp.delete_cookie('userid')

            return resp
    ```

# session
```
def index(request):
    print(request.COOKIES)
    # cookie_name = 'sessioncookie'
    # cookie_val = 'sessioncookievalue'

    print(request.session) # SessionStore라는 객체가 존재한다.
    sessionid = request.COOKIES.get('sessionid', '')
    request.session['test'] = 'sessionvalue' # 서버에 test라는 키값으로 sessionvalue가 추가되었다.
    # 브라우저를 식별하기 위해 세션 아이디라는 쿠키 값을 만든다.(서버에 저장되는 값) 

    response = render(request, 'exam_cookie/index.html', {'sessionid':sessionid})
    # response.set_cookie(cookie_name, cookie_val)
    
    return response
```
![image](https://user-images.githubusercontent.com/79209568/120147041-021b8780-c221-11eb-8d69-ba8d5251ee3a.png)

* `로그인 상태 : {{ request.session.login }}<br>` : login이라는 세션 키 값에 로그인 상태를 기록한 것이다. 
