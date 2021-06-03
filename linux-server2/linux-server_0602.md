# 최종실습
```
가상머신 4개로 진행
1.  128 : NS
2.  129 : Web + XE
3.  130 : DB (서비스로 mysql넣어줌)
4.  131 : Client

Client의 firefox에서 www.finaltest.com 입력해서 2번 머신의 XE화면 띄우기
```
## NS : 128
* `yum -y install bind-*` 설치
* `vi /etc/named.conf` 설정
* `vi /etc/named.rfc1912.zones`
  ```
  zone "finaltest.com" IN {
        type master;
        file "finaltest.com.zone";
        allow-update { any; };
        allow-transfer { any; };
  };

  zone "217.168.192.in-addr.arpa" IN {
          type master;
          file "192.168.217.zone";
          allow-update { any; };
          allow-transfer { any; };
  };
  ```
* cp named.localhost finaltest.com.zone
* vi finaltest.com.zone
  ```
  $TTL 1D
  @       IN SOA  finaltest.com.          root(
                                          0       ; serial
                                          1D      ; refresh
                                          1H      ; retry
                                          1W      ; expire
                                          3H )    ; minimum
          IN      NS      finaltest.com.
          IN      A       192.168.217.128

  www     IN      A       192.168.217.129

  ```
* cp finaltest.com.zone 192.168.217.zone
* vi 192.168.217.zone
  ```
  $TTL 1D
  @       IN SOA  finaltest.com.          root(
                                          0       ; serial
                                          1D      ; refresh
                                          1H      ; retry
                                          1W      ; expire
                                          3H )    ; minimum
          IN      NS      finaltest.com.
          IN      A       192.168.217.128

  129     IN      PTR     www.finaltest.com.
  ```
* chmod 660 \*.zone
* chown .named \*.zone
* systemctl restart named
* vi /etc/resolv.conf
* nslookup 확인

## web
* yum -y install httpd-* php-* --skip-broken
* systemctl restart vsftpd ~~ (xe업로드)
* unzip xe.zip
* vi /etc/httpd/conf/httpd.conf -> xe로 DocumentRoot 바꿈
* 방화벽 열기 firewall-cmd --permanent --add-service=http
* vi /etc/resolv.conf에서 192.168.217.129 추가
* firefox에서 www.finaltest.com 들어가서 설치
* ![image](https://user-images.githubusercontent.com/79209568/120621191-9e02f880-c498-11eb-9386-ff9dab9ff28a.png) 

## DB
* yum -y install mariadb-*
* systemctl restart mariadb
* update user set passsword=password('itbank') where user='root';
* delete from user where user=' ';
* insert into user(host, user, password) values ('192.168.217.%','userfinal', password('itbank'));
* insert into db values ('192.168.217.%', 'dbfinal', 'userfinal', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y', 'y');
* systemctl restart mariadb
* firewall-cmd --permanent --add-port=3306/tcp / --reload

## client
* vi /etc/resolv.conf 에 네임서버 192.168.217.128 추가
