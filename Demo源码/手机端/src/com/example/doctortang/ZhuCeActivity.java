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
import android.widget.Toast;

public class ZhuCeActivity extends Activity
{
    protected void onCreate(Bundle saveInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_zhuce);

        ExitApplication.getInstance().addActivity(this);

        final Button Bzhuceyh=(Button) findViewById(R.id.zhuceyonghu);
        final EditText name1=(EditText) findViewById(R.id.zhuce_name);
        final EditText pass1=(EditText) findViewById(R.id.zhuce_code);

        Bzhuceyh.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                if(name1.getText().toString().equals(""))
                {
                    Toast.makeText(ZhuCeActivity.this, "«Î ‰»Î”√ªß√˚£°", Toast.LENGTH_LONG).show();

                }
                else
                    if(pass1.getText().toString().equals(""))
                    {
                        Toast.makeText(ZhuCeActivity.this, "«Î ‰»Î√‹¬Î£°", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        final Handler handler1=new Handler()
                        {
                            public void handleMessage(Message msg1)
                            {
                                super.handleMessage(msg1);
                                if(msg1.what == 1)
                                {
                                    Toast.makeText(ZhuCeActivity.this, "◊¢≤·≥…π¶", Toast.LENGTH_LONG).show();

                                    final SharedPreferences sp=getSharedPreferences("tangLogin", MODE_PRIVATE);
                                    final SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("name", name1.getText().toString());
                                    editor.putString("pass", pass1.getText().toString());
                                    editor.commit();

                                    Intent intent=new Intent(ZhuCeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    ZhuCeActivity.this.finish();
                                }
                                else
                                {
                                    Toast.makeText(ZhuCeActivity.this, "◊¢≤· ß∞‹£°", Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        Thread t1=new Thread()
                        {
                            public void run()
                            {

                                try
                                {
                                    String n=name1.getText().toString();
                                    String p=pass1.getText().toString();
                                    URL url1=new URL("http://drtang.lindayi.tk/drtang.php?action=register&phonenum=" + n + "&password=" + p);
                                    HttpURLConnection uc1=(HttpURLConnection) url1.openConnection();
                                    InputStream out=uc1.getInputStream();
                                    String result1=String.valueOf(out.read());
                                    if(result1.equals("49"))
                                    {
                                        handler1.sendEmptyMessage(1);
                                    }
                                    if(result1.equals("48"))
                                    {
                                        handler1.sendEmptyMessage(0);
                                    }

                                }
                                catch(IOException e)
                                {
                                    e.printStackTrace();
                                }

                            }
                        };
                        t1.start();
                    }

            }
        });
    }

}
