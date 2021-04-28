# 게시판 만들기 2 (이어서)
* 현재 글쓰기 기능을 구현했고 커낵션 풀로 DB 연결을 해놓은 상태다.

## 글 목록 페이지
#### BoardDTO.java
* 글 목록 리스트를 리턴하는 메서드 추가
* SQL문
  ```sql
  SELECT seq, id, name, subject, content, hit, to_char(logtime, 'YYYY.MM.DD') AS logtime
  FROM    (SELECT rownum rn, tt.*
           FROM (select * from board order by seq desc) tt)
  WHERE rn>=? and rn<=?
  ```
  * seq로 내림차순한 board 테이블의 사본 tt를 rownum을 이용해 번호를 붙인 후, rn이 startNum과 endNum사이의 값인 seq, id, name, subject, content, hit, to_char(logtime, 'YYYY.MM.DD') AS logtime 열의 값을 출력하라는 의미의 구문이다.
  ```java
  public ArrayList<BoardDTO> boardList(int start, int end) {
		// 정렬된 사본 테이블 tt를 쓴다는 것
		// seq로 내림 차순 정렬을 하고 거기에 대해서 rownum을 이용해 rn 번호를 붙이고 select의 컬럼들을 가져온다.
		String sql = "select seq, id, name, subject, content, hit, to_char(logtime, 'YYYY.MM.DD') as logtime from"
				+ "(select rownum rn, tt.* from"
				+ "(select * from board order by seq desc) tt)"
				+ "where rn>=? and rn<=?";
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		BoardDTO boardDTO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			res = pstmt.executeQuery();
			
			// 리스트에 담아준다
			while (res.next()) {
				boardDTO = new BoardDTO();
				boardDTO.setSeq(res.getInt("seq"));
				boardDTO.setId(res.getString("id"));
				boardDTO.setName(res.getString("name"));
				boardDTO.setSubject(res.getString("subject"));
				boardDTO.setContent(res.getString("content"));
				boardDTO.setHit(res.getInt("hit"));
				boardDTO.setLogtime(res.getString("logtime"));
				
				list.add(boardDTO);
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
			
		} return list;
	}
  ```
#### boardList.jsp
