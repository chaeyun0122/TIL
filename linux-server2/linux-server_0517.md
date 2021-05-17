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

* 내용 입력
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
* 저장 후 데몬 재 실행
  * 그냥 재실행 하면 오류 뜬다. (nfs_server라는 폴더가 없기 때문)
  * `mkdir /nfs_server` 폴더 생성
  * `systemctl restart nfs`
* 클라이언트에서 마운트 할 경로 만들기 : `mkdir /nfs_client`
* 마운트 해주기
  ```
  mount -t nfs 192.168.217.128:/nfs_server /nfs_client
  ```
  * t옵션을 통해 nfs를 위한 마운트 인 것을 알 수 있도록 설정
  * 제공해주는 서버 IP와 경로
  * 마운트 할 마운트 포인트

* NFS 연결 확인 : 클라이언트 쪽에 파일을 만들면 서버에서 확인 가능  
  ![image](https://user-images.githubusercontent.com/79209568/118451624-6b36d180-b730-11eb-9aab-a41fd0a96b74.png)

> #### 잘 된것 같지만 마운트 정보를 확인해보면 문제가 있다.
> ![image](https://user-images.githubusercontent.com/79209568/118452293-f87a2600-b730-11eb-8847-e49b6c6becf3.png)  
> * 클라이언트의 용량이 서버의 root 용량과 같다.
 
* 500MB 파티션 생성 후 /nfs_server에 오토마운트한다.
  * `fdisk /dev/sdb`로 +500M인 파티션 생성
  * 초기화 `mkfs.xfs /dev/sdb1`
  * `mount /dev/sdb1 /nfs_server`
  * 오토마운트 설정 `vi /etc/fstab` → `/dev/sdb1  /nfs_server  xfs  defaults  0  0`
  * 재부팅 `init 6`
  * 확인 `df -h`  
  ![image](https://user-images.githubusercontent.com/79209568/118454116-f913bc00-b732-11eb-98d4-24ca21bd1901.png)
