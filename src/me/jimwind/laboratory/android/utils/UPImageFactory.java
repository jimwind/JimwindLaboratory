package me.jimwind.laboratory.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

//http://blog.csdn.net/jdsjlzx/article/details/44228935

public class UPImageFactory {
	public Bitmap getBitmap(String imgPath){
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = false;
		newOpts.inPurgeable = true;
		newOpts.inInputShareable = true;
		newOpts.inSampleSize = 1;
		newOpts.inPreferredConfig = Config.RGB_565;
		return BitmapFactory.decodeFile(imgPath, newOpts);
	}
	public void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException{
		FileOutputStream os = new FileOutputStream(outPath);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
	}
	public void compressAndGenImage(Bitmap image, String outPath, int maxSize/*KB*/) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int options = 100;
		image.compress(Bitmap.CompressFormat.JPEG, options, os);
		int length = os.toByteArray().length;
		//picture size is 2.68MB, but length is 7881327. why?
//		while(length / 1024 > maxSize){
//			os.reset();
//			options -= 10;
//			image.compress(Bitmap.CompressFormat.JPEG, options, os);
//		}
		image.compress(Bitmap.CompressFormat.JPEG, 10, os);
		FileOutputStream fos = new FileOutputStream(outPath);
		fos.write(os.toByteArray());
		fos.flush();
		fos.close();
	}
	public void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException{
		compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
		if(needsDelete){
			File file = new File(imgPath);
			if(file.exists()){
				file.delete();
			}
		}
	}
}
