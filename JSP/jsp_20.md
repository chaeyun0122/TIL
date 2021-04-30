# JSP 파일 업로드
## 파일 업로드에 필요한 요소
* 파일을 입력받는 form의 method 및 enctype
  - enctype : form data가 서버로 보내질 때의 인코딩 타입
  - `method="post" enctype="multipart/form-data"`
  - 파일 또는 이미지를 보낼 때는 무조건 `"multipart/form-data"`로 해야한다.
* 업로드 파일이 저장될 폴더
  - **storage** : WebContent안에 해당 폴더를 만들어 준다.  
  
    ![image](https://user-images.githubusercontent.com/79209568/116683455-99a37580-a9ea-11eb-84e0-1914499b367f.png)
  - 실제 파일은 여기에 저장되지 않고 실제 폴더에 따로 있다. (아래에 설명)
* 파일을 업로드하고 form을 분석하는 라이브러리
  - **cos.jar**

## eclipe의 가상폴더와 실제폴더
* **가상 폴더**
  * `workspace\프로젝트명\WebContent\storage`
* **실제 폴더**
  * `workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps`
  
## cos.jar
* http://servlets.com/cos 해당 사이트에서 cos-20.08/zip 파일 다운로드 후 압축 풀기  
  
  ![image](https://user-images.githubusercontent.com/79209568/116684927-8d201c80-a9ec-11eb-8b4b-7ea5cab0cfd7.png)
* lib 폴더안의 cos.jar을 프로젝트의 `WEB-INF > lib`안에 넣어준다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/116684967-9ad5a200-a9ec-11eb-9213-e1a3ab406d37.png)

