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
> 프로젝트 : examspring07
> 패키지 : examspring03의 spring 패키지
> 리소스 : examspring03의 applicationContext.xml

### DB 연동을 위한 dependency 추가
#### pom.xml
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
    - ojdbc6 jar 파일을 해당 폴더에 넣어주고 pom.xml을 다시 저장해주면 오류는 사라진다.
    ![image](https://user-images.githubusercontent.com/79209568/122336863-6b68fd80-cf78-11eb-938c-2dd10e48fff6.png)
