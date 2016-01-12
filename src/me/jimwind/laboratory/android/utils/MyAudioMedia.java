package me.jimwind.laboratory.android.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import me.jimwind.laboratory.android.config.SystemConstants;
import me.jimwind.laboratory.android.interfaces.IAudioMedia;

public class MyAudioMedia implements IAudioMedia, OnCompletionListener{
	private MediaRecorder mMediaRecorder;
	private String mRecordFileDir = SystemConstants.APP_SDCARD_PATH+"records/";
	private String mRecordFilePath = "";
	private final String SUFFIX = ".amr";
	private ArrayList<String> mTempFiles;
	
	private MediaPlayer mMediaPlayer;
	private Listener mListener;
	public MyAudioMedia(){
		UPFileUtil.createDir(mRecordFileDir);
		mTempFiles = new ArrayList<String>();
	}
	@Override
	public void Play() {
		// TODO Auto-generated method stub
		if(mMediaPlayer == null){
			mMediaPlayer = new MediaPlayer();
		}
		mMediaPlayer.setOnCompletionListener(this);
		try {
			mMediaPlayer.setDataSource(mRecordFilePath);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void RecordSound() {
		// TODO Auto-generated method stub
		if(mMediaRecorder == null){
			mMediaRecorder = new MediaRecorder();
		}
		mRecordFilePath = mRecordFileDir + UPTimeUtil.getCurrentTime(UPTimeUtil.SDF_filename)+SUFFIX;
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mMediaRecorder.setOutputFile(mRecordFilePath);
		try{
			mMediaRecorder.prepare();
			mMediaRecorder.start();
		} catch (IllegalStateException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		mTempFiles.add(mRecordFilePath);
	}

	@Override
	public void Stop() {
		// TODO Auto-generated method stub
		if(mMediaRecorder != null){
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder = null;
			//停止录音,合并录音文件
			mergeRecordFiles();
		}
		if(mMediaPlayer != null){
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

	}
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO
		Stop();
		mListener.onStop();
	}
	@Override
	public void Pause() {
		// TODO Auto-generated method stub
		if(mMediaRecorder != null){
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}
	@Override
	public void setListener(Listener listener){
		mListener = listener;
	}
	private void mergeRecordFiles(){
		mRecordFilePath = mRecordFileDir + UPTimeUtil.getCurrentTime(UPTimeUtil.SDF_filename)+SUFFIX;
		File mf = new File(mRecordFilePath);
		FileOutputStream fos = null;
		if(!mf.exists()){
			try{
				mf.createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		try {
			fos = new FileOutputStream(mf);
		} catch (IOException e){
			e.printStackTrace();
		}
		for(int i=0; i<mTempFiles.size(); i++){
			File file = new File(mTempFiles.get(i));
			try{
				FileInputStream fis = new FileInputStream(file);
				byte[] filebyte = new byte[fis.available()];
				int length = filebyte.length;
				if(i == 0){
					while(fis.read(filebyte) != -1){
						fos.write(filebyte, 0, length);
					}
				} else {
					while(fis.read(filebyte) != -1){
						fos.write(filebyte, 6, length - 6);
					}
				}
				fos.flush();
				fis.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		try{
			fos.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		for(int i=0; i<mTempFiles.size(); i++){
			UPFileUtil.deleteFile(mTempFiles.get(i));
		}
	}
	@Override
	public void Cancel() {
		// TODO Auto-generated method stub
		for(int i=0; i<mTempFiles.size(); i++){
			UPFileUtil.deleteFile(mTempFiles.get(i));
		}
		UPFileUtil.deleteFile(mRecordFilePath);
	}
}
