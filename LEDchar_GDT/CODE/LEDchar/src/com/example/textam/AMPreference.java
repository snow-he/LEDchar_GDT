package com.example.textam;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

public class AMPreference {
	private static AMPreference myAMPreference;
	private static String animation_type = "AMtype";
	private static String animation_text = "AMtext";
	private static String animation_speed = "AMspeed";
	private static String animation_fontsize = "AMfontsize";
	private static String animation_color = "AMcolor";
	private static String animation_bcolor = "AMbcolor";
	private int defcolor = Color.rgb(255, 255, 255);
	private int defbcolor = Color.rgb(190, 52, 225);
	
	protected SharedPreferences mySharePreference;
	private AMPreference(Context context){
		mySharePreference = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public synchronized static AMPreference instance(Context context){
		if(myAMPreference == null){
			myAMPreference = new AMPreference(context);
		}
		return myAMPreference;
	}
	
	public void setAMtype(String animationtype){
		final SharedPreferences.Editor editor = mySharePreference.edit();
		editor.putString(animation_type, animationtype);
		editor.apply();
	}
	
	public void setAMtext(String animationtext){
		final SharedPreferences.Editor editor = mySharePreference.edit();
		editor.putString(animation_text, animationtext);
		editor.apply();
	}
	
	public void setAMspeed(String animationspeed){
		final SharedPreferences.Editor editor = mySharePreference.edit();
		editor.putString(animation_speed, animationspeed);
		editor.apply();	
	}
	
	public void setAMfontsize(String animationfontsize){
		final SharedPreferences.Editor editor = mySharePreference.edit();
		editor.putString(animation_fontsize, animationfontsize);
		editor.apply();	
	}
	
	public void setAMfontcolor(String animationfontcolor){
		final SharedPreferences.Editor editor = mySharePreference.edit();
		editor.putString(animation_color, animationfontcolor);
		editor.apply();	
	}
	
	public void setAMbackgroundcolor(String animationbcolor){
		final SharedPreferences.Editor editor = mySharePreference.edit();
		editor.putString(animation_bcolor, animationbcolor);
		editor.apply();	
	}
	
	public String getAMtype(){
		return mySharePreference.getString(animation_type, "0");
	}
	
	public String getAMtext(){
		return mySharePreference.getString(animation_text, "touch to input");
	}
	
	public String getAMspeed(){
		return mySharePreference.getString(animation_speed, "19");
	}
	
	public String getAMfontsize(){
		return mySharePreference.getString(animation_fontsize, "19");
	}
	
	public String getAMfontcolor(){
		return mySharePreference.getString(animation_color, String.valueOf(defcolor));
	}
	
	public String getAMbackgroundcolor(){
		return mySharePreference.getString(animation_bcolor, String.valueOf(defbcolor));
	}
	

}
