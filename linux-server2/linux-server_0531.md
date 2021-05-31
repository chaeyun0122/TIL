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
