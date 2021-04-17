# 집합 연산자 사용
## 집합 연산자 유형
![image](https://user-images.githubusercontent.com/79209568/115095869-d046ae00-9f5d-11eb-8c5d-09620c56b630.png)
### UNION
* 합집합 (중복 제거)
  
```SQL
SELECT *
FROM EMP
WHERE DEPTNO IN (10,20)
UNION
SELECT *
FROM EMP
WHERE DEPTNO IN (10,30);
```
![image](https://user-images.githubusercontent.com/79209568/115095927-14d24980-9f5e-11eb-9e05-3e83a636af36.png)

### UNION ALL
* 합집합 (중복 포함)
  
```SQL
SELECT *
FROM EMP
WHERE DEPTNO IN (10,20)
UNION ALL
SELECT *
FROM EMP
WHERE DEPTNO IN (10,30);
```
> DEPTNO가 10인 것이 두 번 나옴
> ![image](https://user-images.githubusercontent.com/79209568/115095945-287db000-9f5e-11eb-8262-d60fc1136206.png)  


### INTERSECT
* 교집합
  
```SQL
SELECT *
FROM EMP
WHERE DEPTNO IN (10,20)
INTERSECT
SELECT *
FROM EMP
WHERE DEPTNO IN (10,30);
```
> ![image](https://user-images.githubusercontent.com/79209568/115095978-4c40f600-9f5e-11eb-9cf1-5302f72fe011.png)

### MINUS
* 차집합
* 순서가 중요하다.
  
```SQL
SELECT *
FROM EMP
WHERE DEPTNO IN (10,20)
MINUS
SELECT *
FROM EMP
WHERE DEPTNO IN (10,30);
```
> ![image](https://user-images.githubusercontent.com/79209568/115095991-5b27a880-9f5e-11eb-85c7-e4f72dfb0014.png)

## 집합 연산자 지침
* SELECT list의 표현식은 개수가 일치해야 한다.
  ```SQL
  SELECT DEPTNO, JOB, SUM(SAL)
  FROM EMP 
  GROUP BY DEPTNO, JOB  
  UNION ALL  --그루핑 결과를 다양하게 출력할 때 UNION ALL을 종종 사용함
  SELECT DEPTNO, NULL, SUM(SAL)
  FROM EMP 
  GROUP BY DEPTNO
  ORDER BY DEPTNO;
  ```
* 두번째 query에 있는 각 열의 데이터 유형은 첫번째 query에 있는 상응하는 열의 데이터 유형과 일치해야 한다.
* 실행 순서를 변경하는 데 괄호를 사용할 수 있다.
  ```SQL
  SELECT * 
  FROM EMP 
  WHERE DEPTNO IN (10,20) 
  MINUS
  (SELECT * 
  FROM EMP 
  WHERE DEPTNO IN (10,30) 
  UNION ALL
  SELECT * 
  FROM EMP 
  WHERE DEPTNO IN (10,20) )
  MINUS
  SELECT * 
  FROM EMP 
  WHERE DEPTNO IN (10,30) ; 
  ```
* ORDER BY 절은 명령문의 맨 끝에만 올 수 있다. ORDER BY절에 있는 컬럼은 반드시 첫번째 query SELECT 문에 존재해야한다. (\*로 하면 오류남)
  ```SQL
  SELECT *    -- ERROR 
  FROM EMP 
  WHERE DEPTNO IN (10,20) 
  MINUS
  SELECT * 
  FROM EMP 
  WHERE DEPTNO IN (10,30) 
  ORDER BY DEPTNO; 
  
  SELECT DEPTNO, EMPNO, ENAME 
  FROM EMP 
  WHERE DEPTNO IN (10,20) 
  MINUS
  SELECT DEPTNO, EMPNO, ENAME 
  FROM EMP 
  WHERE DEPTNO IN (10,30) 
  ORDER BY DEPTNO; 
  
  --별칭 가능
  SELECT DEPTNO AS DEPTID, EMPNO, ENAME 
  FROM EMP 
  WHERE DEPTNO IN (10,20) 
  MINUS
  SELECT DEPTNO AS DEPT, EMPNO, ENAME 
  FROM EMP 
  WHERE DEPTNO IN (10,30) 
  ORDER BY DEPTID; 
  ```
## Oracle 서버 및 집합 연산자
* 중복 행은 UNION ALL 외에는 자동으로 제거된다.
* 첫번째 query의 열 이름이 결과에 나타난다.
* UNION ALL의 경우를 제외하고 출력은 기본적으로 오름차순으로 정렬된다.

> ### 추가 : ROLL UP
> * ROLLUP구문은 GROUP BY 절과 같이 사용 되며, GROUP BY절에 의해서 그룹 지어진 집합 결과에 대해서 좀 더 상세한 정보를 반환하는 기능을 수행 한다.
> ```SQL
> SELECT DEPTNO, JOB, SUM(SAL)
> FROM EMP
> GROUP BY ROLLUP(DEPTNO, JOB); --제일 아래에 전체 SUM확인
> ```
> ![image](https://user-images.githubusercontent.com/79209568/115096827-d76fbb00-9f61-11eb-87dc-ce488e38ef91.png)
