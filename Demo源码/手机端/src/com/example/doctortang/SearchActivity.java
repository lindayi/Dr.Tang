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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity
{
    private final int wc=ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int fp=ViewGroup.LayoutParams.MATCH_PARENT;
    private Button myButton;
    private ArrayList<String> list, listDay, listRound;
    private int leap=0, leap1=0;

    protected void onCreate(Bundle saveInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_history);

        ExitApplication.getInstance().addActivity(this);

        final ImageView search_im1=(ImageView) findViewById(R.id.search_image1);

        search_im1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                Intent intent=new Intent(SearchActivity.this, SearchHistory.class);
                startActivity(intent);

            }

        });

        myButton=(Button) findViewById(R.id.search_bt);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                LayoutInflater factory=LayoutInflater.from(SearchActivity.this);
                final View textEntryView=factory.inflate(R.layout.dialog, null);
                AlertDialog.Builder alert=new AlertDialog.Builder(SearchActivity.this);
                alert.setTitle("请输入月份");
                alert.setView(textEntryView);
                alert.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EditText edit_text=(EditText) textEntryView.findViewById(R.id.dialog_name);
                        final CharSequence edit_value=edit_text.getText();
                        final TextView search_time=(TextView) findViewById(R.id.search_time);
                        search_time.setText("2014-" + edit_value.toString());
                        final Handler handler=new Handler()
                        {
                            public void handleMessage(Message msg)
                            {
                                super.handleMessage(msg);
                                Toast.makeText(SearchActivity.this, "历史数据正在获取...", Toast.LENGTH_LONG).show();

                            }
                        };
                        Thread t=new Thread()
                        {
                            public void run()
                            {

                                final SharedPreferences sp1=getSharedPreferences("tangLogin", MODE_PRIVATE);
                                String st1=sp1.getString("name", "fail");
                                String n=st1;
                                String p=edit_value.toString();
                                String p1=Integer.toString((Integer.parseInt(p) + 1));
                                try
                                {
                                    list=new ArrayList<String>();
                                    listDay=new ArrayList<String>();
                                    listRound=new ArrayList<String>();

                                    URL url=new URL("http://drtang.lindayi.tk/drtang.php?action=get_record&phonenum=" + n + "&starttime=2014-" + p + "-01%2000:00:00&endtime=2014-" + p1
                                            + "-01%2023:59:59");
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
                                        list.add(array.getJSONObject(i).getString("value"));
                                        String s12=array.getJSONObject(i).getString("time");
                                        String s13=s12.substring(8, 10);
                                        listDay.add(s13);
                                        String s14=array.getJSONObject(i).getString("round");
                                        listRound.add(Integer.toString((Integer.parseInt(s14) + 1)));
                                        leap1++;
                                        // str1=array.getJSONObject(i).getString("value");
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
                                Thread t=new Thread()
                                {
                                    public void run()
                                    {
                                        TableLayout tableLayout=(TableLayout) findViewById(R.id.TableLayout01);
                                        tableLayout.setStretchAllColumns(true);
                                        for(int row=0; row < 31; row++)
                                        {
                                            TableRow tableRow=new TableRow(SearchActivity.this);
                                            tableRow.setBackgroundColor(Color.rgb(222, 220, 210));
                                            for(int col=0; col < 8; col++)
                                            {
                                                TextView tv=new TextView(SearchActivity.this);
                                                tv.setBackgroundResource(R.drawable.shapee);
                                                if(col == 0)
                                                {
                                                    int t;
                                                    t=row + 1;
                                                    String s=Integer.toString(t);
                                                    tv.setText(s);

                                                }
                                                else
                                                {
                                                    if(leap < leap1)
                                                    {
                                                        int t1, t2;
                                                        t1=Integer.parseInt(listRound.get(leap));
                                                        t2=Integer.parseInt(listDay.get(leap)) - 1;
                                                        if(col == t1 && row == t2)
                                                        {
                                                            tv.setText(list.get(leap));
                                                            leap++;
                                                        }
                                                        else
                                                            tv.setText("---");
                                                    }
                                                    else
                                                        tv.setText("---");
                                                }
                                                tableRow.addView(tv);

                                            }
                                            tableLayout.addView(tableRow, new TableLayout.LayoutParams(fp, wc));
                                        }
                                    }

                                };
                                runOnUiThread(t);
                            }
                        };
                        Timer timer=new Timer();
                        timer.schedule(task, 6000);

                    }
                });
                alert.show();

            }
        });

    }
}
