package com.jason.base.database.daoaccount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.jason.base.account.AccountObject;
import com.jason.base.database.BaseSQLiteOpenHelper;
import com.jason.base.database.DaoInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyp on 2017/12/21.
 */

public class AccountDBDao implements DaoInterface {
    private BaseSQLiteOpenHelper helper = null;
    private Handler handler = null;

    public AccountDBDao(Context context) {
        helper = BaseSQLiteOpenHelper.getInstance(context);
    }


    @Override
    public long isExit(String condition) {
        SQLiteDatabase db = helper.openReadableDatabase();
        Cursor cursor = null;
        int dbid = 0;
        cursor = db.rawQuery("select dbid from " + BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS + " where" + condition, null);
        if (cursor.moveToFirst()) {
            dbid = cursor.getInt(cursor.getColumnIndex("dbid"));
        }
        if (cursor != null) {
            cursor.close();
        }
        return dbid;
    }

    @Override
    public long insertData(Object obj) {
        SQLiteDatabase db = helper.openWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        long dbid = 0;
        try {
            if (obj instanceof AccountObject) {
                AccountObject bean = (AccountObject) obj;
                values.put("userid", bean.getUserId());
                values.put("username", bean.getUserName());
                values.put("address", bean.getAddress());
                values.put("age", bean.getAge());
                values.put("img", bean.getImg());
                values.put("password", bean.getPassword());
                values.put("sex", bean.getSex());
                values.put("tel", bean.getTel());
                values.put("islogin", bean.getIsLogin());
                long id = isExit("userid =" + bean.getUserId());
                if (id > 0) {
                    db.update(BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS, values, "dbid=?", new String[]{String.valueOf(id)});
                    dbid = id;
                } else {
                    dbid = db.insert(BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS, null, values);
                }
                values.clear();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return dbid;
    }

    @Override
    public boolean UpdateData(Object obj) {
        SQLiteDatabase db = helper.openWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {
            if (obj instanceof AccountObject) {
                AccountObject bean = (AccountObject) obj;
                values.put("userid", bean.getUserId());
                values.put("username", bean.getUserName());
                values.put("address", bean.getAddress());
                values.put("age", bean.getAge());
                values.put("img", bean.getImg());
                values.put("password", bean.getPassword());
                values.put("sex", bean.getSex());
                values.put("tel", bean.getTel());
                values.put("islogin", bean.getIsLogin());
                db.update(BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS, values, "dbid=?", new String[]{String.valueOf(bean.getDbid())});
                values.clear();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
        return true;
    }

    @Override
    public boolean deleteData(String condition) {
        String sql = "delete from " + BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS + " where " + condition;
        SQLiteDatabase db = null;
        try {
            db = helper.openWritableDatabase();
            db.execSQL(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } finally {
        }
        return true;
    }

    @Override
    public List<? extends Object> queryData(String condition) {
        List<AccountObject> list = new ArrayList<AccountObject>();
        SQLiteDatabase db = helper.openReadableDatabase();
        Cursor cursor = null;
        try {
            if (condition != null) {
                cursor = db.rawQuery("select * from " + BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS + " where "
                        + condition, null);
            } else {
                cursor = db.rawQuery("select * from " + BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS, null);
            }
            while (cursor.moveToNext()) {
                AccountObject bean = new AccountObject();
                bean.setDbid(cursor.getLong(cursor.getColumnIndex("dbid")));
                bean.setUserId(cursor.getString(cursor.getColumnIndex("userid")));
                bean.setUserName(cursor.getString(cursor.getColumnIndex("username")));
                bean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                bean.setTel(cursor.getLong(cursor.getColumnIndex("tel")));
                bean.setImg(cursor.getString(cursor.getColumnIndex("img")));
                bean.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                bean.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
                bean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                bean.setIsLogin(cursor.getInt(cursor.getColumnIndex("islogn")));
                list.add(bean);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    @Override
    public void insertDatas(List<? extends Object> list) {
        SQLiteDatabase db = helper.openWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {
            for (Object obj : list) {
                if (obj instanceof AccountObject) {
                    AccountObject bean = (AccountObject) obj;
                    values.put("userid", bean.getUserId());
                    values.put("username", bean.getUserName());
                    values.put("address", bean.getAddress());
                    values.put("age", bean.getAge());
                    values.put("img", bean.getImg());
                    values.put("password", bean.getPassword());
                    values.put("sex", bean.getSex());
                    values.put("tel", bean.getTel());
                    values.put("islogin", bean.getIsLogin());
                    long dbid = isExit("userid =" + bean.getUserId());
                    if (dbid > 0) {
                        db.update(BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS, values, "dbid=?", new String[]{String.valueOf(dbid)});
                    } else {
                        db.insert(BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS, null, values);
                    }
                    values.clear();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void updateDatas(List<? extends Object> list) {
        SQLiteDatabase db = helper.openWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {
            for (Object obj : list) {
                if (obj instanceof AccountObject) {
                    AccountObject bean = (AccountObject) obj;
                    values.put("userid", bean.getUserId());
                    values.put("username", bean.getUserName());
                    values.put("address", bean.getAddress());
                    values.put("age", bean.getAge());
                    values.put("img", bean.getImg());
                    values.put("password", bean.getPassword());
                    values.put("sex", bean.getSex());
                    values.put("tel", bean.getTel());
                    values.put("islogin", bean.getIsLogin());
                    db.update(BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS, values, "dbid=?", new String[]{String.valueOf(bean.getDbid())});
                    values.clear();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    @Override
    public int queryCount(String condition) {
        SQLiteDatabase db = helper.openReadableDatabase();
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = db.rawQuery("select count(*) from " + BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS + " where " + condition, null);
            cursor.moveToFirst();
            count = cursor.getInt(0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }


    public void updateLogin(long uid) {
        SQLiteDatabase db = helper.openWritableDatabase();
        db.beginTransaction();
        try {
            String sql = "update " + BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS + "set islogin = 1 where userid = " + uid;
            db.execSQL(sql);
            String sql2 = "update " + BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS + "set islogin = 0 where userid <> " + uid;
            db.execSQL(sql2);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    public AccountObject queryDefualAccount() {
        AccountObject account  = null;
        SQLiteDatabase db = helper.openReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from " + BaseSQLiteOpenHelper.TABLE_NAME_ACCOUNTS + " where islogn = 1", null);
            while (cursor.moveToNext()) {
                account = new AccountObject();
                account.setDbid(cursor.getLong(cursor.getColumnIndex("dbid")));
                account.setUserId(cursor.getString(cursor.getColumnIndex("userid")));
                account.setUserName(cursor.getString(cursor.getColumnIndex("username")));
                account.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                account.setTel(cursor.getLong(cursor.getColumnIndex("tel")));
                account.setImg(cursor.getString(cursor.getColumnIndex("img")));
                account.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                account.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
                account.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                account.setIsLogin(cursor.getInt(cursor.getColumnIndex("islogn")));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return account;
    }
}
