package com.example.myapplication.rxjava;

/**
 * 被观察着
 * 发出事件
 */
public class Observable<T> {
    private ObservableOnSubscribe onSubscribe;

    public Observable(ObservableOnSubscribe onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public Observable() {

    }

    /**
     * 创建被观察者
     * 保存发射器
     */
    public static <T> Observable<T> create(ObservableOnSubscribe<T> onSubscribe){
        return new Observable<T>(onSubscribe);
    }

    /**
     * 订阅观察者
     */
    public void subscribe(Observer<? super T> observer){
        onSubscribe.subscribe(observer);
    }
}
