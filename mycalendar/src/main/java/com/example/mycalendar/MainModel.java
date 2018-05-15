package com.example.mycalendar;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.base.BaseListener;

/**
 * Created by Administrator on 2018/4/1 0001.
 */

class MainModel implements IMainModel {

    @Override
    public void test(@NonNull BaseListener<Object, Object> listener) {
        Log.e("========","调用了model层实例");
        listener.onSucceed(new Object());
    }
}
