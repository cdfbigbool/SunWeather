package com.dssaz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dssaz.db.PeopeoDatabaseHelper;
import com.dssaz.db.People;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RuntimeExceptionDao<People, Integer> dao = PeopeoDatabaseHelper.getInstance().getPeopleRuntimeDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
        People people = new People();
        people.setName("小黄");
        people.setAge(18);
        dao.create(people);

        List<People> peoples = dao.queryForAll();
        for (int i = 0; i < peoples.size(); i++) {
            People p = peoples.get(i);
            Log.d("meee", getClass() + ":\n" + "people:" + p);
        }
    }
}