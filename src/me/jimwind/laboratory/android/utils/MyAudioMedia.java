package me.jimwind.laboratory.android.utils;

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
		mListener.OnStop();
	}
	@Override
	public void Pause() {
		// TODO Auto-generated method stub
		
	}
	public void setListener(Listener listener){
		mListener = listener;
	}
}
