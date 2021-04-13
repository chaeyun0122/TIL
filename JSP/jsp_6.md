## 사용자 계정 2
### 사용자 권한 설정
- `grant 권한1, 권한2 to 계정명;`
  - `connect` : 연결(접속)
  - `resource` : 자원 접근 권한
  
```sql
grant connect, resource to c##dbtest;
```
> #### 출력
> ![image](https://user-images.githubusercontent.com/79209568/114539228-3fd34980-9c8f-11eb-83ba-2d5db56dd839.png)

### tablespace 지정
- DBMS에 저장되는 자료가 있는 물리적인 파일이다. 여기에 데이터가 저장된다.
- `alter user 계정명 default tablespace 테이블스페이스명;`
  
```sql
alter user c##dbtest default tablespace users;
```
> #### 출력
> ![image](https://user-images.githubusercontent.com/79209568/114539867-def84100-9c8f-11eb-9d28-707fb12c9d42.png)

### quota 지정
- 사용할 tablespace 공간 지정
- `alter user 계정명 quota unlimited on 테이블스페이스명;`
  
```sql
alter user c##dbtest quota unlimited on users;
```

> #### 출력
> ![image](https://user-images.githubusercontent.com/79209568/114540163-2bdc1780-9c90-11eb-87b9-0e81a196252f.png)

### 새로운 사용자로 접속
- 새 접속으로 Name, 사용자 이름, 비밀번호 입력
- 테스트 후 `상태: 성공`을 확인
- 접속  
  
![image](https://user-images.githubusercontent.com/79209568/114541210-5ed2db00-9c91-11eb-9f43-02460f88c86f.png)
  
  
> 전체 정리
> ```sql
> create user c##clary identified by a1234;
>
> select * from all_users;
>
> grant connect, resource to c##clary;
>
> alter user c##clary default tablespace users;
>
> alter user c##clary quota unlimited on users;
> ```
> ![image](https://user-images.githubusercontent.com/79209568/114543275-f76a5a80-9c93-11eb-9dc9-cb00f78c1fbd.png)

  
# SQL
## SQL (Structured Query Language)
- 구조화된 질의 언어

## 사용 용도에 따른 질의문
- DQL (Data Query Language) : 데이터 검색
  - **SELECT** : 데이터 검색 시 사용

- DCL (Data Control Language) : 오라클 데이터베이스와 그 구조에 액세스 권한을 제공하거나 삭제
  - **GRANT** : 데이터베이스 권한 부여
  - **REVOKE** : 데이터베이스 권한 삭제

- DDL (Data Definition Language) : 데이터 정의어. 생성 및 변경
  - **CREATE** : 데이터베이스 생성
  - **ALTER** : 데이터베이스 변경
  - **DROP** : 데이터베이스 삭제
  - **RENAME** : 데이터베이스 이름 변경
  - **TRUNCATE** : 데이터베이스 저장 공간 삭제

- DML (Data Manipulation Language) : 데이터 조작
  - **INSERT** : 데이터 입력
  - **UPDATE** : 데이터 수정
  - **DELETE** : 데이터 삭제

- TCL (Transaction Control Language) : DML에 의해 조작된 결과를 다루는 명령어
  - **COMMIT** : 트랜잭션의 정상적인 종료 처리
  - **ROLLBACK** : 트랜잭션 취소
  - **SAVEPOINT** : 트랜잭션 내에 임시 저장 지정 설정

# TABLE
- 데이터베이스를 구성하는 기본 데이터구조로서 행과 열의 구조를 가지며 이 테이블을 이용하여 데이터를 입력, 수정, 삭제등을 하게 된다.
- row(행), column(열)로 구성된다.
  - ex) 테이블 이름 : DEPT

    | DEPTNO | DNAME   | LOC   |
    |--------|---------|-------|
    | 10     | develop | seoul |

## 오라클 데이터 자료형
- 문자/문자열
  - char(size) : 고정형
  - varchar(size) : 가변형
- 숫자형
  - number : 정수. 최대 40자리까지의 숫자 (소수점 포함 X)
  - number(w, d) : 실수. w는 전체길이, d는 소수점 이하 자릿수
- 날짜
  - date : 기본 날짜 형식 -> 'YY/MM/DD' 년/월/일

## 테이블 생성
- 형식
  ```sql
  create table 테이블명 (
  필드명 필드타입,
  필드명 필드타입,
  .
  .
  );
  ```
```sql
create table dbtest (
name varchart2(10),
age number,
height number(10, 2),
logtime date
);
```

### 계정 안의 테이블 확인
- `select * from 테이블명;`
```sql
select * from dbtest;
```
> ![image](https://user-images.githubusercontent.com/79209568/114546095-becc8000-9c97-11eb-9292-b4911d6b1156.png)

### 테이블 항목 확인
- `desc 테이블명;`
```sql
desc dbtest;
```
> ![image](https://user-images.githubusercontent.com/79209568/114546128-c7bd5180-9c97-11eb-958b-e9f883d2850d.png)

### 테이블 삭제
- `drop talble 테이블명;`
```sql
drop table dbtest;
```
> ![image](https://user-images.githubusercontent.com/79209568/114546445-1bc83600-9c98-11eb-9fd5-e24c8bbffbc9.png)

### 테이블 복원
- `flashback table 테이블명 to before drop;`
```sql
flashback table dbtest to before drop;
```
> ![image](https://user-images.githubusercontent.com/79209568/114546474-25519e00-9c98-11eb-9ea2-a44bd31610ab.png)

## 레코드
### 레코드 추가
- 테이블에 데이터 추가하기
- 전체 필드 혹은 일부 필드에 값을 추가  
  `insert into 테이블명 ( 필드명, ... ) values ( 값, ... )`
- 전체 필드에 값을 추가하고, 필드 순서랑 값을 입력할 경우 필드명 생략 가능  
  `insser into 테이블명 values ( 값, ... )`

```sql
insert into dbtest (name, age, height, logtime) values ('kim', 20, 171.1, sysdate);

-- 일부 필드에 데이터 추가
insert into dbtest (name, age) values ('hong', 20); 

-- 전체 필드에 데이터를 추가할 때는 필드 명 제외
insert into dbtest values ('kim', 33, 178.7, sysdate); 

select * from dbtest; -- 확인
```
> ![image](https://user-images.githubusercontent.com/79209568/114547613-9cd3fd00-9c99-11eb-8871-76801b2bc08e.png)

### 정렬
#### 오름차순 정렬
```sql
-- 나이 순으로 오름차순 정렬
select * from dbtest order by age asc;
```
> ![image](https://user-images.githubusercontent.com/79209568/114549820-76fc2780-9c9c-11eb-9853-ba33c5fbed94.png)

#### 내림차순 정렬
```sql
-- 나이 순으로 내림차순 정렬
select * from dbtest order by age desc;
```
> ![image](https://user-images.githubusercontent.com/79209568/114549944-998e4080-9c9c-11eb-8130-a6530006dd70.png)

### 전체 레코드 수
```sql
select count(*) from dbtest;
```
> ![image](https://user-images.githubusercontent.com/79209568/114550095-c80c1b80-9c9c-11eb-8901-bbb66d393360.png)

## 검색
### 조건식을 사용한 검색
- `select * from 테이블명 where 조건;`
```sql
-- 이름이 kim인 데이터 검색
select * from dbtest where name='kim';
```
> ![image](https://user-images.githubusercontent.com/79209568/114550495-39e46500-9c9d-11eb-856c-4d1f0303b7d1.png)

#### Like 연산자
- 와일드 카드 문자 (%, \_)와 함께 사용한다.
  - **%** : 0 개 이상의 일련문자
  - **\_** : 한개의 문자
  - Ex)  
    like A% : A로 시작하는 모든 문자  
    like %D : D로 끝나는 모든 문자  
    like A__ : A로 시작하는 3자리 문자열

```sql
-- 이름에 o가 들어가는 데이터 검색
select * from dbtest where name like '%o%';
```
> ![image](https://user-images.githubusercontent.com/79209568/114551028-d27ae500-9c9d-11eb-857e-7e0a7c930159.png)
```sql
-- 이름이 k로 시작하는 세글자인 데이터 검색
select * from dbtest where name like 'k__';
```
> ![image](https://user-images.githubusercontent.com/79209568/114551705-bb88c280-9c9e-11eb-8019-2e380618e435.png)

```sql
-- 이름에 k가 들어가고 나이가 30이하인 데이터 검색
select * from dbtest where name like '%k%' and age<=30;
```
> ![image](https://user-images.githubusercontent.com/79209568/114551677-af9d0080-9c9e-11eb-9005-d50abc82a240.png)

#### null 값 검색
- null 관련 검색은 일반 연산자가 아닌 `is` 혹은 `is not`을 통해 검색한다.
```sql
-- 키 항목이 null인 데이터 검색
select * from dbtest where height is null;
```
> ![image](https://user-images.githubusercontent.com/79209568/114551652-a6139880-9c9e-11eb-84f4-842b2e3905b1.png)
  
```sql
-- 키 항목이 null이 아닌 데이터 검색
select * from dbtest where height is not null;
```
> ![image](https://user-images.githubusercontent.com/79209568/114551811-dc511800-9c9e-11eb-89e2-bd6d03119e83.png)
