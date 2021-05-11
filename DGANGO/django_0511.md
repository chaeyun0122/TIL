# HTML 2
## form 태그
* <input>태그들을 묶음
  * type속성으로 입력 데이터 양식 설정(text, password, checkbox 등)
  * name속성으로 입력 데이터의 이름 설정(프로그래밍언어의 변수명 같은 개념)
* 사용자가 입력한 데이터들을 서버로 전송하는 역할(요청 파라미터)
* submit이벤트가 발생되면 전송함
  * 하나의 HTML페이지에 두 개 이상의 <form>태그 작성 가능
  * 단, 한 번에 하나의 <form>데이터만 전송 가능

### 속성
* action: 폼 데이터를 전달(요청)할 URL 설정
* method: 폼 데이터의 전달 방식(목적) 지정(get, post, put, delete 등)
  * get: 서버로 요청 시 정보를 얻기 위한 요청을 의미(게시글 번호, 상품 번호 등)
    * 요청 파라미터를 URL뒤에 (?name1=value1&name2=value2...)와 같이 전달
    * 요청 파라미터 길이에 제한이 있음
  * post: 서버로 요청 시 정보를 보내기 위한 요청을 의미(회원가입, 게시글 등록 등)
    * 요청 파라미터를 HTTP Request Body안에 포함하여 전송
    * 요청 파라미터 길이에 제한이 없음
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>폼 데이터 전송</title>
</head>
<body>
    <form action="ex01-html.html" method="get"> <!-- action : 요청을 보내는 페이지 / method : 요청 방식-->
        <input type="text" name="userid"><br>
        <input type="password" name="userpwd"><br>
        <input type="submit" value="GET요청"> <!-- <form> 태그 내에 선언 된 <input>태그에 value들을 전송하는 버튼-->
    </form>
    <hr>
    <form action="ex01-html.html" method="post">
        <input type="text" name="userid"><br>
        <input type="password" name="userpwd"><br>
        <input type="submit" value="POST요청">
    </form>
    <hr>
    <form action="https://search.naver.com/search.naver">
        검색어 : <input type="text" name="query">
        <button type="submit">검색</button>
    </form>
    <!-- 
        get, post 둘 다 보안성이 좋지 않음. post는 웹 브라우저 상 보이지 않을 뿐 request body안에 파라미터를 포함함.
        보안성을 기준으로 선택하지 말고 단지 사용하는 목적에 따라서 다르게 사용하면 됨.
    -->
</body>
</html>
```
## input 태그
### 속성
#### type: 입력 양식 종류 설정
* button: 일반 버튼
* checkbox: 체크 박스
* file: 로컬 파일 선택
* hidden: 표시만 하지 않는 값을 표현(서버로 전달 됨)
* image: 그림 선택
* password: 입력 데이터를 \*로 표시
* radio: 라디오 버튼
* text: 텍스트 입력 상자
* submit: 폼 전송버튼
* reset: 폼에 입력된 데이터를 모두 삭제
#### name: 입력된 데이터의 이름(변수명)
#### value: 입력된 데이터 또는 전송할 값
#### placeholder: 텍스트 입력 상자에 입력할 값을 안내하는 문자열
#### checked: 라디오 버튼이나 체크박스의 기본 선택 여부 지정

## label 태그 
* 지정된 텍스트를 선택하면 해당 input태그에 포커스를 맞춤
  ```html
  for="선택할<input>의 id"
  ```
### form태그(+input, label) 예제
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>입력 양식 태그</title>
</head>
<body>
    <form>
        <label for="n1">text:</label> <input id="n1" type="text" name="n1" value="aaa" placeholder="텍스트 입력"><br>
        <label for="n2">password:</label> <input id="n2" type="password" name="n2" placeholder="비밀번호 입력"><br>
        radio:
        <input type="radio" value="man" name="gender" id="man"><label for="man">남</label>
        <input type="radio" value="woman" name="gender" id="woman" checked><label for="woman">여</label><br>
        checkbox:
        <input type="checkbox" value="A" name="btype" id="A"><label for="A">A</label>
        <input type="checkbox" value="B" name="btype" id="B" checked><label for="B">B</label>
        <input type="checkbox" value="AB" name="btype" id="AB" checked><label for="AB">AB</label>
        <input type="checkbox" value="O" name="btype" id="O"><label for="O">O</label>
        <input type="submit" value="전송">
    </form>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/79209568/117759267-77b5b880-b25e-11eb-8a94-b1e091d41b07.png)

## 선택자
> * 특정 요소들을 선택해서 디자인을 적용시키는 역할을 한다.
> * HTML 문서의 \<head> 태그 내에 \<style> 태그로 입력한다.
> * 따로 css 파일을 적용시키는 것도 가능하다. (\<head>내에 입력한다)
>   ```
>   <link href="style.css" rel="stylesheet">
>   ```
* **tag 선택자** : 적용할 태그 입력
* **id 선택자** : 적용할 아이디 앞에 `#`입력
* **class 선택자** : 적용할 클래스 앞에 `.` 입력
### 디자인이 적용되는 선택자의 우선순위
> tag 선택자 < class 선택자 < id 선택자
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>선택자</title>
    <!-- css 속성, js 정의, 외부 파일 불러오기-->
    <style>
        /* HTML요소에 디자인 속성 (CSS) 지정 */
        h1 { color: red; } /* tag 선택자 */
        #c { color: violet; } /* id 선택자 */
        .grp1 { color: yellowgreen; } /* class 선택자 */
        .grp2 { color: darkgreen; }
    </style>
    
    <!-- <link href="style.css" rel="stylesheet"> -->
</head>
<body>
    <!-- 하나의 요소(객체)들이 있을 -->
    <!-- id는 식별하기 위해서고 class는 그룹으로 묶기 위해서-->
    <h1 id="a" class="grp1">요소1</h1>
    <h1 id="b" class="grp2">요소2</h1>
    <h1 id="c" class="grp1">요소3</h1>
    <h1 id="d" class="grp2">요소4</h1>
    <h1 id="e" class="grp1">요소5</h1>
</body>
</html>
```
* 모든 요소는 tag 선택자를 통해 red로 설정되었지만 class 선택자의 우선순위가 높기 때문에 yellowgreen과 darkgreen으로 나온다.
* '요소3'은 'grp1'클래스여서 yellowgreen으로 설정되었지만 id 선택자의 우선순위가 우선이기 때문에 violet으로 나온다.  
![image](https://user-images.githubusercontent.com/79209568/117762317-dd587380-b263-11eb-81ae-a50403f4ffbf.png)



> * HTML : 구조와 요소를 정의
> * CSS : HTML을 통해 정의된 구조의 디자인을 정의
> * JS : 
>   * HTML 태그 내에 style 속성으로 디자인을 설정할 수 있지만 따로 정의해놓은 디자인 속성들을 적용시키는 것을 









