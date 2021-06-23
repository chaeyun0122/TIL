package exam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ex")
public class ExamController {
	
	/*
	 * 클라이언트로부터 /ex/exam1 이라는 요청이 들어오면
	 * 스프링이 exam1()메서드를 호출!
	 * exam1()동작 후 반환하는 문자열에 따라 뷰를 선택하여 처리하고 응답
	 */
	
	@RequestMapping("/exam1")
	public String exam1() {
		return "exam1";
	}
	
	@RequestMapping("/exam2")
	public String exam2(HttpServletRequest req, Model model) {
		String name = req.getParameter("name");
		int age = Integer.parseInt(req.getParameter("age"));
		
		// 서버 콘솔에서 파라미터 값 확인
		System.out.println("name:" + name);
		System.out.println("age:" + age);
		
		// jsp 파일에 값을 전달하기 위해 model객체에 값 세팅
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		// forward!
		return "exam2";
	}
	
	@RequestMapping(value = "/exam3", method = RequestMethod.GET)
	public String exam3() {
		return "exam3";
	}
	
	@RequestMapping(value = "/exam3", method = RequestMethod.POST)
	public String exam4(HttpServletRequest req, Model model) {
		String name = req.getParameter("name");
		int age = Integer.parseInt(req.getParameter("age"));
		
		// 서버 콘솔에서 파라미터 값 확인
		System.out.println("name:" + name);
		System.out.println("age:" + age);
		
		// jsp 파일에 값을 전달하기 위해 model객체에 값 세팅
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		// forward!
		return "exam4";
	}
}
