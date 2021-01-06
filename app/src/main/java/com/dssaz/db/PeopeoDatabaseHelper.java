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


public class PeopeoDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static class Holder{
        private static PeopeoDatabaseHelper sIntance=new PeopeoDatabaseHelper(ContextProvider.get());
        private static PeopeoDatabaseHelper get(){
            return sIntance;
        }
    }
    public static PeopeoDatabaseHelper getInstance(){
        return Holder.get();
    }



    public static final String DB_NAME="people.db";
    public static final int DB_VERSION=1;

    //出现异常直接抛出
    RuntimeExceptionDao<People, Integer> mRuntimeExceptionDao;
    //出现异常进行catch
    Dao<People, Integer> mDao;

    private PeopeoDatabaseHelper(Context context) {
        //在工厂类中传入null即可
        super(context, DB_NAME, null, DB_VERSION);

        Log.d("meee","("+Thread.currentThread().getStackTrace()[2].getFileName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+")\n"
                +"create");
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, People.class);
        } catch (SQLException e) {
            throw new RuntimeException("建表"+DB_NAME+"失败");
        }
        RuntimeExceptionDao<People, Integer> dao = getPeopleRuntimeDao();
        //在创建数据库时插入一些数据用于测试
        People people = new People();
        people.setAge(23);
        people.setName("小黄");
        dao.create(people);
        People people2 = new People();
        people.setAge(24);
        people.setName("老江");
        dao.create(people2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, People.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException("升级"+DB_NAME+"失败");
        }
    }

    //获取dao的两个方法
    public RuntimeExceptionDao<People, Integer> getPeopleRuntimeDao() {
        if (mRuntimeExceptionDao ==null){
            mRuntimeExceptionDao =getRuntimeExceptionDao(People.class);
        }
        return mRuntimeExceptionDao;
    }
    public Dao<People, Integer> getPeopleDao() throws SQLException {
        if (mDao==null){
            mDao=getDao(People.class);
        }
        return mDao;
    }

    //关闭数据库
    @Override
    public void close() {
        super.close();
        mDao=null;
        mRuntimeExceptionDao=null;
    }
}
