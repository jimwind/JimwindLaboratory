package me.jimwind.laboratory.android.utils;

import java.io.File;
/*
 */
public class UPFileUtil {
	public static File createDir(String s_dir) {
		File dir = new File(s_dir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile()) {
			if (file.exists()) {
				file.delete();
			}
		}
	}
}
