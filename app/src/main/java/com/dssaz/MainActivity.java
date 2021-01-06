package com.dssaz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dssaz.base.BaseActivity;
import com.dssaz.db.Exam;
import com.dssaz.db.ExamDatabase;
import com.dssaz.db.User;
import com.dssaz.ui.ExamineListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG_NAME="MainActivity.username";
    public static void  start(User user, Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(TAG_NAME,user);
        context.startActivity(intent);
    }

    private User user;
    private ExamineListView elv;
    private TextView tv_user;
    private FloatingActionButton fab;
    private RuntimeExceptionDao<Exam, Integer> dao = ExamDatabase.getInstance().getDao();
    private Button btn_pre_page;
    private Button btn_next_page;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        elv=findViewById(R.id.elv_exams);
        tv_user=findViewById(R.id.tv_user);
        fab=findViewById(R.id.fab);
        btn_pre_page=findViewById(R.id.btn_pre_page);
        btn_next_page=findViewById(R.id.btn_next_page);
    }

    @Override
    protected void initDatas() {
        Serializable serializable = getIntent().getSerializableExtra(TAG_NAME);
        if (serializable!=null){
            user=(User)serializable;
        }
    }

    private List<List<Exam>> datasSet=new ArrayList<>();
    private int datasIndex=0;
    @Override
    protected void setView() {
        List<Exam> exams = dao.queryForAll();
        Collections.reverse(exams);

        List<Exam> datas=new ArrayList<>();
        while (!exams.isEmpty()){
            Exam exam = exams.remove(0);
            datas.add(exam);
            if (datas.size()==10){
                datasSet.add(datas);
                datas=new ArrayList<>();
            }
        }

        if (datas.size()!=0){
            datasSet.add(datas);
        }


        setExamList();

        if (user!=null){
            tv_user.setText("你好:"+user.getUsername()+" ");
        }

        boolean b=user!=null&&user.getType()!=1;
        fab.setVisibility(b?View.VISIBLE: View.INVISIBLE);
    }

    private void setExamList(){
        if (datasIndex<datasSet.size()){
            elv.setItems(datasSet.get(datasIndex));
        }
        if (datasIndex<=0){
            btn_pre_page.setEnabled(false);
        }else{
            btn_pre_page.setEnabled(true);
        }
        if (datasIndex>=(datasSet.size()-1)){
            btn_next_page.setEnabled(false);
        }else{
            btn_next_page.setEnabled(true);
        }
    }

    @Override
    protected void addListeners() {
        elv.setOnClickItem(new ExamineListView.onClickItem() {
            @Override
            public void onClick(Exam exam, int position) {
                Log.d("meee",""+exam);
                if (user.getType()==1){//管理员审核
                    ExamDetailActivity.start(user,2,exam,MainActivity.this);
                    finish();
                    return;
                }
                ExamDetailActivity.start(user,1,exam,MainActivity.this);
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamDetailActivity.start(user,0,MainActivity.this);
                finish();
            }
        });
        btn_next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasIndex++;
                setExamList();
            }
        });
        btn_pre_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasIndex--;
                setExamList();
            }
        });
    }

    private long lastBackClicked;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis()-lastBackClicked)>1000){
            lastBackClicked=System.currentTimeMillis();
            toast("再次点击后退将返回登录页面");
            return;
        }
        startActivity(new Intent(mContext,LoginActivity.class));
        finish();
    }
}