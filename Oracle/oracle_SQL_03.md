# 단일 행 함수를 사용하여 출력 커스터마이즈
## 단일 행 SQL 함수
### SQL 함수
* 인수를 입력 받아서 함수가 작업 후 결과 값을 출력한다.

### SQL 함수의 두 가지 유형
* 단일 행 함수 : 행 당 하나의 결과 반환
* 다중 행 함수 : 행 집합 당 하나의 결과 반환

### 단일 행 함수
* 데이터 항목을 조작
* 인수를 사용하고 **하나의 값**을 반환한다.
* 반환되는 각 행에서 실행된다.
* 중첩 가능
* 열이나 표현식을 인수로 사용 가능

## 문자 함수
![image](https://user-images.githubusercontent.com/79209568/114260870-5751ce80-9a12-11eb-846c-cb4b63e16b24.png)

### 대소문자 변환 함수
* 문자열의 대소문자를 변환하는 함수
* ex) `WHERE  LOWER(last_name) = 'higgins'`  
  
![image](https://user-images.githubusercontent.com/79209568/114260884-72244300-9a12-11eb-88e1-c2c161285189.png)

### 문자 조작 함수
* 문자열을 조작하는 함수
  
![image](https://user-images.githubusercontent.com/79209568/114260974-ec54c780-9a12-11eb-9ab2-7249ed9b2701.png)

## 숫자 함수
* ROUND: 지정된 소수점 자릿수로 값을 반올림합니다.
* TRUNC: 지정된 소수점 자릿수로 값을 truncate합니다.
* MOD: 나눈 나머지를 반환합니다.
### DUAL
* 함수 및 계산의 결과를 볼 때 사용할 수 있는 공용(public) 테이블 
(한 개의 행을 가지고 있는 테이블이라고 생각하면 됨)
  
  ```SQL
  -- 오늘 날짜 반환
  SELECT SYSDATE
  FROM DUAL ;  -- 2021/04/10
  
  -- 숫자 하나의 행을 반환
  SELECT ROUND(45.923,2), TRUNC(45.923,-1)
  FROM DUAL ;  -- 45.92, 40
  ```
  
## 날짜 작업
> ### 1. `EMPNO = 7788`인 행을 현재 날짜로 저장한다.
> ```SQL
> UPDATE EMP
> SET HIREDATA = SYSDATE
> WHERE EMPNO = 7788  ;
> ```
>   
> ```SQL
> SELECT *
> FROM EMP;
> ```
> ### 2. `HIREDATE`가 `2021/04/10`으로 변경 되었다.
> ![image](https://user-images.githubusercontent.com/79209568/114261682-e6f97c00-9a16-11eb-83d1-1b14673d3fe6.png)
>   
> > ### WHERE EMPNO = '2021/04/10'으로 검색하면 데이터가 존재하는데 검색되지않는다. 왜??
> > * **저장되는 날짜는 시분초까지 함께 되기 때문에 '='로 조건을 주면 시분초까지 완벽하게 맞아야한다.**
> 
> ### 해결방법
> #### 1) 표시형식을 시분초까지 보이도록 설정 후 조건을 시분초까지 정확히 입력하기
>   ![image](https://user-images.githubusercontent.com/79209568/114262052-c9c5ad00-9a18-11eb-9f40-7f9bf82d100c.png)
>   ```SQL
>   WHERE HIREDATE = '2021/04/10 15:50:46'
>   ```
> #### 2) BETWEEN을 사용하여 조건 입력
>   * 이 경우 형식과 상관없이 검색 가능하므로 그냥 형식을 외우는 게 좋다
>   ```SQL
>   WHERE HIREDATE BETWEEN TO_DATE('2021/04/10','YYYY/MM/DD')
>                      AND TO_DATE('2021/04/11','YYYY/MM/DD') - 1/86400 ; 
>   ```

```SQL
SELECT SYSDATE, SYSDATE + 1  - 1일
              , SYSDATE + 1/24  - 1시간
	      , SYSDATE + 1/1440  - 1분
 	      , SYSDATE + 1/86400  - 1초
FROM DUAL ;
```

```SQL
-- 근무 일수
SELECT EMPNO, HIREDATE, TRUNC(SYSDATE - HIREDATE)
FROM EMP ; 
  
-- 에러남. 날짜 + 날짜는 지원하지 않는다
-- 날짜 + 숫자, 날짜 - 날짜는 가능
SELECT EMPNO, HIREDATE, TRUNC(SYSDATE + HIREDATE)
FROM EMP ; 
```

```SQL
SELECT SYSDATE -- 2021/04/10 16:33:03
      , ROUND(SYSDATE, 'YYYY') --오늘이 1년의 반이 아직 안지났으므로 2021년 1월이 나옴
      , ROUND(SYSDATE+90, 'YYYY') --90일을 더해서 반년보다 지났으므로 2022년 1월이 나옴
      , ROUND(SYSDATE, 'MM') -- 2021/04/01
      , ROUND(SYSDATE, 'DD') -- 2021/04/11
FROM DUAL ;
```
