# 원격 제어 명령어 (remote access) - telnet
* 옛날부터 사용되던 원격 접속 protocol
* CLI 환경으로 사용
* 거의 모든 OS에서 사용가능하지만 보안성이 낮음(비 암호화 통신 = 평문 통신) **→ 가급적 사용 X**
## 관련 정보
- 패키지 : telnet-*
- 데몬 : telnet.socket
- 방화벽 : port=23/tcp, service=telnet
- 설정파일 : 별도의 설정파일 없음
  > * 기본적으로 데몬은 .service 확장자가 자동으로 붙음 (ex. network = newtwork.service)
  > * telnet은 CentOS 7.9부터 별도의 service로 동작 하지 않음 (단순 통신 socket으로 취급)
  >   * 그렇기 때문에 .socket 확장자가 붙음
  >   * service가 아니기 때문에 설정 파일이 없음

## telnet 설치
* `df -h`를 통해 장치에 연결되어있는 디렉토리 확인
  ![image](https://user-images.githubusercontent.com/79209568/117628433-1c2ef080-b1b4-11eb-9483-a7c9ef52ea45.png)
* 'dev/sr0'을 /mnt에 마운트 시키기
  ```
  mount /dev/sr0 /mnt
  ```
  ![image](https://user-images.githubusercontent.com/79209568/117628657-5d270500-b1b4-11eb-8105-98920e619ddd.png)
* '/mnt'의 목록 확인 후 'Package' 폴더로 이동한다. Package 디렉토리에서 telnet 패키지를 검색한다.
  ```
  ls -l /mnt
  cd /mnt/Packages/
  ls | greo telnet
  ```
  ![image](https://user-images.githubusercontent.com/79209568/117629174-d9b9e380-b1b4-11eb-8ddf-9e412d994318.png)
* rpm 명령어로 telnet 패키지 설치
  ```
  rpm -ivh telnet-0.17-65.el7_8.x86_64.rpm
  rpm -ivh telnet-server-0.17-65.el7_8.x86_64.rpm
  ```
  ![image](https://user-images.githubusercontent.com/79209568/117629361-0d950900-b1b5-11eb-9a53-ca4d2ac20436.png)
* 설치 확인
  ```
  rpm -qa | grep telnet
  ```
  ![image](https://user-images.githubusercontent.com/79209568/117629606-4c2ac380-b1b5-11eb-9286-68534bac6e96.png)

## telnet 켜기
* `제어판 > 프로그램 > Windows 기능 켜기/끄기`
* 텔넷 클라이언트 체크하고 확인  
  ![image](https://user-images.githubusercontent.com/79209568/117632025-ceb48280-b1b7-11eb-81f2-f177d8d946f1.png)
* PuTTY에서 텔넷 상태 확인 `system status telnet.socket` → 꺼져있음
* `systemctl restart telnet.socket`로 켜줌  
  ![image](https://user-images.githubusercontent.com/79209568/117632892-78940f00-b1b8-11eb-8249-e94a92efa6bf.png)
* 명령 프롬프트에 `telnet <내 리눅스 IP>` 입력
* 하지만 접속되지 않음 ([방화벽](#firewall-방화벽)때문에)
  ![image](https://user-images.githubusercontent.com/79209568/117635285-a417f900-b1ba-11eb-921b-79a99e4afcce.png)
  * 방화벽에 해당 포트를 추가 해줘야한다. (PuTTY에서 실행)
    ```
    firewall-cmd --permanent --add-port=23/tcp
    firewall-cmd --reload
    ```
    > ### firewall 방화벽
    > * 장치의 네트워크 통신의 보안성을 높이는 요소
    > * 방화벽은 기본적으로 모든 트래픽을 차단하되 예외 규칙이 작성되어 있으면 해당 규칙에 부합하는 트래픽은 허용
    >   * white list 개념 사용(black list 반대)
    > * CentOS 6.x 까지는 iptables를 사용하다가 7.0부터는 **firewall**을 사용
    > #### 사용 방법
    > 1. 예외 규칙 작성
    >   ```
    >   firewall-cmd [--prmanent] <add or remove target>
    >   ```
    >   * `--permanent` : 모든 interface를 대상으로 규칙 적용
    >   * `--add` 추가, `--remove` 제거를 기준으로 port, service 등의 방화벽 예외 규칙 제어  
    ex) --add-port=???, --remove-service=??? 
    >   ![image](https://user-images.githubusercontent.com/79209568/117634958-631fe480-b1ba-11eb-9670-cad90dc93a5d.png)  
    >  
    > 2. 예외 규칙 적용
    >   ```
    >   firewall-cmd --reload
    >   ```
    >   ![image](https://user-images.githubusercontent.com/79209568/117635150-834fa380-b1ba-11eb-99aa-7404dacbf1d0.png)  
    > 3. 예외 규칙 확인
    >   ```
    >   firewall-cmd --list-all
    >   ```
    >   ![image](https://user-images.githubusercontent.com/79209568/117634105-97df6c00-b1b9-11eb-96bc-1bb4d555862f.png)  

## telnet 실행
* 명령 프롬프트에 `telnet <내 리눅스 IP>` 입력
  ```
  telnet 192.168.217.128
  ```
* **root로 로그인하지 못함.** 전에 만들어놓은 일반사용자 itbank 유저로 로그인 후 root로 접속한다.
  * server login : `itbank`
  * Password : `a1234`
  * `su -` 입력 후 root의 비밀번호 `a`를 입력 (현재 itbank의 접속이 유지된 상태로 root권한을 받음)
    > ### 다른 사용자 권한 회복 명령어 (일반적으로 관리자 권한 획득)
    > * `sudo`
    >   * 특정 명령어를 입력할 때 관리자의 권한을 획득해서 명령을 동작
    >   * sudo 명령어를 입력할 사용자 계정을 미리 등록
    >   * 획득한 권한은 해당 termina이 종료되면 폐기
    > * `su`
    >   * 현재 접속을 유지한 상태로 관리자로 추가 접속
    >   * `su` 명령어만 입력하면 환경 변수 등의 개인 설정들이 기존에 접속했던 사용자의 설정으로 유지 되고,  
    > `su -` 입력하면 관리자의 개인 설정을 사용한다. (한 칸 띄는 거 꼭 확인!)

## talnet 접속 끊기
```
exit
```
