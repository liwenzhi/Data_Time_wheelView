package com.example.wheel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wheel.R;
import com.example.wheel.loopview.DataWheelView;
import com.example.wheel.loopview.TimeWheelView;

/**
 * 时间选择器视图
 */
public class TimeWheelViewActivity extends Activity {
    private TimeWheelView startLoopViewData;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        startLoopViewData = (TimeWheelView) findViewById(R.id.startLoopViewData);
        textView = (TextView) findViewById(R.id.textView);

    }

    private void initData() {
        startLoopViewData.setTitleName("开始时间");
        startLoopViewData.setTitleColor(0xff00ff00);//设置标题的颜色
      startLoopViewData.setTitleBackground(0xff0000ff);//设置标题的背景颜色
//        startLoopViewData.setNotLoop();   //设置时间不可循环重复滚动

    }

    private void initEvent() {
    	//点击确定的回调方法
        startLoopViewData.setListenerOKClick(new TimeWheelView.OnListenerOKClick() {
            @Override
            public void selectData(String dataString) {
                textView.append(dataString + "\n");
            }
        });

    }

   

 }
