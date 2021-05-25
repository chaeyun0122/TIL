# Admin 사이트 꾸미기
* fields 정의. 순서변경도 가능
  ```python
  from django.contrib import admin

  # Register your models here.
  from polls.models import Question, Choice

  # 설정 값들을 클래스로 쓰고 나중에 등록할 때 같이 등록함
  class QuestionAdmin(admin.ModelAdmin):
      fields = ['pub_date', 'question_text', ]
      
  admin.site.register(Question, QuestionAdmin) # 설정 값 등록
  admin.site.register(Choice)
  ```
  ![image](https://user-images.githubusercontent.com/79209568/119443859-3eed0780-bd65-11eb-8ef5-c2198b3021e4.png)
* `claseese` 속성 : `collapes` 속성은 숨김이 가능하다
  ```python
  class QuestionAdmin(admin.ModelAdmin):
      # fields = ['pub_date', 'question_text', ]
      fieldsets = [ # 보여지는 항목들을 명확하게 명시해서 분류하는 것
          ('Question Statement', {'fields': ['question_text',]}),
          ('Date Information', 
              {
                  'fields':
                  [
                      'pub_date',
                  ],
                  'classes': # 클래스 속성
                  [
                      'collapse'  # 숨김
                  ]
              }
          ),
      ]
  ```
  ![image](https://user-images.githubusercontent.com/79209568/119444048-94c1af80-bd65-11eb-863e-69f1db9a1878.png)

* 다른 항목 내부에 넣어줄 수 있다. `model`에 넣을 모델 명을 적고 `extra`에 넣을 항목 개수를 적는다. 그 후 클래스 속성에 `inlines`를 추가해준다.
  * 쌓이는 형태
    ```python
    class ChoiceInline(admin.StackedInline):  # 쌓이는 형태
        model = Choice
        extra = 2

    class QuestionAdmin(admin.ModelAdmin):
        # fields = ['pub_date', 'question_text', ]
        fieldsets = [ # 보여지는 항목들을 명확하게 명시해서 분류하는 것
            ('Question Statement', {'fields': ['question_text',]}),
            ('Date Information', {'fields': ['pub_date',],'classes': ['collapse']}),
        ]
        inlines = [ChoiceInline,] # 내부에 들어오게 만드는 속성
    ```
    ![image](https://user-images.githubusercontent.com/79209568/119442897-9ab69100-bd63-11eb-98ff-d57ae5ea49c8.png)
  * 테이블 형태
    ```python
    class ChoiceInline(admin.TabularInline):    # 테이블 형태
        model = Choice
        extra = 2
    ```
    ![image](https://user-images.githubusercontent.com/79209568/119443011-cfc2e380-bd63-11eb-8adb-4599fe2789f3.png)
* `list_display` : model 설정에서의 \_\_str\_\_함수보다 보여지는 항목을 우선 처리하게 된다
  * 원래 모델 설정에서는 question_text만 출력하도록 했다.
    ![image](https://user-images.githubusercontent.com/79209568/119444504-5082df00-bd66-11eb-8f28-f13c77e95376.png)
  * list_display가 우선 처리하므로 pub_date까지 출력한다.
    ```python
    class QuestionAdmin(admin.ModelAdmin):
        # fields = ['pub_date', 'question_text', ]
        fieldsets = [ # 보여지는 항목들을 명확하게 명시해서 분류하는 것
            ('Question Statement', {'fields': ['question_text',]}),
            ('Date Information', {'fields': ['pub_date',],'classes': ['collapse']}),
        ]
        inlines = [ChoiceInline,] # 내부에 들어오게 만드는 속성
        list_display = ('question_text', 'pub_date') # model 설정에서의 __str__함수보다 보여지는 항목을 우선 처리하게 된다
    ```
    ![image](https://user-images.githubusercontent.com/79209568/119443249-3ea03c80-bd64-11eb-8ba1-e1fe471daf75.png)
* `list_filter` : 필터 설정
  ```python
  list_filter = ['pub_date']
  ```
  ![image](https://user-images.githubusercontent.com/79209568/119443356-714a3500-bd64-11eb-8efb-c2370120eed7.png)
* `search_fields` : 검색 설정
  ```python
  search_fields = ['question_text']
  ```
  ![image](https://user-images.githubusercontent.com/79209568/119443497-abb3d200-bd64-11eb-94b4-c46885800e5e.png)
  ![image](https://user-images.githubusercontent.com/79209568/119443529-b9695780-bd64-11eb-9731-94800401e0b4.png)

