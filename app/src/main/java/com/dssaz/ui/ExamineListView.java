package com.dssaz.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.dssaz.R;
import com.dssaz.db.Exam;

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

    private View header;
    private List<View> items=new LinkedList<>();
    private List<Exam> datas;
    private onClickItem onClickItem;
    private void init(Context c){
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.TOP);
        LayoutInflater inflater = LayoutInflater.from(c);

        header = inflater.inflate(R.layout.exanmin_header, this, false);
        addView(header);
        for (int i = 0; i < 10; i++) {
            View sub = inflater.inflate(R.layout.exanmin_item2, this, false);
            items.add(sub);
            sub.setTag(i);
            addView(sub);
            sub.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer n= (Integer) v.getTag();
                    if (n!=null&&onClickItem!=null){
                        Exam exam = datas.get(n);

                        onClickItem.onClick(exam,n);
                    }
                }
            });
        }
    }

    public void setOnClickItem(ExamineListView.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setItems(List<Exam> datas){
        this.datas=datas;
        int size = datas.size();
        for (int i = 0; i < items.size(); i++) {
            setExamine(i<size?datas.get(i):null,items.get(i));
        }
        invalidate();
    }

    private void setExamine(Exam exam,View v){
        if (exam==null){
            v.setVisibility(INVISIBLE);
            return;
        }
        v.setVisibility(VISIBLE);
        ExamTextView tv_subject=v.findViewById(R.id.tv_subject);
        ExamTextView tv_exector=v.findViewById(R.id.tv_exector);
        ExamTextView tv_one=v.findViewById(R.id.tv_one);
        ExamTextView tv_two=v.findViewById(R.id.tv_two);
        ExamTextView tv_three=v.findViewById(R.id.tv_three);
        ExamTextView tv_detail=v.findViewById(R.id.tv_detail);
        ExamTextView tv_time=v.findViewById(R.id.tv_time);
        ExamTextView tv_exam_status=v.findViewById(R.id.tv_exam_status);
        ExamTextView tv_fail_reason=v.findViewById(R.id.tv_fail_reason);
        ExamTextView tv_examiner=v.findViewById(R.id.tv_examiner);

        tv_subject.setText(exam.getSubject());
        tv_exector.setText(exam.getExector());
        tv_one.setText(exam.getOne());
        tv_two.setText(exam.getTwo());
        tv_three.setText(exam.getThree());

        tv_detail.setText(exam.getDetail());
        tv_time.setText(exam.getTime());
        tv_fail_reason.setText(exam.getReason());
        String status=null;
        switch (exam.getStatus()){
            case 0:
                status="待审核";
                break;
            case 1:
                status="审核通过";
                break;
            case 2:
                status="审核驳回";
                break;
        }
        tv_exam_status.setText(status);
        tv_examiner.setText(exam.getExamminor());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface onClickItem{
        void onClick(Exam exam,int position);
    }
}
