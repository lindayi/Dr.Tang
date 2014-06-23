package com.example.doctortang;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;

public class ExitApplication extends Application
{
    private List activityList=new LinkedList();
    private static ExitApplication instance;

    private ExitApplication()
    {

    }

    // ����ģʽ�л�ȡΨһ��ExitApplicationʵ��
    public static ExitApplication getInstance()
    {
        if(null == instance)
        {
            instance=new ExitApplication();
        }
        return instance;
    }

    // ���Activity��������
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }

    // ��������Activity��finish
    public void exit()
    {
        for(Object activity : activityList)
        {
            ((Activity) activity).finish();
        }
        System.exit(0);
    }
}
