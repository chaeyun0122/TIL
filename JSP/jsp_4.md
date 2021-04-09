# HTML 동작 2
## 테이블
### table 태그
* 표를 만드는 태그

### table 태그 사용
- 테이블을 만드는 순서는 먼저 테이블을 정의하고, 행(tr)을 지정한 다음에 그 행을 나누어서 셀(td)를 만든다.
- **tr** : row(행)을 만드는 태그
- **th** : 테이블 헤더를 정의한다. 가운데 정렬, 진하게 속성이 자동을 부여된다.
- **td** : col(열)을 만드는 태그

### 표 시멘틱 태그를 이용한 테이블 (필수는 아니고 권장사항)
- 표의 구조를 제목, 본문, 요약 부분으로 나눈다.
- 시각장애인도 화면 판독기를 사용해서 표의 구조를 쉽게 이해할 수 있다.
- **thead** : 표의 머릿말 부분의 그룹 태그
- **tbody** : 표의 본문 부분의 그룹 태그
- **tfoot** : 표의 꼬리말 부분의 그룹 태그

### 테이블 기본
```html
<table width="300" border="1" align="center">
  <caption> 테이블 </caption>
  <!-- 제목 영역  -->
  <thead>
    <tr>
      <th> No </th>
      <th> 포인트 </th>
    </tr>
  </thead>
  <!-- 본문 영역  -->
  <tbody align="center">
    <tr>
      <td> 1 </td>
      <td> 가입 축하 1000 </td>
    </tr>
    <tr>
      <td> 2 </td>
      <td> 획득 : 1000 </td>
    </tr>
  </tbody>
  <!-- 하단 영역 -->
  <tfoot>
    <tr>
      <th> 합계 </th>
      <th> 2000 </th>
    </tr>
  </tfoot>
</table>
```
> #### 결과화면
>   
> ![image](https://user-images.githubusercontent.com/79209568/114168774-ad1f6b80-996b-11eb-8826-b9198fe5ab1a.png)

### 테이블 병합
#### 셀 병합
* 테이블의 셀과 셀을 합칠 수 있다.
  * rowspan : 셀을 세로로 합칠 때 사용한다. 속성 값으로 병합하려는 행의 수를 지정한다.
  * colspan : 셀을 가로로 합칠 때 사용한다. 속성 값으로 병합하려는 열의 수를 지정한다.
  * rowspan 예시 : 2행을 셀 병합 시키면 아래의 3행과 3행1열이 오른쪽으로 밀린다.
    ```html
    <h1> 셀 병합 </h1>
     <br>
     <table border="1" width="300">
      <caption> 행 통합 테이블 </caption>
      <tr>
        <th> 1행 </th><th> 1행 1열 </th>
      </tr>
      <tr>
        <th rowspan="2"> 2행 </th><th> 2행 1열 </th>
      </tr>
      <tr>
        <th> 3행 </th><th> 3행 1열 </th>
      </tr>
     </table>
    ```
    > ![image](https://user-images.githubusercontent.com/79209568/114169874-1a7fcc00-996d-11eb-9b2b-82889a6127eb.png)  
    
   * 맞춰서 바꿔준다.
     ```html
     <h1> 셀 병합 </h1>
     <br>
     <table border="1" width="300">
      <caption> 행 통합 테이블 </caption>
      <tr>
        <th> 1행 </th><th> 1행 1열 </th>
      </tr>
      <tr>
        <th rowspan="2"> 2행 </th><th> 2행 1열 </th>
      </tr>
      <tr>
        <th> 2행 1열 </th>
      </tr>
     </table>
     ```
     > ![image](https://user-images.githubusercontent.com/79209568/114170925-93cbee80-996e-11eb-9ce1-0341cc9853ca.png)

   * colspan 예시
      ```html
      <table border="1" width="300">
      <caption> 열 통합 테이블 </caption>
      <tr>
        <th> 1열 </th><th colspan="2"> 2열 </th>
      </tr>
      <tr>
        <th> 2행 1열 </th><th> 2행 2열 </th><th> 2행 3열 </th>
      </tr>
      </table>
      ```
      > * colspan 하기 전  
      > ![image](https://user-images.githubusercontent.com/79209568/114170547-0b4d4e00-996e-11eb-9025-629d19d7392d.png)
      > * colspan 한 후  
      > ![image](https://user-images.githubusercontent.com/79209568/114170793-60895f80-996e-11eb-8672-9e3d65e02d26.png)

#### 테이블로 공지사항 보드 만들기
  ```html
    <h1> 공지 사항 </h1>
    <table border="1" width="100%">
      <thead>
        <tr>
          <th>번호</th> <th>제목</th> <th>작성자</th> <th>조회수</th> <th>작성일</th>
        </tr>
      </thead>
      <tbody align="center">
        <tr>
          <td width="50">3</td>
          <td width="400"><a href="#">글 제목</a></td>
          <td>관리자</td>
          <td>10</td>
          <td>2021-04-09</td>
        </tr>
        <tr>
          <td>2</td>
          <td><a href="#">글 제목</a></td>
          <td>관리자</td>
          <td>10</td>
          <td>2021-04-09</td>
        </tr>
        <tr>
          <td>1</td>
          <td><a href="#">글 제목</a></td>
          <td>관리자</td>
          <td>10</td>
          <td>2021-04-09</td>
        </tr>
      </tbody>
      <tfoot>
        <tr>
          <td colspan="5" align="center">
            <a href="#">1</a> &nbsp; <a href="#">2</a> &nbsp; <a href="#">3</a> &nbsp;
        </tr>
      </tfoot>
    </table>
  ```
  > ![image](https://user-images.githubusercontent.com/79209568/114172641-df7f9780-9970-11eb-93ad-8d7faaeb22bc.png)


