package com.example.base;

/**
 * Created by Administrator on 2018/4/1 0001.
 */

public interface BaseListener<T,F> {
    void onSucceed(T t);
    void onFailed(F f);
}
