package com.dssaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dssaz.base.BaseActivity;
import com.dssaz.db.Exam;
import com.dssaz.db.ExamDatabase;
import com.dssaz.db.PeopeoDatabaseHelper;
import com.dssaz.db.People;
import com.dssaz.db.User;
import com.dssaz.ui.ExamineListView;
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
    RuntimeExceptionDao<Exam, Integer> dao = ExamDatabase.getInstance().getDao();

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        elv=findViewById(R.id.elv_exams);
    }

    @Override
    protected void initDatas() {
        Serializable serializable = getIntent().getSerializableExtra(TAG_NAME);
        if (serializable!=null){
            user=(User)serializable;
        }

        List<Exam> exams = dao.queryForAll();
        elv.setItems(exams);
    }

    @Override
    protected void setView() {
        super.setView();
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
    }
}