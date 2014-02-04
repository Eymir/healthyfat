package com.myhealthmemo.adapter;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	public static final String DS_ROWID = "ds_Date";
	public static final String DS_RDA = "rda_Remaining";
	public static final String DS_TCI = "total_Calories_Intake";
	public static final String DS_TCB = "total_Calories_Burned";
	public static final String DS_OW = "old_Weight";
	public static final String DS_NW = "new_Weight";
	public static final String DS_WC = "weight_Change";
	public static final String TAG = "DBAdapter";
	
	private static final String DATABASE_NAME = "MHMDB";
	private static final String DATABASE_TABLE = "Daily_Summary";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE =
			"create table Daily_Summary (ds_Date Date Primary Key , "
			+ "rda_Remaining Integer not null, total_Calories_Intake Integer not null, "
			+ "total_Calories_Burned Integer not null, old_Weight Numeric not null, "
			+ "new_Weight Numeric, weight_Change Numeric);";
	
	private final Context context;
	
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx){
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context ctx){
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			try{
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS Daily_Summary");
			onCreate(db);
		}
	}
	
	//open database
	public DBAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	//close database
	public void close(){
		DBHelper.close();
	}
	
	//insert Daily_Summary records into database
	public long insertDS(String date, int rda, int tci, int tcb, double ow, double nw, double wc){
		ContentValues initialValues = new ContentValues();
		initialValues.put(DS_ROWID, date);
		initialValues.put(DS_RDA, rda);
		initialValues.put(DS_TCI, tci);
		initialValues.put(DS_TCB, tcb);
		initialValues.put(DS_OW, ow);
		initialValues.put(DS_NW, nw);
		initialValues.put(DS_WC, wc);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	//retrieves particular Daily_Summary record
	public Cursor getADS(String rowId) throws SQLException{
		Cursor mCursor=
				db.query(true, DATABASE_TABLE, new String[] {DS_ROWID, DS_RDA,
						DS_TCI, DS_TCB, DS_OW, DS_NW, DS_WC}, DS_ROWID + "=" + rowId, null,
						null,null,null,null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	//updates a Daily_Summary record
	public boolean updateADS(String rowId, int rda, int tci, int tcb, double ow, double nw, double wc){
		ContentValues args = new ContentValues();
		args.put(DS_ROWID, rowId);
		args.put(DS_RDA, rda);
		args.put(DS_TCI, tci);
		args.put(DS_TCB, tcb);
		args.put(DS_OW, ow);
		args.put(DS_NW, nw);
		args.put(DS_WC, wc);
		return db.update(DATABASE_TABLE, args, DS_ROWID + "=" + rowId, null) > 0;
	}
	
}
