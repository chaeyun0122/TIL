## 앱 추가
* 설문조사 앱을 만드는 실습을 진행하기 위해 새로운 앱을 추가한다.
* `python manage.py startapp polls` : 터미널에 입력해서 polls 앱을 생성한다.

## 앱 등록
* `polls > apps.py` 클래스 명을 복사한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/118078869-f98a1b00-b3f1-11eb-8166-2361bd430a48.png)
* 메인 앱(mysite)의 `settings.py` 파일에 `INSTALLED_APPS`에 해당 클래스의 경로를 추가해준다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/118078960-24746f00-b3f2-11eb-99d5-f9e851c56f52.png)
* `settings.py` 파일 아래에 해당 코드들을 변경해준다.  
  
  ```python
  LANGUAGE_CODE = 'ko-kr'

  TIME_ZONE = 'Asia/Seoul'
  ```
  
## 모델 생성
* 모델은 데이터의 설계를 해준다. Question 테이블과, Choice 테이블을 작성한다.
* PK는 자동으로 만들어지지만 FK는 직접 만들어야한다.
* `polls > models.py`에 작성
  
```python
from django.db import models

# Create your models here.
class Question(models.Model):
    # id 값이 PK로 자동 생성됨
    question_text = models.CharField(max_length=200)
    pub_date = models.DateTimeField('date published') # 표시 이름 지정

    def __str__(self):
        return self.question_text

class Choice(models.Model):
    question = models.ForeignKey(Question, on_delete=models.DO_NOTHING) # 참조하는 값이 지워질 경우에 아무것도 안하겠다는 규칙
    choice_text = models.CharField(max_length=200)
    votes = models.IntegerField(default=0)

    def __str__(self):
        return self.choice_text
```
### 변경사항 저장
* 터미널에 해당 명령어 입력. 두 개의 모델이 생성 됐다는 메시지가 뜨면 DB에 저장될 migration이 생성됨  
  
  ```
  python manage.py makemigrations
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/118081297-06107280-b3f6-11eb-98ea-6d8c9fc9c8c9.png)
* migration을 DB에 저장함  
  
  ```
  python manage.py migrate
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/118081983-5c31e580-b3f7-11eb-987d-88488821c612.png)
* [DB Browser for SQLite 프로그램](#DB-Browser-for-SQLite)을 통해 테이블 확인을 해본다. polls앱의 choice, question 테이블이 입력 된 것을 확인
  * 이렇게 코드만 가지고 sql을 조작할 수 있는 것을 ORM이라고 한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/118082322-eed28480-b3f7-11eb-824d-318f2f530a3b.png)

## Admin 페이지 테이블 관리 설정
* `polls > admin.py`에 테이블들을 등록해준다.  
  
  ```python
  from django.contrib import admin

  # Register your models here.
  from polls.models import Question, Choice

  admin.site.register(Question)
  admin.site.register(Choice)
  ```
* 서버 실행 후 (`python manage.py runserver`) admin 페이지로 이동. polls 앱의 관리할 수 있는 테이블들을 확인한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/118082719-a2d40f80-b3f8-11eb-941b-c9a617e02767.png)

## Admin 페이지에서 테이블에 데이터 추가
* 추가를 눌러서 Question과 Choice 데이터 추가
  ![image](https://user-images.githubusercontent.com/79209568/118084066-fe9f9800-b3fa-11eb-893c-b6da6f0f6d4f.png)



> ### DB Browser for SQLite
> * sqlite 파일을 확인할 수 있는 프로그램 →[다운로드](https://github.com/Clary0122/TIL/blob/main/DGANGO/util/DB.Browser.for.SQLite-3.12.0-rc1-win64.zip)
> * 실행 위치 `C:\Program Files\DB Browser for SQLite`의 DB Browser for SQLite.exe
> * `데이터베이스 열기` 클릭 후 .sqlite3 확장자 파일을 연다.

## 동작과 화면 정의
### URL 설정
* polls 앱에 `appurls.py` 파일을 추가 (설문조사 앱에서 요청할 url들은 따로 동작하기 때문에 mysite의 url 정보와 구분해야한다.)
  * 최상위 요청이 들어오면 views.py의 index를 사용한다.
  ```python
  # polls/appurls.py
  from django.urls import path
  from . import views

  urlpatterns = [
      # 사용자가 요청할 URL 경로를 등록한다.
      path('', views.index), 
  ]
  ```
* `views.py`에 index를 정의한다. Question테이블의 값들을 'pub_date'에 대한 정렬 후 5개만 요청
* 그 요청 값의 템플릿은 index.html을 따른다.
  ```python
  # polls/views.py
  from django.shortcuts import render
  from polls.models import Question
  
  # Create your views here.
  def index(request):
      q_list = Question.objects.all().order_by('pub_date')[:5]
      context = { 'q_list':q_list }

      return render(request, 'polls/index.html', context)
  ```
* polls 앱에 새 폴더 추가를 누르고 `templates/polls` 입력. 하위 파일까지 한번에 생성한다.
  * templates/polls 폴더에 `index.html` 파일 생성
  * 변경되는 값들을 표현해야하기 때문에 템플릿 형식으로 작성해준다.
    ```html
    <!-- polls/templates/polls/index.html -->
    {% if q_list %}
        <ul>
            {% for question in q_list %}
                <li><a href="#">{{question.question_text}}</a></li>
            {% endfor %}
        </ul>
    {% else %}
        <p>설문 항목이 존재하지 않음</p>
    {% endif %}    
    ```
* 브라우저에서 요청되는 모든 경로는 메인 앱(mysite)의 `urls.py`에서 처리되어야한다. 따라서 urls.py에서 polls의 `appurls.py`를 불러오도록 설정한다.
  * `from django.urls`에 `include`를 import 시켜준다.
  ```python
  # mysite/urls.py
  from django.contrib import admin
  from django.urls import path, include
  
  urlpatterns = [
      path('admin/', admin.site.urls),
      path('polls/', include('polls.appurls'))
  ]
  ```
### 저장 후 서버 연결
* 일반 localhost:8000에 연결하면 최상위 경로일 때 출력하는 페이지가 두 개 이기 때문에 Page not found가 뜬다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/118086691-4de7c780-b3ff-11eb-9522-fd8fdb6e8d0e.png)
* url에 polls를 입력하면 위에서 설정했던 대로 Question 테이블의 값들이 출력된다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/118086892-a323d900-b3ff-11eb-9b8f-43f1bd7e9a0d.png)
