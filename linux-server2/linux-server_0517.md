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
    * `no_root_squash / root_squash(기본값)` : 클라이언트가 root로 연결할 때 내 입장에서도 root로 받아주는 것 / 클라이언트가 root로 연결할 때 내 입장에서는 root로 받아주지 않고 일반 사용자로 받아주는 것
    * `all_squash(기본값)` :  클라이언트가 일반 사용자로 접근했을 때 내 입장에서 others로 받는 것
    * `sync` : 양 쪽 동기화 시켜주는 옵션
