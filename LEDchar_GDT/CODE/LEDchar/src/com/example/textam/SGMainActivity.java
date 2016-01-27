package com.example.textam;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.umeng.analytics.MobclickAgent;

public class SGMainActivity extends Activity {

	public Button bt1;
	public SeekBar seekbar;
	private Camera mCamera;
	private Camera.Parameters parameters;
	public boolean isStart = true;
	private FlightThread flightThread;
	public int i = 0;
	public int delay = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sgmain);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		bt1 = (Button) findViewById(R.id.button1);
		seekbar = (SeekBar) findViewById(R.id.seekBar1);
		seekbar.setOnSeekBarChangeListener(skclick);
		bt1.setOnClickListener(bt1click);
		bt1.setBackgroundResource(R.drawable.flashlight_on);

		
		initvalue();
        flightThread = new FlightThread();
        flightThread.start();
	}

	private OnClickListener bt1click = new OnClickListener() {

		public void onClick(View v) {
			if(!isStart){
				isStart = true;
				bt1.setBackgroundResource(R.drawable.flashlight_on);
			}else{
				bt1.setBackgroundResource(R.drawable.flashlight_off);
				isStart = false;
				mhandler.obtainMessage(2).sendToTarget();
			}

		}
	};

	private OnSeekBarChangeListener skclick = new OnSeekBarChangeListener(){

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			delay = progress*5+50;
			
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public Handler mhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				openLight();
				break;
			case 2:
				closeLight();
				break;

			}
		}
	};

	private void initvalue() {
		try {
			mCamera = Camera.open();
		} catch (RuntimeException e) {
			mCamera = Camera.open(0);
		}
		parameters = mCamera.getParameters();
	}

	/**
	 * 打开手电
	 */
	private void openLight() {
		parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

	/**
	 * 关闭手电
	 */
	private void closeLight() {
		parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
		mCamera.setParameters(parameters);
	}
	
	class FlightThread extends Thread
    {
		@Override
		public void run(){
			while(true){
				while(isStart){
					
					if(i++ % 2 == 0){
						mhandler.obtainMessage(1).sendToTarget();
					}else{
						mhandler.obtainMessage(2).sendToTarget();
					}
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
    }
	
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("MainScreen"); //统计页面
	}
	@Override  
    protected void onPause() {  
		mCamera.release();
		mCamera=null;
		android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
        super.onPause();  
        MobclickAgent.onPageEnd("MainScreen"); 
    }  

}