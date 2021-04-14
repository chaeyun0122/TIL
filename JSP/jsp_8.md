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
  ```java
  Class.forname("oracle.jdbc.OracleDriver");
  ```

  > ### 새 클래스 생성
  > - `Java Resources > New > Class`
  >  
  >  ![image](https://user-images.githubusercontent.com/79209568/114706640-2b14b580-9d64-11eb-905e-cf0de23986b3.png)
  > - Package : `ch01_driver`
  > - Name : `DriverConnect`  
  >     
  >   ![image](https://user-images.githubusercontent.com/79209568/114706571-19331280-9d64-11eb-87c6-1d6296bf49ff.png)
  >   
  > ### 실행  
  > 1. 오른쪽 상단 perspective를 `java`로 변경  
  > 2. `CTRL + F11` → Java Application 으로 실행  
  >   ![image](https://user-images.githubusercontent.com/79209568/114707997-cfe3c280-9d65-11eb-9e86-5cdd30e2483b.png)  

```java
// 오라클 드라이브 로딩
try {
  Class.forName("oracle.jdbc.OracleDriver");
  System.out.println("로딩 성공");
} catch (ClassNotFoundException e) {
  System.out.println("로딩 실패");
  e.printStackTrace();
}
```
> ![image](https://user-images.githubusercontent.com/79209568/114710121-526d8180-9d68-11eb-8bfb-a11378a6b2b3.png)

## 드라이브 연결
### Connection : java.sql.Connection
- 연결 관리를 할 수 있는 객체를 생생한다.
- java.sql.DriverManager 클래스 안에 getConnection 메서드를 사용해서 생성
  - url		: DB의 위치와 정보를 담고 있는 데이터
    - jdbc : `DBMS명 : 데이터베이스 식별자 (host, port, sid ...)`
    - jdbc:oracle:드라이버:@HOST:PORT:SID
    - jdbc:oracle:thin:@localhost:1521:xe
   - user		: DB 접속 계정
   - password : 접속 계정 비밀번호

```java
// 드리이브 연결
String url = "jdbc:oracle:thin:@localhost:1521:xe";
String user = "c##dbtest";
String pwd = "a1234";

Connection con = null;
try {
  con = DriverManager.getConnection(url, user, pwd);
  System.out.println("연결성공");
} catch (Exception e) {
  System.out.println("연결실패");
  e.printStackTrace();
}
```
> ![image](https://user-images.githubusercontent.com/79209568/114713567-3966cf80-9d6c-11eb-8c8b-b8b5e1c4a09a.png)

# 전체 코드
```java
package ch02_insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

class InsertTest {
	// 생성자
	public InsertTest() {
		// 오라클 드라이브 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("로딩 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("로딩 실패");
			e.printStackTrace();
		}
	}
	
  // 연결이 잘 되었는지 확인 먼저 해본다.
	public Connection getConnection() {
		// 드리이브 연결
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "c##dbtest";
		String pwd = "a1234";
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("연결성공");
		} catch (Exception e) {
			System.out.println("연결실패");
			e.printStackTrace();
		}
		return con;
	}
	
	// java코드로 db에 레코드를 저장
	public void insertArticle() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("이름 입력 > ");
		String name = scanner.next();
		System.out.print("나이 입력 > ");
		int age = scanner.nextInt();
		System.out.print("키   입력 > ");
		double height = scanner.nextDouble();
		
		
		Connection con = getConnection(); // DB 연결 객체
		PreparedStatement pstmt = null; // SQL문 전송할 객체
		int su = 0; // 반환 값 : 추가 성공한 레코드 수
		
		try {
			String sql = "insert into dbtest values(?, ?, ?, sysdate)";
			pstmt = con.prepareStatement(sql); // 연결객체를 사용해서 쿼리문 전송할 객체에 쿼리문을 넣음
			pstmt.setString(1, name);
			pstmt.setInt(2, age);
			pstmt.setDouble(3, height);
			su = pstmt.executeUpdate();  // update 진행하고 성공한 레코드 수를 변수에 담는다
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(su + "개의 행이 추가되었습니다.");
	}
}


public class Insert {
	public static void main(String[] args) {
		InsertTest it = new InsertTest();
		it.insertArticle();
	}
}
```
## 결과
> ### 콘솔 창
  ![image](https://user-images.githubusercontent.com/79209568/114714091-bc882580-9d6c-11eb-8586-a7104567f5db.png)
> ### DB 확인
> * 레코드가 잘 추가 되었다.
  ![image](https://user-images.githubusercontent.com/79209568/114714284-ee00f100-9d6c-11eb-8b91-a2e75b49586a.png)

