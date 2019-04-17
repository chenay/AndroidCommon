package com.chenay.common.desgin.recycleView;

import android.view.View;

public interface OnItemClickListener<T extends Object> {
    void onItemClick(View view, T itemValue);
    void onItemLongClick(View view, T itemValue);
}
