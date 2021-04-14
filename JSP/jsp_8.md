# JDBC (Java Database Connectivity)
- java와 database의 연동을 위한 API
- java.sql 패키지안에 구현되어 있음

## JDBC 드라이버 로딩
- DBMS마다 발도의 드라이버가 필요
- JAR 파일 형태로 DBMS마다 기본적으로 제공
- 드라이버 위치
  - 18c  
    `C:\app\사용자명\product\18.0.0\dbhomeXE\jdbc\ojdbc8.jar`
  - 11g  
    `C:\oraclexe\app\oracle\product\11.x.x\server\jdbc\lib\ojdbc6.jar`
- 프로젝트의 build path를 사용해서 추가
  - `프로젝트명 오른쪽 클릭 > Build path > configure build path`  
  
    ![image](https://user-images.githubusercontent.com/79209568/114704864-0ae3f700-9d62-11eb-86b4-8dae687c1465.png)
  - `Java Build Path > Libraries 탭 > Add External JARs 버튼 > ojdbc8.jar파일 경로 선택 > Apply`  
  
    ![image](https://user-images.githubusercontent.com/79209568/114705143-5f877200-9d62-11eb-810b-9ea89b5d8c4b.png)
  
- Class.forname("JDBC 클래스 이름");  
`oracle.jdbc.
