# 게시판 만들기
## 게시판 테이블 생성
* sql developer에서 생성
  ```sql
  create table board (
      seq number not null,                -- 글 번호
      id varchar2(30) not null,           -- 아이디
      name varchar2(30) not null,         -- 이름
      subject varchar2(255) not null,     -- 제목
      content varchar2(4000) not null,    -- 내용
      hit number default 0,               -- 조회수
      logtime date default sysdate        -- 작성일
  );
  ```
* 시퀀스 객체 생성
  ```sql
  create sequence board_seq nocache nocycle;
  
  -- drop sequence borad_seq; 시퀀스 삭제
  ```
* board 테스트
  ```sql
  insert into board values (board_seq.nextval, 'testid', '미상', '게시판 테스트', '첫 번째 글', 0, sysdate);
  ```
  > ![image](https://user-images.githubusercontent.com/79209568/116228405-34ecde80-a790-11eb-9978-a97ad26ecb41.png)

## DTO 생성
```java
package boardDTO;

public class BoardDTO {
	
	private int seq;
	private String id;
	private String name;
	private String subject;
	private String content;
	private int hit;
	private String logtime;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
}

```

## board 페이지들 생성
* board 폴더를 만들어서 jsp 파일을 만든다.
#### boardWriteForm.jsp
* 게시판에 글을 작성하는 페이지

#### boardWrite.jsp
* 게시판에 글 작성 후 DB에 처리하는 페이지 




