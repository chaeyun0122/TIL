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

데이터베이스(DB)
* DATA FILE -> SGA의 DATABASE BUFFER CACHE에 존재
* CONTROL FILE
* REDO LOG FILE -> SGA의 REDO LOG BUFFER에 존재
* SHARED POOL은 
