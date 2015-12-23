package me.jimwind.laboratory.android.activity.trywidget;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.ui.BaseActivity;
import me.jimwind.laboratory.android.view.SoundRecoder;

public class TrySoundRecorder extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound_recorder);
		TextView sound_recorder = (TextView)findViewById(R.id.sound_recorder);
		sound_recorder.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SoundRecoder sound_recorder_view = (SoundRecoder)findViewById(R.id.sound_recorder_view);
				sound_recorder_view.setVisibility(View.VISIBLE);
			}
		});

	}
}
