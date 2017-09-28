package com.dk.basepack.bseaapplication.util;

import android.content.Context;
import android.view.Gravity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.dk.basepack.bseaapplication.R;

/**
 * Created by Administrator on 2017/9/21.
 *
 * BottomNavigationBar  设置辅助类
 *
 * 使用参考：http://www.jianshu.com/p/134d7847a01e
 */

public class BottomNavigationBarHandler {

    private final BottomNavigationBar bottomNavigationBar;
    private AddBottomNavigationBarItemLisnter barItemLisnter;
    private  int  SHAPE_BADGE_ITEM_SIZE=7;//小点点的直径  大小

    public BottomNavigationBarHandler(BottomNavigationBar bottomNavigationBar) {
        this.bottomNavigationBar=bottomNavigationBar;
    }


    /**
     *
     * @param selected_icon   选中 图片icon 资源
     * @param inactive_icon   未选中 图片 icon 资源
     * @param title      tab  title
     * @return   BottomNavigationItem
     */
    public BottomNavigationItem addBottomNavigationItem(int selected_icon, int inactive_icon , String  title)
    {
        BottomNavigationItem  item=  new BottomNavigationItem(selected_icon, title).setInactiveIconResource(inactive_icon);
        bottomNavigationBar.addItem(item);
        return  item;
    }


    /**
     *
     * -     in-active color:
     *
     *       表示未选中Item中的图标和文本颜色。默认为 Color.LTGRAY
     *
     * -     active color :
     *
     *       在BACKGROUND_STYLE_STATIC下，表示选中Item的图标和文本颜色。而在BACKGROUND_STYLE_RIPPLE下，
     *       表示整个容器的背景色。默认Theme's Primary Color
     *
     * -     active color :
     *
     *       在BACKGROUND_STYLE_STATIC下，表示选中Item的图标和文本颜色。而在BACKGROUND_STYLE_RIPPLE下，
     *       表示整个容器的背景色。默认Theme's Primary Color
     *
     * -     background color :
     *
     *       在BACKGROUND_STYLE_STATIC下，表示整个容器的背景色。
     *       而在BACKGROUND_STYLE_RIPPLE下，表示选中Item的图标和文本颜色。默认 Color.WHITE
     *
     *
     *
     *       因为我xml中 设置的是 mode_fixed，BACKGROUND_STYLE_RIPPLE ，填充模式，带水波纹
     *       所以setBarStyle（）  这个方法具有样式是 ，bottomNavigationBar  白色背景，默认未选中文字颜色
     *       灰色，选中文字颜色浅蓝色。
     */

    public   void  setBarStyle()
    {
        bottomNavigationBar
                .setActiveColor(R.color.white)
                .setInActiveColor(R.color.in_bottombar_active_tabcolor)
                .setBarBackgroundColor(R.color.bottombar_active_tabcolor);//所有的设置需在调用该方法前完成;
    }


    /**
     *  在常见看法中底部导航栏会有消息提示 ，bottomNavigationBar 支持带数字提示
     *  这里我们定义默认样式， 白色边界，填充色红色点点，文本颜色 白色，显示动画2000， 选中这个tab不消失消息提示点点
     * @return
     */
    public TextBadgeItem getTextBadgeItem()
    {
      return new TextBadgeItem()
                .setBorderWidth(0)//Badge的Border(边界)宽度
                .setBorderColor("#ffffff")//Badge的Border颜色
                .setBackgroundColor("#ff0000")//Badge背景颜色 ,填充颜色
                .setGravity(Gravity.RIGHT| Gravity.TOP)//位置，默认右上角
                .setTextColor("#ffffff")//文本颜色
                .setAnimationDuration(2000)
                .setHideOnSelect(false);//当选中状态时消失，非选中状态显示
    }


    /**
     * 上一个定义了带数字的红色消息 提示点点，在实际开发中我们还有另外一种需求，就是不带数字消息提示点点。
     * @param context
     * @return
     */
    public ShapeBadgeItem getShapeBadgeItem(Context  context)
    {
        return  new ShapeBadgeItem().setShape(ShapeBadgeItem.SHAPE_OVAL)//画的类型，原型，支持多种类型
                .setShapeColorResource(R.color.red)//画点点的背景颜色
                .setSizeInDp(context,SHAPE_BADGE_ITEM_SIZE,SHAPE_BADGE_ITEM_SIZE)//大小，高宽
                .setEdgeMarginInDp(context,5)//icon跟小点的距离大小
                .setGravity(Gravity.TOP | Gravity.END)//位置
                .setHideOnSelect(false);//点击不消失
    }


    /**
     * 在所有ui设置完成之后调用，如果之前调用，后面ui设置就显示不了
     */
    public  void  initialise()
    {
        bottomNavigationBar.initialise();
    }

    /**
     * 获取底部导航栏实例
     * @return
     */
    public BottomNavigationBar getBottomNavigationBar() {
        return bottomNavigationBar;
    }

    public   interface   AddBottomNavigationBarItemLisnter{
        void  addItems(BottomNavigationBar bar);
    }


    /**
     *   设置  BottomNavigationBar   添加tab
     * @param barItemLisnter
     */
    public   void  setOnAddBottomNavigationBarItems(AddBottomNavigationBarItemLisnter  barItemLisnter){
        this.barItemLisnter=barItemLisnter;
        if (barItemLisnter!=null)
        {
            barItemLisnter.addItems(bottomNavigationBar);
        }
    }
}
