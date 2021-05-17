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
* path value : 경로를 파라미터 값처럼 사용하는 것

#### index.html
```html
<li>GET 요청 예제 2:
    <a href="get2/10/20/hello/">get2/10/20/hello/</a> 
</li>
```

#### exurls.py
* `get2` 경로를 추가해주는데 경로 변수 처리를 해준다.
```python
path('get2/<int:n1>/<int:v2>/<str:n3>/', views.req_get2)
```

#### views.py
* request와 함께 받을 경로 변수들의 이름을 적어줘야한다.
```python
def req_get2(request, n1, v2, n3):
    # 경로 변수 처리
    print('n1:' + str(n1))
    print('v2:' + str(v2))
    print('n3:' + str(n3))
    return HttpResponse('get2')
```
![image](https://user-images.githubusercontent.com/79209568/118436908-9a434800-b71c-11eb-9654-8559ac9b08dd.png)  
![image](https://user-images.githubusercontent.com/79209568/118436918-9dd6cf00-b71c-11eb-8080-6592157599c2.png)

### POST 요청 예제 1
#### index.html
```html
<li>POST 요청 예제 1: <br>
    <form action='post1/' method="POST">
        {% csrf_token %}
        <input type="text" name="n1"><br>
        <input type="text" name="n2"><br>
        <input type="submit" value="POST요청">
    </form>
</li>
```

#### exurls.py
```python
path('post1/', views.req_post1),
```

#### views.py
```python
def req_post1(request):
    paramNames = request.POST.keys() 
    for paramName in paramNames:
        paramValue = request.POST[paramName]
        print('%s : %s' % (paramName, paramValue))

    return HttpResponse('post1')
```
![image](https://user-images.githubusercontent.com/79209568/118439039-36bb1980-b720-11eb-9b4c-9739b7a2cf2b.png)  
![image](https://user-images.githubusercontent.com/79209568/118439047-391d7380-b720-11eb-8929-6bbb9b2b56fa.png)

### POST 요청 예제 2
* post요청에서의 경로 변수 사용
#### index.html
```html
<li>POST 요청 예제 2: <br>
    <form action="post2/test/hello/" method="POST">
        {% csrf_token %}
        <input type="text" name="n1"><br>
        <input type="text" name="n2"><br>
        <input type="submit" value="POST요청">
    </form>
</li>
```
#### exurls.py
```python
path('post2/<p1>/<p2>/', views.req_post2),
```
#### views.py
```python
def req_post2(request, p1, p2):
    print("p1:" + str(p1))
    print("p2:" + p2)
    paramNames = request.POST.keys() 
    for paramName in paramNames:
        paramValue = request.POST[paramName]
        print('%s : %s' % (paramName, paramValue))

    return HttpResponse('post2')
```
![image](https://user-images.githubusercontent.com/79209568/118440780-23f61400-b723-11eb-9cca-37dca3910a9f.png)  
![image](https://user-images.githubusercontent.com/79209568/118440808-2d7f7c00-b723-11eb-87ee-16fab0d95a4f.png)
![image](https://user-images.githubusercontent.com/79209568/118440864-45570000-b723-11eb-9d8e-e956284f1821.png)


### 요청 URL이 같을 때 하나의 함수에서 처리
#### index.html
* 둘 다 요청 url이 `getpost1/`로 같다.
```html
<li>GET 요청(하나의 함수형 뷰에서 처리):<br>
    <a href="getpost1/">getpost1/</a>
</li>
<li>POST 요청(하나의 함수형 뷰에서 처리):<br>
    <form action="getpost1/" method="post">
        {% csrf_token %}
        <input type="text" name="n1"><br>
        <input type="text" name="n2"><br>
        <input type="submit" value="POST요청">
    </form>
</li>
```
#### exurls.py
```python
path('getpost1/', view.getpost1),
```
#### views.py
```python
def getpost1(request):
    if request.method == 'GET':
        return HttpResponse("getpost1: GET 요청 처리 결과(용도:form응답)")
    elif request.method == 'POST':
        return HttpResponse("getpost1: POST 요청 처리 결과(용도:처리)")
```
![image](https://user-images.githubusercontent.com/79209568/118440275-63703080-b722-11eb-8972-efa9fbc08b35.png)  
* get요청  
  ![image](https://user-images.githubusercontent.com/79209568/118440288-68cd7b00-b722-11eb-95f2-a3bed85e6d35.png)  
* post요청  
  ![image](https://user-images.githubusercontent.com/79209568/118440300-6cf99880-b722-11eb-9361-ef265240156d.png)  
