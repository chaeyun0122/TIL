# Kaggle API
## Colab에서 Kaggle API 사용하기
### 새로운 API 토큰 생성
#### 내 아이콘 → Account
![1](https://user-images.githubusercontent.com/79209568/112648109-8271e500-8e8c-11eb-8910-224bfb265d9f.gif)

#### API 메뉴의 'Create New API Token' 클릭
![image](https://user-images.githubusercontent.com/79209568/112648366-cd8bf800-8e8c-11eb-8335-7bc4de7922b8.png)

#### kaggle.json 파일 저장 후 열기
![image](https://user-images.githubusercontent.com/79209568/112648755-29ef1780-8e8d-11eb-87a7-5edca9c9f6cf.png)

### 내 토큰 Colab에 세팅하기
#### Colab 새 노트에서 해당 코드 작성
```python
import os
```
```python
os.environ['KAGGLE_USERNAME'] = 'chyun2'
os.environ['KAGGLE_KEY'] = 'key value'
```

### 데이터셋 다운로드 및 압축 해제하기
#### 데이터 선택 후 오른쪽 상단에 ••• 클릭 → Copy API command
![image](https://user-images.githubusercontent.com/79209568/112650104-6707d980-8e8e-11eb-9d22-bf875233c636.png)

#### Colab에 붙여넣기 후 실행
![image](https://user-images.githubusercontent.com/79209568/112650444-bbab5480-8e8e-11eb-9be6-2215be4e4503.png)
