package exam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/prac")
public class Practice1 {
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "add";
	}
	
//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	public String addResult(HttpServletRequest req, Model model) {
//		int n1 = Integer.parseInt(req.getParameter("n1"));
//		int n2 = Integer.parseInt(req.getParameter("n2"));
//		int result = n1 + n2;
//				
//		model.addAttribute("n1", n1);
//		model.addAttribute("n2", n2);
//		model.addAttribute("result", result);
//		return "addResult";
//	}
	
	// request를 바로 가져올 수 있다.
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addResult(int n1, int n2, Model model) {
		int result = n1 + n2;
				
		model.addAttribute("n1", n1);
		model.addAttribute("n2", n2);
		model.addAttribute("result", result);
		return "addResult";
	}
	
	@RequestMapping(value = "/factorial", method = RequestMethod.GET)
	public String factorial() {
		return "factorial";
	}
	
	@RequestMapping(value = "/factorial", method = RequestMethod.POST)
	public String factorialResult(HttpServletRequest req, Model model) {
		int n = Integer.parseInt(req.getParameter("n"));
		int result = 1;
		for (int i = n; i>=1; i--) {
			result = result * i;
		}
			
		model.addAttribute("n", n);
		model.addAttribute("result", result);
		return "factorialResult";
	}
}
