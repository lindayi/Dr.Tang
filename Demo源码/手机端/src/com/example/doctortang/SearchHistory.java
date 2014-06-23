package com.example.doctortang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class SearchHistory extends Activity
{
    private ImageView search_hs1, search_hs2, search_hs3;
    private String str1;
    private int[] num=
    { 50, 50, 50, 50, 50, 50, 50 };
    private ArrayList<String> list;

    protected void onCreate(Bundle saveInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search);

        ExitApplication.getInstance().addActivity(this);

        search_hs1=(ImageView) findViewById(R.id.searchhistory_image1);
        search_hs2=(ImageView) findViewById(R.id.searchhistory_image3);
        search_hs3=(ImageView) findViewById(R.id.searchhistory_image4);

        search_hs1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                Intent intent=new Intent(SearchHistory.this, MainActivity.class);
                startActivity(intent);

            }

        });

        search_hs2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                Intent intent=new Intent(SearchHistory.this, SearchActivity.class);
                startActivity(intent);

            }

        });

        search_hs3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                final Handler handler=new Handler()
                {
                    public void handleMessage(Message msg)
                    {
                        super.handleMessage(msg);
                        // Toast.makeText(SearchActivity1.this, num[2], Toast.LENGTH_SHORT).show();
                    }
                };
                Thread t=new Thread()
                {
                    public void run()
                    {
                        try
                        {
                            list=new ArrayList<String>();
                            final SharedPreferences sp1=getSharedPreferences("tangLogin", MODE_PRIVATE);
                            String st1=sp1.getString("name", "fail");
                            String n=st1;
                            URL url=new URL("http://drtang.lindayi.tk/drtang.php?action=get_record&phonenum=" + n + "&starttime=2014-01-01%2000:00:00&endtime=2015-01-01%2023:59:59");
                            HttpURLConnection uc=(HttpURLConnection) url.openConnection();
                            BufferedReader reader=new BufferedReader(new InputStreamReader(uc.getInputStream()));
                            String ms="";
                            String lines;
                            while((lines=reader.readLine()) != null)
                            {
                                ms+=lines;
                            }
                            JSONObject jsono=new JSONObject(ms);
                            JSONArray array=jsono.getJSONArray("record");
                            for(int i=0; i < array.length(); i++)
                            {
                                str1=array.getJSONObject(i).getString("value");
                                list.add(str1);

                            }

                            for(int tep1=0; tep1 < 7; tep1++)
                            {
                                float temp=Float.parseFloat(list.get(tep1));
                                int number=(int) ((temp - 3.0) * 10);
                                num[tep1]=50 + number * 5;

                            }
                            handler.sendEmptyMessage(1);
                        }
                        catch(MalformedURLException e)
                        {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                        catch(JSONException e)
                        {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                        catch(ParseException e)
                        {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                        catch(IOException e)
                        {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }

                    }
                };
                t.start();

                TimerTask task=new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        Thread t1=new Thread()
                        {
                            public void run()
                            {
                                Intent intent=new Intent(SearchHistory.this, SearchActivity1.class);
                                intent.putExtra("number", num);
                                startActivity(intent);
                            }

                        };
                        runOnUiThread(t1);
                    }
                };
                Timer timer=new Timer();
                timer.schedule(task, 4000);

            }

        });

    }
}
