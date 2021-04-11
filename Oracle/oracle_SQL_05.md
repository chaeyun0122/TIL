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
