# 데이터 조작 (DML)
## INSERT : 테이블에 새 행 추가
* 컬럼 명을 생략하면 기존 컬럼 순서대로 대응해서 VALUES에 써 줘야 한다.
* 문자와 날짜는 작은 따옴표로 묶고 숫자는 그냥 써준다.
* 날짜는 SYSDATE 함수가 아니면, 특정 날짜를 지정하는 부분은 가급적 TO_DATE를 써준다.

### 서브 쿼리가 포함되는 INSERT문
* **VALUES절은 쓰면 안된다.** 바로 서브쿼리문 쓴다.
* INSERT 절의 열 개수와 데이터 타입이 서브쿼리의 것과 같아야 한다.

## UPDATE : 테이블의 기존 행 수정
* SET절의 =는 비교 연산자가 아닌 할당 연산자
* `SET column_name = NULL`을 지정하여 열 값을 NULL로 업데이트한다.
* WHERE 절을 지정하면 특정 행에서 값이 수정된다. (생략하면 모든 행에서 값이 수정됨)

### 서브 쿼리가 포함되는 UPDATE문
* SET절, WHERE절 둘 다 서브쿼리 사용 가능

## DELETE : 테이블에서 기존 행 제거
* WHERE 절을 지정하면 특정 행이 삭제된다. (생략하면 모든 행이 삭제됨)

### 서브 쿼리가 포함되는 DELETE문
* WHERE절에서 서브쿼리 사용 가능

> ## TRUNCATE
> * **ROLLBACK이 되지 않음.** 모든 행을 삭제하고 그 내용을 저장하는 명령어
> * 테이블의 구조는 그대로 남겨둔다.
> * DML문이 아니라 DDL문이므로 쉽게 언두할 수 없다.
>   * 언두 : 수정되기 전 원래 데이터의 복사본
> * 속도와 저장공간의 절약에 의미가 있다.
> ```SQL
> TRUNCATE TABLE SALGRADE;
> ```
> ![image](https://user-images.githubusercontent.com/79209568/115097681-acd43100-9f66-11eb-86c2-0722d0cbd65f.png)
 
## 트랜잭션 제어
### 데이터베이스 트랜잭션
* 데이터베이스의 상태를 변환시키는 하나의 논리적 기능을 수행하기 위한 작업의 단위 또는 한꺼번에 모두 수행되어야 할 일련의 연산들
* 트랜잭션 구성
  * 데이터를 일관성 있게 변경하는 여러 DML 문
  * 하나의 DDL 문 (DDL은 auto commit)
  * 하나의 DCL(데이터 제어어) 문 (DCL은 auto commit)
    > **DML과 DDL은 섞어서 쓰면 안됨!**
### 트랜잭션 시작과 종료
* 트랜잭션이 열리는 구문을 넣어야 시작되는 DB도 있지만 ORACLE은 **첫번째 DML SQL 문이 실행될 때 시작된다.**
* 종료
  * **COMMIT 또는 ROLLBACK 문 실행**
  * DDL 또는 DCL 문 실행(자동 커밋)
  * 유저가 SQL Developer 또는 SQL\*Plus를 종료
  * 시스템 중단

## 실습
* 하나의 DB에서 서로 다른 유저의 작업에 대한 결과를 확인해본다.
### 사전 준비
1. 비공유 SQL 워크시트를 새로 연다. (Ctrl + Shift + N)
2. 창을 나눠서 준비한다. (앞으로 편하게 A유저와 B유저라고 표현)
  ![image](https://user-images.githubusercontent.com/79209568/115098430-16563e80-9f6b-11eb-939e-db6c0dea2138.png)

### 읽기 일관성 확인해보기
* **A에서 UPDATE문을 실행한다.**
  * A
    ```sql
    UPDATE EMP
    SET SAL = 9000
    WHERE DEPTNO = 10;

    SELECT EMPNO, ENAME, SAL, DEPTNO FROM EMP WHERE DEPTNO = 10 ;
    ```
    > ![image](https://user-images.githubusercontent.com/79209568/115098492-50274500-9f6b-11eb-93fd-b6faa6c58cb6.png)

  * B
    ```sql
    SELECT EMPNO, ENAME, SAL, DEPTNO FROM EMP WHERE DEPTNO = 10 ;
    ```
    > ![image](https://user-images.githubusercontent.com/79209568/115098573-b4e29f80-9f6b-11eb-940f-99e3c765595d.png)

  ##### A의 UPDATE된 내용을 B가 확인할 수 없다.

* **해당 상태에서 B에서 UPDATE문을 실행**
  * B
    ```SQL
    UPDATE EMP SET SAL = 7000 WHERE EMPNO = 7839;
    ```
    > ![image](https://user-images.githubusercontent.com/79209568/115098762-c4161d00-9f6c-11eb-85c5-d2399924a06d.png)

  * 현재 A에서 UPDATE 작업을 COMMIT으로 끝내지 않았기 때문에 DB에 LOCK이 걸려 있다. 따라서 B에서 실행한 UPDATE 작업은 LOCK이 풀릴 때까지 대기 상태로 있는다. 
  ```SQL
  1. Block(Data, Undo)확보
  2. Lock 생성        -- 현재 A가 정의 중
  3. Redo Log(복구용 데이터) 생성
  4. Update 
  ```
* **A에서 ROLLBACK을 진행**
  * A
    ```SQL
    ROLLBACK;
    ```
  ##### A의 값이 UPDATE전으로 돌아가고 대기 중이던 B의 UPDATE가 진행된다. (이제는 Lock을 B가 정의 중)
  
### SELECT문의 FOR UPDATE 확인해보기
* A
  ```SQL
  SELECT ENAME, SAL FROM EMP WHERE DEPTNO=20 FOR UPDATE;
  ```
* B
  ```SQL
  UPDATE EMP 
  SET SAL = 5000 
  WHERE EMPNO = 7839 ; -- 상관없음

  UPDATE EMP 
  SET SAL = 5000 
  WHERE EMPNO = 7788 ; -- LOCK 걸림
  ```
  ##### SELECT문에 FOR UPDATE를 붙여놓으면 테이블의 해당 컬럼들에 LOCK이 걸린다.
  ##### JOIN을 실행할 때 FOR UPDATE를 붙이면 두 테이블 모두에 LOCK이 걸린다. 특정 테이블에만 LOCK을 걸고 싶으면 `FOR UPDATE OF column_name`으로 작성한다.
    ```sql
    SELECT *
    FROM EMP E, DEPT D 
    WHERE E.DEPTNO = D.DEPTNO 
    FOR UPDATE OF E.EMPNO ;
    ```
## 정리
### COMMIT 또는 ROLLBACK 이전의 데이터 상태
* 이전의 데이터 상태를 복구할 수 있다.
* 현재 유저는 SELECT 문을 사용하여 DML 작업의 결과를 확인할 수 있다.
* 다른 유저는 현재 유저가 실행한 DML 문의 결과를 볼 수 없다.
* 영향을 받는 행이 잠기므로 다른 유저가 영향을 받는 행의 데이터를 변경할 수 없다.

### COMMIT 후의 데이터 상태
* 데이터 변경 사항이 데이터베이스에 저장된다.
* 이전의 데이터 상태를 겹쳐쓴다.
* 모든 유저가 결과를 확인할 수 있다.
* 영향을 받는 행의 잠금이 해제되어 이러한 행을 다른 유저가 조작할 수 있다.
* 모든 저장점이 지워진다.

### ROLLBACK 후의 데이터 상태
* 데이터 변경 사항이 언두된다.
* 이전의 데이터 상태가 복원된다.
* 영향 받는 행의 잠금이 해제된다.
