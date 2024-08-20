package com.chenay.common.desgin.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 添加item监听器
 *
 * @author Y.Chen5
 * @date 3/28/2018
 */

public abstract class ChyBaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private OnItemClickListener<VH> mOnItemClickListener;


    @Override
    public  void onBindViewHolder(final VH holder, int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder,holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder,holder.getAdapterPosition());
                    return true;
                }
            });
        }
        baseOnBindViewHolder(holder,position);
    }

    protected abstract void baseOnBindViewHolder(VH holder, int position);


    public interface OnItemClickListener<VH extends RecyclerView.ViewHolder> {

        void onClick(VH holder, int adapterPosition);

        void onLongClick(VH holder, int adapterPosition);
    }

    public void setOnItemClickListener(OnItemClickListener<VH> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
