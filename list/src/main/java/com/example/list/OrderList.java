package com.example.list;

/**
 * Created by Administrator on 2018/2/9 0009.
 * 顺序表
 * 1.一个萝卜一个坑
 * 2.元素之间的关系就是角标的顺序
 */

public class OrderList <E> {

    /**
     * 容器的大小,默认为10
     * 在容器将要超标时,自动扩容
     */
    int containerSize;

    /**
     * 列表元素数组
     * 数组的length和{@link #containerSize 相同}
     */
    E[] elementData;

    /**
     * 列表元素个数
     */
    int size;
}
