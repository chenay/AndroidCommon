package com.chenay.common.desgin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChySimpleAdapter<T> extends RecyclerView.Adapter<ChySimpleAdapter.BaseViewHolder> {

    private List<T> itemDatas;
    private int defaultLayout;
    private int brId;

    public ChySimpleAdapter(List<T> itemDatas, int defaultLayout, int brId) {
        this.itemDatas = itemDatas;
        this.defaultLayout = defaultLayout;
        this.brId = brId;
    }

    public int getItemLayout(T itemData) {
        return defaultLayout;
    }

    public void addListener(View root, T itemData, int position) {

    }

    public void onItemDatasChanged(List<T> newItemDatas) {
        this.itemDatas = newItemDatas;
        notifyDataSetChanged();
    }

    protected void onItemRangeChanged(List<T> newItemDatas, int positionStart, int itemCount) {
        this.itemDatas = newItemDatas;
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onItemRangeInserted(List<T> newItemDatas, int positionStart, int itemCount) {
        this.itemDatas = newItemDatas;
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onItemRangeRemoved(List<T> newItemDatas, int positionStart, int itemCount) {
        this.itemDatas = newItemDatas;
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayout(itemDatas.get(position));
    }

    @NotNull
    @Override
    public ChySimpleAdapter.BaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull ChySimpleAdapter.BaseViewHolder holder, int position) {
        holder.binding.setVariable(brId, itemDatas.get(position));
        addListener(holder.binding.getRoot(), itemDatas.get(position), position);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return itemDatas == null ? 0 : itemDatas.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
