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
* 
