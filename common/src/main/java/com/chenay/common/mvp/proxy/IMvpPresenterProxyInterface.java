package com.chenay.common.mvp.proxy;

import com.chenay.common.mvp.factory.IMvpPresenterFactory;
import com.chenay.common.mvp.presenter.IMvpBasePresenterN;
import com.chenay.common.mvp.view.IMvpBaseView;

/**
 * Created by Y.Chen5 on 1/5/2018.
 *
 * @description 代理接口
 */

public interface IMvpPresenterProxyInterface<V extends IMvpBaseView, P extends IMvpBasePresenterN<V>> {

    /**
     * 设置创建Presenter的工厂
     *
     * @param presenterFactory
     */
    void setPresenterFactory(IMvpPresenterFactory<V, P> presenterFactory);

    /**
     * 获取Presenter的工厂类
     *
     * @return 返回PresenterMvpFactory类型
     */
    IMvpPresenterFactory<V, P> getPresenterFactory();

    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     */
    P getMvpPresenter();
}
