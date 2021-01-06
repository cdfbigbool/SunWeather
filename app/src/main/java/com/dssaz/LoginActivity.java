package com.dssaz;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dssaz.base.BaseActivity;
import com.dssaz.db.User;
import com.dssaz.db.UserDatabase;
import com.dssaz.listener.OnSecondClickListener;
import com.dssaz.utils.Md5Utils;
import com.dssaz.utils.StatusBarUtil;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class LoginActivity extends BaseActivity {
    RuntimeExceptionDao<User, Integer> dao = UserDatabase.getInstance().getDao();

    @Override
    protected int setContentViewId() {
        return R.layout.activity_login2;
    }

    private Button btn_login;
    private TextView tv_register;
    private EditText et_username;
    private EditText et_pwd;

    @Override
    protected void initView() {

        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);
        et_username = findViewById(R.id.et_phone);
        et_pwd = findViewById(R.id.et_pwd);

        SpannableStringBuilder style = new SpannableStringBuilder("还没有账号？马上注册");
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#262628")), 6, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_register.setText(style);
    }

    @Override
    protected void initDatas() {
    }

    @Override
    protected void setView() {
        StatusBarUtil.setStatusBar(this, true, false);
    }


    @Override
    protected void addListeners() {
        btn_login.setOnClickListener(new OnSecondClickListener() {
            @Override
            public void onSecondClick(View v) {

                String username = et_username.getEditableText().toString();
                String pwd = et_pwd.getEditableText().toString();

                User user = UserDatabase.queryByUsername(dao, username);
                if (user==null){
                    toast("不存在的用户");
                    return;
                }
                if(!Md5Utils.getMD5(pwd).equals(user.getPassword())){
                    toast("密码错误");
                    return;
                }

                MainActivity.start(user,LoginActivity.this);
                finish();
                //todo login
            }
        });
        tv_register.setOnClickListener(new OnSecondClickListener() {
            @Override
            public void onSecondClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateEnsureButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateEnsureButtonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateEnsureButtonStatus(){
        boolean username = et_username.getEditableText().toString().length() > 0;
        boolean pwd = et_pwd.getEditableText().toString().length() > 0;
        btn_login.setEnabled(username&&pwd);
    }
}