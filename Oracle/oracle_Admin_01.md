# 오라클 데이터베이스 구조 탐색
> ## 초기 설정
> * 새로 접속  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/115130444-d22a7300-a02a-11eb-812f-55d2027c02ab.png)
> * `보기 > DBA`  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/115130472-0c941000-a02b-11eb-8b89-2842d6c252a3.png)
> * 왼쪽 아래 DBA 탭에서 `접속 추가` 후 접속에 sys로 설정 확인  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/115130491-2e8d9280-a02b-11eb-9c6a-8c4a5b3b5375.png)
> * 윈도우 서비스에서 oracle 메뉴들 수동으로 변경  
>    
>   ![image](https://user-images.githubusercontent.com/79209568/115130574-b7a4c980-a02b-11eb-889c-afc865a6cb2a.png)

## 오라클 데이터베이스 서버 구조
### 개요  
![image](https://user-images.githubusercontent.com/79209568/115130955-ebcdb980-a02e-11eb-8ed3-235c1d90168c.png)

## Instance 구성  
![image](https://user-images.githubusercontent.com/79209568/115131653-af9d5780-a034-11eb-8b71-3ecf2b076246.png)

### 싱글 인스턴스 환경
- 클러스터화되지 않은 시스템
- 인스턴스가 각각의 DB를 사용
### RAC (Real Application Cluster) 환경
- 클러스터링 : 물리적으로 분리되어있는 인스턴스을 하나로 묶어놓은 것처럼 사용
- 가용성 확보 가능
- 데이터 가드 : 재난재해를 위해 인스턴스들을 동일한 위치에 존재하지 않도록하고 데이터들을 동기화해서 사용하는 것 

## 데이터베이스 Instance에 연결
* 연결(connection) : 프로세스와 프로세스 간의 물리적인 네트워킹
* **세션(session)** : DB에 로그인 ~ 로그아웃의 과정. 논리적인 네트워킹
  * 현재 연결된 세션들 확인
  ```sql
  SELECT * FROM V$SESSION; -- 백그라운드로 연결된 세션들도 확인가능
  ```
  ![image](https://user-images.githubusercontent.com/79209568/115131299-f6d61900-a031-11eb-8b6f-7dbff8fe8fe4.png)

## 오라클 데이터베이스 메모리 구조
![image](https://user-images.githubusercontent.com/79209568/115131463-46691480-a033-11eb-9307-b25ef15882ae.png)
### PGA
* 프로세스별로 할당되는 메모리 자원
* 프로세스가 10개면 10개
* 크기의 차이는 있지만 특정 프로세스만의 작업 공간이기 때문에 **잠금장치가 없음**
* **경합이 존재하지 않는 영역**
### SGA
* 모든 프로세스가 공유해서 사용하는 공유 메모리 자원
* 인스턴스마다 **1개**
* 공유 영역이기 때문에 모든 구성요소는 그 공간에 접근하기 전에 확인 작업을 거쳐야 함. **잠금장치가 있음**
* **경합이 존재하는 영역**
기 때문에 모든 구성요소는 그 공간에 접근하기 전에 확인 작업을 거친다. 잠금장치가 있음. 경합이 존재하는 영역.

### Shared Pool (필수)
* 하나의 파일, 기능을 위해 존재하는 영역이 아닌 DB 전체 인스턴스의 동작을 위해(DB 엔진을 위해) 존재하는 영역
* 실제 조회하면 구성요소가 1000개 이상
  ```sql
  -- 조회 쿼리
  SELECT * 
  FROM V$SGASTAT
  WHERE POOL = 'shared pool';
  ```

### 데이터베이스 버퍼 캐시 (필수)
* 데이터파일을 위해, 디스크의 I/O를 최소화하기 위해 존재하는 메모리 자원
* I/O 최소작업단위는 BLOCK. 예를들어 블록이 8K의 크기라면 데이터버퍼의 크기도 8K여야하고 이 버퍼들의 모여있는 공간이 데이터버퍼 캐시다.
* 데이터 파일에서 읽은 데이터 블록 복사본을 보관함
  - 블록 : 운반 단위의 크기

### 리두 로그 버퍼 (필수)
* 셀렉트 외의 모든 명령어는 리두에 저장한다. FOR UPDATE가 있는 셀렉트는 리두 레코드를 만든다.
* 장애가 나면 장앨르 복구하기 위해서 만듦
* 디스크에서 작업하기엔 느리기때문에 속도가 빠른 리두 로그 버퍼에 사용자의 명령어를 먼저 모아둔다.

### Large Pool

### Java Pool

### Streams Pool

데이터베이스(DB)
* DATA FILE -> SGA의 DATABASE BUFFER CACHE에 존재
* CONTROL FILE
* REDO LOG FILE -> SGA의 REDO LOG BUFFER에 존재

### PGA
```SQL
SELECT *
FROM SH.SALES;
```
![image](https://user-images.githubusercontent.com/79209568/115132489-baa7b600-a03b-11eb-83f5-1ff6c79a59bb.png)
* 더 많은 행이 있지만 인출되는 것은 50개가 우선(내리면 50개씩 늘어남)
* 서버프로세스가 데이터를 보내는것은 적절한 크기가 중요하다.
* 행을 모았다가 보내줘야하는데 그 모으는 공간이 PGA의 CURSOR다. (50개를 채워서 보냄)
* STACK SPACE는 
* UGA : 유저를 위한 서버딴의 예약 공간
