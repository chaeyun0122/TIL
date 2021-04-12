# DB
## DB (database)
- 전자 방식으로 저장 된 구조화 된 정보 또는 데이터의 체계적인 집합을 의미한다.
- 데이터베이스는 데이터베이스 관리 시스템(DBMS)에 의해서 제어된다.

## 데이터베이스 관리 시스템(DBMS : Database Management System)
- 데이터를 편리하게 저장하고 효율적으로 관리하고 검색할 수 있는 환경을 제공 해주는 시스템

## 서비스 확인
- `제어판 > 관리도구 > 서비스`
- `OracleOraDB18Home1TNSListener`과 `OracleServiceXE`의 상태가 `실행 중`이면 DB 서비스가 작동 중이라는 뜻
  
![image](https://user-images.githubusercontent.com/79209568/114385923-8b242400-9bcb-11eb-8879-5721894b9ac9.png)

# sql developer
## sql developer 다운로드
- [오라클 사이트](https://www.oracle.com/tools/downloads/sqldev-downloads.html)에서 sql developer 다운로드

## 실행
- `sqldeveloper.exe` 실행한다.
- JDK 경로를 입력한다.  
   
  ![image](https://user-images.githubusercontent.com/79209568/114386901-ca06a980-9bcc-11eb-9abb-dd96a755fdcc.png)

> ### 실행이 안될 경우
> - JDK 폴더의 jre > bin 폴더 안에 `msvcr100.dll`파일을 넣는다. ([파일 다운로드](JSP/util/msvcr100.dll))
>   - 위치 : C:\Program Files\Java\jdk1.8.0_251\jre\bin
> - `sqldeveloper > sqldeveloper > bin` 폴더의 `sqldeveloper.conf` 파일을 메모장으로 연다.  
>  
>   ![image](https://user-images.githubusercontent.com/79209568/114389342-da6c5380-9bcf-11eb-94d6-c4dabcb2f370.png)
>    
> - 제일 끝에 `SetJavaHome C:\Program Files\Java\jdk1.8.0_251`(jdk경로) 를 입력 후 저장한다.  
>  
>   ![image](https://user-images.githubusercontent.com/79209568/114389511-0d164c00-9bd0-11eb-98f2-8603ce1dd910.png)

### 새 접속
![image](https://user-images.githubusercontent.com/79209568/114390092-c543f480-9bd0-11eb-952e-0b221fe15ea5.png)

### 기본 세팅
> #### Sql Developer 한글 글꼴 설정
> - 한글이 잘 안되는 경우가 종종 있으므로 한글 글꼴을 설정해주는 것이 좋다.
> - sql developer **실행 전**에 [이 글꼴 파일](JSP/util/D2Coding-Ver1.3.2-20180524-all.ttc)을 `모든 사용자용으로 설치`를 한다.
  
#### 세팅 위치 `도구 > 환경설정`
- `환경 > 인코딩` : UTF-8로 설정
- `코드 편집기 > 글꼴 > 글꼴 이름` : D2Coding으로 설정
- `코드 편집기 > 행 여백` : 행 번호 표시 체크 설정
  
#### system 암호 설정
- sqlplus 실행
- 사용자 입력 : **sys as sysdba**
- 비밀번호 : 입력 없이 **Enter**
- `alter user system identified by 변경 비밀번호;`
- `alter user system identified by oracle;`

#### port 확인
- `select dbms_xdb.getHttpPort() from dual;`를 입력하고 확인한다.
- 결과가 9090이 아니면 `exec dbms_xdb.setHttpPort(9090);`로 포트 번호를 9090으로 변경 해준다.

## 실습
### 사용자 계정 생성
- 11g 버전 : `create user 생성계정명 identified by 패스워드;`
- 18c 버전 : `create user c##생성계정명 identified by 패스워드;`
```sql
create user c##dbtest identified by a1234;
```
![image](https://user-images.githubusercontent.com/79209568/114396233-4bb00480-9bd8-11eb-87fc-82c7e5aeccf3.png)

### 계정 확인
```sql
select * from all_users;
```
* 위에서 생성한 c##dbtest를 확인한다.
  ![image](https://user-images.githubusercontent.com/79209568/114396322-697d6980-9bd8-11eb-8f46-0f7a123505c5.png)

### 계정 삭제
- `drop user 계정명`
- 데이터가 있는 계정 삭제의 경우 `drop user 계정명 cascade;`
```sql
drop user c##dbtest;
select * from all_users;
```
![image](https://user-images.githubusercontent.com/79209568/114396510-a0537f80-9bd8-11eb-9d3f-a102532227d0.png)  
![image](https://user-images.githubusercontent.com/79209568/114396725-d4c73b80-9bd8-11eb-88c6-ceede564f042.png)


