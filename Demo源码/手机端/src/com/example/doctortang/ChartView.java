package com.example.doctortang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class ChartView extends View
{
    public int XPoint=30; // ԭ���X����
    public int YPoint=360; // ԭ���Y����
    public int XScale=40; // X�Ŀ̶ȳ���
    public int YScale=60; // Y�Ŀ̶ȳ���
    public int XLength=280; // X��ĳ���
    public int YLength=350; // Y��ĳ���
    public String[] XLabel; // X�Ŀ̶�
    public String[] YLabel; // Y�Ŀ̶�
    public int[] Data; // ����
    public String Title; // ��ʾ�ı���
    private float screenW, screenH;
    private float lastX;
    private float lastY;
    private Scroller scroller;
    private float total_Width=0;
    private String[] str=new String[]
    { "", "50", "100", "150", "200", "250" };

    public ChartView(Context context, AttributeSet attr)
    {
        super(context, attr);
        scroller=new Scroller(context);
        screenW=this.getWidth();
        screenH=this.getHeight();
    }

    public void SetInfo(String[] XLabels, String[] YLabels, int[] AllData, String strTitle)
    {
        XLabel=XLabels;
        YLabel=YLabels;
        Data=AllData;
        Title=strTitle;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);// ��дonDraw����

        System.out.println("3");
        // canvas.drawColor(Color.WHITE);//���ñ�����ɫ
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);// ȥ���
        paint.setColor(Color.BLACK);// ��ɫ
        Paint paint1=new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);// ȥ���
        paint1.setColor(Color.DKGRAY);
        paint.setTextSize(12); // ���������ִ�С
        // ����Y��(����ϵͳ������Ļ��ԭ�������Ͻǣ�
        canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint); // ����
        for(int i=0; i * YScale < YLength; i++)
        {
            canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i * YScale, paint); // �̶�
                                                                                                  // XPoint+5������һ���̵�С����
            try
            {
                canvas.drawText(YLabel[i], XPoint - 22, YPoint - i * YScale + 5, paint); // ����
            }
            catch(Exception e)
            {}
        }
        canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength + 6, paint); // ��ͷ
        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength + 6, paint);
        // ����X��
        canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint); // ����
        for(int i=0; i * XScale < XLength; i++)
        {
            canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale, YPoint - 5, paint); // �̶�
            try
            {
                canvas.drawText(XLabel[i], XPoint + i * XScale - 10, YPoint + 20, paint); // ����
                // ����ֵ
                if(i > 0 && YCoord(Data[i - 1]) != -999 && YCoord(Data[i]) != -999) // ��֤��Ч����
                    canvas.drawLine(XPoint + (i - 1) * XScale, YCoord(Data[i - 1]), XPoint + i * XScale, YCoord(Data[i]), paint);
                canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), 2, paint);
            }
            catch(Exception e)
            {}
        }
        canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6, YPoint - 3, paint); // ��ͷ
        canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6, YPoint + 3, paint);
        paint.setTextSize(16);
        canvas.drawText(Title, 150, 50, paint);
    }

    private int YCoord(int y0) // �������ʱ��Y���꣬������ʱ����-999
    {
        int y;
        try
        {
            y=y0;
        }
        catch(Exception e)
        {
            return -999; // �����򷵻�-999
        }
        try
        {
            return YPoint - y * YScale / Integer.parseInt(str[1]);
        }
        catch(Exception e)
        {}
        return y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastX=event.getX();
                lastY=event.getY();

                return true;
            case MotionEvent.ACTION_MOVE:
                float newX=event.getX();
                float newY=event.getY();

                scrollBy((int) ((lastX - newX) * 0.5), (int) ((lastY - newY) * 0.5));
                lastX=newX;
                lastY=newY;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX=getScrollX();
                int scrollY=getScrollY();
                if((scrollX < 0) && (scrollX < -10 || scrollY > 10))
                {
                    // XY���򳬳����λ��
                    scroller.startScroll(scrollX, scrollY, -scrollX, -scrollY);
                    invalidate();
                }
                else
                    if((scrollX > total_Width - screenW) && (scrollY < -10 || scrollY > 10))
                    {
                        // XY���򳬳��ұ�λ��
                        scroller.startScroll(scrollX, scrollY, (int) (total_Width - screenW - scrollX), -scrollY);
                        invalidate();
                    }
                    else
                        if(scrollX < 0)
                        {
                            // X���򳬳���ߵ�λ��
                            scroller.startScroll(scrollX, scrollY, -scrollX, 0);
                            invalidate();

                        }
                        else
                            if(scrollX > total_Width - screenW)
                            {
                                // X���򳬳��ұ߱ߵ�λ��
                                scroller.startScroll(scrollX, scrollY, (int) (total_Width - screenW - scrollX), 0);
                                invalidate();
                            }
                            else
                                if(scrollY < -10 || scrollY > 10)
                                {
                                    // Y���򳬳���λ��
                                    scroller.startScroll(scrollX, scrollY, 0, -scrollY);
                                    invalidate();
                                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll()
    {
        if(scroller.computeScrollOffset())
        {
            // ����������������������scroller�����˻����Ӷ�ʹ������
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
            return;
        }
        super.computeScroll();
    }
}
