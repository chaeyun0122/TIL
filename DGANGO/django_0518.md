## Request 이해하기 (클래스형 뷰)
### GET 요청 예제 3
#### index.html
```html
<li>GET 요청 예제 3:<br>
    <a href="get3/?n1=10&n2=20&n3=welcome">get3/?n1=10&n2=20&n3=welcome</a>
</li>
```
#### views.py
* 클래스 생성
```python
class ExamGet3(View): # 클래스는 객체를 정의한 자료형
    def get(self, request):
        names = request.GET.keys()
        for name in names:
            value = request.GET[name]
            print('%s : %s' % (name, value))
        
        return HttpResponse("get3")
```
#### exurls.py
* as_view()로 클래스 사용
```python
path('get3/', views.ExamGet3.as_view()), # 클래스형 뷰 사용 설정
```

![image](https://user-images.githubusercontent.com/79209568/118588412-4d717700-b7d9-11eb-809b-547dc6699292.png)
![image](https://user-images.githubusercontent.com/79209568/118588416-4f3b3a80-b7d9-11eb-856d-d15967872563.png)

### GET 요청 예제 4
#### index.html
```html
<li>GET 요청 예제 4:<br>
    <a href="get2/10/20/hello/">get2/10/20/hello/</a> 
</li>
```
#### views.py
```python
class ExamGet4(View):
    def get(self, request):
        print("n1: " + str(n1))
        print("n2: " + n2)
        print("n3: " + n3)

        return HttpResponse("get4")
```
#### exurls.py
* as_view()로 클래스 사용
```python
path('get2/<n1>/<str:v2>/<str:n3>/', views.ExamGet4.as_view()),
```
![image](https://user-images.githubusercontent.com/79209568/118589552-8b6f9a80-b7db-11eb-99f5-32c6619c7515.png)
![image](https://user-images.githubusercontent.com/79209568/118589575-97f3f300-b7db-11eb-9b2c-4126af75c512.png)

### POST 요청 예제 3
#### index.html
```html
<li>POST 요청 예제 3: <br>
    <form action='post1/' method="POST">
        {% csrf_token %}
        <input type="text" name="n1"><br>
        <input type="text" name="n2"><br>
        <input type="submit" value="POST요청">
    </form>
</li>
```

#### views.py
```python
class ExamPost3(View):
    def post(self, request): # View 안에 함수 이름을 통해서 post인지 get인지 결정
        names = request.POST.keys()
        for name in names:
            value = request.POST[name]
            print('%s : %s' % (name, value))
        
        return HttpResponse("post3")
    
    # get 방식으로 해당 url을 요청하면 405 에러가 난다.
    # 따라서 get 함수를 만들어서 '/exam_request/'으로 가도록 한다.
    def get(self, request):
        return HttpResponseRedirect('/exam_request/') # 결과 페이지를 보여줄 필요 없을 때 다른 곳으로 이동하도록 하는 것
```

#### exurls.py
```python
path('post3/', views.ExamPost3.as_view()),
```

### POST 요청 예제 4
#### index.html
```html
<li>POST 요청 예제 4: <br>
    <form action="post2/test/hello/" method="POST">
        {% csrf_token %}
        <input type="text" name="n1"><br>
        <input type="text" name="n2"><br>
        <input type="submit" value="POST요청">
    </form>
</li>
```
reverse 함수 : 경로를 가지고 오는 함수
