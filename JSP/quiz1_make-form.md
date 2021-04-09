# JSP Quiz1
## 해당 사진을 보고 똑같이 만들기
![image](https://user-images.githubusercontent.com/79209568/114174877-3d61ae80-9974-11eb-87bd-216f62c491ea.png)  
  
  
  
```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>개인 신상 정보 입력</title>
</head>
<body>
	<h2> 개인 신상 정보 입력 </h2>
	<label for="username"> - 이름 : </label>
	<input type="text" id="username" name="username"><br><br>
	
	<label for="passwd"> - 비밀번호 : </label>
	<input type="password" id="passwd" name="passwd"><br><br>
	
	<label for="gender"> - 성별 : </label>
	<input type="radio" id="man" name="gender">
	<label for="man">남성</label>
	<input type="radio" id="woman" name="gender">
	<label for="woman">여성</label><br><br>
	
	<label for="hobby"> - 취미 : </label>
	<input type="checkbox" id="hobby1" name="hobby">
	<label for="hobby1">영화감상</label>
	<input type="checkbox" id="hobby2" name="hobby">
	<label for="hobby2">게임</label>
	<input type="checkbox" id="hobby3" name="hobby">
	<label for="hobby3">음악듣기</label><br><br>
	
	<label for="file"> - 파일 첨부 : </label>
	<input type="file" id="file" name="file"><br><br>
	
	<label for="email"> - 이메일 : </label>
	<input type="text"> @ 
	<select id="email" name="email">
		<option value=""> -----선택----- </option>
		<option value="naver"> naver.com </option>
		<option value="gmail"> gmail.com </option>
		<option value="daum"> daum.net </option>
	</select><br><br>
	
	<label for="txtarea">- 자기소개 : </label>
	<textarea id="txtarea" name="txtarea" rows="5" cols="30"></textarea><br><br>
	
	<input type="submit" value="확인">
</body>
</html>
```
