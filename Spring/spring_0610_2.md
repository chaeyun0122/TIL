## Java 코드로 Spring 설정
- config패키지/JavaConfig.java
  ```java
  package config;

  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;

  import spring.MemberDao;
  import spring.MemberInfoPrinter;
  import spring.MemberPrinter;
  import spring.MemberRegisterService;

  @Configuration
  public class JavaConfig {
    @Bean
    public MemberDao memberDao() {
      return new MemberDao();
    } // <bean id = "memberDao" class="spring.MemberDao"/> 와 같은 개념
    // 생성된 memberDao가 있다면 새로 만들지 않고 한 번만 만든다.

    @Bean
    public MemberRegisterService memberRegSvc() {
      return new MemberRegisterService(memberDao()); //명시적 의존 주입
    }

    @Bean
    public MemberPrinter printer() {
      return new MemberPrinter();
    }

    @Bean
    public MemberInfoPrinter infoPrinter() {
      MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
      infoPrinter.setMemberDao(memberDao());
      infoPrinter.setMemberPrinter(printer());
      return infoPrinter;
    }
  }
  ```
- spring패키지/Main.java
  ```
  package spring;

  import org.springframework.context.ApplicationContext;
  import org.springframework.context.annotation.AnnotationConfigApplicationContext;

  import config.JavaConfig;

  public class Main {
    public static void main(String[] args) {
      // xml이 아니라 java 코드를 사용할 것이기 때문에 Annotation~을 사용한다.
      ApplicationContext ctx = 
          new AnnotationConfigApplicationContext(JavaConfig.class);
      MemberRegisterService regSvc = 
          ctx.getBean("memberRegSvc", MemberRegisterService.class);
      MemberInfoPrinter infoPrinter = 
          ctx.getBean("infoPrinter", MemberInfoPrinter.class);

      RegisterRequest regReq = new RegisterRequest();
      regReq.setEmail("aaa@aaa.com");
      regReq.setName("홍길동");
      regReq.setPassword("1234");
      regReq.setConfirmPassword("1234");

      regSvc.regist(regReq); //등록

      infoPrinter.printMemberInfo("aaa@aaa.com");
    }
  }
  ```

## 자동 주입
- Main3 - JavaConfigPart1, JavaConfigPart2 : 두 개의 자바 설정 사용
- Main4 - JavaConfigPartMain, JavaConfigPartSub : Main 설정 파일에 Sub 파일을 추가해서 사용
- Main5 - sub-config.xml, JavaMainConfig : java 설정파일에서 xml 설정파일 불러들여서 사용
- Main6 - main-config.xml, JavaSubConfig : xml 설정파일에서 java 설정파일을 불러들여서 사용
