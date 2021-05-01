# 데이터베이스 백업 수행

```sql
rman target /

SHOW ALL ; -- 현재 환경 설정 확인
```
![image](https://user-images.githubusercontent.com/79209568/116773711-058ce900-aa92-11eb-927d-e6db9aa36990.png)


```
CONFIGURE CONTROLFILE AUTOBACKUP ON ;
CONFIGURE BACKUP OPTIMIZATION ON ; 
CONFIGURE RETENTION POLICY TO RECOVERY WINDOW OF 7 DAYS ; -- 최소한 7일 이내에 백업된 내용이 있어야한다는 설정.(7일당일 까지도 있어야함)
```
![image](https://user-images.githubusercontent.com/79209568/116773724-19d0e600-aa92-11eb-9a70-87ee72f7da47.png)

```
-- db의 구성정보를 가져옴
REPORT SCHEMA ;
```
![image](https://user-images.githubusercontent.com/79209568/116774127-58b46b00-aa95-11eb-8843-68a523e0cf59.png)

```
-- 백업이 필요한 것을 가져옴
REPORT NEED BACKUP ; 
```
![image](https://user-images.githubusercontent.com/79209568/116774134-64a02d00-aa95-11eb-8a62-a29af6696f84.png)

```
-- 백업 리스트를 가져옴
LIST BACKUP ;
LIST COPY ; --이미지 카피 
```
![image](https://user-images.githubusercontent.com/79209568/116774144-7aaded80-aa95-11eb-849a-7eebbbef874a.png)

```
BACKUP DATAFILE 1 ; -- 특정 파일만
BACKUP TABLESPACE users ; -- 특정 테이블 스페이스만
BACKUP DATABASE PLUS ARCHIVELOG ; -- 전체 백업
-- 따로 경로를 지정하지 않으면 자동으로 FRA 폴더를 씀

BACKUP AS COPY TABLESPACE USERS ; -- db 전체를 이미지 카피로 백업

BACKUP INCREMENTAL LEVEL 0 DATABASE ; -- 풀백업
BACKUP INCREMENTAL LEVEL 1 DATABASE ; -- D1
BACKUP INCREMENTAL LEVEL 1 CUMULATIVE DATABASE; -- C1
```

```
-- 특정 경로에 백업 지정 format뒤에 경로를 지정/ tag를 붙여서 어떤 백업인지 확인하기 쉬움
BACKUP TABLESPACE users FORMAT 'D:\app\user\BACKUP\%d_%s_%p.bak' TAG=TBS_BACKUP ;
```

```
-- 백업을 진행 했기 때문에 이제 나옴
LIST BACKUP ;
LIST COPY ;
LIST BACKUP SUMMARY ; -- 간단한 요약본 확인
```
![image](https://user-images.githubusercontent.com/79209568/116774181-cf516880-aa95-11eb-8a79-2bc93b581fa9.png)

![image](https://user-images.githubusercontent.com/79209568/116774185-daa49400-aa95-11eb-80a2-0100d960e71d.png)

![image](https://user-images.githubusercontent.com/79209568/116774188-e55f2900-aa95-11eb-883e-c28d7a1bdd9f.png)


```
-- 보존 정책상 불필요한 것이 무엇인지 확인
REPORT OBSOLETE ;
```
![image](https://user-images.githubusercontent.com/79209568/116774199-f60f9f00-aa95-11eb-9692-d82b776245cf.png)

```
-- 1로 바꾸면 마지막에 했던것만 보존 대상이고 이전은 삭제 대상임
CONFIGURE RETENTION POLICY TO REDUNDANCY 1 ;
REPORT OBSOLETE ;
```
![image](https://user-images.githubusercontent.com/79209568/116774218-0e7fb980-aa96-11eb-888e-b39e349ee326.png)


```
-- 삭제 대상을 지워줌
DELETE OBSOLETE ; 
DELETE NOPROMPT OBSOLETE ; -- 예/아니오를 묻지 않음
```
![image](https://user-images.githubusercontent.com/79209568/116774252-56064580-aa96-11eb-8aee-c11a405cd5a1.png)

```
-- 모든 백업들을 삭제
DELETE NOPROMPT BACKUP ; 
DELETE NOPROMPT COPY ; 
```
![image](https://user-images.githubusercontent.com/79209568/116774259-628a9e00-aa96-11eb-9ee1-d28f1c120522.png)

![image](https://user-images.githubusercontent.com/79209568/116774268-6b7b6f80-aa96-11eb-842c-1451903c2bb0.png)

```
-- 복구 실습을 위한 백업 작업
BACKUP DATABASE PLUS ARCHIVELOG ;
```

```
-- 백업 파일들을 체킹해주는 명령어
CROSSCHECK BACKUP ; 
CROSSCHECK COPY ;

-- 다 끝나고 나오기
EXIT 
```
