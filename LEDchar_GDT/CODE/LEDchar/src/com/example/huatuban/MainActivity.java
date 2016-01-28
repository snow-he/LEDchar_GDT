package com.example.huatuban;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.textam.R;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends Activity {

	/** 画画的内容区 */
	private HuaBanView hbView;
	/** 设置粗细的Dialog */
	private AlertDialog dialog;
	private View dialogView;
	private TextView shouWidth;
	private SeekBar widthSb;
	private int paintWidth;

	private void initView() {
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
				paintWidth = progress + 1;
			}
		});
		hbView = (HuaBanView) findViewById(R.id.huaBanView1);
		dialog = new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle(getResources().getString(R.string.paintwidthsettitle))
				.setView(dialogView)
				.setPositiveButton(getResources().getString(R.string.okbt),
						new OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								hbView.setPaintWidth(paintWidth);
							}
						})
				.setNegativeButton(getResources().getString(R.string.cancelbt),
						null).create();
		Toast.makeText(this, R.string.menunotice, Toast.LENGTH_LONG).show();
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉应用标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
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
		menu.add(1, 2, 2, getResources().getString(R.string.paintsizeset));
		SubMenu widthSm = menu.addSubMenu(1, 3, 3,
				getResources().getString(R.string.paintstyleset));
		widthSm.add(3, 300, 300, getResources().getString(R.string.linepaint));
		widthSm.add(3, 301, 301, getResources()
				.getString(R.string.tianconpaint));
		menu.add(1, 4, 4, getResources().getString(R.string.clean));
		menu.add(1, 5, 5, getResources().getString(R.string.savedraw));
		menu.add(1, 6, 6, getResources().getString(R.string.exitdraw));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int index = item.getItemId();
		switch (index) {
		case 200:
			hbView.setColor(Color.RED);
			break;
		case 210:
			hbView.setColor(Color.GREEN);
			break;
		case 220:
			hbView.setColor(Color.BLUE);
			break;
		case 230:
			hbView.setColor(Color.MAGENTA);
			break;
		case 240:
			hbView.setColor(Color.YELLOW);
			break;
		case 250:
			hbView.setColor(Color.BLACK);
			break;
		case 2:
			dialog.show();
			break;
		case 300:
			hbView.setStyle(HuaBanView.PEN);
			break;
		case 301:
			hbView.setStyle(HuaBanView.PAIL);
			break;
		case 4:
			hbView.clearScreen();
			break;
		case 5:
			if (SaveViewUtil.saveScreen(hbView)) {
				Toast.makeText(this,
						getResources().getString(R.string.savesuccess), 0)
						.show();
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.savefailed), 0)
						.show();
			}
			break;
		case 6:
			finish();
			break;
		}
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
