package com.dk.basepack.bseaapplication.weight;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dk.basepack.bseaapplication.R;
import com.dk.basepack.bseaapplication.mode.FragmentBean;
import com.dk.basepack.bseaapplication.util.FragmentHandoverManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : 底部导航栏组件
 * Created by Administrator on 2017/9/14.
 */

public class CustomizedBottomNavigationBar extends LinearLayout implements RadioGroup.OnCheckedChangeListener {
    private final String tag = "CustomizedBottomNavigationBar";
    private Context context;
    private static List<FragmentBean> fragments;


    private RadioGroup radioGroup;


    //指定fragment 切换模式  替换模式
    public final static int CHANGE_FRAGMENT_REPLACE_MODE = 0;

    //低耗模式  show 或者 hidden 模式
    public final static int CHANGE_FRAGMENT_REPLACE_SHOW_HIDDN = 1;
    private int mode = -1;

    private DragBubbleView bubbleView_tab2;
    private int cruSelectId = 0;
    private FragmentHandoverManagement management;

    public CustomizedBottomNavigationBar(Context context) {
        this(context, null);
    }

    public CustomizedBottomNavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomizedBottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        //这样写就是把布局贴给了CustomizedBottomNavigationBar
        View view = View.inflate(context, R.layout.customized_bottom_navigation_bar, this);

        findViewById();
    }

    /**
     * 初始化控件
     */
    private void findViewById() {
        radioGroup = (RadioGroup) findViewById(R.id.bottomnavigationbar_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.bottomnavigationbar_radiogroup);
        bubbleView_tab2 = (DragBubbleView) findViewById(R.id.dragBubbleView_tab2);
        bubbleView_tab2.setIsMoveFalg(cruSelectId == R.id.bottomnavigationbar_tab2);
        bubbleView_tab2.setOnBubbleStateListener(bubbleStateListener_tab2);
    }


    DragBubbleView.OnBubbleStateListener bubbleStateListener_tab2 = new DragBubbleView.OnBubbleStateListener() {
        @Override
        public void onDrag() {
//            Log.e("---> ", "拖拽气泡");
        }

        @Override
        public void onMove() {
//            Log.e("---> ", "移动气泡");
        }

        @Override
        public void onRestore() {
//            Log.e("---> ", "气泡恢复原来位置");
        }

        @Override
        public void onDismiss() {
//            Log.e("---> ", "气泡消失");
            bubbleView_tab2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bubbleView_tab2.reCreate();
                }
            }, 700);

        }
    };

    /**
     * @param fragments      要跳转的fragment集合
     * @param manager        FragmentManager
     * @param replace_layout 替换布局
     */
    public void setChangedFragmentLists(List<FragmentBean> fragments, FragmentManager manager, int replace_layout) {

        management = new FragmentHandoverManagement(manager, replace_layout);

        management.setFragments(fragments);


        //设置默认选择
        seChecked(0);
    }

    /**
     * @param fragments 要跳转的fragment集合
     * @param manager   FragmentManager
     */
    public void setChangedFragmentLists(List<FragmentBean> fragments, FragmentManager manager) {
        setChangedFragmentLists(fragments, manager, 0);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //解决 onChecked  选中某一个item的时候 优选选中 item，选中过后 购物车，可拖拽圆点才可以拖动
        cruSelectId = checkedId;
        bubbleView_tab2.setIsMoveFalg(cruSelectId == R.id.bottomnavigationbar_tab2);

        if (mode == -1) {
            throw new NullPointerException("onCheckedChanged" + ":" + "切换 模式的缺省值 mode  等于  ---->" + mode + "无效");
        }
        if (fragments == null) return;

        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
        FragmentBean fragment = getFragmentBean((String) radioButton.getTag());
        Fragment fg = fragment.getFragment();
        if (fragment.getFragment() == null) {
            throw new NullPointerException(fragment.getFragmentTag() + ":" + "切换 fragment  等于  null ---->" + tag);
        }


        switch (mode) {
            case CHANGE_FRAGMENT_REPLACE_MODE:

                management.changedReplaceFragmentMode(fragment);

                break;
            case CHANGE_FRAGMENT_REPLACE_SHOW_HIDDN:
                management.changedFragmentHiddeORShowMode(fragment);
                break;
            default:
                break;
        }
    }


    /**
     * 获取当前将要切换fragment信息
     *
     * @param tag
     * @return
     */
    private FragmentBean getFragmentBean(String tag) {
        for (FragmentBean fragmentBean : fragments) {
            if (tag.equals(fragmentBean.getFragmentTag())) {
                return fragmentBean;
            }
        }
        return null;
    }


    /**
     * 是否要关闭掉第一次显示fragment的时候的 淡入淡出效果
     *
     * @param isShowFade 默认开启状态
     */
    public void firstFragmentShowFade(boolean isShowFade) {
        management.setShowFade(isShowFade);
    }


    /**
     * 选中那个
     *
     * @param index
     */
    public void seChecked(int index) {
        ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
        cruSelectId = ((RadioButton) radioGroup.getChildAt(index)).getId();
    }


    /**
     * fragment 的切换模式，目前有两种
     * 替换模式  or    show  hidden 模式
     * <p>
     * 必须在  setChangedFragmentLists（）  之前调用
     *
     * @param mode
     */
    public void setFragmentChangeMode(int mode) {
        this.mode = mode;
    }


}
