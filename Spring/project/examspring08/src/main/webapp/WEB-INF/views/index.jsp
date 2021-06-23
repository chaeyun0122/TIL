<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
<h2>Index</h2>
<ul>
	<li><a href="${pageContext.request.contextPath}/hello">/hello : 기본 동작 실행</a></li>
</ul>
<ul>
	<li><a href="${pageContext.request.contextPath}/ex/exam1">/ex/exam1 : Method처리 - 처리 전</a></li>
	<li><a href="${pageContext.request.contextPath}/ex/exam3">/ex/exam3 : Method처리 - 처리 후</a></li>
</ul>
<ul>
	<li><b>연습</b></li>
	<li><a href="${pageContext.request.contextPath}/prac/add">/prac/add : 덧셈</a></li>
	<li><a href="${pageContext.request.contextPath}/prac/factorial">/prac/factorial : 팩토리얼</a></li>
</ul>
</body>
</html>