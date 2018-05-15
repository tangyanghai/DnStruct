package com.example.list;

/**
 * 2017/11/3日课后作业
 * 2017/11/5日完成
 * 手写双链表
 * 实现增删改查
 */

public class DoubleLinkeList<E> {

    Node<E> first;
    Node<E> last;
    int size;

    public DoubleLinkeList() {
    }

    /**
     * 末尾添加一个元素
     *
     * @param e
     */
    public void add(E e) {
        Node<E> node = new Node<>(e, last, null);
        //如果第一项为空  则添加成成第一项
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
    }

    /**
     * 在指定位置添加一个元素
     */
    public void add(E e, int index) {
        if (index < 0 || index > size) {
            return;
        }

        //添加位置等于size,就是在队尾添加
        if (index == size) {
            add(e);
            return;
        }

        //在队首添加
        if (index == 0) {
            Node<E> node = new Node<>(e, null, first);
            first.pre = node;
            first = node;
            size++;
            return;
        }

        //在队列中间添加
        Node<E> currentNode = getNode(index);
        Node<E> currentPre = currentNode.pre;
        Node<E> node = new Node<>(e, currentPre, currentNode);
        currentPre.next = node;
        currentNode.pre = node;
        size++;
    }


    /**
     * 删除指定位置元素
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            return;
        }

        Node<E> node = getNode(index);
        Node<E> pre = node.pre;
        Node<E> next = node.next;
        if (pre!=null) {
            pre.next = next;
        }else {
            first = next;
        }

        if (next !=null) {
            next.pre = pre;
        }else {
            last = pre;
        }

        size--;
    }

    /**
     * 更改指定位置的元素
     */
    public void update(E e,int index){
        if (index<0&&index>=size){
            return;
        }
        getNode(index).item = e;
    }

    /**
     * 获取指定位置元素
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getNode(index).item;
    }

    /**
     * 获取指定位置 node
     */
    private Node<E> getNode(int index) {
        Node<E> node = null;
        if (index > size >> 1) {
            //从后往前
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.pre;
            }
        } else {
            //从前往后
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }


    static class Node<E> {
        E item;
        Node<E> pre;
        Node<E> next;

        public Node(E item, Node<E> pre, Node<E> next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }
}
