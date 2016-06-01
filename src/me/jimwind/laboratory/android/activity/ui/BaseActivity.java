package me.jimwind.laboratory.android.activity.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * @Description
 * @author mi.gao
 * @date  2015-12-17
 */
public abstract class BaseActivity extends FragmentActivity {
	public int widthScreen;// 屏幕宽
	public int heightScreen;// 屏幕高
	public float density;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		widthScreen = display.getWidth();
		heightScreen = display.getHeight();
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		density = metric.density;
	}
}
