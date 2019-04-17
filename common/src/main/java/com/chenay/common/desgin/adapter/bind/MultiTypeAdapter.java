package com.chenay.common.desgin.adapter.bind;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chenay.common.mvvm.MultiTypeListItemViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MultiTypeAdapter<T extends MultiTypeListItemViewModel>
        extends RecyclerView.Adapter<MultiTypeAdapter.CustomViewHolder> {

    private List<T> mData = new ArrayList<>();
    private SparseIntArray mLayoutMapping;

    public void setData(List<T> data, SparseIntArray layoutMapping) {
        if (data != null) {
            this.mData = data;
            this.mLayoutMapping = layoutMapping;
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> data) {
        if (data != null) {
            int ps = mData.size();
            mData.addAll(data);
            notifyItemRangeInserted(ps, data.size());
        }
    }

    /**
     * 设置布局文件和类型的对应关系
     *
     * @param layoutMapping 布局文件和类型的对应关系
     */
    public void setLayoutMapping(SparseIntArray layoutMapping) {
        this.mLayoutMapping = layoutMapping;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).mType;
    }

    @NotNull
    @Override
    public MultiTypeAdapter.CustomViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int type) {
        if (mLayoutMapping != null) {
            int layoutId = mLayoutMapping.get(type);
            if (layoutId != 0) {
                ViewDataBinding binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()), layoutId, parent, false);
                return new CustomViewHolder(binding);
            } else {
                throw new IllegalArgumentException("LayoutId is 0.");
            }
        } else {
            throw new IllegalArgumentException("LayoutMapping is null.");
        }
    }

    @Override
    public void onBindViewHolder(MultiTypeAdapter.CustomViewHolder holder, int position) {
        T itemInfo = mData.get(position);
        holder.mBinding.setVariable(itemInfo.variableId(), itemInfo);
        holder.mBinding.executePendingBindings();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding mBinding;

        public CustomViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}