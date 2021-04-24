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
































