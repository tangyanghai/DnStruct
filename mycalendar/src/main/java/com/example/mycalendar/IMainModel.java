package com.example.mycalendar;

import android.support.annotation.NonNull;

import com.example.base.BaseListener;
import com.example.base.BaseMVP.IBaseModel;

/**
 * Created by Administrator on 2018/4/1 0001.
 */

interface IMainModel extends IBaseModel{
    void test(@NonNull BaseListener<Object,Object> listener);
}
