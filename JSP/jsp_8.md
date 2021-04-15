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
### Connection 객체 생성 : java.sql.Connection
- 연결 관리를 할 수 있는 객체를 생성한다.
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

## 쿼리 실행
### PreparedStatement 객체 생성 : java.sql.PreparedStatement
- 쿼리 실행을 위한 객체를 생성한다.

### 쿼리 실행 메서드
- int executeUpdate()
  - insert, delete, update 문에서 실행 결과를 받을 때 사용하는 메서드
- ResultSet executeQuery()
  - select 문에서 실행

# JDBC 전체 프로그램 실행 단계
1. JDBC 드라이버 인스턴스 생성
2. Connection 객체 생성 - 연결 관리 JDBC 드라이버 인스턴스를 통해서 DBMS에 연결
3. Statement 생성 - 쿼리문 실행을 위한 객체
4. 쿼리 실행
5. ResultSet 종료
6. Statement 종료
7. Connection 종료

# Insert (전체코드)
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
			String sql = "insert into dbtest values(?, ?, ?, sysdate)"; // 미완성 쿼리문; 물음표 자리 1부터 시작(0아님)
			pstmt = con.prepareStatement(sql); // 연결객체를 사용해서 쿼리문 전송할 객체에 쿼리문을 넣음
			pstmt.setString(1, name);
			pstmt.setInt(2, age);
			pstmt.setDouble(3, height);
			su = pstmt.executeUpdate();  // update 진행하고 성공한 레코드 수를 변수에 담는다
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 모든 실행되었던 자원들을 닫아준다.
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

# Select
```sql
public void selectArticle() {
	Connection con = getConnection();
	PreparedStatement pstmt = null;
	ResultSet res = null;

	try {
		String sql = "select * from dbtest";
		pstmt = con.prepareStatement(sql);
		res = pstmt.executeQuery();

		while (res.next()) {
			String name = res.getString("name");
			int age = res.getInt("age");
			double height = res.getDouble("height");
			String logtime = res.getString("logtime");

			//출력
			System.out.println(name + "\t" + age + "\t" + height + "\t" + logtime);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally { // 모든 실행되었던 자원들을 닫아준다.
		try {
			if (res != null) res.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
```

## 결과
> ### 콘솔 창
  ![image](https://user-images.githubusercontent.com/79209568/114857258-06334780-9e23-11eb-883e-989038a243e0.png)

# Update
```sql
public void updateArticle() {
	Scanner scanner = new Scanner(System.in);
	System.out.print("수정 이름 입력 > ");
	String name = scanner.next();

	Connection con = getConnection();
	PreparedStatement pstmt = null;
	int su = 0;

	try {
		String sql = "update dbtest set age=age+1 where name like ?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "%"+name+"%"); //해당 이름이 있는 레코드는 다 수정
		su = pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	} finally { // 모든 실행되었던 자원들을 닫아준다.
		try {
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
```
  
  
## 결과
> ### 콘솔 창
  ![image](https://user-images.githubusercontent.com/79209568/114857568-60340d00-9e23-11eb-980a-eb4521ccddce.png)
> ### DB 확인
> * 이름에 test가 들어가는 두 레코드의 나이가 +1 되었다.
  
  ![image](https://user-images.githubusercontent.com/79209568/114857690-85c11680-9e23-11eb-81fe-279b60e2aff0.png)
  
# Delete
```sql
public void deleteArticle() {
	Scanner scanner = new Scanner(System.in);
	System.out.print("삭제 이름 입력 > ");
	String name = scanner.next();

	Connection con = getConnection();
	PreparedStatement pstmt = null;
	int su = 0;

	try {
		String sql = "delete dbtest where name=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, name);
		su = pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	} finally { // 모든 실행되었던 자원들을 닫아준다.
		try {
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	System.out.println(su + "개의 행이 삭제되었습니다.");
}
```
  
  
## 결과
> ### 콘솔 창
  ![image](https://user-images.githubusercontent.com/79209568/114861093-c6bb2a00-9e27-11eb-9058-6e630d634395.png)
  
> ### DB 확인
> * 이름이 kim인 레코드가 삭제되었다.
  
  ![image](https://user-images.githubusercontent.com/79209568/114861171-ddfa1780-9e27-11eb-86dd-ab643bde182e.png)

