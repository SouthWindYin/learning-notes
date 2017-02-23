# hashmap是怎样的原理

> http://blog.csdn.net/vking_wang/article/details/14166593

## 数组和链表
数组存储空间连续，占用内存严重，空间复杂度大。数组的二分查找时间复杂度小，为O(1)。数组的特点是：寻址容易，插入和删除难

链表存储空间不连续，占用内存宽松，空间复杂度小，时间复杂度大，为O(N)。链表的特点是：插入和删除容易，寻址困难

## hashmap的特点
hashmap结和了数组和链表的优点，既满足了寻址容易也满足了插入删除容易。hashmap有不同的实现方法，先介绍最常用的一种方法，**拉链法**

### 拉链法实现hashmap

#### “链表的数组”

初始化的hashmap是一个固定长度的数组（例如16），每个元素存储的是一个链表的头结点，每增加一个对象，在数组的某元素后新增一个结点。新增结点的算法是通过**hash(key)%len**，也就是key值的哈希值模数组的长度得到，如果值为12，则在12处指向新增加的结点。

#### Entry
*Entry*是hashmap中的那个线性数组，保存有hashmap的头结点。Entry的重要属性有*key,value,next*


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

    public V put(K key, V value) {
        if (key == null)
            return putForNullKey(value); //null总是放在数组的第一个链表中
        int hash = hash(key.hashCode());
        int i = indexFor(hash, table.length);
        //遍历链表
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            //如果key在链表中已存在，则替换为新value
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        addEntry(hash, key, value, i);
        return null;
    }
    void addEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K,V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<K,V>(hash, key, value, e); //参数e, 是Entry.next
        //如果size超过threshold，则扩充table大小。再散列
        if (size++ >= threshold)
                resize(2 * table.length);
    }

get

     public V get(Object key) {
        if (key == null)
            return getForNullKey();
        int hash = hash(key.hashCode());
        //先定位到数组元素，再遍历该元素处的链表
        for (Entry<K,V> e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
                return e.value;
        }
        return null;
    }

null key的存取

    private V putForNullKey(V value) {
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        addEntry(0, null, value, 0);
        return null;
    }
 
    private V getForNullKey() {
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null)
                return e.value;
        }
        return null;
    }

确定数组index

    /**
     * Returns index for hash code h.
     */
    static int indexFor(int h, int length) {
        return h & (length-1);
    }
    //按位取并，作用上相当于取模mod或者取余%。
    //这意味着数组下标相同，并不表示hashCode相同。

table初始大小

    public HashMap(int initialCapacity, float loadFactor) {
        .....
        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity << 1;
        this.loadFactor = loadFactor;
        threshold = (int)(capacity * loadFactor);
        table = new Entry[capacity];
        init();
    }

rehash过程

    /**
      * 当hash表容量超过默认容量时，必须调整table大小。
      * 当容量达到最大可能时，该方法就将容量调整到Integer.MAX_VALUE返回，
      * 这时需要创建一张新表，将原表映射到新表中
      */
    void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }

    /**
     * Transfers all entries from current table to newTable.
     */
    void transfer(Entry[] newTable) {
        Entry[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            Entry<K,V> e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    Entry<K,V> next = e.next;
                    //重新计算index
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

#### 解决hash冲突方法
1. 开放定址法
2. 再hash法
3. 链地址法
4. 建立公共溢出区

java中采用的是第3种链地址法