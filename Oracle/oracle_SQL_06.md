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
