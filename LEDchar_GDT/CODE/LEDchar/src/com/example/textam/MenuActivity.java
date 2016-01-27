package com.example.textam;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

public class MenuActivity extends Activity {
	public Button animbt, signalcolorbt, huabanbt, flashbt;
	private YoYo.YoYoString rope = null;
	public String UMENGDATA = "UMENGDATA";
	public String UMENGDATA2 = "UMENGDATA2";
	public String UMENGDATA3 = "UMENGDATA3";
	public String UMENGDATAFILE = "UMENGDATAFILE";
	public static boolean ishaslight = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.menuactivitylayout);
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_FLASH)) {
			Toast.makeText(this, R.string.nolight, Toast.LENGTH_LONG).show();
			ishaslight = false;
		}
		initview();
	}

	public void gdtad() {
		// gdt ad
		final String APPId = "1101152570";
		final String BannerPosId = "9079537218417626401";
		
		 RelativeLayout rlMain = (RelativeLayout)findViewById(R.id.admoblayout0);

		 BannerView banner = new BannerView(this, ADSize.BANNER, APPId, BannerPosId); 
         //设置广告轮播时间，为0或30~120之间的数字，单位为s,0标识不自动轮播
         banner.setRefresh(30);
         banner.setADListener(new AbstractBannerADListener() {

             public void onNoAD(int arg0) {
               Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
             }

             public void onADReceiv() {
               Log.i("AD_DEMO", "ONBannerReceive");
             }
           });
		rlMain.addView(banner);
		banner.loadAD();
		 
	}
	
	public void initview() {
		animbt = (Button) findViewById(R.id.animationsbt);
		signalcolorbt = (Button) findViewById(R.id.signalcolorbt);
		huabanbt = (Button) findViewById(R.id.huabanbt);
		flashbt = (Button)findViewById(R.id.rmadbt);

		animbt.setOnClickListener(animbtlistener);
		signalcolorbt.setOnClickListener(signalcolorbtlistener);
		huabanbt.setOnClickListener(huabanbtlistener);
		flashbt.setOnClickListener(flashbtlistener);

		rope = YoYo.with(Techniques.ZoomIn).duration(1000).playOn(animbt);
		rope = YoYo.with(Techniques.ZoomIn).duration(1000)
				.playOn(signalcolorbt);
		rope = YoYo.with(Techniques.ZoomIn).duration(1000)
				.playOn(huabanbt);
		rope = YoYo.with(Techniques.ZoomIn).duration(1000)
				.playOn(flashbt);
		
		//umeng
		String Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
		Imei = Imei.substring(Imei.length()-6,Imei.length());
		
		UmengUpdateAgent.update(this);
		MobclickAgent.updateOnlineConfig(this);
		String umengadswitch= MobclickAgent.getConfigParams(this, "LEDchar_ad_switch");
		String umengphoneid = MobclickAgent.getConfigParams(this, "LEDchar_phoneid");
		String umengphoneid2 = MobclickAgent.getConfigParams(this, "LEDchar_phoneid2");
		
		SharedPreferences settings = getSharedPreferences(UMENGDATAFILE, 0);
		settings.edit().putString(UMENGDATA, umengadswitch).commit();
		settings.edit().putString(UMENGDATA2, umengphoneid).commit();
		settings.edit().putString(UMENGDATA3, umengphoneid2).commit();

		if(umengadswitch.contains("on")){		
			if(!umengphoneid.contains(Imei) && !umengphoneid2.contains(Imei)){
				//ad
				gdtad();
				//rmadbt.setVisibility(View.VISIBLE);
			}//else{
				//rmadbt.setVisibility(View.INVISIBLE);
			//}
		}
//		else{
//			//rmadbt.setVisibility(View.INVISIBLE);
//			gdtad();
//
//		}
		//

	}

	private OnClickListener animbtlistener = new OnClickListener() {

		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MenuActivity.this, TextAM_MainActivity.class);
			startActivity(intent);
		}
	};

	private OnClickListener signalcolorbtlistener = new OnClickListener() {

		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MenuActivity.this, colorimage.class);
			startActivity(intent);
		}
	};

	private OnClickListener huabanbtlistener = new OnClickListener() {

		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MenuActivity.this,
					com.example.huatuban.MainActivity.class);
			startActivity(intent);
		}
	};
	
	private OnClickListener flashbtlistener = new OnClickListener() {

		public void onClick(View v) {
//			Intent intent = new Intent();
//			intent.setClass(MenuActivity.this, rmadactivity.class);
//			startActivity(intent);
			
			if (MyService.camera != null) {

				Parameters mparameters = MyService.camera.getParameters();
				mparameters.setFlashMode(Parameters.FLASH_MODE_OFF);// 关闭
				MyService.camera.setParameters(mparameters);
				MyService.camera.release();
				MyService.camera = null;

			}
			if (ishaslight) {
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), SGMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			} else {
				Toast.makeText(getBaseContext(), R.string.nolight,
						Toast.LENGTH_LONG).show();
			}
		}
	};


	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}