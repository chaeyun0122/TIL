# 포트 변경
* 톰캣의 포트가 8080. 오라클의 디폴트 포트도 8080이므로 포트가 충돌 난다. 따라서 오라클의 포트를 변경해준다.
* 현재 포트 확인 : `select dbms_xdb.getHttpPort() from dual;`  
  ![image](https://user-images.githubusercontent.com/79209568/113852128-2df92e80-97d7-11eb-9cab-09b0a233076a.png)  
* 포트 변경 : `exec dbms_xdb.setHttpPort(9090);`  
  ![image](https://user-images.githubusercontent.com/79209568/113852387-7b759b80-97d7-11eb-9f20-427dbf7535be.png)  

#  프로젝트 생성
* 새 프로젝트 생성 : `file > New > Dynamic Web Project` 
  ![image](https://user-images.githubusercontent.com/79209568/113854624-0788c280-97da-11eb-8d87-d52d7ddf8630.png)  
* 이름 지정 및 모듈을 3.1로 변경  
  ![image](https://user-images.githubusercontent.com/79209568/113854718-28e9ae80-97da-11eb-9f66-24e890029c22.png)  
> 생성된 프로젝트 확인  
> ![image](https://user-images.githubusercontent.com/79209568/113855290-d5c42b80-97da-11eb-992c-93df19393706.png)  
  
## HTML 문서 만들기
* 새 HTML 파일 생성 : `WebContent 오른쪽 클릭 > New > HTML File`  
  ![image](https://user-images.githubusercontent.com/79209568/113855537-1d4ab780-97db-11eb-94b9-fd8cf0be7ac5.png)  
* 파일 이름 작성 후 Finish  
  ![image](https://user-images.githubusercontent.com/79209568/113855625-3a7f8600-97db-11eb-8108-49f636943ed3.png)  
* HTML 작성 후 실행 : `ctrl+F11`  
  ![image](https://user-images.githubusercontent.com/79209568/113855793-67339d80-97db-11eb-89d6-9ad33061c976.png)  
  * localhost:8080은 톰캣의 포트 넘버다.
  * P01_HTML은 서버 명이다.
  * Ex00_start.html은 HTML 문서 명이다.  
    
  * 디폴트 실행 브라우저 변경 방법 : `Window > Web Browser`  
  ![image](https://user-images.githubusercontent.com/79209568/113856060-b4b00a80-97db-11eb-8940-d0de32cbd70d.png)   
    * 0 Internal Web Browser : 이클립스 내의 웹 브라우저로 실행
    * 1 Default system web browser : 현재 윈도우에서 설정된 기본 브라우저로 실행
    * 2 Internet Explorer : 인터넷 익스플로러 브라우저로 실행
    * 3 Chrome : 크롬 브라우저로 실행
  
## HTML Start : 각각의 태그에 대해서
```html
<!-- Ex00_start.html -->
<!-- 
	HTML 주석
	- 주석은 개발자가 작성한 코드에 설명을 넣어줄 때 사용합니다.
	  코드 실행에 영향을 주지 않습니다.
 -->
 <!-- 
 	HTML (Hyper Text Markup Language)
 	- 웹페이지를 만드는 데 사용하는 언어
 	- 웹페이지는 html 태그로 구성되어 있고, .html 확장자를 사용한다
 	- 태그는 기본적으로 시작태그 <> 와 종료태그 </> 의 한쌍으로 구성된다
  -->
<!DOCTYPE html> <!-- html 문서 타입을 알려주는 태그 -->
<html>  <!-- html 문서 시작 -->
<head>  <!-- html 문서의 메타데이터를 정의 -->
<meta charset="UTF-8">  <!-- meta 태그 : 현재 페이지에 대한 속성과 정보를 표시 -->
<title> HTML </title> <!-- 문서 제목 -->
</head>
<body> <!-- 웹페이지에 표시되는 내용 -->
	HTML을 시작합니다.
</body>
</html>  <!-- html 문서 종료 -->
```
 
