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
> [사진으로 한번에 보기](#설정파일-정리)
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

### `connect_from_port_20` : [20번 포트 연결](#FTP-모드) 허용 여부

### `chown_uploads`, `chown_username` : anonymous의 업로드 허용 시 소유권이 ftp로 올라가는데 그걸 특정 사용자의 소유로 만들어주고 싶을 때 하는 설정

### `idle_session_timeout` : ftp 접속 후 아무것도 안할 때 연결 끊길 때까지 걸리는 시간
* 데이터를 주고 받기 전에 아무것도 안함

### `data_connection_timeout` : ftp 접속 후 데이터를 주고 받은 후에 아무것도 안할 때 연결 끊길 때까지 걸리는 시간
* 데이터를 주고 받은 후 아무것도 안함

### `ftpd_banner` : 베너 설정

### `deny_email_enable` : 특정 이메일 주소를 막을 것인지 설정

### `banned_email_file` : 막을 이메일 주소 목록

### `chroot_*` : 로컬 [사용자의 격리](#사용자-격리)에 대한 설정
* 로컬 사용자의 격리를 위해 주석을 풀어준다.  
  ![image](https://user-images.githubusercontent.com/79209568/118107549-d8d5bb80-b419-11eb-95a7-14dacfd37112.png)
* 하지만 아직 예외 목록 파일이 없기 때문에 접속 오류가 난다.  
  ![image](https://user-images.githubusercontent.com/79209568/118107630-f571f380-b419-11eb-98c4-a27559ef5bef.png)
* `touch /etc/vsftpd/chroot_list` 해당 파일을 만들어 준다.(비어있어도 됨)  
* 6 버전까지는 여기까지하면 접속완료, 하지만 7버전부터 새로운 설정을 하나 추가해줘야한다.
  * 7버전 이상이라 접속 오류가 난다.  
  ![image](https://user-images.githubusercontent.com/79209568/118107863-41bd3380-b41a-11eb-9a4b-1da810b7ea1d.png)
* 설정 파일에 `allow_writeable_chroot=YES` 설정을 추가해준다. (데몬 재실행)  
  ![image](https://user-images.githubusercontent.com/79209568/118107928-5699c700-b41a-11eb-9ca0-262213a67264.png)
* 사용자 격리 완료  
  ![image](https://user-images.githubusercontent.com/79209568/118108008-6dd8b480-b41a-11eb-97bd-90e7bfd7a605.png)

### `ls_recurse_enable` : 대문자 R 옵션 사용 가능 여부
![image](https://user-images.githubusercontent.com/79209568/118108190-a8dae800-b41a-11eb-978a-47c7c1373652.png)

### `listen`, `listen_ipv6` : listen설정. 각각 IPv4와 IPv6에 대한 설정
* 데몬을 독립모드(stand alone)로 사용할 것인지 설정하는 것이다. 
* 7버전 부터는 모든 서비스가 system d에 종속되므로 기본값이 `NO`다 
* IPv4는 보안성이 떨어지고 IPv6는 별도로 관리하도록 `YES`가 기본값이다. 

> #### FTP 모드
> * ftp는 active mode와 passive mode로 분류
> * active mode : client가 server의 command port로 접속, data 전송은 server가 client의 data port 연결 (client의 방화벽 설정이 필요)
> * passive mode : client가 server의 command port로 접속, data 전송은 server가 client에게 임의의 port를 알려주고 client가 server의 임의의 port로 연결 (client에서 방화벽 설정 X) **20번 포트 사용을 안함**
>   ![image](https://user-images.githubusercontent.com/79209568/118102271-8c877d00-b413-11eb-9473-4e34d87bc5ee.png)

> #### 사용자 격리
> * FTP server는 보안 목적으로 접속하는 사용자를 격리할 수 있다.
> * 사용자 격리는 사용자의 최초 접속 위치 밖으로는 이동할 수 없도록 만드는 설정
> * 익명 사용자는 격리가 적용, 로컬 사용자는 설정을 통해 사용자 격리가 가능
> * 최초 접속 위치를 `/`로 바꿔서 인식시켜주는 것이 사용자 격리다.
>   ![image](https://user-images.githubusercontent.com/79209568/118107255-7e3c5f80-b419-11eb-9d0b-b1d7fed40d37.png)
>     * 익명 사용자의 pwd는 `/` . 루트가 아님. `var/ftp/`에 있는 디렉토리다.
>     * 따라서 anonymous는 위로 아무리 올라가도 `var/ftp` 현재 위치에서 상위로 벗어날 수 없음


> #### 설정파일 정리
> ![image](https://user-images.githubusercontent.com/79209568/118100189-213cab80-b411-11eb-96c5-1f64e769cbad.png)  
> ![image](https://user-images.githubusercontent.com/79209568/118106735-e3438580-b418-11eb-9d7b-d03f2fe8c8f1.png)  
> ![image](https://user-images.githubusercontent.com/79209568/118108079-85b03880-b41a-11eb-9ab5-662ce0d1e54d.png)  
> ![image](https://user-images.githubusercontent.com/79209568/118108731-50581a80-b41b-11eb-813d-6288d0dad236.png)  



