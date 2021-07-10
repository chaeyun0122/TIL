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
- 새로운 Virtual Machine 생성
- next 누르다가 Linux, CentOS 7 64-bit를 선택 후 next
  
  ![image](https://user-images.githubusercontent.com/79209568/125154704-45f59b00-e196-11eb-9951-3178e01003fa.png)
- workspace에 master, slave1, slave2 폴더를 생성
  
  ![image](https://user-images.githubusercontent.com/79209568/125154729-6de4fe80-e196-11eb-9f40-c270aac77dda.png)
- 가상머신 이름 설정을 CentOS 7 master로 하고 위치를 workspace에 만들어놓은 master로 설정 후 next
- finish 후 edit virtual machine settings에서 메모리를 2GB로 설정
- CD/DVD에서 Use ISO image file을 아까 다운로드 한 CentOS 7 ISO 파일로 설정 후 OK
  
  ![image](https://user-images.githubusercontent.com/79209568/125154786-d338ef80-e196-11eb-8be6-79c83be7d28a.png)
