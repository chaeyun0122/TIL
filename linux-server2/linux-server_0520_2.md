# DHCP
- Dynamic Host Configuration Protocol
- 자동으로 host에게 IP 주소를 할당하는 기능
- client는 관리자가 IP를 직접 부여하지 않고 DHCP 서버에서 할당을 받으면서 IP를 중복 사용하거나 잘못된 설정 등의 문제를 피할 수 있다.
- 동작 순서(사용되는 패킷 종류) → DORA 패킷을 주고 받아 IP 할당
  - client에 IP가 없는 상태로 진행
  > - Discover : client → broadcast, 네트워크의 dhcp 서버를 찾는 패킷
  > - Offer : server → broadcast, discover 패킷을 받은 server가 자신이 줄 수 있는 IP를 알려주는 패킷
  >   - server의 DHCP 데이터베이스에는 해당 IP를 임시할당 상태로 전환
  > - Request : client → broadcast, 제일 먼저 받은 offer를 사용하겠다라고 알려주는 패킷
  > - Ack : server → broadcast, client가 선택한 offer를 보낸 server는 IP정보 및 옵션들을 전송
  >   - offer를 보냈지만 선택받지 못한 server는 임시할당 상태인 IP를 미할당 상태로 전환
  - ack 패킷을 받은 client는 패킷 내용대로 IP정보를 설정 후 IP통신 진행
- client가 할당받은 IP는 영구 적용 X
  - 임대 기간이 존재, 기간 만료 전 갱신 작업을 통하여 기존 IP를 계속 사용

## 관련 정보
- 패키지 : dhcp-*
- 데몬 : dhcpd
- 방화벽 : service=dhcp
- 설정파일 : /etc/dhcp/dhcpd.conf

## vmware DHCP 설정
* `Edit > Virtual Network Editor > Change settings`
  
  ![image](https://user-images.githubusercontent.com/79209568/118942750-f9f45a00-b98d-11eb-9ade-1c4372be3186.png)

## DHCP 설치
```
yum -y install dhcp-*
```
## DHCP 설정파일
> ### `vi /etc/dhcp/dhcpd.conf`

* 추가
```
subnet 192.168.217.0 netmask 255.255.255.0 {
  range dynamic-bootp 192.168.217.201 192.168.217.220;
  option subnet-mask 255.255.255.0;
  option routers 192.168.217.2;
  option domain-name-servers 192.168.217.2;
  default-lease-time 345600;
  max-lease-time 691200;
}
```
```
어떤 네트워크의 아이피를 분배할 것인지 설정(해당 설정은 서브넷쪽에 들어간다)
{ //적용되는 설정들
  분배할 IP주소 : 시작주소와 끝주소 입력 (20개 아이피를 분배하겠다는 뜻)
  클라이언트 ip 설정에 들어갈 서브넷 마스크
  게이트웨이 설정
  dns ip 설정
  갱신 시간(초단위-4일)
  최대 사용 기간(갱신을 실패했을 때 최대 언제까지 사용할 것인지 - 8일). 이후에 ip 정보를 버리고 다시 DHCP 서버를 찾는다
}
```
* 데몬 재실행
  ```
  systemctl restart dhcpd
  ```
### 확인
* client 접속
* 네트워크 설정 > IP설정
  * 자동으로 변경, 네임서버 지우고 적용
  
  ![image](https://user-images.githubusercontent.com/79209568/118946102-0b8b3100-b991-11eb-9f59-2945bae26165.png)
* 스위치 껐다 키고 잠시 후 확인하면 범위내의 아이피를 받아옴
  
  ![image](https://user-images.githubusercontent.com/79209568/118946218-21005b00-b991-11eb-8a7e-a782e95cac35.png)
* IP 할당 확인 : `/var/lib/dhcpd/dhcpd.leases`
  # DHCP
- Dynamic Host Configuration Protocol
- 자동으로 host에게 IP 주소를 할당하는 기능
- client는 관리자가 IP를 직접 부여하지 않고 DHCP 서버에서 할당을 받으면서 IP를 중복 사용하거나 잘못된 설정 등의 문제를 피할 수 있다.
- 동작 순서(사용되는 패킷 종류) → DORA 패킷을 주고 받아 IP 할당
  - client에 IP가 없는 상태로 진행
  > - Discover : client → broadcast, 네트워크의 dhcp 서버를 찾는 패킷
  > - Offer : server → broadcast, discover 패킷을 받은 server가 자신이 줄 수 있는 IP를 알려주는 패킷
  >   - server의 DHCP 데이터베이스에는 해당 IP를 임시할당 상태로 전환
  > - Request : client → broadcast, 제일 먼저 받은 offer를 사용하겠다라고 알려주는 패킷
  > - Ack : server → broadcast, client가 선택한 offer를 보낸 server는 IP정보 및 옵션들을 전송
  >   - offer를 보냈지만 선택받지 못한 server는 임시할당 상태인 IP를 미할당 상태로 전환
  - ack 패킷을 받은 client는 패킷 내용대로 IP정보를 설정 후 IP통신 진행
- client가 할당받은 IP는 영구 적용 X
  - 임대 기간이 존재, 기간 만료 전 갱신 작업을 통하여 기존 IP를 계속 사용

## 관련 정보
- 패키지 : dhcp-*
- 데몬 : dhcpd
- 방화벽 : service=dhcp
- 설정파일 : /etc/dhcp/dhcpd.conf

## vmware DHCP 설정
* `Edit > Virtual Network Editor > Change settings`
  
  ![image](https://user-images.githubusercontent.com/79209568/118942750-f9f45a00-b98d-11eb-9ade-1c4372be3186.png)

## DHCP 설치
```
yum -y install dhcp-*
```
## DHCP 설정파일
> ### `vi /etc/dhcp/dhcpd.conf`

* 추가
```
subnet 192.168.217.0 netmask 255.255.255.0 {
  range dynamic-bootp 192.168.217.201 192.168.217.220;
  option subnet-mask 255.255.255.0;
  option routers 192.168.217.2;
  option domain-name-servers 192.168.217.2;
  default-lease-time 345600;
  max-lease-time 691200;
}
```
```
어떤 네트워크의 아이피를 분배할 것인지 설정(해당 설정은 서브넷쪽에 들어간다)
{ //적용되는 설정들
  분배할 IP주소 : 시작주소와 끝주소 입력 (20개 아이피를 분배하겠다는 뜻)
  클라이언트 ip 설정에 들어갈 서브넷 마스크
  게이트웨이 설정
  dns ip 설정
  갱신 시간(초단위-4일)
  최대 사용 기간(갱신을 실패했을 때 최대 언제까지 사용할 것인지 - 8일). 이후에 ip 정보를 버리고 다시 DHCP 서버를 찾는다
}
```
* 데몬 재실행
  ```
  systemctl restart dhcpd
  ```
### 확인
* client 접속
* 네트워크 설정 > IP설정
  * 자동으로 변경, 네임서버 지우고 적용
  
  ![image](https://user-images.githubusercontent.com/79209568/118946102-0b8b3100-b991-11eb-9f59-2945bae26165.png)
* 스위치 껐다 키고 잠시 후 확인하면 범위내의 아이피를 받아옴
  
  ![image](https://user-images.githubusercontent.com/79209568/118946218-21005b00-b991-11eb-8a7e-a782e95cac35.png)
* IP 할당 확인 : `/var/lib/dhcpd/dhcpd.leases`
  
  ![image](https://user-images.githubusercontent.com/79209568/118948737-87867880-b993-11eb-9607-9493acd7aebf.png)

## 실습
```
전체 가상머신 스냅샷 되돌린 후 진행
1.  범위      : 192.168.X.0/24
    할당 IP   : 51~60
    GW       : 192.168.X.2
    DWS       : 192.168.X.2
    최소일대시간  : 3일
    최대일대시간  : 6일
    
    위의 설정을 갖고 있는 DHCP 서버를 재부팅 후에도 동작 가능하도록 설정
    client에서 IP 할당 받아보기

2. windows에서 client의 공유 폴더 접근하여 메모장 파일 만들어보기
  (단, 접손은 testuser로 진행)
```


