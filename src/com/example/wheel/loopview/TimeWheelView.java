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
 * 时分秒滚轮效果的视图
 */
public class TimeWheelView extends LinearLayout {

    private LoopView loopView_hours;
    private LoopView loopView_minutes;
    private LoopView loopView_seconds;
    private TextView tv_selectOK;
    private TextView tv_data_title;
    private TextView tv_selectNowTime;
    //被选中或默认显示的时间
    private int hours = 10;
    private int minutes = 10;
    private int seconds = 10;
    private String startTime = "10:10:10";
    private ArrayList<String> list_hours = new ArrayList<String>();
    private ArrayList<String> list_minutes = new ArrayList<String>();
    private ArrayList<String> list_seconds = new ArrayList<String>();

    public TimeWheelView(Context context) {
        this(context, null);
    }

    public TimeWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        LayoutInflater.from(context).inflate(R.layout.layout_loop_time, this);
        loopView_hours = (LoopView) findViewById(R.id.loopView_year);
        loopView_minutes = (LoopView) findViewById(R.id.loopView_mooth);
        loopView_seconds = (LoopView) findViewById(R.id.loopView_day);
        tv_selectOK = (TextView) findViewById(R.id.tv_selectOK);
        tv_data_title = (TextView) findViewById(R.id.tv_data_title);
        tv_selectNowTime = (TextView) findViewById(R.id.tv_selectToday);

    }

    private void initData() {

        initDataList();

        //设置原始数据
        hours = getHours();
        minutes = getMinutes();
        seconds = getSeconds();

        loopView_hours.setItems(list_hours);
        loopView_minutes.setItems(list_minutes);
        loopView_seconds.setItems(list_seconds);

        //默认时间,当前时分秒
        for (int i = 0; i < list_hours.size(); i++) {
            if (Integer.parseInt(list_hours.get(i)) == getHours()) {
                loopView_hours.setCurrentPosition(i);
            }
        }
        loopView_minutes.setCurrentPosition(getMinutes() );
        loopView_seconds.setCurrentPosition(getSeconds() );

    }


    private void initEvent() {

        //滚动监听,小时
        loopView_hours.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                hours = Integer.parseInt(list_hours.get(index));
            }
        });

        //滚动监听,分钟
        loopView_minutes.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                minutes = Integer.parseInt(list_minutes.get(index));
            }
        });

        //滚动监听,秒数
        loopView_seconds.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                seconds = Integer.parseInt(list_seconds.get(index));
            }
        });

        //确定按钮的监听
        tv_selectOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = hours + ":" + minutes + ":" + seconds;
                //监听
                if (listenerOKClick != null) {
                    listenerOKClick.selectData(startTime);
                }


            }
        });

        //滚轮显示当时时间的监听
        tv_selectNowTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list_hours.size(); i++) {
                    if (Integer.parseInt(list_hours.get(i)) == getHours()) {
                        loopView_hours.setCurrentPosition(i);
                    }
                }
              
                loopView_minutes.setCurrentPosition(getMinutes() );
                loopView_seconds.setCurrentPosition(getSeconds());
                hours = getHours();
                minutes = getMinutes();
                seconds = getSeconds();

            }

        });


    }

    /**
     * 初始化时间的几个集合数据
     */
    private void initDataList() {
        list_hours.clear();
        list_minutes.clear();
        list_seconds.clear();
        //小时的时间,12小时，还是24？
        for (int i = 0; i <= 23; i++) {
            list_hours.add("" + i);
        }

        //分钟的时间
        for (int i = 0; i <= 59; i++) {
            list_minutes.add("" + i);
        }

        //秒数的时间
        for (int i = 0; i <= 59; i++) {
            list_seconds.add("" + i);
        }
    }





    /**
     * 获取得当前时间的秒数
     *
     * @return
     */
    private int getSeconds() {
        return TimeUtil.getTimeInt("s");
    }


    /**
     * 获得当前时间的分钟
     */
    private int getMinutes() {
        return TimeUtil.getTimeInt("m");
    }

    /**
     * 获得当前时间的小时
     */
    private int getHours() {
        return TimeUtil.getTimeInt("H");
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
        loopView_hours.setNotLoop();
        loopView_minutes.setNotLoop();
        loopView_seconds.setNotLoop();
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
