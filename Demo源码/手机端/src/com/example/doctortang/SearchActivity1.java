package com.example.doctortang;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SearchActivity1 extends Activity
{

    protected void onCreate(Bundle saveInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(saveInstanceState);
        setContentView(R.layout.chartview);

        final ChartView myView=(ChartView) this.findViewById(R.id.myView);
        int[] num=this.getIntent().getIntArrayExtra("number");
        myView.SetInfo(new String[]
        { "1", "2", "3", "4", "5", "6", "7" }, // X轴刻度
                       new String[]
                       { "", "3.0", "4.0", "5.0", "6.0", "7.0" }, // Y轴刻度
                       new int[]
                       { num[0], num[1], num[2], num[3], num[4], num[5], num[6] }, // 数据
                       "折线图");

    }
}
