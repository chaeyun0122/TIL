<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>뷰</title>
</head>
<body>
처리결과 : <%= request.getAttribute("result") %><br>
처리결과 : ${ result }
</body>
</html>

