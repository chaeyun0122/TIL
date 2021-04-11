# Subquery를 사용하여 query 해결
## Subquery: 유형, 구문 및 지침
![image](https://user-images.githubusercontent.com/79209568/114294680-330df480-9adb-11eb-909c-dc0344cc37f9.png)

### Subquery 구문
![image](https://user-images.githubusercontent.com/79209568/114294716-6d779180-9adb-11eb-85cb-3303f206157b.png)
* subquery(inner query)는 main query(outer query)전에 실행된다.
* subquery의 결과는 main query에서 사용된다.

### Subquery 사용 지침
* 괄호로 묶어서 사용한다.
* 가독성을 위해 비교 조건의 오른쪽에 subquery를 배치한다. (하지만 양쪽 어디든 쓰일 수 있다.)
  ```sql
  SELECT *
  FROM EMP 
  WHERE SAL > (SELECT SAL 
                FROM EMP 
               WHERE ENAME = 'JONES') ; 
  ```
* **단일 행 subquery에는 단일 행 연산자를 사용하고 다중 행 subquery에는 다중 행 연산자를 사용한다.**

## 단일 행 subquery
* 결과가 한 행만 반환한다.
* 단일 행 비교 연산자를 사용합니다.
  | **연산자** |     **의미**     |
  |:----------:|:----------------:|
  |      =     | 같음             |
  |      >     | 보다 큼          |
  |     >=     | 보다 크거나 같음 |
  |      <     | 보다 작음        |
  |     <=     | 보다 작거나 같음 |
  |     <>     | 같지 않음        |
  
## 다중 행 subquery
* 두 개 이상의 행을 반환한다.
* 다중 행 비교 연산자를 사용한다.
  | **연산자** |                                                                                 **의미**                                                                                |
  |:----------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
  |     IN     | 리스트의 임의 멤버와 같음                                                                                                                                               |
  |     ANY    | =, !=, >, <, <=, >= 연산자가 앞에 있어야 합니다. 값 하나를 리스트의 값 또는 query에서 반환된 값과 각각 비교합니다. query에서 반환된 행이 없으면 FALSE로 평가됩니다.     |
  |     ALL    | =, !=, >, <, <=, >= 연산자가 앞에 있어야 합니다. 값 하나를 리스트의 모든 값 또는 query에서 반환된 모든 값과 비교합니다. query에서 반환된 행이 없으면 TRUE로 평가됩니다. |




```

------서브쿼리
SELECT *   -- ERROR 
  FROM EMP 
 WHERE SAL = (SELECT MIN(SAL)
                FROM EMP 
			   GROUP BY DEPTNO) ; 
-- 리턴되는 행 개수가 두개 이상이므로 에러가 난다.

SELECT *   
  FROM EMP 
 WHERE SAL IN (SELECT MIN(SAL)
                 FROM EMP 
			    GROUP BY DEPTNO) ; -- 다중 행 연산자를 사용한다.
                
SELECT * 
  FROM EMP 
 WHERE SAL IN (950, 800,1300) ;
 
-- 같은거
SELECT * 
  FROM EMP 
 WHERE SAL = 950 
    OR SAL = 800
    OR SAL = 1300	; -- in 연산자는 or
    
SELECT * 
  FROM EMP 
 WHERE SAL >ALL (SELECT AVG(SAL) 
                FROM EMP
               GROUP BY DEPTNO) ; 
-- 같은거
SELECT * 
FROM EMP 
WHERE SAL > 1566 
  AND SAL > 2175 
  AND SAL > 2916 ;

SELECT * 
  FROM EMP 
 WHERE SAL >ANY (SELECT AVG(SAL) 
                   FROM EMP
                  GROUP BY DEPTNO) ; 
--같은거
SELECT * 
FROM EMP 
WHERE SAL > 1566 
   OR SAL > 2175 
   OR SAL > 2916 ;
   
SELECT * 
FROM EMP 
WHERE DEPTNO =ANY (10,20) ;

-- IN은 많이 사용하지만 ANY, ALL은 굳이 필요가 없음
-- 위에 경우 그냥 가장 큰 2916보다 큰 것을 찾는 것

SELECT * 
  FROM EMP 
 WHERE SAL > (SELECT MIN(AVG(SAL)) 
                   FROM EMP
                  GROUP BY DEPTNO) ; -- 단일 행으로 만들어서 단일행 연산자를 사용
                  
SELECT * 
FROM DEPT 
WHERE DEPTNO IN (SELECT DEPTNO 
                   FROM EMP) ; 
--동일
SELECT *
FROM DEPT
WHERE DEPTNO IN (10, 20, 30);

SELECT * 
FROM DEPT 
WHERE DEPTNO NOT IN (SELECT DEPTNO 
                   FROM EMP) ; 
--동일
SELECT *
FROM DEPT
WHERE DEPTNO NOT IN (10, 20, 30);


SELECT * 
FROM DEPARTMENTS 
WHERE DEPARTMENT_ID NOT IN (SELECT DEPARTMENT_ID  
                              FROM EMPLOYEES ) ; 
-- 결과가 안나옴
-- 서브쿼리에서 NULL이 리턴되기 때문에

--예를 들어서
SELECT * 
FROM DEPARTMENTS 
WHERE DEPARTMENT_ID NOT IN (10,20,NULL) ; 
-- 이 경우 같은 표현이 아래와 같다
WHERE DEPARTMENT_ID != 10
  AND DEPARTMENT_ID != 20
  AND DEPARTMENT_ID != NULL ; -- NULL은 IS, IS NOT 으로 써야하므로 해당 식은 오류가 있다.

-- 방법 1. 따라서 서브쿼리를 NOT IN과 함께 사용할 때는 서브쿼리에서 NULL의 값이 나오지 않도록 조건을 넣어줘야한다.
SELECT * 
FROM DEPARTMENTS 
WHERE DEPARTMENT_ID NOT IN (SELECT DEPARTMENT_ID  
                              FROM EMPLOYEES 
							 WHERE DEPARTMENT_ID IS NOT NULL) ;
-- 방법 2.다른 값으로 대체해주는 방법도 있다.
SELECT * 
FROM DEPARTMENTS 
WHERE DEPARTMENT_ID NOT IN (SELECT NVL(DEPARTMENT_ID ,0)
                              FROM EMPLOYEES );
-- 방법 3. EXIST 연산자를 사용한다. (가장 좋은 방법!) 
SELECT * 
FROM DEPARTMENTS D 
WHERE NOT EXISTS (SELECT 1 
                FROM EMPLOYEES
			   WHERE DEPARTMENT_ID = D.DEPARTMENT_ID) ;


-- Q. 소속 부서의 평균 급여보다 더 많은 급여를 받는 사원? 
--답 1
-- 화면에 평균 급여를 보여야한다면 이것이 정답.
SELECT *
FROM EMP E 
JOIN (SELECT DEPTNO, AVG(SAL) AS AVG_SAL 
        FROM EMP 
	   GROUP BY DEPTNO) A 
  ON E.DEPTNO = A.DEPTNO
 AND E.SAL    > A.AVG_SAL   ; 
-- 답 2
-- 화면에 평균 급여를 안봐도 된다면 이것이 정답.
-- 서브쿼리가 부서 번호별로 반복 수행이 되므로 성능적으로는 더 떨어진다.
SELECT *
FROM EMP E
WHERE SAL > (SELECT AVG(SAL) 
               FROM EMP 
			  WHERE DEPTNO = E.DEPTNO) ; 


-----답 1 과정
SELECT *  -- ERROR 
FROM EMP E 
JOIN (SELECT DEPTNO, AVG(SAL)
        FROM EMP 
	   GROUP BY DEPTNO) A 
  ON E.DEPTNO = A.DEPTNO
 AND E.SAL    > A.AVG(SAL)   ; -- 이름에 괄호가 들어가면 안됨
 
SELECT *
FROM EMP E 
JOIN (SELECT DEPTNO, AVG(SAL) AS AVG_SAL -- 별칭을 넣어주면 됨
        FROM EMP 
	   GROUP BY DEPTNO) A 
  ON E.DEPTNO = A.DEPTNO
 AND E.SAL    > A.AVG_SAL   ; 
-- 화면에 AVG_SAL까지 나옴. 이건 안보고싶고 조건에서 비교만 하고싶다면?

-- 답2 과정
SELECT *
FROM EMP 
WHERE DEPTNO = 30
  AND SAL > (SELECT AVG(SAL) 
               FROM EMP 
			  WHERE DEPTNO = 30);
-- Correlated Subquery 
-- 행단위 처리를 목적으로 만들어진 서브쿼리
-- 서브쿼리는 메인쿼리보다 항상 먼저 시작되는 것이 아니다.
-- 후보 행별로 하나씩 시도가 될수도 있어서 여러번 실행될 수도 있다.
-- 해당 코드의 경우 서브쿼리에서 메인쿼리를 참조하므로 메인쿼리가 먼저 실행된다.

SELECT *
FROM EMP E
WHERE SAL > (SELECT AVG(SAL) 
               FROM EMP 
			  WHERE DEPTNO = E.DEPTNO) ; 
	  


SELECT *
FROM DEPT 
WHERE DEPTNO IN (SELECT DEPTNO 
                   FROM EMP ) ; 
                   
SELECT *
FROM DEPT 
WHERE DEPTNO IN (SELECT DISTINCT DEPTNO 
                   FROM EMP ) ; 
                   
-- 둘 다 

SELECT *
FROM DEPT D 
WHERE EXISTS (SELECT 1 -- 어떤 것을 써도 동일한 결과. (값을 리턴하는 것이아니라 값이 있냐없냐를 평가하기 때문에)
                FROM EMP 
			   WHERE DEPTNO = D.DEPTNO) ; 
               
-- 성능적으로 우수하고 null 걱정을 안해도 된다.
```
