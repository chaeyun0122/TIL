# 그룹 함수를 사용하여 집계된 데이터보고
## 그룹 함수
> #### 행 집합에 대해 실행되어 그룹당 하나의 결과를 산출하는 함수
## 그룹 함수 유형
* AVG (숫자 타입에만 사용가능)
* COUNT
  * COUNT(\*) : 테이블의 행 수를 반환
  * COUNT(expr) : 열에 대해 **NULL이 아닌 값** 행의 개 수 반환
  * COUNT(DISTINCT expr) : NULL이 아닌 구분 값의 수를 반환(중복제거)
* MAX (모든 데이터타입 사용가능)
* MIN (모든 데이터타입 사용가능)
* STDDEV
* SUM (숫자 타입에만 사용가능)
* VARIANCE

## 행 그룹화
### SQL 실행 순서
``` SQL
5 SELECT    : 검색 대상, 표현식
1 FROM      : 대상 집합 (테이블, 뷰, 서브쿼리)
2 WHERE     : 행 제한을 위한 조건식
3 GROUP BY  : 그룹 생성
4 HAVING    : 그룹 제한을 위한 조건식
6 ORDER BY  : 정렬
```
  
* 전체 사원의 급여 합
```SQL
SELECT SUM(SAL)
FROM EMP;
```  
  ![image](https://user-images.githubusercontent.com/79209568/114289595-36da5080-9ab4-11eb-8bd2-8d8cd93449b4.png)
  
* DEPNO가 10인 사원의 급여 합
```SQL
SELECT SUM(SAL)
FROM EMP
WHERE DEPTNO = 10;
```
  ![image](https://user-images.githubusercontent.com/79209568/114289599-422d7c00-9ab4-11eb-9ad1-55fc0a4d6f87.png)
  
* 부서번호가 같은 것들끼리 묶어서 급여를 계산
```SQL
SELECT DEPTNO, SUM(SAL)
FROM EMP
GROUP BY DEPTNO ;
```
  ![image](https://user-images.githubusercontent.com/79209568/114289612-5bcec380-9ab4-11eb-9dd8-30bf1695eb23.png)

* SQL 실행 순서를 통해 주의 해야 할 사항 파악
  ```SQL
  -- 에러
  SELECT DEPTNO, SUM(SAL), JOB
  FROM EMP 
  GROUP BY DEPTNO ; 
  ```
  * 문장을 실행하는 것은 SP
  * FROM 절이 DB에서 테이블을 불러옴
  * WHERE 절이 필요 없는 행들을 떨굼
  * GROUP BY 절이 남은 행들을 그룹화 함
  * **따라서, SELECT의 JOB은 GROUP BY절에 참여하지 않은 열이므로 에러가 난다.**

## GROUP BY
* GROUP BY 절을 사용하여 테이블의 행을 더 작은 그룹으로 나눌 수 있다.
* 그룹 함수에 속하지 않는 SELECT list의 모든 열은 GROUP BY절에 있어야한다.
 ```SQL
 SELECT TO_CHAR(HIREDATE, 'YYYY'), SUM(SAL)
 FROM EMP
 GROUP BY TO_CHAR(HIREDATE, 'YYYY');

 SELECT HIREDATE, SUM(SAL)
 FROM EMP
 GROUP BY TO_CHAR(HIREDATE, 'YYYY');
 -- 에러
 -- HIREDATE의 년도가 같은 것을 가져오기 때문에 SELECT의 HIREDATE는 14행을 가지고 있다.
 ```
* GROUP BY열은 SELECT list에 없어도 된다.
 ```SQL
 SELECT TO_CHAR(HIREDATE, 'YYYY'), SUM(SAL)
 FROM EMP
 GROUP BY HIREDATE;
 -- 이건 가능
 -- HIREDATE가 같은 행끼리 그룹화하고 년도만 보이게 하기때문에

 --에러는 안나지만 GROUP BY에 썼던 식을 SELECT에 그대로 쓰는 것이 좋음
 ```
* GROUP BY에 SELECT절의 별칭을 쓰는 것은 불가능하다. (SELECT 보다 GROUP BY가 먼저 실행되기 때문에)
* 따라서 SELECT의 별칭은 ORDER BY 에서만 사용 가능하다.
 ```SQL
 SELECT TO_CHAR(HIREDATE, 'YYYY') AS HIRE_YYYY, SUM(SAL)
 FROM EMP
 GROUP BY HIRE_YYYY; --ERROR

 -- 별칭을 불러와서 쓰는 것은 ORDER BY절에만 가능
 SELECT SAL*12 AS ANNSAL, ANNSAL/4 -- ERROR
 FROM EMP ;
 ```

```SQL
SELECT DEPTNO, SUM(SAL)
FROM EMP
WHERE DEPTNO IN (10,20)
GROUP BY DEPTNO
HAVING SUM(SAL) > 10000;

SELECT DEPTNO, SUM(SAL)
FROM EMP
GROUP BY DEPTNO
HAVING SUM(SAL) > 10000
 AND DEPTNO IN (10,20);
 
 -- 결과는 동일하지만 성능 차이가 남
 -- 위는 WHERE에서 DEPTNO가 30인 것을 제외하고나서 그룹화하기 때문에 10, 20만 그룹화함
 -- 아래는 10, 20, 30 그룹화를 진행한 후 30을 버림. 성능이 별로 안 좋음

SELECT DEPTNO, SUM(SAL)
  FROM EMP 
 GROUP BY DEPTNO 
 HAVING SUM(SAL) > 10000 
    AND DEPTNO IN (10,20)
	AND JOB > 'A' ; --에러
-- JOB이 GROUP BY에 참여하지 않았기 때문에

-- 그룹함수끼리의 중첩은 한 번만 가능하다.
-- 지원을 하지 않음
SELECT MIN(MAX(AVG(SAL))) -- 세 번째부터는 
FROM EMP 
GROUP BY DEPTNO ; 

-- 이 경우는 가능하다
SELECT TRUNC(MAX(AVG(SAL)))
FROM EMP 
GROUP BY DEPTNO ; 
```
