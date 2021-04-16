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

## DAO
```java
package studentDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public boolean insert(StudentDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		String sql = "insert into school values (?, ?, ?)";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getValue());
			pstmt.setInt(3, dto.getCode());
			int su = pstmt.executeUpdate();
			if (su != 0) {
				check = true;
			}
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
		return check;
	} // insert() end
	
	public void select(StudentDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		String sql = null;
		
		if(dto == null) { // 전체 목록
			sql = "select * from school";
		} else if(dto.getName() != null) { // 이름이 들어간 데이터 목록
			sql = "select * from school where name like ?";
		} else { // 코드번호 목록
			sql = "select * from school where code=?";
		}
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			if(dto != null) {
				if(dto.getName() != null) {
					pstmt.setString(1, "%"+dto.getName()+"%");
				} else {
					pstmt.setInt(1, dto.getCode());
				}
			}
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String name = res.getString("name");
				String value = res.getNString("value");
				int code = res.getInt("code");
				System.out.println("이름 : "+name+"\t");
				if(code == 1) {
					System.out.println("학번 : "+value);
				} else if(code == 2) {
					System.out.println("과목 : "+value);
				} else {
					System.out.println("부서 : "+value);
				}
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
	
	public boolean delete(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean check = false;
		
		String sql = "delete school where name=?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			int su = pstmt.executeUpdate();
			if (su != 0) {
				check = true;
			}
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
		return check;
	}
	
	
	public List<StudentDTO> getList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		
		String sql = "select * from school";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String name = res.getString("name");
				String value = res.getString("value");
				int code = res.getInt("code");
				
				StudentDTO dto = new StudentDTO(name, value, code);
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) res.close();
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(list.isEmpty())
			list = null;
		return list;
	} // getList() end
}

```

## StudentManager
```java
import java.util.List;
import java.util.Scanner;

import studentDAO.StudentDAO;
import studentDTO.StudentDTO;

/*
 * create table school (
 * name varchar2(20) not null,	-- 이름
 * value varchar2(20),			-- 학생 -> 학번, 교수 -> 과목, 관리자 -> 부서
 * code number					-- 1. 학생, 2. 교수, 3. 관리자
 * );
 */

public class StudentManager {
	private Scanner scanner = new Scanner(System.in);
	
	public StudentManager() {
		menu();
	}
	
	private void menu() {
		while(true) {
			System.out.println("----- 관  리 -----");
			System.out.println("1.입력  2.검색  3.삭제  4.목록  0.종료");
			System.out.println("선택 >> ");
			int num = scanner.nextInt();
			
			switch(num) {
			case 1:  // 입력
				insert(); break;
			case 2:  // 검색
				search(); break;
			case 3:  // 삭제
				delete(); break;
			case 4:  // 목록
				list(); break;
			case 0:  // 종료
				exit();
			default:
				System.out.println("선택 오류");
			}
			System.out.println();
		}
	} // menu() end
	
	private int codeInput()	{
		System.out.println("----- Code 선택 -----");
		System.out.println("1.학생  2.교수  3.관리자  4.이전");
		System.out.println("선택 >> ");
		int code = scanner.nextInt();
		return code;
	}
	
	private String valueInput(int code) {
		if (code == 1)
			System.out.println("학번 입력 > ");
		else if (code == 2)
			System.out.println("과목 입력 > ");
		else
			System.out.println("부서 입력 > ");
		String value = scanner.next();
		return value;
	}
	
	private void insert() {
		System.out.println("----- 데이터 추가 -----");
		int code = codeInput();
		if (code < 1 || code > 3) {
			System.out.println("이전 메뉴로 이동합니다.");
			return;
		}
		System.out.print("이름 입력 > ");
		String name = scanner.next();
		String value = valueInput(code);
		
		StudentDTO dto = new StudentDTO(name, value, code);
		StudentDAO dao = new StudentDAO();
		boolean check = dao.insert(dto);
		if (check) {
			System.out.println(name + "이(가) 추가 되었습니다.");
		} else {
			System.out.println("추가 실패");
		}
	} // insert() end
	
	private void search() {
		System.out.println("----- 데이터 검색 -----");
		System.out.println("1.이름  2.코드  3.전체");
		System.out.println("선택 >> ");
		int num = scanner.nextInt();
		if(num < 1 || num > 3) {
			System.out.println("선택 오류");
			System.out.println("이전 메뉴로 이동합니다.");
			return;
		}
		
		StudentDTO dto = null;
		if(num == 1) {
			dto = new StudentDTO();
			System.out.print("이름 입력 > ");
			dto.setName(scanner.next());
		} else if(num == 2) {
			int code = codeInput();
			if(code < 1 || code > 3) {
				System.out.println("code 선택 오류");
				System.out.println("이전 메뉴로 돌아갑니다.");
				return;
			}
			dto = new StudentDTO();
			dto.setCode(code);
		}
		
		StudentDAO dao = new StudentDAO();
		dao.select(dto);
		
	} // search() end
	
	private void delete() {
		System.out.println("----- 데이터 삭제 -----");
		System.out.print("이름 입력 > ");
		String name = scanner.next();
		
		StudentDAO dao = new StudentDAO();
		boolean check = dao.delete(name);
		if (check)
			System.out.println(name+"이(가) 삭제되었습니다.");
		else
			System.out.println("삭제 실패");
	} // delete() end
	
	private void list() {
		StudentDAO dao = new StudentDAO();
		List schoolList = dao.getList(); // 리스트에 담아서 반환시킴
		String title = String.format("%10s %10s %10s", "NAME", "VALUE", "CODE");
		System.out.println(title);
		for (int i=0; i<schoolList.size(); i++) {
			StudentDTO man = (StudentDTO)schoolList.get(i);
			String data = String.format("%10s %10s %10d", man.getName(), man.getValue(), man.getCode());
			System.out.println(data);
		}
	} // list() end
	
	private void exit() {
		System.out.println("- Program end -");
		scanner.close();
		System.exit(0);  // 해당 위치에서 강제 종료
	} // exit() end
} 

```

## StudentMain
```jqva
public class StudentMain {
	public static void main(String[] args) {
		StudentManager stm = new StudentManager();
	}
}
```
