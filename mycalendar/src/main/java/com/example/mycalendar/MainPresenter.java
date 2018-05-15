package com.example.mycalendar;

import android.util.Log;

import com.example.base.BaseListener;
import com.example.base.BaseMVP.BasePresenter;

/**
 * Created by Administrator on 2018/4/1 0001.
 */

public class MainPresenter extends BasePresenter<IMainView,IMainModel,MainModel> {

    public void test(){
        Log.e("========","调用了p层方法");
        mModel.test(new BaseListener<Object, Object>() {
            @Override
            public void onSucceed(Object o) {
                Log.e("========","返回p层");
                IMainView view = getView();
                if (view!=null) {
                    view.testSucceed();
                }
            }

            @Override
            public void onFailed(Object o) {

            }
        });
    }
}
