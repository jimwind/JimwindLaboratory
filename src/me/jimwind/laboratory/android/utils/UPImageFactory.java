package me.jimwind.laboratory.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import me.jimwind.laboratory.android.config.SystemConstants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//http://blog.csdn.net/jdsjlzx/article/details/44228935

public class UPImageFactory {
	public void getImage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		newOpts.inJustDecodeBounds = false;

		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		float h2 = 1280f;
		float w2 = 768f;

		int be = 1;
		if (w > h && w > w2) {// 横向图
			be = (int) (newOpts.outWidth / w2);
		} else if (w < h && h > h2) {// 纵向图
			be = (int) (newOpts.outHeight / h2);
		}
		if (be <= 0) {
			be = 1;
		}
		newOpts.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		compressImage(bitmap);
	}

	private void compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int quality = 100;
		@SuppressWarnings("unused")
		int length = baos.toByteArray().length;
		while (baos.toByteArray().length / 1024 > 1024) {
			baos.reset();
			quality -= 10;
			image.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		}
		@SuppressWarnings("unused")
		int length2 = baos.toByteArray().length;
		String upload_imgpath = SystemConstants.APP_SDCARD_PATH
				+ "/uploadimg.jpg";
		File file = new File(upload_imgpath);
		if (file.exists()) {
			file.delete();
		}
		try {
			storeImage(image, upload_imgpath, quality);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void storeImage(Bitmap bitmap, String outPath, int quality)
			throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream(outPath);
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
	}
}
