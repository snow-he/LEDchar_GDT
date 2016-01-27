package com.example.textam;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class rmadactivity extends Activity{
	public TextView idtv;
	public Button exitbt;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.rmadlayout);
		String Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
				.getDeviceId();
		Imei = Imei.substring(Imei.length()-6,Imei.length());
		idtv = (TextView)findViewById(R.id.rmadtextViewid);
		exitbt = (Button)findViewById(R.id.exitbt);
		
		idtv.setText(idtv.getText()+Imei);
		exitbt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
	}
}
