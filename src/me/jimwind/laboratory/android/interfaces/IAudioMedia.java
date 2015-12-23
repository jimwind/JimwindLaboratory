package me.jimwind.laboratory.android.interfaces;

/*
 *  音频媒体接口
 *  录音,播放,停止,暂停
 */
public interface IAudioMedia {
	//用户操作方法
	public void Play();
	public void RecordSound();
	public void Stop();
	public void Pause();
	public void setListener(Listener listener);
	//录音或播放时被动停止或暂停
	public interface Listener{
		public void OnStop();
		public void OnPause();
	}
}
