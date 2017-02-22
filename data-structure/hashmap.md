# hashmap是怎样的原理

> http://blog.csdn.net/vking_wang/article/details/14166593

## 数组和链表
数组存储空间连续，占用内存严重，空间复杂度大。数组的二分查找时间复杂度小，为O(1)。

**数组的特点是：寻址容易，插入和删除难**

链表存储空间不连续，占用内存宽松，空间复杂度小，时间复杂度大，为O(N)。
**链表的特点是：插入和删除容易，寻址困难**

## hashmap的特点
hashmap结和了数组和链表的优点，**既满足了寻址容易也满足了插入删除容易**。

hashmap有不同的实现方法，先介绍最常用的一种方法，**拉链法**

### 拉链法实现hashmap
拉链法可以理解为**“链表的数组”**

初始化的hashmap是一个固定长度的数组（例如16），每个元素存储的是一个链表的头结点，每增加一个对象，在数组的某元素后新增一个结点。

新增结点的算法是通过**hash(key)%len**，也就是key值的哈希值模数组的长度得到，如果值为12，则在12处指向新增加的结点。

#### Entry
*Entry*是hashmap中的那个线性数组，保存有hashmap的头结点。

Entry的重要属性有*key,value,next*


#### hashmap的存取实现
存储时

    int hash = key.hashCode();

    int index = hash % Entry[].length;

    Entry[index] = value;

取值时

    int hash = key.hashCode();

    int index = hash % Entry[].length;

    return Entry[index];

put

