package com.example.wheel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.wheel.R;
import com.example.wheel.loopview.DataWheelView;

/**
 * 日期选择器视图
 */
public class DataWheelViewActivity extends Activity {
    private DataWheelView startLoopViewData;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        startLoopViewData = (DataWheelView) findViewById(R.id.startLoopViewData);
        textView = (TextView) findViewById(R.id.textView);

    }

    private void initData() {
        startLoopViewData.setTitleName("开始日期");
        startLoopViewData.setTitleColor(0xffff0000);
//        startLoopViewData.setTitleBackground(color);
        startLoopViewData.setNotLoop();   //设置时间不可循环重复滚动

    }

    private void initEvent() {
        startLoopViewData.setListenerOKClick(new DataWheelView.OnListenerOKClick() {
            @Override
            public void selectData(String dataString) {
                textView.append(dataString + "\n");
            }
        });

    }

    /*
    Android Dex: [wheel] Caused by: java.lang.UnsupportedClassVersionError: com/android/dx/command/dexer/Main : Unsupported major.minor version 52.0
    * */
}
