package com.example.doctortang;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class SetActivity extends Activity {
	protected void onCreate(Bundle saveInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_set);

		ExitApplication.getInstance().addActivity(this);

		final ImageView set_im2 = (ImageView) findViewById(R.id.set_image2);
		final ImageView set_im3 = (ImageView) findViewById(R.id.set_image3);
		final ImageView set_im4 = (ImageView) findViewById(R.id.set_image4);
		final ImageView set_im5 = (ImageView) findViewById(R.id.set_image5);
		final ImageView set_im1 = (ImageView) findViewById(R.id.set_image1);

		set_im1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
				Intent intent = new Intent(SetActivity.this,
						MainActivity.class);
				startActivity(intent);

			}

		});

		set_im2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this,
						SetActivity_list.class);
				startActivity(intent);

			}

		});

		set_im3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this,
						SetActivity_password.class);
				startActivity(intent);

			}

		});

		set_im4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this,
						SetActivity_about.class);
				startActivity(intent);

			}

		});

		set_im5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				final SharedPreferences sp = getSharedPreferences("tangLogin",
						MODE_PRIVATE);
				final SharedPreferences.Editor editor = sp.edit();
				editor.clear();
				editor.commit();
				Intent intent = new Intent(SetActivity.this,
						ViewFlipperActivity.class);
				startActivity(intent);
				finish();
			}

		});
	}

}
