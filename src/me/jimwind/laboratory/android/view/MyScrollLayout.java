package me.jimwind.laboratory.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/*
 * 一个横向滚动的控件，为了能显示前一个和后一个的一部分，设置View的宽度为屏幕宽度的70%，这样，左右各有15%显示前一个和后一个
 * 关于两个View的间隙由各个View用setPadding来实现
 */
public class MyScrollLayout extends ViewGroup {

	private int curScreen;
	private int defaultScreen = 0;
	private Scroller mScroller;
	private float mLastMotionX = 0;

	private VelocityTracker mVelocityTracker;

	private int itemViewWidth = 0;//每个itemView的宽度()
	private int mLeft = 0;//当itemViewWidth比屏幕宽度小的时候, itemViewWidth一般居中显示, 根据屏幕宽度和itemViewWidth宽度得出距离左边的间距
	private int mScreenWidth;//屏幕宽度
	/**
	 * percent: 如果想让显示的item view比屏幕小, 则设置百分比, 如0.7就是70%
	 * screenWidth 屏幕宽度, 或者显示控件的宽度
	 */
	public void setScrollLayoutParams(float percent, int screenWidth){
		mScreenWidth = screenWidth;
		if(percent > 0 && percent <=1){
			itemViewWidth = (int)(mScreenWidth * percent);
			mLeft = (mScreenWidth - itemViewWidth) / 2;
		}
	}
	public MyScrollLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public MyScrollLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyScrollLayout(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		curScreen = defaultScreen;
		mScroller = new Scroller(context);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float x = event.getX();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(event);
			}
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			float delt = mLastMotionX - x;
			if (isCanMove((int) delt)) {
				if (mVelocityTracker != null) {
					mVelocityTracker.addMovement(event);
				}
				mLastMotionX = x;
				scrollBy((int) delt, 0);
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
				mVelocityTracker.computeCurrentVelocity(1000);
				float pxsec = mVelocityTracker.getXVelocity();
				if (pxsec > 600 && curScreen > 0) {
					snapToScreen(curScreen - 1);
				} else if (pxsec < -600 && curScreen < getChildCount() - 1) {
					snapToScreen(curScreen + 1);
				} else {
					// 主要是用来获取该滑动到哪个界面，最终调用的是invalid调用draw方法然后draw调用computeScroll方法，然后使用scroller对象
					snapToDestination();
				}

				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			break;

		default:
			break;
		}

		return true;
	}

	private void snapToScreen(int screen) {
		int whichscreen = Math.max(0, Math.min(screen, getChildCount() - 1));
//		if (getScrollX() != (whichscreen * getWidth())) {
//			final int delta = whichscreen * getWidth() - getScrollX();
//			mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
//			curScreen = whichscreen;
//			invalidate();
//		}
		if (getScrollX() != (whichscreen*getItemViewWidth() - mLeft)) {
			final int delta =whichscreen*getItemViewWidth() - mLeft - getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
			curScreen = whichscreen;
			invalidate();
		}
	}

	private void snapToDestination() {
		int screen = (getScrollX() + getItemViewWidth() / 2) / getItemViewWidth();
		snapToScreen(screen);
	}

	private boolean isCanMove(int delta) {
		/*
		 * if(getScrollX()<0 && delat<0){ return false; }
		 */
		if (getScrollX() >= (getChildCount() - 1) * getItemViewWidth() && delta > 0) {
			return false;
		}
		return true;
	}
	public int getItemViewWidth(){
		if(itemViewWidth == 0){
			return getWidth();
		} else {
			return itemViewWidth;
		}
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			int totalHeight = 0;
			int totalwidth = 0;
			int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				View childView = getChildAt(i);
				int childwidth = childView.getMeasuredWidth();
				int childheight = childView.getMeasuredHeight();
				childView.layout(totalwidth, t, totalwidth + childwidth, b);
				totalHeight += childheight;
				totalwidth += childwidth;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}