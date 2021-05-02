# Database Resource Manager 사용
## Consumer Group 생성 및 유저 추가
```SQL
BEGIN
    DBMS_RESOURCE_MANAGER.CLEAR_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.CREATE_PENDING_AREA(); --보류영역을 생성
    DBMS_RESOURCE_MANAGER.CREATE_CONSUMER_GROUP(
        CONSUMER_GROUP => 'OLTP_GROUP',
        COMMENT => '',
        CPU_MTH => 'ROUND-ROBIN');
    DBMS_RESOURCE_MANAGER.SUBMIT_PENDING_AREA();
END;
/

BEGIN
    DBMS_RESOURCE_MANAGER_PRIVS.GRANT_SWITCH_CONSUMER_GROUP(
        GRANTEE_NAME => 'HR', --db 유저를 포함시킴
        CONSUMER_GROUP => 'OLTP_GROUP', 
        GRANT_OPTION => FALSE);
END;
/
```
## 계획 생성
```SQL
BEGIN
    DBMS_RESOURCE_MANAGER.CLEAR_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.CREATE_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.CREATE_PLAN( -- 어떻게 소비하게 할 것인지 플랜을 만듦
        PLAN => 'DAYTIME',
        COMMENT => '',
        MGMT_MTH => 'EMPHASIS');
    DBMS_RESOURCE_MANAGER.CREATE_PLAN_DIRECTIVE(
        PLAN => 'DAYTIME',
        GROUP_OR_SUBPLAN => 'OTHER_GROUPS',
        COMMENT => '');

    DBMS_RESOURCE_MANAGER.CREATE_PLAN_DIRECTIVE(
        PLAN => 'DAYTIME',
        GROUP_OR_SUBPLAN => 'SYS_GROUP',
        MGMT_P1 => 100); --sys유저에게 자원 100% 먼저 줌

    DBMS_RESOURCE_MANAGER.CREATE_PLAN_DIRECTIVE(
        PLAN => 'DAYTIME',
        GROUP_OR_SUBPLAN => 'OLTP_GROUP',
        MGMT_P1 => 0,
        MGMT_P2 => 80, -- sys에게 주고 남은 것의 80%를 OLTP에게
        ACTIVE_SESS_POOL_P1 => 10,
        QUEUEING_P1 => 3,
        PARALLEL_DEGREE_LIMIT_P1 => 10);

    DBMS_RESOURCE_MANAGER.CREATE_PLAN_DIRECTIVE(
        PLAN => 'DAYTIME',
        GROUP_OR_SUBPLAN => 'DSS_GROUP',
        MGMT_P1 => 0,
        MGMT_P2 => 20 ); -- sys에게 주고 남은 것의 20%를 DSS에게

    DBMS_RESOURCE_MANAGER.SUBMIT_PENDING_AREA();
END;
/
```
## 실행
```SQL
SHOW PARAMETER resource_manager_plan 

-- ALTER SYSTEM SET RESOURCE_MANAGER_PLAN = 'DAYTIME' ; 
-- ALTER SYSTEM SET RESOURCE_MANAGER_PLAN = '' ; 
```
## 삭제 
```SQL
BEGIN
    DBMS_RESOURCE_MANAGER.CLEAR_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.CREATE_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.DELETE_PLAN_CASCADE(PLAN => 'DAYTIME');
    DBMS_RESOURCE_MANAGER.SUBMIT_PENDING_AREA();
END;
/
```



#### 전체 
```
---------------------------------------------------------------------------
17. 데이터베이스 유지 관리 

-- 테이블 스페이스 생성
CREATE TABLESPACE NOASSM
DATAFILE 'D:\app\user\oradata\orcl\noassm.dbf' SIZE 100M AUTOEXTEND ON 
EXTENT MANAGEMENT LOCAL UNIFORM SIZE 128K 
SEGMENT SPACE MANAGEMENT MANUAL ; 

-- 테이블 생성
CREATE TABLE noassm_t
(id	NUMBER, 
 name	CHAR(100))
TABLESPACE noassm ; 

-- 스냅샷 수동으로 찍기
execute dbms_workload_repository.create_snapshot

-- 인스턴스 뷰어 띄우고,
-- 테이블에 insert를 1초에 한 번씩 10000번 진행하는 작업을 하는 job 프로세스 10개가 동시에 30초에 한 번씩 실행되려고 시도한다.
BEGIN
  FOR i IN 1..20 LOOP 
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
      end_date        => systimestamp + 60/86400 ,
      auto_drop       => TRUE,
      enabled         => TRUE);
  END LOOP ; 
END ;
/
 

-- 1분 대기 

execute dbms_workload_repository.create_snapshot

@%ORACLE_HOME%\rdbms\admin\addmrpt

-- 테이블 스페이스 삭제
DROP TABLESPACE noassm INCLUDING CONTENTS AND DATAFILES ; 

-- assm을 사용하는 테이블 스페이스 생성
CREATE TABLESPACE ASSM
DATAFILE 'D:\app\user\oradata\orcl\assm.dbf' SIZE 100M AUTOEXTEND ON 
EXTENT MANAGEMENT LOCAL UNIFORM SIZE 128K 
SEGMENT SPACE MANAGEMENT AUTO ; 

-- 테이블 생성
CREATE TABLE assm_t
(id	NUMBER, 
 name	CHAR(100))
TABLESPACE assm ; 

-- 스냅샷 찍기
execute dbms_workload_repository.create_snapshot

-- 다시 경합
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

! sleep 60

execute dbms_workload_repository.create_snapshot

@%ORACLE_HOME%\rdbms\admin\addmrpt


DROP TABLESPACE assm   INCLUDING CONTENTS AND DATAFILES ; 

SELECT * FROM V$SYSTEM_EVENT; --

SELECT * FROM V$EVENT_NAME; --수집 가능한 

SHOW PARAMETER PGA;
-- pga_aggregate_limit : pga공간을 2G로 막음
-- pga_aggregate_target : pga 공간


---------------------------------------------------------------------------
20. Resource Manager 사용

Consumer Group 생성 및 유저 추가

BEGIN
    DBMS_RESOURCE_MANAGER.CLEAR_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.CREATE_PENDING_AREA(); --보류영역을 생성
    DBMS_RESOURCE_MANAGER.CREATE_CONSUMER_GROUP(
        CONSUMER_GROUP => 'OLTP_GROUP',
        COMMENT => '',
        CPU_MTH => 'ROUND-ROBIN');
    DBMS_RESOURCE_MANAGER.SUBMIT_PENDING_AREA();
END;
/

BEGIN
    DBMS_RESOURCE_MANAGER_PRIVS.GRANT_SWITCH_CONSUMER_GROUP(
        GRANTEE_NAME => 'HR', --db 유저를 포함시킴
        CONSUMER_GROUP => 'OLTP_GROUP', 
        GRANT_OPTION => FALSE);
END;
/

계획 생성

BEGIN
    DBMS_RESOURCE_MANAGER.CLEAR_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.CREATE_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.CREATE_PLAN( -- 어떻게 소비하게 할 것인지 플랜을 만듦
        PLAN => 'DAYTIME',
        COMMENT => '',
        MGMT_MTH => 'EMPHASIS');
    DBMS_RESOURCE_MANAGER.CREATE_PLAN_DIRECTIVE(
        PLAN => 'DAYTIME',
        GROUP_OR_SUBPLAN => 'OTHER_GROUPS',
        COMMENT => '');

    DBMS_RESOURCE_MANAGER.CREATE_PLAN_DIRECTIVE(
        PLAN => 'DAYTIME',
        GROUP_OR_SUBPLAN => 'SYS_GROUP',
        MGMT_P1 => 100); --sys유저에게 자원 100% 먼저 줌

    DBMS_RESOURCE_MANAGER.CREATE_PLAN_DIRECTIVE(
        PLAN => 'DAYTIME',
        GROUP_OR_SUBPLAN => 'OLTP_GROUP',
        MGMT_P1 => 0,
        MGMT_P2 => 80, -- sys에게 주고 남은 것의 80%를 OLTP에게
        ACTIVE_SESS_POOL_P1 => 10,
        QUEUEING_P1 => 3,
        PARALLEL_DEGREE_LIMIT_P1 => 10);

    DBMS_RESOURCE_MANAGER.CREATE_PLAN_DIRECTIVE(
        PLAN => 'DAYTIME',
        GROUP_OR_SUBPLAN => 'DSS_GROUP',
        MGMT_P1 => 0,
        MGMT_P2 => 20 ); -- sys에게 주고 남은 것의 20%를 DSS에게

    DBMS_RESOURCE_MANAGER.SUBMIT_PENDING_AREA();
END;
/

SHOW PARAMETER resource_manager_plan 

-- ALTER SYSTEM SET RESOURCE_MANAGER_PLAN = 'DAYTIME' ; 
-- ALTER SYSTEM SET RESOURCE_MANAGER_PLAN = '' ; 

삭제 

BEGIN
    DBMS_RESOURCE_MANAGER.CLEAR_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.CREATE_PENDING_AREA();
    DBMS_RESOURCE_MANAGER.DELETE_PLAN_CASCADE(PLAN => 'DAYTIME');
    DBMS_RESOURCE_MANAGER.SUBMIT_PENDING_AREA();
END;
/


------------------------------------------------------------------------

Uninstall

Microsoft Windows [Version 10.0.19042.928]
(c) Microsoft Corporation. All rights reserved.

C:\WINDOWS\system32> d:
D:\> cd D:\app\oracle\product\12.1.0\dbhome_1\deinstall
...
구성을 해제할 단일 인스턴스 리스너를 모두 지정하십시오. 모두 선택 해제하려면 .(마침표)를 입력하십시오. [L1,LISTENER]:
...
이 Oracle 홈에 구성된 데이터베이스 이름 목록을 지정하십시오. [ORCL]:
...
ORCL 데이터베이스의 세부 정보가 자동으로 검색되었습니다. ORCL 데이터베이스의 세부 정보를 수정하겠습니까? [n]:
...
계속하겠습니까(y - 예, n - 아니오)? [n]: y
```
