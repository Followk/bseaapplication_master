package com.dk.basepack.bseaapplication.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dk.basepack.bseaapplication.mode.FragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 * <p>
 * fragment  的切换管理类，支持  hidden show   fragment 或者是 replace  fragment
 */

public class FragmentHandoverManagement {

    //第一次可见fragment  标记
    private List<String> firstVisibles = new ArrayList<>();
    private final int replace_layout;
    private FragmentManager fm;
    private boolean isShowFade = true;
    private FragmentTransaction transaction;
    private List<FragmentBean> fragments;


    public FragmentHandoverManagement(FragmentManager manager, int replace_layout) {
        this.replace_layout = replace_layout;
        this.fm = manager;
    }


    /**________________________________________________________________________________________*/

    /**
     * 切换fragment  替换模式 replace方法替换
     *
     * @param fragment
     */
    public void changedReplaceFragmentMode(FragmentBean fragment) {
        transaction = fm.beginTransaction();

        firstVisibleFragment(fragment);

        transaction.replace(replace_layout, fragment.getFragment());
        transaction.commit();

    }


    /**
     * 第一次显示fragment 的时候  显示淡入淡出效果
     *
     * @param fragment
     */
    private void firstVisibleFragment(FragmentBean fragment) {
        if (!isShowFade) return;

        if (!firstVisibles.contains(fragment.getFragmentTag())) {
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            firstVisibles.add(fragment.getFragmentTag());
        }
    }


    /**
     * ________________________________________________________________________________________
     */


    public void setShowFade(boolean showFade) {
        isShowFade = showFade;
    }


    /*************************************************************************************************/

    public void setFragments(List<FragmentBean> fragments) {
        this.fragments = fragments;
    }

    /**
     * show   或者 hidden  模式
     *
     * @param fragment
     */
    public void changedFragmentHiddeORShowMode(FragmentBean fragment) {

        Fragment currentFragment= fragment.getFragment();
        Bundle bundle=   fragment.getBundle();


        //每次要创建一个新的事务
        transaction = fm.beginTransaction();

        if (bundle != null) {
            currentFragment.setArguments(bundle);
        }

        //将每一个fragment添加到  事务中
        addFragments(currentFragment);

        //隐藏没有隐藏的fragment
        hideFragments(transaction);

        firstVisibleFragment(fragment);
        //show   fragment
        transaction.show(fragment.getFragment());

        //提交事务
        transaction.commitAllowingStateLoss();
    }


    /**
     * 循环隐藏所有的fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        for (FragmentBean fragmentBean : fragments) {
            if (fragmentBean != null && fragmentBean.getFragment() != null) {
                boolean ishidden = fragmentBean.getFragment().isHidden();

                if (!ishidden) transaction.hide(fragmentBean.getFragment());
            }
        }
    }


    /**
     *只添加一次当前的fragment 是否在事务中
     * @param fragment
     */
    private void addFragments(Fragment fragment) {
        if (!fragment.isAdded()) {
            fragment.setRetainInstance(true);
            transaction.add(replace_layout, fragment);
        }
    }
    /*************************************************************************************************/
}
