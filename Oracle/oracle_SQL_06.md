# 조인을 사용하여 여러 테이블의 데이터 표시
## 조인 유형 및 구문
* 조인 : 여러 테이블에서 데이터를 가지고 오는 것
  * Natural join
  * Outer join
  * Cross join
* 오라클 조인 
  ```sql
  SELECT * 
    FROM EMP E, DEPT D 
   WHERE E.DEPTNO = D.DEPTNO ; 
  ```
* ANSI 조인 
  ```sql
  SELECT * 
    FROM EMP E JOIN DEPT D 
      ON E.DEPTNO = D.DEPTNO ; 
  ```
  
## Self Join
## Nonequijoin
```SQL
SELECT *
FROM SALGRADE ;

SELECT *
FROM EMP E
JOIN SALGRADE S
ON E.SAL BETWEEN S.LOSAL AND S.HISAL -- BETWEEN을 이용
```
![image](https://user-images.githubusercontent.com/79209568/114293586-4cf70980-9ad2-11eb-92fa-6add96573900.png)

## Outer Join
### ANSI조인
* FROM절의 테이블 순서가 중요하다.
#### LEFT OUTER JOIN
* 왼쪽의 테이블 모든 행을 출력
#### RIGHT OUTER JOIN
* 오른쪽의 테이블 모든 행을 출력
#### FULL OUTER JOIN
* 왼쪽 오른쪽 모든 행을 출력
* 성능이 떨어지므로 꼭 필요할 때만 사용하는 게 좋다

### ORACLE조인
* FROM절의 테이블 순서와 WHERE절 조건의 컬럼 순서는 중요하지 않다.

## Cartesian Product
* 카티션 프로덕트
* ANSI의 경우 CROSS JOIN이라고 써준다.
