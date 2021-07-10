## 빅데이터
- 서버 한 대로 처리할 수 없는 규모의 데이터
- 기존의 소프트웨어로는 처리할 수 없는 규모의 데이터
- 3가지 요소를 만족해야함.
  - Volumn, Velocity, Variety
## 하둡이란?
- **대용량의 데이터를 분산처리를 위해 개발 된 오픈소스 소프트웨어**
### 하둡 에코 시스템
- 하둡의 핵심 구성 요소
  - 분산데이터를 저장하는 **HDFS**
  - 분석데이터를 처리하는 **맵리듀스**

- 하둡의 장점 : 하둡은 대용량 데이터 분산처리 프레임웍이다.
  - 스케일아웃(Scale-out) : 리소스를 늘리는 것이 아니라 서버를 추가하여 처리한다. 대용량 처리를 할 때 필요함  
(스케일업(Scale-up) : 오라클에서 데이터 처리를 할 때 메모리를 늘리고 CPU를 장착하는 등 리소스를 추가하는 것)

<hr>

# 환경 설정
## vmware 설치
![image](https://user-images.githubusercontent.com/79209568/125154370-aaaff600-e194-11eb-8958-d84700cfa0a1.png)

## CentOS 7 iso 파일 다운로드
- 다운로드 후 workspace에 옮기기

## 리눅스 설치
### Master
- 새로운 Virtual Machine 생성
- next 누르다가 Linux, CentOS 7 64-bit를 선택 후 next
  
  ![image](https://user-images.githubusercontent.com/79209568/125154704-45f59b00-e196-11eb-9951-3178e01003fa.png)
- workspace에 master, slave1, slave2 폴더를 생성
  
  ![image](https://user-images.githubusercontent.com/79209568/125154729-6de4fe80-e196-11eb-9f40-c270aac77dda.png)
- 가상머신 이름 설정을 CentOS 7 master로 하고 위치를 workspace에 만들어놓은 master로 설정 후 next
- finish 후 edit virtual machine settings에서 메모리를 2GB로 설정
- CD/DVD에서 Use ISO image file을 아까 다운로드 한 CentOS 7 ISO 파일로 설정 후 OK
  
  ![image](https://user-images.githubusercontent.com/79209568/125154786-d338ef80-e196-11eb-8be6-79c83be7d28a.png)

- 한국어 설정 후 다음
- 소프트웨어 선택 - 서버 GUI 선택 후 확인
- 설치 대상 - 바로 확인
- 네트워크 및 호스트명 - 이더넷 연결 토글을 킨 후 확인  
  
![image](https://user-images.githubusercontent.com/79209568/125154986-edbf9880-e197-11eb-87c3-1a74cb0a5fbf.png)

- 설치 시작 클릭
- Root 암호 : master 
  
  ![image](https://user-images.githubusercontent.com/79209568/125155254-77239a80-e199-11eb-9030-cab370f52bdf.png)
- 사용자 생성 : hadoop / hadoop
  
  ![image](https://user-images.githubusercontent.com/79209568/125155262-8571b680-e199-11eb-8101-da3298c5e5d8.png)
- 완료되면 재부팅
- 라이센스 동의 후 설정 완료 클릭
- hadoop 사용자로 로그인
- 완료
  ![image](https://user-images.githubusercontent.com/79209568/125156463-865a1680-e1a0-11eb-9bfa-c76e2d9747fa.png)


### Slave1
- master 가상머신과 앞 부분은 같음
- **소프트웨어 선택** - 인프라 서버
- 나머지 다 같음

### Slave2
- slave1과 같음

<hr>
## 한글 설정
- 오른 쪽 위 클릭 후 설정 버튼 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/125157032-91627600-e1a3-11eb-88e2-d73ad2ec681f.png)

- 지역 및 언어
- `+` 눌러서 영어 선택 후 추가
  
  ![image](https://user-images.githubusercontent.com/79209568/125157013-76900180-e1a3-11eb-83b9-299cf1d80123.png)
- 원래 있던 한국어를 `-`로 삭제 후 `한국어 (Hangul)`로 선택 후 추가
  
  ![image](https://user-images.githubusercontent.com/79209568/125157027-8c052b80-e1a3-11eb-8edc-0c2c595e1cd2.png)

## 유선 네트워크 고정 아이피 설정
### master
- 유선 네트워크 설정 선택
  
  ![image](https://user-images.githubusercontent.com/79209568/125157043-9e7f6500-e1a3-11eb-898c-97b9120743e5.png)
- 아이피 주소 확인 후 설정 버튼 클릭
  
  ![image](https://user-images.githubusercontent.com/79209568/125157052-a939fa00-e1a3-11eb-8f7a-8e2819b356e3.png)
- IPv4 탭으로 들어가서 수동으로 설정 후 주소, 네트마스크, 게이트웨이를 각각 설정 후 적용
  
  ![image](https://user-images.githubusercontent.com/79209568/125157066-c078e780-e1a3-11eb-8ebf-9d5b766a288d.png)

### slave
- slave1에서 hadoop으로 로그인
  
  ![image](https://user-images.githubusercontent.com/79209568/125157084-d5557b00-e1a3-11eb-95f2-e7750e4e1c37.png)
- `su -`로 관리자로 로그인 가능
  
  ![image](https://user-images.githubusercontent.com/79209568/125157095-e4d4c400-e1a3-11eb-871b-4a5a52701e27.png)
- `ifconfig`로 현재 아이피 확인
  
  ![image](https://user-images.githubusercontent.com/79209568/125157111-f1f1b300-e1a3-11eb-8c47-4efaba9a5b7a.png)
- `vi /etc/sysconfig/network-scripts/ifcfg-ens33` vi 에디터로 해당 파일 열기
- BOOTPROTO를 `static`으로 변경, IPADDR, NETMASK, GATEWAY를 각각 입력 후 저장 (:wq)
  
  ![image](https://user-images.githubusercontent.com/79209568/125157459-0f278100-e1a6-11eb-8517-9b7baf3bfbd6.png)
- `systemctl restart network`로 적용
- slave2도 동일하게 설정

### 확인방법
- `ping IP주소`했을 때 핑이 잘 나가면 설정이 잘 된 것이다.

## 호스트네임 변경
### master
- `su -`로 루트 로그인
- `vi /etc/hostname` : 내용 지우고 master라고 쓴 후 저장
- `vi /etc/hosts` : 아이피 등록과 백업 등록
  
  ![image](https://user-images.githubusercontent.com/79209568/125157832-15b6f800-e1a8-11eb-8e51-d314270b8fe2.png)
### slave
- slave1, slave2도 hostname을 각각 변경해주고, hosts의 내용은 master에서 썼던 것과 동일하게 작성 후 저장해준다.

### 확인방법
- `ping master`, `ping slave1`, `ping backup`을 했을 때 핑이 잘 나가면 설정이 잘 된 것이다.
- slave2에서 해 본 결과
  
  ![image](https://user-images.githubusercontent.com/79209568/125158127-dd181e00-e1a9-11eb-9047-6d9c6c096f10.png)

## 크롬 다운로드(필요한 파일 다운로드를 위한)
```
su –
cd /usr/local/
wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm
sudo yum localinstall google-chrome-stable_current_x86_64.rpm
```
![image](https://user-images.githubusercontent.com/79209568/125158297-18671c80-e1ab-11eb-95bf-6a1311d7ca40.png)

## 필요파일 다운로드
### 자바 다운로드
- 크롬에서 `java.oracle.com`
- Java SE 8 버전의 `Linux x64 Compressed Archive`를 다운로드한다.
  ![image](https://user-images.githubusercontent.com/79209568/125159106-40a54a00-e1b0-11eb-8d55-1c42a0e4f93e.png)

### 이클립스 다운로드
- 크롬에서 `www.eclipse.org`
- 2020년 06월의 Linux버전을 다운로드한다.

### 하둡 다운로드
- 크롬에서 `hadoop.apache.org`
- 2.10.1버전을 다운로드한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/125159229-2029bf80-e1b1-11eb-80e6-a07e007900c6.png)
  
  ![image](https://user-images.githubusercontent.com/79209568/125159230-2324b000-e1b1-11eb-9163-d47d72c6078f.png)

