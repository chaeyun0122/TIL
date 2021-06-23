<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>exam3</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/ex/exam3">
	<input type="text" name="name" placeholder="이름"><br>
	<input type="text" name="age" placeholder="나이"><br>
	<input type="submit" value="전송">
</form>
</body>
</html>