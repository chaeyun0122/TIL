# 환경 설치
## SQL Developer 다운로드
* Oracle 데이터베이스에서 SQL로 작업하기위한 통합 개발 환경  
  
![image](https://user-images.githubusercontent.com/79209568/114253251-720d4e80-99e4-11eb-876d-7814bd8febdc.png)

### 새 접속
* '새 접속' 클릭
* 테스트 클릭후 상태:성공 나오면 접속 클릭  
  
  ![image](https://user-images.githubusercontent.com/79209568/114253661-01673180-99e6-11eb-98fc-365310d9695a.png)
  * 사용자 이름 : ORA01
  * 비밀번호 : oracle

### 환경 설정
* `도구 > 환경설정`
  * `코드 편집기 > 글꼴` : 글꼴 크기 20
  * `코드 편집기 > 행 여백` : 행번호 표시 체크
  * `데이터베이스 > 객체 뷰어` : 한번 눌러 객체 열기 체크 해제
  * `데이터베이스 > NLS` : 날짜 형식 'YYYY/MM/DD'로 변경

# 개요
## SQL
> #### 사용자가 SP(서버프로세스)로 명령어를 전달하여 DB server에서 요청을 수행하도록 하는 언어
## SQL 종류
* QUERY : SELECT 
* DML   : INSERT, UPDATE, DELETE, MERGE 
* TCL   : COMMIT, ROLLBACK, SAVEPOINT 
* DDL   : CREATE, DROP, ALTER, TRUNCATE 
* DCL   : GRANT, REVOKE 
## 데이터베이스
* table : 2차원 구조의 표. 관리하고자 하는 속성과 데이터들의 집합
  * column : 관리하고자 하는 속성들
  * row : 하나하나의 데이터들
  
  ![image](https://user-images.githubusercontent.com/79209568/114254731-0dee8880-99ec-11eb-9c8d-11c14ec66263.png)
  
  
# SQL SELECT문을 사용하여 데이터 검색

