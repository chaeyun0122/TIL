# HTML 태그 (이어서)
### HTML 태그 : div 태그
#### div 태그
- 페이지 레이아웃이나 태그를 그룹화 할 때 사용한다.  
```html
<!-- Ex06_div.html -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> div 태그 </title>
<style type="text/css">
#area_one { 
	width:200px;
	height:100px;
	float:left; /* float 속성 값이 없으면 기본으로 세로 정렬을 한다.*/
	border:1px solid red;
}
#area_two { 
	width:200px;
	height:100px;
	float:left;
	border:1px solid blue;
}
#area_three { 
	width:200px;
	height:100px;
	float:left;
	border:1px solid green;
}
</style>
</head>
<body>
	<!-- 
		# div 태그
		  - 페이지 레이아웃이나 태그를 그룹화 할 때 사용한다.
	 -->
	 
	 <h1> div </h1>
	 <br>
	 <div id="area_one">
	 	<p> 왼쪽 그룹 </p>
	 	<p> 안녕하세요 </p>
	 </div>
	 <div id="area_two">
	 	<p> 가운데 그룹 </p>
	 	<p> 안녕하세요 </p>
	 </div>
	 <div id="area_three">
	 	<p> 오른쪽 그룹 </p>
	 	<p> 안녕하세요 </p>
	 </div>
</body>
</html>
```  
  
> #### 결과화면
>   
> ![image](https://user-images.githubusercontent.com/79209568/114014898-176cd900-98a4-11eb-814e-add0cf7ea23f.png)
