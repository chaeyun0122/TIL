<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>exam1</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/ex/exam2">
	<input type="text" name="name" placeholder="�̸�"><br>
	<input type="text" name="age" placeholder="����"><br>
	<input type="submit" value="����">
</form>
</body>
</html>