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
> ![image](https://user-images.githubusercontent.com/79209568/119625965-b2fedc80-be45-11eb-858c-c07171f36b62.png)

### 2, 3
```
<public_html 폴더 안에 test1폴더를 만들고 그 안에 index.html을 만든다.>

cd ~main/public_html

mkdir test1

echo "test1" >> test1/index.html

<test2 폴더를 test1 폴더 안에 만들고 그 안에 index.html을 만든다.>

mkdir test1/test2

echo "test2" >> test1/test2/index.html
```
> ![image](https://user-images.githubusercontent.com/79209568/119633440-b5b10000-be4c-11eb-9fd9-3c3464ac987a.png)

### 3, 4
```
useradd -mk /kg user1
useradd -mk /kg user2

chmod 701 /home/user*

echo "user1's home" >> ~user1/public_html/index.html
echo "user2's home" >> ~user2/public_html/index.html

cd /etc/httpd/conf
```

### 3
```
vi userdir.conf
```
* 위의 주석을 걸고 아래에 주석을 풀어준다.
  ![image](https://user-images.githubusercontent.com/79209568/119786913-a0e77180-bf0b-11eb-8c7a-200cc4a56045.png)
  
> ![image](https://user-images.githubusercontent.com/79209568/119787241-f91e7380-bf0b-11eb-9a2d-1059c015ab36.png)

### 4
```
vi autoindex.conf
```
* Alias를 추가해준다.
  ![image](https://user-images.githubusercontent.com/79209568/119787002-b6f53200-bf0b-11eb-95eb-f4c34db9bd4f.png)
  
> ![image](https://user-images.githubusercontent.com/79209568/119787190-ed32b180-bf0b-11eb-8e6f-8156ff1b8ed1.png)
