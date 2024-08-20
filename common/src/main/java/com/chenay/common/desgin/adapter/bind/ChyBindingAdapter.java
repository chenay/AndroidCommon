package com.chenay.common.desgin.adapter.bind;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.chenay.common.desgin.recycleView.OnItemClickListener;

import java.util.List;

/**
 * @author Y.Chen5
 */
public class ChyBindingAdapter<T extends BindingAdapterItemEntity> extends RecyclerView.Adapter<BindingViewHolder> {
    /**
     * 单一布局时给layoutId赋值
     */
    private int layoutId;
    private List<T> itemList;
    protected OnItemClickListener itemClickListener;
    private ViewDataBinding dataBinding;

    public ChyBindingAdapter() {
    }

    public ChyBindingAdapter(int layoutId, List<T> itemList) {
        this.layoutId = layoutId;
        this.itemList = itemList;
    }

    public ChyBindingAdapter(List<T> itemList) {
        this.itemList = itemList;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    public List<T> getItemList() {
        return itemList;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (layoutId > 0) {
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), layoutId, viewGroup, false);
        } else {
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), i, viewGroup, false);
        }
        final BindingViewHolder viewHolder = new BindingViewHolder(dataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder viewHolder, int i) {
        viewHolder.bindData(itemList.get(i),i);
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getViewType();
    }



}
