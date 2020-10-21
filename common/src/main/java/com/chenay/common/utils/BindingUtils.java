package com.chenay.common.utils;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chenay.common.desgin.recycleView.OnItemClickListener;
//import com.facebook.drawee.view.SimpleDraweeView;


/**
 * @author Y.Chen5
 */
public class BindingUtils {
private static final String TAG = "BindingUtils";
//    @BindingAdapter("image")
//    public static void loadImage(SimpleDraweeView image, String uri) {
//        if (image != null) {
//            image.setImageURI(uri);
//        }
//    }
//
//    @BindingAdapter("image")
//    public static void loadImage(SimpleDraweeView image, int id) {
//        if (image != null) {
//            image.setImageResource(id);
//        }
//    }

    @BindingAdapter("onNavigationItemSelectedListener")
    public static void setOnNavigationItemSelectedListener(
            BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }

    @BindingAdapter("viewPagerAdapter")
    public static void setViewPagerAdapter(ViewPager view, PagerAdapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("recycleAdapter")
    public static void setRecycleAdatper(RecyclerView view, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        view.setAdapter(adapter);
    }


    @BindingAdapter({"recycleItemListener", "itemValue"})
    public static void setRecycleItemListener(View view, OnItemClickListener listener, Object itemValue) {
        if (listener == null) {
            Log.e(TAG, "setRecycleItemListener: this OnItemClickListener is null");
        } else {
            view.setOnClickListener(v -> listener.onItemClick(v, itemValue));
        }

        view.setOnLongClickListener(v -> {
            listener.onItemLongClick(v, itemValue);
            return true;
        });
    }

    @BindingAdapter("ico")
    public static void setImage(ImageView imageView, String ico) {
        final String packageName = imageView.getContext().getPackageName();
        int tempId = imageView.getResources().getIdentifier(ico, "drawable", packageName);
        imageView.setImageResource(tempId);
    }


    @BindingAdapter("selectCheck")
    public static void setCheckBox(CheckBox checkBox, boolean isCheck) {
        checkBox.setChecked(isCheck);
    }




}
