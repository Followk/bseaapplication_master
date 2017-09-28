package com.dk.basepack.bseaapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/9/22.
 */

public abstract class BaseActivity extends RootActivity {


    public View navigationBarView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragments();
        initView(inflater,mContext);
        findViewById();
        bindListener();
        initPresenter();
        changeThemes();
    }



     /**等同于onCreate */
    public abstract void initView(LayoutInflater inflater, android.app.Activity mContext) ;

   /**事件触发*/
    protected abstract void bindListener();


    /**查找控件*/
    public  abstract void findViewById() ;


    /**
     * 初始化 切换Fragments，为底部导航切换做数据源准备
     */
    protected  void initFragments(){}


    /**MVP 中的  p层设置*/
    protected void initPresenter() {
    }

    /**更改主题*/
    public void changeThemes() {

    }



    /**
     * 初始化底部的导航栏布局
     * @param layout_id
     */
    public   void   setBottomNavigationBarLayout(int layout_id)
    {

        navigationBarLl.removeAllViews();
        navigationBarLl.setClipChildren(false);
        navigationBarView = inflater.inflate(layout_id,navigationBarLl,false);

        navigationBarView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        navigationBarLl.addView(navigationBarView);
    }
}
