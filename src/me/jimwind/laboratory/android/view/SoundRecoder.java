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
	private final int RECORD_START = RECORD_INIT + 1;
	private final int RECORD_STOP = RECORD_INIT + 2;
	private final int RECORD_PLAY = RECORD_INIT + 3;
	private int mRecordState = RECORD_INIT;
	private View mParentView;
	private Context mContext;
	private IAudioMedia mAudioMedia;
	
	private TextView sound_record_center_btn;
	private TextView sound_record_cancel_btn;
	private TextView sound_record_reset_btn;
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
		sound_record_reset_btn = (TextView)mParentView.findViewById(R.id.sound_record_reset_btn);
		sound_record_save_btn = (TextView)mParentView.findViewById(R.id.sound_record_save_btn);
	}
	private void setListener(){
		sound_record_center_btn.setOnClickListener(this);
		sound_record_cancel_btn.setOnClickListener(this);
		sound_record_reset_btn.setOnClickListener(this);
		sound_record_save_btn.setOnClickListener(this);
		
	}
	private void init(){
		//当前View如果更换MyAudioMedia，即可更换多媒体实现方式，而其它实现方式都是实现了接口IAudioMedia的。
		mAudioMedia = new MyAudioMedia();
		mAudioMedia.setListener(new Listener(){

			@Override
			public void OnStop() {
				// TODO Auto-generated method stub
				mRecordState = RECORD_STOP;
				setButtonState(mRecordState);
			}

			@Override
			public void OnPause() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	private void setButtonState(int status){
		switch(status){
		case RECORD_START:{
			sound_record_center_btn.setBackgroundResource(R.drawable.sound_record_stop_img);
		}
			break;
		case RECORD_STOP:{
			sound_record_center_btn.setBackgroundResource(R.drawable.sound_record_play_img);
		}
			break;
		case RECORD_PLAY:{
			sound_record_center_btn.setBackgroundResource(R.drawable.sound_record_stop_img);
		}
			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.sound_record_center_btn:{
			if(mRecordState == RECORD_INIT){
				mRecordState = RECORD_START;
				mAudioMedia.RecordSound();
				Log.e("jimwind","start record ...");
			} else if (mRecordState == RECORD_START){
				mRecordState = RECORD_STOP;
				mAudioMedia.Stop();
				Log.e("jimwind","record done ...");
			} else if (mRecordState == RECORD_STOP){
				mRecordState = RECORD_PLAY;
				mAudioMedia.Play();
				Log.e("jimwind","start play ...");
			} else if (mRecordState == RECORD_PLAY){
				mRecordState = RECORD_STOP;
				mAudioMedia.Stop();
				Log.e("jimwind","stop play ...");
			}
			setButtonState(mRecordState);
		}
			break;
		case R.id.sound_record_cancel_btn:{
			
		}
			break;
		case R.id.sound_record_reset_btn:{
			
		}
			break;
		case R.id.sound_record_save_btn:{
			
		}
			break;
		}
	}

}
