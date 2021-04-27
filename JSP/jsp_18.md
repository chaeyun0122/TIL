# Connection Pool
* 데이터베이스에 연결하기 위한 Connection은 객체다. (로딩 → 연결 → 접근 → 실행 → 해제 반복 ...)
  * Connection 객체를 생성하고 해제하는 동안 많은 시스템 자원이 소모된다.
  * 이 문제를 해결하기 위해서 Connection Pools에 커넥션 객체들을 만들어 놓은 후에, 커넥션 객체가 필요한 경우 작성한 객체를 할당해 주고, 사용이 끝나면 다시 커넥션 풀로 회수하는 방법을 사용한다.
* 서버에 미리 Connection 객체를 설정해 놓은 것이다.
* DB와 연결된 Connection 객체를 미리 만들어 놓고 Pool 속에 저장해 두고 있다가, 요청이 있을 때 마다 가져다 사용하고 반환하는 기법이다.

## Connection Pool 사용하기
### 1. jar 파일 추가 : `WEB-INF → lib`
* tomcat server : 설치 폴더안에 기본 제공한다.
  * `lib > tomcat-dbcp.jar`  
    
    ![image](https://user-images.githubusercontent.com/79209568/116239249-6324eb00-a79d-11eb-80f0-d849c65c99be.png)
  * 복사해서 `WEB-INF → lib`에 붙여 넣는다.
    
    ![image](https://user-images.githubusercontent.com/79209568/116239462-a1220f00-a79d-11eb-8d64-a1c6bb95b7c5.png)

### 2. DBCP 정보 설정
* `.xml` 파일 : 웹이나 앱에서 데이터 및 구조화 된 문서들을 위한 표준이고, 주로 설정할 때 사용한다.
#### `context.xml` : Connection Pool의 설정을 하는 파일
* 위치 : `META-INF` (웹과는 상관없는 팡리 저장소)  

  ![image](https://user-images.githubusercontent.com/79209568/116240064-5ead0200-a79e-11eb-8694-933961e78596.png)
* 내용
  * auth : 자원 관리자
  * name : 설정을 구별하는 이름
  * type : 사용하는 클래스 명
  * driverClassName : 드라이버 이름
  * url : DB 결로 
  * username : 접속 계정
  * password : 접속 비밀번호
  * maxActive : 최대 연결 수
  * maxIdle : 사용되지 않고 풀에 저장될 수 있는 커넥션 수
  * maxWait : 사용 가능 커넥션이 없을 때 대기 시간(1/1000 초, - 값이면 무한 대기)  

  ```xml
  <Context>
    <Resource auth="Container"
        name="jdbc/oracle"
        type="javax.sql.DataSource"
        driverClassName="oracle.jdbc.OracleDriver"
        url="jdbc:oracle:thin:@localhost:1521:xe"
        username="c##dbtest"
        password="a1234"
        maxActive="20"
        maxIdle="3"
        maxWait="-1">

    </Resource>
  </Context>
  ```

### 3. JNDI 설정
* 설정된 정보를 이름을 사용해서 가져올 때 java의 naming API 를 사용한다.
* javax.naming 패키지의 클래스를 사용하여 이름으로 객체를 가져오는 것을 JNDI라고 한다.
* 서버의 커넥션을 얻어오려면 javax.sql.DataSource를 사용한다.
#### `web.xml`
* description : 설명
* res-ref-name : JDBC 이름 → \<Resource>의 name과 동일하다.
* res-type : \<Resource>의 type과 동일하다.
* res-auth : \<Resource>의 auth와 동일하다.
  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" id="WebApp_ID" version="3.1">
  <display-name>P11_board_ConnectionPool</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  
  <resource-ref>
  	<description>connection</description>
  	<res-ref-name>jdbc/oracle</res-ref-name>
  	<res-type>javax.sql.DataSource</res-type>
  	<res-auth>Container</res-auth>
  </resource-ref>
</web-app>
```
