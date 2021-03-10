# 링크드 리스트 (Linked List)

## 링크드 리스트(Linked List) 구조
* **연결 리스트**라고도함
* 배열은 순차적으로 연결된 공간에 데이터를 나열하는 데이터 구조
* 링크드 리스트는 떨어진 곳에 존재하는 데이터를 화살표로 연결해서 관리하는 데이터 구조
* 본래 C언어에서는 주요한 데이터 구조이지만, **파이썬은 리스트 타입이 링크드 리스트의 기능을 모두 지원**
* <span style="color:blue">코테에 간단하게 구현하라는 식으로 나올 수 있으니 구현 코드를 잘 익혀두자!</span>

## 용어
* 노드(Node) : 데이터 저장 단위 (데이터값, 포인터)로 구성
* 포인터(Pointer) : 각 노드 안에서, 다음이나 이전의 노드와의 연결 정보를 가지고 있는 공간
* 일반적인 링크드 리스트 형태
  <img src="https://www.fun-coding.org/00_Images/linkedlist.png" />

## 링크드 리스트의 장단점 (전통적인 C언어에서의 배열과 링크드 리스트)
* 장점
  - 미리 데이터 공간을 할당하지 않아도 됨 (배열은 미리 데이터공간을 할당해야함)
* 단점
  - 저장공간 효율이 높지 않음 -> 연결을 위한 별도 데이터 공간이 필요하기 때문에
  - 접근 속도가 느림 -> 연결 정보를 찾는 시간이 필요하기 때문
  - 중간 데이터 삭제 시, 앞뒤 데이터의 연결을 재구성해야하는 부가적인 작업 

## 간단한 링크드 리스트 예
### Node 구현
* 보통 Python에서 링크드 리스트 구현 시, 클래스를 활용함
* -> 클래스를 사용해서 구현하는 이유 : 데이터 두 가지(데이터, 주소)로 이루어져 있는 노드를 저장할 수 있는 구조를 만들려면 클래스가 수월

```python
class Node:
  def __init__(self, data, next=None):  # 주소의 디폴트값을 None으로 넣는다
    self.data = data
    self.next = next
```

### Node와 Node 연결하기 (포인터 활용)
```python
node1 = Node(1)  # 데이터가 1이고 포인터주소는 None인 노드
node2 = Node(2)  # 데이터가 2이고 포인터주손는 None인 노드
node1.next = node2  # node1의 포인터주소를 node2로 
head = node1     # 가장 앞에 있는 값은 node1
```

### 데이터 추가하기
- add 함수를 생성
```python
class Node:
  def __init__(self, data, next=None):
    self.data = data
    self.next = next
    
def add(data):
  node = head  # 가장 앞의 노드부터 시작
  while node.next:  # 맨 끝의 노드를 찾아가는 while문
    node = node.next
  node.next = Node(data)  # 가장 끝 노드의 포인터주소를 새로운 노드로 지정
```
```python
# 링크드 리스트에 데이터 추가해보기
node1 = Node(1)
head = node1
for index in range(2,10):  # 1만 있는 노드에 2~9까지 링크드 리스트로 추가
  add(index)
```

### 링크드 리스트 데이터 출력
```python
node = head  # node변수에 head값 node1을 할당
while node.next:
  print(node.data)
  node = node.next
print(node.data)  # 마지막 노드까지 출력
```
**1**  
**2**  
**3**  
**4**  
**5**  
**6**  
**7**  
**8**  
**9**  



## 복잡한 링크드 리스트 1 : 링크드 리스트 데이터 사이에 데이터를 추가
- 링크드 리스트는 유지 관리에 부가적인 구현이 필요함
<img src="https://www.fun-coding.org/00_Images/linkedlistadd.png" />


```python
# 1과 2사이에 추가 할 노드
node3 = Node(1.5)

node = head
search = True
while search:
  if node.data == 1:  # node.data가 추가할 노드의 앞 노드인 1인 경우 while문을 빠져나감
    search = False
  else:               # 1이 아닌경우 다음 노드로 넘어간다
    node = node.next

# 현재 node값이 1인 상태로 while문을 빠져나옴 (node.data=1, node.next=2)     
node_next = node.next  # node_next라는 변수에 현재 node의 포인터주소인 2를 넣음
node.next = node3      # node의 포인터가 node3을 가리키도록 함
node3.next = node_next # node3의 포인터가 2를 가리키도록 함
```
```python
# 1과 2 사이에 잘 들어갔는지 확인
node = head
while node.next:
  print(node.data)
  node = node.next
print(node.data)
```
**1**  
**1.5**  
**2**  
**3**  
**4**  
**5**  
**6**  
**7**  
**8**  
**9**  

## 파이썬 객체지향 프로그래밍으로 링크드 리스트 구현하기 (현재까지의 기능들 전체 통합)
```python
class Node:  # 노드 생성 클래스
  def __init__(self, data, next=None):
    self.data = data
    self.next = next
    
class NodeMgmt:  # 링크드 리스트 관리 클래스
  def __init__(self, data):  # 맨 앞의 노드 값을 헤드로 설정
    self.head = Node(data)
    
  def add(self, data):  # 링크드 리스트 맨 끝에 노드를 추가하는 함수
    if self.head == '':
      self.head = Node(data)
    else:
      node = self.head
      while node.next:
        node = node.next
      node.next = Node(data)
      
  def desc(self):  # 해당 링크드 리스트를 전체 출력하는 함수
    node = self.head
    while node:
      print(node.data)
      node = node.next
     
```
```python
# 노드 생성과 확인
linkedlist1 = NodeMgmt(0)
linkedlist1.desc()
```
**0**  


```python
# 노드 추가와 확인
for data in range(1,10):
  linkedlist1.add(data)
linkedlist.desc()
```
**0**  
**1**  
**2**  
**3**  
**4**  
**5**  
**6**  
**7**  
**8**  
**9**  


## 복잡한 링크드 리스트 2 : 특정 노드를 삭제
* 1번째 케이스 : head 삭제
* 2번째 케이스 : 마지막 노드 삭제
* 3번째 케이스 : 중간 노드 삭제  
  
* 다음 코드는 위의 코드에서 delete 메서드만 추가한 것이므로 해당 메서드만 확인하면 됨
```python
class Node:
    def __init__(self, data, next=None):
        self.data = data
        self.next = next
    
class NodeMgmt:
    def __init__(self, data):
        self.head = Node(data)
        
    def add(self, data):
        if self.head == '':
            self.head = Node(data)
        else:
            node = self.head
            while node.next:
                node = node.next
            node.next = Node(data)
        
    def desc(self):
        node = self.head
        while node:
            print (node.data)
            node = node.next
    
    def delete(self, data):  # 노드 삭제 함수
      if self.head == '':  # 링크드리스트에 값이 없음
        print("No list")
        return
      
      if self.head.data == data:  # 맨 앞의 노드를 삭제
        temp = self.head
        self.head = self.head.next
        del temp
      else:  # 중간이나 맨 끝의 노드를 삭제
        node = self.head
        while node.next:
          if node.next.data == data:
            temp = node.next
            noed.next = node.next.next
            del temp
            return
          else:
            node = node.next
```

- 테스트 해보기
```python
# 노드 하나 만들기
linkedlist1 = NodeMgmt(0)

# 노드 추가
for data in range(1,10):
  linkedlist1.add(data)
  
# 노드 중 하나 삭제 (케이스 3: 중간값 삭제)
linkedlist1.delete(4)   # 0, 1, 2, 3, 5, 6, 7, 8, 9



## 다양한 링크드 리스트 구조
* 더블 링크드 리스트(Double Linked List) 기본 구조
  - 이중 연결 리스트라고도 함
  - 장점 : 양방향으로 연결되어 있어서 노드 탐색이 양쪽으로 모두 가능 (맨 앞에서부터 탐색해야하는 기존 링크드 리스트의 단점 보완) 
  
  <img src="https://www.fun-coding.org/00_Images/doublelinkedlist.png" />
  
  
```python
class Node:
  def __init__(self, data, prev=None, next=None):  # 기존의 링크드 리스트에서 prev 변수가 생김
    self.data = data
    self.prev = prev
    self.next = next
    
 class NodeMgmt:
   def __init__(self, data):
     self.head = Node(data)
     self.tail = self.head  # 처음에 노드가 하나 생성되면 head나 tail이나 같다
     
   def insert(self, data):
     if self.head == None:
       self.head = None(data)
       self.tail = self.head
     else:
       node = self.head
       while node.next:  # 맨 끝 노드를 찾아가서
         node = nede.next
       new = Node(data)  # 일단 새로운 노드를 만들고
       node.next = new   # 맨 끝 노드의 포인터를 new로
       new.prev = node   # new의 prev 포인터를 원래의 맨 끝 노드로
       self.tail = new   # 맨 끝 노드가 new가 됐으므로 tail을 new로
  
  def desc(self):  # 모든 노드 출력 함수
    node = self.head
    while node:
      print(node.data)
      node = node.next
      
  def search_from_head(self, data):  # 앞에서부터 찾는 함수
        if self.head == None:
            return False
    
        node = self.head
        while node:
            if node.data == data:
                return node
            else:
                node = node.next
        return False
    
    def search_from_tail(self, data):  # 끝에서부터 찾는 함수
        if self.head == None:
            return False
    
        node = self.tail
        while node:
            if node.data == data:
                return node
            else:
                node = node.prev
        return False
    
    def insert_before(self, data, before_data):  # 특정 숫자인 노드 앞에 데이터를 추가하는 함수
        if self.head == None:
            self.head = Node(data)
            return True
        else:
            node = self.tail
            while node.data != before_data:
                node = node.prev
                if node == None:
                    return False
            new = Node(data)  # 특정 노드를 찾아야지만 실행되는 코드들
            before_new = node.prev
            before_new.next = new
            new.prev = before_new
            new.next = node
            node.prev = new
            return True
            
    def insert_after(self, data, after_data):  # 특정 숫자인 노드 뒤에 데이터를 추가하는 함수
        if self.head == None:
            self.head = Node(data)
            return True            
        else:
            node = self.head
            while node.data != after_data:
                node = node.next
                if node == None:
                    return False
            new = Node(data)
            after_new = node.next
            new.next = after_new
            new.prev = node
            node.next = new
            if new.next == None:
                self.tail = new
            return True
```

