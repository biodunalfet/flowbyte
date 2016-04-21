package com.sjrnr.hamza.flowbyte;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Hamza Fetuga on 4/21/2016.
 */
public class MyView extends RelativeLayout {
    Bitmap mBitmap;
    int mShapeW;
    int mShapeH;
    int mShapeX;
    int mShapeY;
    Paint paint = new Paint();
    ValueAnimator anim;

    public MyView(Context context) {
        super(context);
        setupShape();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupShape();

    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupShape();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupShape();

    }


    private void setupShape() {
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.lcw);

//        mBitmap.setHeight(150);
//        mBitmap.setWidth(100);
        mShapeW = mBitmap.getWidth();
        mShapeH = mBitmap.getHeight();

//        setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startAnimation();
//            }
//        });


    }

    /**
     * Moving the shape in x or y causes an invalidation of the area it used to occupy
     * plus the area it now occupies.
     */
    public void setShapeX(int shapeX) {
        int minX = mShapeX;
        int maxX = mShapeX + mShapeW;
        mShapeX = shapeX;
        minX = Math.min(mShapeX, minX);
        maxX = Math.max(mShapeX + mShapeW, maxX);
        invalidate(minX, mShapeY, maxX, mShapeY + mShapeH);
    }

    /**
     * Moving the shape in x or y causes an invalidation of the area it used to occupy
     * plus the area it now occupies.
     */
    public void setShapeY(int shapeY) {
        int minY = mShapeY;
        int maxY = mShapeY + mShapeH;
        mShapeY = shapeY;
        minY = Math.min(mShapeY, minY);
        maxY = Math.max(mShapeY + mShapeH, maxY);
        invalidate(mShapeX, minY, mShapeX + mShapeW, maxY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mShapeX = (w - mBitmap.getWidth()) / 2;
        mShapeY = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mShapeX, mShapeY, paint);
    }

    void startAnimation() {
        // This version uses ValueAnimator, which requires adding an update
        // listener and manually updating the shape position on each frame.
//        mBitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.logo_white_smaller);
//        mShapeW = mBitmap.getWidth();
//        mShapeH = mBitmap.getHeight();
        anim = getValueAnimator();
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();
    }

    void stopAnimation(){
        anim.cancel();
    }

    ValueAnimator getValueAnimator() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 5);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setShapeY((int) (animation.getAnimatedFraction() *
                        (getHeight() - mShapeH)));
            }
        });
        return anim;
    }

}
