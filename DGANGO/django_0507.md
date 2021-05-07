> visual code extensions
> * **Emmet Live** :쓰고자 하는 태그를 여러 개 쓰고 싶을 때 사용 
>   * `br*5` → `<br><br><br><br><br>`
>   * `! + 엔터`를 하면 html 기본 구조를 작성해준다.
>   * `ul>li*4`
> * **Auto Rename** :태그를 바꾸면 자동으로 닫는 태그를 바꿔준다.  
  
# HTML
> HTML이란 웹 브라우저들 사이에서 태그를 통일시키는 웹 표준 언어
## HTML 기본 구조
#### ex01-html
```html
<!DOCTYPE html>  <!-- HTML5 문서 타입 선언 -->
<html>
    <head>
        <!-- 브라우저에 미리 알려줄 내용 -->
        <title> 문서 제목 </title>
    </head>
    <body>
        <h1> 제목입니다. </h1> <!-- 태그 -->
        사용자에게 보여질 내용
    </body>
</html>
```

## h 태그
> h 태그는 글자 크기를 지정해주는 태그다.
* Elements : 문서를 구성하는 요소 → 태그
* h 태그는 display 속성을 block으로 가지기 때문에 자동 개행이 된다. 만약 inline으로 바꾸면 개행하지 않는다.  
  
  <img src="https://user-images.githubusercontent.com/79209568/117397039-32c81400-af36-11eb-80af-b53bc9c1dab4.png" width="400" height="400">
* 따로 개행을 하려면 `<br>` 태그를 사용해야한다.
#### ex02-h.html
```html
<!DOCTYPE html>
<html>
    <head>
        <title> 헤드라인 </title>
    </head>
    <body>
        <h1>정보</h1>
        이름 : 홍길동<br>
        나이 : 20살<br>
        주소 : 산골짜기<br>

        <h1>Headline</h1>
        <h2>Headline</h2>
        <h3>Headline</h3>
        <h4>Headline</h4>
        <h5>Headline</h5>
        <h6>Headline</h6>
    </body>
</html>
```
![image](https://user-images.githubusercontent.com/79209568/117397382-f9dc6f00-af36-11eb-8113-48f3c5feff99.png)

## p 태그
> p 태그는 단락을 만들어주는 태그다.
* 기본 display 속성이 block이다. (자동 개행)
#### ex03-p.html
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    안녕하세요<br>
    <hr> <!-- 줄 그어주는 태그 -->
    <p></p>
    <hr>
    <p>
        제 이름은 홍길동입니다.
    </p>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/79209568/117399233-0b277a80-af3b-11eb-9a68-3e2bcff3fb97.png)

## a 태그
> a 태그는 링크를 만들어주는 태그다.
* 기본 display 속성이 inline이다.
* 속성
  * href : 링크 경로 (로컬 파일 경로와 URL가 가능하다, `#이름`으로 다른 a 태그의 `name="이름"` 위치로 이동한다.)
  * target : `"_blank"` 속성 값을 넣어주면 새 창에서 링크를 연다.
#### ex04-a.html
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>앵커</title>
</head>
<body>
    <!-- <a href="링크경로(로컬파일경로, URL)">보여질 링크</a> -->
    <a href="https://www.naver.com/">네이버 링크</a><br>
    <a href="./ex01-html.html">로컬 파일 링크</a><br>

    <p>a태그에 target="_blank" 속성을 추가하면 새 탭에서 링크 결과 보여줌</p>
    <a href="https://www.naver.com/" target="_blank">네이버 링크(target="_blank")</a><br>
    <a href="./ex01-html.html" target="_blank">로컬 파일 링크(target="_blank")</a><br>
</body>
</html>
```
#### ex05-a.html
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>앵커</title>
</head>
<body>
    <a href="#bottom" name="top">아래로</a><br><br>
    처음 문장<br>
    문장<br>
    문장<br>
    문장<br>
    문장<br>
  .
  .
  .
    문장<br>
    문장<br>
    문장<br>
    문장<br>
    문장<br>
    끝 문장<br><br>
    <!-- <a name="bottom"></a> -->
    <a href="#top" name="bottom">위로</a>
</body>
</html>
```

## list 태그
### ul
> ul 태그는 순서가 없는 리스트를 작성하는 태그다.
#### ex06-ls.html
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>목록</title>
</head>
<body>
    <h2>프로그래밍 언어</h2>
    <ul>
        <li>C언어</li>
        <li>네트워크 기초</li>
        <li>JAVA</li>
        <li>JSP</li>
    </ul>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/79209568/117401634-eaadef00-af3f-11eb-82db-97ecd1275181.png)

### ol
> ol 태그는 순서가 있는 리스트를 작성하는 태그다.
#### ex07-ls.html
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>목록</title>
</head>
<body>
    <h2>프로그래밍 언어</h2>
    <ol>
        <li>C언어</li>
        <li>네트워크 기초</li>
        <li>JAVA</li>
        <li>JSP</li>
    </ol>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/79209568/117401702-0b764480-af40-11eb-8346-f03c2dbc31b1.png)

### 실습 문제
> 리스트 항목을 클릭하면 해당 사이트로 이동하도록 직성
* ul태그와 a태그를 사용한다.
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>실습문제</title>
</head>
<body>
    <h2>다음 리스트 항목을 클릭하면 해당 사이트로 이동하도록 작성하세요</h2> 
    <hr>
    <ul>
        <li><a href="https://www.naver.com/" target="_blank">네이버</a></li>
        <li><a href="https://www.daum.net/" target="_blank">다음</a></li>
        <li><a href="https://www.google.com/" target="_blank">구글</a></li>
    </ul>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/79209568/117402615-b3404200-af41-11eb-8d5e-e3d27af15fe8.png)
