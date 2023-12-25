package collections;
/**
 * 定义一个哈希表类，包含一个节点数组，一个大小和一个阈值。
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class MyHashMap<K, V> {

    /**
     * 节点数组，存储键值对的链表。
     */
    Node<K, V>[] table;

    /**
     * 哈希表的大小。
     */
    int size;

    /**
     * 扩容阈值，当大小超过阈值时，会扩容哈希表。
     */
    int threshold;

    /**
     * 哈希表的初始容量。
     */
    static final int INITIAL_CAPACITY = 16;

    /**
     * 负载因子，用于计算扩容阈值。
     */
    static final float LOAD_FACTOR = 0.75f;

    /**
     * 节点类，包含键，值，哈希码和下一个节点的引用。
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     */
    private static class Node<K, V> {
        /**
         * 键。
         */
        final K key;

        /**
         * 值。
         */
        V value;

        /**
         * 哈希码。
         */
        final int hash;

        /**
         * 下一个节点的引用。
         */
        Node<K, V> next;

        /**
         * 构造方法，初始化键，值，哈希码和下一个节点。
         *
         * @param key   键
         * @param value 值
         * @param hash  哈希码
         * @param next  下一个节点
         */
        Node(K key, V value, int hash, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }

    /**
     * 构造方法，初始化节点数组，大小和阈值。
     */
    public MyHashMap() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
        threshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
    }

    /**
     * 计算一个对象的哈希码，使用了Java官方的方法。
     *
     * @param key 键
     * @return 哈希码
     */
    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 根据哈希码和数组长度计算一个对象在数组中的索引。
     * 这个公式的好处是，它可以保证索引的范围在0到数组长度减一之间，而且数组的长度必须是2的幂，
     * 这样可以使得哈希码的每一位都能参与到索引的计算中，从而均匀地分布在数组中，减少冲突的概率，
     * 提高哈希表的性能。
     *
     * @param hash   哈希码
     * @param length 数组长度
     * @return 索引
     */
    static int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    /**
     * 根据键获取对应的值，如果键不存在，返回null。
     *
     * @param key 键
     * @return 对应的值，如果键不存在，返回null
     */
    public V get(Object key) {
        // 计算键的哈希码
        int hash = hash(key);
        // 计算键在数组中的索引
        int index = indexFor(hash, table.length);
        // 遍历索引位置的链表，找到键相等的节点，返回其值
        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                return e.value;
            }
        }
        // 如果没有找到键相等的节点，返回null
        return null;
    }

    /**
     * 向哈希表中添加一个键值对，如果键已经存在，更新其值。
     *
     * @param key   键
     * @param value 值
     */
    public void put(K key, V value) {
        // 计算键的哈希码
        int hash = hash(key);
        // 计算键在数组中的索引
        int index = indexFor(hash, table.length);
        // 遍历索引位置的链表，找到键相等的节点，更新其值
        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                e.value = value;
                return;
            }
        }
        // 如果没有找到键相等的节点，创建一个新的节点，插入到链表的头部
        Node<K, V> newNode = new Node<>(key, value, hash, table[index]);
        table[index] = newNode;
        // 增加哈希表的大小
        size++;
        // 如果大小超过了阈值，就扩容哈希表
        if (size > threshold) {
            resize(2 * table.length);
        }
    }

    /**
     * 扩容哈希表，重新分配每个节点的位置。
     *
     * @param newCapacity 新的容量
     */
    void resize(int newCapacity) {
        // 创建一个新的数组，长度为新的容量
        Node<K, V>[] newTable = new Node[newCapacity];
        // 把旧的数组中的每个节点转移到新的数组中
        transfer(newTable);
        // 把新的数组赋值给旧的数组
        table = newTable;
        // 更新阈值为新的容量乘以负载因子
        threshold = (int) (newCapacity * LOAD_FACTOR);
    }

    /**
     * 把当前哈希表的数组中的每个节点转移到另一个数组中。
     *
     * @param newTable 新的数组
     */
    void transfer(Node<K, V>[] newTable) {
        // 遍历旧的数组中的每个位置
        for (int i = 0; i < table.length; i++) {
            // 获取当前位置的第一个节点
            Node<K, V> e = table[i];
            // 如果节点不为空，就进行转移
            if (e != null) {
                // 把当前位置设为null，方便垃圾回收
                table[i] = null;
                // 遍历当前位置的链表，把每个节点转移到新的数组中
                do {
                    // 获取下一个节点
                    Node<K, V> next = e.next;
                    // 根据节点的哈希码和新的数组长度，计算节点在新的数组中的索引
                    int index = indexFor(e.hash, newTable.length);
                    // 把节点插入到新的数组的索引位置的链表的头部
                    e.next = newTable[index];
                    newTable[index] = e;
                    // 继续处理下一个节点
                    e = next;
                } while (e != null);
            }
        }
    }

    /**
     * 从哈希表中删除一个键，返回其对应的值，如果键不存在，返回null。
     *
     * @param key 键
     * @return 对应的值，如果键不存在，返回null
     */
    public V remove(Object key) {
        // 计算键的哈希码
        int hash = hash(key);
        // 计算键在数组中的索引
        int index = indexFor(hash, table.length);
        // 遍历索引位置的链表，找到键相等的节点，把它从链表中断开，返回其值
        Node<K, V> prev = table[index];
        Node<K, V> e = prev;
        while (e != null) {
            Node<K, V> next = e.next;
            Object k;
            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                size--;
                if (prev == e)
                    table[index] = next;
                else
                    prev.next = next;
                return e.value;
            }
            prev = e;
            e = next;
        }
        // 如果没有找到键相等的节点，返回null
        return null;
    }

    /**
     * 清空哈希表。
     */
    public void clear() {
        // 遍历数组中的每个位置，把它们设为null，方便垃圾回收
        for (int i = 0; i < table.length; i++)
            table[i] = null;
        // 把大小设为0
        size = 0;
    }

    /**
     * 返回哈希表的大小。
     *
     * @return 哈希表的大小
     */
    public int size() {
        return size;
    }

    /**
     * 判断哈希表是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断是否包含Key
     *
     * @param key 关键
     * @return boolean
     */
    public boolean containsKey(Object key) {
        // 计算键的哈希码
        int hash = hash(key);
        // 计算键在数组中的索引
        int index = indexFor(hash, table.length);
        // 遍历索引位置的链表，判断键相等的节点是否存在
        for (Node<K, V> kvNode = table[index]; kvNode != null; kvNode = kvNode.next) {
            Object k;
            if (kvNode.hash == hash && ((k = kvNode.key) == key || (key != null && key.equals(k)))) {
                return true; // 键相等的节点存在，返回true
            }
        }
        // 键相等的节点不存在，返回false
        return false;
    }

    /**
     * 合并两个不同的HashMap对象，返回合并后的新HashMap对象,
     * 如果两个HashMap中有相同的键，那么后一个HashMap中的键值对会覆盖前一个HashMap中的键值对
     *
     * @param map1 map1
     * @param map2 map2
     * @return {@link MyHashMap}<{@link K}, {@link V}>
     */
    public static <K, V> MyHashMap<K, V> merge(MyHashMap<K, V> map1, MyHashMap<K, V> map2) {
        // 创建一个新的HashMap对象
        MyHashMap<K, V> mergedMap = new MyHashMap<>();

        // 遍历第一个HashMap的每个节点，将其键值对添加到新的HashMap中
        for (Node<K, V> e : map1.table) {
            while (e != null) {
                mergedMap.put(e.key, e.value);
                e = e.next;
            }
        }

        // 遍历第二个HashMap的每个节点，将其键值对添加到新的HashMap中
        for (Node<K, V> e : map2.table) {
            while (e != null) {
                mergedMap.put(e.key, e.value);
                e = e.next;
            }
        }

        return mergedMap;
    }
}
