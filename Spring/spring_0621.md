# 톰캣
- 톰캣 8버전 다운로드
https://tomcat.apache.org/download-80.cgi
- 이클립스에서 동작하도록 설정
  - `Window > Preference` 설정의 `Server > Runtime Environment > Add`  
    ![image](https://user-images.githubusercontent.com/79209568/122705064-eccdd200-d28f-11eb-95ab-bace11cc528e.png)
  - 해당 내용으로 선택  
    ![image](https://user-images.githubusercontent.com/79209568/122705555-f60b6e80-d290-11eb-9174-002cfde98e99.png)
- 프로젝트 `examjsp01` 생성 
  - `New > Dynamic Web Project`  
    ![image](https://user-images.githubusercontent.com/79209568/122705645-1dfad200-d291-11eb-9bfd-c937e56313e2.png)

- servlet : 웹 서비스 요청을 처리해주는 클래스
- MVC : "뷰"-보여주는 역할(JSP) - "컨트롤러"-요청을 받아서 분류(서블릿) - "모델"-컨트롤러를 통해 처리를 요청받음

# Servlet
- 서블릿 클래스 생성 (HttpServlet을 extend)  
  ![image](https://user-images.githubusercontent.com/79209568/123097109-c0ba7880-d46a-11eb-8702-551130cbce03.png)
- web.xml 만들기  
  ![image](https://user-images.githubusercontent.com/79209568/122710257-e1cc6f00-d29a-11eb-997a-8973b3ddac38.png)\
  ![image](https://user-images.githubusercontent.com/79209568/122710262-e729b980-d29a-11eb-9ea7-5cf1f8d18cb2.png)
- 서블릿 등록과 매핑  
  ![image](https://user-images.githubusercontent.com/79209568/122710301-f3ae1200-d29a-11eb-87f2-5f92e8305bfe.png)
- 프로젝트에 `Run As > Run on Server`  
  ![image](https://user-images.githubusercontent.com/79209568/122710393-1d673900-d29b-11eb-87c1-d3ce7b84ee9e.png)
- 결과  
  ![image](https://user-images.githubusercontent.com/79209568/122710418-28ba6480-d29b-11eb-8f52-6fc3f136154a.png)

----------
- WEB-INF아래에 jsp 파일을 넣는 이유
웹에서 직접 요청이 불가능하게 만들기 위해 WEB-INF아래에 둔다. 서블릿을 통해서만 응답하도록한다.
