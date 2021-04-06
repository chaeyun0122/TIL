# JAVA 환경설정
* eclipse 사이트에서 JDK 1.8 
![image](https://user-images.githubusercontent.com/79209568/113699549-ae089100-9710-11eb-9f57-fba8a0093fc8.png)  
* 해당 디렉터리 복사  
![image](https://user-images.githubusercontent.com/79209568/113699638-d0021380-9710-11eb-8dda-f02a16d25c10.png)  
* 고급 시스템 설정 클릭  
![image](https://user-images.githubusercontent.com/79209568/113699707-e4461080-9710-11eb-8856-a5c3d9bfea34.png)  
* 환경변수 클릭  
![image](https://user-images.githubusercontent.com/79209568/113699740-ee680f00-9710-11eb-91d7-5843f7f6c837.png)  
* 시스템 변수에서 새로 만들기  
* 변수 이름 : JAVA_HOME  
* 변수 값 : 위에서 복사한 디렉터리  
![image](https://user-images.githubusercontent.com/79209568/113699819-0a6bb080-9711-11eb-91d1-3a10cb98b84a.png)  
* Path값 더블클릭  
![image](https://user-images.githubusercontent.com/79209568/113699861-148daf00-9711-11eb-85cd-8da256986075.png)   
* 새로 만들기  
## 확인하기
![image](https://user-images.githubusercontent.com/79209568/113699932-2a9b6f80-9711-11eb-80ea-47568bb79790.png)  
* 관리자 cmd에서 확인  
  
  
# eclipse
![image](https://user-images.githubusercontent.com/79209568/113701771-8d8e0600-9713-11eb-9e97-185b58f0ef74.png)  
* eclipse 사이트의 'Download Package' 클릭  
![image](https://user-images.githubusercontent.com/79209568/113702489-86b3c300-9714-11eb-8d6d-d3c508e6b962.png)  
* 오른쪽 아래에 More Download에서 Eclipse 2020-06(4.16) 클릭    
* 권장 버전은 6월 이전 버전이다  
![image](https://user-images.githubusercontent.com/79209568/113702499-8adfe080-9714-11eb-8e8a-d36dfaf14947.png)  
* Enterprise버전의 window로 다운로드 받는다  
![image](https://user-images.githubusercontent.com/79209568/113702613-b2cf4400-9714-11eb-85c8-1a68bcac8fa9.png)  
* select Another Mirror를 눌러서 한국 서버에서 다운을 받아야 빠르게 받을 수 있다  
  
  
# oracle
## 사전 설정
* pc이름이 영어여야한다.
* 개발자 모드여야한다.

![image](https://user-images.githubusercontent.com/79209568/113703399-bf07d100-9715-11eb-89d4-f1a7bd4fc634.png)  
* 오라클 사이트에서 Support > Software Download > Database > Data 18c Express Edition  
![image](https://user-images.githubusercontent.com/79209568/113705533-7e5d8700-9718-11eb-805b-b4d3579b30bf.png)  
* 윈도우 버전으로 다운로드 후 설치  
![image](https://user-images.githubusercontent.com/79209568/113707304-aea62500-971a-11eb-972b-7f6ff8e9a0fa.png)  
* 설치 완료된 후 해당 화면의 내용을 따로 기록해두도록 한다  

## 확인하기
![image](https://user-images.githubusercontent.com/79209568/113710979-5887b080-971f-11eb-9217-5697ba17ba64.png)  
* 프롬프트 창에 sqlplus 입력
* 사용자명 : system
* 비밀번호 : 설치할 때 설정했던 비밀번호 *내 경우 9525*
  
# Apache Tomcat
![image](https://user-images.githubusercontent.com/79209568/113706364-91248b80-9719-11eb-8460-23b435cd34c8.png)  
* [사이트](http://tomcat.apache.org/)로 이동 후 왼쪽에 있는 Download 중 tomcat 9 클릭  
![image](https://user-images.githubusercontent.com/79209568/113706385-984b9980-9719-11eb-8eeb-fe20e9204dcf.png)  
* 64bit windows 파일 다운로드
![image](https://user-images.githubusercontent.com/79209568/113707010-43f4e980-971a-11eb-973f-341fa9e17421.png)  
* C드라이브에 옮긴 후 bin폴더의 startup.bat 파일 실행  
* 프롬프트 창이 뜨면서 알아서 실행된다  
![image](https://user-images.githubusercontent.com/79209568/113709801-e6629c00-971d-11eb-9ec8-ac43fcc37d0b.png)  
* 웹 브라우저에 `localhost:8080` 입력 후 해당 화면이 나오면 서버가 잘 작동하는 것  
![image](https://user-images.githubusercontent.com/79209568/113710562-d5665a80-971e-11eb-8a4e-ef952ae43aeb.png)  
* 서버 종료하기

# Oracle 실행하기
## 실행
![image](https://user-images.githubusercontent.com/79209568/113713164-f5e3e400-9721-11eb-9386-d9323172588d.png)   
* 작업 폴더를 생성한다. M4jspWork  
* eclipse.exe를 실행한다. (편리하도록 바로가기를 생성)  
* 해당 작업 폴더를 workspace에 지정한다.  

## 서버 설정
![image](https://user-images.githubusercontent.com/79209568/113713368-36dbf880-9722-11eb-9a4f-3b09217a1250.png)  
* Server 탭을 클릭해서 `No servers are available. Click this link to create a new server...`를 클릭  
![image](https://user-images.githubusercontent.com/79209568/113713527-6c80e180-9722-11eb-87c4-aafd1c78f4b4.png)  
* Apache 폴더 내의 Tomcat 9버전을 클릭 후 Next  
![image](https://user-images.githubusercontent.com/79209568/113713630-89b5b000-9722-11eb-8eab-0af96da0664e.png)  
* 설치했던 Tomcat 위치를 설정 후 Finish  
### 서버 실행
![image](https://user-images.githubusercontent.com/79209568/113713769-ab169c00-9722-11eb-9942-cac4f674cc6b.png)  
* Server 탭의 설정한 서버를 클릭 후 오른쪽 위의 실행 버튼을 클릭  
* Console 탭에서 시작되었다는 메시지가 뜨면 서버가 잘 실행 된 것이다.  
### 서버 종료
![image](https://user-images.githubusercontent.com/79209568/113713937-da2d0d80-9722-11eb-8a73-90769b51288b.png)  
* 오른쪽 위의 정지 버튼 클릭  

## eclipse 인코딩 설정
* `Window 탭 > Preferences`  
![image](https://user-images.githubusercontent.com/79209568/113714292-33953c80-9723-11eb-8e5f-2533bda7f4bd.png)   
* `General > Workspace` 에서 아래에 `Text file encoding`을 other에서 UTF-8f로 변경  
![image](https://user-images.githubusercontent.com/79209568/113714425-56bfec00-9723-11eb-88d8-8a90c716eaed.png)
* `Web` 탭에서 `CSS Files`, `HTML Files`, `JSP Files` 모두 Encoding을 UTF-8로 변경  
![image](https://user-images.githubusercontent.com/79209568/113714615-8c64d500-9723-11eb-9eb6-f7b8fcdb82a8.png)  
* `General > Content Types`의 `Java Class File`을 클릭 후 아래에 UTF-8을 입력

