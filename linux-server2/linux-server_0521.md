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
* Domain Namespace 구조
  * 효율적인 주소의 관리를 위해 root domain에서 top-level domain으로, top-level domain에서 second-level domain으로 위임처리를 하고,  
일반적으로 second-level domain에서 목적지 FQDN의 IP를 확인
