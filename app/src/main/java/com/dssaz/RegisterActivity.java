package com.dssaz;

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
import com.dssaz.listener.OnSecondClickListener;
import com.dssaz.utils.StatusBarUtil;

public class RegisterActivity extends BaseActivity {
    @Override
    protected int setContentViewId() {
        return R.layout.activity_register;
    }

    private Button btn_login;
    private TextView tv_register;
    private EditText et_phone;
    private EditText et_pwd;

    @Override
    protected void initView() {
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);
        et_phone = findViewById(R.id.et_phone);
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
                //todo login
            }
        });
        tv_register.setOnClickListener(new OnSecondClickListener() {
            @Override
            public void onSecondClick(View v) {
                //todo register
                //startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        et_phone.addTextChangedListener(new TextWatcher() {
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
        boolean username = et_phone.getEditableText().toString().length() > 0;
        boolean pwd = et_pwd.getEditableText().toString().length() > 0;
        btn_login.setEnabled(username&&pwd);
    }
}