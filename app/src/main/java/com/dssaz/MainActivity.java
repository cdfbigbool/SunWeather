package com.dssaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dssaz.base.BaseActivity;
import com.dssaz.db.PeopeoDatabaseHelper;
import com.dssaz.db.People;
import com.dssaz.db.User;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG_NAME="MainActivity.username";
    public static void  start(User user, Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(TAG_NAME,user.getUsername());
        context.startActivity(intent);
    }

    private String username;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDatas() {
        username=getIntent().getStringExtra(TAG_NAME);
    }

    @Override
    protected void setView() {
        super.setView();
    }
}