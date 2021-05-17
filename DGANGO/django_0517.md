# 새 프로젝트
> 1. 프로젝트 명 : project02  
    `django-admin startproject mysite`  
    `project02로 이름 변경`  
> 2. 앱 추가 : exam_request  
    `python manage.py startapp exam_request`  
> 3. url 파일 추가 : exurls.py  

 ## request 정보
 #### views.py
 ```python
 from django.shortcuts import render
from django.http.response import HttpResponse

# Create your views here.
def index(request):
    print('SERVER:브라우저가 요청을 보냄')
    print(request.path)
    print(request.method)
    print(request.content_type)
    print(request.get_host())
    print(request.get_port())
    # return HttpResponse('Hello Django!')
    return render(request, 'exam_request/index.html')
 ```
 ![image](https://user-images.githubusercontent.com/79209568/118433914-0753df00-b717-11eb-82e3-5b25decabb3c.png)

### 파라미터 보내기
### GET 요청 예제 1
#### index.html
```html
<!-- templates/exam_request/index.html -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
</head>
<body>
<h1>Exam Request Main</h1>
<ol>
        <li>GET 요청 예제 1:
            <a href="get1/?n1=10&n2=20&n3=hello">get1/?n1=10&n2=20&n3=hello</a>
        </li>
</ol>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/79209568/118434516-2e5ee080-b718-11eb-820d-60e8df9309f9.png)  
![image](https://user-images.githubusercontent.com/79209568/118434530-31f26780-b718-11eb-9260-4ef2b6ab1a05.png)

#### exurls.py
* `get1/` 경로 추가
```python
from django.urls import path
from exam_request import views

urlpatterns = [
    path('', views.index),
    path('hello/', views.index),
    path('main/', views.main),
    path('get1/', views.req_get1),
]
```

#### views.py
```python
def req_get1(request):
    paramNames = request.GET.keys()  # [n1, n2, n3] 리스트 반환
    for paramName in paramNames:
        paramValue = request.GET[paramName]
        print('%s : %s' % (paramName, paramValue))

    return HttpResponse('get1')
```
* 해당 경로(get1/?n1=10&n2=20&n3=hello)로 서버 실행 시 request의 GET에 딕셔너리 형태로 저장된 파라미터들을 출력  
  ![image](https://user-images.githubusercontent.com/79209568/118435343-c8735880-b719-11eb-9c92-9951d0f476ec.png)

### GET 요청 예제 2
