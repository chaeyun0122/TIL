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
* Database : table들의 집합
* table : 2차원 구조의 표. 관리하고자 하는 속성과 데이터들의 집합
  * column : 관리하고자 하는 속성들
  * row : 하나하나의 데이터들
  
  ![image](https://user-images.githubusercontent.com/79209568/114254731-0dee8880-99ec-11eb-9c8d-11c14ec66263.png)
  
  
# SQL SELECT문을 사용하여 데이터 검색
## sql문 작성
* SQL문은 대소문자를 구분하지 않는다.
* SQL문은 한 줄 또는 여러 줄에 입력할 수 있다.
* 키워드는 약어로 표기하거나 여러 줄에 걸쳐 입력할 수 없다.
* SQL Developer에서 쿼리문 실행의 단축키는 `CTRL + ENTER`
* 쿼리문 마지막엔 ;(세미콜론)을 입력해야 한다.
  * SQL Developer의 경우 여러 SQL문을 실행하는 경우에 필요
  * SQL \*PLUS의 경우 반드시 세미콜론으로 끝내야한다.

## 기본 SELECT 문
![image](https://user-images.githubusercontent.com/79209568/114254911-3d51c500-99ed-11eb-8e74-e10795912b8c.png)

### 모든 열 선택
```sql
SELECT * FROM EMP;
```
### 특정 열 선택
* ,(콤마)로 열을 구분해서 입력
```sql
SELECT EMPNO, ENAME, SAL, DEPTNO FROM EMP;
```

## SELECT문의 산술식 및 NULL 값
### 산술식
* 산술 연산자를 사용해서 숫자 및 날짜 데이터로 표현식을 작성할 수 있다. **(문자일 때는 실행되지 않음)**
* 숫자일 경우 모두 가능하고 날짜일 경우 안되는 것도 있다.
  * \+ 는 가능하지만 \* 는 안됨.
  ```SQL
  SELECT HIREDATE, HIREDATE + 1 FROM EMP; -- 가능
  SELECT HIREDATE, HIREDATE * 2 FROM EMP; -- 오류
  ```
* 연산자 우선 순위 존재

### NULL 값 정의
* Null은 사용할 수 없거나, 할당되지 않았거나, 알 수 없거나, 적용할 수 없는 값
* **Null은 0이나 공백과는 다르다**
* SQL Developer에서는 널 값과 공백을 구분하기 위해 표시 형식을 지정할 수 있다.
  
  ![image](https://user-images.githubusercontent.com/79209568/114255797-a38c1700-99f0-11eb-8026-3658460fd89f.png)
* NULL 값을 포함하는 산술식은 NULL로 계산된다. 무조건 NULL을 출력

## 열 ALIAS
* 열 머리글의 이름을 바꾼다.
* 열 이름 바로 뒤에 변경할 이름 작성. AS를 써도 된다.
  ```SQL
  SELECT EMPNO, ENAME, SAL, COMM, SAL+COMM AS SALARY FROM EMP;
  
  SELECT EMPNO, ENAME, SAL, COMM, SAL+COMM SALARY FROM EMP;
  ```
* 공백이나 특수 문자를 포함하거나 대소문자를 구분하는 경우 **큰 따옴표**가 필요
  ```SQL
  SELECT EMPNO, ENAME, SAL, COMM, SAL+COMM AS "salary" FROM EMP;
  ```
  
## 연결 연산자
* 열이나 문자열을 다른 열에 연결
* 두 개의 세로선( || )으로 나타냄
* 결과 열은 문자식으로 생성됨
```SQL
SELECT ENAME, JOB, ENAME || JOB FROM EMP ;
```
> ![image](https://user-images.githubusercontent.com/79209568/114256075-9a9c4500-99f2-11eb-9412-5f2317216007.png)


## 리터럴 문자열
* 리터럴은 SELECT 문에 포함된 문자, 숫자 또는 날짜
* 숫자는 그냥 써도 된다.
* 날짜 및 문자 리터럴 값은 **작은 따옴표**로 묶어야한다.
  * 작은 따옴표를 출력하려면 두 번 쓰면된다.
  ```SQL
  SELECT department_name || 'Department''s Manager Id: ' || manager_id As "Department and Manager" FROM departments;
  ```
```SQL
SELECT ENAME, JOB, ENAME || ' is a ' || JOB FROM EMP ;
```
> ![image](https://user-images.githubusercontent.com/79209568/114256111-dc2cf000-99f2-11eb-820d-f671dd0e3a6b.png)

## 대체 인용(q) 연산자
* 자신의 따옴표 구분자를 지정
* 구분자를 임의로 선택한다.
* 가독성 및 사용성이 증가한다.
```SQL
SELECT department_name || q'[ Department's Manager Id: ]' || manager_id AS "Department and Manager" FROM departments;
```

## 중복 행
* 기본적으로 query 결과에는 중복 행을 포함한 모든 행이 표시된다.
* 중복 행을 제거하기 위해 ```DISTINCT``` 혹은 ```UNIQUE```를 사용한다.
  ```sql
  SELECT DISTINCT DEPTNO FROM EMP ;
  SELECT DISTINCT DEPTNO, JOB FROM EMP ;
  ```
* DISTINCT는 행의 중복을 제거하는 것이기 때문에 열 별로 중복 제거를 지정할 수는 없다.
  ```sql
  SELECT DEPTNO, DISTINCT JOB FROM EMP ;  -- ERROR 
  ```
  
## DESCRIBE 명령
* 테이블의 구조를 표시하는 명령어
* 약어 ```DESC```로도 가능하다.
```SQL
DESCRIBE EMP;
DESC EMP;
```
