package com.example.textam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextView extends TextView implements Runnable {
	// 当前滚动的位置
	private int currentScrollX;
	private static int scrollspeed;
	private boolean isStop = false;
	private int textWidth;
	private boolean isMeasure = false;

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MarqueeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		// TODO Auto-generated method stub
		// 滚动速度
		currentScrollX += scrollspeed*2 + 2;
		scrollTo(currentScrollX, 0);
		if (isStop) {
			return;
		}
		if (getScrollX() >= textWidth) {
			scrollTo(-(this.getWidth()), 0);
			currentScrollX = -(this.getWidth());
			// return;
		}
		postDelayed(this, 5);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (!isMeasure) {
			// 问自己宽度只需获取一次就可以了
			getTextWidth();
			isMeasure = true;
		}
	}

	private void getTextWidth() {
		Paint paint = this.getPaint();
		String str = this.getText().toString();
		textWidth = (int) paint.measureText(str);
	}
	
	public void getScrollSpeed(int speed) {
	    scrollspeed = speed;
	    //if(scrollspeed < 1 || scrollspeed > 5){
	        //scrollspeed = 3;
	    //}
	}

	// 开始滚动
	public void startScroll() {
		isStop = false;
		this.removeCallbacks(this);
		post(this);
	}

	// 停止滚动
	public void stopScroll() {
		isStop = true;
	}

	// 从头开始滚动
	public void startFor0() {
		currentScrollX = 0;
		startScroll();
	}
}
