# DNS
* Domain Name Service / Domain Name System
* 인터넷에 존재하는 무수히 많은 서버들을 사람이 쉽게 기억할 수 있도록 문자주소(FQDN)를 이용해서 접근할 수 있도록 변환 해주는 서버
  * 문자주소(FQDN) ←→ IP address 상호 변환
* InterNIC에서 관리하는 호스트들의 데이터베이스 역할을 하는 Domain Namespace라는 트리구조에 맞춰서 구성
  * DNS 서버의 역할은 등록된 서버의 IP를 알아온다.
* FQDN은 일반적으로 말하는 도메인 주소를 의미
  * host anme과 domain name으로 구성되어 있다. 
  * domain name은 일반적으로 하나의 네트워크와 매핑, host name은 하나의 장치와 매핑  
ex) `www.google.com` 이라는 주소가 있다면 `www` 가 host name, `google.com`이 domain name이 되고,  
`www`는 웹서버 장치에 매핑, `drive`는 구글 드라이브 서버 장치에 매핑하는 방식으로 사용
  * 영문자 및 숫자를 사용한 이름으로 자원에 대한 접근이 가능
### Domain Namespace 구조
  * 효율적인 주소의 관리를 위해 `root domain`에서 `top-level domain`으로, `top-level domai`n에서 `second-level domain`으로 위임처리를 하고,  
일반적으로 `second-level domain`에서 목적지 FQDN의 IP를 확인
 * 구조 예시
   ```
            - com  -------- google          - www
   .  ------- net         - naver   --------- cafe
            - org         - nate            - blog
           
   root      top-level    second-level       host
   domain    domain       domain             name
   ```
#### root domain
* Internet에서 관리하는 Domain Namespace의 최상위 서버
* 전 세계에 총 13개가 있고, 각각 a ~ m 까지 이름이 붙어있다.
#### top-level domain
* FQDN의 마지막 단락 (ex. com, net, org 등) 
* 해당 서버의 이름으로 끝나는 모든 Name Server들의 IP를 가지고 있다.
#### second-level domain
* 일반적으로 각 회사에서 관리하는 Name Server host name의 정보를 record로 관리 
* ex. google.com, nate.com 등
#### host name
* second-level domain ~ root domain 까지 연결되어 만들어지는 domain name 앞에 붙는 문자 주소
* 해당 host name이 붙어서 만들어지는 FQDN이 어느 IP로 연결되어야 하는지 정보를 가지고 있다.

### DNS server의 역할
#### IP address의 FQDN을 상호 변환
1. client 장치를 사용하는 사용자가 FQDN을 입력하면 client는 자신의 cache를 확인하여 FQDN에 대한 IP를 알고 있다면 해당 IP로 바로 연결
2. FQDN에 대한 IP주소가 없다면 IP설정에 등록된 DNS server에게 FQDN에 대한 IP주소를 요청
3. DNS server는 자신의 zone 영역을 확인하여 해당 FQDN에 대한 IP주소가 있다면 client에게 전송  
없다면 cache를 확인하여 기존에 해당 FQND에 대한 IP주소를 알아온 적이 있는지 확인
4. cache에 있다면 해당 IP 주소를 확인하여 client에게 전송  
cache에 없다면 root hint를 확인하여 root domain에 해당 FQDN의 IP를 확인
5. root domain 은 FQDN의 top-level domain을 확인하여 해당 서버의 IP를 DNS server에게 전송  
DNS server는 다시 top-level domain에 찾아가서 FQDN의 IP를 확인  
6. top-level domain은 FQDN의 second-level domain을 확인하여 해당 서버의 IP를 DNS server에게 전송  
DNS server는 second-level domain에 찾아가서 FQDN의 IP를 확인
7. second-level domain에서는 FQDN의 host name이 자신에게 있는지 확인하고 해당 IP주소를 DNS server에게 전송
8. DNS server는 IP주소를 알아온 과정을 cache에 저장 후 client에게 알아온 IP를 전송
9. client는 자신의 cache 에 IP를 저장 후 해당 IP로 연결
* 1 ~ 9 과정을 거쳐서 FQDN을 IP주소로 변환 (DNS server는 3 ~ 8 까지를 담당)
#### Name Server 구성
* DNS server를 설처히면 name server를 구성할 수 있는 (= zone 여역을 만들 수 있는) 파일이 같이 설치된다.
* root domain, top-level domain, second-level domain 전부 name server이다.  
(실습에서는 second-level domain을 생성할 것)

## DNS 관련 정보
- 패키지 : bind-*
- 데몬 : named
- 방화벽 : port=53/tcp, service=dns
- 설정파일 : /etc/named.conf

* `/etc/hosts` : DNS cache를 확인하기 이전에 먼저 확인하게 되는 파일
  
  ![image](https://user-images.githubusercontent.com/79209568/119106711-8ddf2800-ba59-11eb-93f6-8d0079fb8ccb.png)
  * 뒤에 있는 문자주소가 컴퓨터에 입력되면 앞에 있는 IP주소로 연결을 한다는 뜻
  * firefox 열기

    ![image](https://user-images.githubusercontent.com/79209568/119107099-e9111a80-ba59-11eb-89bb-353bc977d773.png)
  * www.naver.com 으로 들어가기
  * `메뉴 > 환경설정 > 개인 정보 및 보안 > 쿠키와 사이트 데이터` 데이터 삭제

    ![image](https://user-images.githubusercontent.com/79209568/119107476-38efe180-ba5a-11eb-8da3-f33e48aec5d6.png)
  * 다시 그 파일로 가서 내 IP 주소와 네이버 주소 입력
    
    ![image](https://user-images.githubusercontent.com/79209568/119107772-84a28b00-ba5a-11eb-8898-d849cbd0bf01.png)
  * 네이버 연결 시 연결되지 않음 → 네이버 주소가 내 IP로 연결되도록 설정했기 때문에
    
    ![image](https://user-images.githubusercontent.com/79209568/119108039-c0d5eb80-ba5a-11eb-9cfe-fc9c41309626.png)
* `/etc/resolv.conf` : 클라이언트 역할의 파일
  * cache에 없는 주소가 들어오면 위에서 아래로 순서대로 DNS가 있는지 찾음
  ![image](https://user-images.githubusercontent.com/79209568/119110422-1ad7b080-ba5d-11eb-90a9-b7fd7b0cd233.png)

  * 필요하다면 직접 작성도 가능
    ```
    nameserver 168.126.63.1  // kt에서 운영중인 DNS 서버
    nameserver 8.8.8.8       // 구글에서 운영중인 DNS 서버
    ```
  * 네트워크 데몬 재실행하면 추가한 DNS 서버들이 사라지고 원래대로 돌아옴
    ```
    systemctl restart network
    ```
    ![image](https://user-images.githubusercontent.com/79209568/119111118-ba953e80-ba5d-11eb-852f-f3f790b9c84c.png)

## DNS server 관련 파일 설치
```
yum -y install bind-*
```

## DNS 설정 파일
> ### `vi /etc/named.conf`

* 주석이 `#`이 아닌 `//`로 들어가있음
### options
![image](https://user-images.githubusercontent.com/79209568/119113764-6475ca80-ba60-11eb-807b-af440a740734.png)

* `listen-on port 53`를 `any`로 변경  
  ![image](https://user-images.githubusercontent.com/79209568/119112290-e533c700-ba5e-11eb-95ce-478f63423eb0.png)
* `allow-query`를 `any`로 변경 (웬만하면 `listen-on port 53`와 맞춤)  
  ![image](https://user-images.githubusercontent.com/79209568/119112869-815dce00-ba5f-11eb-8660-f67b5b6d5e41.png)
### logging
![image](https://user-images.githubusercontent.com/79209568/119113744-5f188000-ba60-11eb-8aef-2229e6234faa.png)

### zone
![image](https://user-images.githubusercontent.com/79209568/119113720-5aec6280-ba60-11eb-8a39-fccc346cde91.png)

* 루트 힌트 영역
* 모든 DNS서버는 전세계의 IP 주소를 가지고 있는 파일이 있다. 

### include
![image](https://user-images.githubusercontent.com/79209568/119114406-1b724600-ba61-11eb-8cdd-765db6e71c46.png)

* named 데몬이 실행될 때 named.conf 파일을 체크하는데 해당 include된 파일들도 함께 체크한다.
