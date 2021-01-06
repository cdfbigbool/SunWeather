package com.dssaz.listener;

import android.view.View;


public abstract class OnSecondClickListener implements View.OnClickListener {
    private long lastClickTime;
    @Override
    final public void onClick(View v) {
        if (System.currentTimeMillis()-lastClickTime>interval()){
            lastClickTime= System.currentTimeMillis();
            onSecondClick(v);
        }
    }

    private int interval(){
        return 1500;
    }

    public abstract void onSecondClick(View v);
}
