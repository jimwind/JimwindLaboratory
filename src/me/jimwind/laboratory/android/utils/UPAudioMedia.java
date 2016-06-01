package me.jimwind.laboratory.android.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.jimwind.laboratory.android.config.SystemConstants;
import me.jimwind.laboratory.android.interfaces.IAudioMedia;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.util.Log;
/*
 * 参考 http://blog.sina.com.cn/s/blog_4c070656010127tn.html
 * 
 */
public class UPAudioMedia implements IAudioMedia, OnCompletionListener {
	private String mRecordFileDir = SystemConstants.APP_SDCARD_PATH+"records/";
	private String mRecordFilePath = "";
	private boolean isRecording = false;
	private Listener mListener;
	public UPAudioMedia(){
		UPFileUtil.createDir(mRecordFileDir);
	}
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mListener.onStop();
	}

	@Override
	public void Play() {
		// TODO Auto-generated method stub
		File file = new File(mRecordFilePath);
		int musicLength = (int)(file.length() / 2);
		short[] music = new short[musicLength];
		try {
			InputStream is = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			DataInputStream dis = new DataInputStream(bis);
			int i=0;
			while (dis.available() > 0){
				music[i] = dis.readShort();
				i++;
			}
			dis.close();
			AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
													11025,
													AudioFormat.CHANNEL_CONFIGURATION_MONO,
													AudioFormat.ENCODING_PCM_16BIT,
													musicLength * 2,
													AudioTrack.MODE_STREAM);
			audioTrack.play();
			audioTrack.write(music, 0, musicLength);
			audioTrack.stop();
		} catch (Throwable t){
			Log.e("jimwind","playback failed");
		}
	}

	@Override
	public void RecordSound() {
		mRecordFilePath = mRecordFileDir + UPTimeUtil.getCurrentTime(UPTimeUtil.SDF_filename)+".pcm";
		Thread thread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				record();
			}
			
		});
		thread.start();
	}

	@Override
	public void Stop() {
		// TODO Auto-generated method stub
		isRecording = false;
	}

	@Override
	public void Pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListener(Listener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
	}
	private void record(){
		// TODO Auto-generated method stub
		int frequency = 11025;
		int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
		int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
		File file = new File(mRecordFilePath);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e){
			throw new IllegalStateException("Failed to create "+mRecordFilePath);
		}
		
		try {
			OutputStream os = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			DataOutputStream dos = new DataOutputStream(bos);
			
			int bufferSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
			AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
														frequency, channelConfiguration,
														audioEncoding, bufferSize);
			short[] buffer = new short[bufferSize];
			audioRecord.startRecording();
			isRecording = true;
			while (isRecording){
				int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
				for(int i=0; i<bufferReadResult; i++){
					dos.writeShort(buffer[i]);
				}
			}
			audioRecord.stop();
			dos.close();
		} catch (Throwable t){
			Log.e("jimwind","Recording Failed");
		}
	}
	@Override
	public void Cancel() {
		// TODO Auto-generated method stub
		
	}

}
