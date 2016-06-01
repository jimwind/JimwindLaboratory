package me.jimwind.laboratory.android.activity.trywidget;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.ui.BaseActivity;

public class TryHorizontalScrollView extends BaseActivity {
	private LinearLayout mLinearLayout;
	public final String APP_SDCARD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/up360_teacher_school/camera/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.try_horizontalscrollview);
		mLinearLayout = (LinearLayout) findViewById(R.id.mygallery);
		File photoFiles = new File(APP_SDCARD_PATH);
		for (File photoFile : photoFiles.listFiles()) {
			mLinearLayout.addView(getImageView(photoFile.getAbsolutePath()));
		}
	}

	private View getImageView(String absolutePath) {
		int picWidth = (int)(widthScreen * 0.7f);
		int layoutWidth = (int)(widthScreen * 0.8f);
		Bitmap bitmap = decodeBitmapFromFile(absolutePath, picWidth, 200);
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(layoutWidth, 250));
		layout.setGravity(Gravity.CENTER);

		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(new LayoutParams(picWidth, 200));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bitmap);
		layout.addView(imageView);

		return layout;
	}

	private Bitmap decodeBitmapFromFile(String absolutePath, int reqWidth,
			int reqHeight) {
		Bitmap bm = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(absolutePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(absolutePath, options);

		return bm;
	}

	private int calculateInSampleSize(Options options, int reqWidth,
			int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

}
