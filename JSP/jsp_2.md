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
> ### 생성된 프로젝트 확인  
>   
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
  
### HTML Start : 각각의 태그에 대해서
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
 
### HTML 태그 : h tag
#### 제목 태그 : h1 ~ h6
- 제목을 표현 할 때 사용하는 태그
- 1이 가장 크고, 숫자가 커질수록 글자가 작아진다  
  
#### hr 태그
- 구분선, 수평라인을 표시한다.
- 속성
  - align : 수평선 정렬 지정
  - size  : 수평선 두께 지정
  - width : 수평선 넓이 지정  
  
  
```html
<!-- Ex01_h-tag.html -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>h 태그</title>
</head>
<body>
	<!-- 
		# 제목 태그 : h1 ~ h6
		 - 제목을 표현 할 때 사용하는 태그
		 - 1이 가장 크고, 숫자가 커질수록 글자가 작아진다
	 -->
	 <h1> h1, 제목1 </h1>
	 <h2> h2, 제목2 </h2>
	 <h3> h3, 제목3 </h3>
	 <h4> h4, 제목4 </h4>
	 <h5> h5, 제목5 </h5>
	 <h6> h6, 제목6 </h6>
	 
	 <!-- 
	   	# hr 태그
	   		- 구분선, 수평라인을 표시한다.
	   		- 속성
	   		 > align : 수평선 정렬 지정
	   		   size  : 수평선 두께 지정
	   		   width : 수평선 넓이 지정
	   
	  -->
	  <hr size="30" align="left" width="300px"> <!-- 기본 단위 픽셀 -->
	  <hr size="30%" align="center" width="50%"> <!-- % : 화면 전체 크기에서 지정. 화면 크기를 변경하면 그에 맞는 퍼센트로 크기가 변경된다 -->
	  <hr align="right" width="500px" noshade="noshade"> <!-- noshade : 그림자없이 한 줄만 -->
</body>
</html>
```
> #### 결과화면
>   
> ![image](https://user-images.githubusercontent.com/79209568/113863322-7bc86380-97e4-11eb-9381-890a4a7b0b8f.png)
  
  
### HTML 태그 : p tag
#### p 태그
- p 태그는 문단 하나를 만들 때 사용한다.
#### br 태그
 - 줄 바꾸기  
  
  
```html
<!-- Ex02_p-tag.html -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> p 태그</title>
<!-- 스타일을 밖으로 빼서 정의 가능 head의 title 아래에 적는다. 전체에 적용되지만 만약 태그 내부에서 설정한 값이 있으면 그것이 우선 적용된다. -->
<style>
p {
	font-size:30px;
}
</style>
</head>
<body>
	<!-- 
		# p 태그
		  - p 태그는 문단 하나를 만들 때 사용한다
	 -->
	 <h1> p 태그 </h1>
	 <p style="font-size:10px;"> 문서의 단락을 표기하기 위해 사용되는 태그다. </p>
	 <!-- 태그 안에다가 css를 적는 것을 권장하지 않음. -->
	 <p> 이전 단락과 구분되는 새로운 단락으로 단락과 단락 사이에 공간이 형성된다. </p>
	 
	 <!-- 
	   # br 태그
	     - 줄 바꾸기
	  -->
	  <p> 단락 안에서 개행이 필요 할 경우 br 태그를 사용한다.<br>
	  	  br 태그는 열린 태그만 제공한다.
	  </p>
</body>
</html>
```
> #### 결과화면
>   
> ![image](https://user-images.githubusercontent.com/79209568/113863815-16c13d80-97e5-11eb-9c97-dcfa151d5a03.png)
  
  
### HTML 폰트 스타일
#### 폰트 스타일 태그
- b 태그 : 글자 굵게 표시
- i 태그 : 글자 기울여서 표시
- small 태그 : 해당 글자를 주변 글씨보다 작게 표시
- u 태그 : 글자에 밑줄 표시
- mark 태그 : 글자에 하이라이트 표시  
  
##### html 특수 기호 (entity code)
- 웹브라우저 상에 특정 문자가 표시되지 않고 html 코드로 인식하는 경우에 사용한다.
- & 로 시작해서 ; 으로 끝난다.  
  
  
```html
<!-- Ex03_font-style.html -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 폰트 스타일 </title>
</head>
<body>
	<h1> font style </h1>
	<br>
	<p> b 태그는 글자를 <b>굵게 표시</b> 한다.</p>
	<p> i 태그는 글자를 <i>기울여서 표시</i> 한다.</p>
	<p> small 태그는 해당 글자를 주변 글자보다 <small>작게 표시</small> 한다.</p>
	<p> u 태그는 글자에 <u>밑줄 표시</u>를 한다.</p>
	<p> mark 태그는 글자에 <mark>하이라이트 표시</mark>를 한다.</p>
	
	<br>
	<hr>
	<br>
	
	<!-- 
		# html 특수 기호 (entity code)
		  - 웹브라우저 상에 특정 문자가 표시되지 않고 html 코드로 인식하는 경우에 사용한다.
		  - & 로 시작해서 ; 으로 끝난다.
	 -->
	 <h1> html 특수 기호 </h1>
	 <p> &amp;amp; → &amp; (앤드, 엠퍼센트) </p>
	 <p> &amp;nbsp; → 공&nbsp;&nbsp;&nbsp;&nbsp;백 </p> <!-- 원래 html에서 띄어쓰기는 많이해도 한 번만 적용된다. -->
	 <p> &amp;lt; → &lt; (꺽쇠 괄호 열기) </p>
	 <p> &amp;gt; → &gt; (꺽쇠 괄호 닫기) </p>
	 <p> &amp;quot; → &quot; (쌍따옴표) </p>
	 <p> &amp;#124; → &#124; (파이프)</p>
	 <p> &amp;#40; → &#40; (괄호 열기)</p>
	 <p> &amp;#41; → &#41; (괄호 닫기)</p>
	 
</body>
</html>
```

> #### 결과화면
>   
> ![image](https://user-images.githubusercontent.com/79209568/113863976-4cfebd00-97e5-11eb-98e1-922cdfb086ae.png)

### HTML 태그 : a tag
#### a 태그
- 텍스트나 이미지에 링크를 걸 때 사용한다.
- <a href="url" target="">
  - href   : 이동하는 url을 지정한다.
  - target : 링크를 클릭했을 때 웹사이트가 열리는 곳을 지정한다.  
    - _self : 현재 페이지에서 열림
    - _blank : 새로운 페이지에서 열림
  
  
```html
<!-- Ex04_a-tag.html -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>a 태그</title>
<style>
#top { font-size:30px; text-decoration:none; } 
#end { font-size:30px; }
</style>
</head>
<body>
	<!-- text-decoration:none; 밑줄 사라짐 -->
	<!-- 
		# a 태그
		  - 텍스트나 이미지에 링크를 걸 때 사용한다.
		  - <a href="url" target="">
		    > href   : 이동하는 url을 지정한다.
		      target : 링크를 클릭했을 때 웹사이트가 열리는 곳을 지정한다.
	 -->
	 <h1> a 태그 </h1>
	 <br>
	 <p><a href="http://www.naver.com" target="_self"> NAVER </a></p>
	 <p><a href="http://www.kdca.go.kr/index.es?sid=a2" target="_blank"> 질병관리청 </a></p>
	 <!-- target 
	 		self: 현재 페이지에서 열림 
	 		blank: 새로운 페이지에서 열림
	 -->
	 <br>
	 <p><a href="#end" id="top"> 마지막 이동 </a></p> <!-- # : 아이디 속성 값을 쓸때 사용 -->
	 <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	 <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	 <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	 <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	 <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	 <p><a id="end"> 마지막</a></p>
	 <p><a href="#top"> top</a></p>
</body>
</html>
```
> #### 결과 화면
>  
> ![image](https://user-images.githubusercontent.com/79209568/113867004-f5fae700-97e8-11eb-957f-73e06f7054f1.png)  
> * 네이버 클릭했을 때  
> ![image](https://user-images.githubusercontent.com/79209568/113868207-648c7480-97ea-11eb-9923-c0df55303520.png)
> * 마지막 이동을 클릭했을 때 (top 클릭하면 '마지막 이동'으로 이동)
> ![image](https://user-images.githubusercontent.com/79209568/113867041-01e6a900-97e9-11eb-8d93-cf5d2cfcec17.png)


### HTML 태그 : img tag
#### img 태그
- 웹페이지에 이미지를 넣어 줄 때 사용한다
  - src : 이미지 파일이 저장된 경로 지정
  - title : 이미지에 대한 설명
  - alt : 웹 접근성을 위해 시각 장애인 등 화면낭독기를 사용하는 사람을 위한 대체 텍스트
  - width/height : 이미지의 가로, 세로 사이즈 지정  
  
  
```html
<!-- Ex05_img-tag.html -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>a 태그</title>
</head>
<body>
	<!--
		# img 태그
		  - 웹페이지에 이미지를 넣어 줄 때 사용한다
		    > src	: 이미지 파일이 저장된 경로 지정
		      title	: 이미지에 대한 설명
		      alt	: 웹 접근성을 위해 시각 장애인 등 화면낭독기를 사용하는 사람을 위한 대체 텍스트
		      width/height : 이미지의 가로, 세로 사이즈 지정
	 -->
	 
	 <h1> img 태그 </h1>
	 <br>
	 <!-- 기본 사용 -->
	 <img src="images/img_02.jpg" alt="유재석 피규어 사진입니다">
	 
	 <br>
	 <!-- 이미지 타이틀, 크기 조정 -->
	 <!-- title : 마우스를 이미지 위에 올렸을 때 뜨는 텍스트 -->
	 <img src="images/html5.jpg" title="HTML5 로고" width="200px" height="250px">
	 <br>
	 <img src="images/html5.jpg" title="HTML5 로고" width="50%" height="60%">
	 <br>
</body>
</html>
```
> #### 결과 화면
>   
> ![image](https://user-images.githubusercontent.com/79209568/113868587-cd73ec80-97ea-11eb-8609-ffcb35c9902e.png)

