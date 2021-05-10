# 원격 제어 명령어 (remote access) - telnet
* 옛날부터 사용되던 원격 접속 protocol
* CLI 환경으로 사용
* 거의 모든 OS에서 사용가능하지만 보안성이 낮음(비 암호화 통신 = 평문 통신) **→ 가급적 사용 X**
## 관련 정보
- 패키지 : telnet-*
- 데몬 : telnet.socket
- 방화벽 : port=23/tcp, service=telnet
- 설정파일 : 별도의 설정파일 없음
  > * 기본적으로 데몬은 .service 확장자가 자동으로 붙음 (ex. network = newtwork.service)
  > * telnet은 CentOS 7.9부터 별도의 service로 동작 하지 않음 (단순 통신 socket으로 취급)
  >   * 그렇기 때문에 .socket 확장자가 붙음
  >   * service가 아니기 때문에 설정 파일이 없음

## telnet 설치
* CD를 마운트해서 telnet으로 시작하는 패키지 설치(2개)
