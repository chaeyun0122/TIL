# XpressEngine
- PHP 언어로 만들어진 web page 제공하는 zero board 중 하나
  - web page의 내용들을 DB서버에 저장
- 저장된 데이터를 불러와서 언제든지 web page 복구 가능
- core와 각종 module로 구성
  - core 설치 후 필요한 module을 조합하여 web page를 생성
- DB 서버와 web 서버가 서로 다른 장치에 구성되어 있어도 정상 동작이 가능
  - web 서버는 서울에, DB 서버는 외국에 구성
- DB에 web page 내용이 저장될 때 테이블 접두사를 이용하여 저장된 정보 분류
  - 최초에 core 설치 시 설정에 적혀있는 테이블 접두사가 해당 database에 없으면 table 전부 생성
  - database에 그 접두사가 붙어있는 table이 있다면 해당 내용을 불러온다.
<hr>

- https://xe1.xpressengine.com/index.php?mid=download&package_id=18325662&release_id=22756225 에서 다운로드
  
  ![image](https://user-images.githubusercontent.com/79209568/120296203-d96bbe80-c302-11eb-80f6-b0f698eb946a.png)
- ftp로 DocumentRoot에 업로드
  
  ![image](https://user-images.githubusercontent.com/79209568/120296623-467f5400-c303-11eb-8c7c-71281e4acd28.png)
- 압축해재
  ```
  unzip xe.zip
  ```
- firefox로 `www.hatems.com/xe/` 접속 : xe 코어를 설치할 수 있게 해주는 화면
  
  ![image](https://user-images.githubusercontent.com/79209568/120297011-a544cd80-c303-11eb-89b7-e07745bb02d8.png)
  - 설치 언어 : 한국어
  - 사용권 동의 : 동의 체크 후 다음
  - 설치 조건 확인
    
    ![image](https://user-images.githubusercontent.com/79209568/120297260-e6d57880-c303-11eb-8e8b-6d256ac075c8.png)
    - `chmod 707 xe`로 권한을 잡아준다.
      
      ![image](https://user-images.githubusercontent.com/79209568/120297377-04a2dd80-c304-11eb-9609-d13445c5f636.png)
    - 다음 누르고 설치 가능 확인 후 설치 진행
    
    ![image](https://user-images.githubusercontent.com/79209568/120297507-1edcbb80-c304-11eb-93f4-60d4eae41d0e.png)
  - DB 선택
    
    ![image](https://user-images.githubusercontent.com/79209568/120297788-6c592880-c304-11eb-9d3e-834afa3bf840.png)
    - innodb : mysql, mariadb를 위한 DB 엔진 (5.5버전 기준으로 innodb가 기본적으로 포함되어있다.)
  - DB 정보 입력
    
    ![image](https://user-images.githubusercontent.com/79209568/120299133-b2fb5280-c305-11eb-9587-34b9824fddda.png)

    - DB 정보 : 어떤 사용자로 접속해서 어느 DB를 쓸 것인지에 대한 내용
    - DB 호스트네임 : DB 서버 접근 IP
    - DB Port : DB 서버 접근 port
  - 환경설정
    
    ![image](https://user-images.githubusercontent.com/79209568/120299175-bdb5e780-c305-11eb-8dcc-3e0ef2cef138.png)
  - 관리자 정보 입력
    
    ![image](https://user-images.githubusercontent.com/79209568/120299218-c73f4f80-c305-11eb-9f61-fd27b962ef47.png)
  - 완료

- useritbank 유저로 접속해서 dbitbank 테이블을 확인하면 xe 접두사가 붙은 테이블들을 확인할 수 있다.
  
  ![image](https://user-images.githubusercontent.com/79209568/120299721-3b79f300-c306-11eb-927c-e6f0ba0ee29b.png)
