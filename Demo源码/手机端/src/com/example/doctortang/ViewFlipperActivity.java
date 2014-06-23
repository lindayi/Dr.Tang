package com.example.doctortang;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ViewFlipperActivity extends Activity implements OnGestureListener
{
    /** Called when the activity is first created. */
    private int[] imageID=
    { R.drawable.a, R.drawable.b, R.drawable.c };
    private ViewFlipper viewFlipper=null;
    private GestureDetector gestureDetector=null;
    private static Boolean isExit=false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);

        ExitApplication.getInstance().addActivity(this);

        viewFlipper=(ViewFlipper) findViewById(R.id.viewflipper);
        // ����GestureDetector�������ڼ�������¼�
        gestureDetector=new GestureDetector(this);
        // ��������л���ͼƬ
        for(int i=0; i < imageID.length; i++)
        {
            // ����һ��ImageView����
            ImageView image=new ImageView(this);
            image.setImageResource(imageID[i]);
            // �������ؼ�
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            // ��ӵ�viewFlipper��
            viewFlipper.addView(image, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        }

        final TextView Tzhuce=(TextView) findViewById(R.id.TVzhuce);
        Tzhuce.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(ViewFlipperActivity.this, ZhuCeActivity.class);
                startActivity(intent);
                //ViewFlipperActivity.this.finish();

            }
        });

        final TextView Tdenglu=(TextView) findViewById(R.id.TVdenglu);
        Tdenglu.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(ViewFlipperActivity.this, DengLuActivity.class);
                startActivity(intent);
                //ViewFlipperActivity.this.finish();
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
            Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
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

    public boolean onDown(MotionEvent arg0)
    {
        return false;
    }

    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3)
    {
        // ����ָ�����ľ�������˼��㣬��������������120���أ������л��������������κ��л�������
        // �������һ���
        if(arg0.getX() - arg1.getX() > 120)
        {
            // ��Ӷ���
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.animator.push_left_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.animator.push_left_out));
            this.viewFlipper.showNext();
            return true;
        }// �������󻬶�
        else
            if(arg0.getX() - arg1.getX() < -120)
            {
                this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.animator.push_right_in));
                this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.animator.push_right_out));
                this.viewFlipper.showPrevious();
                return true;
            }
        return true;
    }

    public void onLongPress(MotionEvent e)
    {}

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        return false;
    }

    public void onShowPress(MotionEvent e)
    {}

    public boolean onSingleTapUp(MotionEvent e)
    {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return this.gestureDetector.onTouchEvent(event);
    }
}