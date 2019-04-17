package com.chenay.common.desgin.recycleView.stickyitem1;

import android.view.View;

public interface StickyView {
    /**
     * 是否是吸附view
     * @param view
     * @return
     */
    boolean isStickyView(View view);

    /**
     * 得到吸附view的itemType
     * @return
     */
    int getStickViewType();
}
