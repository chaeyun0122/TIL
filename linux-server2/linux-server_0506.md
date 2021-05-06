> 1w : 설치, IP개념 설정(네트워크), 네트워크 관련 명령어, remote access(telnet, ssh, vnc)  
> 2w : ftp, *오토 마운트, nfs, autofs*, samba, dhcp  
> 3w : *DNS, web  
> 4w : DB, wep+DB utils*


# 초기설치
## centos 설치
* https://www.centos.org/centos-linux/ 페이지에서 7버전
* 해당 파일 다운로드  
  ![image](https://user-images.githubusercontent.com/79209568/117254727-b70d8f00-ae83-11eb-8990-3d6ed4c6b10e.png)
## vmware 15.5 이상 설치
### 새로운 virtual machine 만들기
![image](https://user-images.githubusercontent.com/79209568/117258369-dc040100-ae87-11eb-9afd-95f8350980a0.png)  
  
![image](https://user-images.githubusercontent.com/79209568/117258485-fdfd8380-ae87-11eb-86e7-8418feadf9b6.png)

![image](https://user-images.githubusercontent.com/79209568/117258515-0655be80-ae88-11eb-8ad4-ccc699c39804.png)

![image](https://user-images.githubusercontent.com/79209568/117258598-18cff800-ae88-11eb-8bc3-70f9f9ce2d24.png)

![image](https://user-images.githubusercontent.com/79209568/117258809-546ac200-ae88-11eb-855e-67e17eb04714.png)

![image](https://user-images.githubusercontent.com/79209568/117258847-6187b100-ae88-11eb-971d-647c6f1433fe.png)

![image](https://user-images.githubusercontent.com/79209568/117258906-76fcdb00-ae88-11eb-9a15-904d45dda006.png)

![image](https://user-images.githubusercontent.com/79209568/117258980-8bd96e80-ae88-11eb-844d-3fb191d0d42f.png)

![image](https://user-images.githubusercontent.com/79209568/117259011-985dc700-ae88-11eb-9e35-a12a8db965a6.png)

![image](https://user-images.githubusercontent.com/79209568/117259038-a14e9880-ae88-11eb-8a90-62b62c8b9fe6.png)

![image](https://user-images.githubusercontent.com/79209568/117259062-a90e3d00-ae88-11eb-9371-83c9b32d37c8.png)

![image](https://user-images.githubusercontent.com/79209568/117259102-b297a500-ae88-11eb-926f-61da2d126e86.png)

![image](https://user-images.githubusercontent.com/79209568/117259134-b9261c80-ae88-11eb-8994-c1490eab6e14.png)

![image](https://user-images.githubusercontent.com/79209568/117259164-bfb49400-ae88-11eb-8f7b-d03096de3921.png)

### 실행
* centos를 넣어준 후 실행
* 한국어 설정 후 다음
#### 소프트웨어 선택
  ![image](https://user-images.githubusercontent.com/79209568/117261502-2dfa5600-ae8b-11eb-92ca-114dfc480c22.png)
#### 설치대상
  ![image](https://user-images.githubusercontent.com/79209568/117261653-5b470400-ae8b-11eb-8132-837f9ae6c5db.png)
  * 'boot' 1024, 'swap' 2048, '/' 빈 칸 으로 설정 후 완료
  * mbr영역이 사용가능한 공간(992.5)으로 남음
#### 네트워크 호스트 이름
* 호스트 이름 : server → 적용 후 위의 토글 킴
  ![image](https://user-images.githubusercontent.com/79209568/117262647-7403e980-ae8c-11eb-89d1-2f2ff415d5bb.png)
#### 설치 시작
#### 사용자 설정
* root : a
* 사용자 : itbank/a1234

#### 완료 후 재부팅
#### 라이센스 동의
#### root로 로그인

## ip 설정
* `edit > virtual network editor`의 
  ![image](https://user-images.githubusercontent.com/79209568/117264922-add5ef80-ae8e-11eb-9cdc-6f8cac00529c.png)

* `프로그램 > 시스템도구 > 설정 > 네트워크 > 유선의 설정 > IPv4`
  ![image](https://user-images.githubusercontent.com/79209568/117264931-b1697680-ae8e-11eb-87db-a576bf456bfd.png)
* 스위치 껐다 켜기

## 터미널
* `바탕화면 오른쪽 > 터미널 열기`
* 만약 서버이름이 server가 아니면 `etc/hostname`의 server로 이름을 바꾸고 재부팅
* `vi etc/selinux/` disabled로 바꾼 후 저장 및 재부팅
  ![image](https://user-images.githubusercontent.com/79209568/117265882-a95e0680-ae8f-11eb-932e-5e4d7088a74d.png)

## putty 설정
