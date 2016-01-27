package com.example.textam;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AMlist_adapter extends BaseAdapter {
	
	int[] animation_list = {R.string.animation1, R.string.animation2};

	public int getCount() {
		return 2;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView AM_Text = new TextView(parent.getContext());
		AM_Text.setText(animation_list[position]);
		return AM_Text;
	}

}
