package com.chenay.common.utils;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

public class ListenerUtils {

    public static ObservableList.OnListChangedCallback addListChangedListener(RecyclerView.Adapter adapter) {
        return new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                if (itemCount == 1) {
                    adapter.notifyItemMoved(fromPosition, toPosition);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                    adapter.notifyDataSetChanged();
            }
        };
    }
}
