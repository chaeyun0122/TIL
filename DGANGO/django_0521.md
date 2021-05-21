## with
* 탬플릿에서 변수 선언하고 특정 위치까지 유지시켜줄 수 있다.
#### views.py
```python
def ex06(request):
    val1 = 100
    info1 = Info('홍길동', 33)

    context = {
        'val1':val1,
        'info1':info1,
    }
    return render(request, 'extemp/ex06.html', context)
```

#### ex06.html
* `with`에서 선언한 변수 n이 `endwith`까지 유지되기 때문에 그 후에 n을 출력하려고하면 없는 값이라고 나온다.
  ```html
  val1 : {{ val1 }}<br>
  <hr>
  {% with n=val1 %} <!-- n : 탬플릿에서 변수를 선언 -->
  n : {{ n }}<br>
  {% endwith %} <!-- n 값이 여기까지 유지된다 -->
  n : {{ n }}<br>
  <hr>
  ```
  ![image](https://user-images.githubusercontent.com/79209568/119082491-2f09b680-ba39-11eb-9d69-501083a31614.png)

* 객체 안의 값을 꺼내서 쓰는 방법이 `info1.name`인데 with를 통해 변수안에 넣어쓰면 좀 더 짧게 줄여서 쓸 수 있다.
* 방식이 두 가지다. 
  * `{% with name=info1.name %}   ~   {% endwith %}`
  * `{% with info1.name as name %}   ~   {% endwith %}`
  ```html
  <hr>
  {% with name=info1.name %} <!-- 객체 안의 값을 꺼내서 name변수에 넣어준다 -->
  올해의 인물 이름: {{ name }}<br>
  작년 올해의 인물: {{ name }}<br>
  {% endwith %}
  {% with info1.name as name %}
  올해의 인물 이름: {{ name }}<br>
  작년 올해의 인물: {{ name }}<br>
  {% endwith %}
  ```
  ![image](https://user-images.githubusercontent.com/79209568/119083195-747ab380-ba3a-11eb-9543-1ac470e289b2.png)

## 템플릿 주석
```
1.  {# 템플릿 싱글라인 주석 #}
2.  {% comment 'desc' %}
        템플릿의
        멀티
        라인
        주석
    {% endcomment %}
3.  <!-- html 주석 -->
```

## 문자 이스케이프
* 이스케이프 : 문자열에 들어 있는 태그가 브라우저에서 해석되지 않도록 특수 문자로 변환하는 것
  ```html
  <h1>Hello</h1>      <!-- 태그로 해석함 -->
  결과: {{ values }}  <!-- 태그로 해석되지 않음 -->
  ```
  > ![image](https://user-images.githubusercontent.com/79209568/119084089-24045580-ba3c-11eb-9be4-92365a558e26.png)
  > * 페이지 소스  
  >   ![image](https://user-images.githubusercontent.com/79209568/119084257-6af24b00-ba3c-11eb-96be-65e472bbb229.png)

### 이스케이프 끄기
```
1.
  {% autoescape off %}
  결과: {{ values }}
  {% endautoescape %}
2.
  결과: {{ values|safe }}
```

# 페이지 상속
* 공통으로 사용할 페이지를 만들어두고 그 페이지를 상속시켜서 동일한 내용이 모든 페이지 내에 존재하도록 하는 것
#### base.html
```html
{# 이 템플릿은 모든 템플릿의 기본 베이스가 되는 템플릿 #}
{# 이 템플릿을 상속하여 block이 지정된 부분을 재정의하여 표현 #}
{# 재정의하지 않으면 block 내의 내용이 출력 됨 #}
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>{% block title %}Main{% endblock %}</title>
</head>
<body>
    <h3>{% block headline%}Headline{% endblock %}</h3>
    <hr>
    {% block content %}Content..{% endblock %}
</body>
</html>
```
#### index.html
* `{% extends './base.html' %}` 코드를 통해 `base.html`을 상속 시켜준다.
* 쓰지 않은 block은 기본 값이 출력된다.
```html
{% extends './base.html' %}

{% block title %}Index{% endblock %}
{% block headline %}Main Page Index. {% endblock %}
{% block content %}
    현재 시간 : {{ nowdate }}입니다.<br>
    <hr>
    <ul>
        <li><a href="{% url 'extemp:ex01' %}">예제 1</a></li>
        <li><a href="{% url 'extemp:ex02' %}">예제 2</a></li>
        <li><a href="{% url 'extemp:ex03' %}">예제 3</a></li>
        <li><a href="{% url 'extemp:ex04' %}">예제 4</a></li>
        <li><a href="{% url 'extemp:ex05' %}">예제 5</a></li>
        <li><a href="{% url 'extemp:ex06' %}">예제 6</a></li>
        <li><a href="{% url 'extemp:ex07' %}">예제 7</a></li>
    </ul>
{% endblock %}
```
![image](https://user-images.githubusercontent.com/79209568/119086313-48623100-ba40-11eb-8346-9755f063cc9d.png)

## static
