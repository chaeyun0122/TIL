# FTP2
## 실습
### ftp서버에 익명 사용자로 접속해서 파일 업로드 해보기
* 접속 후 권한 변경은 안됨

```
1. 설정 파일에서 anon 업로드, 쓰기 권한 주기
  vi /etc/vsftpd/vsftpd.conf
  
    anon_upload_enable=YES
    anon_mkdir_write_enable=YES

2. 데몬 재실행
  systemctl restart vsfpd

3. pub 폴더 권한 주기 (안 주면 553 Could not create file.오류뜸)
  - pub 폴더에 권한 주는 이유 : ftp 폴더에 주면 500 OOPS: vsftpd: refusing to run with writable root inside chroot() 오류뜸
  chmod 757 /var/ftp/pub

4. pub 폴더로 이동하고 업로드해주기
  cd pub
  put a.txt
```
