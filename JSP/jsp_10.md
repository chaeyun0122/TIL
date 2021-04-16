# 학교의 행정 관리 프로그램 만들기
## DTO
```java
package studentDTO;

public class StudentDTO {
		private String name;
		private String value;
		private int code;
		
		public StudentDTO() {}
		
		public StudentDTO(String name, String value, int code) {
			this.name = name;
			this.value = value;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}
}
```
> ### Getter/Setter 쉽게 만드는 법
> * `오른쪽 클릭 > Source > Generate Getters and Setters`
>   
> ![image](https://user-images.githubusercontent.com/79209568/115011895-1feb9200-9eea-11eb-86d6-fb0d599b7017.png)

# DAO
```java
package studentDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import studentDTO.StudentDTO;

public class StudentDAO {
	public StudentDAO() {
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
	
	public int insert(StudentDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int su = 0;
		
		String sql = "insert into school values (?, ?, ?)";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getValue());
			pstmt.setInt(3, dto.getCode());
			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}
}

```

# StudentManager
