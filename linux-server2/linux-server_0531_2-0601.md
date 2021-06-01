## 실습 (준비)
```
1. 스냅샷 되돌리기
  useritbank 만들어서 dbitbank 연동
  useritbank 접속해서 연동여부 확인(중요!!)
  (익명사용자 다 지우기, root 계정 비밀번호 설정(itbank))
  
2. user            : main
   자동생성디렉터리  :  public_html (DocumnetRoot로 사용)
   도메인           :  www.hatems.com
   화면내용         :  MAIN 
```

# phpmyadmin
- mariadb(mysql) DB server를 web page를 이용한 GUI 환경으로 제공
  - web server와 DB server가 같은 장치에 구성되어 있어야 사용 가능
- php 언어로 작성되어 있기 때문에 web server가 php 언어를 알고 있어야 한다.
  - php 버전과 DB server 버전을 확인하여 사용
- 일반 사용자들에게 편의성을 제공하기 위해 사용
<hr>

- https://www.phpmyadmin.net/files/ 에서 4.0.10.20 버전 zip 다운로드
  ![image](https://user-images.githubusercontent.com/79209568/120170623-1d47c080-c23c-11eb-985b-364b523f9886.png)

- ftp로 업로드
  ```
  <PuTTY>
  1. yum -y install vsftpd-*
  2. systemctl restart vsftpd
  3. firewall-cmd --permanent --add-service=ftp
  4. firewall-cmd --reload

  <명령 프롬프트>
  1. 다운로드 받은 파일 C드라이브로 옮기기
  2. C드라이브로 cd
  3. ftp 192.168.217.128
  4. itbank계정으로 로그인
  5. cd /home/main/public_html
  6. put phpMyAdmin-4.0.10.20-all-languages.zip
  ```
  ![image](https://user-images.githubusercontent.com/79209568/120279402-5aba5580-c2f1-11eb-9280-4b21882a0b31.png)

- 압축해제
  ```
  unzip phpMyAdmin-4.0.10.20-all-languages.zip
  ```
- 이름 pma로 변경
  
  ![image](https://user-images.githubusercontent.com/79209568/120279921-006dc480-c2f2-11eb-8644-386ac512cc75.png)
- 데몬 재실행과 resolv.conf에 네임서버 192.168.217.128 추가
  
  ![image](https://user-images.githubusercontent.com/79209568/120280161-504c8b80-c2f2-11eb-83fa-648a7085ab8e.png)

- firefox에 `www.hatems.com/pma`로 접속
  
  ![image](https://user-images.githubusercontent.com/79209568/120280529-bf29e480-c2f2-11eb-81bf-3ec712e3aec2.png)

- 아파치 설정파일 (/etc/httpd/conf/httpd.conf)에서 형식에 index.php를 추가해준다.
  - 만약 DocumentRoot에 index.html이 없으면 index.php를 찾는다.
  
  ![image](https://user-images.githubusercontent.com/79209568/120280958-54c57400-c2f3-11eb-8af0-b04567cb4ff4.png)
- 데몬 재실행 후 새로고침하면 내용이 바뀐다. `index.php` 파일이 나옴
  - 리눅스가 php 언어를 모르기 때문에 php 소스코드가 그대로 나온다.
  
  ![image](https://user-images.githubusercontent.com/79209568/120281504-fe0c6a00-c2f3-11eb-9a3f-b7533d2854ba.png)

- php 패키지 설치 `yum -y install php-*`
  - 오류나므로 `--skip-broken` 옵션 추가해서 설치

- 데몬 재실행 후 새로고침하면 php 파일을 읽을 수 있게 된다.
  
  ![image](https://user-images.githubusercontent.com/79209568/120283077-c6062680-c2f5-11eb-84de-d839e83b65e4.png)
- root 계정으로 접속한다. (root/itbank)
  
  ![image](https://user-images.githubusercontent.com/79209568/120283869-9d326100-c2f6-11eb-8a1a-92de869b7593.png)

## 실습
```
* root에서 진행
D/B     : dbkg
user    : userkg

* userkg에서 진행
table   : tbkg
field   : num, name, age, phone
values  : 5개 이상
```
![image](https://user-images.githubusercontent.com/79209568/120292011-dbcc1980-c2fe-11eb-9ffc-89cf3820f23d.png)
![image](https://user-images.githubusercontent.com/79209568/120292041-e1296400-c2fe-11eb-9be8-570a6f9412cc.png)
![image](https://user-images.githubusercontent.com/79209568/120292053-e71f4500-c2fe-11eb-9a64-09becf540aa9.png)
![image](https://user-images.githubusercontent.com/79209568/120292068-eab2cc00-c2fe-11eb-827c-ef189919cc25.png)
![image](https://user-images.githubusercontent.com/79209568/120292077-f0101680-c2fe-11eb-884c-299730a0f769.png)
![image](https://user-images.githubusercontent.com/79209568/120292094-f2727080-c2fe-11eb-86de-2ccc3304b394.png)
![image](https://user-images.githubusercontent.com/79209568/120292215-13d35c80-c2ff-11eb-965a-a48ee911cded.png)
