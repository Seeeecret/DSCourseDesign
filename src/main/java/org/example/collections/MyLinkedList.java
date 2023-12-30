package org.example.collections;


import java.util.Iterator;

/**
 * 自己实现的一个链表类，包含头节点和一些基本操作
 *
 */
public class MyLinkedList<T> implements Iterable<T>{
    Node<T> head; // 头节点
    int size; // 链表的大小

    // 构造方法，初始化头节点和大小
    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * 实现迭代器接口
     *
     * @return {@link Iterator}<{@link T}>
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /**
     * 内部类实现迭代器
     *
     **/
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the list");
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }


    /**
     * 定义一个节点类，包含数据和下一个节点的引用
     *
     */
    private static class Node<T> {
        T data; // 数据域
        Node<T> next; // 指针域

        // 构造方法，初始化数据和下一个节点
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * 在链表的末尾添加一个新的节点
     *
     * @param data 数据
     */
    public void add(T data) {
        // 创建一个新的节点
        Node<T> newNode = new Node<>(data);
        // 如果链表为空，直接把新节点设为头节点
        if (head == null) {
            head = newNode;
        } else {
            // 否则，遍历链表，找到最后一个节点
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            // 把新节点链接到最后一个节点的后面
            current.next = newNode;
        }
        // 链表的大小加一
        size++;
    }

    /**
     * 在链表的指定位置插入一个新的节点
     *
     * @param index 指数
     * @param data  数据
     */
    public void add(int index, T data) {
        // 检查索引是否有效
        if (index < 0 || index > size) {
            System.out.println("Invalid index");
            return;
        }
        // 创建一个新的节点
        Node<T> newNode = new Node<>(data);
        // 如果索引为0，直接把新节点设为头节点
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            // 否则，遍历链表，找到索引位置的前一个节点
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            // 把新节点插入到前一个节点和后一个节点之间
            newNode.next = current.next;
            current.next = newNode;
        }
        // 链表的大小加一
        size++;
    }

    /**
     * 从链表中删除一个指定的节点
     *
     * @param data 数据
     */
    public void remove(T data) {
        // 如果链表为空，直接返回
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        // 如果头节点的数据等于要删除的数据，直接把头节点设为下一个节点
        if (head.data.equals(data)) {
            head = head.next;
            // 链表的大小减一
            size--;
            return;
        }
        // 否则，遍历链表，找到要删除的节点的前一个节点
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null && !current.data.equals(data)) {
            previous = current;
            current = current.next;
        }
        // 如果找到了要删除的节点，就把它从链表中断开
        if (current != null) {
            previous.next = current.next;
            // 链表的大小减一
            size--;
        } else {
            // 如果没有找到要删除的节点，就打印提示信息
            System.out.println("Data not found");
        }
    }

    /**
     * 清空链表
     */
    public void clear() {
        // 把头节点设为null，就相当于清空了链表
        head = null;
        // 链表的大小设为0
        size = 0;
    }

    /**
     * 判断链表是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        // 如果头节点为null，就说明链表为空
        return head == null;
    }

    /**
     * 返回链表的大小
     *
     * @return int
     */
    public int size() {
        return size;
    }

    /**
     *
     * 重写toString方法，用来打印链表的内容
     *
     * @return {@link String}
     */
    @Override
    public String toString() {
        // 创建一个字符串缓冲区，用来存储链表的内容
        StringBuilder sb = new StringBuilder();
        // 遍历链表，把每个节点的数据添加到字符串缓冲区中
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data).append(" -> ");
            current = current.next;
        }
        // 在最后添加一个null，表示链表的结束
        sb.append("null");
        // 返回字符串缓冲区的内容
        return sb.toString();
    }
}
