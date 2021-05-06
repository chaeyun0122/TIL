# 네트워크 개요
## Wireshark
![image](https://user-images.githubusercontent.com/79209568/117244259-af45ee80-ae73-11eb-916f-3c326e92b63f.png)

* 이 중 외부에 사용되는 랜카드를 찾아야한다.
* cmd 창에 `ipconfig` 입력
  ![image](https://user-images.githubusercontent.com/79209568/117244403-e916f500-ae73-11eb-8f19-06af51310e52.png)
* wireshark에서 해당 이름을 클릭
  ![image](https://user-images.githubusercontent.com/79209568/117244475-077cf080-ae74-11eb-992a-7070c5bdc143.png)
  ![image](https://user-images.githubusercontent.com/79209568/117244521-24192880-ae74-11eb-9832-0f290f46329f.png)
  * Status Code
    * 200 : 정상
    * 404 : 페이지를 찾을 수 없음 (사용자가 잘못된 요청을 보냄)
    * 500 : 사용자가 제대로 된 요청을 보냈지만 서버가 오류남

# 초기환경 세팅
## Visual Code 받기
## 웹서버 환경설정
* [해당 파일](DGANGO/util/Apache httpd(웹서버).7z) 압축풀기
* 아래는 해당 ReadMe.txt에 적힌대로 한 것
  ![image](https://user-images.githubusercontent.com/79209568/117249864-3f3c6600-ae7d-11eb-913e-a1470761592f.png)

* `httpd-2.4.41-win64-VS16` 압축 풀기
* `VC_redist.x64` 설치 (이미 설치 되어있다는 오류 뜨면 그냥 닫기)
* `Apache24` 폴더를 c드라이브 바로 아래에 붙여넣기
* 명령 프롬프트에서 `cd \` 후 `cd Apache24\bin`으로 bin 폴더 이동 후 `httpd.exe` 프로그램 실행
  ![image](https://user-images.githubusercontent.com/79209568/117250162-c8539d00-ae7d-11eb-8866-82bf703ad789.png)
