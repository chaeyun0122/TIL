# Database Quiz
### student 계정 생성
```sql
-- system계정에서 실행

create user c##student identified by a1234;
grant connect, resource to c##student; --권한 설정
alter user c##student default tablespace users; --tablespace 지정
alter user c##student quota unlimited on users; --quota 지정
```

### student 계정 접속
![image](https://user-images.githubusercontent.com/79209568/114700157-1e8c5f00-9d5c-11eb-8571-379a1b7e65ec.png)

### student 테이블 생성
```sql
-- student계정에서 실행

create table student(
studno number not null,       -- 학생번호
name varchar2(20) not null,   -- 이름
userid varchar2(10) not null, -- 아이디
grade number not null,        -- 학년
idnum varchar2(13) not null,  -- 주민번호
birthdate date not null,      -- 생년월일
tel varchar2(15) not null,    -- 전화번호
height number not null,       -- 키
weight number not null,       -- 몸무게
deptno number not null,       -- 학과번호
profno number default null,   -- 담당교수
PRIMARY KEY (studno)
);

desc student;
```
![image](https://user-images.githubusercontent.com/79209568/114700342-5f847380-9d5c-11eb-9314-0970493a783b.png)

```sql
-- 레코드 추가
insert into student values (10102, '박미경', 'ansel1414', 1, '8405162123648',
To_Date('1984-05-16', 'yyyy-mm-dd'), '055)261-8947', 168, 52, 101, null);

insert into student values (10103, '김영균', 'mandu', 3, '8103211063421',
To_Date('1981-03-21', 'yyyy-mm-dd'), '051)824-9637', 170, 88, 101, 9906);

insert into student values (10104, '지은경', 'gomo00', 2, '8004122298371',
To_Date('1980-04-12', 'yyyy-mm-dd'), '055)418-9627', 161, 42, 101, 9907);

insert into student values (10105, '임유진', 'youjin12', 2, '8301212196482',
To_Date('1983-01-21', 'yyyy-mm-dd'), '02)312-9838', 171, 54, 101, 9907);

insert into student values (10106, '서재진', 'seolly', 1, '8511291186273',
To_Date('1985-11-29', 'yyyy-mm-dd'), '051)239-4861', 186, 72, 101, null);

insert into student values (10107, '이광훈', 'huriky', 4, '8109131276431',
To_Date('1981-09-13', 'yyyy-mm-dd'), '055)736-4981', 175, 92, 101, 9903);

insert into student values (10108, '류민정', 'clearsky', 2, '8108192157498',
To_Date('1981-08-19', 'yyyy-mm-dd'), '055)248-3679', 162, 72, 101, 9907);

insert into student values (10201, '김진영', 'simply', 2, '8206062186327',
To_Date('1982-06-06', 'yyyy-mm-dd'), '055)419-6328', 164, 48, 102, 9905);

insert into student values (10202, '오유석', 'yousuk', 4, '7709121128379',
To_Date('1977-09-12', 'yyyy-mm-dd'), '051)724-9618', 177, 92, 102, 9905);

insert into student values (10203, '하나리', 'hanal', 1, '8501092378641',
To_Date('1985-01-09', 'yyyy-mm-dd'), '055)296-3784', 160, 68, 102, null);

insert into student values (10204, '윤진욱', 'samba7', 3, '7904021358671',
To_Date('1979-04-02', 'yyyy-mm-dd'), '053)487-2698', 171, 70, 102, 9905);

insert into student values (20101, '이동훈', 'dals', 1, '8312101128467',
To_Date('1983-12-10', 'yyyy-mm-dd'), '055)426-1752', 172, 64, 201, null);

insert into student values (20102, '박동진', 'ping2', 1, '8511241639826',
To_Date('1985-11-24', 'yyyy-mm-dd'), '051)742-6384', 182, 70, 201, null);

insert into student values (20103, '김진경', 'lovely', 2, '8302282169387',
To_Date('1983-02-28', 'yyyy-mm-dd'), '052)175-3941', 166, 51, 201, 9902);

insert into student values (20104, '조명훈', 'rader214', 1, '8412141254963',
To_Date('1984-12-14', 'yyyy-mm-dd'), '02)785-6984', 184, 62, 201, null);

select * from student;

commit;
```
![image](https://user-images.githubusercontent.com/79209568/114698273-e257ff00-9d59-11eb-83d0-3a08d077d4b9.png)


### Q-1. 학생이름과 학생번호를 출력하세요
```sql
select name, studno from student;
```
![image](https://user-images.githubusercontent.com/79209568/114697942-75446980-9d59-11eb-8cfb-f0db67d3a1b0.png)

### Q-2. 1학년 학생만 검색하여 학번, 이름, 학과 번호를 출력하세요
```sql
select studno, name, deptno from student where grade='1';
```
![image](https://user-images.githubusercontent.com/79209568/114698108-a7ee6200-9d59-11eb-83e9-449283fbcc24.png)

### Q-3. 학과번호가 101번인 학생들의 학번, 이름, 학년을 출력하세요
```sql
select studno, name, grade from student where deptno='101';
```
![image](https://user-images.githubusercontent.com/79209568/114698223-d0765c00-9d59-11eb-8114-d663045b0cf7.png)

### Q-4. 몸무게가 70kg 이하인 학생만 검색하여 학번, 이름, 학년, 학과번호, 몸무게를 출력하세요
```sql
select studno, name, grade, deptno, weight from student where weight<=70;
```
![image](https://user-images.githubusercontent.com/79209568/114698418-0adff900-9d5a-11eb-9a46-3df10bfbf908.png)

### Q-5. 키가 170 이상인 학생의 학번, 이름, 학년, 학과번호, 키를 출력하세요
```sql
select studno, name, grade, deptno, height from student where height>=170;
```
![image](https://user-images.githubusercontent.com/79209568/114698538-2c40e500-9d5a-11eb-9aa6-e999e6360a3a.png)

### Q-6. 1학년 이면서 몸무게가 70kg 이상인 학생만 검색하여 이름, 학년, 몸무게, 학과번호를 출력하세요
```sql
select name, grade ,weight, deptno from student where grade='1' and weight>=70;
```
![image](https://user-images.githubusercontent.com/79209568/114698674-55617580-9d5a-11eb-8e84-0de2a0f10fc3.png)

### Q-7. 성이 '김'씨인 학생의 이름, 학년, 학과번호를 출력하세요
```sql
select name, grade, deptno from student where name like '김%';
```
![image](https://user-images.githubusercontent.com/79209568/114698779-732eda80-9d5a-11eb-995d-e5088ceb0fcd.png)

### Q-8. 이름을 가나다 순으로 정렬하여 이름, 학년, 전화번호를 출력하세요
```sql
select name, grade, tel from student order by name;
```
![image](https://user-images.githubusercontent.com/79209568/114699006-b4bf8580-9d5a-11eb-9dd0-53df4fda67b2.png)

### Q-9. 학생 테이블에 아래의 내용을 입력하세요
  > 번호 : 10110  
  > 이름 : test  
  > 아이디 : test01  
  > 학년 : 1  
  > 주민번호 : 123456-1234567  
  > 생년월일 : 2013년 1월 1일  
  > 전화번호 : 031-777-7777  
  > 키       : 170  
  > 몸무게   : 70  
  > 학과번호 : 101  
  > 담당교수번호 : 9903  
```sql
insert into student values(10110, 'test', 'test01', 1, '1234561234567', '13/01/01', '031)777-7777', 170, 70, 101, 9903);
```
![image](https://user-images.githubusercontent.com/79209568/114699662-8ee6b080-9d5b-11eb-8da5-4d96a167cd66.png)

### Q-10. 학번이 20103인 학생의 데이터를 삭제하세요
```sql
delete student where studno=20103;
```




