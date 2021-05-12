# 초기 세팅
## Python 설치 전 환경설정
* 프로그램에서 python관련 파일들 다 삭제
* 실행창(`ctrl + r`)에 `shell:startup`로 들어가서 상위 폴더 `프로그램`으로 갔을 때 python 폴더가 있으면 삭제
* 실행창에 `sysdm.cpl`을 입력해서 고급 탭의 환경 변수로 간다. 사용자 변수와 시스템 변수에 python 관련 path들을 지워준다.

## Python 설치
* `Python 3.9.2` 설치 파일 다운로드
* Customize installation 클릭  
  ![image](https://user-images.githubusercontent.com/79209568/117919750-85d00b80-b328-11eb-98b3-44856b090010.png)
* 위치를 해당 사진처럼 입력  
  ![image](https://user-images.githubusercontent.com/79209568/117919800-a730f780-b328-11eb-9e36-33d5b3f15ad3.png)
* 환경 변수 설정
  > 환경변수 path의 역할 : python이 없는 위치에서도 python을 실행할 수 있게 해준다.
  * PYTHONHOME을 추가해서 python을 설치한 위치를 값으로 넣어준다.  
    ![image](https://user-images.githubusercontent.com/79209568/117920075-33dbb580-b329-11eb-94f9-37b39a9ae5f8.png)

  * PYTHONPATH을 추가해서 python의 script 폴더 위치를 값으로 넣어준다.  
    ![image](https://user-images.githubusercontent.com/79209568/117920085-3807d300-b329-11eb-9570-0655ac3dcde1.png)
  
  * Path에 새로만들기를 눌러서 추가한 변수들을 넣어준다. *(%는 변수의 값을 가져오도록 해주는 기호)*
  * 두 경로를 가장 위로 올려준다.  
    ![image](https://user-images.githubusercontent.com/79209568/117920311-9f258780-b329-11eb-8968-67ef83173998.png)

### 설치 확인
* cmd 창에서 `python` 입력 후 잘 실행되는 지 확인한다.
* `path`를 입력했을 때 python이 존재하는지 확인한다.   
  ![image](https://user-images.githubusercontent.com/79209568/117920582-0f340d80-b32a-11eb-9f76-efa05798915e.png)

## Django 설치
### pip로 django 설치
* cmd 창에서 `pip list` 명령어 입력 → `python -m pip install --upgrade pip`를 입력하라는 경고가 나옴
* `python -m pip install --upgrade pip` 입력해서 pip를 업그레이드 버전으로 받아 준다.
* `pip install django==2.2.22` 버전 명시 후 다운로드 진행
  * `pip install django`로 다운로드 진행 시 현재 최신버전인 3.x 버전으로 다운로드 됨. 실습은 2버전으로 진행함
