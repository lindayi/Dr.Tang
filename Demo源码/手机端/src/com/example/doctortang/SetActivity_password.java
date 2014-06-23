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
import android.widget.Toast;

public class SetActivity_password extends Activity
{
    private EditText set_pass1, set_pass2;
    private Button set_editpass;

    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        ExitApplication.getInstance().addActivity(this);

        final ImageView pass_im1=(ImageView) findViewById(R.id.set_pass_image);
        set_pass1=(EditText) findViewById(R.id.set_pass1);
        set_pass2=(EditText) findViewById(R.id.set_pass2);
        set_editpass=(Button) findViewById(R.id.set_editpass);

        pass_im1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                Intent intent=new Intent(SetActivity_password.this, SetActivity.class);
                startActivity(intent);

            }

        });

        set_editpass.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(set_pass1.getText().toString().equals(""))
                {
                    Toast.makeText(SetActivity_password.this, "请输入新密码！", Toast.LENGTH_SHORT).show();
                }
                else
                    if(set_pass2.getText().toString().equals(""))
                    {
                        Toast.makeText(SetActivity_password.this, "请再次输入新密码！", Toast.LENGTH_SHORT).show();
                    }
                    else
                        if((set_pass2.getText().toString().equals(set_pass1.getText().toString())) == false)
                        {
                            Toast.makeText(SetActivity_password.this, "密码不同，请重新输入！", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(SetActivity_password.this, "密码修改成功", Toast.LENGTH_LONG).show();

                                        final SharedPreferences sp=getSharedPreferences("tangLogin", MODE_PRIVATE);
                                        final SharedPreferences.Editor editor=sp.edit();

                                        editor.putString("pass", set_pass2.getText().toString());

                                        editor.commit();

                                        Intent intent=new Intent(SetActivity_password.this, SetActivity.class);
                                        startActivity(intent);
                                        SetActivity_password.this.finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(SetActivity_password.this, "密码修改失败！", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            Thread t=new Thread()
                            {
                                public void run()
                                {

                                    try
                                    {
                                        final SharedPreferences sp1=getSharedPreferences("tangLogin", MODE_PRIVATE);
                                        String st1=sp1.getString("name", "fail");
                                        String n=st1;

                                        URL url=new URL("http://drtang.lindayi.tk/drtang.php?action=edit_password&phonenum=" + n + "&password=" + set_pass2.getText().toString());
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
    }

}
