package com.chenay.common.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * @author Y.Chen5
 */
public abstract class LazyLoadFragment extends Fragment {
    private static final String TAG = "LazyLoadFragment";
    /**
     * 界面是否已创建完成
     */
    private boolean isViewCreated;
    /**
     * 是否对用户可见
     */
    private boolean isVisibleToUser;
    /**
     * 数据是否已请求, isNeedReload()返回false的时起作用
     */
    private boolean isDataLoaded;
    /**
     * // 记录当前fragment的是否隐藏
     */
    private boolean isHidden = true;

    /**
     * 使用ViewPager嵌套fragment时，切换ViewPager回调该方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        Log.d(TAG, getClass().getSimpleName() + " setUserVisibleHint: " + isVisibleToUser);
        tryLoadData();
        tryRecycleData();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName() + " onActivityCreated: ");
        isViewCreated = true;
        tryLoadData();
    }

    /**
     * 使用show()、hide()控制fragment显示、隐藏时回调该方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        if (!hidden) {
            tryLoadData1();
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     *
     * @return
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof LazyLoadFragment && ((LazyLoadFragment) fragment).isVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof LazyLoadFragment && ((LazyLoadFragment) child).isVisibleToUser) {
                ((LazyLoadFragment) child).tryLoadData();
            }
        }
    }

    /**
     * fragment再次可见时，是否重新请求数据，默认为flase则只请求一次数据
     *
     * @return
     */
    protected boolean isNeedReload() {
        return false;
    }

    /**
     * ViewPager场景下，尝试请求数据
     */
    public void tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && (isNeedReload() || !isDataLoaded)) {
            onLoadData();
            isDataLoaded = true;
            dispatchParentVisibleState();
        }
    }

    public void tryRecycleData() {
        if (isViewCreated && !isVisibleToUser && isDataLoaded) {
            onRecycleData();
            isDataLoaded = false;
            dispatchParentHiddenState();
        }
    }


    /**
     * show()、hide()场景下，当前fragment没隐藏，如果其子fragment也没隐藏，则尝试让子fragment请求数据
     * 或者回收数据
     */
    private void dispatchParentHiddenState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof LazyLoadFragment && !((LazyLoadFragment) child).isHidden) {
                ((LazyLoadFragment) child).tryLoadData1();
            }
        }
    }

    /**
     * show()、hide()场景下，父fragment是否隐藏
     *
     * @return
     */
    private boolean isParentHidden() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return false;
        } else if (fragment instanceof LazyLoadFragment && !((LazyLoadFragment) fragment).isHidden) {
            return false;
        }
        return true;
    }

    /**
     * show()、hide()场景下，尝试请求数据
     */
    public void tryLoadData1() {
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded)) {
            onLoadData();
            isDataLoaded = true;
            dispatchParentHiddenState();
        }
    }

    @Override
    public void onDestroy() {
        isVisibleToUser = false;
        tryRecycleData();
        isViewCreated = false;
        isDataLoaded = false;
        isHidden = true;
        super.onDestroy();
        Log.d(TAG, getClass().getSimpleName() + " onDestroy: ");
    }

    /**
     * 加载数据
     *
     * @param
     */
    public void onLoadData() {
        Log.d(TAG, getClass().getSimpleName() + " onLoadData: ");
    }

    public void onRecycleData() {
        Log.d(TAG, getClass().getSimpleName() + " onRecycleData: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, getClass().getSimpleName() + " onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, getClass().getSimpleName() + " onResume: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, getClass().getSimpleName() + " onAttach: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, getClass().getSimpleName() + " onDestroyView: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, getClass().getSimpleName() + " onDetach: ");
    }
}
