# DB 연동
## 초기설정
### 1. vmware 15 pro 설치
### 2. vmware에서 window7 실행
- cmd에서 `ipconfig`로 IP 확인  

  ![image](https://user-images.githubusercontent.com/79209568/122333030-76209400-cf72-11eb-9005-080b3f277d67.png)

### 3. sql developer
- window7의 사용자로 접속
  > system/1234

  ![image](https://user-images.githubusercontent.com/79209568/122333210-c566c480-cf72-11eb-8a12-278f16625713.png)
- 새로운 사용자를 만들고 권한을 준다.

  ![image](https://user-images.githubusercontent.com/79209568/122333269-db748500-cf72-11eb-84ea-8cd72f86b6ad.png)
- 새로 만든 tester1로 접속 (**system 계정말고 이 계정을 이용한다.**)

  ![image](https://user-images.githubusercontent.com/79209568/122333311-e92a0a80-cf72-11eb-9b68-9f97bc8064f5.png)

## DB 준비
### table 생성
```sql
create table MEMBER (
    ID number primary key,
    EMAIL varchar2(200) unique,
    PASSWORD varchar2(100),
    NAME varchar2(100),
    REGDATE date
);
```
### sequence 생성
```sql
create sequence MEMBER_SEQ start with 1 increment by 1 maxvalue 99999;
```

### 새로운 행 삽입 테스트
![image](https://user-images.githubusercontent.com/79209568/122334683-0fe94080-cf75-11eb-8533-38cfdb47c6a8.png)

## Spring 프로젝트 준비
> - 프로젝트 : examspring07 ([👉project file](https://github.com/Clary0122/TIL/blob/main/Spring/project/examspring07))
> - 패키지 : examspring03의 spring 패키지
> - 리소스 : examspring03의 applicationContext.xml

### DB 연동을 위한 설정 추가
#### pom.xml
- **Dependency 추가**
- [spirng-jdbc 4.1.0버전](https://mvnrepository.com/artifact/org.springframework/spring-jdbc/4.1.0.RELEASE)
- [c3p0 0.9.5.2 버전](https://mvnrepository.com/artifact/com.mchange/c3p0/0.9.5.2)
- [commons-dbcp 1.4 버전](https://mvnrepository.com/artifact/commons-dbcp/commons-dbcp/1.4)
- ojdbc 6 버전
  - 직접 수동으로 추가
    ```
    <dependency>
        <groupId>oracle</groupId>
        <artifactId>ojdbc6</artifactId>
        <version>11.2.0.3</version>
    </dependency>
    ```
  - 저장하면 오류가 난다.
    - 사용자의 `.m2 > repository > oracle > ojdbc6 > 11.2.0.3` 폴더에 ojdbc6 jar 파일이 없기 때문이다.
    - [ojdbc6 jar](https://github.com/Clary0122/TIL/blob/main/Spring/project/ojdbc6-11.2.0.3.jar) 파일을 해당 폴더에 넣어주고 pom.xml을 다시 저장해주면 오류는 사라진다.
    
    ![image](https://user-images.githubusercontent.com/79209568/122336863-6b68fd80-cf78-11eb-938c-2dd10e48fff6.png)
- 추가 완료
  
  ![image](https://user-images.githubusercontent.com/79209568/122337078-c864b380-cf78-11eb-9738-1be9e8eb7cf5.png)

#### applicationContext.xml
![image](https://user-images.githubusercontent.com/79209568/122340392-2abfb300-cf7d-11eb-9de7-b7182bf82003.png)

#### MemberDao.java
- JdbcTemplate 클래스를 의존하는 MemberDao  
![image](https://user-images.githubusercontent.com/79209568/122342349-7e330080-cf7f-11eb-8da0-c6e8248d8293.png)

#### applicationContext.xml
- MemberDao 빈 설정   
![image](https://user-images.githubusercontent.com/79209568/122342572-c2be9c00-cf7f-11eb-9a06-ddb6709c68f9.png)

> ORM 기법

## SELECT
### 이메일로 조회
- RowMapper를 익명 중첩 클래스로 정의
- jdbcTemplate의 query함수는 세 가지 인자를 받는다.
  - 첫 번째 : SQL문
  - 두 번째 : rowMapper - 각 행이 매핑 될 하나의 오브젝트
  - 세 번째 : 쿼리에 바인딩할 인수  
![image](https://user-images.githubusercontent.com/79209568/122342792-f7325800-cf7f-11eb-9b22-2c2285a4eb03.png)

### 전체 목록 조회
#### MemberDao
- 익명 중첩 클래스로 정의했던 MapRow를 전체 조회에서도 같은 형식으로 사용할 것이기 때문에 따로 클래스로 정의하도록 한다. 
- SelectByEmailRowMapper 클래스를 정의한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/122507153-a7649700-d03a-11eb-8d18-5d55b842410a.png)
- `selectAll()`  
  
  ![image](https://user-images.githubusercontent.com/79209568/122507333-fb6f7b80-d03a-11eb-9fe5-9471db6b606d.png)
#### MemberListPrinter
![image](https://user-images.githubusercontent.com/79209568/122507624-7cc70e00-d03b-11eb-9211-7b752be02d0c.png)
#### Main
- 전체 목록 출력하는 코드 추가  
  
![image](https://user-images.githubusercontent.com/79209568/122507660-8e101a80-d03b-11eb-8d5a-f3a083e2aff8.png)
#### 결과
![image](https://user-images.githubusercontent.com/79209568/122507707-9ff1bd80-d03b-11eb-9dae-de777fa6046e.png)


### 목록 개수 가져오기
#### MemberDao
- queryForObject() : 결과가 하나의 행일 때 사용  
  
  ![image](https://user-images.githubusercontent.com/79209568/122507871-fced7380-d03b-11eb-9c45-804cb3a8ad2a.png)
#### Main
![image](https://user-images.githubusercontent.com/79209568/122507972-25756d80-d03c-11eb-826e-780d3ba3f213.png)
#### 결과
![image](https://user-images.githubusercontent.com/79209568/122507988-30c89900-d03c-11eb-8f92-f6a81435f555.png)

## UPDATE
### 이름, 비밀번호 수정하기
#### MemberDao  
![image](https://user-images.githubusercontent.com/79209568/122508193-88670480-d03c-11eb-8040-f8dbdc63939e.png)
#### Main  
![image](https://user-images.githubusercontent.com/79209568/122508547-1d69fd80-d03d-11eb-92c5-be6cf63bb38c.png)
#### 결과  
![image](https://user-images.githubusercontent.com/79209568/122508676-52765000-d03d-11eb-9fbb-db2f982ae0b9.png)

## INSERT
#### MemberDao
- KeyHolder : 쿼리에 사용되었던 값을 홀딩해주는 역할
- `new String[] {"ID"}` : 쿼리에 쓰인 홀딩할 인자(여기선 ID)를 리스트에 넣는다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/122510123-011b9000-d040-11eb-91fa-671db30db4f1.png)
- 키 값이 유지되는지 확인  
  
  ![image](https://user-images.githubusercontent.com/79209568/122510264-463fc200-d040-11eb-8608-ad1b70ba24cb.png)
#### Main
![image](https://user-images.githubusercontent.com/79209568/122510569-c2d2a080-d040-11eb-9325-1ed5e44ae746.png)
#### 결과
![image](https://user-images.githubusercontent.com/79209568/122510631-e0a00580-d040-11eb-9609-8345216704b5.png)

