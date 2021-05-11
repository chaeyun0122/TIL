# 원격 제어 명령어 (remote access) - ssh
* Secure SHell : 보호된 shell
* 암호화 된 통신을 제공
  * 개인 key 교환을 통해 암호화에 사용할 key를 생성하고, 암호화 된 통신을 진행한다. (키를 교환해서 암호화에 쓸 새로운 키를 만든다는 뜻)
  * **보완성이 높다.**
* version 1, 2가 있으며 현재는 version2를 사용한다.
* Linux는 기본적으로 ssh 접속을 지원한다. 
## 관련정보
* 패키지 : openssh-*  
  
  > * 패키지 확인 `rpm -qa | grep openssh`  
  >   ![image](https://user-images.githubusercontent.com/79209568/117772276-f9631180-b271-11eb-9784-dedb10f4f934.png)

* 데몬 : sshd  
  
  > * 데몬 확인 `systemctl status sshd`
  >   ![image](https://user-images.githubusercontent.com/79209568/117772622-53fc6d80-b272-11eb-8329-e5c630f80254.png)

* 방화벽 : port=22/tcp, service=ssh
* 설정파일 : /etc/ssh/sshd_config

## ssh 설정 파일
* 설정 파일 열기 : `vi /etc/ssh/sshd_config`  
  ![image](https://user-images.githubusercontent.com/79209568/117775090-1ea54f00-b275-11eb-9be1-2c939e3d3631.png)
  * 설정 값들의 기본 값 확인 : `man sshd_config`
### 설정 값
* `Port 22` : ssh는 기본으로 22번 포트를 사용하도록 되어 있다.
  * 다른 포트를 쓰고 싶으면 주석 삭제 후 다른 포트를 쓴다. *(= 포트 포워딩)*
* `ListenAddress 0.0.0.0` : 어떤 주소로 접속을 허락할 것인지 설정. 0.0.0.0 내가 어떤 ip를 쓰던간에 나에게 접속할 수 있다.
 ```
 내가 쓰는 IP들
 ens32 : 192.168.10.128
 ens33 : 192.168.10.129
 ens34 : 192.168.10.130

 ListenAddress 192.168.10.130 -> 다른 IP로는 접속하지 못하고 130 IP만 허용한다.
 ```
* HostKey /etc/ssh/ssh_host_rsa_key
   #HostKey /etc/ssh/ssh_host_dsa_key
  * ecdsa, ed25519 : 추가 확장 key

* logging
  * version1에선 auth 사용, version 2에선 authpriv 사용이 기본 값

* authentication
  * LoginGraceTime : 로그인 대기 시간 (일정 시간 이후 연결 끊음)
  * PermitRootLogin : 루트로의 직접적인 로그인을 허용할 것 인지
  * StrictModes : 비밀번호 없는 접속을 허용할 것 인지(ssh는 불가능)
  * MaxAuthTries : 암호를 틀릴 수 있는 최대 횟수(그 이후는 연결 시도를 안함)
  * maxSessions : 최대 연결 회선 개수

* PubkeyAuthentication : 공개 키로 인증하는 것을 허용할 것 인지
* PasswordAuthentication yes : 암호없이 로그인할 때 필요한 설정
   #PermitEmptyPasswords no
* Banner : 로그인할 때 나오는 메시지 설정
* Subsystem       sftp    /usr/libexec/openssh/sftp-server : sftp에 대한 설정 내용

### 설정하기
* `PermitRootLogin no`로 변경
  ![image](https://user-images.githubusercontent.com/79209568/117780027-316e5280-b27a-11eb-82a9-84b7ec869ab6.png)
  
  * 하지만 다시 root로 접속해도 접속이 된다. 바뀐 정보를 적용해야한다.
* 데몬 재 실행
  ```
  systemctl restart sshd
  ```
  * 루트 접속 불가능 적용됨  
  ![image](https://user-images.githubusercontent.com/79209568/117780709-d8eb8500-b27a-11eb-8d73-eaebb08020ba.png)
* 이렇게 루트 접속을 불가능하게 한 후 루트 접속을 하려면 저번처럼 일반 사용자 접속 후 `su -`로 접속해야한다.  
  ![image](https://user-images.githubusercontent.com/79209568/117780972-19e39980-b27b-11eb-9fc5-cc3ee120bcae.png)


## ssh 접속하기
* `ssh <접속할 IP>`
  * 루트 접속을 막아놨기 때문에 로그인 거절이 된다. (ctrl c로 원래 프롬프토로)
    ![image](https://user-images.githubusercontent.com/79209568/117781531-b6a63700-b27b-11eb-9fb9-260e70d991c5.png)

> * 접속 명령
> 1. ssh
>   * ssh <접속할 IP> : 명령어를 입력한 사용자명으로 접속
