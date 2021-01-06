package com.dssaz;

import xunsky.base.listview_adapter.HolderAdapter.LvAdapter;
import xunsky.base.listview_adapter.HolderAdapter.LvCommonViewHolder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dssaz.base.BaseActivity;
import com.dssaz.db.Exam;
import com.dssaz.db.ExamDatabase;
import com.dssaz.db.User;
import com.j256.ormlite.stmt.PreparedQuery;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

public class ExamDetailActivity extends BaseActivity {

    private static final String TAG_NAME="ExamDetailActivity.username";
    private static final String TAG_TYPE="ExamDetailActivity.type";
    private static final String TAG_EXAM="ExamDetailActivity.exam";
    //type:0,新增;1,修改;2,审核
    public static void  start(User user,int type, Context context){
        Intent intent = new Intent(context, ExamDetailActivity.class);
        intent.putExtra(TAG_NAME,user);
        intent.putExtra(TAG_TYPE,type);
        context.startActivity(intent);
    }
    public static void  start(User user,int type,Exam exam, Context context){
        Intent intent = new Intent(context, ExamDetailActivity.class);
        intent.putExtra(TAG_NAME,user);
        intent.putExtra(TAG_TYPE,type);
        intent.putExtra(TAG_EXAM,exam);
        context.startActivity(intent);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_exam_detail;
    }

    private User user;
    private Exam exam;
    private int type;

    private Button btn_send_exam;
    private Button btn_modify_exam;
    private Button btn_cancel;
    private Button btn_pass;
    private Button btn_fail;
    private Button btn_withdraw;

    private TextView tv_date;
    private EditText et_subject;
    private EditText et_detail;
    private TextView tv_exector;
    private Spinner sp_one;
    private Spinner sp_two;
    private Spinner sp_three;

    private List<String> oneDatas=new ArrayList<>();
    private List<String> twoDatas=new ArrayList<>();
    private List<String> threeDatas=new ArrayList<>();

    @Override
    protected void initView() {
        btn_send_exam=findViewById(R.id.btn_send_exam);
        btn_modify_exam=findViewById(R.id.btn_modify_exam);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_pass=findViewById(R.id.btn_pass);
        btn_fail=findViewById(R.id.btn_fail);
        btn_withdraw=findViewById(R.id.btn_withdraw);

        tv_date=findViewById(R.id.tv_date);
        et_subject=findViewById(R.id.et_subject);
        tv_exector=findViewById(R.id.tv_exector);
        sp_one =findViewById(R.id.sp_one);
        sp_two =findViewById(R.id.sp_two);
        sp_three =findViewById(R.id.sp_three);
        et_detail=findViewById(R.id.et_detail);
    }

    @Override
    protected void initDatas() {
        Serializable serializable = getIntent().getSerializableExtra(TAG_NAME);
        if (serializable!=null){
            user=(User)serializable;
        }else{
            throw new RuntimeException("user must be not null");
        }
        int intExtra = getIntent().getIntExtra(TAG_TYPE, -1);

        if (intExtra==-1){
            throw new RuntimeException("type error");
        }
        type=intExtra;

        for (int i = 0; i < 10; i++) {
            oneDatas.add("一级分类数据"+i);
            twoDatas.add("二级分类数据"+i);
            threeDatas.add("三级分类数据"+i);
        }
        if(getIntent().getSerializableExtra(TAG_EXAM)!=null){
            exam=(Exam) getIntent().getSerializableExtra(TAG_EXAM);
        }
    }

    @Override
    protected void setView() {
        tv_exector.setText(user.getUsername());

        sp_one.setAdapter(new LvAdapter<String>(mContext,oneDatas,android.R.layout.simple_list_item_1){

            @Override
            protected void bindDatas(LvCommonViewHolder holder, String data, int position) {
                TextView tv=holder.getView(android.R.id.text1);
                tv.setMaxLines(1);
                tv.setText(data);
            }
        });
        sp_two.setAdapter(new LvAdapter<String>(mContext,twoDatas,android.R.layout.simple_list_item_1){

            @Override
            protected void bindDatas(LvCommonViewHolder holder, String data, int position) {
                TextView tv=holder.getView(android.R.id.text1);
                tv.setMaxLines(1);
                tv.setText(data);
            }
        });
        sp_three.setAdapter(new LvAdapter<String>(mContext,threeDatas,android.R.layout.simple_list_item_1){

            @Override
            protected void bindDatas(LvCommonViewHolder holder, String data, int position) {
                TextView tv=holder.getView(android.R.id.text1);
                tv.setMaxLines(1);
                tv.setText(data);
            }
        });

        switch (type){
            case 0://新增
                btn_send_exam.setVisibility(View.VISIBLE);
                break;
            case 1://修改
                btn_withdraw.setVisibility(View.VISIBLE);
                btn_modify_exam.setVisibility(View.VISIBLE);

                for (int i = 0; i < 10; i++) {
                    String one = oneDatas.get(i);
                    String two = twoDatas.get(i);
                    String three = threeDatas.get(i);
                    if (exam.getOne().equals(one)){
                        sp_one.setSelection(i);
                    }
                    if (exam.getTwo().equals(two)){
                        sp_two.setSelection(i);
                    }
                    if (exam.getThree().equals(three)){
                        sp_three.setSelection(i);
                    }
                }
                tv_exector.setText(exam.getExector());
                tv_date.setText(exam.getTime());
                et_subject.setText(exam.getSubject());
                et_detail.setText(exam.getDetail());
                break;
            case 2://审核
                btn_pass.setVisibility(View.VISIBLE);
                btn_fail.setVisibility(View.VISIBLE);

                for (int i = 0; i < 10; i++) {
                    String one = oneDatas.get(i);
                    String two = twoDatas.get(i);
                    String three = threeDatas.get(i);
                    if (exam.getOne().equals(one)){
                        sp_one.setSelection(i);
                    }
                    if (exam.getTwo().equals(two)){
                        sp_two.setSelection(i);
                    }
                    if (exam.getThree().equals(three)){
                        sp_three.setSelection(i);
                    }
                }
                tv_exector.setText(exam.getExector());
                tv_date.setText(exam.getTime());
                et_subject.setText(exam.getSubject());
                et_detail.setText(exam.getDetail());

                et_detail.setEnabled(false);
                et_subject.setEnabled(false);
                tv_date.setEnabled(false);
                sp_one.setEnabled(false);
                sp_two.setEnabled(false);
                sp_three.setEnabled(false);


                break;
        }
    }

    @Override
    protected void addListeners() {
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcalendar = Calendar.getInstance(); // 获取当前时间 — 年、月、日
                int year = mcalendar.get(Calendar.YEAR); // 得到当前年
                int month = mcalendar.get(Calendar.MONTH); // 得到当前月
                final int day = mcalendar.get(Calendar.DAY_OF_MONTH); // 得到当前日
                new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
                        mcalendar.set(year,month,dayOfMonth);
                        String format = dateformat.format(new Date(mcalendar.getTimeInMillis()));
                        tv_date.setText(format);
                    }
                }, year,month,day).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(user, mContext);
                finish();
            }
        });
        btn_send_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_subject.getText().toString().trim().length()<=0){
                    toast("请输入主题");
                    return;
                }
                if(et_detail.getText().toString().trim().length()<=0){
                    toast("请输入主题");
                    return;
                }

                Exam exam = new Exam();
                exam.setSubject(et_subject.getText().toString());
                exam.setExector(tv_exector.getText().toString());
                exam.setOne(oneDatas.get(sp_one.getSelectedItemPosition()));
                exam.setTwo(twoDatas.get(sp_two.getSelectedItemPosition()));
                exam.setThree(threeDatas.get(sp_three.getSelectedItemPosition()));
                exam.setDetail(et_detail.getText().toString());
                exam.setTime(tv_date.getText().toString());
                exam.setStatus(0);
                ExamDatabase.getInstance()
                        .getDao()
                        .create(exam);
                MainActivity.start(user, mContext);
                finish();
            }
        });
        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getUsername().equals(exam.getExector())){
                    toast("只能撤回自己发起的审核");
                    return;
                }
                try {
                    if (exam.getStatus()==1){
                        toast("审核已经完成,无法撤回");
                        return;
                    }

                    ExamDatabase.getInstance()
                            .getDao()
                            .deleteById(exam.getId());
                    MainActivity.start(user, mContext);

                } catch (Exception throwables) {
                    Log.d("meee",""+"ex:"+throwables.getMessage());
                }
            }
        });
        btn_modify_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getUsername().equals(exam.getExector())){
                    toast("只能修改自己发起的审核");
                    return;
                }

                if (exam.getStatus()==1){
                    toast("审核已经完成,无法修改");
                    return;
                }

                exam.setSubject(et_subject.getText().toString());
                exam.setExector(tv_exector.getText().toString());
                exam.setOne(oneDatas.get(sp_one.getSelectedItemPosition()));
                exam.setTwo(twoDatas.get(sp_two.getSelectedItemPosition()));
                exam.setThree(threeDatas.get(sp_three.getSelectedItemPosition()));
                exam.setDetail(et_detail.getText().toString());
                exam.setTime(tv_date.getText().toString());
                exam.setStatus(0);
                ExamDatabase.getInstance()
                        .getDao()
                        .update(exam);
                MainActivity.start(user, mContext);
                finish();
            }
        });
        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exam.getStatus()!=0){
                    toast("操作失败,该申请已经被通过/驳回");
                    return;
                }
                exam.setStatus(1);
                exam.setExamminor(user.getUsername());
                ExamDatabase.getInstance()
                        .getDao()
                        .update(exam);
                MainActivity.start(user, mContext);
                finish();
            }
        });
        btn_fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exam.getStatus()!=0){
                    toast("操作失败,该申请已经被通过/驳回");
                    return;
                }



                showLoginPasswordDialog(new LoginPasswordCallback() {
                    @Override
                    public void callback(String pwd) {
                        exam.setStatus(2);
                        exam.setExamminor(user.getUsername());
                        exam.setReason(pwd);
                        ExamDatabase.getInstance()
                            .getDao()
                            .update(exam);
                        MainActivity.start(user, mContext);
                        finish();
                    }
                });
            }
        });
    }
}