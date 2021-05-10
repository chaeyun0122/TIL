## 실습
### 1. 로고 이미지를 클릭 시 해당 페이지로 이동하도록 한다.
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>실습</title>
</head>
<body>
    <a href="https://www.naver.com/" target="_blank"><img width="500px" src="./logo/naver.png"></a><br>
    <a href="https://www.daum.net/" target="_blank"><img width="500px" src="./logo/daum.png"></a><br>
    <a href="https://www.google.com/" target="_blank"><img width="500px" src="./logo/google.png"></a><br>
</body>
</html>
```
> * 이미지 클릭  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/117608909-f3016680-b199-11eb-88ea-de38490648a1.png)
> * 해당 페이지로 이동  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/117608934-fdbbfb80-b199-11eb-8121-562749058bd5.png)

### 2. 테이블에 있는 영화 이미지 클릭 시 네이버 영화에서 그 영화의 설명사이트를 요청하도록한다.
```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Movie</title>
</head>
<body>
    <a name="top"></a>
    <h1>Mable Cinematic Movie</h1>
    <a href="#bottom">맨 밑</a><br><br>

    <table border="1" padding="80%">
        <tr>
            <th colspan="2" align="center">캡틴 아메리카</th>
        </tr>
        <tr>
            <td align="center">퍼스트 어벤져</td>
            <td>
                <a href="https://movie.naver.com/movie/bi/mi/basic.nhn?code=76348" target="_blank">
                    <img width="400px" src="./captin/firstavanger.jpg">
                </a>
            </td>
        </tr>
        <tr>
            <td align="center">윈터 솔져</td>
            <td>
                <a href="https://movie.naver.com/movie/bi/mi/basic.nhn?code=96327" target="_blank">
                    <img width="400px" src="./captin/wintersoldier.jpg">
                </a>
            </td>
        </tr>
        <tr>
            <td align="center">시빌 워</td>
            <td>
                <a href="https://movie.naver.com/movie/bi/mi/basic.nhn?code=122527" target="_blank">
                    <img width="400px" src="./captin/civilwar.jpg">
                </a>
            </td>
        </tr>
    </table>

    <br><br>
    <a href="#top" name="bottom">맨 위</a>
</body>
</html>
```
> * 이미지 클릭 시 해당 페이지로 이동
> * `맨 밑`클릭 시 페이지 가장 아래로 이동, '맨 위'를 클릭 시 가장 위로 이동  
>   
>   ![image](https://user-images.githubusercontent.com/79209568/117611724-fa773e80-b19e-11eb-9461-d1a9da650f7a.png)
>   ![image](https://user-images.githubusercontent.com/79209568/117611779-0f53d200-b19f-11eb-9dd4-8c82215df1e8.png)


