package com.dssaz;

import androidx.appcompat.app.AppCompatActivity;
import xunsky.base.listview_adapter.HolderAdapter.LvAdapter;
import xunsky.base.listview_adapter.HolderAdapter.LvCommonViewHolder;
import xunsky.base.listview_adapter.LvBaseAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dssaz.base.BaseActivity;
import com.dssaz.db.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ExamDetailActivity extends BaseActivity {

    private static final String TAG_NAME="ExamDetailActivity.username";
    private static final String TAG_TYPE="ExamDetailActivity.type";
    //type:0,新增;1,修改;2,审核
    public static void  start(User user,int type, Context context){
        Intent intent = new Intent(context, ExamDetailActivity.class);
        intent.putExtra(TAG_NAME,user);
        intent.putExtra(TAG_TYPE,type);
        context.startActivity(intent);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_exam_detail;
    }

    private User user;
    private int type;

    private Spinner sp;
    private Button btn_send_exam;
    private Button btn_modify_exam;
    private Button btn_cancel;
    private Button btn_pass;
    private Button btn_fail;
    private Button btn_withdraw;

    @Override
    protected void initView() {
        sp=findViewById(R.id.sp);
        btn_send_exam=findViewById(R.id.btn_send_exam);
        btn_modify_exam=findViewById(R.id.btn_modify_exam);
        btn_cancel=findViewById(R.id.btn_cancel);
        btn_pass=findViewById(R.id.btn_pass);
        btn_fail=findViewById(R.id.btn_fail);
        btn_withdraw=findViewById(R.id.btn_withdraw);
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
    }

    @Override
    protected void setView() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("哈哈哈哈"+i);
        }
        sp.setAdapter(new LvAdapter<String>(mContext,strings,android.R.layout.simple_list_item_1){

            @Override
            protected void bindDatas(LvCommonViewHolder holder, String data, int position) {
                TextView tv=holder.getView(android.R.id.text1);
                tv.setMaxLines(1);
                tv.setText(data);
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("meee","("+Thread.currentThread().getStackTrace()[2].getFileName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+")\n"
                        +"select position:"+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switch (type){
            case 0://新增
                btn_send_exam.setVisibility(View.VISIBLE);
                break;
            case 1://修改
                btn_withdraw.setVisibility(View.VISIBLE);
                btn_modify_exam.setVisibility(View.VISIBLE);
                break;
            case 2://审核
                btn_pass.setVisibility(View.VISIBLE);
                btn_fail.setVisibility(View.VISIBLE);
                break;
        }
    }
}