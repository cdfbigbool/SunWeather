package com.dssaz.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

public class ExamHeaderTextView extends AppCompatTextView {
    public ExamHeaderTextView(Context context) {
        super(context);
        init(context);
    }

    public ExamHeaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExamHeaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint paint;
    private int lineColor = 0xffD0D0D0;
    private int lineWidth=3;
    private void init(Context c){
        paint = new Paint();
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);
        setGravity(Gravity.CENTER);
        setMaxLines(2);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, lineWidth/2, getWidth(), lineWidth/2, paint);
        canvas.drawLine(0, getHeight() - lineWidth/2, getWidth(), getHeight() - lineWidth/2, paint);
        canvas.drawLine(getWidth()-lineWidth/2,0,getWidth()-lineWidth/2,getHeight(),paint);
        canvas.drawLine(getWidth()-lineWidth/2,0,getWidth()-lineWidth/2,getHeight(),paint);
    }
}
