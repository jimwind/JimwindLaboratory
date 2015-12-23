package me.jimwind.laboratory.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UPTimeUtil {
	public final static SimpleDateFormat SDF_y_M_d_H_m_s = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat SDF_filename = new SimpleDateFormat(
			"yyyy_MM_dd_HH_mm_ss");
	public static String getCurrentTime(SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(System.currentTimeMillis()));
	}
}
