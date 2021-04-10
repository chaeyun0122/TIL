# 데이터 제한 및 정렬
## 행 제한 방법
### WHERE 절
![image](https://user-images.githubusercontent.com/79209568/114258597-91b36f80-9a02-11eb-8376-1bb1fa9fafb9.png)
* WHERE 절을 사용하여 반환되는 행을 제한한다.
* WHERE 절은 FROM 절 다음에 나온다.

#### 문자열 및 날짜
* 문자열 및 날짜 값은 **작은 따옴표**로 묶는다. (숫자는 그냥 써도 된다.)
* 문자 값은 **대소문자를 구분**하고 날짜 값은 **형식을 구분**한다.
  * 기본 날짜 표시 형식: `DD-MON-RR`
  ```sql
  -- 문자열
  SELECT * 
  FROM EMP
  WHERE JOB = 'SALESMAN' ;
  
  -- 날짜
  SELECT * 
  FROM EMP 
  WHERE HIREDATE = '1982/12/09' ; -- 기본 날짜 형식을 'YYYY/MM/DD'로 바꿔놨기때문에 해당 형식으로 작성하지 않으면 오류남
  ```
  
### 비교 연산자를 사용하여 조건 정의
#### 비교 연산자
|      **연산자**     |            **의미**           |
|:-------------------:|:-----------------------------:|
|          =          | 같음                          |
|          >          | 보다 큼                       |
|          >=         | 보다 크거나 같음              |
|          <          | 보다 작음                     |
|          <=         | 보다 작거나 같음              |
|          <>         | 같지 않음                     |
| BETWEEN ... AND ... | 두 값 사이(**경계값 포함**)   |
|       IN(set)       | 값 리스트 중 일치하는 값 검색 |
|         LIKE        | 일치하는 문자 패턴 검색       |
|       IS NULL       | null 값인지 여부              |

* 비교연산자는 숫자와 날짜는 물론 **문자열**도 대소비교 가능 
  ```SQL
  SELECT *
  FROM EMP
  WHERE ENAME < 'SCOTT' ; -- 에러 안남
  ```
* `BETWEEN ... AND ...`는 꼭 앞의 값이 뒤의 값보다 작아야 결과가 나온다. (오류는 안나지만 값이 안나옴)
  
  ```SQL
  SELECT *
  FROM EMP
  WHERE SAL BETWEEN 2000 AND 3000; -- O

  SELECT *
  FROM EMP
  WHERE SAL BETWEEN 3000 AND 2000; -- X
  ```
  ```SQL
  -- 같은 값이 출력됨
  SELECT * 
  FROM EMP 
  WHERE SAL >= 2000
    AND SAL <= 3000 ; 
  ```

* `IN (set)`은 리스트 안의 값이 포함되면 참을 반환한다.

  ```SQL
  SELECT * 
  FROM EMP 
  WHERE DEPTNO IN (10,20) ; 
  
  -- 같은 값이 출력됨
  SELECT * 
  FROM EMP 
  WHERE DEPTNO = 10 
     OR DEPTNO = 20 ;
  ```
* `LIKE`는 유효한 검색 문자열 값의 대체 문자 검색을 수행한다.
  * 검색 조건에는 리터럴 문자나 숫자가 포함될 수 있다.
    - **%**는 **0개 이상의 문자**를 나타냄
    - **\_**은 **한 문자**를 나타냄

    ```SQL
    -- S로 시작하는 5글자 문자열
    SELECT * 
    FROM EMP 
    WHERE ENAME LIKE 'S____' ;

    -- 길동으로 끝나는 문자열 (길이 상관 x)
    SELECT * 
    FROM EMP 
    WHERE ENAME LIKE '%길동' ;
    
    -- 김으로 시작하는 문자열
    SELECT * 
    FROM EMP 
    WHERE ENAME LIKE '김%' ;
    
    -- 세 번째에 S가 들어가는 문자열
    SELECT * 
    FROM EMP 
    WHERE ENAME LIKE '__S%' ;
    ```
  * 만약 %나 \_를 문자열로 인식하고 싶을 경우 `ESCAPE`를 사용한다. 
    - `ESCAPE` 뒤에 문자를 지정하고 LIKE절 안에 문자열로 인식하고 싶은 문자의 앞에 써준다.
    
    ```SQL
    SELECT * 
    FROM CUSTS 
    WHERE EMAIL LIKE '%!_%' ESCAPE '!'; 
    ```
* `NULL`은 비교 연산자를 사용할 수 없다. 무조건 `IS NULL` 혹은 `IS NOT NULL`만 가능
  
    ```SQL
    SELECT * 
    FROM EMP 
    WHERE COMM IS NULL ;
    
    SELECT * 
    FROM EMP 
    WHERE COMM = NULL ; -- 오류는 안나지만 값이 나오지 않음
    ```
> ### WHERE 절 이해하기!
> > 이 경우 어떤 출력이 나올까?
> ```SQL
> SELECT * 
> FROM EMP 
> WHERE 1 = 1 ;
> ```
> * **전체 행이 출력 된다.**
>   * WHERE 절은 참과 거짓을 판별하는 절이므로 `1 = 1`이 참이기 때문에 모든 값이 참이 된다.
>   * 마찬가지로 `WHERE 1 = 0`의 경우는 거짓이므로 모든 값이 거짓이 되어 하나도 출력 되지 않는다.
  
### 논리 연산자를 사용하여 조건 정의
#### 논리 연산자
| **연산자** |                          **의미**                         |
|:----------:|:---------------------------------------------------------:|
|     AND    | 두 구성 요소 조건이 **모두 참**인 경우 TRUE를 반환합니다. |
|     OR     | 구성 요소 중 **하나가 참**인 경우 TRUE를 반환합니다.      |
|     NOT    | 조건이 **거짓**인 경우 TRUE를 반환합니다. (비교 연산자 앞 쪽에 써줌) |

## 표현식의 연산자 우선 순위 규칙
![image](https://user-images.githubusercontent.com/79209568/114260108-caa51180-9a0d-11eb-88cf-4e917b065b86.png)
* AND 와 OR의 우선순위를 잘 살펴봐야한다!
  
  ```SQL
  SELECT * 
  FROM EMP 
  WHERE DEPTNO = 10 
     OR DEPTNO = 30 
    AND SAL    > 2000 ; 
  ```
  * 이 경우 AND가 먼저 실행 된 후 OR이 실행된다.
  * 만약 OR을 먼저 실행하고 싶으면 괄호를 해준다.
  
  ```SQL
  SELECT * 
  FROM EMP 
  WHERE (DEPTNO = 10 
     OR DEPTNO = 30 )
    AND SAL    > 2000 ; 
  ```
