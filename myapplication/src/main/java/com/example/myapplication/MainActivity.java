package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.rxjava.Observable;
import com.example.myapplication.rxjava.ObservableOnSubscribe;
import com.example.myapplication.rxjava.Observer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<? extends Fruit> list = new ArrayList<>();
        Fruit f = list.get(2);
        //list.add(new Fruit());
        //报异常-->? extends 只能取,不能存
        List<? super Fruit> list2 = new ArrayList<>();
        final Object object = list2.get(2);
        list2.add(new Fruit());
        list2.add(new Bannana());
        //? super-->只能存,不能取-->只能取出Object
        //又要写  又要读  用T
        Observable.create(new ObservableOnSubscribe<String>(){
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("---------->");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.e("onNext",s);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onCompelet() {

            }
        });

    }

    public void hehe(List<? super Fruit> dest,List<? extends Fruit> src){
        for (int i = 0; i <src.size(); i++) {
//            src.add(dest.get(i));
            dest.add(src.get(i));
        }
    }



    class Point<T> {
        T x;
        T y;



    }

    class Food{

    }
    class Fruit extends Food{

    }

    class Bannana extends Fruit{

    }



}
