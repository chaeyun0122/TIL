# 실습
```
1.  user                : main
    skel                : /kg/
    자동생성 디렉토리    : public_html/
    DocumentRoot        : ~main/public_html/

    2bwithu.co.kr       : 화면 내용 main
--------------------------------------------------------------------------------------    
2.  www.2bwithu.co.kr/test1          : 화면 내용 test1   httpd.conf X
3.  www.2bwithu.co.kr/test1/test2    : 화면 내용 test2   hint : FQDN -> DocumentRoot
--------------------------------------------------------------------------------------
4.   www.2bwithu.co.kr/~user1        : user1 홈 디렉터리 내부 index.html  httpd.conf O
5.   www.2bwithu.co.kr/user2/        : user2 홈 디렉터리 내부 index.html  hint : IncludeOptional
```
## 정답
### 1.
```
cp -r /etc/skel /kg

mkdir /kg/public_html

useradd -mk /kg main

cd ~main/public_html/

echo "main's home" >> index.html

chmod 701 ~main

systemctl restart httpd
```
![image](https://user-images.githubusercontent.com/79209568/119625965-b2fedc80-be45-11eb-858c-c07171f36b62.png)
