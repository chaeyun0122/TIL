# 데이터베이스 저장 영역 구조 관리
```sql
-- 테이블 스페이스 생성
CREATE TABLESPACE tbs2
DATAFILE 'D:\app\user\oradata\orcl\tbs2.dbf' SIZE 10M ;

-- 테이블 생성
CREATE TABLE t2
( id      NUMBER, 
  name   CHAR(1000)) --테이블은 객체
SEGMENT CREATION IMMEDIATE --저장공간을 할당
TABLESPACE tbs2 ; --테이블이 저장되는 테이블 스페이스 지정

SELECT tablespace_name, status, contents, logging, segment_space_management, bigfile
FROM dba_tablespaces ;

SELECT file_id, file_name, tablespace_name, bytes/1024/1024 AS MB, 
       blocks, autoextensible, increment_by, TRUNC(maxbytes/1024/1024/1024) AS MAX_GB
FROM dba_data_files ;
```
![image](https://user-images.githubusercontent.com/79209568/115951581-678ea100-a51c-11eb-8862-22fb92990242.png)

```sql
SELECT segment_name, segment_type, tablespace_name, 
       header_file, header_block, bytes, blocks,  extents
FROM dba_segments 
WHERE segment_name = 'T2' ; 

SELECT segment_name, extent_id, file_id, block_id, bytes, blocks 
FROM dba_extents 
WHERE segment_name = 'T2' ;

INSERT INTO t2 
SELECT object_id, object_name FROM dba_objects 
WHERE rownum <= 100 ; 

COMMIT ; 

SELECT segment_name, segment_type, tablespace_name, 
       header_file, header_block, bytes, blocks, extents
FROM dba_segments 
WHERE segment_name = 'T2' ; 

SELECT segment_name, extent_id, file_id, block_id, bytes, blocks 
FROM dba_extents 
WHERE segment_name = 'T2' ;

SELECT id, rowid, DBMS_ROWID.ROWID_RELATIVE_FNO(rowid)   AS FILE_NO, 
                  DBMS_ROWID.ROWID_BLOCK_NUMBER(rowid)   AS BLOCK_NO, 
                  DBMS_ROWID.ROWID_ROW_NUMBER(rowid)     AS ROW_NO
FROM t2 ;
```
![image](https://user-images.githubusercontent.com/79209568/115951655-ceac5580-a51c-11eb-9664-fc2833b318cb.png)

