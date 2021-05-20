# 새 프로젝트
> 1. 프로젝트 명 : project03  
    `django-admin startproject mysite`  
    `project03로 이름 변경`  
> 2. 앱 추가 : exam_template  
    `python manage.py startapp exam_template`  
> 3. url 파일 추가 : exurls.py
>   ```python
>   app_name = 'extemp'
>
>   urlpatterns = [
>        path('', views.index, name='index'),
>   ]
>   ```
> 4. viws.py
> ```python
> def index(request):
>   nowdate = datetime.today()
>   context = {'nowdate' : nowdate}
>   print(nowdate)
>   
>   return render(request, 'extemp/index.html', context)
> ```
> 5. tempates/extemp 폴더 생성 후 index.html 생성
>  ```html
> <body>
>     현재 시간 : {{ nowdate }}입니다.<br>
>     <hr>
>     <ul>
>         <li>예제 1</li>
>         <li>예제 2</li>
>     </ul>
> </body>
> ```
> ![image](https://user-images.githubusercontent.com/79209568/118917567-2303f300-b96c-11eb-8aa3-839e55a8a23e.png)

# Template
## 탬플릿 추가
#### ex01.html
```html
n1 : {{ n1 }}<br>
lst : {{ lst }}<br>
tup : {{ tup }}<br>
dic : {{ dic }}<br>
```
#### views.py
```python
def ex01(request):
    n1 = 100
    lst = [1,2,3]
    # return render(request, 'extemp/ex01.html', {'n1':n1, 'lst':lst,})

    tup = {4,5,6}
    dic = {'a':10, 'b':20, 'c':30}

    context = {
        'n1':n1,
        'lst':lst,
        'tup':tup,
        'dic':dic,
    }
    return render(request, 'extemp/ex01.html', context)
```
#### index.html
* 예제 1을 링크로 변경
```html
<li><a href="{% url 'extemp:ex01' %}">예제 1</a></li>
```
> * 소스를 보면 각각의 값으로 대체 된 것을 확인할 수 있다.
> ![image](https://user-images.githubusercontent.com/79209568/118920487-7af12880-b971-11eb-9987-b7cdc612b863.png)

* 이런식으로도 각각의 값을 출력할 수도 있다.
  ```html
  {{ lst.0 }}, {{ lst.2 }}<br>
  {{ tup.1 }}, {{ tub.2 }}<br>
  {{ dic.a }}, {{ dic.c }}<br>
  ```
## Invalid
* 모르는 값이 들어오면 특정 값이 나오도록 설정할 수 있다.
#### ex01.html
* `un`이라는 값은 `views.py`의 ex01 함수에 정의하지 않았다.
```html
unkown : [{{ un }}]<br>
```
> * 출력 : 빈 값으로 나옴  
> ![image](https://user-images.githubusercontent.com/79209568/118921653-7e85af00-b973-11eb-9b4c-c23fa8791348.png)
* `settings.py`에 TEMPLATES에 추가한다.  
  ![image](https://user-images.githubusercontent.com/79209568/118922142-72e6b800-b974-11eb-9fde-afaf836f4270.png)
* 빈 값에 지정한 문자가 나옴  
  ![image](https://user-images.githubusercontent.com/79209568/118922168-7f6b1080-b974-11eb-9788-c83157c0ca53.png)

## 필터
#### views.py
```python
def ex02(request):
    val1 = 'hello<world><br>'
    context = { 'val1':val1 }
    return render(request, 'extemp/ex02.html', context)
```
#### ex02.html
```html
val1 : {{ val1 }}<br>
val1|excape : {{ val1|escape }}<br> 
<!-- 
    의미있는 문자를 코드값으로 알려줘서 의미없이 문자로 출력하도록한다. (웹사이트 개발에 해석되지 않도록 하는 것)
-->
val1|stringtags : {{ val1|striptags }}<br>
val1|length : {{ val1|length }}<br>
val1|upper|linebreaks : {{ val1|upper|linebreaks }}<br>
```
![image](https://user-images.githubusercontent.com/79209568/118923184-213f2d00-b976-11eb-898e-af287a26dd90.png)

## 필터2
### for
#### views.py
```python
def ex02(request):
    val1 = 'hello<world><br>'
    lst = ['ho', 'hi', 'welcome']
    tup = (4, 5, 6)
    dic = {'a':10, 'b':20, 'c':30}
    bio = 'hi1 hi2 hi3 hi4 hi5 hi6 hi7 hi8 hi9 hi10'
    context = { 
        'val1':val1,
        'lst':lst,
        'tup':tup,
        'dic':dic,
        'bio':bio,
    }
    return render(request, 'extemp/ex02.html', context)
```
#### ex02.html
* 코드 추가
```html
<hr>
lst|lower|join:"@" : {{ lst|lower|join:"@" }}<br>
dic|upper : {{ dic|upper }}<br>
unknown|default:"기본 값" : {{ unknown|default:"기본 값" }}<br>
bio|truncatewords:4 : {{ bio|truncatewords:4 }}<br>
lst|pluralize : {{ lst|pluralize }}<br>
```
![image](https://user-images.githubusercontent.com/79209568/118923604-bfcb8e00-b976-11eb-9972-d294a716b37b.png)

## 태그
#### views.py
```python
# 클래스 : 객체를 정의해놓은 틀
class Info:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def __str__(self):
        return "Info[%s, %d]" % (self.name, self.age)

def ex03(request):
    name_list = ['홍길동', '이순신', '강감찬']

    info1 = Info('홍길동', 33) # Info 클래스의 __init__ 함수가 호출 됨 (객체생성)
    info2 = Info('이순신', 34)
    info3 = Info('강감찬', 53)
    info4 = Info('임꺽정', 89)

    print(info1)  # Info 클래스의 __str__함수가 호출된 결과 출력
    print(info2)
    print(info3)
    print(info4)

    info_list = [
        info1,
        info2,
        info3,
        info4,
    ]
    context = {
        'name_list':name_list,
        'info_list':info_list,
    }
    return render(request, 'extemp/ex03.html', context)
```
#### ex03.html
```html
<!-- 하나씩 출력 -->
{{ name_list.0 }}<br>
{{ name_list.1 }}<br>
{{ name_list.2 }}<br>
<hr>
<!-- '{%' 이 부분은 파이썬 코드, 없는 것은 html 코트 -->
<ul>
{% for name in name_list %}
    <li>이름 : {{ name }}</li>
{% endfor %}
</ul>
```
> * 출력화면  
> ![image](https://user-images.githubusercontent.com/79209568/118926533-70d42780-b97b-11eb-8d8c-a0a5b7f4ffdf.png)
### 클래스 객체 for문으로 출력
```html
<!-- __str__함수 출력처럼 나옴 -->
<ul>
    {% for info in info_list %}
        <li>{{ info }}</li>
    {% endfor %}
</ul>
<hr>
<!-- 각각의 객체가 갖고있는 속성 값들을 꺼냄 -->
<ul>
    {% for info in info_list %}
        <li>{{ info.name }} : {{ info.age }} </li>
    {% endfor %}
</ul>
```
> * 출력 화면  
> ![image](https://user-images.githubusercontent.com/79209568/118929333-5734df00-b97f-11eb-9c2f-83ced790bee8.png)

## forloof
* for 문 안이면 모두 사용가능한 카운트 함수다
```html
<!-- 순서 카운팅 : forloop -->
<ul>
    {% for name in name_list %}
        <li>
            이름 : {{ name }}<br>
            forloop.counter: {{ forloop.counter }}<br> <!-- 1부터 시작하는 카운팅 -->
            forloop.counter0: {{ forloop.counter0 }}<br> <!-- 0부터 시작하는 카운팅 -->
            forloop.revcounter: {{ forloop.revcounter }}<br> <!-- 거꾸로 카운팅 -->
            forloop.revcounter0: {{ forloop.revcounter0 }}<br> <!-- 마지막이 0인 거꾸로 카운팅 -->
            forloop.first: {{ forloop.first }}<br> <!-- 반복의 처음 시점이면 True -->
            forloop.last: {{ forloop.last }}<br> <!-- 반복의 마지막 시점이면 False -->
        </li>

    {% endfor %}
</ul>
```
> * 출력 결과  
> ![image](https://user-images.githubusercontent.com/79209568/118927837-5733df80-b97d-11eb-964e-2eb148eee71f.png)

## if
#### ex04.html
```html
{% if name_list %} <!-- name_list가 있으면 True, 없으면 False -->
    name_list|length : {{ name_list|length }}
{% else %}
    name_list is empty!
{% endif %}
<hr>
{% if age_list %} <!-- age_list는 존재하지 않음 -->
    age_list|length : {{ age_list|length }}
{% else %}
    age_list is empty!
{% endif %}
<hr>
```
> * 출력 화면  
> ![image](https://user-images.githubusercontent.com/79209568/118929444-7f244280-b97f-11eb-8b62-04b3f536f216.png)
