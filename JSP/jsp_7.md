# 데이터 무결성 조건
- 테이블에 잘못된 자료가 입력되는 것을 방지하기 위해서 테이블을 생성할 때, 각 컬럼에 대해서 정의하는 규칙
  - 무결성 : 데이터의 정확성 유지
  - 제약조건 : 잘못된 데이터가 저장되는 것을 방지

## 제약조건
| 제약조건    | 기능                                                                 |
|-------------|----------------------------------------------------------------------|
| NOT NULL    | NULL을 허용하지 않음                                                 |
| UNIQUE      | 중복된 값을 허용하지 않음                                            |
| PRIMARY KEY | NULL을 허용하지 않고, 중복된 값을 허용하지 않음                      |
| FOREIGN KEY | 참조되는 테이블의 컬럼의 값이 있으면 허용                            |
| CHECK       | 저장 가능한 데이터의 값의 범위나 조건을 지정하여 설정한 값 만을 허용 |

## 시퀀스 (sequence)
- 숫자가 순차적으로 증가하는 데이터베이스 객체
- `create sequence 시퀀스명 nocache nocycle;`
  - nocache : 시퀀스 값 미리 할당 설정
  - nocycle : 순환 설정
- 데이터 입력시에서는 `nextvar`을 이용해서 시퀀스 값 적용
  
```sql
create sequence seq_test nocache nocycle;
```

### 제약조건을 사용해서 테이블 생성
```SQL
create table test (
seq number not null,
id varchar2(20) primary key,
name varchar2(50) not null,
logtime date
);
```
```sql
desc test;
```
> ![image](https://user-images.githubusercontent.com/79209568/114694801-e124d300-9d55-11eb-89e8-396cb74bf353.png)

### 레코드 추가
```sql
insert into test values(seq_test.nextval, '001', 'testA', sysdate);
```
> ![image](https://user-images.githubusercontent.com/79209568/114695693-d9196300-9d56-11eb-8f97-950d8f165932.png)

### primary key 컬럼에 중복된 값 추가
```sql
insert into test (seq, id, name, logtime) values (seq_test.nextval, '001', 'testB', sysdate);
```
> #### 오류!
> ![image](https://user-images.githubusercontent.com/79209568/114696072-3dd4bd80-9d57-11eb-8c3c-38be6068f8b6.png)
 
### not null 컬럼에 null값으로 추가
```sql
insert into test (seq, id, logtime) values (seq_test.nextval, '003', sysdate);
```
> #### 오류!
> ![image](https://user-images.githubusercontent.com/79209568/114696335-7ffdff00-9d57-11eb-8ca5-d96d901afade.png)


