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
