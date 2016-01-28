package com.example.textam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

public class colorimage extends Activity {
	private int backgroundcolor = Color.GREEN;
	private int animationspeed = 500;
	private Animation AM1;
	private long AM1duration;
	private AlertDialog dialog;
	private View dialogView;
	private TextView shouWidth;
	private SeekBar widthSb;
	private LinearLayout amlayout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȥ����Ϣ��
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// ǿ��Ϊ����
		initv();

	}

	public void initv() {
		Toast.makeText(this, R.string.menunotice, Toast.LENGTH_LONG).show();
		AM1 = AnimationUtils.loadAnimation(this, R.anim.animation1);
		AM1duration = AM1.getDuration();

		dialogView = getLayoutInflater().inflate(R.layout.dialog_width_set,
				null);
		shouWidth = (TextView) dialogView.findViewById(R.id.textView1);
		widthSb = (SeekBar) dialogView.findViewById(R.id.dialogseekBar);
		widthSb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				shouWidth.setText(getResources().getString(
						R.string.currentwidth)
						+ (progress + 1));
				animationspeed = (11 - progress)*100;
				showanim();
			}
		});
		dialog = new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle(getResources().getString(R.string.flashspeedsettitle))
				.setView(dialogView)
				.setPositiveButton(getResources().getString(R.string.okbt),
						new OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
							}
						})
				.setNegativeButton(getResources().getString(R.string.cancelbt),
						null).create();

		setContentView(R.layout.colorimage);
		amlayout = (LinearLayout) findViewById(R.id.colordisplay);
		showanim();
	}

	public void showanim() {
		// ���ñ�����ɫ
		amlayout.setBackgroundColor(backgroundcolor);
		if (animationspeed != 0) {
			AM1.setDuration(animationspeed);
			amlayout.startAnimation(AM1);
		} else {
			if (AM1.hasStarted())
				amlayout.clearAnimation();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN || keyCode==KeyEvent.KEYCODE_VOLUME_UP){
			openOptionsMenu();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu colorSm = menu.addSubMenu(1, 1, 1,
				getResources().getString(R.string.colorchoice));
		colorSm.add(2, 200, 200, getResources().getString(R.string.redcolor));
		colorSm.add(2, 210, 210, getResources().getString(R.string.greencolor));
		colorSm.add(2, 220, 220, getResources().getString(R.string.bluecolor));
		colorSm.add(2, 230, 230, getResources().getString(R.string.zisecolor));
		colorSm.add(2, 240, 240, getResources().getString(R.string.yellowcolor));
		colorSm.add(2, 250, 250, getResources().getString(R.string.blackcolor));
		menu.add(1, 2, 2, getResources().getString(R.string.flashspeedsettitle));

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int index = item.getItemId();
		switch (index) {
		case 200:
			backgroundcolor = Color.RED;
			break;
		case 210:
			backgroundcolor = Color.GREEN;
			break;
		case 220:
			backgroundcolor = Color.BLUE;
			break;
		case 230:
			backgroundcolor = Color.MAGENTA;
			break;
		case 240:
			backgroundcolor = Color.YELLOW;
			break;
		case 250:
			backgroundcolor = Color.BLACK;
			break;
		case 2:
			dialog.show();
			break;
		}
		showanim();
		return true;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
