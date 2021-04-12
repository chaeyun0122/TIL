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
- JDK 폴더의 jre > bin 폴더 안에 `msvcr100.dll`파일을 넣는다. ([파일 다운로드](JSP/util/msvcr100.dll))
  - 위치 : C:\Program Files\Java\jdk1.8.0_251\jre\bin
- `sqldeveloper > sqldeveloper > bin` 폴더의 `sqldeveloper.conf` 파일을 메모장으로 연다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/114389342-da6c5380-9bcf-11eb-94d6-c4dabcb2f370.png)
    
- 제일 끝에 `SetJavaHome C:\Program Files\Java\jdk1.8.0_251`(jdk경로) 를 입력 후 저장한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/114389511-0d164c00-9bd0-11eb-98f2-8603ce1dd910.png)

## 실행
- `sqldeveloper.exe` 실행한다.
- JDK 경로를 입력한다.  
   
  ![image](https://user-images.githubusercontent.com/79209568/114386901-ca06a980-9bcc-11eb-9abb-dd96a755fdcc.png)


