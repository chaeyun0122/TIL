package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//클라이언트의 요청을 받아서 분석하고 처리함
//처리된 결과는 JSP 페이지를 선택하여 forwarding으로 응답(redirect도 가능함)
public class SimpleController extends HttpServlet{
	
	//1. 요청을 받음
	// get이나 post 어떤 요청이 들어와도 processRequest 함수로 넘어간다.
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		processResquest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		processResquest(req, resp);
	}

	//2. 요청 처리
	private void processResquest(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//2-1. 요청 분석(command, URL 등을 기준으로)
		String type = req.getParameter("type"); // type파라미터는 명령어 역할

		//2-2. type에 따라 요청을 처리하여 결과를 준비
		Object resultObject = null; // 처리결과 객체

		// 이 부분을 Model이라고 볼 수 있음(실제 로직 부분)
		if (type == null || type.equals("greeting")) {
			resultObject = "안녕하세요";
		}else if (type.equals("date")) {
			resultObject = new Date(); //java.util.Date
		}else {
			resultObject = "Invalid Type!!";
		}
		
		//2-3. 처리 결과를 request 또는 session과 같은 객체에 속성으로 담기(뷰에 전달할 데이터)
		//전달할 값이 없는 경우 안할 수 도 있고, redirect응답을 줄 수도 있음
		req.setAttribute("result", resultObject);
		
		//2-4.적절한 뷰로 JSP파일을 선택하여 forwarding
		//redirect응답을 줄 수도 있음
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher("/WEB-INF/views/simpleView.jsp");
		dispatcher.forward(req, resp); //포워드
		
	}
	
}
