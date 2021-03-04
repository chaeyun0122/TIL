# git start

## Done
### 1. shell command
#### 리스트 출력
- **ls** : 전체 리스트
- **ls -a** : 숨긴 파일까지 출력
- **ls -l** : 자세히
- **ls -al** : 합쳐서 사용 가능
#### 디렉토리
- **mkdir {디렉토리명}** : 생성
- **rm {디렉토리명}** : (디렉토리 내에 파일 없을 시 가능)삭제
- **rm -r {디렉토리명} **: (디렉토리 내에 파일까지) 삭제

#### 파일
- **touch {파일명}** : 생성
- **mv {파일명} { 디렉토리명}** : 디렉토리로 파일 옮기기
- **mv {파일명} {새로운 파일명}** : 파일 명 변경(새로운 파일 명에 디렉토리 추가 시 그 디렉토리에 생성됨) 
- **cp {파일명} [{디렉토리}]** : 복사
- **rm {파일명}** : 삭제
- **cat {파일명}** : 내용 확인

### 2. vim command
#### vim 열기
- **vi {파일명}**

#### vim 모드
- **I** : insert 모드로 진입
- **Esc** : normal 모드로 돌아오기

#### vim command (노말모드에서)
- 한 줄 복사 : shift + y
- 붙여넣기 : p
- 한 줄 지우기 : dd

#### 저장
- **:q**	나가기
- **:q!**	강제로 나가기
- **:w**	저장
- **:wq**	(저장하고)나가기

### 3. Markdown
- 주석 : `<!-- 주석 표시 -->`
- 제목 : `#` 개수(~6 개까지)
- 순서없는 리스트 : `- 내용` (탭으로 들여쓰기)
- 순서있는 리스트 : `1. 내용`
- 하이퍼링크 : `[링크텍스트](링크 URL)`
- 이미지 : `![대체텍스트](이미지 URL)`

### 4. What is git?
분산형 형상관리 도구

### 5. Why is need it?
- 빠른속도, 단순한 구조
- 분산형 저장소 지원
- 비선형적 개발(수천개의 브랜치) 가능

### 6. How to use git?
![image](https://user-images.githubusercontent.com/79209568/109812060-eda21000-7c6e-11eb-81dc-5d641b021a92.png)
1. working directory -> staging area 	: **git add**
2. staging area -> localrepo 		: **git commit**
3. localrepo -> remote repo 		: **git push**

#### Start project with git init
1. git 환경설정
 ```shell
 $ git config --global user.name "깃헙유저네임"
 $ git config --global user.email "깃헙가입이메일"
 $ git config --global core.editor "vim"
 $ git config --global core.paper "cat"
 ```
2. Run in shell
 ```shell
 $ mkdir my-first-repo	<!--새 디렉토리 생성-->
 $ cd my-first-repo	<!--새 디렉토리로 이동-->
 $ git init
 $ git remote add origin https://github.com/{username}/{reponame}.git	<!-- origin : 별명 -->
 $ touch README.md	<!--깃에 올릴 파일 생성-->
 $ git add README.md	<!--working directory 에서 staging area로 올라감-->
 $ git commit		<!--staging area에서 local repo로 올라감-->
 $ git push origin main	<!--local repo에서 remote repo로 올라감-->
 ```

#### Start project with git clone
1. Copy address of repository from github
2. Run in shell
 ```shell
 $ git clone {repo address}
 $ touch README.md	<!--깃에 올릴 파일 생성-->
 $ git add README.md
 $ git commit
 $ git push origin main
 ```

### 7. Create my Github page
1. Static site Generator to use : **Hexo**
2. install Hexo
 ```shell
 $ npm install -g hexo-cli
 ```
3. init Hexo
 ```shell
 $ hexo init hexo-blog	<!--새로운 디렉토리 생성됨-->
 $ npm install
 ```
4. create new post
 ```shell
 $ hexo new post "My first post"	<!--포스트 명-->
 $ vi source/_posts/My-first-post.md	<!--vim에서 포스트 작성-->
 ```
5. clean & generate static files
 ```shell
 $ hexo clean && hexo generate
 ```
6. Run hexo server (in localhost)
 ```shell
 $ hexo server
 ```
7. deploy
 ```shell
 $ npm install hexo-deployer-git --save
 $ vi _config.yml
 ```
 ```python
 deploy:
   type: git
   repo: https://github.com/{username}/{username}.github.io.git
   branch: main
 ```
 ![image](https://user-images.githubusercontent.com/79209568/109818462-593bab80-7c76-11eb-8f6c-7f162b47d503.png)

8. clean & deploy
 ```shell
 $ hexo clean && hexo deploy
 ```
9. Done! go to '{username}.git.io'




## TODO
1. 블로그 테마 상세 설정
2. TIL을 블로그에 업로드해보기

