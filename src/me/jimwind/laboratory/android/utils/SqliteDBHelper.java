package me.jimwind.laboratory.android.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "diary_db";
	private static final int VERSION = 1;
	private static final String TABLE_NAME = "diary";
	public SqliteDBHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	public SqliteDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String strSQL = "create table "
								+ TABLE_NAME
								+ " (tid integer primary key autoincrement, title varchar(20), weather varchar(10), context text, publish date)";
		db.execSQL(strSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
