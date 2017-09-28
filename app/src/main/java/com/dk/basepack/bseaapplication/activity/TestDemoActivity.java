package com.dk.basepack.bseaapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.dk.basepack.bseaapplication.R;
import com.dk.basepack.bseaapplication.fragment.ClassifyFragment;
import com.dk.basepack.bseaapplication.fragment.HomePageFragment;
import com.dk.basepack.bseaapplication.fragment.InformationFragment;
import com.dk.basepack.bseaapplication.fragment.PersonalCenterFragment;
import com.dk.basepack.bseaapplication.fragment.ShoppingCartFragment;
import com.dk.basepack.bseaapplication.mode.FragmentBean;
import com.dk.basepack.bseaapplication.util.BottomNavigationBarHandler;
import com.dk.basepack.bseaapplication.util.FragmentHandoverManagement;
import com.dk.basepack.bseaapplication.weight.CustomizedToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class TestDemoActivity extends BaseActivity {


    private BottomNavigationBarHandler barHandler;
    private List<FragmentBean> fragments;
    private FragmentHandoverManagement management;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setBinMode(CustomizedToolbar.MODE_IMAGE_TEXT);
        toolbar.setToolbarTltleText("中间康城");
        toolbar.setToolbarLeftImge(getResources().getDrawable(R.mipmap.transparent_back));


    }

    @Override
    public void initView(LayoutInflater inflater, android.app.Activity mContext) {
        setBottomNavigationBarLayout(R.layout.nb_layout_bottom_navigation_bar);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        barHandler = new BottomNavigationBarHandler(bottomNavigationBar);

        barHandler.setOnAddBottomNavigationBarItems(new BottomNavigationBarHandler.AddBottomNavigationBarItemLisnter() {
            @Override
            public void addItems(BottomNavigationBar bar) {
                barHandler.addBottomNavigationItem(R.mipmap.tab_bar_logo0_ordinary, R.mipmap.tab_bar_logo0_ordinary, "步行");

                barHandler.addBottomNavigationItem(R.mipmap.tab_bar_logo1_ordinary, R.mipmap.tab_bar_logo1_ordinary, "骑行");


                TextBadgeItem badge = barHandler.getTextBadgeItem();//保存下，更新消息个数，操作显示隐藏，都是操作这个实例
                badge.setText("0");
                barHandler.addBottomNavigationItem(R.mipmap.tab_bar_logo2_ordinary, R.mipmap.tab_bar_logo2_ordinary, "公交").setBadgeItem(badge);

                ShapeBadgeItem badgeItem = barHandler.getShapeBadgeItem(bar.getContext());
                barHandler.addBottomNavigationItem(R.mipmap.tab_bar_logo3_ordinary, R.mipmap.tab_bar_logo3_ordinary, "自驾").setBadgeItem(badgeItem);

                barHandler.addBottomNavigationItem(R.mipmap.tab_bar_logo4_ordinary, R.mipmap.tab_bar_logo4_ordinary, "火车");
            }
        });

        barHandler.setBarStyle();
        barHandler.initialise();


        management = new FragmentHandoverManagement(getSupportFragmentManager(), replaceLayout);
        management.setFragments(fragments);
        bottomNavigationBar.setTabSelectedListener(tabSelectedListener);
        bottomNavigationBar.selectTab(0);//选中第几个tab






        //setBottomNavigationBarLayout(R.layout.cd_bottom_navigation_bar);




//        CustomizedBottomNavigationBar navigationBar= (CustomizedBottomNavigationBar) findViewById(R.id.navigation_bar);
//        //确定那种Fragment的切换模式
//        navigationBar.setFragmentChangeMode(CustomizedBottomNavigationBar.CHANGE_FRAGMENT_REPLACE_MODE);
//        //设置操作Fragment 数据集合
//        navigationBar.setChangedFragmentLists(fragments,getSupportFragmentManager(),R.id.fragment_container);
    }

   private BottomNavigationBar.OnTabSelectedListener  tabSelectedListener= new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {//当前选中的
            if (isOpenMore())  return;//用户操作立马关闭，终止fragment 开启事务操作

            management.changedFragmentHiddeORShowMode(fragments.get(position));
        }

        @Override
        public void onTabUnselected(int position) {//上一个选中tab
        }

        @Override
        public void onTabReselected(int position) {//重复选中
        }
    };


    @Override
    protected void bindListener() {

    }

    @Override
    public void findViewById() {

    }


    @Override
    protected void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new FragmentBean(new HomePageFragment(), "tab0"));
        fragments.add(new FragmentBean(new ClassifyFragment(), "tab1"));
        fragments.add(new FragmentBean(new ShoppingCartFragment(), "tab2"));
        fragments.add(new FragmentBean(new InformationFragment(), "tab3"));
        fragments.add(new FragmentBean(new PersonalCenterFragment(), "tab4"));
    }

    @Override
    protected void onFirstResume() {
        super.onFirstResume();
    }
}
