package com.jason.base.ui.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jason.base.R;


/**
 * Created by zhangcuicui on 2016/4/19.
 */
public class DrawableTextView extends AppCompatTextView
{
    private int mDrawableHeight;
    private int mDrawableWidth;
    private Drawable[] mDrawables;

    public DrawableTextView(Context context)
    {
        super(context);
    }

    public DrawableTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);

        this.mDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableHeight, 0);
        this.mDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableWidth, 0);

        typedArray.recycle();
        this.mDrawables = getCompoundDrawables();
        reinitCompoundDrawables();
    }

    private void reinitCompoundDrawables()
    {
        if ((this.mDrawableHeight > 0) && (this.mDrawableWidth > 0)) {
            Rect bound = new Rect(0, 0, this.mDrawableWidth, this.mDrawableHeight);
            for (int index = 0; index < 4; index++) {
                if (this.mDrawables[index] != null) {
                    this.mDrawables[index].setBounds(bound);
                }
            }
        }
        super.setCompoundDrawables(this.mDrawables[0], this.mDrawables[1], this.mDrawables[2], this.mDrawables[3]);
    }

    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom)
    {
        if (this.mDrawables == null) {
            this.mDrawables = new Drawable[4];
        }
        this.mDrawables[0] = left;
        this.mDrawables[1] = top;
        this.mDrawables[2] = right;
        this.mDrawables[3] = bottom;

        reinitCompoundDrawables();
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }
}