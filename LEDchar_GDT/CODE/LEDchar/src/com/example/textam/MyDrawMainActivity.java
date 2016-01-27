package com.example.textam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyDrawMainActivity extends Activity {
	Button clearBtn;
	myview paintView;
	private AMPreference myAMPreference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ���Զ���Ŀؼ�����Ϊ�������
		// setContentView(new MyPaintView(this));

		// ʹ�ò����ļ�
		setContentView(R.layout.activity_my_draw_main);
		myAMPreference = AMPreference.instance(getBaseContext());
		String pcolor = myAMPreference.getAMfontcolor();

		paintView = (myview) findViewById(R.id.view_paint);
		paintView.pcolor = Integer.parseInt(pcolor);
		Toast.makeText(MyDrawMainActivity.this, getResources().getString(R.string.menunotice), Toast.LENGTH_LONG).show();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) { // ���/����������
			paintView.clear();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
