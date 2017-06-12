package com.example.wheel.loopview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wheel.R;

import java.util.ArrayList;

/**
 * 年月日滚轮效果的视图
 */
public class DataWheelView extends LinearLayout {

    private LoopView loopView_year;
    private LoopView loopView_mooth;
    private LoopView loopView_day;
    private TextView tv_selectOK;
    private TextView tv_data_title;
    private TextView tv_selectToday;
    //被选中或默认显示的时间
    private int year = 2010;
    private int mooth = 10;
    private int day = 10;
    private int oldDayCounts = 31;
    private String startTime = "2010-10-10";
    private ArrayList<String> list_year = new ArrayList<String>();
    private ArrayList<String> list_mooth = new ArrayList<String>();
    private ArrayList<String> list_day = new ArrayList<String>();

    public DataWheelView(Context context) {
        this(context, null);
    }

    public DataWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
        initData();
        initEvent();
    }


    /**
     * 初始化数据
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_loop_data, this);
        loopView_year = (LoopView) findViewById(R.id.loopView_year);
        loopView_mooth = (LoopView) findViewById(R.id.loopView_mooth);
        loopView_day = (LoopView) findViewById(R.id.loopView_day);
        tv_selectOK = (TextView) findViewById(R.id.tv_selectOK);
        tv_data_title = (TextView) findViewById(R.id.tv_data_title);
        tv_selectToday = (TextView) findViewById(R.id.tv_selectToday);

    }

    private void initData() {

        initDataList();

        //设置原始数据
        year = getYear();
        mooth = getMooth();
        day = getDay();

        loopView_year.setItems(list_year);
        loopView_mooth.setItems(list_mooth);
        loopView_day.setItems(list_day);

        //默认时间,当前年月日
        for (int i = 0; i < list_year.size(); i++) {
            if (Integer.parseInt(list_year.get(i)) == getYear()) {
                loopView_year.setCurrentPosition(i);
            }
        }
        loopView_mooth.setCurrentPosition(getMooth() - 1);
        loopView_day.setCurrentPosition(getDay() - 1);

    }


    private void initEvent() {

        //滚动监听,年份
        loopView_year.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                year = Integer.parseInt(list_year.get(index));
                setRightDayCount();
            }
        });

        //滚动监听,月份
        loopView_mooth.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                mooth = Integer.parseInt(list_mooth.get(index));
                setRightDayCount();
            }
        });

        //滚动监听,天数
        loopView_day.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                day = Integer.parseInt(list_day.get(index));
            }
        });

        //确定按钮的监听
        tv_selectOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = year + "-" + mooth + "-" + day;
                //监听
                if (listenerOKClick != null) {
                    listenerOKClick.selectData(startTime);
                }


            }
        });

        //滚轮显示今天的日期的监听
        tv_selectToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list_year.size(); i++) {
                    if (Integer.parseInt(list_year.get(i)) == getYear()) {
                        loopView_year.setCurrentPosition(i);
                    }
                }
                loopView_mooth.setCurrentPosition(getMooth() - 1);
                loopView_day.setCurrentPosition(getDay() - 1);
                year = getYear();
                mooth = getMooth();
                day = getDay();

            }

        });


    }

    /**
     * 初始化日期的几个集合数据
     */
    private void initDataList() {
        list_year.clear();
        list_mooth.clear();
        list_day.clear();
        //年的时间
        for (int i = 2000; i < 2031; i++) {
            list_year.add("" + i);
        }

        //月的时间
        for (int i = 1; i < 13; i++) {
            list_mooth.add("" + i);
        }

        //日的时间
        for (int i = 1; i < 32; i++) {
            list_day.add("" + i);
        }
    }

    /**
     * 根据年数的月份显示对应的天数
     */
    private void setRightDayCount() {

        int dayCounts = 31;
        //28天的情况，润年2月
        if (leapYear(year) && mooth == 2) {
            dayCounts = 28;
        }
        //29天的情况，平年2月
        else if (!leapYear(year) && mooth == 2) {
            dayCounts = 29;
        }

        //30天的情况，2，4，6，9，11
        else if (mooth == 2 || mooth == 4 || mooth == 6 || mooth == 9 || mooth == 11) {
            dayCounts = 30;
        }

        //31天的情况 ，1，3，5，7，8，10，12
        else {
            dayCounts = 31;
        }

        list_day.clear();
        //日的时间
        for (int i = 1; i <= dayCounts; i++) {
            list_day.add("" + i);
        }

        if (oldDayCounts != dayCounts) {    //如果新老的天数不一样才变换天数
            //重新改变天的数量
            loopView_day.setItems(list_day);

            //判断是否要变更选中的天数，比如选中3-31滑动到2月变成2-28或2-29
            if (dayCounts < day) {
                loopView_day.setCurrentPosition(dayCounts - 1);
            }
            oldDayCounts = dayCounts;
        }


    }

    /**
     * 判断是闰年leapYear还是平年
     */
    private boolean leapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }

        if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }

        return false;
    }


    /**
     * 获取得当前时间的天数
     *
     * @return
     */
    private int getDay() {
        return TimeUtil.getTimeInt("d");
    }


    /**
     * 获得当前时间的月份
     */
    private int getMooth() {
        return TimeUtil.getTimeInt("M");
    }

    /**
     * 获得当前时间的年份
     */
    private int getYear() {
        return TimeUtil.getTimeInt("yyyy");
    }

    /*`````下面是一些公开方法`````*/

    /**
     * 设置标题的名字
     *
     * @param titleName
     */
    public void setTitleName(String titleName) {
        tv_data_title.setText("" + titleName);
    }

    
    /**
     * 设置标题的字体颜色
     */
    public void setTitleColor(int color){
    	 tv_data_title.setTextColor(color); 
    }

    
    /**
     * 设置标题的背景
     */
    public void setTitleBackground(int color){
    	 tv_data_title.setBackgroundColor(color); 
    }
    
    /**
     * 设置滚轮不可以循环，默认是可以循环的
     */

    public void setNotLoop() {
        //设置是否不循环播放
        loopView_year.setNotLoop();
        loopView_mooth.setNotLoop();
        loopView_day.setNotLoop();
    }

    /**
     * 点击确定时的回调方法
     */
    public interface OnListenerOKClick {
        void selectData(String dataString);
    }

    private OnListenerOKClick listenerOKClick;

    /**
     * 设置回调监听事件，往外面发送选中的日期字符串
     *
     * @param listenerOKClick
     */
    public void setListenerOKClick(OnListenerOKClick listenerOKClick) {
        this.listenerOKClick = listenerOKClick;
    }

}
