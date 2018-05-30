package com.example.myapplication.rxjava;

/**
 * 观察者
 * 老鸨
 */
public abstract class Observer<T> {
    public abstract void onNext(T t);
    public abstract void onError();
    public abstract void onCompelet();
}
