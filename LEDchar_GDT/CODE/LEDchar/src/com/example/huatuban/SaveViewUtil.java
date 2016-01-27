package com.example.huatuban;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.view.View;

public class SaveViewUtil {
	
	private static final File rootDir = new File(Environment.getExternalStorageDirectory()+File.separator+"huaban/");

	/**保存截图的方法*/
	public static boolean saveScreen(View view){
		//判断sdcard是否可用
		if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			return false;
		}
		if(!rootDir.exists()){
			rootDir.mkdir();
		}
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		try {
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(new File(rootDir,System.currentTimeMillis()+".jpg")));
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}finally{
			view.setDrawingCacheEnabled(false);
			bitmap = null;
		}
	}
}
