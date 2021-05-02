# 데이터베이스 유지 관리
```SQL
SELECT * FROM V$SYSSTAT; --시스템이 오픈 된 이후의 세션들의 작업 전체 누적 값
SELECT * FROM V$SESSTAT; -- 각각의 세션들이(SID) 로그인을 한 후 사용한 자원들
SELECT * FROM V$STATNAME; -- 세션 아이디들의 이름들
-- 하지만 이 누적된 값의 평균을 가지고 DB가 정상이다 라고 판단할 수 없다.
-- 주기적(10~15분)으로 조회를 해야지만 DB가 어떤 것을 하고 언제 IO가 몰리고 어떤 문제가 있는지 알 수 있다.
```
* SQL DEVELOPER의 AWR 스냅샷 확인
  ![image](https://user-images.githubusercontent.com/79209568/116799069-c6fa3b80-ab30-11eb-8932-d57d94259245.png)

## 실습
### 스냅샷 확인
* 테이블 스페이스 생성
  ```sql
  CREATE TABLESPACE NOASSM
  DATAFILE 'D:\app\user\oradata\orcl\noassm.dbf' SIZE 100M AUTOEXTEND ON 
  EXTENT MANAGEMENT LOCAL UNIFORM SIZE 128K 
  SEGMENT SPACE MANAGEMENT MANUAL ; 
  ```
* 테이블 생성
  ```sql
  CREATE TABLE noassm_t
  (id	NUMBER, 
   name	CHAR(100))
  TABLESPACE noassm ; 
  ```

* 스냅샨 수동으로 찍기
  ```sql
  execute dbms_workload_repository.create_snapshot
  ```  
  
> * 마지막 번호 : 128  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116799487-bb107880-ab34-11eb-8648-53fa8fd88689.png)
> * 스냅샷 몇 번 찍은 후 마지막 번호 : 135  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/116799505-e6936300-ab34-11eb-9bef-aa58090cfe4c.png)

### 경합 발생 후 스냅샷
* 인스턴스 뷰어 띄우기
  ![image](https://user-images.githubusercontent.com/79209568/116799530-0a56a900-ab35-11eb-8335-1dc14d73efc0.png)

* 테이블에 insert를 1초에 한 번씩 10000번 진행하는 작업을 하는 job 프로세스 10개가 동시에 30초에 한 번씩 실행되려고 시도한다.
  ```sql
  BEGIN
    FOR i IN 1..10 LOOP 
      dbms_scheduler.create_job(
        job_name => 'NOASSM'||i,
        job_type => 'PLSQL_BLOCK',
        job_action => 'BEGIN
                          FOR i IN 1..10000 LOOP 
                            INSERT INTO noassm_t VALUES (i, ''ABC'') ; 
                            COMMIT ;
                          END LOOP ; 
                       END;',
        repeat_interval => 'FREQ=SECONDLY;INTERVAL=1',
        start_date      => systimestamp,
        end_date        => systimestamp + 30/86400 ,
        auto_drop       => TRUE,
        enabled         => TRUE);
    END LOOP ; 
  END ;
  /
  ```
* 인스턴스 뷰어 확인
  ![image](https://user-images.githubusercontent.com/79209568/116799769-59e9a480-ab36-11eb-802e-9de9dd423929.png)

* 스냅샷 찍기
  * `ADDM Findings`에 Yes라고 찍혀있음
  ![image](https://user-images.githubusercontent.com/79209568/116800350-4bea5280-ab3b-11eb-866c-7b75f1de4e66.png)

* ADDM 확인
  * `자동 데이터베이스 진단 모니터`에서 스냅샷 아이디와 동일한 것을 더블 클릭
  ![image](https://user-images.githubusercontent.com/79209568/116800261-8b646f00-ab3a-11eb-9b7e-5836794662bb.png)
  * ADDM 보고서 탭 클릭
  ![image](https://user-images.githubusercontent.com/79209568/116800372-79cf9700-ab3b-11eb-925f-274aa9185ef9.png)
  * ![image](https://user-images.githubusercontent.com/79209568/116800408-b56a6100-ab3b-11eb-82fb-2ecb433953eb.png)
  * ![image](https://user-images.githubusercontent.com/79209568/116800410-b9967e80-ab3b-11eb-80e8-cfd3ad5eaf5d.png)
  * ![image](https://user-images.githubusercontent.com/79209568/116800412-bdc29c00-ab3b-11eb-928d-cb989d7a096d.png)

* 현재 NoASSM 테이블 스페이스 삭제
  ```sql
  DROP TABLESPACE noassm INCLUDING CONTENTS AND DATAFILES ; 
  ```
* ASSAM을 사용하는 테이블 스페이스 생성
  ```sql
  CREATE TABLESPACE ASSM
  DATAFILE 'D:\app\user\oradata\orcl\assm.dbf' SIZE 100M AUTOEXTEND ON 
  EXTENT MANAGEMENT LOCAL UNIFORM SIZE 128K 
  SEGMENT SPACE MANAGEMENT AUTO ; 
  ```
* 테이블 생성
  ```sql
  CREATE TABLE assm_t
  (id	NUMBER, 
   name	CHAR(100))
  TABLESPACE assm ; 
  ```
* 다시 경합 발생
  ```sql
  BEGIN
    FOR i IN 1..20 LOOP 
      dbms_scheduler.create_job(
        job_name => 'NOASSM'||i,
        job_type => 'PLSQL_BLOCK',
        job_action => 'BEGIN
                          FOR i IN 1..10000 LOOP 
                            INSERT INTO assm_t VALUES (i, ''ABC'') ; 
                            COMMIT ;
                          END LOOP ; 
                       END;',
        repeat_interval => 'FREQ=SECONDLY;INTERVAL=1',
        start_date      => systimestamp,
        end_date        => systimestamp + 60/86400 ,
        auto_drop       => TRUE,
        enabled         => TRUE);
    END LOOP ; 
  END ;
  /
  ```
* 인스턴스 뷰어 확인
  ![image](https://user-images.githubusercontent.com/79209568/116800105-0167d680-ab39-11eb-98c9-ad892c2114ab.png)

* 스냅샷 찍기

* ASSAM 사용 전 후 인스턴스 뷰어 경합 비교  
  * 전  
  ![image](https://user-images.githubusercontent.com/79209568/116800232-3b85a800-ab3a-11eb-8011-f0ed17780c7f.png)
  * 후  
  ![image](https://user-images.githubusercontent.com/79209568/116800227-227cf700-ab3a-11eb-90ad-1dd2a745c4cd.png)

