package cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandHandler {
	public String process(HttpServletRequest req, HttpServletResponse resp)
	throws Throwable;
}

/* 
 * 클라이언트의 요청을 처리하는 클래스는 모두 구현
 * 1. 명령어와 관련된 비즈니스 로직 처리
 * 2. 뷰(JSP)페이지에서 사용할 값을 저장
 * 3. 뷰(JSP)페이지의 URI를 반환
 */ 
