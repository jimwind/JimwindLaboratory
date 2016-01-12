package me.jimwind.laboratory.android.activity.trywidget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import me.jimwind.laboratory.android.activity.ui.BaseActivity;

public class TryFileApi extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	//创建的文件保存在/data/data/<package name>/files/下
	public String read(){
		try {
			FileInputStream inStream = this.openFileInput("message.txt");
			byte[] buffer = new byte[1024];
			int hasRead = 0;
			StringBuilder sb = new StringBuilder();
			while((hasRead = inStream.read(buffer)) != -1){
				sb.append(new String(buffer, 0, hasRead));
			}
			inStream.close();
			return sb.toString();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void write(String msg){
		if(msg == null) return;
		try{
			FileOutputStream fos = this.openFileOutput("message.txt", MODE_APPEND);
			fos.write(msg.getBytes());
			fos.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/* 读写SD卡中的文件
	<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	*/
	private final String DIR = "jimwind_laboratory";
	private final String FILENAME = "try_read_sd.txt";
	public String read_SD(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File file = new File(Environment.getExternalStorageDirectory().toString()
										+ File.separator
										+ DIR
										+ File.separator
										+ FILENAME);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			Scanner scan = null;
			StringBuilder sb = new StringBuilder();
			try {
				scan = new Scanner(new FileInputStream(file));
				while (scan.hasNext()){
					sb.append(scan.next() +"\n");
				}
				return sb.toString();
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				if (scan != null){
					scan.close();
				}
			}
		} else {
			Toast.makeText(this, "读取失败,SD卡不存在", Toast.LENGTH_LONG).show();
		}
		return null;
	}
	public void write_SD(String content){
		if( Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File file = new File(Environment.getExternalStorageDirectory().toString()
					+ File.separator
					+ DIR
					+ File.separator
					+ FILENAME);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			PrintStream out = null;
			try {
				out = new PrintStream(new FileOutputStream(file, true));
				out.println(content);
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				if ( out != null){
					out.close();
				}
			}
		} else {
			Toast.makeText(this, "保存失败,SD卡不存在", Toast.LENGTH_LONG).show();
		}
	}
	
}
