
# 네트워크 관련 명령어
## ifconfig
  - Interface CONFIGuraion : interface의 정보 확인 및 제어
  - CentoS 6.X 버전까지는 시스템에 기본적으로 탑재되 명령어였으나 CentOS 7.0버전 부터 추가로 설치해야 사용가능  
(현재는 ip addr 명령어가 기본 탑재, net-tools 패키지 설치 후 ifconfig 명령어 사용가능)
### 사용방법
* 모든 interface의 정보를 출력  
  ```
  ifconfig
  ```
  ![image](https://user-images.githubusercontent.com/79209568/117428581-2d33f380-af61-11eb-8429-19e25183c1b0.png)

* 해당 interface의 정보만 출력  
  ```
  ifconfig <Interface 이름>
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/117428611-32913e00-af61-11eb-89b1-850ba1d9df59.png)

* 해당 interface 상태 비활성화
  ```
  ifconfig <Interface 이름> down
  ```
  * PuTTY 는 반응 없고 VMware의 터미널 가서 확인하면 `RUNNING`이 사라진 것을 확인  
    ![image](https://user-images.githubusercontent.com/79209568/117617574-b3da1200-b1a7-11eb-96ea-2d36e2b3f340.png)

* 해당 interface 상태 활성화
  ```
  ifconfig <Interface 이름> up
  ```
  * 터미널에서 up 시키고 PuTTY에서 엔터치면 다시 동작함  
    ![image](https://user-images.githubusercontent.com/79209568/117617754-f7cd1700-b1a7-11eb-9a21-8128ed9c50c3.png)

* interface에서 현재 사용중인 IP 변경(일시적인 변경, 설정은 변경 X)
  ```
  ifconfig <Interface 이름> <IP address> netmask <subnetmask>
  ```
  * PuTTY에 `ifconfig ens32 192.168.217.200 mask 255.255.255.0` 입력 후 엔터치면 반응 없어지고 VMware 터미널 에서 `ifconfig ens32`를 치면 일시적으로 IP주소가 변경된다.  
  ![image](https://user-images.githubusercontent.com/79209568/117618413-de789a80-b1a8-11eb-8acb-e64e0c93f54d.png)

  * `systemctl restart network`로 다시 시작
    * network [데몬](#데몬-프로세스)을 재설정하여 설정 내용을 다시 load  
  ![image](https://user-images.githubusercontent.com/79209568/117619110-d0774980-b1a9-11eb-8183-f56488c3d1f6.png)

## netstat
* 네트워크 접속 여부나 대기 상태, 네트워크 인터페이스의 통계 정보 등을 확인하는 명령
### 사용법
```
netstat [옵션] | grep { port번호 | service }`
```
> #### 옵션
> * `-a` : 모든 저보 출력
> * `-t` : tcp를 사용하는 정보만 출력
> * `-n` : port번호 출력
> * `-p` : 프로그램 이름 + PID
> * `-l` : listen 상태 (연결 대기 중인 상태)만 출력
  
![image](https://user-images.githubusercontent.com/79209568/117621170-45e41980-b1ac-11eb-8455-885a9863057f.png)

## ping
* 내 장치와 목적지 장치간의 네트워크 통신 상태 확인
* 기본적으로 횟수 제한이 없음 (무한정 지속) 윈도우에선 4번으로 제한되어있음
### 사용법
```
ping [옵션] <목적지 IP 주소>
```
> #### 옵션
> * `-c` : 횟수 지정
>   * 횟수 지정하지 않으면 무제한으로 나온다. 종료 하고 싶을 때 `Ctrl + c`
> * `-i` : 간격 지정 (초 단위), 0.5 미만은 관리자만 사용 가능
> * `-s  : 크기 지정, ping은 정해진 크기까지 의미없는 데이터를 집어넣는다.

* c 옵션으로 횟수 지정
  ```
  ping -c 5 192.168.217.128
  ```
  ![image](https://user-images.githubusercontent.com/79209568/117623532-e2a7b680-b1ae-11eb-82a5-6361e1b687c4.png)

* i 옵션으로 속도 지정
  ```
  ping -c 5 -i 0.1 192.168.217.128
  ```
  * 빠르게 끝남

* s 옵션으로 크기 지정
  ```
  ping -c 5 -s 1000 192.168.217.128
  ```
  ![image](https://user-images.githubusercontent.com/79209568/117624239-ac1e6b80-b1af-11eb-8320-d9c92878a97d.png)

## traceroute
* 내 장치의 목적지 IP 사이에 몇 개의 네트워크 (=router)가 있는지 확인
* 최대 30 hop 까지 확인이 가능하지만 기본적으로 전 세계 어디에 있어도 20개를 초과하는 일이 거의 없다.  
(단, 중간에 이쓴 router가 자신의 정보를 아렬주지 않거나, 통신이 안되면 hop 추가)
### 사용법
```
traceroute <목적지 IP>
```

* `traceroute www.google.com`을 입력해본다.
  * 가상 머신은 이 기능이 작동하지 않는다. (그래도 30까지 출력한 후 멈춤)
  ![image](https://user-images.githubusercontent.com/79209568/117625088-9c535700-b1b0-11eb-98e9-3e7deffeb501.png)

* cmd에서 `tracert www.google.com`을 입력한다.
  * 구글 사이트까지 14개의 네트워크를 지나서 도착하는 것을 확인할 수 있다.
  ![image](https://user-images.githubusercontent.com/79209568/117625257-cd338c00-b1b0-11eb-8aa6-2ce697920a33.png)


> ### 데몬 프로세스
> * 서비스의 제어를 담당하는 프로세스
> * 데몬이 실행되면서 서비스의 설정파일을 읽고 이상이 없으면 서비스를 동작시키는 방식
>   * 기본적으로 데몬은 수동 실행, 설정에 따라 부팅 시 자동으로 실행이 가능
> #### 데몬 프로세스 제어
>   * `systemctl { start | stop | restart | status | enable | disable } <데몬명>`
>   * start : 실행
>   * stop : 종료
>   * restart : 재실행(종료 후 실행, 종료 상태에서도 사용 가능)
>   * status  : 상태 확인
>   * enable : 부팅 시 데몬 자동으로 실행
>   * disable : enable 해제

