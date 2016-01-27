package com.example.textam;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Camera.Parameters;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;


public class MyService extends Service implements SensorEventListener {
	private static final String TAG = "MyService";
	private SensorManager mSensorManager;
	public static Camera camera = null;
	private Parameters parameters = null;
	public static boolean kaiguan = true;
	private Vibrator mVibrator;
	private final int ROCKPOWER = 15;// 这是传感器系数
	public static int count = 0;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		// 获取传感器管理服务
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// 震动服务
		mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE); // 震动需要在androidmainfest里面注册哦亲

		Log.i(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		mSensorManager.unregisterListener(this);// 退出界面后，把传感器释放。
		closelight();
		Toast.makeText(this, R.string.servicestop, Toast.LENGTH_LONG).show();
		Log.i(TAG, "onDestroy");
	}

	@Override
	public void onStart(Intent intent, int startid) {
		// 加速度传感器
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				// 还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
				// 根据不同应用，需要的反应速率不同，具体根据实际情况设定
				SensorManager.SENSOR_DELAY_NORMAL);
		Log.i(TAG, "onStart");
	}

	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		// values[0]:X轴，values[1]：Y轴，values[2]：Z轴
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			// 在 这个if里面写监听，写要摇一摇干么子，知道么？猪头~~~
			if ((Math.abs(values[0]) > ROCKPOWER
					|| Math.abs(values[1]) > ROCKPOWER || Math.abs(values[2]) > ROCKPOWER)) {
				System.out.println("YYYYYYYYYYYY   Math.abs(values[0]="
						+ Math.abs(values[0]) + "     Math.abs(values[1]="
						+ Math.abs(values[1]) + "       Math.abs(values[2]"
						+ Math.abs(values[2]));
				if (count == 3) {
					mVibrator.vibrate(500);// 设置震动。
					if (kaiguan) {
						openlight();
						kaiguan = false;
					} else {
						closelight();
						kaiguan = true;
					}
					count = 0;
				} else {
					count++;
				}
			}
		}

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void openlight() {
		try {
			camera = Camera.open();
		} catch (RuntimeException e) {
			camera = Camera.open(0);
		}
		if (camera != null) {
			parameters = camera.getParameters();
			parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// 开启
			camera.setParameters(parameters);
		}
	}

	public void closelight() {
		if (camera != null) {
			parameters = camera.getParameters();
			parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// 开启
			camera.setParameters(parameters);
			camera.release();
			camera = null;
		}
	}
}