> ## 새 프로젝트
> 1. 프로젝트 명 : project10
> 2. 앱 추가 : board
> 3. admin 계정 생성 : admin/1234

# 게시판
## BoardModel 생성
```python
# board/models.py

from django.utils import timezone

class BoardModel(models.Model):
    title = models.CharField(max_length=50)
    writer = models.CharField(max_length=30)
    content = models.TextField()
    regdate = models.DateField(auto_now=timezone.now)
    readcount = models.IntegerField(default=0)

    def __str__(self):
        return '%s. %s(%d)' % (self.title, self.writer, self.readcount)

    def incrementReadCount(self):
        self.readcount += 1
        self.save()
```
* migrate하기 
  ```
  python manage.py makemigrations
  python manage.py migrate
  ```
* 관리자 계정에 등록
  ```
  # board/admin.py
  
  from board.models import BoardModel
  admin.site.register(BoardModel)
  ```
