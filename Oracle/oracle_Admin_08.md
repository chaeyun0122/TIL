# 백업 및 리커버리 : 개념
## User Error
```SQL
DELETE SCOTT.EMP ;
COMMIT;

SELECT * FROM SCOTT.EMP AS OF TIMESTAMP (SYSDATE - 5/1440) ;
-- 언두 데이터를 통해 과거 시점의 데이터를 가져올 수 있다.
-- 언두 데이터가 사라지면 복구하지 못한다. (길어야 1시간 이내)

INSERT INTO SCOTT.EMP 
SELECT * FROM SCOTT.EMP AS OF TIMESTAMP (SYSDATE - 5/1440) ;

COMMIT ;
```

# 백업 및 리커버리 : 구성

## 준비
```
mkdir D:\app\user\DISK1
mkdir D:\app\user\DISK2
mkdir D:\app\user\FRA
mkdir D:\app\user\BACKUP
```
![image](https://user-images.githubusercontent.com/79209568/116771652-3a943e00-aa88-11eb-824e-645502dedc76.png)

## control file 다중화
* 관리자 명령 프롬프트로 진행
* sqlplus로 로그인
```
sqlplus / as sysdba 
```
![image](https://user-images.githubusercontent.com/79209568/116772236-6dd6cd00-aa88-11eb-8271-ecbc0518aae1.png)

* 컨트롤 파일의 경로 설정
```sql
show parameter control_files

alter system 
set control_files = 'D:\app\user\DISK1\control01.ctl','D:\app\user\DISK2\control02.ctl' scope=spfile;
```
![image](https://user-images.githubusercontent.com/79209568/116772270-c312de80-aa88-11eb-987b-44295d30eda6.png)

* 데이터베이스를 닫고나서 진행해줘야 한다.
* 만들어놓은 경로로 컨트롤 파일을 복사해준다.
* 그 후 데이테베이스를 열어준다.
```sql
shutdown immediate

host copy D:\app\user\oradata\orcl\control01.ctl D:\app\user\DISK1\control01.ctl
host copy D:\app\user\oradata\orcl\control01.ctl D:\app\user\DISK2\control02.ctl
host del D:\app\user\oradata\orcl\control01.ctl
host del D:\app\user\fast_recovery_area\orcl\control02.ctl

startup 
```
![image](https://user-images.githubusercontent.com/79209568/116772350-5a783180-aa89-11eb-9b1e-2311d919a3c0.png)

* 확인
```sql
show parameter control_files
```
![image](https://user-images.githubusercontent.com/79209568/116772370-7f6ca480-aa89-11eb-8074-fecf9c8ae28c.png)

## Redo Log file 다중화
* v$log : 리두로그 그룹의 상태
* save log replace : log.sql로 저장하고 @log로 실행할 수 있다.
```
select group#, sequence#, status, archived, members, bytes/1024/1024 size_mb
from v$log; 
save log replace
```
![image](https://user-images.githubusercontent.com/79209568/116772394-b642ba80-aa89-11eb-87ec-19d7e092f54c.png)

* v$logfile : 멤버 파일의 형태를 꺼내 볼 수 있음
* status : 결과 화면에 아무것도 보이지 않을 때 정상
```sql
select group#, member, status 
from v$logfile
order by group# ;
save logfile replace
```
![image](https://user-images.githubusercontent.com/79209568/116773012-d2485b00-aa8d-11eb-8643-93d2dea4fc05.png)

* 6개의 멤버 파일을 각각의 그룹당 2개씩 추가 작업을 진행한다.
```sql
alter database add logfile member 
'D:\app\user\DISK1\redo01a.log' to group 1,
'D:\app\user\DISK1\redo02a.log' to group 2,
'D:\app\user\DISK1\redo03a.log' to group 3,
'D:\app\user\DISK2\redo01b.log' to group 1,
'D:\app\user\DISK2\redo02b.log' to group 2,
'D:\app\user\DISK2\redo03b.log' to group 3 ;

@log

@logfile
```
![image](https://user-images.githubusercontent.com/79209568/116773041-fa37be80-aa8d-11eb-81e4-143bc39ebb7d.png)

* status에 invalid라고 나와있는 멤버들은 아직 사용하지 못하므로 스위치 시켜줘야한다.
```sql
alter system switch logfile ;
alter system switch logfile ;
alter system switch logfile ;
@log
@logfile 
```
![image](https://user-images.githubusercontent.com/79209568/116773078-2b17f380-aa8e-11eb-96da-4b96b300093d.png)

* 멤버가 아니라 그룹을 추가하고 싶은 경우와 삭제
```sql
--추가
alter database add logfile group 4 
'D:\app\user\DISK1\redo04a.log' size 5m ;

--삭제
alter database drop logfile group 4;
```

* 멤버를 삭제할 때 현재 쓰고 있는 것은(current 상태) 삭제에 오류가 난다.
* 나머지를 지우고 current만 남긴 후 스위치 시키고나서 삭제하면 삭제 가능
```sql
alter database drop logfile member 'D:\app\user\oradata\orcl\redo01.log' ;
alter database drop logfile member 'D:\app\user\oradata\orcl\redo02.log' ;
alter database drop logfile member 'D:\app\user\oradata\orcl\redo03.log' ; -- current라서 오류

alter system switch logfile ;
alter database drop logfile member 'D:\app\user\oradata\orcl\redo03.log' ;

@log
@logfile
```
![image](https://user-images.githubusercontent.com/79209568/116773146-a083c400-aa8e-11eb-8329-62e49222e217.png)

## Archivelog mode 
```
-- 전체 아카이브
show parameter archive

show parameter db_recovery
-- 방금 만들었던 경로로 만들어줌
alter system set db_recovery_file_dest = 'D:\app\user\FRA' ; 
```
![image](https://user-images.githubusercontent.com/79209568/116776031-7c7dae00-aaa1-11eb-9974-b9de0e676df3.png)

```
shutdown immediate
startup mount
alter database archivelog ; 
alter database open ; 
```

```
-- 아카이브 로그인지 아닌지를 확인할 수 있음
archive log list

alter system switch logfile ;

select * 
from v$archived_log ;
```




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
```
