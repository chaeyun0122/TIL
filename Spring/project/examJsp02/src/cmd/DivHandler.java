package cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DivHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		String method = req.getMethod();
		String view = "/WEB-INF/views/divForm.jsp";
		if(method.equals("POST")) {
			int n1 = Integer.parseInt(req.getParameter("n1"));
			int n2 = Integer.parseInt(req.getParameter("n2"));
			int ret = n1 / n2;
			req.setAttribute("n1", n1);
			req.setAttribute("n2", n2);
			req.setAttribute("result", ret);
			view = "/WEB-INF/views/divResult.jsp";
		}
		return view;
	}

}
