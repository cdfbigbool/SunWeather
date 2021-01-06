package com.dssaz.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.dssaz.R;

import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.widget.LinearLayoutCompat;

public class ExamineListView extends LinearLayoutCompat {
    public ExamineListView(Context context) {
        super(context);
        init(context);
    }

    public ExamineListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExamineListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    View header;
    List<View> items=new LinkedList<>();

    private void init(Context c){
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.TOP);
        LayoutInflater inflater = LayoutInflater.from(c);

        header = inflater.inflate(R.layout.exanmin_header, this, true);
        for (int i = 0; i < 10; i++) {
            items.add(inflater.inflate(R.layout.exanmin_header, this, true));
        }
    }

    private void setExamine(Object obj,View v){
        ExamTextView tv_subject=v.findViewById(R.id.tv_subject);
        ExamTextView tv_exector=v.findViewById(R.id.tv_exector);
        ExamTextView tv_one=v.findViewById(R.id.tv_one);
        ExamTextView tv_two=v.findViewById(R.id.tv_two);
        ExamTextView tv_three=v.findViewById(R.id.tv_three);
        ExamTextView tv_detail=v.findViewById(R.id.tv_detail);
        ExamTextView tv_time=v.findViewById(R.id.tv_time);
        ExamTextView tv_exam_status=v.findViewById(R.id.tv_exam_status);
        ExamTextView tv_fail_reason=v.findViewById(R.id.tv_fail_reason);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
