# samba
* 공유 폴더 제공 서비스
* 익명 공유, 사용자 지정 공유, 그룹 지정 공유로 분류
  * 익명/사용자 지정 공유 실습

## 관련 정보
* 패키지 : samba-*
* 데몬 : smb
* 방화벽 : service=samba
* 설정파일 : /etc/samba/smb.conf

## samba 설치
```
yum -y install samba-*
```
## samba 설정파일
> ### `vi /etc/samba/smb.conf`
* 익명 사용자 연결 설정(현재 버전에서만!) : `map to guest = bad user`  
  
  ![image](https://user-images.githubusercontent.com/79209568/118935651-b9451280-b986-11eb-9316-8da1c6281e36.png)

### 익명 사용자 공유
* 공유폴더 설정  
  
  ![image](https://user-images.githubusercontent.com/79209568/118935975-12ad4180-b987-11eb-8144-d2080f3e704b.png)
  * `[Anon]` : 공유폴더 이름
  * `commnet` : 마우스를 올렸을 때 보이는 설명
  * `path` : 실제로 사용되는 디렉터리. 절대경로로 작성한다.(어떤 디렉터리를 공유폴더로 사용하고 있는지)
  * `writable, read olny` : 한 세트. 읽고 쓰기 설정
  * `guest ok` : 게스트를 허용할 것인지. map to guest 설정과 별개로 이 설정을 해줘야지만 익명사용자가 공유폴더에 접근 가능
* 설정파일 저장 후 진행
* 공유 폴더 디렉터리 생성
```
mkdir -p /samba/anon
```
* 데몬 재실행
```
systemctl restart smb
```
* 윈도우에서 접근시키기 위한 방화벽 열기
```
firewall-cmd --permanent --add-service=samba
firewall-cmd --reload
```
* 실행 창에 `\\리눅스 IP` 입력  
  
  ![image](https://user-images.githubusercontent.com/79209568/118937177-33c26200-b988-11eb-9fa5-755e9f9bff0f.png)
* 접근 완료  
  
  ![image](https://user-images.githubusercontent.com/79209568/118937378-7126ef80-b988-11eb-8198-efab7a1ee971.png)
* 해당 공유폴더로 복사해보기
  ```
  cp /etc/inittab /samba/anon
  ```
  ![image](https://user-images.githubusercontent.com/79209568/118937457-87cd4680-b988-11eb-8c2c-799b770795ad.png)

### 사용자 지정 공유
* samba에 사용할 사용자를 등록해줘야한다.
  ```
  smbpasswd -a itbank
  ```
  * 비번 : a1234
* 설정 파일에 추가  
  
  ![image](https://user-images.githubusercontent.com/79209568/118939583-cc59e180-b98a-11eb-957c-b9bd8c909a83.png)
  * valid users : samba에 등록시킨 사용자를 적는다. 사용자명 앞에는 특수문자를 넣어주는 것이 설정형식이다.

* 공유 폴더 디렉터리 생성
  ```
  mkdir /samba/itbank
  ```

* 데몬 재실행
  ```
  systemctl restart smb
  ```
* itbank 공유 폴더 생성됨을 확인
  
  ![image](https://user-images.githubusercontent.com/79209568/118940260-7e91a900-b98b-11eb-940b-90fba781631a.png)
* 접근 시 해당 유저의 이름과 비밀번호로 접근  
  
  ![image](https://user-images.githubusercontent.com/79209568/118940325-8ea98880-b98b-11eb-9630-c1604164f6d3.png)
* `writable = Yes` 로 했는데 파일이 생성되지 않음. 현재 해당 폴더의 소유자가 root이기 때문이다. 소유자를 itbank로 변경해준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/118940512-c284ae00-b98b-11eb-9d6d-8646948157e1.png)
* 파일 생성 가능  
  
  ![image](https://user-images.githubusercontent.com/79209568/118940592-d7614180-b98b-11eb-8fc8-6c3dfa3e04f2.png)

 
