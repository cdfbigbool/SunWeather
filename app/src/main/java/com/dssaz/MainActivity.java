package com.dssaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dssaz.base.BaseActivity;
import com.dssaz.db.Exam;
import com.dssaz.db.ExamDatabase;
import com.dssaz.db.PeopeoDatabaseHelper;
import com.dssaz.db.People;
import com.dssaz.db.User;
import com.dssaz.ui.ExamineListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

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
    RuntimeExceptionDao<Exam, Integer> dao = ExamDatabase.getInstance().getDao();

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        elv=findViewById(R.id.elv_exams);
        tv_user=findViewById(R.id.tv_user);
        fab=findViewById(R.id.fab);
    }

    @Override
    protected void initDatas() {
        Serializable serializable = getIntent().getSerializableExtra(TAG_NAME);
        if (serializable!=null){
            user=(User)serializable;
        }
    }

    @Override
    protected void setView() {
        List<Exam> exams = dao.queryForAll();
        elv.setItems(exams);

        if (user!=null){
            tv_user.setText("你好:"+user.getUsername()+" ");
        }

        boolean b=user!=null&&user.getType()!=1;
        fab.setVisibility(b?View.VISIBLE: View.INVISIBLE);
    }

    @Override
    protected void addListeners() {
        elv.setOnClickItem(new ExamineListView.onClickItem() {
            @Override
            public void onClick(Exam exam, int position) {
                Log.d("meee","("+Thread.currentThread().getStackTrace()[2].getFileName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+")\n"
                        +"position:"+position+",exam:"+exam);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamDetailActivity.start(user,0,MainActivity.this);
//                ExamDetailActivity.start(user,1,MainActivity.this);
//                ExamDetailActivity.start(user,2,MainActivity.this);
                finish();
            }
        });
    }
}