# 변환 함수 및 조건부 표현식 사용
## 암시적 및 명시적 데이터 유형 변환
### 암시적 데이터 유형 변환
* Oracle 서버가 표현식을 자동으로 변환해준다.
  ```SQL
  SELECT * 
  FROM EMP 
  WHERE DEPTNO = '10' ;
  ```
  * DEPTNO은 숫자 데이터 열인데 문자로 입력해도 암시적으로 데이터 유형을 변환해서 DEPTNO가 10인 행을 출력해준다. 
### 명시적 데이터 유형 변환
* 날짜와 숫자는 꼭 문자를 거쳐서 바뀐다. (바로 날짜→숫자, 숫자→날짜 안된다.)
  ![image](https://user-images.githubusercontent.com/79209568/114262552-93d5f800-9a1b-11eb-84c7-947744f3ab53.png)
  
## TO_CHAR
### 날짜에서 사용
```SQL
SELECT SYSDATE
      ,TO_CHAR(SYSDATE,'YYYY')
      ,TO_CHAR(SYSDATE,'MM/DD')	  
      ,TO_CHAR(SYSDATE,'DAY') -- 요일
      ,TO_CHAR(SYSDATE,'W') -- (월별)주차
      ,TO_CHAR(SYSDATE,'Q') -- 분기
      ,TO_CHAR(SYSDATE,'WW') -- (연별)주차  
FROM DUAL ; 
```
![image](https://user-images.githubusercontent.com/79209568/114262739-8f5e0f00-9a1c-11eb-8d44-06c72aa07ca5.png)
  
  
```SQL
SELECT EMPNO, ENAME, HIREDATE, 
       TO_CHAR(HIREDATE, 'Month DD, YYYY', 'NLS_DATE_LANGUAGE=AMERICAN'),
       TO_CHAR(HIREDATE, 'fmMonth DD, YYYY', 'NLS_DATE_LANGUAGE=AMERICAN')
FROM EMP ;
```
![image](https://user-images.githubusercontent.com/79209568/114262823-11e6ce80-9a1d-11eb-9f5f-015a614f3379.png)


### 숫자에서 사용
![image](https://user-images.githubusercontent.com/79209568/114263160-ecf35b00-9a1e-11eb-9d92-83fb296cc9a9.png)
```SQL
SELECT EMPNO, ENAME, TO_CHAR(SAL, '99,999.00')
                   , TO_CHAR(SAL, '00,999.00')
                   , TO_CHAR(SAL, '$99,999.00')
                   , TO_CHAR(SAL, 'L99,999.00')
FROM EMP ; 
```
![image](https://user-images.githubusercontent.com/79209568/114263193-1a400900-9a1f-11eb-87fb-6ffbb9fec935.png)

## TO_NUMBER
* TO_NUMBER로 문자를 숫자로 변경한다.
* 하지만 숫자는 그냥 쓰면 되기 때문에 별로 의미 없다.
```SQL
SELECT TO_NUMBER('1203120930912'), '1203120930912'
FROM DUAL ; 

SELECT TO_NUMBER('$123,456','$999,999') + TO_NUMBER('$123,456','$999,999')
FROM DUAL ;
```
## TO_DATE
```SQL
SELECT '2021/04/10', TO_DATE('2021/04/10','YYYY/MM/DD')
FROM DUAL ;
```
![image](https://user-images.githubusercontent.com/79209568/114263366-05b04080-9a20-11eb-82ee-bf32105bc24a.png)
    * 앞은 날짜처럼보이지만 문자다.

## 함수 중첩
* 단일 행 함수는 어떠한 레벨로도 중첩될 수 있다
* 중첩 함수는 가장 깊은 레벨에서 덜 깊은 레벨로 평가된다
![image](https://user-images.githubusercontent.com/79209568/114288799-2ffc0f80-9aad-11eb-8b1f-77d09453fff7.png)
* ex) 
  ```SQL
  UPPER (CONCAT (SUBSTR (LAST_NAME, 1, 8), '_US'))
  ```

## 일반 함수
* 임의의 데이터 유형을 사용하며 NULL 사용과 관련 있는 함수들
   * NVL (expr1, expr2)
   * NVL2 (expr1, expr2, expr3)
   * NULLIF (expr1, expr2)
   * COALESCE (expr1, expr2, ... , exprn)

```SQL
ELECT EMPNO, ENAME, MGR, NVL(MGR, 'NO MANAGER')
FROM EMP; -- 오류 남(숫자 열인데 문자를 넣으려고하기 때문에)

SELECT EMPNO, ENAME, MGR, NVL(TO_CHAR(MGR), 'NO MANAGER')
FROM EMP; -- TO_CHAR로 MGR열을 문자로 만들어주면 오류가 안남
```
![image](https://user-images.githubusercontent.com/79209568/114288336-d7774300-9aa9-11eb-9389-e7dbff6dcaa4.png)


## 조건부 표현식
* SQL 문에서 IF-THEN-ELSE 논리를 사용할 수 있다. `CASE식`, `DECODE함수` 두 가지 있다.
### CASE식
* CASE, WHEN, THEN, ELSE, END 형식으로 쓴다
```SQL
SELECT EMPNO, ENAME, SAL, DEPTNO, 
       CASE DEPTNO WHEN 10 THEN SAL * 1.1
                   WHEN 20 THEN SAL * 1.2
                   ELSE SAL * 1.3 END
FROM EMP;
```
```SQL
-- 아예 조건식 자체를 지정할 수도 있음. 좀 더 다양한 조건식을 적용할 수 있음
SELECT EMPNO, ENAME, SAL, DEPTNO, 
       CASE WHEN DEPTNO = 10 THEN SAL * 1.1
            WHEN DEPTNO = 20 THEN SAL * 1.2
            ELSE SAL * 1.3 END
FROM EMP;
```
### DECODE 함수
* 콤마(,)로 구분
```SQL
SELECT EMPNO, ENAME, SAL, DEPTNO,
       DECODE (DEPTNO,10,SAL*1.1
                     ,20,SAL*1.2
                        ,SAL*1.3)
FROM EMP;
```
