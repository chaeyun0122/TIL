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

  zone "217.168.192.in-addr-arpa" IN {
          type master;
          file "192.168.217.zone";
          allow-update { any; };
          allow-transfer { any; };
  };
  ```
* cp named.localhost finaltest.com.zone
* vi finaltest.com.zone
* cp finaltest.com.zone 192.168.217.zone
* vi 192.168.217.zone
* chmod 660 \*.zone
* chown .named \*.zone
* systemctl restart named
* vi /etc/resolv.conf
* nslookup

## DB : 130
* yum -y install mariadb-*
