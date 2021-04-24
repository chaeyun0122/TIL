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
-- 현재 sys계정이다
show parameter resource_limit
```  
![image](https://user-images.githubusercontent.com/79209568/115951046-96efde80-a519-11eb-9325-a991f6ea6a13.png)

* 생성한 프로파일을 inventory 유저에게 정의 (각각의 유저마다 프로파일은 하나만 할당된다.)
```sql
ALTER USER inventory PROFILE hrprofile ; 
```   
![image](https://user-images.githubusercontent.com/79209568/115951172-3c0ab700-a51a-11eb-816b-12cfce831e49.png)

* inventory 유저의 패스워드를 oracle로 변경 후 다시 한 번 같은 패스워드로 변경한다.
* `password_reuse_max		2`로 설정했기 때문에 재사용 불가능하다.
* 다른 비밀번호로는 잘 바뀜
```sql
ALTER USER inventory IDENTIFIED BY oracle ;
ALTER USER inventory IDENTIFIED BY oracle ;

ALTER USER inventory IDENTIFIED BY oracle_4U ;
```  
![image](https://user-images.githubusercontent.com/79209568/115951255-b3404b00-a51a-11eb-9d81-78e4331e5d1b.png)

* inventory 계정으로 로그인 후 해당 select문 실행
* `cpu_per_call			50`로 cpu 사용량을 제한 했기 때문에 호출 한계치가 초과했다는 오류가 난다.
```sql
CONN inventory/oracle_4U
SELECT * 
FROM all_objects a, all_objects b, all_objects c 
ORDER BY 1,2,3,4,5,6,7 ; 
```  
![image](https://user-images.githubusercontent.com/79209568/115951317-16ca7880-a51b-11eb-9a0d-99d73f313f20.png)

* 다시 sys계정으로 로그인 후 프로파일을 수정해준다.
* inventory 계정의 프로파일을 defaul로 바꿔준다.
```sql
CONN / as sysdba
ALTER SYSTEM SET resource_limit = FALSE ;
ALTER USER inventory PROFILE DEFAULT ; 
```  
![image](https://user-images.githubusercontent.com/79209568/115951366-5c874100-a51b-11eb-97ea-fb0764a96aaf.png)

* 프로파일 삭제와 inventory 유저를 삭제한다.
```sql
DROP PROFILE hrprofile ;
DROP USER inventory CASCADE ; 
```  
![image](https://user-images.githubusercontent.com/79209568/115951384-76c11f00-a51b-11eb-8e21-ffb8fb2901e3.png)

