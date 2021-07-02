# 최종정리
> 시작 전 설정해 놓은 프로젝트
- spring버전을 올려준다(Getmapping을 사용하기 위해서)

## 컨트롤러 생성
#### BoardController
- board.controller 패키지

<hr>
- 브라우저에서 /board/list 요청
- BoardController의 list()가 동작
- list()는 BoardService의 list()를 호출
- BoardService의 list는 BoardDao의 selectAll() 호출
- selectAll()은 SqlSessionTemplate의 selectList를 호출("list")
- 이제부터 마이 바티스의 영역
  - sqlmap-board.xml에 정의된 list라는 아이드를 가진 쿼리를 사용
  - 결과를 resultType에 선언된 타입으로 List를 반환
- selectList("list")의 결과가 BoardController까지 반환됨
- model에 목록을 담고
- 반환되는 문자열에 해당되는 jsp 파일(/views/board/list.jsp)을 처리하여 
- 브라우저에 응답

## 글 확인
![image](https://user-images.githubusercontent.com/79209568/124225970-49a87280-db43-11eb-8edb-028a8de9e262.png)
![image](https://user-images.githubusercontent.com/79209568/124226142-8ecca480-db43-11eb-8324-19a68a0306c9.png)
![image](https://user-images.githubusercontent.com/79209568/124226303-dce1a800-db43-11eb-92d4-47effd8186dd.png)
![image](https://user-images.githubusercontent.com/79209568/124226656-6d1fed00-db44-11eb-96e4-7b9c33712e3a.png)
- GetMapping : get일때만 매핑  
![image](https://user-images.githubusercontent.com/79209568/124227202-3c8c8300-db45-11eb-92a1-b1eb5c49b04e.png)

<hr>

## 빈 수동으로 등록하지 않아도 자동으로 넣기
- 등록했던 빈들 주석처리  
  ![image](https://user-images.githubusercontent.com/79209568/124229175-2e8c3180-db48-11eb-95a4-21f8138ceaa4.png)
  ![image](https://user-images.githubusercontent.com/79209568/124229182-30ee8b80-db48-11eb-9210-3f0f9262d7bd.png)
- component 등록  
  ![image](https://user-images.githubusercontent.com/79209568/124229236-42379800-db48-11eb-9011-72ebaac1dc9d.png)
- controller, service, dao에 어노테이션
  ![image](https://user-images.githubusercontent.com/79209568/124229311-5e3b3980-db48-11eb-9738-49f0f1f59bf1.png)
  ![image](https://user-images.githubusercontent.com/79209568/124229487-a9ede300-db48-11eb-9575-d4d191246f35.png)
  ![image](https://user-images.githubusercontent.com/79209568/124229602-d1dd4680-db48-11eb-910f-cd6377cb9d89.png)



  
## 여태 했던 것 자동으로 프로젝트 만들기
- ![image](https://user-images.githubusercontent.com/79209568/124229690-f6392300-db48-11eb-89e4-b694a19d397e.png)
- ![image](https://user-images.githubusercontent.com/79209568/124229702-f89b7d00-db48-11eb-8ef3-4ca8dfa8c291.png)
- 재실행
- ![image](https://user-images.githubusercontent.com/79209568/124229750-0e10a700-db49-11eb-8b06-6a47e06e9887.png)
- ![image](https://user-images.githubusercontent.com/79209568/124229828-27195800-db49-11eb-9f0d-be126e1ddb2c.png)
- ![image](https://user-images.githubusercontent.com/79209568/124229894-40ba9f80-db49-11eb-973e-3e614d2e3efe.png)
  - 패키지 구조는 3단계로(org.test.ex)


> - DI/IoC, AOP, PSA
> 
> - Spring Tool Suite(STS) : 이클립스랑 같은 건데 Spring 기반으로 개발하기 좋은 것
> 
> - Spring Lagacy(여태한거) -> 좀 더 향상된 기능 Spring Boot
> - Spring MVC 프로젝트
> - ORM
> - Mybatis/JPA
> - Lombok
> - REST API
