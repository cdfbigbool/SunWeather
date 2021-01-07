package com.dssaz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import xunsky.utils.context_provider.ContextProvider;


public class ExamDatabase extends OrmLiteSqliteOpenHelper {
    private static class Holder{
        private static ExamDatabase sIntance=new ExamDatabase(ContextProvider.get());
        private static ExamDatabase get(){
            return sIntance;
        }
    }
    public static ExamDatabase getInstance(){
        return Holder.get();
    }



    public static final String DB_NAME="exam.db";
    public static final int DB_VERSION=1;

    //出现异常直接抛出
    RuntimeExceptionDao<Exam, Integer> mRuntimeExceptionDao;

    private ExamDatabase(Context context) {
        //在工厂类中传入null即可
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Exam.class);
        } catch (SQLException e) {
            throw new RuntimeException("建表"+DB_NAME+"失败");
        }
//        RuntimeExceptionDao<Exam, Integer> dao = getDao();
//        Exam exam = new Exam();
//        exam.setSubject("subject");
//        exam.setExector("exector");
//        exam.setOne("one");
//        exam.setTwo("two");
//        exam.setThree("three");
//        exam.setDetail("detail");
//        exam.setTime("time");
//        exam.setReason("reason");
//        exam.setStatus(1);
//        exam.setExamminor("examminor");
//        dao.create(exam);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Exam.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException("升级"+DB_NAME+"失败");
        }
    }

    //获取dao的两个方法
    public RuntimeExceptionDao<Exam, Integer> getDao() {
        if (mRuntimeExceptionDao ==null){
            mRuntimeExceptionDao =getRuntimeExceptionDao(Exam.class);
        }
        return mRuntimeExceptionDao;
    }


    //关闭数据库
    @Override
    public void close() {
        super.close();
        mRuntimeExceptionDao=null;
    }
}
