package com.example.textam;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

public class MarqueeMainActivity extends Activity {
	private MarqueeTextView test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȥ����Ϣ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// ǿ��Ϊ����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.marquee_activity_main);

		LinearLayout marqueelayout = (LinearLayout) findViewById(R.id.marqueelayout);

		String str;
		String speed;
		String color;
		String bcolor;
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		str = bundle.getString("str");
		speed = bundle.getString("speed");
		color = bundle.getString("color");
		bcolor = bundle.getString("bcolor");

		marqueelayout.setBackgroundColor(Integer.parseInt(bcolor));
		test = (MarqueeTextView) this.findViewById(R.id.test);
		test.setText(str);
		test.setTextColor(Integer.parseInt(color));
		test.getScrollSpeed(Integer.parseInt(speed));
		test.startScroll();

	}

	public void start(View v) {
		test.startScroll();
	}

	public void stop(View v) {
		test.stopScroll();
	}

	public void onBackPressed() {
		Toast.makeText(getBaseContext(),
				getResources().getString(R.string.backnotice),
				Toast.LENGTH_SHORT).show();
		finish();
	}

	public void startFor0(View v) {
		test.startFor0();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);//umeng
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);//umeng
	}
}
