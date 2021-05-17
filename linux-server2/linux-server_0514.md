# Auto mount
* 기본적으로 마운트 된 모든 장치는 시스템 종료 시 전부 마운트 해제
  * 시스템 부팅 시 자동으로 마운트가 되도록 설정이 되어 있는 장치들만 마운트 된 상태로 부팅

## 오토 마운트 설정 파일 : `/etc/fstab`
* OS를 설치하면 자동으로 생성되고 설정 되어있는 파일
* 장치와 마운트포인트를 준비하고 fstab 파일에 설정만 정확하게 작성하면 오토마운트 끝  
  
![image](https://user-images.githubusercontent.com/79209568/118242581-768fc000-b4d8-11eb-8ac9-f3c5fcbb006a.png)
  
### 장치명
* 오토 마운트 할 장치의 이름을 절대경로로 작성
* 현재는 기본 설정된 장치들의 장치명이 아닌 UUID 값으로 작성(IDE 장치 관련 버그 때문에)
  * UUID : 장치의 고유 값, blkid로 확인 가능
### 마운트포인트
* 장치를 오토마운트 할 디렉토리의 이름을 절대경로로 작성
### 장치의 fstype 
* 오토 마운트 할 장치의 파일 시스템 형식을 작성
### 마운트 옵션
* 오토마운트를 하면서 적용시킬 옵션 작성, 일반적으로 defualts 사용
  * defaults 옵션은 기본적으로 사용하는 옵션들을 모아놓은 특수 옵션
### dump 운용
* 장치의 dump를 사용할 것인지 확인
  * 0: 사용안함 
  * 1: 사용
* 단, CentOS 7.0 부터는 0으로 고정(systemd가 알아서 사용)
### 부팅 시 fsck (file system check) 동작
* 부팅 시 file system(= partition)이 정상인지 상태 확안 
  * 0: 사용안함
  * 1: root partition(/ 에 마운트 할 파티션)
  * 2: 나머지 자료를 저장할 파티션)
* 단, CentOS 7.0 부터는 0으로 고정(systemd가 알아서 사용)

## 실습
```
<오토마운트 해보기>

HDD : 1GB (SCSI) 추가

partition    mount point

100MB  : Linux
200MB  : Clang
200MB  : Ms
300MB  : Net
200MB  : Anon
```
* 새 디스크 1GB 추가
* `fdisk /dev/sdb`로 새로운 디스크 설정
  * 첫 번째 파티션 : `n` → `<enter>` → `<enter>` → `+100M`
  * 두 번째 파티션 : `n` → `<enter>` → `<enter>` → `+200M`
  * 세 번째 파티션 : `n` → `<enter>` → `<enter>` → `+200M`
  * 네 번째 전 로지컬 파티션 생성 : 그냥 `<enter>`
  * 네 번째 파티션 : `n` → `<enter>` → `<enter>` → `+300M`
  * 다섯 번째 파티션 : `n` → `<enter>` → `<enter>` → `+200M`
  * 저장 : `w`
* `fdisk -l`로 `/dev/sdb1`, `/dev/sdb2`, `/dev/sdb3`, `/dev/sdb4`, `/dev/sdb5`, `/dev/sdb6`이 생긴 것을 확인
* `mkfs.xfs /dev/sdb1`~ `sdb6`까지 파티션 포맷하기 (`sdb4`는 extend이므로 하지 않는다)
* 경로 생성
  ```
  mkdir /test
  cd /test
  mkdir /Linux
  mkdir /Clang
  mkdir /Ms
  mkdir /Net
  mkdir /Anon
  ```
* 
