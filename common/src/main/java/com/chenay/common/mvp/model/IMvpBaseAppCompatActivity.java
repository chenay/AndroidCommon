package com.chenay.common.mvp.model;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chenay.common.base.BaseActivity;
import com.chenay.common.mvp.factory.IMvpPresenterFactory;
import com.chenay.common.mvp.factory.IMvpPresenterFactoryImpl;
import com.chenay.common.mvp.presenter.IMvpBasePresenterN;
import com.chenay.common.mvp.proxy.IMvpBaseProxy;
import com.chenay.common.mvp.proxy.IMvpPresenterProxyInterface;
import com.chenay.common.mvp.view.IMvpBaseView;

/**
 * Created by Y.Chen5 on 1/8/2018.
 */

@SuppressLint("LongLogTag")
public class IMvpBaseAppCompatActivity <V extends IMvpBaseView,P extends IMvpBasePresenterN<V>> extends BaseActivity implements IMvpPresenterProxyInterface {
    private static final String TAG = "IMvpBaseAppCompatActivity";
    public static final String KEY_SAVE_PRESENTER = "presenter_save_key";
    private IMvpBaseProxy<V, P> mProxy = new IMvpBaseProxy<>(IMvpPresenterFactoryImpl.<V,P>createFactory(getClass()));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.e(TAG, "onCreate: mProxy="+ mProxy );
        if (savedInstanceState!=null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(KEY_SAVE_PRESENTER));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
        mProxy.onResume((V) this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " + isChangingConfigurations());
        mProxy.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
        outState.putBundle(KEY_SAVE_PRESENTER, mProxy.onSaveInstanceState());
    }

    @Override
    public void setPresenterFactory(IMvpPresenterFactory presenterFactory) {
        Log.e(TAG, "setPresenterFactory: ");
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public IMvpPresenterFactory getPresenterFactory() {
        Log.e(TAG, "getPresenterFactory: " );
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        Log.e(TAG, "getMvpPresenter: ");
        return mProxy.getMvpPresenter();
    }
}
