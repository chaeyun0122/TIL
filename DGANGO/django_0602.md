> ## 새 프로젝트
> 1. 프로젝트 명 : project09
> 2. 앱 추가 : updown
> 3. admin 계정 생성 : admin/1234

# 파일 업로드
## 업로드 모델
* `FileField` : 서버에 저장된 파일의 경로를 저장하는 필드
```python
# updown/models.py

class UploadFileModel(models.Model):
    title = models.CharField(default='', max_length=200)
    file = models.FileField(null=True) # 서버에 저장된 파일의 경로를 저장

    def __str__(self):
        return "%s [ %s ]" % (self.title, self.file)
```
* admin 사이트에 모델 등록
```python
# updown/admin.py

from django.contrib import admin
from updown.models import UploadFileModel

admin.site.register(UploadFileModel) # 모델을 admin 사이트에 등록
```
* 값과 값을 구분하는 구분자가 &였는데 enctype="multipart/form-data"를 통해 보내게되면 구분자가 랜덤의 바운더리값이 된다. 
  
  ![image](https://user-images.githubusercontent.com/79209568/120426164-88afa080-c3aa-11eb-845a-acb0901e09f5.png)
* 업로드한 파일을 눌러보면, 해당 오류가 뜬다. 이는 관리자 사이트에서 
  ![image](https://user-images.githubusercontent.com/79209568/120426357-e643ed00-c3aa-11eb-9720-289170c40ea2.png)
  ![image](https://user-images.githubusercontent.com/79209568/120426377-f1971880-c3aa-11eb-8859-e951128ef3f2.png)

## 업로드 폼
* `exform.py` 파일 생성
  ```python
  from django import forms
  from updown.models import UploadFileModel

  class UploadFileForm(forms.ModelForm):
      class Meta:
          model = UploadFileModel # 어떤 모델로 폼을 만들것인가
          fields = ('title', 'file') # 그 모델에 어떤 필드를 입력 받을 것인가

      # 생성자(객체 생성 시 호출)
      def __init__(self, *args, **kargs): 
          super(UploadFileForm, self).__init__(*args, **kargs)  # 부모의 생성자를 호출

          self.fields['file'].required = False # 파일 필드의 값은 필수가 아님  
  ```
* views.py에 upload 함수를 지정
  ```python
  from updown.models import UploadFileModel
  from updown.exforms import UploadFileForm

  def upload(request):
      if request.method == 'POST':
          # 일반적인 데이터들은 POST에 있고 파일 2진데이터는 FILES에 있다. 해당 값들을 통해 폼을 생성한다.
          form = UploadFileForm(request.POST, request.FILES)
          if form.is_valid():
              form.save() # UploadFileForm클래스에 지정된 Model에 저장
              return redirect('/updown/')
      else:
          form = UploadFileForm()

      return render(request, 'updown/upload.html', {'form':form, })
  ```
* upload.html로 템플릿 생성
  * enctype 설정을 `multipart/form-data`로 설정해줘야 파일 업로드가 가능하다.
    ![image](https://user-images.githubusercontent.com/79209568/120427822-99ade100-c3ad-11eb-8fe4-a90b1ca1aba7.png)

  ```html
  <!DOCTYPE html>
  <html lang="ko">
  <head>
      <meta charset="UTF-8">
      <title>업로드</title>
  </head>
  <body>
      <form action="{% url 'updown:upload' %}" method="post" enctype="multipart/form-data">
          {% csrf_token %}
          {{ form.as_p }}
          <input type="submit" value="업로드">
      </form>
  </body>
  </html>
  ```
  ![image](https://user-images.githubusercontent.com/79209568/120428684-13929a00-c3af-11eb-946b-d93658278999.png)
### 업로드 경로 지정
* updown폴더에 media 폴더 생성
* settings.py에 url 등록
  ```python
  # 브라우저r가 서버에 요청 시 파일에 대한 요청 URL을 식별
  MEDIA_URL = '/updown/media/'

  # 실제 서버에서 파일을 저장할 경로
  import os
  MEDIA_ROOT = os.path.join('updown/media/')
  # 위 설정은 urlpatterns 추가 시 사용할 정보
  ```
* urls.py에 urlpatterns를 추가해준다.
  ```python 
  from django.conf import settings
  from django.conf.urls.static import static

  print('(before)urlpatterns:', urlpatterns)
  urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
  print('(after)urlpatterns:', urlpatterns)
  ```
  * 이전과 이후 비교
    ![image](https://user-images.githubusercontent.com/79209568/120429838-0d9db880-c3b1-11eb-9a9e-86a827f98fea.png)

  * 업로드
    ![image](https://user-images.githubusercontent.com/79209568/120429868-18584d80-c3b1-11eb-96fb-3f0d4ff05a60.png)

### 파일 리스트와 다운로드
* file_list.html 생성
  * a 태그에 download 옵션을 넣으면 해당 링크를 클릭 시 파일이 다운로드 된다.
  ```html
  <!DOCTYPE html>
  <html lang="ko">
  <head>
      <meta charset="UTF-8">
      <title>파일 업로드</title>
  </head>
  <body>
      <h2>업로드 파일 목록</h2>
      <ul>
      {% for file in list %}
          <li> {{ forloop.counter }}.
              <a href="/updown/media/{{ file.file }}" download>{{ file.title }}</a>
          </li>
      {% endfor %}
      </ul>
  </body>
  </html>
  ```
* views.py에 리스트를 보이는 함수 생성
  ```python
  def file_list(request):
      list = UploadFileModel.objects.all()
      return render(request, 'updown/file_list.html', { 'list':list, })
  ```
![image](https://user-images.githubusercontent.com/79209568/120431064-ee078f80-c3b2-11eb-8d98-b8d23251980e.png)

### 파일 삭제
* views.py에 delete 함수 생성
  ```python
  import os
  from django.conf import settings

  def delete_file(request, deleteid):
      qs = UploadFileModel.objects.get(id=deleteid)
      print('filePath:', str(qs.file))

      media_root = settings.MEDIA_ROOT
      print('media_root:', media_root)

      deleteFile = media_root + "/" + str(qs.file)
      print('deleteFile:', delete_file)

      # 실제 파일 삭제를 위한 코드
      if os.path.isfile(deleteFile):
          print('%s파일 삭제됨' % deleteFile)
          os.remove(deleteFile) # 저장 되어있는 실제 파일 삭제
      else:
          print('삭제 될 파일이 없음:', deleteFile)

      qs.delete() # 모델(DB)에서 해당 레코드 삭제

      return redirect('updown:file_list')
  ```
* file_list.html에 삭제 버튼을 만듦
  ```html
  <a href="{% url 'updown:delete_file' file.id %}"><button>삭제</button></a>
  ```
* 삭제 버튼 누름  
  ![image](https://user-images.githubusercontent.com/79209568/120433002-b3532680-c3b5-11eb-953e-1b8059961f1d.png)
* 콘솔 확인  
  ![image](https://user-images.githubusercontent.com/79209568/120433057-bfd77f00-c3b5-11eb-9976-78b3e88e1893.png)
* 삭제 됨  
  ![image](https://user-images.githubusercontent.com/79209568/120433078-c5cd6000-c3b5-11eb-992d-730a020c2c23.png)


