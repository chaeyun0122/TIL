package spring;

import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainForSpring {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		while(true) {
			System.out.print("명령어 입력>");
			String command = in.nextLine();
			if(command.equalsIgnoreCase("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			if(command.startsWith("new")) {
				processNewCommand(command.split(" "));
				continue;
			}else if(command.startsWith("change")) {
				processChangeCommand(command.split(" "));
				continue;
			}else if(command.startsWith("list")) {
				processListCommand(command.split(" "));
				continue;
			}else if(command.startsWith("info")) {
				processInfoCommand(command.split(" "));
				continue;
			}else if(command.startsWith("version")) {
				processVersionCommand(command.split(" "));
				continue;
			}
		}
	}




	// 사용법 
	private static void printHelp() {
		System.out.println();
		System.out.println("사용법 확인");
		System.out.println("Usage:");
		System.out.println("새로운 유저 : new <이메일> <이름> <암호> <암호확인>");
		System.out.println("비밀번호 수정 : change <이메일> <현재암호> <새로운암호>");
		System.out.println("유저 리스트 : list");
		System.out.println("유저 정보 : info <이메일>");
		System.out.println("버전 정보 : version");
		System.out.println();
	}
	
	// ctx 생성
	private static ApplicationContext ctx
		= new GenericXmlApplicationContext("classpath:applicationContext.xml");
	
	// 사용자 생성
	private static void processNewCommand(String[] args) {
		//new email name 1234 1234
		if(args.length != 5) {
			printHelp();
			return;
		}
		MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
		
		RegisterRequest req = new RegisterRequest();
		req.setEmail(args[1]);
		req.setName(args[2]);
		req.setPassword(args[3]);
		req.setConfirmPassword(args[4]);
		
		if(!req.isPasswordEqualToConfirmPassword()) {
			System.out.println("암호와 확인이 일치하지 않습니다.");
			return;
		}
		try {
			regSvc.regist(req);
		}catch(AlreadyExistingMemberException e) {
			System.out.println("이미 존재하는 이메일입니다.");
		}
		System.out.println("등록이 완료되었습니다.");
	}
	
	// 비밀번호 변경
	private static void processChangeCommand(String[] args) {
		if(args.length != 4) {
			printHelp();
			return;
		}
		ChangePasswordService pwdSvc = ctx.getBean("changePwdSvc", ChangePasswordService.class);
		
		try {
			pwdSvc.changePassword(args[1], args[2], args[3]);
			System.out.println("암호가 변경되었습니다.");
		}catch(MemberNotFoundException e) {
			System.out.println("멤버가 존재하지 않습니다.");
		}catch(IdPasswordNotMatchingException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다.");
		}
		
	}
	
	// 사용자 리스트 출력
	private static void processListCommand(String[] args) {
		MemberListPrinter memberListPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
		memberListPrinter.printAll();
	}
	
	// 정보 출력
	private static void processInfoCommand(String[] args) {
		if(args.length != 2) {
			printHelp();
			return;
		}
		MemberInfoPrinter memberInfoPrinter = 
				ctx.getBean("infoPrinter", MemberInfoPrinter.class);
		memberInfoPrinter.printMemberInfo(args[1]);
	}
	
	// 버전 정보
	private static void processVersionCommand(String[] split) {
		VersionPrinter vp = ctx.getBean("versionPrinter", VersionPrinter.class);
		vp.print();
		
	}
}