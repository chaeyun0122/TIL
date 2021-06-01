## 실습 (준비)
```
1. 스냅샷 되돌리기
  useritbank 만들어서 dbitbank 연동
  useritbank 접속해서 연동여부 확인(중요!!)
  
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
