package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cmd.AddHandler;
import cmd.CommandHandler;
import cmd.DivHandler;
import cmd.HelloHandler;
import cmd.MulHandler;
import cmd.NullHandler;
import cmd.SubHandler;

public class ControllerUsingURI extends HttpServlet{
	
	//핸들러 객체를 담는 컬렉션(컨테이너 같은 개념)
	private Map<String, CommandHandler> commandHandlerMap = 
			new HashMap<>();
	
	//서블릿 생성 시 초기화 메서드 활용(Tomcat에서 서블릿 생성 시 자동 호출됨)
	public void init() throws ServletException{
		//명령에 따라 동작할 핸들러들을 등록
		commandHandlerMap.put("hello", new HelloHandler());
		commandHandlerMap.put("add", new AddHandler());
		commandHandlerMap.put("sub", new SubHandler());
		commandHandlerMap.put("mul", new MulHandler());
		commandHandlerMap.put("div", new DivHandler());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	//2. 요청을 처리
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {

		//요청에서 명령을 가져옴
		//String cmd = req.getParameter("cmd"); 
		
		//요청 URI에서 명령을 가져옴
		String cmd = req.getRequestURI();
		System.out.println("Request URI:" + cmd);
		if(cmd.endsWith(".do")) {
			if(cmd.indexOf(req.getContextPath()) == 0) {
				cmd = cmd.substring(req.getContextPath().length()+1, cmd.length()-3); // 'examjsp02/hello.do'에서 hello만 뽑아냄
			}
		}
		System.out.println("cmd:" + cmd);
		
		//명령에 동작할 핸들러 객체 가져오기
		CommandHandler handler = commandHandlerMap.get(cmd);
		
		if(handler == null) {
			handler = new NullHandler();
		}
		
		String viewPage = null;
		try {
			//비즈니스 로직 처리(Model을 사용하는 개념)
			viewPage = handler.process(req, resp);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		
		//선택된 뷰로 포워드하여 응답
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, resp);
		
	}

}