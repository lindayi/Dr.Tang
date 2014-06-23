package com.example.doctortang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class UpRecordActivity extends Activity
{

    private EditText record_editv2;
    private Button record_bt;
    private ImageView record_im1;
    private Spinner record_sp0, record_sp1, record_sp2, record_sp3;
    private String urlstr;

    protected void onCreate(Bundle saveInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_upload_record);

        ExitApplication.getInstance().addActivity(this);

        record_editv2=(EditText) findViewById(R.id.record_canhou);

        String stemp=this.getIntent().getStringExtra("value");
        record_editv2.setText(stemp);
        // Toast.makeText(UpRecordActivity.this, stemp, Toast.LENGTH_SHORT).show();

        record_bt=(Button) findViewById(R.id.record_upload);

        record_im1=(ImageView) findViewById(R.id.record_image1);

        record_im1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                Intent intent=new Intent(UpRecordActivity.this, MainActivity.class);
                startActivity(intent);

            }

        });

        record_bt.setOnClickListener(new View.OnClickListener() // 上传记录按钮监听器
        {
            public void onClick(View view)
            {

                // Toast.makeText(UpRecordActivity.this, stemp, Toast.LENGTH_SHORT).show();
                if(record_editv2.getText().toString().equals(""))
                {
                    Toast.makeText(UpRecordActivity.this, "请输入血糖值！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(UpRecordActivity.this, "测量记录正在上传中...", Toast.LENGTH_LONG).show();
                    final SharedPreferences sp=getSharedPreferences("tangLogin", MODE_PRIVATE);
                    record_sp0=(Spinner) findViewById(R.id.record_spinner0);
                    record_sp1=(Spinner) findViewById(R.id.record_spinner1);
                    record_sp2=(Spinner) findViewById(R.id.record_spinner2);
                    record_sp3=(Spinner) findViewById(R.id.record_spinner3);
                    String st1=sp.getString("name", "fail");
                    String n1=st1;

                    String n2=record_editv2.getText().toString();

                    String n3=record_sp1.getSelectedItem().toString();
                    String n4=record_sp2.getSelectedItem().toString();
                    String n5=record_sp3.getSelectedItem().toString();
                    String n0=record_sp0.getSelectedItem().toString();

                    if(n0.equals("早餐前"))
                        n0="0";
                    if(n0.equals("早餐后"))
                        n0="1";
                    if(n0.equals("午餐前"))
                        n0="2";
                    if(n0.equals("午餐后"))
                        n0="3";
                    if(n0.equals("晚餐前"))
                        n0="4";
                    if(n0.equals("晚餐后"))
                        n0="5";
                    if(n0.equals("睡觉前"))
                        n0="6";
                    try
                    {
                        urlstr="http://drtang.lindayi.tk/drtang.php?action=add_record&phonenum=" + n1

                        + "&value=" + n2

                        + "&food=" + URLEncoder.encode(n3, "utf-8")

                        + "&sport=" + URLEncoder.encode(n4, "utf-8")

                        + "&medicine=" + URLEncoder.encode(n5, "utf-8")

                        + "&round=" + n0;
                    }
                    catch(UnsupportedEncodingException e1)
                    {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }

                    final Handler handler=new Handler()
                    {
                        public void handleMessage(Message msg)
                        {
                            super.handleMessage(msg);

                            Toast.makeText(UpRecordActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();

                        }
                    };
                    Thread t=new Thread()
                    {
                        public void run()
                        {
                            try
                            {

                                URL url=new URL(urlstr);
                                HttpURLConnection uc=(HttpURLConnection) url.openConnection();
                                BufferedReader reader=new BufferedReader(new InputStreamReader(uc.getInputStream()));

                                String result=reader.readLine();

                                Message msg=new Message();
                                msg.obj=result;
                                handler.sendMessage(msg);

                            }
                            catch(IOException e)
                            {
                                e.printStackTrace();
                            }

                        }
                    };
                    t.start();
                }
            }
        });
    }
}
