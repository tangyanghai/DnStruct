package com.example.list;

import com.example.list.DoubleLinkeList;
import com.example.list.SingleLinkedList;

/**
 * 测试
 */

public class TestClass {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //单链表
//        testSingleLinkedList();
        //双链表
        testDoubleLinkedList();

    }

    /**
     * 测试双链表
     */
    private static void testDoubleLinkedList() {
        DoubleLinkeList<Integer> list = new DoubleLinkeList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);
//        list.add(555,3);
//        list.remove(3);
        list.update(555,10);
        for (int i = 0; i < list.size; i++) {
            int a = list.get(i);
            System.out.print(i + ":"+a+"  ");
        }
    }

    /**
     * 测试单链表
     */
    private static void testSingleLinkedList() {
        //单链表测试
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        list.add(1);
        list.add(3);
        list.add(4);
//        list.add(10,0);
//        list.add(13,3);
        //删除
//        list.remove(4);
        //倒置
//        list.invert();
        //更改
        list.update(50,2);
        for (int i = 0; i < list.size; i++) {
            System.out.print(i + ":"+list.get(i)+"  ");
        }
    }
}
