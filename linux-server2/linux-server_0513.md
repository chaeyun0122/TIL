# FTP
* File Transger Protocol ; 파일 전송 프로토콜
* client가 FTP server에 접속하여 자신의 파일을 server에 저장하거나 **(upload)**  
server에 있는 파일을 내 컴퓨터로 전송 **(download)**
* FTP 프로그램 중 실습에는 `vsftpd` 사용

## 관련정보
* 패키지 : vsftpd-*
* 데몬 : vsftpd
* 방화벽 : port=21(ftp-command), 20(ftp-data), random(passive mode)/tcp, service=ftp
* 설치파일 : /etc/vsftpd/vsftpd.conf

## FTP 설치
* vsftpd 설치  
  ```
  yum -y install vsftpd-*
  ```
* 데몬 재 실행  
  ```
  systemctl restart vsftpd
  ```
* 방화벽 서비스 등록
  ```
  firewall-cmd --permanent --add-service=ftp
  ```
  ![image](https://user-images.githubusercontent.com/79209568/118095096-a07ab100-b40a-11eb-8281-99d2fa2f8e68.png)

## FTP 접속
* windows cmd에서 진행
* FTP server는 두 가지 목적으로 분류
  * **로컬 ftp server** : ftp server가 설치된 시스템의 사용자 계정들이 접속하는 ftp server
    * itbank 로컬 사용자로 접속  
    
      ![image](https://user-images.githubusercontent.com/79209568/118095486-14b55480-b40b-11eb-857b-ec1610181989.png)
    * `quit`으로 나오기
  * **익명 ftp server** : 누구나 접근 가능한 ftp
    * `anonymous` 계정으로 로그인 (암호 X)  
    
      ![image](https://user-images.githubusercontent.com/79209568/118095691-5645ff80-b40b-11eb-99d3-6fc89094edf9.png)

## FTP 설정파일
> ### `vi /etc/vsftpd/vsftpd.conf`
> [사진으로 한번에 보기](vsftpd.conf-정리)
### `anonymous_enable` : 익명 접속 가능 여부 설정 
* 현재 `YES`가 기본 값인데 `NO`로 바꿔본다.
* 그 후 데몬 재 실행 `systemctl restart vsftpd`
* anonymous로 접속해보기  

  ![image](https://user-images.githubusercontent.com/79209568/118096645-98237580-b40c-11eb-90ce-912743e96c96.png)
### `local_enable` : 로컬 접속 가능 여부 설정
* `NO`로 바꿔본다.  

  ![image](https://user-images.githubusercontent.com/79209568/118096998-0f590980-b40d-11eb-811b-7fdae113388c.png)
> * 둘 다 NO로 바꾼다면? → 바로 연결 끊김  
>  
>   ![image](https://user-images.githubusercontent.com/79209568/118097329-81315300-b40d-11eb-814d-9f81bf5f7eea.png)

### `write_enable` : 쓰기 권한 허용 여부 설정
### `local_umask=022` : ftp에 접속한 로컬 유저들을 위한 umask값
* 로컬 유저들이 파일 업로드, mkdir을 하면 umask가 무조건 22로 적용됨
### `anon_upload_enable`, `anon_mkdir_write_enable` : 익명 사용자의 업로드
* 기본적으로 막혀 있다
### `dirmessage_enable` : 디렉토리 메시지 설정
* `.message` 파일이 존재하면 접속 시 그 파일의 내용이 그대로 출력 되도록 할 수 있다.
  * 로컬 사용자(itbank)에 `.message`파일을 작성한다.  

    ![image](https://user-images.githubusercontent.com/79209568/118098650-1f71e880-b40f-11eb-9bfb-3c8a1373d9d0.png)
  * ftb에서 로컬 사용자로 접속  
    
    ![image](https://user-images.githubusercontent.com/79209568/118098975-94452280-b40f-11eb-8b0c-e4ef3adff05f.png)

### `xferlog_enable` : 로그를 남길 것인지 여부
### `xferlog_file` : 로그파일의 경로와 이름 설정
* 기본적으로 해당 경로로 만들어주지만 다른 경로를 원하면 바꿔주면 된다.
### `xferlog_std_format` : 로그파일을 기본 형식으로 저장할 것인지 여부
* 다른 형식으로 하려면 `NO`로 바꾸면 된다.
* 다른 형식으로 할 때의 주의점은 `xferlog_file`의 경로와 이름을 다른 것으로 바꿔줘야한다는 점이다.

> #### vsftpd.conf 정리
> ![image](https://user-images.githubusercontent.com/79209568/118100189-213cab80-b411-11eb-96c5-1f64e769cbad.png)
