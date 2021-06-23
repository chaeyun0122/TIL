package exam;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("now", new Date()); //jsp파일에 전달 될 값
		return "hello";
	}
}
