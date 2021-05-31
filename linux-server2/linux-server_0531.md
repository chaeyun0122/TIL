# DB Server 2
## SQL 문 alter
* 여러가지 용도로 사용 가능한 SQL문
### field 제어하는 방법
```
alter table <table 이름> { add | drop | change | modify } ...
```
* **modify** : field 자료형 변경
  ```
  modify <field 이름> <변경할자료형>
  ```
  > * `alter table tbtest modify phone char(14);`
  >   
  >   ![image](https://user-images.githubusercontent.com/79209568/120151914-d223b280-c227-11eb-9144-425fa92430a9.png)
* **add** : field 추가
  ```
  add <field 이름> <자료형> { first | after <기존 field 이름> |       };
  ```
  * `first` : 첫 번째 field로 추가
  * `after <field 이름>` : 기존 field의 다음 field로 추가
  * `생략` : 마지막 field로 추가
  > * `alter table tbtest add old int(3) after name;`
  >   * name 필드 뒤에 old 필드 추가 (기본으로 null값이 들어가있음)
  >   
  >   ![image](https://user-images.githubusercontent.com/79209568/120152812-efa54c00-c228-11eb-8334-fad730cd6d8d.png)
* **change** : field 이름\[+자료형] 변경 **(자료형 생략 불가)**
  ```
  change <기존 field 이름> <변경할 field 이름> <자료형>;
  ```
  > * `alter table tbtest change old age int(3);`
  >   * old 필드의 이름을 age로 변경
  >   
  >   ![image](https://user-images.githubusercontent.com/79209568/120153423-aacde500-c229-11eb-8cf3-d780a92ffefe.png)

* **drop** : field 제거
  ```
  drop <제거할 field 이름>
  ```
  > * `alter table tbtest drop addr;`
  >   * addr 필드 삭제
  >   
  >   ![image](https://user-images.githubusercontent.com/79209568/120153761-131cc680-c22a-11eb-95c5-0e9f83e288c7.png)


## 한글 데이터 사용
* `show variables like 'c%';` 
  
  ![image](https://user-images.githubusercontent.com/79209568/119961673-4a4c6700-bfe1-11eb-98e8-a5980414747d.png)
  * `utf8`로 변경해줘야한다.
* db 나오고 `/etc/my.cnf` 파일 열기
* `[mysqld]` 영역에 내용 추가
  ```
  character-set-server = utf8
  collation-server = utf8_general_ci
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/120154638-0b115680-c22b-11eb-85bc-036ad6187186.png)
* 데몬 재실행 `systemctl restart mariadb`
* **이 후 만들어지는 모든 DB는 한글을 지원한다.**
* 확인해보면 라틴어 남은 것이 없다.
  
  ![image](https://user-images.githubusercontent.com/79209568/120155285-9d195f00-c22b-11eb-9ebd-880566efc82d.png)
### 기존에 만들었던 DB도 한글 사용 가능하게 만들기
```
alter database <database 이름> default character set utf8;
commit;
```
> * `use dbtest;` : 기존에 있던 DB를 사용하도록 이동
> * `status;`
>   * DB가 latin으로 되어있다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120156048-80315b80-c22c-11eb-9481-92980ece16d8.png)
> * `alter database dbtest default character set utf8; / commit;`
>   * 모두 utf8로 변경되었다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120156416-ecac5a80-c22c-11eb-9471-221c2d4749e7.png)


### 기존에 만들었던 table도 한글 사용 가능하게 만들기
```
(database 사용 상태로 전환)
alter table <table 이름> modify <field 이름> <자료형> char set utf8;
```
> * `alter table tbtest modify name char(20) char set utf8;`
>   * 이름을 한글 사용이 가능하도록 변경한다.
> * `update tbtest set name='테스트3' where num=3;`
>   * 3번의 이름을 한글로 변경한다.
>   
>   ![image](https://user-images.githubusercontent.com/79209568/120156784-47de4d00-c22d-11eb-80ac-a4b932c7c406.png)
