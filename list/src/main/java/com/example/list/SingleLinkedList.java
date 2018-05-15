package com.example.list;

/**
 * 2017/11/3日课后作业
 * 2017/11/5日完成
 * 手写单链表
 * 实现增删改查
 */
public class SingleLinkedList<E> {
    Node<E> first;
    int size;

    /**
     * 队尾增加一个元素
     */
    public void add(E e) {
        if (e == null) {
            return;
        }
        Node<E> p = new Node(e, null);
        if (size != 0) {
            getNode(size - 1).next = p;
        } else {
            first = p;
        }
        size++;
    }

    /**
     * 在指定位置添加元素
     *
     * @param e
     * @param index
     */
    public void add(E e, int index) {
        if (index < 0 || index > size) {
            System.out.println("越界了");
            return;
        }

        //要添加到index的node
        Node<E> node = new Node<>(e, null);
        //拿到当前index的node
        Node<E> currentNode = getNode(index);
        node.next = currentNode;
        if (index == 0) {
            //要添加在首位
            first = node;
        } else {
            //不是添加在首位,就要拿到index-1位置上的node
            Node<E> pre = getNode(index - 1);
            pre.next = node;
        }

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
        //如果是首位
        if (index == 0) {
            first = node.next;
        } else {
            //如果不是首位  获取其前一位
            Node<E> pre = getNode(index-1);
            pre.next = node.next;
        }

        size --;
    }

    /**
     * 更改指定位置的元素
     * @param e
     * @param index
     */
    public void update(E e,int index){
        if (index<0||index>=size){
            return;
        }

        Node<E> node = getNode(index);
        node.item = e;
    }

    /**
     * 获取指定位置的  元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index > size) {
            System.out.println("越界了");
            return null;
        }
        return getNode(index).item;
    }

    /**
     * 倒置列表
     */
    public void invert(){
        if (size == 0||size == 1){
            return;
        }
        SingleLinkedList<E> invertList = new SingleLinkedList<>();
        Node<E> node = first;
        invertList.add(node.item,0);
        for (int i = 1; i < size; i++) {
            node = node.next;
            invertList.add(node.item,0);
        }
        first = invertList.first;
    }


    /**
     * 根据index  获取 {@code Node<E>}
     */
    private Node<E> getNode(int index) {
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    public static class Node<E> {
        E item;
        Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

}
