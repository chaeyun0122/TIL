# 네트워크
## OSI 7 Layer 모델 & TCP/IP 모델
- 장치가 다른 장치와 통신을 할 때는 동일한 프로토콜(통신 규칙/약속)을 사용하고 있어야 한다.
  - 초창기에는 네트워크 장비를 생산하는 회사마다 고유의 프로토콜을 사용하여 서로 다른 회사의 장비와는 통신이 불가능했다. (점유율 싸움때문에)
  - 현재는 모든 장치가 서로 통신을 할 수 있도록 **표준 프로토콜**을 사용
- ISO(국제 표준화 기구)에서 Internet을 이용한 network 통신 *(= Internetworking)* 에 사용하기 위해 연구했던 표준 프로토콜의 집합이 OSI 7 Layer이다.
  - 연구 과정이 많이 지연되면서 기존에 사용하던 TCP/IP 모델이 인터넷의 표준 프로토콜로 사용
  - 너무 많이 사용 중이어서 OSI 7 Layer로 다 바꾸기가 쉽지 않다.
- **현재는 OSI 7 Layer 모델은 이론적인 내용을 표현하는 용도 (연구용/개발용/교육용)로 사용하고 실제 인터넷 통신에는 TCP/IP 모델을 사용하고 있다.**

### OSI 7 Layer
- 장치간의 통신을 하면서 데이터가 전달되는 과정을 세분화 시킨 계층적 모델
- 상위 계층(**L5 ~ L7, apllication**)과 하위 계층(**L1 ~ L4, data-flow**)으로 분류
#### 하위 계층
- **L4** : **Transport**
  - port 번호를 이용하여 서비스를 식별
  - TCP/UDP 등으 프로토콜이 존재
- **L3** : **Networ**
  - 서로 다른 네트워크 사이에서 packet 전송을 담당
  - 사용하는 프로토콜에 따른 Logical address(논리적 주소) 사용
  - IP 등의 프로토콜이 존재
- **L2** : **Data-link**
  - 동일 network 안에서 통신을 담당하는 계층
  - 사용하는 프로토콜에 따른 Physical address(물리적 주소) 사용. (프로토콜에 따라 주소가 없을 수 있다.
  - Ethernet 등의 프로토콜이 존재
- **L1** : **Physical**
  - 통신에 관련된 모든 물리적인 규칙을 정의 (ex. 신호 변환 방식, cable 규격, connector 규격, 전압 등)
  - 전송하려는 데이터를 signal(신호)로 변환 *(= Line coding : 이진수 데이터를 물리적인 신호로 변환)*

### Port
- Port 번호는 **0 ~ 65535** 범위 사용
  - 0 ~ 1023 : well-known port(잘 알려진 포트)라고 하여 자주 사용되는 서비스들이 고정적으로 사용하도록 예약
  - 그 외 : system이 임의로 사용 (+ 포트 포워딩에 사용)
    - http는 80번 포트를 쓰도록 전세계적으로 약속 되어있고, https는 443번 포트를 쓰도록 했다. 특정 사람들만 접속하는 사이트를 만드려면 임의의 포트를 입력해서 제공하도록 한다. 이것이 포트 포워딩이다.

### Logical address Layer
- Logical address Layer은 3계층에서 사용하는 프로토콜에 따라 달라진다.
  - IPv4 : IPv4 address(X.X.X.X)
  - IPv6 : IPv6 address(X:X:X:X:X:X:X:X)

#### IP address
- 인터넷 표준 프로토콜 TCP/IP 에서 사용되는 logical address(논리적 주소)
  - TCP/IP 표준 프로토콜을 사용하는 모든 장비들은 반드시 IP address가 할당되어 있어야 통신이 가능
- 운영체제(OS)가 인식한 interface마다 할당 *(interface : 네트워크 통신에 사용되는 선을 연결할 수 있는 소켓. 랜선 하나하나가 인터페이스라고 생각하면 됨)*
  - 사람이 직접 입력
  - DHCP 서버를 이용하여 자동 할당
- IP 주소는 InterNIC에서 관리하는게 기본. InterNIC에서 각 국가별로 분배해주고 국가에서는 ISP(통신업계)에 IP를 분배
  - 사용자는 ISP에 IP를 신청하여 할당
- 초창기에는 IPv4를 사용하다가 IP의 개수가 부족해지면서 1996년에 IPv6 제정
  - 기존의 장비들은 IPv6와 호환되지 않음. 기존에 만들어진 장비들과의 호환성을 위해 IPv4와 IPv6를 함께 사용(현재까지)
- **IPv4** : 32bit, 2^32 = 약 42억 9천만 개  
**IPv6** : 128bit, 2^128 개
##### IPv4 구성
- Network ID + Host ID
  - Network ID를 InterNIC/ISP 에서 할당, Host ID는 사용자가 network 범위 안에서 자유롭게 사용
- `192.168.10.128` → 여기서 Network ID와 Host ID를 구별 가능? **불가능**
  - netmask(subnetmask)를 이용하여 Network ID와 Host ID를 구별
  - Network ID의 bit 수 만큼 1을 나열, Host ID의 bit 수 만큼 0을 나열
    - ex)
      ```
      IP address : 192.168.10.128, netmask : 255.255.255.0
      IP address : 11000000.10101000.00001010.1000000
      netmask    : 11111111.11111111.11111111.0000000
                   |<----------------------->|<----->|
                            network ID        host ID
      ```
  - 동일한 Network ID를 사용하는 장치들은 논리적으로 같은 네트워크에 속해 있다.

- IPv4 address는 기본적으로 두 가지 방식으로 사용(Classful, Clasless)
  - **Classful**
    - IP주소의 첫 번째 8bit(loctet)의 범위에 따라 A ~ E class를 구분하고 class에 따라 Network ID 범위를 파악  
(IPv4에서 8bit = loctet, 4개 octet이 존재하고 loctet은 0 ~ 255를 2진수로 표현)
    - **Class A** (0 ~ 127) (00000000 ~ 011111111)
      - 첫 번째 bit가 0으로 고정된 범위
      - 앞의 8bit를 Network ID로, 뒤의 24bit를 Host ID로 사용
      - 하나의 network가 2^24 = **16,777,216**개의 IP를 포함 
    - **Class B** (128 ~ 191) (10000000 ~ 10111111)
      - 첫 번째 ~ 두 번째 bit가 10으로 고정된 범위
      - 앞 쪽의 16bit를 Network ID로, 뒤의 16bit를 Host ID로 사용
      - 하나의 network가 2^16 = **65,536**개의 IP를 포함
    - **Class C** (192 ~ 223) (11000000 ~ 11011111)
      - 첫 번째 ~ 세 번째 bit가 110으로 고정된 범위
      - 앞의 24bit를 Network ID로, 뒤의 8bit를 Host ID로 사용
      - 하나의 network가 2^8 = **256**개의 IP를 포함
    - **Class D, Class E**는 장치에 할당하여 사용하는 주소가 아니고 **특수 목적**으로 사용
    - 하지만 Classful 방식은 낭비가 심하기 때문에 현재 사용하지 않음
  - **Classless**
    - 필요에 따라 Class 네트워크를 분할하여 사용하는 방식
    - Classful 네트워크를 분할하는 작업을 subnetting이라고 한다.
      - 분할 된 네트워크를 subnetwork(subnet)이라고 부르고 netmask는 subnetmask로 바꿔서 부른다.
    - Classless 방식에서는 subnetmask의 표현이 정확해야 한다.
      - IP 주소의 첫 번째 8bit에 따라 Network ID를 결정하지 않는다.  
subnetmask 정보를 보고 Subnet ID *(= Network ID)* 의 bit의 개수를 파악
    - Subnetmak 계산하여 사용하는 대신에 Subnet ID의 bit 수를 그대로 적어주는 Prefix Length 표현 방식이 존재
      - ex)
        ```
        192.168.100.0 네트워크의 Subnet ID가 24bit인 경우
        
        255.255.255.0   -> 192.168.100.0/255.255.255.0
        /24             -> 192.168.100.0/24
        ```
    - Classful 방식과 Classless 방식 전부 각 네트워크의 Host ID 중 2개의 Host는 장치에 할당하지 않는다.
      - 첫 번째 IP는 해당 네트워크의 이름(= 대표주소, Network ID)으로 사용
      - 마지막 IP는 해당 네트워크의 방송 주소(= broadcast)로 사용 
        - broadcast : 1:n, 방송 주소, 특정 네트워크 내부에 전체 IP에다 동일한 내용을 전달  
unicast : 1:1, 장치와 장치간의 통신  
multicast : 1:n, 몇몇 장치를 그룹화 시켜서 Class D IP로 묶어준 후 그 IP에 데이터를 전달(그룹 통신이라고 부름)
        - VMware의 경우 첫 번째 IP와 마지막 IP를 동일하게 사용하지 못하고, 다른 IP를 추가로 사용하지 못한다.
          - ex)
            ```
            192.168.10.0/24 네트워크 일 때
            0     : Subnet ID (= Network ID)          >> Hostonly, NAT, bridge
            1     : (Host PC) virtual network adapter >> Hostonly, NAT
            2     : gateway (외부로 나가는 문)         >> NAT
            255   : broadcast                         >> Hostonly, NAT, bridge
            ```
          - 1번 확인 : `window 실행창 > ncpa.cpl` 네트워크 연결 콘솔  
          
            ![image](https://user-images.githubusercontent.com/79209568/117425533-d11ba000-af5d-11eb-84da-e9dbc2f309ee.png)
          - 2번 확인 : VMware `Edit > Virtual Network Editor > NAT 클릭 > NAT Setting`  
          
            ![image](https://user-images.githubusercontent.com/79209568/117426159-8baba280-af5e-11eb-89e8-b00379429f7c.png)


### NAT
- NAT : Network Address Translation : 네트워크 주소 변환(네트워크 주소 = IP 주소)
- IPv4 의 문제점 중 하나인 주소 부족현상을 해결하기 위해 90년대 후반에 만들어짐
- 내부 네트워크와 외부 네트워크를 분리해서 사용 (보안성이 높음)
  - 외부 네트워크 : 공인 네트워크(public network), 공인 IP 주소 사용
  - 내부 네트워크 : 사설 네트워크(private network), 사설 IP 주소 사용
- 공인 IP 주소/ 사설 IP 주소 구분
  - 공인 IP 주소 : 유료, ISP에서 제공, 외부 네트워크와 직접적인 통신 가능
  - 사설 IP 주소 ; 무료, 내 마음대로 사용, 외부 네트워크와 직접적인 통신 불가능
    - 사설 IP 범위
      - Class A : 10.0.0.0/8 (10.0.0.0 ~ 10.255.255.255) 10으로 시작하는 모든 IP
      - Class B : 172.16.0.0/12 (172.16.0.0 ~ 172.31.255.255)
      - Class C : 192.168.0.0/16 (192.168.0.0 ~ 192.168.255.255)
- NAT는 보안 목적으로 사용하는 Basic NAT와 IP주소 절약 목적으로 사용하는 NAPT로 분류
 - 공유기기에서NAPT가 동작, NAPT를 일반적으로 NAT라고 부른다.
   - NAPT : Network Address Port Translation

### Physical address Layer
- Physical address Layer은 2계층에서 사용하는 프로토콜에 따라 달라진다.
  - LAN protocol(Ethernet) : MAC address (XX-XX-XX-XX-XX-XX)

#### MAC address
- LAN 구간 Protocol에서 사용되는 Layer 2 주소 (Physical address, 물리적 주소라고도 표현)
- Ethernet 방식의 Interface에 부여
  - 기본적으로 전 세계에 하나밖에 없는 고유한 MAC address 할당
- 장비 생산 과정에서 부여되는 주소
  - 총 48bit(2진수 48자리 = 16진수 12자리)의 이진수로 구성되어 있다
    - 전반부 24bit는 **벤더 ID** (회사 고유 ID)
    - 후반부 24bit는 **Host ID** (장치 고유 ID)
- 벤더 ID는 **IEEE**에서 관리
  - 네트워크 장비 생산 회사는 IEEE에 일정 금액을 지불하고 벤터 ID를 할당받아 사용

# 네트워크 관련 명령어
## ifconfig
  - Interface CONFIGuraion : interface의 정보 확인 및 제어
  - CentoS 6.X 버전까지는 시스템에 기본적으로 탑재되 명령어였으나 CentOS 7.0버전 부터 추가로 설치해야 사용가능  
(현재는 ip addr 명령어가 기본 탑재, net-tools 패키지 설치 후 ifconfig 명령어 사용가능)
### 사용방법
* `ifconfig` : 모든 interface의 정보를 출력  
  
  ![image](https://user-images.githubusercontent.com/79209568/117428581-2d33f380-af61-11eb-8429-19e25183c1b0.png)

* `ifconfig <Interface 이름>` : 해당 interface의 정보만 출력  
  
  ![image](https://user-images.githubusercontent.com/79209568/117428611-32913e00-af61-11eb-89b1-850ba1d9df59.png)









