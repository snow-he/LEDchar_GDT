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
	private final int ROCKPOWER = 15;// ���Ǵ�����ϵ��
	public static int count = 0;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		// ��ȡ�������������
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// �𶯷���
		mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE); // ����Ҫ��androidmainfest����ע��Ŷ��

		Log.i(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		mSensorManager.unregisterListener(this);// �˳�����󣬰Ѵ������ͷš�
		closelight();
		Toast.makeText(this, R.string.servicestop, Toast.LENGTH_LONG).show();
		Log.i(TAG, "onDestroy");
	}

	@Override
	public void onStart(Intent intent, int startid) {
		// ���ٶȴ�����
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				// ����SENSOR_DELAY_UI��SENSOR_DELAY_FASTEST��SENSOR_DELAY_GAME�ȣ�
				// ���ݲ�ͬӦ�ã���Ҫ�ķ�Ӧ���ʲ�ͬ���������ʵ������趨
				SensorManager.SENSOR_DELAY_NORMAL);
		Log.i(TAG, "onStart");
	}

	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		// values[0]:X�ᣬvalues[1]��Y�ᣬvalues[2]��Z��
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			// �� ���if����д������дҪҡһҡ��ô�ӣ�֪��ô����ͷ~~~
			if ((Math.abs(values[0]) > ROCKPOWER
					|| Math.abs(values[1]) > ROCKPOWER || Math.abs(values[2]) > ROCKPOWER)) {
				System.out.println("YYYYYYYYYYYY   Math.abs(values[0]="
						+ Math.abs(values[0]) + "     Math.abs(values[1]="
						+ Math.abs(values[1]) + "       Math.abs(values[2]"
						+ Math.abs(values[2]));
				if (count == 3) {
					mVibrator.vibrate(500);// �����𶯡�
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
			parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// ����
			camera.setParameters(parameters);
		}
	}

	public void closelight() {
		if (camera != null) {
			parameters = camera.getParameters();
			parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// ����
			camera.setParameters(parameters);
			camera.release();
			camera = null;
		}
	}
}