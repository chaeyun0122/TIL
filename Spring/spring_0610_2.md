> - 프로젝트명 : examspring04 ([👉project file](https://github.com/Clary0122/TIL/tree/main/Spring/project/examspring04))


## Java 코드로 설정
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
  ```java
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
### 1. 두 개의 자바 설정 사용
- JavaConfigPart1, JavaConfigPart2 두 개의 자바 설정 파일을 한 번에 사용하도록 한다.
#### JavaConfigPart1
![image](https://user-images.githubusercontent.com/79209568/121806902-bf2fca00-cc8c-11eb-8a6d-9331f9864e74.png)
#### JavaConfigPart2
- JavaConfigPart2에서 MemberInfoPrinter의 경우 MemberDao가 필요하다.
- **방법 1**
  - MemberDao를 Autowired 해준다.
    
    ![image](https://user-images.githubusercontent.com/79209568/121807037-69a7ed00-cc8d-11eb-804d-490bd5e9d75e.png)
 - **방법 2**
  - JavaConfigPart1 설정 파일에 MemberDao가 있기 때문에 JavaConfigPart1을 Autowired 해준다.
    
    ![image](https://user-images.githubusercontent.com/79209568/121807100-b390d300-cc8d-11eb-8753-7bac58118e63.png)

#### Main3
- 두 개의 자바 설정파일을 포함해준다.
![image](https://user-images.githubusercontent.com/79209568/121807184-15e9d380-cc8e-11eb-9931-05259d90e350.png)

### 2. 주 설정파일에 다른 자바 설정 포함
- JavaConfigPartMain 설정 파일에 JavaConfigPartSub 설정 파일을 포함하도록 한다. Main에서는 JavaConfigPartMain만을 사용한다.
#### JavaConfigPartMain
- `@Import` annotation을 통해 다른 설정 파일을 포함하도록 설정한다.
![image](https://user-images.githubusercontent.com/79209568/121807304-87c21d00-cc8e-11eb-9ea4-ddddcec37516.png)

#### JavaConfigPartSub
![image](https://user-images.githubusercontent.com/79209568/121807335-af18ea00-cc8e-11eb-9058-aa8992eb604c.png)

#### Main4
![image](https://user-images.githubusercontent.com/79209568/121807377-d2dc3000-cc8e-11eb-92ac-ccac0ddc26b6.png)

### 3. Java 설정 파일에서 xml 설정 파일을 불러들여서 사용
- JavaMainConfig 설정 파일에서 sub-config.xml 파일을 불러들여서 사용한다. Main에서는 JavaMainConfig만 사용한다.

#### JavaMainConfig
- `@ImportResource` annotation으로 xml 파일을 포함하도록 설정한다.
![image](https://user-images.githubusercontent.com/79209568/121807505-67df2900-cc8f-11eb-83bc-b4fd03130131.png)

#### sub-config.xml
![image](https://user-images.githubusercontent.com/79209568/121807542-87765180-cc8f-11eb-9332-6bc7204aa3ec.png)

#### Main5
![image](https://user-images.githubusercontent.com/79209568/121807555-98bf5e00-cc8f-11eb-9b16-237c888c739b.png)

### 4. xml 설정파일에서 Java 설정파일을 불러들여서 사용
- main-config.xml 설정 파일에서 JavaSubConfig 설정 파일을 불러들여서 사용한다. Main에서는 main-config.xml 만 사용한다.

#### main-config.xml
- `<context:annotation-config/>` : `@Configuration` 을 적용한 클래스를 <bean>태그로 등록
- `<bean class="config.JavaSubConfig"/>` : JavaSubConfig 설정 파일을 지정한다.
![image](https://user-images.githubusercontent.com/79209568/121807642-fd7ab880-cc8f-11eb-9ef8-f56246b7c301.png)

#### JavaSubConfig
![image](https://user-images.githubusercontent.com/79209568/121807766-872a8600-cc90-11eb-9e7f-68787caaaf75.png)

#### Main6
![image](https://user-images.githubusercontent.com/79209568/121807780-96a9cf00-cc90-11eb-9419-ab9453c1e752.png)

