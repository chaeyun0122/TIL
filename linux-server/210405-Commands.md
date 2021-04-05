# 명령어 구조
1. **명령어**
2. **명령어 directory(작업대상) 또는 file**
  * 한 칸 이상 꼭 띄어쓰기!
  * ex) ls /etc/passwd
3. **명령어 [option]**
  * 명령어 + 옵션의 경우 한 칸 띄고 '-'를 써준다.
  * ex) ls -l
4. **명령어 [option] directory 또는 file**
  * ex) ls -l /home
  * 옵션이 맨 뒤로 가도 작동한다. (ls /home -l)
5. **명령어 [option] [sub option]**
  * ex) ls -l -d : 옵션끼리도 띄어서 쓴다
  * ex) ls -ld : 하나의 '-'에 옵션 여러개를 쓸 수도 있다.(하지만 순서가 바뀌면 안되는 옵션도 있으니 주의해야한다)
6. **명령어 [option] [sub option] directory 또는 file**
