# 데이터베이스(Oracle 12c) 설치
## 제품 설치
### 보안 갱신 구성
* 체크 박스 해제
* 경고 창 나오면 `예`누르고 넘기기  
  
  ![image](https://user-images.githubusercontent.com/79209568/115101440-211ace80-9f7f-11eb-9c20-2cfbdaad98d0.png)
### 설치 옵션
* `데이터베이스 소프트웨어만 설치` 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/115101459-48719b80-9f7f-11eb-87c0-2c9345f0c5a5.png)
### Grid 설치 옵션
* `단일 인스턴스 데이터베이스 설치`
  * RAC : 둘 이상의 인스턴스를 하나에 연결하는 것
  
  ![image](https://user-images.githubusercontent.com/79209568/115101474-7656e000-9f7f-11eb-8e90-91baf19cbd8c.png)
### 제품 언어
* 한국어 영어 그대로 두기
  
  ![image](https://user-images.githubusercontent.com/79209568/115101484-7fe04800-9f7f-11eb-889d-5a020289eef8.png)
### 데이터베이스 버전
* `Enterprise Edition`
* 디스크 공간이 6기가 이상이 필요함
  
  ![image](https://user-images.githubusercontent.com/79209568/115101488-8a024680-9f7f-11eb-9b15-2c12d33a3ba1.png)
### Oracle 홈 사용자 선택
* `Windows 내장 계정 사용`
* 경고창 나오면 `예` 누르고 넘기기
  
  ![image](https://user-images.githubusercontent.com/79209568/115101550-262c4d80-9f80-11eb-9bb1-3c7008ae6589.png)
### 설치 위치
* 저장공간이 충분한 디스크로 경로 설정
* 경로를 수정할 경우 한글 경로가 포함되지 않도록 주의한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/115101607-92a74c80-9f80-11eb-95f2-8e995284e990.png)
### 필요 조건 검사
* 필요 조건 검사가 완료되면 `설치` 시작
  
  ![image](https://user-images.githubusercontent.com/79209568/115101634-d00bda00-9f80-11eb-8c78-526556362482.png)
### 제품 설치
* 설치가 완료되는 것을 확인
* 설치 도중 보안 경고가 나오면 `액세스 허용` 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/115101667-05182c80-9f81-11eb-8252-9dead16e6e4e.png)
### 완료
* 완료된 것을 확인하고 `닫기`
  
  ![image](https://user-images.githubusercontent.com/79209568/115101722-85d72880-9f81-11eb-9515-c7d4815948d8.png)

## DB 생성
* 시작에 `Oracle - OraDB12Home1`폴더가 새로 설치된 것을 확인

  ![image](https://user-images.githubusercontent.com/79209568/115101757-c767d380-9f81-11eb-8d7a-500635ad424a.png)
* `Databasse Configuration Assistant` 실행
### 데이터베이스 작업
* `데이터베이스 생성`
  ![image](https://user-images.githubusercontent.com/79209568/115101817-1ca3e500-9f82-11eb-9d96-de4f14df5d51.png)
### 생성 모드
* `고급 모드` 클릭
  ![image](https://user-images.githubusercontent.com/79209568/115101823-2d545b00-9f82-11eb-83ee-5e253ef44ab2.png)
### 데이터베이스 템플리트
* `범용 또는 트랜잭션 처리`
  ![image](https://user-images.githubusercontent.com/79209568/115101864-6bea1580-9f82-11eb-8a6d-2bdb00b5f5b6.png)
### 데이터베이스 ID
* `orcl` 입력
  * 현재는 DB이름과 SID의 이름이 동일. SID는 실행된 소프트웨어 영역 인스턴스의 이름이다. 만약 RAC 환경을 쓴다면 둘의 아이디는 달라질 수도 있다.
* `컨테이너 데이터베이스로 생성` **절대 체크하지 않기**
* 보안경고창 뜨면 `액세스 허용` 클릭

  ![image](https://user-images.githubusercontent.com/79209568/115101936-db600500-9f82-11eb-8a07-f79f298e9dae.png)
### 관리 옵션
* 맨 위 `EM 구성` 체크를 해도되고 안해도 됨(안하고 넘어감)
  
  ![image](https://user-images.githubusercontent.com/79209568/115101963-16facf00-9f83-11eb-80f5-6c2bffd702ce.png)
### 데이터베이스 인증서
* `모든 계정에 동일한 관리 비밀번호 사용`클릭
  * 비밀번호 : `oracle_4U`
  
  ![image](https://user-images.githubusercontent.com/79209568/115101989-47db0400-9f83-11eb-86b0-14c70097931c.png)
### 네트워크 구성
* `새 리스너 생성` 클릭
  * 리스너 이름 : LISTENER
  * 리스너 포트 : 1521
  
  ![image](https://user-images.githubusercontent.com/79209568/115102024-7527b200-9f83-11eb-992c-552419460fe0.png)
### 저장 영역 위치
* 그대로 `다음` 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/115102059-a99b6e00-9f83-11eb-927e-bebac1c1e35d.png)
### 데이터베이스 옵션
* `샘플 스키마` 체크
  
  ![image](https://user-images.githubusercontent.com/79209568/115102070-bb7d1100-9f83-11eb-967e-6a695bae27bb.png)
### 초기화 매개변수
#### 메모리
* 일반 설정의 `메모리 크기`를 `1024`로 설정
* `자동 메모리 관리 사용` 체크
  
  ![image](https://user-images.githubusercontent.com/79209568/115102104-e5cece80-9f83-11eb-8fa5-c360124bf13f.png)
#### 문자집합
* `유니코드 사용` 선택
  * 기본값 : 한국어, 영어만 쓰려면 이걸로 선택
  * 유니코드 : 여러언어를 다 쓰려면 이걸로 선택
  
  ![image](https://user-images.githubusercontent.com/79209568/115102160-2a5a6a00-9f84-11eb-98cb-209c389ced4e.png)
### 생성 옵션
* `데이터베이스 생성` 체크 된 그대로 다음
* 템플리트로 저장 : 해당 DB생성 옵션들을 파일로 저장가능
  
  ![image](https://user-images.githubusercontent.com/79209568/115102203-5e358f80-9f84-11eb-9910-5fe4c49b5d7c.png)
### 요약
* `완료` 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/115102223-6d1c4200-9f84-11eb-9afa-929b16497145.png)
### 완료
* `닫기` 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/115102560-92aa4b00-9f86-11eb-8cca-84b38d2cf794.png)

## 설정
* [해당 파일](Oracle/util/adsql.zip)(데이터 펌프) 압축 풀기. **반드시 C 드라이브** 바로 밑에 압축풀기
  
  ![image](https://user-images.githubusercontent.com/79209568/115102657-3693f680-9f87-11eb-8c93-243899f90364.png)
* `SQL Plus` 파일 바탕화면으로 드래그 앤 드롭
  
  ![image](https://user-images.githubusercontent.com/79209568/115102771-27617880-9f88-11eb-864a-3a570e8d30eb.png)
* `오른쪽 클릭 > 속성 > 바로가기`의 시작 위치를 `c:\adsql`로 입력 후 확인
  
  ![image](https://user-images.githubusercontent.com/79209568/115102792-46600a80-9f88-11eb-98cf-eb18ffefd380.png)
* SQL Plus 실행 후 `/ as sysdba` 입력 후 엔터
  
  ![image](https://user-images.githubusercontent.com/79209568/115102804-67286000-9f88-11eb-9225-eed741f55d6a.png)
* `@setup_win` 입력 후 엔터를 누르면 임포트가 자동 실행되며 완료되면 화면 사라짐
* 사용자명 입력에 `test/test`를 입력해서 test 사용자로 로그인
  
  ![image](https://user-images.githubusercontent.com/79209568/115102900-e1f17b00-9f88-11eb-8a95-092adb13828d.png)
* `alter user system identifyed by oracle_4U;` 명령 입력
  
  ![image](https://user-images.githubusercontent.com/79209568/115102938-15cca080-9f89-11eb-8b42-00ea496b41fc.png)

## SQL Developer
* 새 접속 클릭
* 해당 내용들을 입력 후 `테스트`를 눌러 `상태: 성공`이 뜨면 `접속` 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/115102981-5b896900-9f89-11eb-9d5c-14b5f219433f.png)

