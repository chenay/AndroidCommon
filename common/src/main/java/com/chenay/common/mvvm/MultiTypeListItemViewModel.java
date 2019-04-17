package com.chenay.common.mvvm;

/**
 * 多布局Recycler
 * @author Y.Chen5
 */
public abstract class MultiTypeListItemViewModel extends BaseViewModel {
    public int mType;

    public abstract int variableId();
}
