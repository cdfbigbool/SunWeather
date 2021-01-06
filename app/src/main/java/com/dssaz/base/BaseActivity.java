package com.dssaz.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dssaz.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by qiuxiuyu on 2018/8/21.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (setContentViewId() != 0) {
            setContentView(setContentViewId());
        }
        initView();
        initDatas();
        setView();
        addListeners();
    }

    protected int setContentViewId() {
        return 0;
    }

    protected void initView() {
    }

    protected void initDatas() {

    }

    protected void setView() {
    }

    protected void addListeners() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取点击事件
     */
    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void toast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected interface PayPasswordCallback {
        void callback(String pwd);
    }

    protected void showLoginPasswordDialog(LoginPasswordCallback callback) {
        EditTextPopupWindow pop = new EditTextPopupWindow(mContext, callback);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.show();
    }

    private class EditTextPopupWindow extends PopupWindow {
        private Activity mActiviaty;

        public TextView tv_close;
        public EditText et;
        public Button btn_comfirm;
        private LoginPasswordCallback mListener;

        public EditTextPopupWindow(Context context, LoginPasswordCallback listener) {
            super(context);
            mActiviaty = (Activity) context;
            mListener = listener;
            this.initView();
        }

        public void show() {
            showAtLocation(mActiviaty.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }

        private void initView() {
            View root = LayoutInflater.from(mActiviaty)
                    .inflate(R.layout.pop_password_edit, null);

            this.setContentView(root);

            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setFocusable(true);
            //设置SelectPicPopupWindow弹出窗体动画效果
            this.setAnimationStyle(R.style.dialog_style);
            ColorDrawable dw = new ColorDrawable(0x00000000);
            this.setBackgroundDrawable(dw);
            WindowManager.LayoutParams lp = mActiviaty.getWindow().getAttributes();
            lp.alpha = 0.8f;
            //mActiviaty.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mActiviaty.getWindow().setAttributes(lp);
            this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            this.setOutsideTouchable(false);
            this.setTouchable(true);
            tv_close = root.findViewById(R.id.tv_close);
            et = root.findViewById(R.id.et);
            btn_comfirm = root.findViewById(R.id.btn_comfirm);
            btn_comfirm.setEnabled(false);

            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    btn_comfirm.setEnabled(et.getText().length() > 0);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            btn_comfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.callback(et.getText().toString());
                    dismiss();
                }
            });
            et.post(new Runnable() {
                @Override
                public void run() {
                    et.setFocusable(true);
                    et.setFocusableInTouchMode(true);
                    et.requestFocus();
                    InputMethodManager imm2 = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm2.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            });
        }
    }

    public interface LoginPasswordCallback {
        void callback(String pwd);
    }

    protected void toast(String message, int icon_id) {
        Toast toast = new Toast(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.toast_authorize_bus_code_success, null);

        ImageView iv = view.findViewById(R.id.iv);
        TextView tv_message = view.findViewById(R.id.tv_message);
        tv_message.setText(message);
        iv.setBackgroundResource(icon_id);

        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
