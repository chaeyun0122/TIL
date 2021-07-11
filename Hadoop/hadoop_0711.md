# 환경설정2
## CentOS의 JAVA를 바꾸기
### JAVA 지우기
- 현재 설치된 자바를 확인. 지워야할 파일들이다.
  
  ![image](https://user-images.githubusercontent.com/79209568/125185082-83712b80-e25d-11eb-9fe5-97b449ff73e0.png)

- 설치된 자바를 삭제
  ```
  yum remove java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64
  yum remove java-1.8.0-openjdk-headless-1.8.0.131-11.b12.el7.x86_64
  ```
- 확인 : 아무것도 안나와야 한다.

  ![image](https://user-images.githubusercontent.com/79209568/125185102-9edc3680-e25d-11eb-9921-c4a230072d18.png)

### 새로 설치한 JAVA 등록
- 사용자 계정으로 이동 : `exit`
- `.bash_profile`에 자바를 등록해준다. 
  
  ```
  vi .bash_profile
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125185925-6428cd00-e262-11eb-9cbe-ef1ddf7315bc.png)

### 적용 후 확인
- 적용하기
  
  ```
  source .bash_profile
  ```
- hadoop과 java 버전을 확인한다.
  
  ![image](https://user-images.githubusercontent.com/79209568/125185964-95a19880-e262-11eb-8d47-43a2761b569d.png)
  
## 방화벽 내리기
- 특정 IP를 열어주는 명령어를 사용해서 master, slave1, slave2의 IP 모두 열어준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/125186038-fcbf4d00-e262-11eb-8de6-5b83b4ab6e4d.png)
  
- 방화벽 설정을 적용하기
  
  ```
  firewall-cmd --reload
  ```
- 적용된 방화벽 리스트 확인하기

  ```
  firewall-cmd --list-all
  ```
  
![image](https://user-images.githubusercontent.com/79209568/125186116-5fb0e400-e263-11eb-949a-1d4d5318df1e.png)

- 해당 방화벽 열어주는 작업을 slave1, slave2 가상머신에서도 해준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/125186137-87a04780-e263-11eb-8959-e4edb8d545ae.png)

## ssh rsa키 이용하기
- 공개 키를 생성해서 나눠 가지고 해당 키를 가지고 있으면 바로 접근가능하도록 설정
### 공개키 생성
- master 사용자 계정으로 공개키를 생성하는 코드를 작성한다.
 
  ```
  ssh-keygen -t rsa
  ```
  ![image](https://user-images.githubusercontent.com/79209568/125193751-183d4e80-e289-11eb-99b2-c15ba13d7273.png)
### .ssh 확인
- `ls -la` 로 숨김 파일까지 확인하면 .ssh 폴더가 있는 것을 확인할 수 있다.
  
  ![image](https://user-images.githubusercontent.com/79209568/125193759-28edc480-e289-11eb-80ff-550c1263a7be.png)
- 폴더 안을 확인해보면 `id_rsa`는 master의 개인키고, `id_rsa.pub`가 공개키다.
  
  ![image](https://user-images.githubusercontent.com/79209568/125193812-64888e80-e289-11eb-8d62-5d13199ee780.png)

### slave1, slave2에서도 개인키/공개키 생성
### master 공개키를 authorized_key에 추가
- `id_rsa.pub`을 `authorized_keys`에 복사한다.
  
  ```
  cp id_rsa.pub authorized_keys
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125193924-f0021f80-e289-11eb-820d-20b1d78e32c6.png)
### authorized_keys에 공개키 모으기
- slave1, slave2, backup 에서 생성했던 공개키들을 authorized_keys 파일에 추가해준다.( \> : 덮어쓰기, \>\> : 추가하기)
  ```
  ssh hadoop@slave1 cat ~/.ssh/id_rsa.pub >> authorized_keys
  ssh hadoop@slave2 cat ~/.ssh/id_rsa.pub >> authorized_keys
  ssh hadoop@backup cat ~/.ssh/id_rsa.pub >> authorized_keys
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125194031-6dc62b00-e28a-11eb-9b9b-9a015391d073.png)
  
### 확인하기
- master, slave1, slave2, master(backup) 네 개의 공개키가 추가되었으면 잘 된 것이다.
  
  ```
  cat authorized_keys
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125194063-9817e880-e28a-11eb-95ba-c7b3a76ae2ad.png)

### 공개키를 모든 node에 재분배하기
- 모든 공개키를 모은 authorized_keys 파일을 각각 복사해준다.
  
  ```
  scp authorized_keys hadoop@slave1:~/.ssh/
  scp authorized_keys hadoop@slave2:~/.ssh/
  scp authorized_keys hadoop@backup:~/.ssh/
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125194315-add9dd80-e28b-11eb-9ed6-b714923fcaa6.png)

### 적용하기
- master에서만 적용해주면 된다.
  
  ```
  ssh-add
  ```
- 권한은 644
## 하둡 환경 설정
### 설정 파일 경로
- 하둡 데몬을 실행시키기 위해 환경 설정 파일들을 설정해야한다. 해당 설정 파일들이 위치한 경로로 이동한다.
  
  ```
  cd /usr/local/hadoop-2.10.1/etc/hadoop/
  ```
### masters, slaves
- **masters**
  - 보조네임노드를 실행할 서버를 설정하는 파일이다.
  - vi를 통해 backup을 입력해준다.
- **slaves**
  - 데이터노드를 실행할 서버를 설정하는 파일이다.
  - localhost가 기본 값이지만 지워주고 slave1, slave2 서버를 입력해준다.
  
  ![image](https://user-images.githubusercontent.com/79209568/125194662-3dcc5700-e28d-11eb-9997-bf3065efd452.png)

### hadoop-env.sh
- 하둡 실행하는 쉘 스크립트 파일에서 필요한 환경변수를 설정하는 파일
- jdk 설치 경로를 등록해준다.
  
  ```
  vi hadoop-env.sh

  export JAVA_HOME=/usr/local/jdk1.8.0_291
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125188704-b1f80200-e26f-11eb-8b72-22b4de9a542f.png)

### core-site.xml 
- 로그파일, 네트워크 튜닝, I/O 튜닝, 파일시스템 튜닝, 압축 등 하부 시스템의 설정 파일
- **HDFS와 맵리듀스에서 공통으로 사용할 환경정보를 설정하는 곳**
  
  ```
  vi core-site.xml
  
  <configuration>
          <property>
                  <name>fs.default.name</name>
                  <value>hdfs://master:9000</value>
          </property>
          <property>
                  <name>hadoop.tmp.dir</name>
                  <value>/usr/local/hadoop-2.10.1/tmp</value>
          </property>
  </configuration>
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125194859-1aee7280-e28e-11eb-91a8-42ac675be7d6.png)

### hdfs-site.xml
- HDFS에서 사용할 환경 정보를 설정하는 파일
- dfs.replication 속성이 1일때는 가상 분산 모드로 하둡을 실행, 3일때는 완전 분산 모드로 하둡을 실행 한다는 뜻

  ```
  vi hdfs-site.xml

  <configuration>
          <property>
                  <name>dfs.replication</name>
                  <value>3</value>
          </property>
          <property>
                  <name>dfs.permissions.enabled</name>
                  <value>false</value>
          </property>
          <property>
                  <name>dfs.webhdfs.enabled</name>
                  <value>true</value>
          </property>
          <property>
                  <name>dfs.namenode.http.address</name>
                  <value>master:50070</value>
          </property>
          <property>
                  <name>dfs.secondary.http.address</name>
                  <value>backup:50090</value>
          </property>
  </configuration>
  ```

  ![image](https://user-images.githubusercontent.com/79209568/125195037-d57e7500-e28e-11eb-80cf-dfed21f397ac.png)

### mapred-site.xml
- 맵리듀스 환경 설정 파일
- 존재하지 않을 경우 탬플릿파일을 복사한 후 사용한다.
  
  ```
  cp mapred-site.xml.template mapred-site.xml
  vi mapred-site.xml

  <configuration>
          <property>
                  <name>mapred.job.tracker</name>
                  <value>master:9001</value>
          </property>
          <property>
                  <name>mapreduce.framework.name</name>
                  <value>yarn</value>
          </property>
  </configuration>
  ```

  ![image](https://user-images.githubusercontent.com/79209568/125195196-9d2b6680-e28f-11eb-85ea-d18f9bf0243f.png)

### yarn-site.xml
- 얀 환경 설정 파일
- 맵리듀스 매니저 관련 설정이다.
  ```
  vi yarn-site.xml
  
  <configuration>
          <property>
                  <name>yarn.nodemanager.aux-services</name>
                  <value>mapreduce_shuffle</value>
          </property>
          <property>
                  <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
                  <value>org.apache.hadoop.mapred.ShuffleHandler</value>
          </property>
          <property>
                  <name>yarn.resourcemanager.hostname</name>
                  <value>192.168.202.128</value>
          </property>
  </configuration>
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125195287-f85d5900-e28f-11eb-8094-96e37b00d3be.png)

## 다른노드 설정 파일 동기화
- master에서 수정해준 설정 파일들을 다른 노드와의 동기화를 시켜준다.
### slave에서 폴더 생성
- slave1, slave2에서 관리자 모드로 jdk, hadoop 폴더를 생성 후 소유권을 hadoop으로 변경해준다.
  
  ```
  cd /usr/local

  mkdir jdk1.8.0_291
  mkdir hadoop-2.10.1

  chown -R hadoop:hadoop jdk1.8.0_291/
  chown -R hadoop:hadoop hadoop-2.10.1/
  ```
### java, hadoop 설치 파일 복사
- master에 있는 모든 java 설치 파일을 slave1, slave2에 생성한 jdk1.8.0_291 폴더로 복사해준다. backup에도 복사
  
  ```
  cd /usr/local/jdk1.8.0_291

  scp -r . hadoop@slave1:/usr/local/jdk1.8.0_291/
  scp -r . hadoop@slave2:/usr/local/jdk1.8.0_291/
  scp -r . hadoop@backup:/usr/local/jdk1.8.0_291/
  ```
- master에 있는 모든 hadoop 설치 파일을 slave1, slave2에 생성한 hadoop-2.10.1 폴더로 복사해준다. backup에도 복사
  
  ```
  cd /usr/local/hadoop-2.10.1

  scp -r . hadoop@slave1:/usr/local/hadoop-2.10.1/
  scp -r . hadoop@slave2:/usr/local/hadoop-2.10.1/
  scp -r . hadoop@backup:/usr/local/hadoop-2.10.1/
  ```
### .bash_profile 파일 복사
- master의 `.bash_profile` 파일을 slave1, slave2, backup으로 복사해준다.
  
  ```
  scp .bash_profile hadoop@slave1:~/
  scp .bash_profile hadoop@slave2:~/
  scp .bash_profile hadoop@backup:~/
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125195685-b1706300-e291-11eb-8d55-1cb2325b62cb.png)

### slave에서 설정 적용
- slave1, slave2에서 사용자 계정으로 .bash_profile을 적용한다.
  
  ```
  source .bash_profile
  ```
  
  ![image](https://user-images.githubusercontent.com/79209568/125195730-e67cb580-e291-11eb-8002-e733462b8bc4.png)

## 네임노드 초기화
### 초기화 진행
- master에서 사용자 계정으로 bin으로 이동 후 네임노드를 초기화해준다.
  ```
  cd /usr/local/hadoop-2.8.5/bin
  hdfs namenode - format
  ```
### 하둡 프로세스 실행
- sbin으로 이동 후 데몬 프로세스를 실행해준다.
```
cd /usr/local/hadoop-2.8.5/sbin
./start-dfs.sh
./start-yarn.sh

```

![image](https://user-images.githubusercontent.com/79209568/125195861-9eaa5e00-e292-11eb-9cbb-e80de59978a2.png)

### jps 조회
- jps는 Java Virtual Machine Process Status Tool의 약자. jvm 위에서 돌아가는 프로세스를 확인하는 툴이다.
- master에서 jps를 실행
  
  ![image](https://user-images.githubusercontent.com/79209568/125195934-e03b0900-e292-11eb-8ea0-3cce1d2abe4e.png)
  
- slave에서 jps를 실행
  
  ![image](https://user-images.githubusercontent.com/79209568/125195949-ecbf6180-e292-11eb-824f-3fbff916a877.png)
- 브라우저에서도 확인 : `master:50070`
  - Live Nodes가 2 이상이면 잘 된 것이다.
    
    ![image](https://user-images.githubusercontent.com/79209568/125195974-09f43000-e293-11eb-8af5-7c4368ec27d6.png)

  - Live Nodes를 클릭하면 slave1, slave2 노드가 실행 중인 것을 확인할 수 있다.
    
    ![image](https://user-images.githubusercontent.com/79209568/125196001-209a8700-e293-11eb-93f0-d88226e996b7.png)

## 프로세스 종료
- master에서 종료 쉘을 실행
  
  ![image](https://user-images.githubusercontent.com/79209568/125196091-69ead680-e293-11eb-8273-78ff89b842b8.png)

