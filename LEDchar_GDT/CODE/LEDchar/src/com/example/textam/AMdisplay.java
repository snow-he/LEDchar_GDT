package com.example.textam;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lb.auto_fit_textview.AutoResizeTextView;
import com.umeng.analytics.MobclickAgent;

public class AMdisplay extends Activity {
	private Animation AM1;
	private AutoResizeTextView animationText;
	private AMPreference myAMPreference;
	private String animationtype;
	private String animationspeed;
	private String animationfontcolor;
	private String animationbackgroundcolor;
	private String animationstring;
	private int AMspeedlevel;
	private long AM1duration;
	private YoYo.YoYoString rope = null;
	private Timer timer = new Timer();
	private boolean flag = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȥ����Ϣ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// ǿ��Ϊ����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.amdisplay);

		dispanim();
	}

	public void dispanim() {
		AM1 = AnimationUtils.loadAnimation(this, R.anim.animation1);
		AM1duration = AM1.getDuration();

		Intent intent = new Intent();

		LinearLayout amlayout = (LinearLayout) findViewById(R.id.amdisplaylayout);
		animationText = new AutoResizeTextView(AMdisplay.this);
		myAMPreference = AMPreference.instance(getBaseContext());
		animationtype = myAMPreference.getAMtype();

		// ��ȡ���ö������ٶ�
		animationspeed = myAMPreference.getAMspeed();
		// ��ȡ������ɫ
		animationfontcolor = myAMPreference.getAMfontcolor();
		// ��ȡ������ɫ
		animationbackgroundcolor = myAMPreference.getAMbackgroundcolor();
		// ��ȡ��ʾ�ַ�
		animationstring = myAMPreference.getAMtext().toString();

		animationText.setGravity(Gravity.CENTER);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		final int width = displayMetrics.widthPixels; // ����
		final int height = displayMetrics.heightPixels; // ����
		final int maxLinesCount = 4;
		animationText.setMaxLines(maxLinesCount);
		animationText.setTextSize(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, height, getResources()
						.getDisplayMetrics()));
		animationText.setEllipsize(TruncateAt.END);
		// since we use it only once per each click, we don't need to cache the
		// results, ever
		animationText.setEnableSizeCache(false);
		animationText.setEnableSizeCache(false);
		animationText.setLayoutParams(new LayoutParams(width, height));
		animationText.setText(animationstring);
		// ���ñ�����ɫ
		amlayout.setBackgroundColor(Integer.parseInt(animationbackgroundcolor));
		// ����������ɫ
		animationText.setTextColor(Integer.parseInt(animationfontcolor));
		amlayout.addView(animationText);

		AMspeedlevel = Integer.parseInt(animationspeed);

		intent.putExtra("str", animationstring);
		intent.putExtra("color", animationfontcolor);
		intent.putExtra("bcolor", animationbackgroundcolor);
		if (AMspeedlevel >= 1 && AMspeedlevel <= 4) {
			intent.putExtra("speed", "1");
		} else if (AMspeedlevel > 4 && AMspeedlevel <= 8) {
			intent.putExtra("speed", "2");
		} else if (AMspeedlevel > 8 && AMspeedlevel <= 12) {
			intent.putExtra("speed", "3");
		} else if (AMspeedlevel > 12 && AMspeedlevel <= 16) {
			intent.putExtra("speed", "4");
		} else if (AMspeedlevel > 16 && AMspeedlevel <= 20) {
			intent.putExtra("speed", "5");
		} else {
			intent.putExtra("speed", "3");
		}

		if (!animationspeed.equals("0")) {

			if (!animationtype.equals("1")) {

				if (animationtype.equals("0")) {
					AM1.setDuration(((20 - AMspeedlevel) * AM1duration) / 20);
					animationText.startAnimation(AM1);
				} else {
					timer.schedule(task, 1000, 1000); // 1s��ִ��task,����1s�ٴ�ִ��
				}

			} else {
				intent.setClass(AMdisplay.this,
						com.example.textam.MarqueeMainActivity.class);
				startActivity(intent);
				finish();
			}
		} else {
			if(!animationtype.equals("1") && !animationtype.equals("0")){
				timer.schedule(task, 1000, 1000);
			}
			else{
				if (AM1.hasStarted())
					animationText.clearAnimation();
			}
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.DropOut).duration(500)
						.playOn(animationText);
				break;
			case 2:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.Landing).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.TakingOff).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 3:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.Pulse).duration(500)
						.playOn(animationText);
				break;
			case 4:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.RubberBand).duration(500)
						.playOn(animationText);

				break;
			case 5:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.Shake).duration(500)
						.playOn(animationText);
				break;
			case 6:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.Swing).duration(500)
						.playOn(animationText);

				break;
			case 7:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.Wobble).duration(500)
						.playOn(animationText);
				break;
			case 8:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.Bounce).duration(500)
						.playOn(animationText);
				break;

			case 9:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.Tada).duration(500)
						.playOn(animationText);
				break;
			case 10:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.Wave).duration(500)
						.playOn(animationText);
				break;
			case 11:
				if (rope != null) {
					rope.stop(true);
				}

				rope = YoYo.with(Techniques.Hinge).duration(500)
						.playOn(animationText);

				break;
			case 12:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.RollIn).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.RollOut).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 13:
				if (rope != null) {
					rope.stop(true);
				}
				rope = YoYo.with(Techniques.BounceIn).duration(500)
						.playOn(animationText);

				break;
			case 14:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.BounceInDown).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.BounceInUp).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 15:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.BounceInLeft).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.BounceInRight).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;

			case 16:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.FadeIn).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.FadeOut).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 17:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.FadeInDown).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.FadeOutDown).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 18:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.FadeInLeft).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.FadeOutRight).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 19:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.FadeInRight).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.FadeOutLeft).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 20:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.FadeInUp).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.FadeOutUp).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 21:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.RotateIn).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.RotateOut).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 22:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.RotateInDownLeft).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.RotateOutDownLeft)
							.duration(500).playOn(animationText);
					flag = true;
				}
				break;
			case 23:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.RotateInDownRight)
							.duration(500).playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.RotateOutDownRight)
							.duration(500).playOn(animationText);
					flag = true;
				}
				break;
			case 24:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.RotateInUpLeft).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.RotateOutUpLeft).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 25:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.RotateInUpRight).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.RotateOutUpRight).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 26:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.SlideInDown).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.SlideOutDown).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 27:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.SlideInRight).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.SlideOutLeft).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 28:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.SlideInLeft).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.SlideOutRight).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 29:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.SlideInUp).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.SlideOutUp).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 30:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.ZoomIn).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.ZoomOut).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 31:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.ZoomInDown).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.ZoomOutDown).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 32:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.ZoomInRight).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.ZoomOutLeft).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 33:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.ZoomInLeft).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.ZoomOutRight).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;
			case 34:
				if (rope != null) {
					rope.stop(true);
				}
				if (flag) {
					rope = YoYo.with(Techniques.ZoomInUp).duration(500)
							.playOn(animationText);
					flag = false;
				} else {
					rope = YoYo.with(Techniques.ZoomOutUp).duration(500)
							.playOn(animationText);
					flag = true;
				}
				break;

			}
		}
	};

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = Integer.parseInt(animationtype) - 1;
			handler.sendMessage(msg);

		}
	};

	public void onPause() {
		super.onPause();
		if (rope != null) {
			rope.stop(true);
		}
		MobclickAgent.onPause(this);// umeng
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);//umeng
	}

}
