package me.jimwind.laboratory.android.activity.trywidget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import me.jimwind.laboratory.R;
import me.jimwind.laboratory.android.activity.ui.BaseActivity;
import me.jimwind.laboratory.android.view.MyScrollLayout;

public class TryViewGroupScroll extends BaseActivity {
	private MyScrollLayout mMyScrollLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.try_viewgroup_scroll);
		mMyScrollLayout = (MyScrollLayout)findViewById(R.id.myscrolllayout);
		mMyScrollLayout.setScrollLayoutParams(0.7f, widthScreen);
		for(int i=0; i<8; i++){
	        TextView nameTextView = new TextView(this);
            RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams((int)(widthScreen * 0.7f), RelativeLayout.LayoutParams.WRAP_CONTENT);
            nameParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            nameTextView.setLayoutParams(nameParams);
            nameTextView.setTextSize(20);
            nameTextView.setPadding(25, 15, 25, 0);
            nameTextView.setText("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
            		"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
            		"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
            		"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ "+i);
            if(i % 2 == 0)
            	nameTextView.setBackgroundColor(0xff63a309);
            
            mMyScrollLayout.addView(nameTextView);
		}
	}



	class MyViewGroup extends ViewGroup {

		public MyViewGroup(Context context) {
			super(context);
			Button button1 = new Button(context);
			button1.setText("button1");

			Button button2 = new Button(context);
			button2.setText("button2");

			TextView textView = new TextView(context);
			textView.setText("textView");

			addView(button1);
			addView(button2);
			addView(textView);

			mScroller = new Scroller(context);
		}

		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			int childCount = getChildCount();
			int left = 0;
			int top = 10;
			for (int i = 0; i < childCount; i++) {
				View child = getChildAt(i);
				child.layout(left, top, left + 60, top + 60);
				top += 70;
			}

		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

			int childCount = getChildCount();
			// 设置该ViewGroup的大小
			int specSize_width = MeasureSpec.getSize(widthMeasureSpec);
			int specSize_height = MeasureSpec.getSize(heightMeasureSpec);
			setMeasuredDimension(specSize_width, specSize_height);

			for (int i = 0; i < childCount; i++) {
				View childView = getChildAt(i);
				childView.measure(80, 80);
			}
		}

		private float mLastMotionY = 0;
		private int move = 0;
		private int offset = 0;

		public boolean onTouchEvent(MotionEvent ev) {
			final float y = ev.getY();
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!mScroller.isFinished()) {
					mScroller.forceFinished(true);
					move = mScroller.getFinalY();
				}
				mLastMotionY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				int detaY = (int) (mLastMotionY - y);
				mLastMotionY = y;
				offset += detaY;
				scrollBy(0, detaY);
				break;
			case MotionEvent.ACTION_UP:

				break;
			}
			return false;
		}

		private Scroller mScroller;

		public void computeScroll() {
			if (mScroller.computeScrollOffset()) {
				scrollTo(0, mScroller.getCurrY());
				// view一旦重绘就会回调onDraw，onDraw中又会调用computeScroll，不停绘制
				postInvalidate();
			}
		}
	}
}
