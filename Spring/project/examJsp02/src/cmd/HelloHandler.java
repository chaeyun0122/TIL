package cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cmd.CommandHandler;

public class HelloHandler implements CommandHandler{
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		req.setAttribute("hello", "안녕하세요");
		return "/WEB-INF/views/hello.jsp";
	}
	
}
