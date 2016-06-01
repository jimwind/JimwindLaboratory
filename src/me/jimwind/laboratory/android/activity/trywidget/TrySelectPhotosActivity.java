package me.jimwind.laboratory.android.activity.trywidget;

import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.ui.BaseActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
/*
 *  仿微信修改头像时选照片
 *  仿微信发送图片时选照片
 */
public class TrySelectPhotosActivity extends BaseActivity implements
		OnClickListener {
	private GridView mGridView;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_photos);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
