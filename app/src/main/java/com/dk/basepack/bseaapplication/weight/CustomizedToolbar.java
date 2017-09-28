package com.dk.basepack.bseaapplication.weight;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dk.basepack.bseaapplication.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/9/25.
 */

public class CustomizedToolbar extends Toolbar implements View.OnClickListener {
    public static final int MODE_TEXT = 0;
    public static final int MODE_IMAGE = 1;
    public static final int MODE_TEXT_IMAGE = 2;
    public static final int MODE_IMAGE_TEXT = 3;
    public static final int MODE_CUSTOMIZE = 4;
    private final Context context;
    private RelativeLayout mToolbarLeftRl, mToolbarContextRl, mToolbarRightRl;
    private TextView mToolbarLeftTv, mToolbarTitleTv, mToolbarRightTv;


    private int binMode = MODE_IMAGE_TEXT;
    private ImageView mToolbarLeftIv, mToolbarRightIv;
    private String tb_text_left;
    private String tb_text_tltle;
    private String tb_text_right;
    private Drawable tb_icon_left;
    private Drawable tb_icon_right;
    private ColorStateList tb_textcolor_left;
    private ColorStateList tb_textcolor_tltle;
    private ColorStateList tb_textcolor_right;
    private ToolbarCustomizetModeLisenter customizetModeLisenter;




    @IntDef({MODE_TEXT, MODE_IMAGE, MODE_TEXT_IMAGE, MODE_IMAGE_TEXT, MODE_CUSTOMIZE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }


    public CustomizedToolbar(Context context) {
        this(context, null);
    }

    public CustomizedToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomizedToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context=context;


        /** 拿到配置样式说明*/
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomizedToolbar, defStyleAttr, 0);

        //文字设置
        tb_text_left = array.getString(R.styleable.CustomizedToolbar_tb_text_left);
        tb_text_tltle = array.getString(R.styleable.CustomizedToolbar_tb_text_tltle);
        tb_text_right = array.getString(R.styleable.CustomizedToolbar_tb_text_right);


        //图标设置
        tb_icon_left = array.getDrawable(R.styleable.CustomizedToolbar_tb_icon_left);
        tb_icon_right = array.getDrawable(R.styleable.CustomizedToolbar_tb_icon_right);


        //那种模式，目前支持自定义模式
        binMode = array.getInt(R.styleable.CustomizedToolbar_binCdMode, -1);


        //字体颜色设置
        tb_textcolor_left = array.getColorStateList(R.styleable.CustomizedToolbar_tb_text_color_left);
        tb_textcolor_tltle = array.getColorStateList(R.styleable.CustomizedToolbar_tb_text_color_tltle);
        tb_textcolor_right = array.getColorStateList(R.styleable.CustomizedToolbar_tb_text_color_right);
        array.recycle();//释放资源

        inflateView();


    }


    /**
     * 设置显示模式
     * @param binMode
     */
    public void setBinMode(int binMode) {
        this.binMode = binMode;
        inflateView();
    }


    public void setToolbarLeftTextColor(ColorStateList toolbarRightTextColor) {
        mToolbarLeftTv.setTextColor(toolbarRightTextColor!=null?toolbarRightTextColor:ColorStateList.valueOf(0xFF000000));
    }

    public void setToolbarRightTextColor(ColorStateList toolbarRightTextColor) {
        mToolbarRightTv.setTextColor(toolbarRightTextColor!=null?toolbarRightTextColor:ColorStateList.valueOf(0xFF000000));
    }

    public void setToolbarTltleTextColor(ColorStateList toolbarTltleTextColor) {
        mToolbarTitleTv.setTextColor(toolbarTltleTextColor!=null?toolbarTltleTextColor:ColorStateList.valueOf(0xFF000000));
    }

    public  void  inflateView()
    {
        if (binMode==-1) return;

        removeAllViews();
        /**
         * 填充一个布局，我不知道这样会不会降低，绘制效率
         */
        View  view=null;
        switch (binMode) {
            case MODE_TEXT://纯文字类型
                view = View.inflate(context, R.layout.customized_toolbar0, this);
                break;
            case MODE_IMAGE://纯图片
                view = View.inflate(context, R.layout.customized_toolbar1, this);
                break;
            case MODE_TEXT_IMAGE://左边文字，右边图片类型
                view = View.inflate(context, R.layout.customized_toolbar2, this);
                break;
            case MODE_IMAGE_TEXT://左边图片右边文字类型
                view = View.inflate(context, R.layout.customized_toolbar3, this);
                break;
            case MODE_CUSTOMIZE://自定义类型
                view = View.inflate(context, R.layout.customized_toolbar4, this);
                break;

        }
        //初始化控件
        initView();
    }

    private void initView() {


        mToolbarTitleTv = (TextView) findViewById(R.id.toolbar_title_tv);
        switch (binMode) {
            case MODE_TEXT://纯文字类型
                findRl();

                mToolbarLeftTv = (TextView) findViewById(R.id.toolbar_left_tv);
                mToolbarRightTv = (TextView) findViewById(R.id.toolbar_right_tv);

                setToolbarLeftText(tb_text_left);
                setToolbarRightText(tb_text_right);

                setToolbarLeftTextColor(tb_textcolor_left);
                setToolbarRightTextColor(tb_textcolor_right);

                break;
            case MODE_IMAGE://纯图片
                findRl();

                mToolbarLeftIv = (ImageView) findViewById(R.id.toolbar_left_iv);
                mToolbarRightIv = (ImageView) findViewById(R.id.toolbar_right_iv);

                setToolbarLeftImge(tb_icon_left);
                setToolbarRightImge(tb_icon_right);

                break;
            case MODE_TEXT_IMAGE://左边文字，右边图片类型
                findRl();

                mToolbarLeftTv = (TextView) findViewById(R.id.toolbar_left_tv);
                mToolbarRightIv = (ImageView) findViewById(R.id.toolbar_right_iv);

                setToolbarLeftText(tb_text_left);
                setToolbarRightImge(tb_icon_right);

                setToolbarLeftTextColor(tb_textcolor_left);
                break;
            case MODE_IMAGE_TEXT://左边图片右边文字类型
                findRl();

                mToolbarLeftIv = (ImageView) findViewById(R.id.toolbar_left_iv);
                mToolbarRightTv = (TextView) findViewById(R.id.toolbar_right_tv);
                setToolbarLeftImge(tb_icon_left);
                setToolbarRightText(tb_text_right);
                setToolbarRightTextColor(tb_textcolor_right);
                break;
            case MODE_CUSTOMIZE://自定义类型
                findRl();
                if (customizetModeLisenter!=null)
                {
                    customizetModeLisenter.onInitCustomizetView(mToolbarLeftRl,mToolbarContextRl,mToolbarRightRl);
                }
                break;

        }

        setToolbarTltleText(tb_text_tltle);
        setToolbarTltleTextColor(tb_textcolor_tltle);

    }

    public void setToolbarRightImge(Drawable tb_icon_right) {
        if (tb_icon_right != null) {
            mToolbarRightIv.setImageDrawable(tb_icon_right);
            mToolbarRightIv.setVisibility(VISIBLE);
        }else {
            mToolbarRightIv.setVisibility(GONE);
        }
    }

    public void setToolbarLeftImge(Drawable icon_left) {
        if (icon_left != null) {
            mToolbarLeftIv.setImageDrawable(icon_left);
            mToolbarLeftIv.setVisibility(VISIBLE);
        }else {
            mToolbarLeftIv.setVisibility(GONE);
        }
    }


    private void findRl() {
        mToolbarLeftRl = (RelativeLayout) findViewById(R.id.toolbar_left_rl);
        mToolbarContextRl = (RelativeLayout) findViewById(R.id.toolbar_context_rl);
        mToolbarRightRl = (RelativeLayout) findViewById(R.id.toolbar_right_rl);

        mToolbarLeftRl.setOnClickListener(this);
        mToolbarRightRl.setOnClickListener(this);
    }

    private ToolbarClickLeftLayoutLisenter leftLayoutLisenter;
    private ToolbarClickRightLayoutLisenter rightLayoutLisenter;

    @Override
    public void onClick(View v) {//点击事件，普通模式，支持左右点击事件，中间的点击事件，不支持，如果要做中间的，建议使用自定义模式
        if (binMode==MODE_CUSTOMIZE) return;

        switch (v.getId()) {
            case R.id.toolbar_left_rl:
                if (mToolbarLeftRl!=null)
                {
                   int  Visibility = mToolbarLeftRl.getChildAt(0).getVisibility();
                    if (Visibility==View.GONE) break;
                }

                if (leftLayoutLisenter!=null) leftLayoutLisenter.onToolbarClickLeft();
                break;
            case R.id.toolbar_right_rl:
                int  Visibility = mToolbarRightRl.getChildAt(0).getVisibility();
                if (Visibility==View.GONE) break;

                if (rightLayoutLisenter!=null) rightLayoutLisenter.onToolbarClickRight();
                break;
        }
    }


    public void setToolbarRightText(String tb_text_right) {
        if (!TextUtils.isEmpty(tb_text_right)) {
            mToolbarRightTv.setText(tb_text_right);
            mToolbarRightTv.setVisibility(VISIBLE);
        }else {
            mToolbarRightTv.setVisibility(GONE);
        }
    }

    public void setToolbarTltleText(String tb_text_tltle) {
        if (!TextUtils.isEmpty(tb_text_tltle)) {
            mToolbarTitleTv.setText(tb_text_tltle);
        }
    }

    public void setToolbarLeftText(String leftText) {
        if (!TextUtils.isEmpty(leftText)) {
            mToolbarLeftTv.setText(leftText);
            mToolbarLeftTv.setVisibility(VISIBLE);
        }else {
            mToolbarLeftTv.setVisibility(GONE);
        }

    }


/**
 * 除了自定义模式没有，左右中建的点击事件
 */
    /*******************************************************************************************************/
    public interface ToolbarClickLeftLayoutLisenter {//左边点击事件

        void onToolbarClickLeft();
    }


    public interface ToolbarClickRightLayoutLisenter {//右边点击事件

        void onToolbarClickRight();
    }

    public void setOnToolbarClickLeftLayoutLisenter(ToolbarClickLeftLayoutLisenter leftLayoutLisenter) {
        this.leftLayoutLisenter = leftLayoutLisenter;
    }

    public void setOnToolbarClickRightLayoutLisenter(ToolbarClickRightLayoutLisenter rightLayoutLisenter) {
        this.rightLayoutLisenter = rightLayoutLisenter;
    }

    /*******************************************************************************************************/


    public interface ToolbarCustomizetModeLisenter {//中建点击事件

        void onInitCustomizetView(ViewGroup leftLayout, ViewGroup contextLayout, RelativeLayout rightLayout);
    }


    public  void  setOnToolbarCustomizetModeLisenter(ToolbarCustomizetModeLisenter  customizetModeLisenter)
    {
        if (mToolbarContextRl!=null) mToolbarContextRl.removeAllViews();
        this.customizetModeLisenter=customizetModeLisenter;
    }
}
