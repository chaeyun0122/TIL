# 리눅스의 파일 시스템 및 계층적 구조
## 파일 시스템(file system)이란?
* 컴퓨터에서 파일이나 자료를 쉽게 발견 및 접근할 수 있도록 보관 또는 조직하는 체제

## 계층적 구조
![image](https://user-images.githubusercontent.com/79209568/113513239-a37cb900-95a3-11eb-990e-e8da9b74a57f.png)
### /
* **최상위 루트 디렉터리 또는 최상위 디렉터리**
* 리눅스의 모든 디렉터리의 시작점(꼭대기)

### /bin(바로가기파일) → /usr/bin(원본)
* **기본 명령어**가 들어있는 디렉터리
* 일반 사용자 계정과 관리자가 사용

### /boot
* 리눅스 **부트로더(bootloder)** 가 들어있는 디렉터리
* 이 디렉터리에 오류가 발생하면 리눅스를 부팅할 수 없음

### /dev
* **장치파일**이 들어있는 디렉터리

### /etc
* **시스템의 거의 모든 서비스**와 설정파일이 들어있는 디렉터리

### /home
* **사용자 계정의 홈 디렉터리**가 들어있는 디렉터리

### /lib(바로가기파일) → /usr/lib(원본)
* **커널, 모듈, 라이브러리**파일들이 들어있는 디렉터리

### /media
* **로컬 장치파일**이 사용되는 경로
* ex) cd-rom, usb 등

### /mnt
* **원격 장치파일**이 사용되는 경로
* ex) bluetooth, wifi 등

### /opt
* **추가한 소프트웨어, 설치한 파일**들이 들어있는 디렉터리

### /proc
* 커널과 프로세스를 위한 **가상 파일 시스템**이 들어있는 디렉터리

### /root
* **root(관리자)의 홈** 디렉터리

### /run
* **계속 실행되고 있는 프로세스** 관련 파일들이 들어있는 디렉터리

### /sbin(바로가기파일) → /usr/sbin(원본)
* **시스템 관련 명령어**가 들어있는 디렉터리
* 관리자만 사용

### /srv
* **시스템이 제공하는 서비스**를 위한 파일들이 들어있는 디렉터리

### /sys
* **시스템이 필요로하는 파일들**이 들어있는 디렉터리

### /tmp
* 공용 디렉터리
* 임시 저장소 : 서비스 사용시 관련 파일이 잠시 보관되었다가 시스템을 재부팅하면 사라짐

### /usr
* 시스템이 아닌 **사용자 계정 또는 관리자가 사용하는 서비스를 설치**하는 경로
* 크기가 크거나 자주 사용하지 않는 것들이 들어있음

### /var
* 시스템 운영 중에 **기록이 저장되는 파일**이 들어있는 디렉터리
* 로그 파일, 사용자 계정의 메일 파일 등

### /swap
* **가상 메모리**(마치 파티션을 진자 메모리처럼 인식하게 함)
  * 4GB 이하는 RAM의 2배
  * 4GB 이상은 RAM 그대로

# 프롬프트 (command prompt)
> 컴퓨터가 입력을 기다리고 있음을 가리키기 위해 화면에 나타내는 표시
* Windows 형식
  * 드라이브:\경로명
  * ex)  C:\Linux
* Linux 형식
  * [계정명@서버명 현재경로]#|$
  * [] : bash라고 함
  * \# : 관리자일 경우
  * $ : 일반사용자일 경우
  * ex) [root@localhost~]#
* Unix 형식 : bash가 없음


