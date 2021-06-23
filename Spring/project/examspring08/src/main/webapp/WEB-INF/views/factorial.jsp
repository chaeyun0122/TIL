<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>factorial</title>
</head>
<body>
<h2>팩토리얼</h2>
<form method="post" action="${pageContext.request.contextPath}/prac/factorial">
	<input type="text" name="n" placeholder="숫자입력"> !<br><br>
	<input type="submit" value="전송"></form>
</body>
</html>