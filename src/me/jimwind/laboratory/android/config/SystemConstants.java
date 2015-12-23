package me.jimwind.laboratory.android.config;

import android.os.Environment;

public class SystemConstants {
	public static final String APP_SDCARD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/jimwind_laboratory/";
}
