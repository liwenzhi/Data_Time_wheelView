package com.example.wheel.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wheel.R;
import com.example.wheel.loopview.DataWheelView;
import com.example.wheel.loopview.TimeWheelView;

/**
 * 时间滚轮效果
 */
public class MyActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    /**
     * 选择日期视图
     */
    public void selectData(View view) {
            startActivity(new Intent(this,DataWheelViewActivity.class));
    }


    /**
     * 选择时间视图
     *
     * @param view
     */
    public void selectTime(View view) {
        startActivity(new Intent(this,TimeWheelViewActivity.class));
    }


    /**
     * 选择日期视图对话框
     */
    public void selectDataDialog(View view) {
   	View dataView=View.inflate(this, R.layout.activity_data, null);
    	DataWheelView loopViewData=(DataWheelView) dataView.findViewById(R.id.startLoopViewData);
//    	loopViewData.setTitleColor(0xff00ff00);
//    	loopViewData.setTitleBackground(0xff0000ff);
    	loopViewData.setTitleName("选择日期");
    	// 创建对话框对象
        final AlertDialog dialog = new AlertDialog.Builder(this).
        // 设置标题
//                setTitle("通过按钮关闭对话框").
                // 添加输入的文本框
                setView(dataView).
                // 产生
                create();

        // 设置对话框不可以关闭，一般情况下对话框是失去焦点后自动消失的
        // 但是加 了.setCancelable(false)，对话框就不会消失，除非手动退出
        dialog.setCancelable(false);

        // 显示
        dialog.show();

//        //给对话框设置一个监听时间，对话框退出前会执行
//        dialog.setOnDismissListener(new OnDismissListener() {
//
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                // 只要关闭都会调用
//                Toast.makeText(MyActivity.this, "关闭", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });		
        
        //时间视图的确定的回调方法
        loopViewData.setListenerOKClick(new DataWheelView.OnListenerOKClick() {
			
			@Override
			public void selectData(String dataString) {
				dialog.cancel();
				  Toast.makeText(MyActivity.this, "你选择的日期是："+dataString, Toast.LENGTH_SHORT)
                .show();	 
				
			}
		});
    }


    /**
     * 选择时间视图对话框
     *
     * @param view
     */
    public void selectTimeDialog(View view) {
     	View timeView=View.inflate(this, R.layout.activity_time, null);
    	TimeWheelView loopViewData=(TimeWheelView) timeView.findViewById(R.id.startLoopViewData);
    	loopViewData.setTitleName("选择时间");
    	loopViewData.setTitleColor(0xff00ff00);
    	loopViewData.setTitleBackground(0xff0000ff);
    	// 创建对话框对象
        final AlertDialog dialog = new AlertDialog.Builder(this).
                setView(timeView).
                create();
        dialog.show();
        
        //时间视图的确定的回调方法
        loopViewData.setListenerOKClick(new TimeWheelView.OnListenerOKClick() {
			
			@Override
			public void selectData(String dataString) {
				dialog.cancel();
				  Toast.makeText(MyActivity.this, "你选择的时间是："+dataString, Toast.LENGTH_SHORT)
                .show();	 
				
			}
		});
        
    }


}
