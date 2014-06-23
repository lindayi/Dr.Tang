package com.example.doctortang;

import android.app.Activity;

import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;

public class SetActivity_about extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_about);

		ExitApplication.getInstance().addActivity(this);

	}

}
