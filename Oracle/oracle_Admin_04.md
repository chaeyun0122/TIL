# 유저 보안 관리
## 데이터베이스 유저 계정
```sql
CREATE USER inventory     --유저 명
IDENTIFIED BY oracle_4U   --인증 방식 : 패스워드
PROFILE default           --자원 관리, 패스워드 관리
DEFAULT TABLESPACE tbs1   --저장 공간 
TEMPORARY TABLESPACE temp --임시 공간
ACCOUNT UNLOCK            --계정 잠금
PASSWORD EXPIRE           --패스워드 만료 시키기
QUOTA UNLIMITED ON tbs1 ; --저장공간 크기 제한
```
> * tablespace : 메모리에서 유저 별로 사용 공간을 지정한다.
> * quota : 테이블 스페이스 중 사용할 공간을 지정한다.

* 스키마 : 데이터베이스 유저가 소유하는 데이터베이스 객체의 모음. 유저 계정고 동일 이름
  ![image](https://user-images.githubusercontent.com/79209568/115949310-32c81d00-a50f-11eb-822b-8c5c0fd032f6.png)

* 테이블 스페이스 생성 (SQL Plus에서 진행)
  ```sql
  CREATE TABLESPACE tbs1
  DATAFILE 'D:\app\user\oradata\orcl\tbs1.dbf' SIZE 100M AUTOEXTEND ON NEXT 10M MAXSIZE 1G ;
  ```
* 유저 생성
  ```sql
  CREATE USER inventory 
  IDENTIFIED BY oracle_4U 
  PROFILE default 
  DEFAULT TABLESPACE tbs1
  TEMPORARY TABLESPACE temp 
  ACCOUNT UNLOCK 
  PASSWORD EXPIRE 
  QUOTA UNLIMITED ON tbs1 ; 
  ```
  
## 권한
### 시스템 권한
- 유저가 데이터베이스에서 특정 작업을 수행할 수 있도록 한다.
### 객체 권한
- 유저가 특정 객체를 액세스 및 조작할 수 있다.

* 권한 부여
  ```sql
  GRANT create session TO inventory ; 
  CONN inventory/oracle_4U
  ```
  
# 프로파일
* 실질적으로 쓰지 않음
```sql
ALTER PROFILE default LIMIT 
composite_limit		       UNLIMITED
sessions_per_user		UNLIMITED     -- 동시 접속 유저 개수 제한
private_sga			UNLIMITED
connect_time			UNLIMITED
idle_time			UNLIMITED       -- 놀고있는 세션 끊음
logical_reads_per_call	        UNLIMITED
logical_reads_per_session	UNLIMITED
cpu_per_call			UNLIMITED         
cpu_per_session		        UNLIMITED
password_verify_function	NULL
password_reuse_max		UNLIMITED   -- 동일한 패스워드 재사용 가능 횟수
password_reuse_time		UNLIMITED   -- 해당 일 수 이후 동일한 패스워드 재사용 가능 
password_life_time		UNLIMITED   -- 해당 일 수 이후 패스워드 변경을 해야함
failed_login_attempts		UNLIMITED    -- 해당 횟수 이상 패스워드 틀리면 계정이 잠김
password_lock_time		UNLIMITED     -- 계정 잠궈 놓는 기간 설정
password_grace_time		UNLIMITED ; -- 변경 요청 메시지 이후 해당 기간만큼 유예기간을 줌

CREATE PROFILE hrprofile LIMIT 
sessions_per_user		1
idle_time			15
cpu_per_call			50        --자원 소모를 막음
password_reuse_max		2
password_life_time		30
failed_login_attempts		5
password_lock_time		1 ; 
```
![image](https://user-images.githubusercontent.com/79209568/115950895-e4b81700-a518-11eb-9029-4d8234357ad1.png)

```sql
show parameter resource_limit

ALTER USER inventory PROFILE hrprofile ; 

ALTER USER inventory IDENTIFIED BY oracle ;
ALTER USER inventory IDENTIFIED BY oracle ;

ALTER USER inventory IDENTIFIED BY oracle_4U ;

CONN inventory/oracle_4U
SELECT * 
  FROM all_objects a, all_objects b, all_objects c 
ORDER BY 1,2,3,4,5,6,7 ; 

CONN / as sysdba
ALTER SYSTEM SET resource_limit = FALSE ;
ALTER USER inventory PROFILE DEFAULT ; 
DROP PROFILE hrprofile ;
DROP USER inventory CASCADE ; 
```
![image](https://user-images.githubusercontent.com/79209568/115950907-f5688d00-a518-11eb-914e-e5e70adf430a.png)
