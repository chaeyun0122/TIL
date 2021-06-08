## 실습1
- spring01 패키지 복사해서 spring02로 이름 변경
- resource에 applicationContext복사해서 applicationContext2로 변경
- applicationContext2의 bean의 클래스를 spring02로 바꿈
- spring02의 main에 가서 applicationContext를 applicationContext2로 바꿈
- Greeter greeter1, Greeter greeter2으로 만든다. 객체를 두개 생성한다.
- system out으로 greeter1 == greeter2  둘의 동일성을 확인한다.
- 결과가 true로 나옴
- greeter라는 아이디는 고유한 식별값이기 때문에 getBean을 하면 참조값만을 반환하는 것이다. (싱글톤:단일객체를 만든다)

public Greeter() {
 sysout("Greeter생성");
}

main에서 생성자 주석처리
- 결과 : Greter생성이 나옴

## 실습2
- spring03, applicationContext3
- beean선언에 scope="prototype" 설정을 넣어주면 getBean할 때마다 객체가 새로 생성된다.

## spring01_1 : 탬플릿용도
- pom.xml
  - artifactId를 spring01_1

- 이걸 복사해서 spring01q, pom.xml 바꿈
- import해서 Exisitng maven project > next > 워크스페이스를 선택해서 > spring01q를 선택

<hr>

## examspring01
- 회원가입 시스템
