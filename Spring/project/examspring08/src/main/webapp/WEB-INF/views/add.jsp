<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>add</title>
</head>
<body>
<h2>더하기</h2>
<form method="post" action="${pageContext.request.contextPath}/prac/add">
	<input type="text" name="n1" placeholder="숫자1"> + 
	<input type="text" name="n2" placeholder="숫자2"><br><br>
	<input type="submit" value="전송"></form>
</body>
</html>