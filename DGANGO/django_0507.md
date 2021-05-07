> visual code extends
> * **Emmet Live** :쓰고자 하는 태그를 여러 개 쓰고 싶을 때 사용 (`br*5` → `<br><br><br><br><br>`)
> * **Auto Rename** :태그를 바꾸면 자동으로 닫는 태그를 바꿔준다.  
  
# HTML
> HTML이란 웹 브라우저들 사이에서 태그를 통일시키는 웹 표준 언어
## ex01-html
* HTML 기본 구조
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

## ex02-h-tag.html
* Elements : 문서를 구성하는 요소 → 태그
* h 태그는 display 속성을 block으로 가지기 때문에 자동 개행이 된다. 만약 inline으로 바꾸면 개행하지 않는다.  
  ![image](https://user-images.githubusercontent.com/79209568/117397039-32c81400-af36-11eb-80af-b53bc9c1dab4.png)
* 따로 개행을 하려면 `<br>` 태그를 사용해야한다.
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
