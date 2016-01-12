package me.jimwind.laboratory.android.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//http://www.cnblogs.com/ITtangtang/p/3920916.html
public class DiaryDao {

	private SqliteDBHelper sqliteDBHelper;
	private SQLiteDatabase db;
	
	public DiaryDao(Context context){
		this.sqliteDBHelper = new SqliteDBHelper(context);
		db = sqliteDBHelper.getWritableDatabase();
	}
	
	public String execQuery(final String strSQL){
		try {
			System.out.println("strSQL>"+strSQL);
			Cursor cursor = db.rawQuery(strSQL, null);
			cursor.moveToFirst();
			StringBuffer sb = new StringBuffer();
			while(!cursor.isAfterLast()){
				sb.append(cursor.getInt(0) +"/"+cursor.getString(1) +"/"
						+ cursor.getString(2) +"/"+cursor.getString(3)+"/"
						+ cursor.getString(4)+"#");
				cursor.moveToNext();
			}
			db.close();
			return sb.deleteCharAt(sb.length() -1).toString();
		} catch (RuntimeException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean execOther(final String strSQL){
		db.beginTransaction();
		try{
			System.out.println("strSQL "+strSQL);
			db.execSQL(strSQL);
			db.setTransactionSuccessful();
			db.close();
			return true;
		} catch (RuntimeException e){
			e.printStackTrace();
			return false;
		} finally{
			db.endTransaction();
		}
	}
}
