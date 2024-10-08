package com.chenay.common.mvp.factory;


import androidx.annotation.Keep;

import com.chenay.common.mvp.presenter.IMvpBasePresenterN;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 * Created by Y.Chen5 on 1/5/2018.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Keep
public @interface IMvpCreatePresenter {
    Class<? extends IMvpBasePresenterN> value();
}
