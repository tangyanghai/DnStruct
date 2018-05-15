package com.example.mycalendar;

import android.util.Log;
import android.view.View;

import com.example.base.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView{

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }



    @Override
    public void testSucceed() {
        Log.e("========","调用了view层实例");
        toash("测试成功");
    }

    public void onTestClicked(View view) {
        mPresenter.test();
    }
}
