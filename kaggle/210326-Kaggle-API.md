# Kaggle API
## Colab에서 Kaggle API 사용하기
### 1. 새로운 API 토큰 생성
1. 내 아이콘 → Account
![1](https://user-images.githubusercontent.com/79209568/112648109-8271e500-8e8c-11eb-8910-224bfb265d9f.gif)
2. API 메뉴의 'Create New API Token' 클릭
![image](https://user-images.githubusercontent.com/79209568/112648366-cd8bf800-8e8c-11eb-8335-7bc4de7922b8.png)
3. kaggle.json 파일 저장 후 열기
![image](https://user-images.githubusercontent.com/79209568/112648755-29ef1780-8e8d-11eb-87a7-5edca9c9f6cf.png)
4. Colab 새 노트에서 해당 코드 작성
```python
import os
```
```python
os.environ['KAGGLE_USERNAME'] = 'chyun2'
os.environ['KAGGLE_KEY'] = 'key value'
```
