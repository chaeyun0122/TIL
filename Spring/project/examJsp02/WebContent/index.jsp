<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Index</title>
</head>
<body>
<h2>Index Page</h2>
<ul>
	<li><a href="${pageContext.request.contextPath}/simple">/simple</a></li>
	<li><a href="${pageContext.request.contextPath}/simple?type=greeting">/simple?type=greeting</a></li>
	<li><a href="${pageContext.request.contextPath}/simple?type=date">/simple?type=date</a></li>
	<li><a href="${pageContext.request.contextPath}/simple?type=test">/simple?type=test</a></li>
</ul>
<ul>
	<li><a href="${pageContext.request.contextPath}/conui">/conui</a></li>
	<li><a href="${pageContext.request.contextPath}/conui?cmd=">/conui?cmd=</a></li>
	<li><a href="${pageContext.request.contextPath}/conui?cmd=hello">/conui?cmd=hello</a></li>
	<li><a href="${pageContext.request.contextPath}/conui?cmd=add">/conui?cmd=add</a></li>
	<li><a href="${pageContext.request.contextPath}/conui?cmd=sub">/conui?cmd=sub</a></li>
	<li><a href="${pageContext.request.contextPath}/conui?cmd=mul">/conui?cmd=mul</a></li>
	<li><a href="${pageContext.request.contextPath}/conui?cmd=div">/conui?cmd=div</a></li>
</ul>
<ul>
	<li><a href="${pageContext.request.contextPath}/hello.do">/hello.do</li>
	<li><a href="${pageContext.request.contextPath}/add.do">/add.do</li>
	<li><a href="${pageContext.request.contextPath}/sub.do">/sub.do</li>
	<li><a href="${pageContext.request.contextPath}/mul.do">/mul.do</li>
	<li><a href="${pageContext.request.contextPath}/div.do">/div.do</li>
	<li><a href="${pageContext.request.contextPath}/test.do">/test.do</li>
	<li><a href="${pageContext.request.contextPath}/test">/test</li>
</ul>
</body>
</html>