> ## 새 프로젝트
> 1. 프로젝트 명 : project05
> 2. 앱 추가 : exam_model

# Model
* 모델을 생성한다.
  ```python
  class Student(models.Model): # DB에 모델임을 알리려면 Model을 상속시켜줘야한다.
      name = models.CharField(max_length=20)
      age = models.IntegerField()
      intro = models.TextField()
      regdate = models.DateTimeField(auto_now_add=True)
  ```
* 변경된 정보를 확인한다.
  ```
  python manage.py makemigrations
  ```
  ![image](https://user-images.githubusercontent.com/79209568/119600935-1297c080-be23-11eb-97a9-0374b68f7312.png)
  * `migrations`폴더에 `0001_initial.py`가 만들어지며 
* 변경된 정보를 DB에 저장한다.
  ```
  python manage.py migrate
  ```
  
## Model 확인
* admin 페이지에서 확인한다.
* `admin.py`에 Student 모델을 등록한다.
  ```
  # admin.py
  from django.contrib import admin
  from exam_model.models import Student

  admin.site.register(Student)
  ```
* 웹브라우저에서 admin으로 로그인한 후 확인
  
  ![image](https://user-images.githubusercontent.com/79209568/119602078-5c81a600-be25-11eb-98b4-6d73f21719a6.png)

## 객체 생성
* 객체가 생성될 때 출력될 문자열을 정의한다.
  ```
  # models.py의 Student class
  def __str__(self):
    return "Student(%s, %d)" % (self.name, self.age)
  ```
* django shell로 로그인
  ```
  python manage.py shell
  ```
* Student 모델 import 후 객체 생성 및 저장
  ```
  >>> from exam_model.models import Student
  >>> q = Student(name='까까', age=5, intro='냥아치') 
  >>> q
  <Student: Student(까까, 5)>
  >>> q.save()
  ```
* admin 사이트에서 확인
  ![image](https://user-images.githubusercontent.com/79209568/119602871-13325600-be27-11eb-8e86-70073faf4a1e.png)
