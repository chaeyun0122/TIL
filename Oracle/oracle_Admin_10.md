```
SELECT * FROM V$SESSION;

--11g
AUDIT select on scott.emp ; 
AUDIT CREATE TABLE ; 
AUDIT CREATE SESSION ;
/* 
- 명령어마다 다 AUDIT 명령어으로 수동 작업이 필요하다.
- 비활성화는 NOAUDIT 명령어를 사용한다.
*/

SELECT username, timestamp, owner, obj_name, action_name, sql_text, sql_bind
FROM dba_audit_trail ;

TRUNCATE TABLE aud$ ;

SELECT username, timestamp, owner, obj_name, action_name, sql_text, sql_bind
FROM dba_audit_trail ;

----------------------------------------------------------------------------------------------
# New Terminal 
CONN system/oracle_4U

CREATE TABLE t (id NUMBER) ;

DROP TABLE t PURGE ;

SELECT empno, sal
FROM scott.emp ;
----------------------------------------------------------------------------------------------

SELECT username, timestamp, owner, obj_name, action_name, sql_text, sql_bind
FROM dba_audit_trail ;

# Unified Auditing
https://docs.oracle.com/database/121/TDPSG/GUID-BF747771-01D1-4BFB-8489-08988E1181F6.htm#TDPSG50528

------------------------------------------------------------------------------------
DELETE SCOTT.EMP ;
COMMIT;

SELECT * FROM SCOTT.EMP AS OF TIMESTAMP (SYSDATE - 5/1440) ;
-- 언두 데이터를 통해 과거 시점의 데이터를 가져올 수 있다.
-- 언두 데이터가 사라지면 복구하지 못한다. (길어야 1시간 이내)
------------------------------------------------------------------------------------
13장. 백업 및 복구 구성

# 준비 
mkdir D:\app\user\DISK1
mkdir D:\app\user\DISK2
mkdir D:\app\user\FRA
mkdir D:\app\user\BACKUP

# controlfile 다중화
sqlplus / as sysdba 
show parameter control_files

alter system 
set control_files = 'D:\app\user\DISK1\control01.ctl','D:\app\user\DISK2\control02.ctl' scope=spfile;

shutdown immediate

host copy D:\app\user\oradata\orcl\control01.ctl D:\app\user\DISK1\control01.ctl
host copy D:\app\user\oradata\orcl\control01.ctl D:\app\user\DISK2\control02.ctl
host del D:\app\user\oradata\orcl\control01.ctl
host del D:\app\user\fast_recovery_area\orcl\control02.ctl

startup 

show parameter control_files
select * from v$controlfile ; 

# Redo Log file 다중화 

select group#, sequence#, status, archived, members, bytes/1024/1024 size_mb
from v$log; 
save log replace
-- v$log : 리두로그 그룹의 상태
-- save log replace : log.sql로 저장하고 @log로 실행할 수 있다.

select group#, member, status 
from v$logfile
order by group# ;
save logfile replace
-- v$logfile : 멤버 파일의 형태를 꺼내 볼 수 있음
-- status : 아무것도 보이지 않을 때 정상

-- 6개의 멤버 파일을 각각의 그룹당 2개씩 추가 작업을 진행한다.
alter database add logfile member 
'D:\app\user\DISK1\redo01a.log' to group 1,
'D:\app\user\DISK1\redo02a.log' to group 2,
'D:\app\user\DISK1\redo03a.log' to group 3,
'D:\app\user\DISK2\redo01b.log' to group 1,
'D:\app\user\DISK2\redo02b.log' to group 2,
'D:\app\user\DISK2\redo03b.log' to group 3 ;

@log
@logfile 

-- 사용하려면 로그스위치를 사용해야한다.
alter system switch logfile ;
alter system switch logfile ;
alter system switch logfile ;
@log
@logfile 

-- 멤버가 아니라 그룹을 추가하고 싶을 경우,
alter database add logfile group 4 
'D:\app\user\DISK1\redo04a.log' size 5m ;

alter database drop logfile group 4;

@log

-- 현재 쓰고 있는 것은 (current 상태) 삭제에 오류가 난다.
alter database drop logfile member 'D:\app\user\oradata\orcl\redo01.log' ;
alter database drop logfile member 'D:\app\user\oradata\orcl\redo02.log' ;
alter database drop logfile member 'D:\app\user\oradata\orcl\redo03.log' ;

alter system switch logfile ;
alter database drop logfile member 'D:\app\user\oradata\orcl\redo03.log' ;

@log
@logfile

# Archivelog mode 

show parameter archive


show parameter db_recovery
-- 방금 만들었던 경로로 만들어줌
alter system set db_recovery_file_dest = 'D:\app\user\FRA' ; 

shutdown immediate
startup mount
alter database archivelog ; 
alter database open ; 

-- 아카이브 로그인지 아닌지를 확인할 수 있음
archive log list

alter system switch logfile ;

select * 
from v$archived_log ;
----------------------------------------------------------
14장. 데이터베이스 백업 수행

-- 그냥 rman이라고 써도 됨
C:\WINDOWS\system32> rman target /

SHOW ALL ; -- 현재 환경 설정 확인
CONFIGURE CONTROLFILE AUTOBACKUP ON ;
CONFIGURE BACKUP OPTIMIZATION ON ; 
CONFIGURE RETENTION POLICY TO RECOVERY WINDOW OF 7 DAYS ;

REPORT SCHEMA ;
REPORT NEED BACKUP ; 

LIST BACKUP ;
LIST COPY ; --이미지 카피 

BACKUP DATAFILE 1 ; -- 특정 파일만
BACKUP TABLESPACE users ; -- 특정 테이블 스페이스만
BACKUP DATABASE PLUS ARCHIVELOG ; -- 전체 백업
-- 따로 경로를 지정하지 않으면 자동으로 FRA 폴더를 씀


BACKUP AS COPY TABLESPACE USERS ; -- db 전체를 이미지 카피로 백업

BACKUP INCREMENTAL LEVEL 0 DATABASE ; -- 풀백업
BACKUP INCREMENTAL LEVEL 1 DATABASE ; -- D1
BACKUP INCREMENTAL LEVEL 1 CUMULATIVE DATABASE; -- C1

-- 특정 경로에 백업 지정 format뒤에 경로를 지정/ tag를 붙여서 어떤 백업인지 확인하기 쉬움
BACKUP TABLESPACE users FORMAT 'D:\app\user\BACKUP\%d_%s_%p.bak' TAG=TBS_BACKUP ;

-- 백업을 진행 했기 때문에 이제 나옴
LIST BACKUP ;
LIST COPY ;
LIST BACKUP SUMMARY ; -- 간단한 요약본 확인

-- 보존 정책상 불필요한 것이 무엇인지 확인
REPORT OBSOLETE ;
-- 1로 바꾸면 마지막에 했던것만 보존 대상이고 이전은 삭제 대상임
CONFIGURE RETENTION POLICY TO REDUNDANCY 1 ;
REPORT OBSOLETE ;

-- 삭제 대상을 지워줌
DELETE OBSOLETE ; 
DELETE NOPROMPT OBSOLETE ; -- 예/아니오를 묻지 않음

-- 모든 백업들을 삭제
DELETE NOPROMPT BACKUP ; 
DELETE NOPROMPT COPY ; 

-- 복구 실습을 위한 백업 작업
BACKUP DATABASE PLUS ARCHIVELOG ;

-- 백업 파일들을 체킹해주는 명령어
CROSSCHECK BACKUP ; 
CROSSCHECK COPY ; 

-- 다 끝나고 나오기
EXIT 
---------------------------------------------------------------
15장. 데이터베이스 복구 수행

sqlplus / as sysdba

ALTER TABLESPACE users OFFLINE ; -- 뒤에 아무것도 안쓰면 normal이 기본값
ALTER TABLESPACE users ONLINE ; 

ALTER TABLESPACE users OFFLINE IMMEDIATE ; -- 변경된 사항이 메모리에 떠있다고 하더라고 동기화 작업을 시키지 않고 오프라인으로 바꿈
-- 온라인으로 바로 안됨 복구를 해야 온라인으로 변경 가능함
ALTER TABLESPACE users ONLINE ; 

SELECT * FROM scott.emp ; -- 에러
ORA-00376: 현재 파일 6를 읽을 수 없습니다
ORA-01110: 6 데이터 파일: 'D:\APP\ORACLE\ORADATA\ORCL\USERS01.DBF'

exit 

rman target /
rman target ' " / as sysbackup" ' --백업과 관련된 유저

LIST FAILURE ; -- 어떠한 장애사항이 있는지 보여줌
ADVISE FAILURE ; -- rman을 이용하고 있을 때 복구 방법을 알려줌
/* 
해당 주소를 복사해서 메모장 > 열기 > 붙여넣기 후 확인
거기에 있는 명령 붙여넣기 하면 복구 됨
*/
REPAIR FAILURE ; --메모장에 있던 내용 그대로 나옴

-- 이제 잘 됨
ALTER TABLESPACE users ONLINE ;
SELECT * FROM scott.emp ; 

exit 

-- 최고 권한자로 다시 들어감
sqlplus / as sysdba

-- 오프라인으로 만들 수 없음
ALTER TABLESPACE SYSTEM OFFLINE IMMEDIATE ;

-- 정상적으로 종료되지 않고 인스턴스가 그냥 내려감
SHUTDOWN ABORT 

host del D:\app\user\oradata\orcl\SYSTEM01.DBF

-- 파일이 내려갔기 때문에 오픈 됐다는 명령어는 볼 수 없음
-- 마운트 까지만 올라가있는 상태에서 rman으로 들어감
STARTUP
exit 

rman target /

LIST FAILURE ;
ADVISE FAILURE ;
-- REPAIR FAILURE ; 

RESTORE TABLESPACE SYSTEM ; 
RECOVER TABLESPACE SYSTEM ; 

ALTER DATABASE OPEN ; 
exit 

-------------------------------------------------------------
16장. 데이터 이동

C:\WINDOWS\system32> cd c:\adsql
sqlplus / as sysdba

-- 사용자 생성
create user test_user identified by oracle_4U 
default tablespace users 
quota 10m on users; 
grant resource, connect to test_user; 

create or replace directory data_dir as 'C:\adsql' ; -- db안에서 서버딴에서 접근할 수 있는 경로
grant read, write on directory data_dir to test_user ; 

exit

-- expdp : db에 원하는 단위별로 데이터를 밖으로 뽑는 작업
expdp -help --사용 방법

expdp system/oracle_4U directory=data_dir dumpfile=empndept.dmp tables=scott.emp,scott.dept --테이블 단위
expdp system/oracle_4U directory=data_dir dumpfile=scott.dmp schemas=scott --스키마 단위
expdp system/oracle_4U directory=data_dir dumpfile=users_tbs.dmp tablespaces=users --테이블 스페이스 단위

notepad expdp_sh.par
--------------------------------------------------
userid=system/oracle_4U
directory=data_dir
job_name=expdp_sh
logfile=expdp_sh.log
dumpfile=expdp_sh%U.dmp
compression=all --압축
filesize=10m  --파일의 크기
SCHEMAS=SH  --스키마 지정
EXCLUDE=TABLE:"LIKE 'C%'"  --c로 시작하는 것은 빼고 가져오도록 설정
--------------------------------------------------
-- 속성을 써놓은 파일로도 expdp 가능
expdp parfile=expdp_sh.par

-- 덤프파일 db에 넣기 (scott이 아니라 test_user에 임포트 됨)
impdp system/oracle_4U directory=data_dir dumpfile=empndept.dmp remap_schema=scott:test_user

sqlplus test_user/oracle_4U
select * from emp ; 
exit 

# SQL Loader 

sqlplus test_user/oracle_4U
truncate table emp ;
exit 

notepad emp.dat
------------------------------------------------------
7839,KING,PRESIDENT,,1981-11-17,5000,,10
7698,BLAKE,ABCDEFGHIJKLMN,7839,1981-05-01,2850,,30
7782,CLARK,MANAGER,7839,1981-06-09,2450,,10
7566,JONES,MANAGER,7839,1981-04-02,2975,,20
7654,MARTIN,SALESMAN,7698,1981-09-28,1250,1400,30
7499,ALLEN,SALESMAN,7698,1981-02-20,1600,300,30
7844,TURNER,SALESMAN,7698,1981-09-08,1500,0,30
7900,JAMES,CLERK,7698,1981-12-03,950,,30
7521,WARD,SALESMAN,7698,1981-02-22,1250,500,30
7902,FORD,ANALYST,7566,1981-12-03,3000,,20
7369,SMITH,CLERK,7902,1980-12-17,800,,20
7788,SCOTT,ANALYST,7566,1982-12-09,3000,,20
7876,ADAMS,CLERK,7788,1983-01-12,1100,,20
7934,MILLER,CLERK,7782,1982-01-23,1300,,10
------------------------------------------------------

-- 어디에 임포트 할 것인지 컨트롤 파일
notepad emp.ctl
------------------------------------------------------
LOAD DATA
   INFILE 'emp.dat' 
   APPEND INTO TABLE emp
     FIELDS TERMINATED BY ',' 
   TRAILING NULLCOLS
   (empno INTEGER EXTERNAL,
    ename CHAR,
    job   CHAR,
    mgr   INTEGER EXTERNAL ,
    hiredate DATE "YYYY-MM-DD",
    sal   INTEGER EXTERNAL,
    comm  INTEGER EXTERNAL ,
    deptno INTEGER EXTERNAL)
------------------------------------------------------

-- sqlldr로 넣을 수 있음
sqlldr test_user/oracle_4U control=emp.ctl log=emp.log bad=emp.bad direct=n

sqlplus test_user/oracle_4U

DROP TABLE emp_ext ; 
CREATE TABLE emp_ext
(empno      NUMBER(4),
 ename      VARCHAR2(10),
 job        VARCHAR2(9),
 mgr        NUMBER(4),
 hiredate   DATE,
 sal        NUMBER(7,2),
 comm       NUMBER(7,2),
 deptno     NUMBER(2))
ORGANIZATION external --데이터를 db 밖의 플랫폼에 저장
( TYPE oracle_loader
  DEFAULT DIRECTORY DATA_DIR
  ACCESS PARAMETERS
  ( RECORDS DELIMITED BY NEWLINE
    BADFILE 'emp_ext.bad'
    LOGFILE 'emp_ext.log'
    FIELDS TERMINATED BY ','
    ( EMPNO      CHAR(4),
      ENAME      CHAR(10),
      JOB        CHAR(9),
      MGR        CHAR(4),
      HIREDATE   CHAR(10) DATE_FORMAT DATE MASK "YYYY-MM-DD",
      SAL        CHAR(7),
      COMM       CHAR(7),
      DEPTNO     CHAR(2)
))
  location ('emp.dat')
)REJECT LIMIT UNLIMITED ;
-- 딕셔너리 정보만 만들어서 db 밖의 테이블을 읽어옴
select * from emp_ext ;

exit 
```
