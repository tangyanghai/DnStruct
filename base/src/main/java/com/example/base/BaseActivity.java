package com.example.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.base.BaseMVP.BasePresenter;
import com.example.base.BaseMVP.IBaseView;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator on 2018/4/1 0001.
 */

public abstract class BaseActivity<T extends BasePresenter> extends Activity implements IBaseView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        beforeSetContentView();
        setContentView(getLayoutId());
        initView();
        initDatas();
    }

    /**
     * 初始化presenter
     */
    private void initPresenter() {
        //拿到presenter类型
        Class<T> t = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        //开始初始化
        try {
            mPresenter = t.newInstance();
            mPresenter.setView(this);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initView();

    protected abstract void initDatas();

    protected void beforeSetContentView() {

    }

    protected abstract int getLayoutId();

    @Override
    public void toash(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
