package com.example.doctortang;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity_list extends Activity {

	private String str[] = { "", "", "", "", "" };
	private TextView set_text1, set_text2, set_text3, set_text4, set_text5;
	private Button set_add, set_delete;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_list);

		ExitApplication.getInstance().addActivity(this);

		set_text1 = (TextView) findViewById(R.id.set_list_phone1);
		set_text2 = (TextView) findViewById(R.id.set_list_phone2);
		set_text3 = (TextView) findViewById(R.id.set_list_phone3);
		set_text4 = (TextView) findViewById(R.id.set_list_phone4);
		set_text5 = (TextView) findViewById(R.id.set_list_phone5);

		set_add = (Button) findViewById(R.id.set_list_add);
		set_delete = (Button) findViewById(R.id.set_list_delete);

		final TextView set_sz[] = { set_text1, set_text2, set_text3, set_text4,
				set_text5 };

		final ImageView list_im1 = (ImageView) findViewById(R.id.set_image1);

		upDate();

		list_im1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
				Intent intent = new Intent(SetActivity_list.this,
						SetActivity.class);
				startActivity(intent);

			}

		});

		set_add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LayoutInflater factory = LayoutInflater
						.from(SetActivity_list.this);
				final View textEntryView = factory.inflate(R.layout.dialog,
						null);
				AlertDialog.Builder alert = new AlertDialog.Builder(
						SetActivity_list.this);
				alert.setTitle("添加联系人");
				alert.setView(textEntryView);
				alert.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								EditText edit_text = (EditText) textEntryView
										.findViewById(R.id.dialog_name);
								final CharSequence edit_value = edit_text
										.getText();
								final Handler handler = new Handler() {
									public void handleMessage(Message msg) {
										super.handleMessage(msg);
										if (msg.what == 1) {
											Toast.makeText(
													SetActivity_list.this,
													"添加联系人成功",
													Toast.LENGTH_LONG).show();
										} else {
											Toast.makeText(
													SetActivity_list.this,
													"添加失败，请重试！",
													Toast.LENGTH_LONG).show();
										}
									}
								};
								Thread t = new Thread() {
									public void run() {

										try {
											final SharedPreferences sp1 = getSharedPreferences(
													"tangLogin", MODE_PRIVATE);
											String st1 = sp1.getString("name",
													"fail");
											String n = st1;
											String p = edit_value.toString();
											URL url = new URL(
													"http://drtang.lindayi.tk/drtang.php?action=add_guardian&phonenum="
															+ n
															+ "&guardiantel="
															+ p);
											HttpURLConnection uc = (HttpURLConnection) url
													.openConnection();
											InputStream out = uc
													.getInputStream();
											String result = String.valueOf(out
													.read());

											if (result.equals("49")) {
												handler.sendEmptyMessage(1);
											} else {
												handler.sendEmptyMessage(0);
											}

										} catch (IOException e) {
											e.printStackTrace();
										}

									}
								};
								t.start();
								Thread t1 = new Thread() {
									@Override
									public void run() {
										try {
											Thread.sleep(2000);
										} catch (InterruptedException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									}
								};
								t1.start();
								SetActivity_list.this.finish();
								Intent intent = new Intent(
										SetActivity_list.this,
										SetActivity_list.class);
								startActivity(intent);
							}
						});
				alert.show();

			}
		});

		set_delete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LayoutInflater factory = LayoutInflater
						.from(SetActivity_list.this);
				final View textEntryView = factory.inflate(R.layout.dialog,
						null);
				AlertDialog.Builder alert = new AlertDialog.Builder(
						SetActivity_list.this);
				alert.setTitle("删除联系人");
				alert.setMessage("请输入要删除的联系人号码：");
				alert.setView(textEntryView);
				alert.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								EditText edit_text = (EditText) textEntryView
										.findViewById(R.id.dialog_name);
								final CharSequence edit_value = edit_text
										.getText();
								final Handler handler = new Handler() {
									public void handleMessage(Message msg) {
										super.handleMessage(msg);
										if (msg.what == 1) {
											Toast.makeText(
													SetActivity_list.this,
													"删除联系人成功",
													Toast.LENGTH_LONG).show();
										} else {
											Toast.makeText(
													SetActivity_list.this,
													"删除失败，请重试！",
													Toast.LENGTH_LONG).show();
										}
									}
								};
								Thread t = new Thread() {
									public void run() {

										try {
											final SharedPreferences sp1 = getSharedPreferences(
													"tangLogin", MODE_PRIVATE);
											String st1 = sp1.getString("name",
													"fail");
											String n = st1;
											String p = edit_value.toString();
											URL url = new URL(
													"http://drtang.lindayi.tk/drtang.php?action=del_guardian&phonenum="
															+ n
															+ "&guardiantel="
															+ p);
											HttpURLConnection uc = (HttpURLConnection) url
													.openConnection();
											InputStream out = uc
													.getInputStream();
											String result = String.valueOf(out
													.read());

											if (result.equals("49")) {
												handler.sendEmptyMessage(1);
											} else {
												handler.sendEmptyMessage(0);
											}

										} catch (IOException e) {
											e.printStackTrace();
										}

									}
								};
								t.start();
								Thread t1 = new Thread() {
									@Override
									public void run() {
										try {
											Thread.sleep(2000);
										} catch (InterruptedException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									}
								};
								t1.start();
								SetActivity_list.this.finish();
								Intent intent = new Intent(
										SetActivity_list.this,
										SetActivity_list.class);
								startActivity(intent);
							}
						});
				alert.show();
			}
		});

	}

	public void upDate() {
		final TextView set_sz[] = { set_text1, set_text2, set_text3, set_text4,
				set_text5 };

		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				set_sz[msg.what].setText(str[msg.what]);
				if (set_sz[0].getText().toString().equals("")) {
					Toast.makeText(SetActivity_list.this, "未添加联系人",
							Toast.LENGTH_LONG).show();
				}
			}
		};
		Thread t = new Thread() {
			public void run() {
				final SharedPreferences sp1 = getSharedPreferences("tangLogin",
						MODE_PRIVATE);
				String st1 = sp1.getString("name", "fail");
				HttpGet re = new HttpGet(
						"http://drtang.lindayi.tk/drtang.php?action=get_guardian&phonenum="
								+ st1);
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse response = httpClient.execute(re);

					String ms = "["
							+ EntityUtils.toString(response.getEntity()) + "]";
					JSONArray array = new JSONArray(ms);
					for (int i = 0; i < array.length(); i++) {
						JSONObject o = (JSONObject) array.get(i);
						JSONArray strID = o.getJSONArray("guardian");
						for (int j = 0; j < strID.length(); j++) {

							str[j] += strID.getString(j);
							handler.sendEmptyMessage(j);

						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		};
		t.start();
	}
}
