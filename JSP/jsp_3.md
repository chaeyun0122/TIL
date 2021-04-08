# HTML 태그 2
### HTML 태그 : div 태그
#### div 태그
- 페이지 레이아웃이나 태그를 그룹화 할 때 사용한다.  
##### \<style>
```html
	<style type="text/css">
	#area_one { 
		width:200px;
		height:100px;
		float:left; /* float 속성 값이 없으면 기본으로 세로 정렬을 한다.*/
		border:1px solid red;
	}
	#area_two { 
		width:200px;
		height:100px;
		float:left;
		border:1px solid blue;
	}
	#area_three { 
		width:200px;
		height:100px;
		float:left;
		border:1px solid green;
	}
	</style>
```
##### \<body>
```html
	<body>
	 <h1> div </h1>
	 <br>
	 <div id="area_one">
		<p> 왼쪽 그룹 </p>
		<p> 안녕하세요 </p>
	 </div>
	 <div id="area_two">
		<p> 가운데 그룹 </p>
		<p> 안녕하세요 </p>
	 </div>
	 <div id="area_three">
		<p> 오른쪽 그룹 </p>
		<p> 안녕하세요 </p>
	 </div>
	</body>
```  
  
  > #### 결과화면
  >   
  > ![image](https://user-images.githubusercontent.com/79209568/114014898-176cd900-98a4-11eb-814e-add0cf7ea23f.png)

### HTML 태그 : form 태그
#### form 태그
* 사용자가 입력한 데이터를 서버에 전송 시키기 위해 사용하는 태그
  * action : form을 전송할 서버쪽 파일 지정
  * name   : form을 식별하기 위한 이름
  * target : form에 입력된 데이터가 서버를 거쳐 처리된 결과를 표시할 프레임
  * method : form을 서버에 전송할 http 메서드 지정
#### label 태그
- input 태그의 text 이름을 지정

#### input 태그
- form 태그 안에 사용하는 요소 중에서 가장 중요한 요소로, 사용자로부터 데이터를 입력받기 위해 사용한다.
  - type : 입력 태그의 형태 지정
  - name : 서버로 전달되는 이름 지정
  - value : 입력 변수(태그)의 초기값을 설정


# HTML 동작
## 텍스트 관련

### input type="text"
* 문자를 입력 받는 타입
### input type="password"
* 비밀번호를 입력 받는 타입(입력 시 \*로 숨김 처리)
### input type="hidden" 
* 사용자에게 보이지 않고 서버 쪽에 데이터를 보낼 때 사용하는 타입. summit 후 url에 보임(get 형식일 때)
	```html
	 <form action="#" method="get">
 		 <fieldset>  <!-- 테두리 생성 -->
 			<legend> 내용 입력 </legend>
 			<label> ID </label>
 			<input type="text" id="userid" name="userid" maxlength="20"><br>
 			<label> 이름 </label>
 			<input type="text" id="username" name="username" maxlength="20"><br>
 			<label> 비밀번호 </label>
 			<input type="password" id="userpw" name="userpw" maxlength="20"><br> <!-- 비밀번호 작성 input 타입 -->
 			<label> 자기소개 </label><br>
 			<textarea id="memo" name="memo" rows="5" cols="50"></textarea><br>
 			<!-- 사용자에겐 보이지 않지만 서버쪽에 데이터를 보낼 때 : hidden 타입-->
 			<label> 숨김항목 </label>
 			<input type="hidden" id="secret" name="secret" value="비밀(보낼 값 작성)"> <!-- 화면에 안보이지만 summit 후 url에 value값이 보임 -->
  			<br>
 			<input type="submit" value="보내기"> <!-- 전송하는 input 타입 -->
 		</fieldset>
 	 </form>
 	```
 	> #### 결과화면
 	>   
 	> ![image](https://user-images.githubusercontent.com/79209568/114021303-8568ce80-98ab-11eb-8d54-69116391e8f0.png)
 	> * submit 후 URL : `localhost:8080/P01_HTML/Ex07_input-text.html?userid=hi&username=cy&userpw=1234&memo=hello&secret=비밀%28보낼+값+작성%29#`
 
## 선택 관련
### input type="checkbox"
* 하나의 name에 value값만 다르게 구성해서 여러개를 선택할 수 있다.
 
 	```html
 	<h1> checkbox </h1>
 	<br>
 	<form action="#">
 		<fieldset>
 			<legend> 취미 선택 </legend>
 			<input type="checkbox" id="hobby1" name="hobby" value="축구" checked> <!-- checked : 기본 선택 항목 설정 -->
 			<label for="hobby1"> 축구 </label> <!-- for : label과 input 태그를 연결(id값이랑 맞춤) -->
 			<input type="checkbox" id="hobby2" name="hobby" value="야구">
			<label for="hobby2"> 야구 </label>
 			<input type="checkbox" id="hobby3" name="hobby" value="농구">
 			<label for="hobby3"> 농구 </label>
 		</fieldset>
 	</form>
 	```
 	> #### 결과화면
 	>   
 	> ![image](https://user-images.githubusercontent.com/79209568/114023947-6fa8d880-98ae-11eb-87fe-93e8b3f9fc71.png)
 
### input type="radio"
* 하나의 그룹에 radio 버튼 하나만 선택 할 수 있다.
	```html
	 <h1> radio </h1>
	 <br>
	 <form action="#">
		<fieldset>
			<legend> 성별 선택 </legend>
			<input type="radio" id="gender_m" name="gender" value="m" checked>
			<label for="gender_m"> 남성 </label>
			<input type="radio" id="gender_f" name="gender" value="f">
			<label for="gender_f"> 여성 </label>
		</fieldset>
		<br>
		<input type="submit" value="보내기">
	 </form>
	```
	> #### 결과화면
	>   
	> ![image](https://user-images.githubusercontent.com/79209568/114029648-b13c8200-98b4-11eb-95c3-2b1dd3829392.png)



### select 태그
- 드롭다움을 사용해서 여러개의 목록을 정의한다.
- 서버에 전송되는 데이터는 select 요소의 name 속성이 key, option의 value 속성이 값으로 해서 'key-value' 형태로 전송된다.
##### option 태그
- select 태그 안의 목록을 생성한다.
##### optgrouop 태그
- option 태그를 그룹화한다.
```html
<h1> select 태그 </h1>
<br>
<form action="#">
	<fieldset>
		<legend> 통신사 </legend>
		<label for="telecom"> 선택 </label>
		<select id="telecom" name="telecom">
			<option value="KT"> KT </option>
			<option value="SKT"> SKT </option>
			<option value="U+"> LG </option>
		</select>
	</fieldset>
	<fieldset>
		<legend> 자동차 </legend>
		<select id="car_list" name="carList" size="8">
			<optgroup label="domestic">
				<option value="현대"> 현대 </option>
				<option value="기아"> 기아 </option>
				<option value="쌍용"> 쌍용 </option>
			</optgroup>
			<optgroup label="foreign">
				<option value="benz"> BENZ </option>
				<option value="audi"> AUDI </option>
				<option value="bmw"> BMW </option>
			</optgroup>
		</select>
	</fieldset>
	<br>
	<input type="submit" value="전송">
</form>
```
> #### 결과화면
>   
> ![image](https://user-images.githubusercontent.com/79209568/114026014-b697cd80-98b0-11eb-86c3-15f169348e95.png)


