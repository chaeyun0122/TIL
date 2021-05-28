> ## 새 프로젝트
> 1. 프로젝트 명 : project06
> 2. 앱 추가 : exam_form
> 3. admin 계정 생성 : admin/1234

# Form
* 장고가 제공하는 form을 import해준다.
  ```python
  from django import forms
  ```
* forms의 Form을 상속하는 NameForm 클래스를 생성한다.
  * input태그를 아래 처럼 쉽게 작성가능
  ```python
  class NameForm(forms.Form):
      nameText = forms.CharField(label='name', max_length=100)
  ```
* get_name 함수를 만들어서 폼을 출력한다.
  ```python
  def get_name(request):
      if request.method == 'POST':
          form = NameForm(request.POST)
          # 폼 데이터 검증
          if form.is_valid():
              new_name = form.cleaned_data['nameText'] # 폼 데이터 확인
              context = { 'name': new_name }
              return render(request, 'exam_form/nameResult.html', context)
      else:
          form = NameForm()

      context = { "form":form, }
      return render(request, 'exam_form/nameForm.html', context)
  ```
  * method가 GET으로 들어올 경우 생성했던 NameForm 클래스를 통해 빈 form을 생성하도록 한다.
    ![image](https://user-images.githubusercontent.com/79209568/119928601-d6956480-bfb6-11eb-96f3-bab6050a180c.png)
  * method가 POST로 들어올 경우 검증 작업을 한다.
    * `new_name = form.cleaned_data['nameText']` : nameText를 검증해서 new_name 변수에 넣음
    * 검증을 하는 이유? JS는 브라우저에서 검증 처리를 한다. (클라이언트 쪽) 하지만 악의적으로 우회가 가능하므로 서버에서도 검증작업이 필요하다. 
* nameForm.html
  ```
  <form method="POST">
      {% csrf_token %}
      {{ form }}
      <input type="submit" value="등록">
  </form>
  ```
* nameResult.html
  ```
  전송된 이름 : {{ name }}
  ```
* **결과**
  * nameForm에 값을 입력한다.   
    
    ![image](https://user-images.githubusercontent.com/79209568/119929480-90410500-bfb8-11eb-8ca2-987ce2fc7a0a.png)
  * 값이 nameResult.html로 POST 방식으로 넘어간다.
    
    ![image](https://user-images.githubusercontent.com/79209568/119929526-abac1000-bfb8-11eb-9adb-18f077d691ab.png)

