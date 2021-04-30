# JSP 파일 업로드
## 파일 업로드에 필요한 요소
* 파일을 입력받는 form의 method 및 enctype
  - enctype : form data가 서버로 보내질 때의 인코딩 타입
  - `method="post" enctype="multipart/form-data"`
  - 파일 또는 이미지를 보낼 때는 무조건 `"multipart/form-data"`로 해야한다.
* 업로드 파일이 저장될 폴더
  - **storage** : WebContent안에 해당 폴더를 만들어 준다.  
  
    ![image](https://user-images.githubusercontent.com/79209568/116683455-99a37580-a9ea-11eb-84e0-1914499b367f.png)
  - 실제 파일은 여기에 저장되지 않고 실제 폴더에 따로 있다. (아래에 설명)
* 파일을 업로드하고 form을 분석하는 라이브러리
  - **cos.jar**

## eclipe의 가상폴더와 실제폴더
* **가상 폴더**
  * `workspace\프로젝트명\WebContent\storage`
* **실제 폴더**
  * `workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\프로젝트명\storage`
  
## cos.jar
* http://servlets.com/cos 해당 사이트에서 cos-20.08/zip 파일 다운로드 후 압축 풀기  
  
  ![image](https://user-images.githubusercontent.com/79209568/116684927-8d201c80-a9ec-11eb-8b4b-7ea5cab0cfd7.png)
* lib 폴더안의 cos.jar을 프로젝트의 `WEB-INF > lib`안에 넣어준다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/116684967-9ad5a200-a9ec-11eb-9213-e1a3ab406d37.png)

## 파일 업로드 실습
#### fileForm.jsp
```jsp
<%-- fileForm.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 파일 업로드 </title>
</head>
<body>
	<form action="fileUpload.jsp" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<th width="50"> 제 목 </th>
				<td><input type="text" name="subject" size="50"></td>
			</tr>
			<tr>
				<th width="50"> 내 용 </th>
				<td><textarea name="content" rows="20" cols="50" style="resize:none"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="file" name="upload1" size="50"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="file" name="upload2" size="50"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="upload"></td>
			</tr>
		</table>
	</form>
</body>
</html>
```

#### fileUpload.jsp
* 서버 파일 저장 할 실제 폴더 선언
  ```jsp
  String realFolder = request.getServletContext().getRealPath("/storage");
  ```
* 파일 업로드 객체
  * **new MultipartRequest(request, 서버 파일 저장경로, 파일 최대 크기, 인코딩 방식, 파일 변경 정책);**
    * **DefaultFileRenamePolicy()** : 업로드 시 똑같은 파일이 있을 경우 기존 파일이름에 숫자를 덧붙여서 저장한다.
  ```jsp
  MultipartRequest mr = new MultipartRequest(request, realFolder, 5*1024*1024, "utf-8", new DefaultFileRenamePolicy());
  ```
* 전체 코드
```jsp
<%-- fileUpload.jsp --%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 실제 폴더
String realFolder = request.getServletContext().getRealPath("/storage");

// 파일 업로드 객체
// new MultipartRequest( request, 서버 파일 저장경로, 파일 최대 크기, 인코딩 방식, 파일 변경 정책);
//  > DefaultFileRenamePolicy() : 업로드 시 똑같은 파일이 있을 경우 기존 파일이름에 숫자를 덧붙여서 저장한다.
MultipartRequest mr = new MultipartRequest(request, realFolder, 5*1024*1024, "utf-8", new DefaultFileRenamePolicy());

String subject = mr.getParameter("subject");
String content = mr.getParameter("content");

// 사용자가 업로드한 실제 파일명 변환
String originalFileName1 = mr.getOriginalFileName("upload1");
String originalFileName2 = mr.getOriginalFileName("upload2");

// 서버에 저장된 파일명 변환
String fileName1 = mr.getFilesystemName("upload1");
String fileName2 = mr.getFilesystemName("upload2");

// 파일 객체 변환
File file1 = mr.getFile("upload1");
File file2 = mr.getFile("upload2");

long fileSize1 = 0;
long fileSize2 = 0;
if (file1 != null) fileSize1 = file1.length();
if (file2 != null) fileSize2 = file2.length();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 파일 업로드 확인 </title>
</head>
<body>
	<h3> 업로드 완료 ! </h3>
	<br>
	<ul>
		<li> 제 목 : <%=subject %></li>
		<li> 내 용 : <%=content %></li>
		<li> 파 일 : 
			<a href="fileDownload.jsp?fileName=<%=URLEncoder.encode(originalFileName1, "utf-8") %>">
				<%=originalFileName1 %>
			</a>
			&nbsp;&nbsp;&nbsp;
			<%=fileName1 %>
		</li>
		<li> 크 기 : <%=fileSize1 %></li>
		<br>
		<li> 파 일 : <%=originalFileName2 %> &nbsp;&nbsp;&nbsp; <%=fileName2 %></li>
		<li> 크 기 : <%=fileSize2 %></li>
	</ul>
</body>
</html>
```

> #### 결과
> * 파일 선택 버튼을 눌러서 파일을 업로드  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116690624-9ca36380-a9f4-11eb-927c-68cf0a5b4bce.png)
> * 업로드가 완료됨  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116690659-aaf17f80-a9f4-11eb-8df2-d7f87d884fa5.png)
> * 프로젝트내의 WebContent의 storage 폴더에는 파일이 들어가 있지 않는다. (가상 폴더이기 때문)
> * 파일 탐색기로 실제 폴더 위치 (`...\wtpwebaaps\프로젝트명\storage`)로 이동하면 파일이 저장되어 있음을 확인할 수 있다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116691840-5fd86c00-a9f6-11eb-92f2-f6c55714f598.png)

