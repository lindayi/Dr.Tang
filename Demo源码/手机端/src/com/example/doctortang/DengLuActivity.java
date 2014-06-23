package com.example.doctortang;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import android.widget.TextView;
import android.widget.Toast;

public class DengLuActivity extends Activity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);

        ExitApplication.getInstance().addActivity(this);

        final EditText name=(EditText) findViewById(R.id.login_userName);
        final EditText pass=(EditText) findViewById(R.id.login_userCode);
        final Button Bdenglu=(Button) findViewById(R.id.login_denglu);
        final TextView Tpass=(TextView) findViewById(R.id.login_jizhumima);
        final ImageView login_im1=(ImageView) findViewById(R.id.login_logo1);

        login_im1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                Intent intent=new Intent(DengLuActivity.this, ViewFlipperActivity.class);
                startActivity(intent);

            }

        });

        Bdenglu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(name.getText().toString().equals(""))
                {
                    Toast.makeText(DengLuActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                }
                else
                    if(pass.getText().toString().equals(""))
                    {
                        Toast.makeText(DengLuActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {

                        final Handler handler=new Handler()
                        {
                            public void handleMessage(Message msg)
                            {
                                super.handleMessage(msg);
                                if(msg.what == 1)
                                {
                                    Toast.makeText(DengLuActivity.this, "登录成功", Toast.LENGTH_LONG).show();

                                    final SharedPreferences sp=getSharedPreferences("tangLogin", MODE_PRIVATE);
                                    final SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("name", name.getText().toString());
                                    editor.putString("pass", pass.getText().toString());

                                    editor.commit();

                                    Intent intent=new Intent(DengLuActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    DengLuActivity.this.finish();
                                }
                                else
                                {
                                    final SharedPreferences sp=getSharedPreferences("tangLogin", MODE_PRIVATE);
                                    final SharedPreferences.Editor editor=sp.edit();
                                    editor.clear();
                                    editor.commit();
                                    Toast.makeText(DengLuActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        Thread t=new Thread()
                        {
                            public void run()
                            {

                                try
                                {
                                    String n=name.getText().toString();
                                    String p=pass.getText().toString();
                                    URL url=new URL("http://drtang.lindayi.tk/drtang.php?action=login&phonenum=" + n + "&password=" + p);
                                    HttpURLConnection uc=(HttpURLConnection) url.openConnection();
                                    InputStream out=uc.getInputStream();
                                    String result=String.valueOf(out.read());

                                    if(result.equals("49"))
                                    {
                                        handler.sendEmptyMessage(1);
                                    }
                                    else
                                    {
                                        handler.sendEmptyMessage(0);
                                    }

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

        final TextView Tzhucehao=(TextView) findViewById(R.id.login_zhuce);

        Tzhucehao.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent=new Intent(DengLuActivity.this, ZhuCeActivity.class);
                startActivity(intent);
            }
        });

        Tpass.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                if(name.getText().toString().equals(""))
                {
                    Toast.makeText(DengLuActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                }
                else
                    if(pass.getText().toString().equals(""))
                    {
                        Toast.makeText(DengLuActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        final SharedPreferences sp=getSharedPreferences("tangLogin", MODE_PRIVATE);
                        final SharedPreferences.Editor editor=sp.edit();

                        editor.putString("record", "1");
                        editor.commit();
                        Toast.makeText(DengLuActivity.this, "密码已记录！", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}