#滚轮效果View的日期选择器和时间选择器和对话框

由于之前需要使用滚轮效果的时间View，摸索一一段时间自己封装了一个，应该对大家都是有点帮助的。

原本我是在一个多选菜单中筛选时间的，对话框肯定是有点突兀了，因为其他几个条件都是在下面出现的，时间也是要那种效果。

筛选框如下：

![1](http://i.imgur.com/RtEbM1r.png)

时间的筛选显示也要上面的效果。

![2](http://i.imgur.com/GLtwnAZ.png)

筛选框架的使用，之气已经写过了：

http://blog.csdn.net/wenzhi20102321/article/details/70045048

本文主要介绍自定义滚轮时间的View设计：

效果：

![3](http://i.imgur.com/IOQnw65.gif)

其实上面时间和时间的对话框是同一个自定义View，对话框中放入View，一样是可以显示在对话框中的。

上面日期的View：DataWheelView和时间的View：TimeWheelView，方法都是很类似的。

介绍下TimeWheelView的使用：

看时间所在的Activity就会用了：

```
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
        startLoopViewData.setTitleColor(0xff00ff00);//设置标题的颜色，默认黑色
      startLoopViewData.setTitleBackground(0xff0000ff);//设置标题的背景颜色，默认白色
//        startLoopViewData.setNotLoop();   //设置时间不可循环重复滚动，默认可以循环

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


```
是不是很简单啊，因为操作都是封装在TimeWheelView类里面的，只需监听确定按钮，就可以获取返回的数据了。
同样，日期的Activity也是同样简单。

再看看时间对话框的代码：
```

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

```
这个滚轮效果的View，就介绍到这里了，需要的可以自己看里面的封装代码，也可以修改。

可以看到上面动态效果的时间选择器时有个小的debug，由于分钟和秒钟没有从0开始，已经修改过来了。

源码地址：https://github.com/liwenzhi/Data_Time_wheelView

#共勉：每个人都是独一无二的来到这个世界，理应享受那份独一无二的精彩。要相信别人再有钱都不能成为你，因为你。。。独一无二，在这个世界上仅此一份。

