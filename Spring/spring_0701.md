O/R Mapper : object relation mapper
- 쿼리를 직접쓰는 것이 아니라 사용자는 java만 쓰고 쿼리 매핑을 도와주는 데이터베이스 프레임워크가 mybatis

# Mybatis
## 의존 라이브러리 추가
#### pom.xml
![image](https://user-images.githubusercontent.com/79209568/124063489-f1ec0780-da6d-11eb-8aa1-996d1a303b24.png)

## mybatis 설정 파일
#### resources/config/mybatis-config.xml
- settings 태그 안에 마이바티스 설정들을 써준다.
- typeAlias로 별칭을 지정해준다.
  
![image](https://user-images.githubusercontent.com/79209568/124071383-3087bf00-da7a-11eb-9581-3828dfec6f8e.png)

## SQL 매핑 정의 파일
#### resources/config/sqlmap-member.xml
- mapper의 namespace는 쿼리 목록의 이름. 해당 매퍼를 memberDao라는 이름으로 찾는다.
- mapper안에 쿼리문을 작성한다.
- **insert**
  - id는 insert이고, 전달받는 종류는 member다. (member는 spring.Member의 alias)
  - \#{ }는 spring.Member의 getter로 해당 값을 찾는다.
  
![image](https://user-images.githubusercontent.com/79209568/124071404-37aecd00-da7a-11eb-9323-d9e7e2be8eec.png)

## MemberDao
- MemberDao 클래스 명 MemberDaoImpl로 변경
- MemberDao 인터페이스 생성
- MemberDaoImpl 클래스 MemberDao를 implements

#### MemberDaoImpl
- insert 메서드 추가  
  
  ![image](https://user-images.githubusercontent.com/79209568/124072275-83ae4180-da7b-11eb-9dae-b19650568a2f.png)

#### MemberDao
- insert 추가  
  
  ![image](https://user-images.githubusercontent.com/79209568/124072310-9163c700-da7b-11eb-8858-03c83acc0691.png)

## mybatis 빈 등록
#### spring-member.xml
- 마이바티스 연동을 위한 빈
  - SqlSessionFactroyBean 클래스 사용
  - config에 만들어 줬던 마이바티스 설정 파일과 매퍼의 위치를 선언해준다.
- 마이바티스 사용을 위한 빈
  - SqlSessionTemplate 클래스 사용
  - 연동 빈을 의존주입
- memberDao 빈 의 클래스를 MemberDaoImpl로 변경해주고 sqlSessionTemplate 빈을 의존주입
  
![image](https://user-images.githubusercontent.com/79209568/124072933-6463e400-da7c-11eb-9c65-0716c1677ea4.png)

## 프로젝트 오류 해결
- 오류가 나는 메서드는 MemberDaoImpl 클래스에 삽입해주고, MemberDao 인터페이스에도 선언해준다.  
![image](https://user-images.githubusercontent.com/79209568/124073070-983f0980-da7c-11eb-9514-0d3d8c2e3185.png)
![image](https://user-images.githubusercontent.com/79209568/124073085-9ffeae00-da7c-11eb-9dda-3ade87375a34.png)

## 서버 실행 결과
- 회원가입으로 insert가 잘 동작하는지 확인한다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/124073291-f0760b80-da7c-11eb-915b-54c58ef80c16.png)
- insert 작동이 잘 된다.  
  
  ![image](https://user-images.githubusercontent.com/79209568/124073519-fff55480-da7c-11eb-9ea8-c94076b0ea99.png)
- 콘솔에도 insert 반환 값이 1로 나타는 것을 확인할 수 있다.
  
  ![image](https://user-images.githubusercontent.com/79209568/124073580-1b605f80-da7d-11eb-84d9-fe77afebd168.png)
