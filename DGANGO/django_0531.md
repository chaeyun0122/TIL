# 쿠기(cookie)
- 서버에서 생성하고 응답해주면 브라우저에 저장되는 값
- ex) 비회원 장바구니 / 아이디 기억하기 등에 활용
- 세션쿠키 : 세션은 서버에서 브라우저를 구분하기 위해 존재하는 정보
- 유효기간이 정해진 쿠키 : 유지 시간을 지정해서 쿠키가 유지되는 기간을 정해놓은 쿠키

> ## 새 프로젝트
> 1. 프로젝트 명 : project07
> 2. 앱 추가 : exam_cookie
> 3. admin 계정 생성 : admin/1234

#### set cookie
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




