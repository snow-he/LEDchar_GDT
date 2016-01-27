package com.example.textam;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.colorpicker;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.umeng.analytics.MobclickAgent;

public class TextAM_MainActivity extends Activity {

	private TextView animationText;
	private EditText inputEdit;
	private int whichAM;
	private Animation animation1;
	private Animation animation2;
	private AMPreference myAMPreference;
	private ArrayAdapter<String> AMadapter;
	private long AM1duration;
	private long AM2duration;
	private colorpicker dialog;
	private colorpicker bdialog;
	private Context context;
	private String fontcolor;
	private SeekBar speedSKB;
	public static final String TXTDATA = "textdata";
	public static final String DATAFILE = "datafile";
	public String UMENGDATA = "UMENGDATA";
	public String UMENGDATA2 = "UMENGDATA2";
	public String UMENGDATA3 = "UMENGDATA3";
	public String UMENGDATAFILE = "UMENGDATAFILE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去锟斤拷锟斤拷锟斤拷锟斤拷
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_text_am__main);

		
		init();
	}

	public void gdtad() {
		// gdt ad
		final String APPId = "1101152570";
		final String BannerPosId = "9079537218417626401";
		
		 RelativeLayout rlMain = (RelativeLayout)findViewById(R.id.admoblayout);
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

	public void init() {
		String TAG = "TextAM_MainActivity";
		Spinner AMspinner = (Spinner) findViewById(R.id.spinner1);
		Button start_BT = (Button) findViewById(R.id.button1);
		Button colorpicker_BT = (Button) findViewById(R.id.button2);
		Button bcolorpicker_BT = (Button) findViewById(R.id.button3);

		speedSKB = (SeekBar) findViewById(R.id.seekBar1);
		// SeekBar fontsizeSKB = (SeekBar) findViewById(R.id.seekBar2);
		inputEdit = (EditText) findViewById(R.id.editText1);
		animationText = (TextView) findViewById(R.id.textView1);
		myAMPreference = AMPreference.instance(getApplicationContext());
		fontcolor = myAMPreference.getAMfontcolor();
		animationText.setTextColor(Integer.parseInt(fontcolor));
		animationText.setBackgroundColor(Integer.parseInt(myAMPreference
				.getAMbackgroundcolor()));

		// AMlist_adapter animation_adatpter = new AMlist_adapter();
		animation1 = AnimationUtils.loadAnimation(this, R.anim.animation1);
		animation2 = AnimationUtils.loadAnimation(this, R.anim.animation2);
		AM1duration = animation1.getDuration();
		AM2duration = animation2.getDuration();
		Log.i(TAG, "================AM1duration = " + AM1duration);
		Log.i(TAG, "================AM2duration = " + AM2duration);
		String[] animation_list = getResources().getStringArray(
				R.array.animatype_array);

		AMadapter = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, animation_list);
		AMadapter
				.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		AMspinner.setAdapter(AMadapter);
		AMspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View view,
					int postion, long id) {
				whichAM = postion;
				AMchange();
				if(whichAM == 0 || whichAM == 1){
					speedSKB.setVisibility(View.VISIBLE);
					speedSKB.setProgress(Integer.parseInt(myAMPreference
							.getAMspeed()));
				}else{
					speedSKB.setVisibility(View.INVISIBLE);
				}

			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});


		speedSKB.setOnSeekBarChangeListener(speedSKBclick);
		colorpicker_BT.setOnClickListener(colorpicker_BTclick);
		bcolorpicker_BT.setOnClickListener(bcolorpicker_BTclick);
		start_BT.setOnClickListener(start_BTclick);
		speedSKB.setProgress(Integer.parseInt(myAMPreference.getAMspeed()));
		String Imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
		Imei = Imei.substring(Imei.length()-6,Imei.length());
		
		SharedPreferences settings = getSharedPreferences(UMENGDATAFILE, 0);
		String umengadswitch= settings.getString(UMENGDATA, "");
		String umengphoneid = settings.getString(UMENGDATA2, "");
		String umengphoneid2 = settings.getString(UMENGDATA3, "");
		if(umengadswitch.contains("on")){		
			if(!umengphoneid.contains(Imei) && !umengphoneid2.contains(Imei)){
				//ad
				gdtad();
			}
		}
//		else if(umengadswitch.contains("off")){
//
//		}else{
//			//ad
//			gdtad();
//		}
		//umeng end

	}

	private OnSeekBarChangeListener speedSKBclick = new OnSeekBarChangeListener() {

		public void onStopTrackingTouch(SeekBar seekBar) {

			long level = seekBar.getProgress();
			if (level == 20) {
				level = 19;
				animation1.setDuration(((20 - level) * AM1duration) / 20);
				animation2.setDuration(((20 - level) * AM2duration) / 20);

			} else if (level == 0) {
			} else {
				animation1.setDuration(((20 - level) * AM1duration) / 20);
				animation2.setDuration(((20 - level) * AM2duration * 2) / 20);
			}
			myAMPreference.setAMspeed(String.valueOf(level));
			AMchange();
		}

		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			long level = progress;
			if (level == 20) {
				level = 19;
				animation1.setDuration(((20 - level) * AM1duration) / 20);
				animation2.setDuration(((20 - level) * AM2duration) / 20);

			} else if (level == 0) {
			} else {
				animation1.setDuration(((20 - level) * AM1duration) / 20);
				animation2.setDuration(((20 - level) * AM2duration * 2) / 20);
			}
			myAMPreference.setAMspeed(String.valueOf(level));
			AMchange();

		}
	};

	private OnClickListener colorpicker_BTclick = new OnClickListener() {

		public void onClick(View v) {
			dialog = new colorpicker(context, Integer.parseInt(myAMPreference
					.getAMfontcolor()), getResources().getString(
					R.string.colorpickerbutton),
					new colorpicker.OnColorChangedListener() {

						public void colorChanged(int color) {
							animationText.setTextColor(color);
							myAMPreference.setAMfontcolor(String.valueOf(color));
						}
					});
			dialog.setTitle(R.string.colorinfo);
			dialog.show();
		}
	};

	private OnClickListener bcolorpicker_BTclick = new OnClickListener() {

		public void onClick(View v) {
			bdialog = new colorpicker(context, Integer.parseInt(myAMPreference
					.getAMbackgroundcolor()), getResources().getString(
					R.string.colorpickerbutton),
					new colorpicker.OnColorChangedListener() {

						public void colorChanged(int color) {
							myAMPreference.setAMbackgroundcolor(String
									.valueOf(color));
							animationText.setBackgroundColor(color);
						}
					});
			bdialog.setTitle(R.string.colorinfo);
			bdialog.show();
		}
	};

	private OnClickListener start_BTclick = new OnClickListener() {

		public void onClick(View v) {

			String inputtext = inputEdit.getText().toString();
			SharedPreferences settings = getSharedPreferences(DATAFILE, 0);
			settings.edit().putString(TXTDATA, inputtext).commit();
			if (inputtext.trim().isEmpty()) {
				Toast.makeText(getBaseContext(),
						getResources().getString(R.string.inputnotice),
						Toast.LENGTH_LONG).show();
				return;
			} else {
				myAMPreference.setAMtext(inputtext);
			}
			Intent intent = new Intent();
			intent.setClass(TextAM_MainActivity.this,
					com.example.textam.AMdisplay.class);
			startActivity(intent);
		}
	};

	public void AMchange() {
		/*
		 * if (whichAM == 0) { myAMPreference.setAMtype("0"); } else {
		 * myAMPreference.setAMtype("1"); }
		 */
		myAMPreference.setAMtype(String.valueOf(whichAM));
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public void onResume() {
		super.onResume();
		SharedPreferences settings = getSharedPreferences(DATAFILE, 0);
		String username = settings.getString(TXTDATA, "happy birthday!");
		inputEdit.setText(username);

		MobclickAgent.onResume(this);// umeng
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);// umeng
	}
}
