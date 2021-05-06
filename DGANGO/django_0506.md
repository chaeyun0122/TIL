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
