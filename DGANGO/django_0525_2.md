# Django Shell
## 실행 
> ### `python manage.py shell`

## 객체 생성
* objects.all()
  ```
  >>> from polls.models import Question, Choice
  >>> from django.utils import timezone
  >>>
  >>> Question.objects.all()
  <QuerySet [<Question: 취미가 무엇입니까?>, <Question: 어떤 음식을 좋아합니까?>, <Question: 어떤 동물을 좋아합니까?>]>
  ```

* 객체 생성
  ```
  >>> Question(question_text='언어', pub_date=timezone.now())
  <Question: 언어>
  ```
* 생성한 객체는 아직 DB에 반영되지 않았기 때문에 반영시켜줘야한다.
* 먼저 변수 q에 해당 객체를 넣어주고 `save()`함수로 DB에 반영시켜준다.
  ```
  >>> q = Question(question_text='언어', pub_date=timezone.now()) 
  >>> q
  <Question: 언어>
  >>> q.save()
  ```
* 추가 되었다
  ```
  >>> Question.objects.all()
  <QuerySet [<Question: 취미가 무엇입니까?>, <Question: 어떤 음식을 좋아합니까?>, <Question: 어떤 동물을 좋아합니까?>, <Question: 언어>]>
  ```
## 검색
* qs 변수에 Question 객체들을 넣어준다
  ```
  >>> qs = Question.objects.all()
  >>> qs
  <QuerySet [<Question: 취미가 무엇입니까?>, <Question: 어떤 음식을 좋아합니까?>, <Question: 어떤 동물을 좋아합니까?>, <Question: 언어>]>
  ```
* QuerySet의 `filter`함수를 통해 검색이 가능하다.
  ```
  >>> qs.filter(question_text__contains='음')
  <QuerySet [<Question: 어떤 음식을 좋아합니까?>]>

  >>> qs.filter(id=2)
  <QuerySet [<Question: 어떤 음식을 좋아합니까?>]>

  >>> qs.filter(question_text='언어')
  <QuerySet [<Question: 언어>]>

  >>> qs.filter(question_text__contains='?')     
  <QuerySet [<Question: 취미가 무엇입니까?>, <Question: 어떤 음식을 좋아합니까?>, <Question: 어떤 동물을 좋아합니까?>]>
  ```
* get 함수를 통해 검색이 가능하지만 반드시 결과가 하나인 질의만 가능하다.
  * 여러개를 가져오는 상황에서는 에러가 난다.
    ```
    >>> qs.get(question_text__contains='?')
    Traceback (most recent call last):
      File "<console>", line 1, in <module>
      File "C:\Python39\lib\site-packages\django\db\models\query.py", line 410, in get
        raise self.model.MultipleObjectsReturned(
    polls.models.Question.MultipleObjectsReturned: get() returned more than one Question -- it returned 3!    
    ```
  * 하나의 값을 반환할 때는 가능
    ```
    >>> qs.get(id=1)
    <Question: 취미가 무엇입니까?>
    ```
* 체인 방식으로 여러 함수를 연결해서 호출 가능
  ```
  <Question: 취미가 무엇입니까?>
  >>> qs = Question.objects.all().get(id=1)
  >>> qs
  <Question: 취미가 무엇입니까?>

  >>> qs = Question.objects.all().filter(question_text__contains='?').get(id=3)
  >>> qs
  <Question: 어떤 동물을 좋아합니까?>
  ```
* 인덱싱, 슬라이싱 가능하다.
  ```
  >>> Question.objects.all()[1]      
  <Question: 어떤 음식을 좋아합니까?>
  >>> Question.objects.all()[2] 
  <Question: 어떤 동물을 좋아합니까?>
  >>> Question.objects.all()[3] 
  <Question: 언어>

  >>> Question.objects.all()[:2] 
  <QuerySet [<Question: 취미가 무엇입니까?>, <Question: 어떤 음식을 좋아합니까?>]>
  ```

## 객체 수정
```
>>> q = Question.objects.all().get(id=1)
>>> q
<Question: 취미가 무엇입니까?>
>>> q.question_text="what is your hobby?"
>>> q
<Question: what is your hobby?>
>>> q.save()
>>> Question.objects.all()
<QuerySet [<Question: what is your hobby?>, <Question: 어떤 음식을 좋아합니까?>, <Question: 어떤 동물을 좋아합니까?>, <Question: 언어>]>
```








