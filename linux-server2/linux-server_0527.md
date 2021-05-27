# DB server
- DataBase Server : 데이터베이스를 모아 놓은 서버
- 스마트폰 개발 및 보금에 따라 인터넷의 활용이 급속도로 늘어났고, 무수히 많은 데이터 생성
  - 많은 데이터들을 효율적으로 저장 및 관리하기 위해서 database를 사용
- CentOS는 6.x 버전까지 `mysql` 이라는 DB서버를 무료로 사용했으나,  
`mysql`이 유료화되면서 CentOS 7.0 버전부터는 `mysql`의 마이너 카피인 `mariadb` 사용

## DB 용어 정리
| 용어 | 의미 |
|:---:|:---|
| database | table의 집합체 |
| table | 실제 데이터가 저장되는 최소 단위 |
| field | table에 저장되는 데이터의 종류 (= column) |
| value | field에 맞춰서 table에 저장되는 데이터 |
| SQL문 | DB의 제어를 위해 사용하는 명령어 |

## mariadb 정보
- 패키지 : mariadb-*
- 데몬 : mariadb
- 방화벽 : service=mysql (well-known port 사용 X)
- 설정파일 :
  - /etc/my.cnf(DB서버 동작 자체에 영향)
  - mysql (DB서버 내부에 있는 database)

## mariadb 설치
```
yum -y install mariadb-*
```
* `systemctl restart mariadb` 데몬 재실행으로 서버 먼저 동작 시키기

## 접속 명령어
### 형식
```
mysql -u <DB계정> -p [<database이름>]
# database이름을 적어주면 해당 DB를 사용하면서 접속
```
### 접속해보기
```sql
mysql -u root -p mysql
```
* 암호 입력창이 나오지만 현재 암호 없으므로 그냥 Enter
![image](https://user-images.githubusercontent.com/79209568/119790620-0c7f0e00-bf0f-11eb-80fc-798d39389368.png)

## DB 사용
* **화면 청소** : `ctrl + l`
* **문장 끝마침** : `;` 혹은 `\g`
* **문장 무효시키기** : `\c`
* **DB 서버의 버전, 날짜, 시간 확인** : `select version(), current_date, current_time;`
  
  ![image](https://user-images.githubusercontent.com/79209568/119791358-b9f22180-bf0f-11eb-82ae-4faf91840a37.png)
### Database와 Table
* **Database/table 확인**
  ```
  show { databases | tables };
  ```
  * databases : 현재 접속한 DB 서버에서 내가 사용 가능한 database 목록 출력
    
    ![image](https://user-images.githubusercontent.com/79209568/119792475-b01cee00-bf10-11eb-9bb6-899809f72505.png)
  * tables : 현재 사용중인 database 내부의 table 목록 출력
    
    ![image](https://user-images.githubusercontent.com/79209568/119792950-1bff5680-bf11-11eb-9438-c9b577cbb640.png)
    * user 테이블 : db서버의 사용자 계정 관리 테이블
    * db 테이블 : 사용자 권한에 관련한 테이블
      * 일반 사용자의 경우 user테이블에서 모든 권한을 막고 db 테이블에서 사용자의 특정 권한을 설정해준다.

* **Database/table 생성**
  ```
  create database <생성할 database 이름>;
  create table <생성할 table 이름> (필드1 자료형(크기), 필드2 자료형(크기) ... );
  ```
  
  > ![image](https://user-images.githubusercontent.com/79209568/119795463-74cfee80-bf13-11eb-8ea2-65a08d8f7448.png)  
  > * (database를 바꿔주고,)  
  > 
  > ![image](https://user-images.githubusercontent.com/79209568/119796518-67673400-bf14-11eb-85e6-c25144f13775.png)


* **사용중인 database 변경**
  ```
  use <database 이름>;
  ```
  
  > ![image](https://user-images.githubusercontent.com/79209568/119795808-c8423c80-bf13-11eb-97db-8e256314ff8f.png)

* **table의 구조 확인 (field 확인)**
  ```
  describe <table 이름>;
  desc <table 이름>;
  explain <table 이름>;
  ```
  
  > ![image](https://user-images.githubusercontent.com/79209568/119797391-27ed1780-bf15-11eb-9dff-b66874d31910.png)
  > ![image](https://user-images.githubusercontent.com/79209568/119800737-03df0580-bf18-11eb-883d-cf5c5789b261.png)
  > * host에서 접속하는 user의 passwor의 내용
  > * primary key를 한번에 확인 가능
  > * 호스트에서 접속하는 사용자가 DB서버 전체에 대해서 어떤 권한을 쓸 수 있는지에 대한 내용

* Database/table 삭제
  ```
  drop { database | table } <이름>;
  ```
  * DB를 사용중이어도 지운다.
  
  > ![image](https://user-images.githubusercontent.com/79209568/119797910-a2b63280-bf15-11eb-8952-0df59908ddd8.png)
  > ![image](https://user-images.githubusercontent.com/79209568/119798263-f294f980-bf15-11eb-8297-02ea144f4291.png)

### Table Value
* table 내부 value 확인
  ```
  select <field 이름> from <table 이름>;
  ```
  > ![image](https://user-images.githubusercontent.com/79209568/119800914-2c66ff80-bf18-11eb-979a-25cc5293115e.png)





