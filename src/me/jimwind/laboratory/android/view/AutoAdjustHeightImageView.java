package me.jimwind.laboratory.android.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class AutoAdjustHeightImageView extends ImageView{
    private int imageWidth;
    private int imageHeight;

    public AutoAdjustHeightImageView(Context context) {
        this(context,null);
    }



    public AutoAdjustHeightImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public AutoAdjustHeightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getImageSize();
    }

    private void getImageSize() {
        Drawable drawable = this.getDrawable();
        if (drawable == null) return;
        imageWidth = drawable.getIntrinsicWidth();
        imageHeight = drawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(imageWidth == 0){
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width  * imageHeight / imageWidth;
        this.setMeasuredDimension(width, height);
    }


    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        getImageSize();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        getImageSize();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        getImageSize();
    }
}
