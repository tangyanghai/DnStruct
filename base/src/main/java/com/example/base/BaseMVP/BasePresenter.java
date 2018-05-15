package com.example.base.BaseMVP;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator on 2018/4/1 0001.
 * <b>
 * <p>说明:</p>
 * <p>1.MVP-P层的Base</p>
 * <p>2.对view层使用了弱引用,避免内存泄漏,</p>
 * <p>3.在view层中实现了自动初始化P层,不需要用户每次都去初始化P层</p>
 * <p>4.实现了自动初始化Model,不需要用户每次都去new Model</p>
 * </b>
 * <b>
 * <p>使用:</p>
 * <p>继承本类,传入相应泛型</p>
 * </b>
 * <b>
 * <p>注意:</p>
 * <p>要得到view层,需要调用{@link #getView()}方法,并对返回值判空后使用,
 * 为了避免内存泄漏,这点牺牲是值得的</p>
 * </b>
 *
 * @param <T>   View层接口
 * @param <M>   Model层接口
 * @param <Q>   Model层实现
 */

public abstract class BasePresenter<T extends IBaseView, M extends IBaseModel,Q extends M> {
    private WeakReference<T> mView;
    protected M mModel;

    /**
     * 初始化model层
     */
    private void initModel(){
        Class<Q> q = (Class <Q>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        try {
            mModel = q.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 由于view层使用的是弱引用,有可能会被回收,所以每次使用前都需要判空处理
     * @return view层
     */
    protected T getView() {
        T t = mView.get();
        return t;
    }

    /**
     * 设置view层,初始化model
     *
     * 注意:这个方法是给base用的,用户请勿调用
     * @param view view层
     */
    public void setView(T view) {
        this.mView = new WeakReference<T>(view);
        initModel();
    }
}

