# 회원 가입 페이지
## 회원 데이터베이스 테이블 생성
* sqlDeveloper에서 DB에 member테이블을 생성한다.
```sql
 create table member(
	 name varchar2(30) not null,
	 id varchar2(30) primary key,
	 pwd varchar2(30) not null,
	 gender varchar2(3),
	 email varchar2(20),
	 domain varchar2(20),
	 tel varchar2(13),
	 addr varchar2(100),
	 logtime date
 );
```

## 회원 가입 페이지 생성
### wirteForm JSP 페이지
* 프로젝트의 WebContent에 `member폴더 > writeFrom.jsp` 파일 생성
* form의 틀을 만든다.
  ```html
  <form action="write.jsp" name="writeForm" method="post">
		<table border="1" cellpadding="4">
			<tr>
				<td width="120" align="center"> 이 름 </td>
				<td width="300"><input type="text" name="name"></td>
			</tr>
			<tr>
				<td align="center"> 아이디 </td>
				<td>
					<input type="text" name="id">
					<input type="button" value="중복체크" onclick="checkId()">
				</td>
			</tr>
			<tr>
				<td align="center"> 비밀번호 </td>
				<td><input type="password" name="pwd"></td>
			</tr>
			<tr>
				<td align="center"> 비밀번호 확인 </td>
				<td><input type="password" name="repwd"></td>
			</tr>
			<tr>
				<td align="center"> 성 별</td>
				<td>
					<input type="radio" name="gender" id="genderM" value="M" checked>
					<label for="genderM"> 남 </label>
					<input type="radio" name="gender" id="genderF" value="F">
					<label for="genderF"> 여 </label>
				</td>
			</tr>
			<tr>
				<td align="center"> E-mail </td>
				<td>
					<input type="text" name="email" size="10"> @
					<select name="domain">
						<option value="naver.com"> naver </option>
						<option value="gmail.com"> gmail </option>
						<option value="daum.net"> daum </option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="center"> 핸드폰 </td>
				<td>
					<input type="text" name="tel1" size="3" maxlength="3"> -
					<input type="text" name="tel2" size="3" maxlength="4"> -
					<input type="text" name="tel3" size="3" maxlength="5">
				</td>
			</tr>
			<tr>
				<td align="center"> 주 소 </td>
				<td><input type="text" name="addr" size="50"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="회원가입" onclick="checkWrite()"> &nbsp;
					<input type="reset" value="다시작성">
				</td>
			</tr>
		</table>
	</form>
  ```
  > ![image](https://user-images.githubusercontent.com/79209568/115703688-64bd7000-a3a5-11eb-961d-2d738b155272.png)

### DTO 생성






