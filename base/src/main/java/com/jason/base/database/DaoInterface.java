package com.jason.base.database;

import android.os.Handler;

import java.util.List;

public interface DaoInterface {
	 long isExit(String condition);
	 long insertData(Object obj);
	 boolean UpdateData(Object obj);
	 boolean deleteData(String condition);
	 List<? extends Object> queryData(String condition);
	 void insertDatas(List<? extends Object> list);
	 void updateDatas(List<? extends Object> list);
	 int queryCount(String condition);

}