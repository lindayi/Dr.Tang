package com.example.doctortang;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{

    private static Boolean isExit=false;

    protected void onCreate(Bundle saveInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        ExitApplication.getInstance().addActivity(this);

        final ImageView imagev2=(ImageView) findViewById(R.id.main_image2);
        final ImageView imagev4=(ImageView) findViewById(R.id.main_image4);
        final ImageView imagev5=(ImageView) findViewById(R.id.main_image5);
        final ImageView imageblue1=(ImageView) findViewById(R.id.main_blue1);
        final TextView main_tv=(TextView) findViewById(R.id.main_tv1);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate=new Date(System.currentTimeMillis());// 获取当前时间
        String str=formatter.format(curDate);
        main_tv.setText(str);

        imagev2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // finish();
                Intent intent=new Intent(MainActivity.this, UpRecordActivity.class);
                startActivity(intent);

            }

        });

        imageblue1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // finish();
                Intent intent=new Intent(MainActivity.this, Bluetooth.class);
                startActivity(intent);

            }

        });

        imagev4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // finish();
                Intent intent=new Intent(MainActivity.this, SearchHistory.class);
                startActivity(intent);
            }

        });

        imagev5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // finish();
                Intent intent=new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent);
            }

        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitprogram();
        }
        return false;
    }

    private void exitprogram()
    {
        Timer tExit=null;
        if(isExit == false)
        {
            isExit=true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit=new Timer();
            tExit.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    isExit=false;
                }
            }, 2000);
        }
        else
        {
            ExitApplication.getInstance().exit();

        }
    }
}
