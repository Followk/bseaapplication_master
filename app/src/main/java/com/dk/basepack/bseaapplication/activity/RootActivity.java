package com.dk.basepack.bseaapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dk.basepack.bseaapplication.R;
import com.dk.basepack.bseaapplication.weight.CustomizedToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/27.
 */

public class RootActivity extends AppCompatActivity {
    public LayoutInflater inflater;
    public Activity mContext;
    public int replaceLayout;


    @BindView(R.id.customized_toolbar)
    CustomizedToolbar toolbar;
    @BindView(R.id.navigation_bar_ll)
    LinearLayout navigationBarLl;

    private long mLastTime;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mUnBinder = ButterKnife.bind(this);

        //获取填充器
        inflater = LayoutInflater.from(this);
        //上下文
        this.mContext = this;
        //替换布局
        replaceLayout = R.id.fragment_container;


        baseFindViewById();
        initToobar();
        setToolbarLisenter();
    }


    /**
     * 初始化基础布局
     */
    private void baseFindViewById() {

    }


    /**
     * 填充toobar，固定写法
     */
    protected void initToobar() {
        setSupportActionBar(toolbar);
    }


    /**
     * 设置toolbar 左边的点击事件，如果需要有其他逻辑，重写就可以了
     */
    public void setToolbarLisenter() {
        toolbar.setOnToolbarClickLeftLayoutLisenter(new CustomizedToolbar.ToolbarClickLeftLayoutLisenter() {
            @Override
            public void onToolbarClickLeft() {
                finish();
            }
        });
    }

    /**
     * 是否第一次显示
     */
    private boolean isFirstResume = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstResume) {
            onFirstResume();
            isFirstResume = false;
        }
    }

    /**
     * 第一次显示activity的时候调用方法，只会调用一次
     */
    protected void onFirstResume() {
    }


    /**
     * 如果用户快速关闭了这个activity，那么为子类提供一个方法告知，终止一些没必要的操作
     *
     * @return
     */
    public boolean isOpenMore() {

        if (System.currentTimeMillis() - mLastTime <= 100) {
            mLastTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ButterKnife 解绑操作可选的
        if (mUnBinder != null) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
    }
}
