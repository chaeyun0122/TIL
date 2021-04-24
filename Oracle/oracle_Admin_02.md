# 데이터베이스 인스턴스 관리
## 초기화 파라미터 파일
* spfileorcl.ora
* initorcl.ora

## 초기화 파라미터 유형
* 오라클이 파라미터에 대한 유형을 기본, 고급으로 나눠났다.
* 파라미터들은 기본 값으로 설정되어 있고 원하는 값들을 수정하면 된다.
* 파라미터 확인
  ```sql
  show parameter;
  select * from v$parameter;
  
  show parameter control; --특정 값을 검색할 때
  ```

## 초기화 파라미터 종류
* 메모리의 크기를 조절하는 파라미터들이 있다.
* 프로세스의 개수 조절
* 운영 관련 설정 (자동, 수정)
* 파라미터 종류를 확인할 수 있는 메뉴얼 [URL](https://docs.oracle.com/database/121/REFRN/GUID-FD266F6F-D047-4EBB-8D96-B51B1DCA2D61.htm#REFRN-GUID-FD266F6F-D047-4EBB-8D96-B51B1DCA2D61)

## 초기화 파라미터 값 변경
### Static Parameter
* 인스턴스가 이미 시작 되어 있는 도중에 수정하는 것이 불가능
* 변경하고 싶으면 파라미터 파일에서만 변경 가능
### Dynamic Parameter
* alter system : 전체 시스템에 반영
* alter session : 현재 로그인되어 있는 유저가 작업하는 범위 내에서만 반영


# 오라클 데이터베이스 인스턴스
## 시작 (SHUTDOWN → NOMOUNT → MOUNT → OPEN)
### NOMOUNT
* 인스턴스 시작 : pfile만 있으면 됨
* DB 생성할 때
### MOUNT
* cotrol 파일을 오픈하는 작업 : control 파일만 있으면 됨
* 위치정보는 파라미터에 있다.
### OPEN
* 데이터 파일과 리두로그 파일을 오픈하는 작업 : 리두로그 파일과 데이터 파일이 있어야 됨

## 종료
* 적어도 실행 된 인스턴스가 존재 한다는 뜻
* 시작과 다르게 **단계별로 내릴 수 없음**
* 어떤 옵션을 뜨던지 간에 SHUTDOWN 프로세스가 시작된다면 새로운 사용자 로그인은 절대 못함
### 종료 옵션
![image](https://user-images.githubusercontent.com/79209568/115942749-b6b9df00-a4e6-11eb-8bb8-c36cf79bdbaf.png)
* **N**(Nomal) : 모든 사용자가 알아서 다 자기 작업하고 session을 끊고 나가서 아무도 이 인스턴스에 연결되어있지 않을 때 shutdown 작업이 진행된다.
* check point 프로세스가 모든 파일의 싱크를 동일하게 맞춰준 후 인스턴스가 내려가는 작업이다. (현장에서 많이 쓰이지 않음)
* **T** (Transactional) : 하나의 트랜잭션 단위가 끝나면 또 다른 트랜잭션을 시작할 수 없도록 세션을 끊는 작업 (현장에서 많이 쓰이지 않음
* **I** : 현재 연결되어있던 모든 세션들을 그 즉시 kill 시킴. 일단 더티버퍼들은 저장 시켜줌 변경된 작업들을 강제로 rollback을 시킴 (가장 많이 사용 됨)
* **A** : 응급 상황일 때. 비정상 종료를 진행하는 작업. 인스턴스의 복구작업이 필요할 수 있는 작업

## 시작 종료 실습
* 현재 가동 중인 DB의 인스턴스 명을 확인
  ```
  SELECT instance_name, status
  FROM v$instance ; 
  ```
  ![image](https://user-images.githubusercontent.com/79209568/115943591-c7208880-a4eb-11eb-9464-11da5285ba15.png)

* 인스턴스가 가동 중인 상태에서 `STARTUP` 명령어 수행해보기  
  ![image](https://user-images.githubusercontent.com/79209568/115943633-fe8f3500-a4eb-11eb-9ac2-1e63eb1faa8f.png)

* `SHUTDOWN IMMEDIATE` 해보기  
  ![image](https://user-images.githubusercontent.com/79209568/115943652-1a92d680-a4ec-11eb-880d-a2842f718dc6.png)

* 가동 중인 인스턴스가 없는 상태에서 `STARTUP` 해보기  
  ![image](https://user-images.githubusercontent.com/79209568/115943668-3c8c5900-a4ec-11eb-8fe8-e17f90fbd0a3.png)

* `STARTUP FORCE`로 인스턴스 다시 시작하기  
  ![image](https://user-images.githubusercontent.com/79209568/115943682-634a8f80-a4ec-11eb-89dd-995bcfb37a5d.png)

## Alert Log 보기
* db 운영 상황에 대한 모든 기록들이 순차적으로 남는 파일이다.
* os딴에 만들어짐
* 경로 : `D:\app\user\diag\rdbms\orcl\orcl\trace`의 **alert_orcl.log** 파일  
  
  ![image](https://user-images.githubusercontent.com/79209568/115943759-d7853300-a4ec-11eb-9259-cd9ba689a70d.png)

# Dynamic Performance View
* v$ 로 시작한다.
* Instance 메모리 구조의 상태 변경과 관련된 정보에 액세스할 수 있다.

# Data Dictionary View
* DBA_, ALL_, USER_ 로 시작한다.
  ![image](https://user-images.githubusercontent.com/79209568/115944708-3dc08480-a4f2-11eb-80c8-4d1f87662046.png)
* DB에 영구히 저장되어야 하는 정보들에 액세스할 수 있다.


```sql
select * from v$session;
select * from v$sgastat;
select * from v$transaction; --아무것도 없다면 ,,
-- dynamic performance 뷰


select * from dba_tables; -- db에 존재하는 모든 테이블
select * from dba_users; -- db에 존재하는 모든 유저
-- 데이터 딕셔너리

select * from obj$;
select * from tab$;
select * from col$;

select *
from dba_views
where view_name='DBA_TABLES';
```
