package me.jimwind.laboratory.android.utils;

import java.io.File;
/*
 * 局部变量，前面加小横线
 */
public class UPFileUtil {
	public static File createDir(String s_dir) {
		File dir = new File(s_dir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
}
