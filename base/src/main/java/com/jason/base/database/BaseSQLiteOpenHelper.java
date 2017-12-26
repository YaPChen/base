package com.jason.base.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * 数据库构造类
 *
 * @author Administrator
 */
public class BaseSQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    public static final String DB_NAME = "Base_Mobile.db";
    public static final int DB_VERSION = 1;

    public final static String TABLE_NAME_ACCOUNTS = "Accounts";


    private BaseSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private  BaseSQLiteOpenHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }


    public static BaseSQLiteOpenHelper helper;
    public static BaseSQLiteOpenHelper getInstance(Context context){
        if(helper==null){
            helper=new BaseSQLiteOpenHelper(context);
        }
        return helper;
    }

    private SQLiteDatabase mWritableDatabase;
    private SQLiteDatabase mReadableDatabase;

    public synchronized SQLiteDatabase openWritableDatabase() {
        if (mWritableDatabase == null) {
            mWritableDatabase = getWritableDatabase();
        }
        return mWritableDatabase;
    }

    public synchronized SQLiteDatabase openReadableDatabase() {
        if (mReadableDatabase == null) {
            mReadableDatabase = getReadableDatabase();
        }
        return mReadableDatabase;
    }

    public synchronized void closeReadableDatabase() {
        if (mReadableDatabase != null && mReadableDatabase.isOpen()) {
            mReadableDatabase.close();
            mReadableDatabase = null;
        }
    }

    public synchronized void closeWritableDatabase() {
        if (mWritableDatabase != null && mWritableDatabase.isOpen()) {
            mWritableDatabase.close();
            mWritableDatabase = null;
        }
    }

    public synchronized void closeDatabase() {
        closeReadableDatabase();
        closeWritableDatabase();
    }

    // 当数据库创建的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        // /////////////////yonghu/////////////////////////////////
        String AccoutCreateSql = "CREATE TABLE ["+TABLE_NAME_ACCOUNTS+"](" + "[dbid] INTEGER PRIMARY KEY NOT NULL,"
                + "[userName] TEXT NULL," + "[userId] TEXT NULL," + "[password] TEXT NULL,"
                + "[tel] INTEGER NULL," + "[img] TEXT NULL," + "[sex] INTEGER NULL,"
                + "[age] INTEGER NULL," + "[address] TEXT NULL," + "[isLogin] INTEGER NULL,"
                + "[data1] TEXT NULL,"+ "[data2] TEXT NULL,"+ "[data3] TEXT NULL)";
        db.execSQL(AccoutCreateSql);
    }

    // 版本更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < 3 && newVersion >= 3) {
//
//        }
    }
}
