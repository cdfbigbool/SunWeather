package com.dssaz;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dssaz.base.BaseActivity;
import com.dssaz.db.User;
import com.dssaz.db.UserDatabase;
import com.dssaz.listener.OnSecondClickListener;
import com.dssaz.utils.Md5Utils;
import com.dssaz.utils.StatusBarUtil;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class RegisterActivity extends BaseActivity {
    @Override
    protected int setContentViewId() {
        return R.layout.activity_register;
    }

    private Button btn_register;
    private EditText et_username;
    private EditText et_pwd;
    RuntimeExceptionDao<User, Integer> dao = UserDatabase.getInstance().getDao();


    @Override
    protected void initView() {
        btn_register = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_phone);
        et_pwd = findViewById(R.id.et_pwd);
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
        btn_register.setOnClickListener(new OnSecondClickListener() {
            @Override
            public void onSecondClick(View v) {
                String username = et_username.getEditableText().toString();
                String pwd = et_pwd.getEditableText().toString();

                User user = UserDatabase.queryByUsername(dao, username);
                if (user!=null){
                    toast("该账号已经被注册");
                    return;
                }

                user=new User();
                user.setType(0);
                user.setUsername(username);
                user.setPassword(Md5Utils.getMD5(pwd));
                dao.create(user);
                toast("注册成功,请登录");
                finish();
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
        btn_register.setEnabled(username&&pwd);
    }
}