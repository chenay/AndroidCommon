package com.chenay.common.desgin.adapter.bind;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.chenay.common.BR;


/**
 * @author Y.Chen5
 */
public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T mBinding;

    public BindingViewHolder(@NonNull T binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    public void bindData(BindingAdapterItemEntity itemEntity, int index) {
        mBinding.setVariable(BR.itemEntity, itemEntity);
        mBinding.setVariable(BR.itemIndex, index);
    }
}
