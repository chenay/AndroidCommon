package com.chenay.common.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chenay.common.mvp.view.IMvpBaseView;

import io.reactivex.disposables.CompositeDisposable;

/**
 *
 * @author Y.Chen5
 * @date 1/5/2018
 *
 * @description 所有Presenter的基类，并不强制实现这些方法，有需要在重写
 */

public class IMvpBasePresenterN<V extends IMvpBaseView> {
    private static final String TAG = "IMvpBasePresenterN";
    protected CompositeDisposable compositeDisposable;
    /**
     * V层View
     */
    private V mMvpVIew;

    /**
     * Presenter 被创建后调用
     *
     * @param savedState
     */
    public void onCreatePresenter(@Nullable Bundle savedState) {
        Log.e(TAG, "onCreatePresenter: =");
    }

    /**
     * 绑定view
     * @param view
     */
    public void onAttachMvpView(V view) {
        this.mMvpVIew = view;
        compositeDisposable = new CompositeDisposable();
        Log.e(TAG, "onAttachMvpView: " );
    }

    /**
     * 解除绑定View
     */
    public void onDetachMvpView(){
        mMvpVIew=null;
        compositeDisposable.dispose();
        compositeDisposable = null;
        Log.e(TAG, "onDetachMvpView: " );
    }

    /**
     * Presenter被销毁时调用
     */
    public void onDestroyPresenter() {
        Log.e(TAG, "onDestroyPresenter: " );
    }
    /**
     * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     * 时机相同
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState: ");
    }

    /**
     * 获取V层接口的view
     * @return  返回当前 {@link #mMvpVIew}
     */
    public V getMvpView() {
        return mMvpVIew;
    }
}
