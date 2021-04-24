# 오라클 네트워크 환경 구성
* 서버 딴에 적절한 리스너가 존재해야한다.

# 리스너 추가
![image](https://user-images.githubusercontent.com/79209568/115947182-ed045800-a500-11eb-8868-5fd27608bab3.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947188-f42b6600-a500-11eb-8b64-824c100fb442.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947190-f7beed00-a500-11eb-87ea-56d3fddea171.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947195-fd1c3780-a500-11eb-9070-a810262ee4e7.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947198-00afbe80-a501-11eb-9607-c69712d74e3f.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947203-0d341700-a501-11eb-8ad8-00a02541fa31.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947205-10c79e00-a501-11eb-9f1e-5817f6f4e112.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947210-158c5200-a501-11eb-9a8a-eb45295b6c3b.png)

* 명령 프롬프트 관리자로 실행
* `lsnrctl status LISTENER` : 기본 리스너 상태 확인
  * 기본 리스너에는 서비스 정보를 확인할 수 있다.
    ![image](https://user-images.githubusercontent.com/79209568/115947767-ea0b6680-a504-11eb-935c-5a3a5a8580c3.png)

* `lsnrctl status L1` : 새로 생성한 리스너 상태 확인
  * 새로운 리스너에는 서비스 정보가 없다.
    ![image](https://user-images.githubusercontent.com/79209568/115947771-f394ce80-a504-11eb-9130-5c9030717914.png)
  * 서비스 정보를 자동/수동 설정해줘야한다.

# 리스너 서비스 정보 설정
![image](https://user-images.githubusercontent.com/79209568/115947776-fabbdc80-a504-11eb-9a71-34a5ace6c954.png)
    
![image](https://user-images.githubusercontent.com/79209568/115947784-0ad3bc00-a505-11eb-9732-a79b69e00061.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947790-158e5100-a505-11eb-9d25-a24bd7169aa5.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947793-1a530500-a505-11eb-9b08-e5ddfa8ea61a.png)
  
* 다시 연결  
![image](https://user-images.githubusercontent.com/79209568/115947808-39ea2d80-a505-11eb-8a3c-bf3c369b2bd1.png)
* 설정한 서비스 확인  
![image](https://user-images.githubusercontent.com/79209568/115947819-51c1b180-a505-11eb-8876-282ac19d36a2.png)

# 서비스 이름 지정
![image](https://user-images.githubusercontent.com/79209568/115947831-5f773700-a505-11eb-8e4f-9f8279ae21f9.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947834-6605ae80-a505-11eb-95c3-c79c2fca45bf.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947841-6c942600-a505-11eb-8f1d-25f69b620c13.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947846-7027ad00-a505-11eb-917e-e47de605b661.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947864-87ff3100-a505-11eb-93fa-694e4c6d4ce3.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947874-951c2000-a505-11eb-8aee-78283184ab17.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947878-99e0d400-a505-11eb-8926-9f103dfb3672.png)
  
# 백업 리스너 추가  
![image](https://user-images.githubusercontent.com/79209568/115947898-b1b85800-a505-11eb-8d9b-cc506d92efe5.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947901-b7ae3900-a505-11eb-91df-fabacdf31d2c.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947906-baa92980-a505-11eb-9524-0aee85b73104.png)
  
![image](https://user-images.githubusercontent.com/79209568/115947910-be3cb080-a505-11eb-9be5-815db8873483.png)
  
* 확인  
  ![image](https://user-images.githubusercontent.com/79209568/115947915-c85eaf00-a505-11eb-808b-ab76e5417a11.png)

> ## shared server
> ```sql
> alter system set shared_servers = 2 ; 
> alter system set max_shared_servers = 5 ; 
> alter system set shared_server_sessions = 100 ; 
> alter system set dispatchers = '(PROTOCOL=TCP)(DISPATCHERS=2)' ;
> alter system set max_dispatchers = 3 ;
> ```

# DB 링크
* db가 둘 이상 존재하고 한 db에 접속했다가 다른 db에 접속하고 싶을 때 전 db와의 연결을 끊는 게 아닌, db 간의 링크를 둬서 하나의 연결로 두 db를 사용한다.




