# 조인을 사용하여 여러 테이블의 데이터 표시
## 조인 유형 및 구문
* 조인 : 여러 테이블에서 데이터를 가지고 오는 것
  * Natural join
  * Outer join
  * Cross join
* 애초에 한 테이블에 모든 정보를 넣지 않고 나눠놓은 후 조인을 통해 데이터를 사용하는 이유
  * 중복 값을 줄여서 저장 공간을 줄이면 비용을 줄일 수 있다.
  * 테이블이 크면 유지 관리 비용이 증가한다. 
* 조인할 때 테이블 별칭은 AS를 쓰면 오류가 난다. 테이블 명 바로 뒤에 별칭을 입력한다.
  ```sql
  FROM EMP AS E  -> ERROR
  FORM EMP E
  ```
### Oracle 조인 
  * 만약 조인 조건을 쓰지 않아도 에러가 나지 않는다
  ```sql
  SELECT * 
    FROM EMP E, DEPT D 
   WHERE E.DEPTNO = D.DEPTNO ; 
  ```
### ANSI 조인 
  * 만약 조인 조건을 쓰지 않으면 에러가 난다.
  * 가독성과 조건을 쓰지 않는 실수를 알기 위해 ANSI를 사용한다.
  ```sql
  SELECT * 
    FROM EMP E JOIN DEPT D 
      ON E.DEPTNO = D.DEPTNO ; 
  ```
 
## Natural Join
* ANSI만 가능
* NATURAL JOIN 절은 이름이 같은 두 테이블의 모든 열을 기반으로 한다.
* 이 절은 두 테이블에서 대응되는 모든 열의 값이 동일한 행을 선택한다.
* 동일한 이름을 가진 열이 서로 다른 데이터 유형을 가지면 오류가 반환된다.
* 경우에 따라서는 필요없는 컬럼까지 이름이 같기만하면 같이 조인된다.
```sql
SELECT *
FROM EMPLOYEES E NATURAL JOIN DEPARTMENTS D ;
```
### Using 사용해서 조인
* ANSI만 가능
* 여러 열의 이름이 동일하지만 데이터 유형은 다를 경우 USING 절을 사용하여 열을 Equijoin으로 지정한다.
* USING 절을 사용하면 두 개 이상의 열이 일치하는 경우 하나의 열만 일치하도록 할 수 있다.
* NATURAL JOIN과 USING 절은 함께 사용할 수 없다.
```SQL
SELECT *
FROM EMPLOYEES E JOIN DEPARTMENTS D 
USING (DEPARTMENT_ID); 
```
#### USING 주의사항
* 같은 이름의 컬럼이 있는 경우 어떤 것을 보여줄지 지정해줘야 한다.
  ```SQL
  SELECT LAST_NAME, DEPARTMENT_NAME, MANAGER_ID   -- ERROR 
  FROM EMPLOYEES   E  JOIN DEPARTMENTS D 
  USING (DEPARTMENT_ID) ;
  ```
  * MANAGER_ID가 두 테이블 모두 있음. 어떤 테이블의 MANAGER_ID를 출력할지 정해주지 않았기 때문에 에러가 남
* USING절에 사용된 컬럼을 SELECT절에 별칭을 넣으면 에러가 난다.
  ```SQL
  SELECT LAST_NAME, DEPARTMENT_NAME, E.MANAGER_ID, D.DEPARTMENT_ID -- ERROR 
  FROM EMPLOYEES   E  JOIN DEPARTMENTS D 
  USING (DEPARTMENT_ID) ; 
  ```
  * USING절에서 DEPARTMENT_ID를 사용했는데 SELECT절에서 테이블 별칭을 넣어서 사용했기때문에 에러가 남
> #### 이렇듯 복잡하고 신경써야할 것이 많기 때문에 `Natural Join`과 `Using`은 되도록이면 쓰지 말자!

### ON절을 사용해서 조인
* Natural join의 조인 조건은 기본적으로 이름이 같은 모든 열의 Equijoin이다.
* ON 절을 사용하여 임의 조건을 지정하거나 조인할 열을 지정한다.
* 조인 조건은 다른 검색 조건과는 별개다.
* ON 절을 사용하면 코드를 이해하기 쉽다.
```SQL
SELECT *
FROM EMPLOYEES   E 
JOIN DEPARTMENTS D 
  ON (E.DEPARTMENT_ID = D.DEPARTMENT_ID) ;
```
#### ON절을 사용해서 3-Way 조인 생성 (세 개이상 조인)
* 조인마다 ON절로 조건을 써준다. (JOIN - ON 이 하나의 형식)
```SQL
SELECT *  
FROM EMPLOYEES   E 
JOIN DEPARTMENTS D 
  ON E.DEPARTMENT_ID = D.DEPARTMENT_ID 
JOIN LOCATIONS L 
  ON D.LOCATION_ID = L.LOCATION_ID  ;
```
* 3개 이상 조인을 할 때는 Oracle join방식이 좀 더 간단하다.
```sql
SELECT *
FROM EMPLOYEES   E 
     ,DEPARTMENTS D 
     ,LOCATIONS   L 
WHERE E.DEPARTMENT_ID = D.DEPARTMENT_ID 
AND D.LOCATION_ID   = L.LOCATION_ID  ;
```

## Self Join
* 테이블 자체 조인이다. 자신을 조인한다.
* 별칭을 다르게 지정해서 사용한다.
```sql
-- JONES보다 많은 급여를 받는 사원
SELECT E.*, J.SAL
FROM EMP E
JOIN EMP J
  ON J.ENAME = 'JONES'
  AND J.SAL < E.SAL ;
```
> #### 별칭 지정
> ```SQL
> SELECT *   -- ERROR 
> FROM EMP E 
> WHERE EMP.SAL > 2000 ;
> ```
> * 에러나는 이유 : FROM절에서 EMP를 E로 별칭지정하면 불러온 테이블은 더이상 EMP가 아닌 E로 이름이 바뀐다. 따라서 EMP라는 테이블을 찾을 수 없다는 에러가 난다. 


## Nonequijoin
* 범위를 지정하여 조인한다. 조인 조건에서 `BETWEEN ... AND ...`을 사용한다.
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
* 일치하는 행만 반환하는 두 테이블의 조인을 Inner Join이라고 한다.
* Inner join의 결과와 함께 기준 행의 모든 행을 반환하는 것을 Outer join이라고 한다.
### ANSI조인
* FROM절의 테이블 순서가 중요하다.
#### LEFT OUTER JOIN
* 왼쪽의 테이블 모든 행을 출력
```sql
SELECT E.EMPLOYEE_ID, E.LAST_NAME, E.DEPARTMENT_ID, 
       D.DEPARTMENT_ID, D.DEPARTMENT_NAME    
  FROM EMPLOYEES   E 
  LEFT OUTER JOIN DEPARTMENTS D 
    ON E.DEPARTMENT_ID = D.DEPARTMENT_ID ;
```
#### RIGHT OUTER JOIN
* 오른쪽의 테이블 모든 행을 출력
```sql
SELECT E.EMPLOYEE_ID, E.LAST_NAME, E.DEPARTMENT_ID, 
       D.DEPARTMENT_ID, D.DEPARTMENT_NAME    
  FROM EMPLOYEES   E 
  RIGHT OUTER JOIN DEPARTMENTS D 
    ON E.DEPARTMENT_ID = D.DEPARTMENT_ID ;
```
#### FULL OUTER JOIN
* 왼쪽 오른쪽 모든 행을 출력
* 성능이 떨어지므로 꼭 필요할 때만 사용하는 게 좋다
```sql
SELECT E.EMPLOYEE_ID, E.LAST_NAME, E.DEPARTMENT_ID, 
       D.DEPARTMENT_ID, D.DEPARTMENT_NAME    
  FROM EMPLOYEES   E 
  FULL OUTER JOIN DEPARTMENTS D 
    ON E.DEPARTMENT_ID = D.DEPARTMENT_ID ;
```

### ORACLE조인
* FROM절의 테이블 순서와 WHERE절 조건의 컬럼 순서는 중요하지 않다.
* **조건에 기준 테이블의 반대 테이블 컬럼에 `(+)` 기호를 추가한다.**
(예를 들어 left outer join이면 왼쪽 테이블의 모든 값을 가져오고 오른쪽 테이블은 일치하는 값만 가져오므로 오른쪽 테이블의 데이터가 더 적다. 더 적게 가지고 있는 곳에 \+를 달아준다고 생각하면 쉽다.)

#### LEFT OUTER JOIN
```sql
SELECT E.EMPLOYEE_ID, E.LAST_NAME, E.DEPARTMENT_ID, 
       D.DEPARTMENT_ID, D.DEPARTMENT_NAME    
  FROM EMPLOYEES   E, DEPARTMENTS D 
 WHERE E.DEPARTMENT_ID = D.DEPARTMENT_ID (+);
```

#### RIGHT OUTER JOIN
```sql
SELECT E.EMPLOYEE_ID, E.LAST_NAME, E.DEPARTMENT_ID, 
       D.DEPARTMENT_ID, D.DEPARTMENT_NAME    
  FROM EMPLOYEES   E, DEPARTMENTS D 
 WHERE E.DEPARTMENT_ID (+) = D.DEPARTMENT_ID;
 ```
 
 #### FULL OUTER JOIN은 지원하지 않는다.
## Cartesian Product
* 카티션 프로덕트가 생성되는 경우
  * 조인 조건이 생략된 경우
  * 조인 조건이 잘못된 경우
  * 첫번째 테이블의 모든 행이 두번째 테이블의 모든 행에 조인되는 경우
* Cartesian Product가 생성되지 않게 하려면 유효한 조인 조건을 포함시켜야 한다.
* 직접 카티션 프로덕트를 생성할 경우 `CROSS JOIN`(ANSI의 경우)이라고 써준다.
