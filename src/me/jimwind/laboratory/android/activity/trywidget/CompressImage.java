package me.jimwind.laboratory.android.activity.trywidget;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.ui.BaseActivity;
import me.jimwind.laboratory.android.config.SystemConstants;
import me.jimwind.laboratory.android.utils.UPImageFactory;

//http://blog.csdn.net/feng88724/article/details/6170955

public class CompressImage extends BaseActivity {
	private final int REQUEST_SELECT_IMAGE = 1;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.try_compress_image);
		Button button = (Button) findViewById(R.id.btn_select_image);
		button.setOnClickListener(BtnOnClick);
	}

	Button.OnClickListener BtnOnClick = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_select_image: {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, REQUEST_SELECT_IMAGE);
			}
				break;
			}
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_SELECT_IMAGE
				&& resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			ContentResolver cr = this.getContentResolver();
			try {
				final Bitmap bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(uri));
				ImageView imageView = (ImageView) findViewById(R.id.image);
				imageView.setImageBitmap(bitmap);

				final UPImageFactory factory = new UPImageFactory();
				((Activity) mContext).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
						try {
							factory.compressAndGenImage(bitmap,
									SystemConstants.APP_SDCARD_PATH
											+ "/compress.jpg", 1024);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			} catch (FileNotFoundException e) {

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
