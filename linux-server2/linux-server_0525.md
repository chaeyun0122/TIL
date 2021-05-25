# Web Server
- client에게 web page를 제공해주는 서버
  - client가 web browser 프로그램을 이용하여 web server에 접근하면 접근한 client에게 알맞은 web page를 전송
  - client는 전송받은 내용을 web browser에 출력
- 웹 페이지는 두 가지 종류로 구분
  - **정적 웹페이지** : client들에게 기존에 만들어 놓은 web page를 동일하게 제공
    - html, java script
  - **동적 웹페이지** : client들에게 접속한 사용자에 맞춰진 web page를 만들어서 제공
    -  JSP, PHP
  > - web server가 정적\/동적 웹페이지를 구분하여 서비스하지 않는다.  
 접속한 화면에서 제공하는 페이지의 작성언어가 무엇인지에 따라 달라진다.
- 실습은 web server 프로그램 중 점유율이 가장 높은 `apache`를 사용
  - apache는 거의 모든 상용 운영체제에서 동작 가능(호환성이 높다.)

## 관련 정보
- 패키지 : httpd-*
- 데몬 : httpd
- 방화벽 : port=80, service=http
  - https : http + ssl 인증 → port=443(ssl)
- 설정파일 : /etc/httpd/conf/httpd.conf
  - 설정 파일 내부는 전역 설정과 메인 영역 설정으로 분류  
(CentOS 6.x 까지 제공되던 apache는 virtual host 예문이 있었으나 현재는 X)

## web server 설치
```
yum -y install httpd-*
```
* 설치 후 바로 데몬 재실행 `systemctl restart httpd`
* 아파치는 기본적으로 제공하는 화면이 있다.
  * 현재 자신의 IP로 연결해놓은 `www.itbank.com`을 firefox를 통해 열면 아파치 기본 화면이 나온다. (DNS서버 실습을 통해 변경해놓음([DNS서버실습](https://github.com/Clary0122/TIL/blob/main/linux-server2/linux-server_0521-24.md#%EC%8B%A4%EC%8A%B5))
    
    ![image](https://user-images.githubusercontent.com/79209568/119456510-b32fa700-bd75-11eb-8228-72cb9b99b850.png)
