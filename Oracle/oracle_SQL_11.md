# DDL 문을 사용하여 테이블 생성 및 관리
## 테이블 이름 지정 규칙
* 문자로 시작해야 한다.
* 길이는 1–30자 사이여야 한다.
* A–Z, a–z, 0–9, \_, $, \# 만 포함할 수 있다. (타이핑할 때의 말이고 저장되면 무조건 대문자로 생성됨)
* 동일한 유저가 소유한 다른 객체의 이름과 중복되면 안된다.
* Oracle 서버 예약어는 사용할 수 없다.

## CREATE TABLE 문
### 테이블 생성
```sql
CREATE TABLE DEPT (
  DEPTNO  NUMBER(2),
  DNAME   VARCHAR2(14),
  LOC     VARCHAR2(13),
  DATA    DATE DEFAULT SYSDATE
);
```
### 테이블 생성 확인
```SQL
DESCRIBE DEPT;
```
### 다른 유저의 테이블에 액세스
* 다른 유저가 소유한 테이블은 유저의 스키마에 없기 때문에 다른 유저의 소유자 이름을 접두어로 사용해야 한다.
```SQL
SELECT * FROM SCOTT.EMP;  -- SCOTT 유저의 EMP 테이블
```
### DEFAULT 옵션
* 삽입 시 열의 기본 값을 지정한다.
```SQL
column_name   data_type  DEFAULT  ~~;
```
* 올바른 값 : 리터럴 값, 표현식, SQL함수
* 잘못된 값 : 다른 열의 이름, pseudocolumn

## 데이터 유형
| 데이터 유형     | 설명                                                        |
|-----------------|-------------------------------------------------------------|
| VARCHAR2(size)  | 가변 길이 문자 데이터                                       |
| CHAR(size)      | 고정 길이 문자 데이터                                       |
| LONG            | 가변 길이 문자 데이터(최대 2GB)                             |
| CLOB            | 문자 데이터(최대 4GB)                                       |
| NUMBER(p,s)     | 가변 길이 숫자 데이터                                       |
| DATE            | 날짜 및 시간 값                                             |
| RAW 및 LONG RAW | Raw binary data                                             |
| BLOB            | 이진 데이터(최대 4GB)                                       |
| BFILE           | 외부 파일에 저장된 이진 데이터(최대 4GB)                    |
| ROWID           | 테이블에 있는 행의 고유한 주소를 나타내는 base-64 숫자 체계 |

* LONG보다 CLOB 사용을 권장함.

### NUMBER
```SQL
CREATE TABLE T2 
(C1   NUMBER, 
 C2   NUMBER(4,2), -- 전체 4자리에서 소수점 자리 2자리 최대값 99.99 
 C3   NUMBER(4,0), -- (4)와 같음 전체 4자리 최대값 9999.
 C4   NUMBER(2,4), -- 최대값 0.0099
 C5   NUMBER(2,-2));  -- 최대값 9900.
```
### DATE
![image](https://user-images.githubusercontent.com/79209568/115103845-eff6ca00-9f8f-11eb-9095-85b504584623.png)  
```SQL
SELECT SYSDATE + TO_YMINTERVAL('1-5') --오늘로부터 1년 5개월 후의 날짜
FROM DUAL ; 
```

## 제약 조건
* 제약 조건은 잘못된 데이터의 삽입, 수정, 삭제를 막아주는 규칙
* 외울 필요 x
* 총 5개 : `NOT NULL`, `UNIQUE`, `PRIMARY KEY`, `FOREIGN KEY`, `CHECK`

### 제약 조건 지침
* 유저가 제약 조건의 이름을 지정하거나 Oracle 서버가 SYS_Cn 형식을 사용하여 이름을 생성할 수 있다.
* 제약 조건 생성 시점
  * 테이블 생성되는 시점 (CREATE TABLE ~ CONSTRAINT ~)
  * 테이블 생성 후 (ALTER TABLE ~ ADD CONSTRAINT ~)
* 열 또는 테이블 레벨에서 제약 조건을 정의한다.
  * 열 레벨 제약 조건
    ```SQL
    CREATE TABLE EMP2 (
      EMPNO  NUMBER(6) CONSTRAINT emp_no_pk PRIMARY KEY,
      ...
    );
    ```
  * 테이블 레벨 제약 조건
    ```SQL
    CREATE TABLE EMP2 (
      EMPNO  NUMBER(6),
      ...
      DEPTNO  NUMBER(6) NOT NULL,
      CONSTRAINT emp_no_pk PRIMARY KEY (EMPNO)
    );
    ```

### PRIMARY KEY
* 중복 값과 널 값을 허용하지 않도록 보장
```SQL
ALTER TABLE DEPT2 
ADD CONSTRAINT DEPT2_PK PRIMARY KEY(DEPTNO) ;
```

### NOT NULL
* 열에 NULL값이 허용되지 않도록 보장
```SQL
ALTER TABLE EMP2 
MODIFY ENAME NOT NULL ; 
```

### UNIQUE
* 중복 값을 허용하지 않도록 보장. 하지만 널 값은 가능
```SQL
ALTER TABLE EMP2 
ADD CONSTRAINT EMP2_UK UNIQUE (EMAIL);
```
### CHECK
* 각 행이 충족해야하는 조건을 정의
```SQL
ALTER TABLE EMP2 
ADD CHECK (SAL > 0) ; 
```

### FOREIGN KEY
* 테이블 제약 조건 레벨에서 하위 테이블의 열을 정의한다.
* `ON DELETE CASCASE` : 상위 테이블의 행이 삭제될 때 하위 테이블의 종속 행을 삭제
* `ON DELETE SET NULL` : 종속 FOREIGN KEY 값을 NULL로 변환
```SQL
ALTER TABLE EMP2 
ADD FOREIGN KEY(DEPTNO) REFERENCES DEPT2(DEPTNO) ; 

-- 이후 삭제 쿼리 실행하면 오류가 난다.
DELETE DEPT2
WHERE DEPTNO = 30;
/* 만약 FOREIGN 제약조건에 ON DELETE CASCADE를 추가했으면 부서번호 30을 쓰는 사원들이 다 함께 삭제됨
                          ON DELETE SET NULL을 추가했으면 부서번호가 30인 사원들의 DEPTNO가 NULL값이 됨 */

```

## 서브쿼리를 사용하여 테이블 생성
* **CREATE TABLE 문**과 **AS subquery 옵션**을 결합하여 테이블을 생성하고 행을 삽입
  ![image](https://user-images.githubusercontent.com/79209568/115104017-d99d3e00-9f90-11eb-8cb7-c56d69fcfa4d.png)
  ```SQL
  CREATE TABLE EMP2 
  AS SELECT * FROM EMP ; 
  ```
## ALTER TABLE
* 새 열 추가
* 기존 열 정의 수정
* 새 열에 기본값 정의
* 열 삭제
* 열 이름 바꾸기
* 읽기 전용 상태로 테이블 변경
  - 테이블을 읽기 전용 모드로 설정하여 테이블 유지 관리 중에 DDL 또는 DML이 변경되는 것을 방지
  - 테이블을 읽기/쓰기 모드로 다시 되돌린다.
  ```SQL
  ALTER TABLE EMP READ ONLY;
  ALTER TABLE EMP READ WRITE;
  ```
## DROP TABLE
* 테이블 삭제이며 Recycle bin(휴지통)으로 이동
  ```sql
  DROP TABLE T1;
  ```
### DROP한 테이블 다시 살리기
* Recycle bin을 확인
  ```SQL
  SHOW RECYCLEBIN;
  ```
  ![image](https://user-images.githubusercontent.com/79209568/115106937-c09d8880-9fa2-11eb-8666-9ebce7b27d34.png)
* 해당 이름을 SELECT문으로 확인해도 가능함 (DROP은 이름을 바꾼다고도 볼 수 있음)
  ```SQL
  SELECT * FROM "BIN$6yks0Jt0QIKMsNMMNOBnLQ==$0";
  ```
* 테이블 되 살리기
  ```SQL
  FLASHBACK TABLE T2 TO BEFORE DROP;
  ```
### 완전히 삭제하는 방법
* `PURGE` 명령어 사용
  ```SQL
  DROP TABLE T1 PURGE;
  ```
