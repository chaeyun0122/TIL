# JavaBean
- 데이터를 표현하는 목적으로 작성하는 class
  - DB의 데이터 처리
- 뷰 페이지와 로직 페이지를 서로 분리하기 위해서 만들어지는 class
- 하나의 테이블에는 **DTO(VO), DAO**가 한 세트로 만들어 진다.
  - **DTO (Data Transfer Object) = VO (Value Object)** : 데이터를 DB로 보내거나 DB로부터 레코드를 가져올 때 하나의 객체로 만드는 class
  - **DAO (Data Access Object)** : 객체화 된 DTO를 가지고 DB에 접속 시 사용되는 메서드를 정의한 class

## JavaBean 규칙
1. class는 반드시 특정 package 소속이어야 한다.
2. DTO의 멤버필드(변수)를 property라고도 한다.
3. property의 접근 지정자는 private으로 설정해야 한다.
4. 멤버 필드마다 별도의 getter/setter 메서드가 있어야 한다.
5. getter/setter 메서드의 접근 지정자는 public으로 설정해야 한다.//
6. 매개변수가 없는 기본생성자가 있어야 한다.

## DTO
```sql
/*
package : DBtestDTO
name : DBtestDTO
*/

package DBtestDTO;

import java.sql.Date;

public class DBtestDTO {
	
	// DTO의 property의 접근 지정자는 private으로 설정해야 한다.
	private String name;
	private int age;
	private double height;
	private Date date;
	
	// 매개변수가 없는 기본생성자가 있어야 한다.
	public DBtestDTO() { }
	
	public DBtestDTO(String name, int age, double height) {
		this.name = name;
		this.age = age;
		this.height = height;
	}
	
	// getter/setter 메서드의 접근 지정자는 public으로 설정해야 한다.
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public int getAge() { return age; }
	public void setAge(int age) { this.age = age; }
	
	public double getHeight() { return height; }
	public void setHeight(double height) { this.height = height; }
	
	public Date getDate() { return date; }
	public void setDate(Date date) { this.date = date; }
}

```
  
  
## DAO
```sql
/*
package : DBtestDAO
name : DBtestDAO
*/

package DBtestDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBtestDTO.DBtestDTO;

public class DBtestDAO {
	// 생성자에서 드라이브 로딩
	public DBtestDAO() {
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
	
	public int insert(DBtestDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int su = 0;
		
		String sql = "insert into dbtest values (?, ?, ?, sysdate)";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getAge());
			pstmt.setDouble(3, dto.getHeight());
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
		return su;
	} // insert() end
	
	public void select() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		String sql = "select * from dbtest";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String name = res.getString("name");
				int age = res.getInt("age");
				double height = res.getDouble("height");
				String logtime = res.getString("logtime");
				
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
	} // select() end
	
	public int update(DBtestDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int su = 0;
		
		String sql = "update dbtest set age=?, height=? where name=?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getAge());
			pstmt.setDouble(2, dto.getHeight());
			pstmt.setString(3, dto.getName());
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
	} // update() end
	
	public int delete(String name) {  // dto로 해도 됨
		Connection con = null;
		PreparedStatement pstmt = null;
		int su = 0;
		
		String sql = "delete dbtest where name=?";
		
		try {
			con = getConnection();
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
	} // delete() end
}
```  
  
# 동작 시켜보기
## DBtestManger
- 작성한 DTO, DAO를 이용해서 DB 관리 페이지를 만들어본다.  
  
```sql
/*
package : 없음
name : DBtestManger
*/

import java.util.Scanner;

import DBtestDAO.DBtestDAO;
import DBtestDTO.DBtestDTO;

public class DBtestManager {
	private final Scanner scanner = new Scanner(System.in);
	
	public DBtestManager() {
		menu();
	}
	
	public void menu() {
		boolean run = true;
		while(run) {
			System.out.println("1.추가  2.수정  3.삭제  4.목록");
			System.out.print("선택 >> ");
			int num = scanner.nextInt();
			
			switch(num) {
			case 1: //추가
				insert(); break;
			case 2: //수정
				update(); break;
			case 3: //삭제
				//delete(); break;
			case 4: //목록
				list(); break;
			case 0: //종료
				System.out.println("- Program end -");
			default:
				System.out.println("선택 오류");
			}
			System.out.println();
		}
	}// menu() end
	
	public void insert() {
		System.out.println("--- DB 추가 ---");
		System.out.println("이름 입력 > ");
		String name = scanner.next();
		System.out.println("나이 입력 > ");
		int age = scanner.nextInt();
		System.out.println("키    입력 > ");
		double height = scanner.nextDouble();
		
		DBtestDTO dto = new DBtestDTO(name, age, height);
		DBtestDAO dao = new DBtestDAO();
		int res = dao.insert(dto);
		if (res > 0) {
			System.out.println(name + "이(가) 추가 되었습니다.");
		} else {
			System.out.println("추가 실패");
		}
	} // insert() end
	
	public void update() {
		System.out.println("--- DB 수정 ---");
		System.out.println("이름 입력 > ");
		String name = scanner.next();
		System.out.println("나이 입력 > ");
		int age = scanner.nextInt();
		System.out.println("키    입력 > ");
		double height = scanner.nextDouble();
		
		DBtestDTO dto = new DBtestDTO(name, age, height);
		DBtestDAO dao = new DBtestDAO();
		int res = dao.update(dto);
		if (res > 0) {
			System.out.println(name + "이(가) 수정 되었습니다.");
		} else {
			System.out.println("수정 실패");
		}
	}
	
	public void list() {
		System.out.println("--- DB 목록 ---");
		
		DBtestDAO dao = new DBtestDAO();
		dao.select();
	} // list() end
	

}
```

## DBMain
- 관리 페이지를 실행
```sql
/*
package : 없음
name : DBMain
*/

public class DBMain {
	public static void main(String[] args) {
		DBtestManager dbm = new DBtestManager();
	}
}
```

## 결과
> ### 콘솔 창
  ![image](https://user-images.githubusercontent.com/79209568/114873401-36d0ac80-9e36-11eb-9aac-b28b310ab4de.png)
  
> ### DB 확인
> * 레코드가 추가 되었다.
  
  ![image](https://user-images.githubusercontent.com/79209568/114873524-52d44e00-9e36-11eb-81fb-2a93ae28c5fd.png)
