package me.jimwind.laboratory.android.activity.ui;

import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.trywidget.CompressImage;
import me.jimwind.laboratory.android.activity.trywidget.TryListViewActivity;
import me.jimwind.laboratory.android.activity.trywidget.TrySoundRecorder;
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
		//Intent intent = new Intent(MainActivity.this,CompressImage.class);
		//Intent intent = new Intent(MainActivity.this,TrySoundRecorder.class);
		Intent intent = new Intent(MainActivity.this,TryListViewActivity.class);
		startActivity(intent);
	}
}
