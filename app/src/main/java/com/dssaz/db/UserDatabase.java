package com.dssaz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dssaz.utils.Md5Utils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import xunsky.utils.context_provider.ContextProvider;


public class UserDatabase extends OrmLiteSqliteOpenHelper {
    private static class Holder{
        private static UserDatabase sIntance=new UserDatabase(ContextProvider.get());
        private static UserDatabase get(){
            return sIntance;
        }
    }
    public static UserDatabase getInstance(){
        return Holder.get();
    }



    public static final String DB_NAME="user.db";
    public static final int DB_VERSION=1;

    RuntimeExceptionDao<User, Integer> mRuntimeExceptionDao;

    private UserDatabase(Context context) {
        //在工厂类中传入null即可
        super(context, DB_NAME, null, DB_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            throw new RuntimeException("建表"+DB_NAME+"失败");
        }
        User user = new User();
        user.setType(1);
        user.setPassword(Md5Utils.getMD5("admin"));
        user.setUsername("admin");
        getDao().create(user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException("升级"+DB_NAME+"失败");
        }
    }

    //获取dao的两个方法
    public RuntimeExceptionDao<User, Integer> getDao() {
        if (mRuntimeExceptionDao ==null){
            mRuntimeExceptionDao =getRuntimeExceptionDao(User.class);
        }
        return mRuntimeExceptionDao;
    }

    //关闭数据库
    @Override
    public void close() {
        super.close();
        mRuntimeExceptionDao=null;
    }

    public static User queryByUsername(RuntimeExceptionDao<User, Integer> dao,String username){
        try {
            List<User> users = dao.queryBuilder()
                    .where()
                    .eq("username", username)
                    .query();
            if (users!=null&&users.size()>0)
                return users.get(0);

        } catch (SQLException throwables) {
            Log.d("meee","("+Thread.currentThread().getStackTrace()[2].getFileName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+")\n"
                    +"ex:"+throwables.getMessage());
        }
        return null;
    }
}
