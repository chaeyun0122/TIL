## SQL 문장 처리 과정
1. Parse    : 구문 분석 - 실행 계획 확보
2. Bind     : 바인드 변수에 값 입력
3. Excute   : 실행
4. Fetch    : 인출
  
```sql
drop table t1 purge ; 

create table t1 
as select * from dba_objects ; 

delete t1 where rownum <= 30000 ;
commit;

-- 9초 걸림
select * 
from t1 a 
where 2 < (select count(*)
              from t1 
             where data_object_id  = a.data_object_id) ; 
```
* 실행 계획 F10 (실행계획은 실행한 것이 아닌 계획이다. 예를들어 네비게이션)
![image](https://user-images.githubusercontent.com/79209568/116805313-82869400-ab60-11eb-8efc-a6dc4dbce287.png)

## 실행 통계 확인
* `/*+ gather_plan_statistics */` : 실행 통계 

* 두 쿼리를 한번에 같이 실행
```sql
select /*+ gather_plan_statistics */ * 
from t1 a 
where 2 < (select count(*)
             from t1 
            where data_object_id  = a.data_object_id) ; 

-- shared pool에 문장을 실행하는데 사용한 리소스 양을 저장
-- 이것을 꺼내오는 것
select * 
from table(dbms_xplan.display_cursor(null,null,'allstats last')) ;

```
* 결과
```
SQL_ID  f54z30vrtb7pt, child number 0
-------------------------------------
select /*+ gather_plan_statistics */ *  from t1 a  where 2 < (select 
count(*)              from t1              where data_object_id  = 
a.data_object_id)
 
Plan hash value: 2626881942
 
--------------------------------------------------------------------------------------
| Id  | Operation           | Name | Starts | E-Rows | A-Rows |   A-Time   | Buffers |
--------------------------------------------------------------------------------------
|   0 | SELECT STATEMENT    |      |      1 |        |      0 |00:00:08.38 |    6351K|
|*  1 |  FILTER             |      |      1 |        |      0 |00:00:08.38 |    6351K|
|   2 |   TABLE ACCESS FULL | T1   |      1 |  64201 |  61972 |00:00:00.01 |    1547 |
|   3 |   SORT AGGREGATE    |      |   4105 |      1 |   4105 |00:00:08.36 |    6350K|
|*  4 |    TABLE ACCESS FULL| T1   |   4105 |    642 |   4108 |00:00:08.36 |    6350K|
--------------------------------------------------------------------------------------
 
Predicate Information (identified by operation id):
---------------------------------------------------
 
   1 - filter(>2)
   4 - filter("DATA_OBJECT_ID"=:B1)
 
Note
-----
   - dynamic statistics used: dynamic sampling (level=2)
```

* id : 그냥 행번호
* operation : 읽는 순서는 (위에서 내려가며) 안쪽 깊이에서 바깥 깊이, 같은 깊이에서는 위에서 아래로 **(2-4-3-1-0)**
* Stats : 수행 횟수
* E-Rows : 만날거라고 **예상**하는 행의 개수
* A-Rows : **실제**로 만난 행의 개수
* A-Time : 수행 시간
* Buffers : I/O 횟수

## 메모리를 비우고 실행
```sql
alter system flush buffer_cache ; 
```
```sql
--한번에 실행
select /*+ gather_plan_statistics */ * 
from t1 a 
where 2 < (select count(*)
             from t1 
            where data_object_id  = a.data_object_id) ; 

select * 
from table(dbms_xplan.display_cursor(null,null,'allstats last')) ; --135초 걸림
```
* Read가 추가되면 **물리적인 i/o가 일어난 것이다**.
```
 
-----------------------------------------------------------------------------------------------
| Id  | Operation           | Name | Starts | E-Rows | A-Rows |   A-Time   | Buffers | Reads  |
-----------------------------------------------------------------------------------------------
|   0 | SELECT STATEMENT    |      |      1 |        |      0 |00:03:31.84 |    6343K|   6331K|
|*  1 |  FILTER             |      |      1 |        |      0 |00:03:31.84 |    6343K|   6331K|
|   2 |   TABLE ACCESS FULL | T1   |      1 |  64201 |  61972 |00:00:00.05 |    1545 |   1543 |
|   3 |   SORT AGGREGATE    |      |   4105 |      1 |   4105 |00:03:31.73 |    6342K|   6329K|
|*  4 |    TABLE ACCESS FULL| T1   |   4105 |    642 |   4108 |00:03:31.70 |    6342K|   6329K|
-----------------------------------------------------------------------------------------------
 
Predicate Information (identified by operation id):
---------------------------------------------------
 
   1 - filter(>2)
   4 - filter("DATA_OBJECT_ID"=:B1)
 
```
> ### 현재 4번에서 조건도 있는데 table full scan을 해서 3분이나 걸린다. 이것을 해결해야한다.
 
## 인덱스를 만들어서 다시 실행
```sql
alter session set statistics_level = all ; 

create index t1_ix on t1(data_object_id) ;
```
```sql
--한번에 실행
select /*+ gather_plan_statistics */ * 
from t1 a 
where 2 < (select count(*)
             from t1 
            where data_object_id  = a.data_object_id) ; 

select * 
from table(dbms_xplan.display_cursor(null,null,'allstats last')) ; 
```
* 성능이 훨씬 향상되었다.
```
 
-----------------------------------------------------------------------------------------------
| Id  | Operation          | Name  | Starts | E-Rows | A-Rows |   A-Time   | Buffers | Reads  |
-----------------------------------------------------------------------------------------------
|   0 | SELECT STATEMENT   |       |      1 |        |      0 |00:00:00.08 |    1829 |   1552 |
|*  1 |  FILTER            |       |      1 |        |      0 |00:00:00.08 |    1829 |   1552 |
|   2 |   TABLE ACCESS FULL| T1    |      1 |  64201 |  61972 |00:00:00.05 |    1545 |   1542 |
|   3 |   SORT AGGREGATE   |       |   4105 |      1 |   4105 |00:00:00.02 |     284 |     10 |
|*  4 |    INDEX RANGE SCAN| T1_IX |   4105 |    642 |   4108 |00:00:00.02 |     284 |     10 |
-----------------------------------------------------------------------------------------------
 
Predicate Information (identified by operation id):
---------------------------------------------------
 
   1 - filter(>2)
   4 - access("DATA_OBJECT_ID"=:B1)
 
```

> ### 성능을 더 향상 시킬 수 있을까?
## 분석함수를 사용
```sql
select /*+ gather_plan_statistics */ *
from (select t1.*, count(data_object_id) over(partition by data_object_id) as cnt 
        from t1)
where cnt > 2 ; 


select * 
from table(dbms_xplan.display_cursor(null,null,'allstats last')) ; 
```
* 메모리를 더 사용하지만 I/O가 1545로 줄어든 것을 확인할 수 있다.
```
 
--------------------------------------------------------------------------------------------------------------------------
| Id  | Operation           | Name | Starts | E-Rows | A-Rows |   A-Time   | Buffers | Reads  |  OMem |  1Mem | Used-Mem |
--------------------------------------------------------------------------------------------------------------------------
|   0 | SELECT STATEMENT    |      |      1 |        |      0 |00:00:00.14 |    1545 |   1542 |       |       |          |
|*  1 |  VIEW               |      |      1 |  64201 |      0 |00:00:00.14 |    1545 |   1542 |       |       |          |
|   2 |   WINDOW SORT       |      |      1 |  64201 |  61972 |00:00:00.13 |    1545 |   1542 |    10M|  1248K| 9370K (0)|
|   3 |    TABLE ACCESS FULL| T1   |      1 |  64201 |  61972 |00:00:00.06 |    1545 |   1542 |       |       |          |
--------------------------------------------------------------------------------------------------------------------------
 
Predicate Information (identified by operation id):
---------------------------------------------------
 
   1 - filter("CNT">2)
 
```

## 오라클이 고도화되며 옵티마이저가 유저가 던진 문장을 그대로 쓰지 않음
```sql
alter session set statistics_level = all ; 

select * 
from scott.emp 
where deptno in (select deptno from scott.dept) ;    
   
 select * 
from table(dbms_xplan.display_cursor(null,null,'allstats last')) ; 
```
```
---------------------------------------------------------------------------------------------
| Id  | Operation         | Name | Starts | E-Rows | A-Rows |   A-Time   | Buffers | Reads  |
---------------------------------------------------------------------------------------------
|   0 | SELECT STATEMENT  |      |      1 |        |     12 |00:00:00.01 |       7 |      6 |
|*  1 |  TABLE ACCESS FULL| EMP  |      1 |     12 |     12 |00:00:00.01 |       7 |      6 |
---------------------------------------------------------------------------------------------
Predicate Information (identified by operation id):
---------------------------------------------------
   1 - filter("DEPTNO" IS NOT NULL)
 
```
* 옵티마이저는 dept 테이블에 가지 않고 아래의 쿼리로 바꿔서 실행계획을 짠 것이다.
  ```sql
  select * 
  from scott.emp 
  where deptno is not null ;      
  ```


