# NFS
* Network File System
* 네트워크를 통해 다른 장치의 파티션을 나에게 마운트
* 최초에는 Unix 중에 하나가 사용하던 service
  * 유용성이 너무 높아서 다른 OS에서도 사용
* 용량을 제공해주는 장치를 NFS server, 용량을 제공받는 장치를 NFS client로 분류
## 관련 정보
* 패키지 : nfs-* (CentOS 7.0부터는 단일 패키지)
* 데몬 : nfs
* 방화벽 : service=nfs
* 설정파일 : /etc/exports (NFS server)

## NFS 설치
* 설치 되어있다.  
  ![image](https://user-images.githubusercontent.com/79209568/118446969-2d837a00-b72b-11eb-984b-a170bfe1fa77.png)

## NFS 설정파일
### 서버 쪽 설정
> ### `vi /etc/exports`
> * 비어 있다.

#### 내용 입력
```
/nfs_server   192.168.217.128(rw,no_root_squash,sync)
```
* `/nfs_server` : 제공해줄 디렉터리 (디렉터리 단위로 용량을 제공해준다.)
* `192.168.217.128` : 클라이언트 IP (제공해줄 디렉터리를 누구에게 줄 것인지)
* `(rw,no_root_squash,sync)` : NFS 옵션 (어떤 옵션을 기반으로 용량을 제공해 줄 것인지)
  * `rw / ro` : read write / read only
  * `no_root_squash / root_squash(기본값)` :  
클라이언트가 root로 연결할 때 내 입장에서도 root로 받아주는 것   
/ 클라이언트가 root로 연결할 때 내 입장에서는 root로 받아주지 않고 일반 사용자로 받아주는 것
  * `all_squash(기본값)` :  클라이언트가 일반 사용자로 접근했을 때 내 입장에서 others로 받는 것
  * `sync` : 양 쪽 동기화 시켜주는 옵션
#### 저장 후 데몬 재 실행
* 그냥 재실행 하면 오류 뜬다. (nfs_server라는 폴더가 없기 때문)
* `mkdir /nfs_server` 폴더 생성
* `systemctl restart nfs`
#### 클라이언트에서 마운트 할 경로 만들기 : `mkdir /nfs_client`
#### 마운트 해주기
```
mount -t nfs 192.168.217.128:/nfs_server /nfs_client
```
* t옵션을 통해 nfs를 위한 마운트 인 것을 알 수 있도록 설정
* 제공해주는 서버 IP와 경로
* 마운트 할 마운트 포인트

#### NFS 연결 확인 : 클라이언트 쪽에 파일을 만들면 서버에서 확인 가능  
![image](https://user-images.githubusercontent.com/79209568/118451624-6b36d180-b730-11eb-9aab-a41fd0a96b74.png)

> #### 잘 된것 같지만 마운트 정보를 확인해보면 문제가 있다.
> ![image](https://user-images.githubusercontent.com/79209568/118452293-f87a2600-b730-11eb-8847-e49b6c6becf3.png)  
> * 클라이언트의 용량이 서버의 root 용량과 같다.
 
#### 500MB 파티션 생성 후 /nfs_server에 오토마운트한다.
* `fdisk /dev/sdb`로 +500M인 파티션 생성
* 초기화 `mkfs.xfs /dev/sdb1`
* `mount /dev/sdb1 /nfs_server`
* 오토마운트 설정 `vi /etc/fstab` → `/dev/sdb1  /nfs_server  xfs  defaults  0  0`
* 재부팅 `init 6`
* 확인 `df -h`  
![image](https://user-images.githubusercontent.com/79209568/118454116-f913bc00-b732-11eb-98d4-24ca21bd1901.png)

#### 데몬 자동 실행 설정
* `systemctl enable nfs`
* 다시 실행 해준다. `systemctl restart nfs`

#### 클라이언트에서 NFS 다시 마운트
```
mount -t nfs 192.168.217.128:/nfs_server /nfs_client
```
![image](https://user-images.githubusercontent.com/79209568/118460169-675a7d80-b737-11eb-9d6e-32607a5fe7be.png)

#### NFS 연결 확인 : 클라이언트 쪽에 파일을 만들면 서버에서 확인 가능
![image](https://user-images.githubusercontent.com/79209568/118460212-70e3e580-b737-11eb-8e0e-8bf03ea349a0.png)

### 클라이언트 쪽 설정
* 클라이언트에서 자동으로 연결 되도록 만들어주기 위해 `autofs`를 사용한다.
> ## autofs
> * auto file system
> * 자동으로 file system(= partition)을 사용할 수 있도록 해주는 서비스
> * /etc/fstab 파일과 역할은 비슷하지만, 동작 순서는 autofs가 후순위
> ### 관련 정보
> * 패키지 : autofs-*
> * 데몬 : autofs
> * 방화벽 : X
> * 설정파일 : /etc/ustofs.conf
> ### 설치
> ```
> yum -y install autofs-*
> ```
> ### 설정 파일
> ### autofs.conf → auto.master → auto.misc 이렇게 연결 되어있음
> #### `vi /etc/autofs.conf`
> * `master_map_name` : 자동으로 마운트되는 모든 장치들은 특정 디렉토리 안쪽으로 마운트되도록 몰아놓도록 되어있다. 이 설정이 auto.master파일 안에 들어가 있다. 어느 디렉토리에 자동으로 마운트되도록 할 것인지 지정 가능
> * `timeout` : 마운트 해제까지 걸리는 시간
> * `browse_mode` : 클라이언트에 연결되는 마운트 포인트들의 목록을 노출할 것인지 설정. 목록이 노출이 되어야 접근도 가능하기 때문에 장치를 사용하려면 반드시 yes로 바꿔줘야한다. 
> #### `vi /etc/auto.master`
> * `/misc   /etc/auto.misc` : 자동 마운트에서 매핑되는 파일을 어느 파일에서 어느 디렉토리로 마운트 시킬 것인지 확인. `/etc/auto.misc` 파일에 설정한 것들이 `/misc`에 적용되는 것
> #### `vi /etc/auto.misc`  
> ![image](https://user-images.githubusercontent.com/79209568/118463814-28c6c200-b73b-11eb-96d0-f26fa21a0d5a.png)
> * 실질적인 auto mount 설정.
> ```
> nfs_auto   -rw,hard,intr   192.168.217.128:/nfs_server
> ```
> * 경로를 따로 적어주지 않고 이름만 적어놓는다. /misc속에 적어놓은 이름으로 마운트 포인트가 만들어진다.
> * 설정 옵션들
>   * `ro / rw`: read only, read write
>   * `soft / hard` : 각각 ro, rw에서 주로 사용. soft는 서버 연결 시도했는데 안되면 그대로 끝냄, hard는 서버랑 연결이 안되면 time out때 까지 계속 auto mount를 시도를 함
>   * `intr` : 비정상적인 종료가 발생해도 공유는 계속 이어가겠다는 설정
> * 마운트 될 IP주소와 경로

#### 확인
* 데몬 재실행 후 디렉터리들 확인  
  ![image](https://user-images.githubusercontent.com/79209568/118464195-8ce98600-b73b-11eb-9827-fb528c034974.png)
* enable 시키기
```
systemctl enable autofs
```

## 전체 설정 확인
![image](https://user-images.githubusercontent.com/79209568/118465049-7859bd80-b73c-11eb-91ba-03dc590aec8d.png)

## 실습
```
- 기존 가상머신 2개 스냅샷 되돌린 후 진행 -

서울지사에서 서버관리자로 일하고 있는 도중 본사에서 급한 연락을 받았다.
본사 서버에 용량이 부족한데 본사 서버관리자가 휴가 및 외근으로 인해 작업을 할 수 있는 사람이 없다고 할 때
서울 지사에서 본사에 5G 용량을 확보해주기
(단, 지사 머신에 HDD 추가 후 진행)

* 용량 제공은 자동으로 동작되도록 설정
* 스냅샷 되돌린 후에는 putty를 하나만 켜고 한다.
```
