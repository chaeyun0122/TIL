# 오라클 데이터베이스 감사(Audit) 구현
### 11g버전
* 감사 명령어 `AUDIT`
  * 명령어마다 다 AUDIT 명령어으로 수동 작업이 필요하다.
  * 비활성화는 NOAUDIT 명령어를 사용한다.
  ```SQL
  AUDIT select on scott.emp ; 
  AUDIT CREATE TABLE ; 
  AUDIT CREATE SESSION ;
  ```
* 감사된 내역은 `dba_audit_trail`에 저장됨
  * 새로운 터미널에서 `system/oracle_4U`로 로그인
  * 해당 명령어 순차적으로 실행
    ```sql
    CREATE TABLE t (id NUMBER) ;

    DROP TABLE t PURGE ;

    SELECT empno, sal
    FROM scott.emp ;
    ```
  * 설정한 감사 내용이 기록 되었는지 확인
    ```SQL
    SELECT username, timestamp, owner, obj_name, action_name, sql_text, sql_bind
    FROM dba_audit_trail ;
    ```
    ![image](https://user-images.githubusercontent.com/79209568/116766217-50900780-aa64-11eb-809d-124c44b6b80d.png)

* 비우기도 가능
  ```SQL
  TRUNCATE TABLE aud$ ;
  ```
