<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>factorial</title>
</head>
<body>
<h2>���丮��</h2>
<form method="post" action="${pageContext.request.contextPath}/prac/factorial">
	<input type="text" name="n" placeholder="�����Է�"> !<br><br>
	<input type="submit" value="����"></form>
</body>
</html>