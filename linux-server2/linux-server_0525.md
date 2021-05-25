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

## Apache 설치
```
yum -y install httpd-*
```
* 설치 후 바로 데몬 재실행 `systemctl restart httpd`
* 아파치는 기본적으로 제공하는 화면이 있다.
  * 현재 자신의 IP로 연결해놓은 `www.itbank.com`을 firefox를 통해 열면 아파치 기본 화면이 나온다. (DNS서버 실습을 통해 변경해놓음([DNS서버실습](https://github.com/Clary0122/TIL/blob/main/linux-server2/linux-server_0521-24.md#%EC%8B%A4%EC%8A%B5))
    
    ![image](https://user-images.githubusercontent.com/79209568/119456510-b32fa700-bd75-11eb-8228-72cb9b99b850.png)
* `/var/www/html`에 `index.html`을 생성해주면 해당 html 화면으로 나온다.
  
  ![image](https://user-images.githubusercontent.com/79209568/119459728-eaec1e00-bd78-11eb-8f22-05431b748cb4.png)
  
  ![image](https://user-images.githubusercontent.com/79209568/119459749-efb0d200-bd78-11eb-8b2f-e0cb4e488c77.png)

## Apache 설정파일
> ### `vi /etc/httpd/conf/httpd.conf`

* ![image](https://user-images.githubusercontent.com/79209568/119461253-7b772e00-bd7a-11eb-8122-0bc0aaea55a8.png) 
  * named.conf에 있는 디렉터리 설정과 비슷하다. 이 아래로 있는 apache 관련 설정들의 기본 경로
* ![image](https://user-images.githubusercontent.com/79209568/119461276-803be200-bd7a-11eb-9992-1441f2578d9e.png) 
  * 서버에 접근할 때 사용하는 IP와 port를 지정할 때 사용
    * 80번 port가 기본 html port이기 때문에 해당 설정이 기본 값이다. 특정 IP의 특정 port를 지정하고 싶으면 <41>예문을 주석 풀고 사용
* ![image](https://user-images.githubusercontent.com/79209568/119461323-88941d00-bd7a-11eb-80b7-126a3c294c43.png) 
  * 각종 모듈들을 끌어올 때 LoadModule을 통해 모듈을 끌어온다. 현재는 `/etc/httpd/conf.modules.d/`의 모든 모듈을 include 하나로 다 끌어온다.
    ![image](https://user-images.githubusercontent.com/79209568/119461510-baa57f00-bd7a-11eb-9b7b-1aea4232eecb.png)
* ![image](https://user-images.githubusercontent.com/79209568/119461593-d1e46c80-bd7a-11eb-911c-48c176262f01.png)
  * 아파치 동작할 때 어느 사용자 권한으로 동작하는지 설정. 설치할 때 apache라는 사용자와 그룹이 자동으로 만들어졌기 때문에 해당 사용자와 그룹이 기본값이다.
### 서버관리 설정
* ![image](https://user-images.githubusercontent.com/79209568/119461923-2ab40500-bd7b-11eb-8af5-f25bf1d246ea.png)
  * 서버의 관리자 이메일 주소
* ![image](https://user-images.githubusercontent.com/79209568/119461988-3d2e3e80-bd7b-11eb-9976-f5ac25ea1019.png)
  * client 화면에 출력해주는 별칭주소를 설정
* ![image](https://user-images.githubusercontent.com/79209568/119462489-be85d100-bd7b-11eb-898b-87b70a66310e.png)
  * 서버 파일 시스템에 대한 설정
      * 형식
        ```
        <Directory "경로+이름">
            옵션
            옵션
            옵션
        </Directory>
        ```
  * 최상위 디렉터리에 대한 옵션들이다. (현재 설정된 옵션들은 아피치 웹페이지를 통해 최상위 경로로 접근하지 못한다는 옵션들이다.)
* ![image](https://user-images.githubusercontent.com/79209568/119462812-18869680-bd7c-11eb-82a8-ac5e30e77a24.png)
  * 어느 디렉터리에 있는 문서를 client에게 제공해 줄 것인지 정하는 설정
* ![image](https://user-images.githubusercontent.com/79209568/119463162-74e9b600-bd7c-11eb-9a75-769a239ff306.png)
  * Document root의 상위 디렉터리에 대한 옵션
* ![image](https://user-images.githubusercontent.com/79209568/119463363-a95d7200-bd7c-11eb-8e30-6528b183e079.png)
  * Document root 자체의 옵션
* ![image](https://user-images.githubusercontent.com/79209568/119463567-d873e380-bd7c-11eb-8284-0d294794de7a.png)
  * Document root 속의 어떤 이름의 파일을 client에게 제공해 줄 것인지 정하는 설정
  * 암묵적으로 이름은 index로 한다. 그리고 뒤에는 작성한 언어를 적어준다. 따라서 해당 index.html은 **html로 작성한 기본문서**라는 뜻이된다.
  * 다른 언어로 기본문서를 작성하고 싶으면 뒤에 적어주면 된다. 다만 앞에 있을수록 우선순위가 높다.  
    ![image](https://user-images.githubusercontent.com/79209568/119463966-3e606b00-bd7d-11eb-8619-ce29cedee77d.png)
* ![image](https://user-images.githubusercontent.com/79209568/119464041-56d08580-bd7d-11eb-9872-63d965285018.png)
  * 로그 저장될 경로와 로그 파일 이름
* ![image](https://user-images.githubusercontent.com/79209568/119464323-95664000-bd7d-11eb-9abc-f8b849af8cc0.png)
  * 로그를 남기는 심각성 정도를 설정

* ![image](https://user-images.githubusercontent.com/79209568/119464496-be86d080-bd7d-11eb-9521-749713e9a4f7.png)
  * 기본 문자셋

* ![image](https://user-images.githubusercontent.com/79209568/119464617-dfe7bc80-bd7d-11eb-91e6-1ae00e61d83f.png)
  * include로 끌어오는 옵션들이 conf.d/ 위치에 \*.conf 확장자로 되어있다는 설정

## 실습
```
현재 /var/www/html/에 index.html 파일이 있는데(위에서 만듦)
그걸 itbank의 홈 디렉터리로 옮긴다.

mv index.html ~itbank

이 상태에서 apche 실행 시 index.html이 뜨도록 바꾼다.
```
### 정답
* `vi /etc/httpd/conf/httpd.conf`에서 설정을 바꿔준다.
  * `DocumentRoot "/home/itbank"`
  * ![image](https://user-images.githubusercontent.com/79209568/119470161-06f4bd00-bd83-11eb-923d-5450bc153d9c.png)
* `chmod 701 ~itbank` 홈 디렉터리의 권한을 바꿔준다.
* `systemctl restart httpd` 데몬 재실행
* index.html이 출력
  ![image](https://user-images.githubusercontent.com/79209568/119470482-5804b100-bd83-11eb-8a1f-08d6d014055e.png)
