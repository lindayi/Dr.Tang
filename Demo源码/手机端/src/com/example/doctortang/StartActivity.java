package com.example.doctortang;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class StartActivity extends Activity
{
    protected void onCreate(Bundle saveInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_start);

        ExitApplication.getInstance().addActivity(this);

        if(isConnect(this) == false)
        {
            new AlertDialog.Builder(this).setTitle("�������").setMessage("��������ʧ�ܣ���ȷ����������").setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface arg0, int arg1)
                {
                    ExitApplication.getInstance().exit();
                }
            }).show();
        }

        TimerTask task=new TimerTask()
        {
            @Override
            public void run()
            {
                Thread t=new Thread()
                {
                    public void run()
                    {
                        final SharedPreferences sp=getSharedPreferences("tangLogin", MODE_PRIVATE);
                        String pass;
                        pass=sp.getString("record", "fail");
                        if(pass.equals("fail"))
                        {
                            Intent intent=new Intent(StartActivity.this, ViewFlipperActivity.class);
                            startActivity(intent);
                            StartActivity.this.finish();
                        }
                        if(pass.equals("1"))
                        {
                            Intent intent=new Intent(StartActivity.this, MainActivity.class);
                            startActivity(intent);
                            StartActivity.this.finish();
                        }
                    }

                };
                runOnUiThread(t);
            }
        };
        Timer timer=new Timer();
        timer.schedule(task, 3000);

    }

    public static boolean isConnect(Context context)
    {
        // ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
        try
        {
            ConnectivityManager connectivity=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivity != null)
            {
                // ��ȡ�������ӹ���Ķ���
                NetworkInfo info=connectivity.getActiveNetworkInfo();
                if(info != null && info.isConnected())
                {
                    // �жϵ�ǰ�����Ƿ��Ѿ�����
                    if(info.getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        catch(Exception e)
        {
            // TODO: handle exception
            Log.v("error", e.toString());
        }
        return false;
    }
}