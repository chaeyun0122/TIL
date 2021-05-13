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
    pub_date = models.DateTimeField('date published')

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




> ### DB Browser for SQLite
> * sqlite 파일을 확인할 수 있는 프로그램 →[다운로드](DGANGO/util/DB.Browser.for.SQLite-3.12.0-rc1-win64.zip)
> * 실행 위치 `C:\Program Files\DB Browser for SQLite`의 DB Browser for SQLite.exe
> * `데이터베이스 열기` 클릭 후 .sqlite3 확장자 파일을 연다.
