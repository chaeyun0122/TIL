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
	
	//�ڵ鷯 ��ü�� ��� �÷���(�����̳� ���� ����)
	private Map<String, CommandHandler> commandHandlerMap = 
			new HashMap<>();
	
	//���� ���� �� �ʱ�ȭ �޼��� Ȱ��(Tomcat���� ���� ���� �� �ڵ� ȣ���)
	public void init() throws ServletException{
		//��ɿ� ���� ������ �ڵ鷯���� ���
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

	//2. ��û�� ó��
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {

		//��û���� ����� ������
		//String cmd = req.getParameter("cmd"); 
		
		//��û URI���� ����� ������
		String cmd = req.getRequestURI();
		System.out.println("Request URI:" + cmd);
		if(cmd.endsWith(".do")) {
			if(cmd.indexOf(req.getContextPath()) == 0) {
				cmd = cmd.substring(req.getContextPath().length()+1, cmd.length()-3); // 'examjsp02/hello.do'���� hello�� �̾Ƴ�
			}
		}
		System.out.println("cmd:" + cmd);
		
		//��ɿ� ������ �ڵ鷯 ��ü ��������
		CommandHandler handler = commandHandlerMap.get(cmd);
		
		if(handler == null) {
			handler = new NullHandler();
		}
		
		String viewPage = null;
		try {
			//����Ͻ� ���� ó��(Model�� ����ϴ� ����)
			viewPage = handler.process(req, resp);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		
		//���õ� ��� �������Ͽ� ����
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, resp);
		
	}

}