package me.jimwind.laboratory.android.view;


import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.interfaces.IAudioMedia;
import me.jimwind.laboratory.android.interfaces.IAudioMedia.Listener;
import me.jimwind.laboratory.android.utils.MyAudioMedia;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SoundRecoder extends RelativeLayout implements OnClickListener {
	private final int RECORD_INIT = 0;
	//录音，录音暂停，录音播放，录音播放暂停
	//取消，重新录音，录音完成，录音保存(用于回调)
	private final int RECORD_START = RECORD_INIT + 1;
	private final int RECORD_PAUSE = RECORD_INIT + 2;
	private final int RECORD_STOP = RECORD_INIT + 3;
	private final int RECORD_PLAY = RECORD_INIT + 4;
	private int mRecordState = RECORD_INIT;
	private View mParentView;
	private Context mContext;
	private IAudioMedia mAudioMedia;
	
	private TextView sound_record_center_btn;
	private TextView sound_record_cancel_btn;
	private View sound_record_line1;
	private TextView sound_record_finish_btn;
	private View sound_record_line2;
	private TextView sound_record_reset_btn;
	private View sound_record_line3;
	private TextView sound_record_save_btn;

	public SoundRecoder(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public SoundRecoder(Context context, AttributeSet attrs) {
		super(context, attrs,0);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		mParentView = LayoutInflater.from(context).inflate(
				R.layout.sound_recorder, null);
		this.addView(mParentView);
		loadViewLayout();
		setListener();
		init();
	}
	public SoundRecoder(Context context, AttributeSet attrs,int defStyle) {
		// TODO Auto-generated constructor stub
		super(context, attrs, defStyle);
	}
	private void loadViewLayout() {
		sound_record_center_btn = (TextView)mParentView.findViewById(R.id.sound_record_center_btn);
		
		sound_record_cancel_btn = (TextView)mParentView.findViewById(R.id.sound_record_cancel_btn);
		sound_record_line1 = mParentView.findViewById(R.id.sound_record_line1);
		sound_record_finish_btn = (TextView)mParentView.findViewById(R.id.sound_record_finish_btn);
		sound_record_line2 = mParentView.findViewById(R.id.sound_record_line2);
		sound_record_reset_btn = (TextView)mParentView.findViewById(R.id.sound_record_reset_btn);
		sound_record_line3 = mParentView.findViewById(R.id.sound_record_line3);
		sound_record_save_btn = (TextView)mParentView.findViewById(R.id.sound_record_save_btn);
		
	}
	private void setListener(){
		sound_record_center_btn.setOnClickListener(this);
		
		sound_record_cancel_btn.setOnClickListener(this);
		sound_record_finish_btn.setOnClickListener(this);
		sound_record_reset_btn.setOnClickListener(this);
		sound_record_save_btn.setOnClickListener(this);
		
		
	}
	private void init(){
		//当前View如果更换MyAudioMedia，即可更换多媒体实现方式，而其它实现方式都是实现了接口IAudioMedia的。
		mAudioMedia = new MyAudioMedia();
		mAudioMedia.setListener(new Listener(){

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
				mRecordState = RECORD_STOP;
				SwitchViewByState(mRecordState);
			}

			@Override
			public void onPause() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	private void SwitchViewByState(int status){
		switch(status){
		case RECORD_START:{
			sound_record_center_btn.setBackgroundResource(R.drawable.sound_record_start_img);
			sound_record_center_btn.setText("暂停");
			setButtonVisible(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		}
			break;
		case RECORD_PAUSE:{
			sound_record_center_btn.setBackgroundResource(R.drawable.sound_record_start_img);
			sound_record_center_btn.setText("继续");
			setButtonVisible(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE);
		}
			break;
		case RECORD_STOP:{
			sound_record_center_btn.setBackgroundResource(R.drawable.sound_record_play_img);
			sound_record_center_btn.setText("");
			setButtonVisible(View.VISIBLE, View.GONE, View.VISIBLE, View.VISIBLE);
		}
			break;
		case RECORD_PLAY:{
			sound_record_center_btn.setBackgroundResource(R.drawable.sound_record_stop_img);
			sound_record_center_btn.setText("");
			setButtonVisible(View.GONE, View.GONE, View.VISIBLE, View.VISIBLE);
		}
			break;
		case RECORD_INIT:{
			sound_record_center_btn.setBackgroundResource(R.drawable.sound_record_start_img);
			sound_record_center_btn.setText("");
			setButtonVisible(View.VISIBLE, View.GONE, View.GONE, View.GONE);
		}
			break;
		}
	}
	public void setButtonVisible(int cancel_visibility, int finish_visibility, int reset_visibility, int save_visibility){
		sound_record_cancel_btn.setVisibility(cancel_visibility);
		
		sound_record_line1.setVisibility(finish_visibility);
		sound_record_finish_btn.setVisibility(finish_visibility);
		
		sound_record_line2.setVisibility(reset_visibility);
		sound_record_reset_btn.setVisibility(reset_visibility);
		
		sound_record_line3.setVisibility(save_visibility);
		sound_record_save_btn.setVisibility(save_visibility);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.sound_record_center_btn:{
			if(mRecordState == RECORD_INIT || mRecordState == RECORD_PAUSE){
				mRecordState = RECORD_START;
				mAudioMedia.RecordSound();
				Log.e("jimwind","start record ...");
			} else if (mRecordState == RECORD_START){
				mRecordState = RECORD_PAUSE;
				mAudioMedia.Pause();
				Log.e("jimwind","record pause ...");
			} else if (mRecordState == RECORD_STOP){
				mRecordState = RECORD_PLAY;
				mAudioMedia.Play();
				Log.e("jimwind","start play ...");
			} else if (mRecordState == RECORD_PLAY){
				mRecordState = RECORD_STOP;
				mAudioMedia.Stop();
				Log.e("jimwind","stop play ...");
			}
			SwitchViewByState(mRecordState);
		}
			break;
		case R.id.sound_record_cancel_btn:{
			if(mRecordState == RECORD_INIT){
			} else {
				mAudioMedia.Cancel();
			}
			this.setVisibility(View.GONE);
		}
			break;
		case R.id.sound_record_reset_btn:{
			mAudioMedia.Cancel();
			mRecordState = RECORD_INIT;
			SwitchViewByState(mRecordState);
		}
			break;
		case R.id.sound_record_save_btn:{
			
		}
			break;
		case R.id.sound_record_finish_btn:{
			if(mRecordState == RECORD_START || mRecordState == RECORD_PAUSE){
				mRecordState = RECORD_STOP;
				mAudioMedia.Stop();
				SwitchViewByState(mRecordState);
			}
		}
			break;
		}
	}

}
