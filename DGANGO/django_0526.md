> ## 새 프로젝트
> 1. 프로젝트 명 : project05
> 2. 앱 추가 : exam_model
> 3. admin 계정 생성 : admin/1234

# Model
* 모델을 생성한다.
  ```python
  class Student(models.Model): # DB에 모델임을 알리려면 Model을 상속시켜줘야한다.
      name = models.CharField(max_length=20)
      age = models.IntegerField()
      intro = models.TextField()
      regdate = models.DateTimeField(auto_now_add=True)
  ```
* 객체가 생성될 때 출력될 문자열을 정의한다. (admin 사이트의 객체 목록에도 해당 문자열로 출력되어 보여진다.)
  ```python
  # models.py의 Student class
  def __str__(self):
    return "Student(%s, %d)" % (self.name, self.age)
  ```
* 변경된 정보를 확인한다.
  ```
  python manage.py makemigrations
  ```
  ![image](https://user-images.githubusercontent.com/79209568/119600935-1297c080-be23-11eb-97a9-0374b68f7312.png)
  * `migrations`폴더에 `0001_initial.py` 파일이 만들어진다.
* 변경된 정보를 DB에 저장한다.
  ```
  python manage.py migrate
  ```
  
## Model 확인
* admin 페이지에서 확인한다.
* `admin.py`에 Student 모델을 등록한다.
  ```python
  # admin.py
  from django.contrib import admin
  from exam_model.models import Student

  admin.site.register(Student)
  ```
* 웹브라우저에서 admin으로 로그인한 후 확인
  
  ![image](https://user-images.githubusercontent.com/79209568/119602078-5c81a600-be25-11eb-98b4-6d73f21719a6.png)

## CRUD
* django shell에서 진행
### Create (insert)
#### 첫 번째 방법
```python
>>> from exam_model.models import Student
>>> Student(name='홍길동', age=38, intro='산에 살아요').save()
>>> Student.objects.all()
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>]>
```
#### 두 번째 방법
```python
Student.objects.create(name='공유', age=50, intro='도깨비')
<Student: Student(공유, 50)>
>>> Student.objects.all()
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
```

### Read (select)
#### get은 하나의 결과일 경우에만 사용한다.
```python
>>> Student.objects.get(id=1)
<Student: Student(까까, 5)>

>>> Student.objects.get(id=2)
<Student: Student(홍길동, 38)>

>>> Student.objects.get(id=3)
<Student: Student(공유, 50)>

>>> Student.objects.get(age=5)
<Student: Student(까까, 5)>
```
#### 개수 가져오기
```python
>>> Student.objects.all().count()
3
```
#### 목록 조회(여러개)
##### 전체 조회
```python
>>> Student.objects.all() #순서를 보장하지 않음
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
```
##### 조건으로 조회 (where개념) 
* `filter([조건])`
  
  ```python
  >>> Student.objects.filter(name='공유')
  <QuerySet [<Student: Student(공유, 50)>]>
  ```
* `filter([필드명]__contain='[조건]')` : 조건이 포함되는 필드의 객체들을 반환, 조건 여러개인 경우 and 연산
  
  ```python
  >>> Student.objects.filter(name__contains='공')
  <QuerySet [<Student: Student(공유, 50)>]>

  >>> Student.objects.filter(name__contains='공', age=50) #and연산
  <QuerySet [<Student: Student(공유, 50)>]>
  ```
* or연산 사용하려는 경우 django.db.models.Q 객체 사용
  
  ```python
  >>> from django.db.models import Q
  
  >>> Student.objects.filter(name__contains='공', age=30)
  <QuerySet []>

  >>> Student.objects.filter(Q(name__contains='공') | Q(age=38))
  <QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
  ```
* `filter([필드명]__gt=[숫자])` : 값 초과의 큰 값을 가진 필드의 객체들을 반환
* `filter([필드명]__gte=[숫자])` : 값 이상의 큰 값을 가진 필드의 객체들을 반환
* `filter([필드명]__lt=[숫자])` : 값 미만의 작은 값을 가진 필드의 객체들을 반환
* `filter([필드명]__lte=[숫자])` : 값 이하의 작은 값을 가진 필드의 객체들을 반환
  
  ```python
  >>> Student.objects.filter(age__gt=38)
  <QuerySet [<Student: Student(공유, 50)>]>
  
  >>> Student.objects.filter(age__gte=38)
  <QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
  
  >>> Student.objects.filter(age__lt=38)
  <QuerySet [<Student: Student(까까, 5)>]>
  
  >>> Student.objects.filter(age__lte=38)
  <QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>]>
  ```
* `filter([필드명]__startswith='[조건]')` : 해당 조건 글자로 시작하는 필드의 객체를 반환
* `filter([필드명]__endswith='[조건]')` : 해당 조건 글자로 끝나는 필드의 객체를 반환
  
  ```python
  >>> Student.objects.filter(name='공')
  <QuerySet []>
  
  >>> Student.objects.filter(name__startswith='공')
  <QuerySet [<Student: Student(공유, 50)>]>
  
  >>> Student.objects.filter(name__endswith='유')
  <QuerySet [<Student: Student(공유, 50)>]>
  ```
##### 정렬하기
```python
# 객체 좀 더 추가
>>> Student.objects.create(name='아이유', age=29, intro='가수')
<Student: Student(아이유, 29)>
>>> Student.objects.create(name='이', age=20, intro='강사')
<Student: Student(이, 20)>
>>> Student.objects.all()
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>, <Student: Student(아이유, 29)>, <Student: Student(이, 20)>]>
```
* 오름차순 정렬
  ```python
  >>> Student.objects.all().order_by('age')

  <QuerySet [<Student: Student(까까, 5)>, <Student: Student(이규철, 20)>, <Student: Student(아이유, 29)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
  ```
* 내림차순 정렬 : 앞에 `-`를 붙인다.
  ```python
  >>> Student.objects.all().order_by('-age')
  
  <QuerySet [<Student: Student(공유, 50)>, <Student: Student(홍길동, 38)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>, <Student: Student(까까, 5)>]>
  >>>
  ```
* 조회 결과를 정렬
  * filter를 이용한 결과인 QuerySet객체에다 order_by함수 사용
  ```python
  >>> Student.objects.filter(age__gte=30)
  <QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>

  >>> Student.objects.filter(age__gte=30).order_by('-age')
  <QuerySet [<Student: Student(공유, 50)>, <Student: Student(홍길동, 38)>]>

  >>> Student.objects.filter(age__gte=30).order_by('name')
  <QuerySet [<Student: Student(공유, 50)>, <Student: Student(홍길동, 38)>]>

  >>> Student.objects.filter(age__gte=30).order_by('-name')
  <QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
  ```
### Update
#### 첫 번째 방법
* 특정 객체 하나를 가져와서 수정 후 save
  ```python
  >>> q = Student.objects.get(id=1)
  >>> q
  <Student: Student(까까, 5)>
  >>> q.name='kkakka'
  >>> q
  <Student: Student(kkakka, 5)>
  >>> Student.objects.all()
  <QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>]>
  >>> q.save()
  >>> Student.objects.all()
  <QuerySet [<Student: Student(kkakka, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>]>
  >>>
  ```
#### 두 번째 방법
* QuerySet객체의 update함수 사용



update함수 : filter를 통해 수정한 것이기 때문에 여러개가 수정될 수 있기때문에 조심해야한다.


```
C:\djangowork\project05>python manage.py shell
Python 3.9.2 (tags/v3.9.2:1a79785, Feb 19 2021, 13:44:55) [MSC v.1928 64 bit (AMD64)] on win32
Type "help", "copyright", "credits" or "license" for more information.
(InteractiveConsole)
>>> from exam_model.models import Student
>>> 
>>> #create 방법(insert)
>>> #첫 번째 방법
>>> Student(name='홍길동', age=38, intro='산에 살아요')
<Student: Student(홍길동, 38)>
>>> Student(name='홍길동', age=38, intro='산에 살아요').save()
>>> Student.objects.all()
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>]>
>>>
>>> #두번째 방법
>>> Student.objects.create(name='공유', age=50, intro='도깨비')
<Student: Student(공유, 50)>
>>> Student.objects.all()
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>>
>>>
>>> #read 방법(select)
>>> Student.objects.get(id=1)
<Student: Student(까까, 5)>
>>> #get은 결과가 하나일 경우에만 사용
>>> Student.objects.get(id=2)
<Student: Student(홍길동, 38)>
>>> Student.objects.get(id=3)
<Student: Student(공유, 50)>
>>> Student.objects.get(age=5)
<Student: Student(까까, 5)>
>>> Student.objects.get(age=50)
<Student: Student(공유, 50)>
>>>
>>> #개수가져오기
>>> Student.objects.all().count()
3
>>>
>>> #목록 조회(여러 개)
>>> Student.objects.all() #순서를 보장하지 않음
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>> #조건으로 조회(where개념)
>>> Student.objects.filter(name='공유')
<QuerySet [<Student: Student(공유, 50)>]>
>>> Student.objects.filter(name__contains='공')
<QuerySet [<Student: Student(공유, 50)>]>
>>> Student.objects.filter(name__contains='공', age=30)
<QuerySet []>
>>> Student.objects.filter(name__contains='공', age=50) #and연산
<QuerySet [<Student: Student(공유, 50)>]>
>>> Student.objects.filter(age=50)
<QuerySet [<Student: Student(공유, 50)>]>
>>> Student.objects.filter(age__gt=30)
<QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>> Student.objects.filter(age__gt=38)
<QuerySet [<Student: Student(공유, 50)>]>
>>> Student.objects.filter(age__gte=38)
<QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>> Student.objects.filter(age__lt=38)
<QuerySet [<Student: Student(까까, 5)>]>
>>> Student.objects.filter(age__lte=38)
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>]>
>>>
>>> Student.objects.filter(name='공')
<QuerySet []>
>>> Student.objects.filter(name__startswith='공')
<QuerySet [<Student: Student(공유, 50)>]>
>>> Student.objects.filter(name__endswith='유')
<QuerySet [<Student: Student(공유, 50)>]>
>>> Student.objects.filter(name__endswith='공')
<QuerySet []>
>>>
>>> 
>>> #or연산 사용하려는 경우 django.db.models.Q 객체 사용
>>> from django.db.models import Q
>>> Student.objects.filter(name__contains='공', age=30)
<QuerySet []>
>>> Student.objects.filter(Q(name__contains='공') | Q(age=30))
<QuerySet [<Student: Student(공유, 50)>]>
>>> Student.objects.filter(Q(name__contains='공') | Q(age=38))
<QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>>
>>>
>>> #정렬하기
>>> Student.objects.all()
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>> Student.objects.create(name='아이유', age=29, intro='가수')
<Student: Student(아이유, 29)>
>>> Student.objects.create(name='이규철', age=20, intro='강사')
<Student: Student(이규철, 20)>
>>> Student.objects.all()
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>]>
>>> Student.objects.all().order_by('age')
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(이규철, 20)>, <Student: Student(아이유, 29)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>> Student.objects.all().order_by('-age') #-를 붙이면 내림차순
<QuerySet [<Student: Student(공유, 50)>, <Student: Student(홍길동, 38)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>, <Student: Student(까까, 5)>]>
>>>
>>> #조회 결과를 정렬(filter를 이용한 결과인 QuerySet객체에다 order_by함수 사용)
>>> Student.objects.filter(age__gte=30)
<QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>> Student.objects.filter(age__gte=30).order_by('age')
<QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>> Student.objects.filter(age__gte=30).order_by('-age')
<QuerySet [<Student: Student(공유, 50)>, <Student: Student(홍길동, 38)>]>
>>> Student.objects.filter(age__gte=30).order_by('name')
<QuerySet [<Student: Student(공유, 50)>, <Student: Student(홍길동, 38)>]>
>>> Student.objects.filter(age__gte=30).order_by('-name')
<QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>]>
>>>
>>> #update 방법1(특정 객체 하나를 가져와서 수정 수 save)
>>> q = Student.objects.get(id=1)
>>> q
<Student: Student(까까, 5)>
>>> q.name='kkakka'
>>> q
<Student: Student(kkakka, 5)>
>>> Student.objects.all()
<QuerySet [<Student: Student(까까, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>]>
>>> q.save()
>>> Student.objects.all()
<QuerySet [<Student: Student(kkakka, 5)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>]>
>>>
>>>
>>> #update 방법2(QuerySet객체의 update함수 사용)
>>> Student.objects.get(id=1)
<Student: Student(kkakka, 5)>
>>> Student.objects.get(id=1).update(age=6) #모델 객체에는 update함수가 없음
Traceback (most recent call last):
  File "<console>", line 1, in <module>
AttributeError: 'Student' object has no attribute 'update'
>>> Student.objects.filter(id=1)
<QuerySet [<Student: Student(kkakka, 5)>]>
>>> Student.objects.filter(id=1).update(age=6) #QuerySet에 update함수를 사용
1
>>> Student.objects.all()
<QuerySet [<Student: Student(kkakka, 6)>, <Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>]>
>>>
>>> #delete 방법(QuerySet객체의 delete함수 사용)
>>> Student.objects.filter(age=6)
<QuerySet [<Student: Student(kkakka, 6)>]>
>>> Student.objects.filter(age=6).delete()
(1, {'exam_model.Student': 1})
>>> Student.objects.filter(age=6)
<QuerySet []>
>>> Student.objects.all()
<QuerySet [<Student: Student(홍길동, 38)>, <Student: Student(공유, 50)>, <Student: Student(아이유, 29)>, <Student: Student(이규철, 20)>]>
>>> Student.objects.all().update(age=100) #이렇게 수정하면 QuerySet에 선택된 모든 객체의 값이 수정됨
4
>>> Student.objects.all()
<QuerySet [<Student: Student(홍길동, 100)>, <Student: Student(공유, 100)>, <Student: Student(아이유, 100)>, <Student: Student(이규철, 100)>]>
>>> Student.objects.all().delete() #모두 삭제
(4, {'exam_model.Student': 4})
>>> Student.objects.all()
<QuerySet []>
>>>

전체 내용 정리하기!!
```
