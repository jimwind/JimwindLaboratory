package me.jimwind.laboratory.android.activity.ui;

import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.trywidget.CompressImage;
import android.content.Intent;
import android.os.Bundle;

/**
 * @Description
 * @author mi.gao
 * @date  2015-12-17
 */
public class MainActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent(MainActivity.this,CompressImage.class);
		startActivity(intent);
	}
}
