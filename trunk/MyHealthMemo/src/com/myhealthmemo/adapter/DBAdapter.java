package com.myhealthmemo.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myhealthmemo.model.Activity;
import com.myhealthmemo.model.ActivityDiaryDetails;
import com.myhealthmemo.model.DailySummary;
import com.myhealthmemo.model.DietDiaryDetails;
import com.myhealthmemo.model.Food;

public class DBAdapter {
	// database configuration
	// if you want the onUpgrade to run then change the database_version
	private static final int DATABASE_VERSION = 1;
	//database name
	private static final String DATABASE_NAME = "myhealthmemo.db";

	// Food table configuration
		private static final String FOOD_TABLE_NAME = "Food";         // Food Table name
		private static final String FOOD_COLUMN_ID = "f_ID";     // a column named "_id" is required for cursor
		private static final String FOOD_COLUMN_FOOD_NAME = "food_Name";
		private static final String FOOD_COLUMN_CALORIES ="calories";
		private static final String FOOD_COLUMN_CARBOHYDRATES = "carbohydrates";
		private static final String FOOD_COLUMN_PROTEIN = "protein";
		private static final String FOOD_COLUMN_DIETARY_FIBRE = "dietary_Fibre";
		private static final String FOOD_COLUMN_SODIUM = "sodium";
		private static final String FOOD_COLUMN_FATS = "fats";
		private static final String FOOD_COLUMN_CHOLESTROL = "cholestrol";
		private static final String FOOD_COLUMN_IMAGE = "image";
		private static final String TAG = "DBAdapter";
	
	
	//Diet_Diary_detail table configuration
	private static final String DIETDIARYDETAILS_TABLE_NAME = "DietDiaryDetails";
	private static final String DIETDIARYDETAILS_COLUMN_DATE = "dd_Date";
	private static final String DIETDIARYDETAILS_COLUMN_FID ="f_ID";
	private static final String DIETDIARYDETAILS_COLUMN_SERVING_SIZE = "serving_Size";
	private static final String DIEDIARYDETAILS_COLUMN_CALORIES_INTAKE = "calories_Intake";
	private static final String DIETDIARYDETAILS_COLUMN_MEAL_TYPE = "meal_Type";
	
	
	//Activity table configuration
	private static final String ACTIVITY_TABLE_NAME = "Activities";
	private static final String ACTIVITY_COLUMN_ID = "a_Id";
	private static final String ACTIVITY_COLUMN_ACTIVITY_CATEGORY = "activity_Category";
	private static final String ACTIVITY_COLUMN_ACTIVITY_NAME = "activity_Name";
	private static final String ACTIVITY_COLUMN_MET_VALUE = "met_Value";
	private static final String ACTIVITY_COLUMN_ACTIVITY_IMAGE = "activity_Image";
	
	//ActivityDiaryDetails configuration
	private static final String ACTIVITYDIARYDETAILS_TABLE_NAME = "ActivityDiaryDetails";
	private static final String ACTIVITYDIARYDETAILS_COLUMN_DATE = "ad_Date";
	private static final String ACTIVITYDIARYDETAILS_COLUMN_ID = "a_Id";
	private static final String ACTIVITYDIARYDETAILS_COLUMN_DURATION = "duration";
	private static final String ACTIVITYDIARYDETAILS_COLUMN_CALORIESBURNED = "calories_Burned";
	private static final String ACTIVITYDIARYDETAILS_COLUMN_DISTANCE = "distance";
	private static final String ACTIVITYDIARYDETAILS_COLUMN_SPEED = "speed";
	
	//DailySummary configuration
	private static final String DAILYSUMMARY_TABLE_NAME ="DailySummary";
	private static final String DAILYSUMMARY_COLUMN_DATE = "ds_Date";
	private static final String DAILYSUMMARY_COLUMN_TOTAL_CALORIES_INTAKE = "total_Calories_Intake";
	private static final String DAILYSUMMARY_COLUMN_TOTAL_CALORIES_BURNED = "total_Calories_Burned";
	private static final String DAILYSUMMARY_COLUMN_OLD_WEIGHT = "old_Weight";
	private static final String DAILYSUMMARY_COLUMN_NEW_WEIGHT = "new_Weight";
	private static final String DAILYSUMMARY_COLUMN_WEIGHT_CHANGE = "weight_Change";
	private static final String DAILYSUMMARY_COLUMN_RDA_REMAINING = "rda_Remaining";

	 // Create your tables here
	//CREATE FOOD TABLE
    
	//static final String DATABASE_CREATE = "CREATE TABLE " + FOOD_TABLE_NAME + "( " + FOOD_COLUMN_ID + " TEXT PRIMARY KEY, " +
   // FOOD_COLUMN_FOOD_NAME + " TEXT, " + FOOD_COLUMN_CARBOHYDRATES + " DECIMAL, " + FOOD_COLUMN_PROTEIN + " DECIMAL," +  FOOD_COLUMN_DIETARY_FIBRE + " DECIMAL," + FOOD_COLUMN_SODIUM + " DECIMAL," + FOOD_COLUMN_FATS + " DECIMAL," + FOOD_COLUMN_MEAT_CATEGORY_ID + " INTEGER," + FOOD_COLUMN_VEGETABLE_CATEGORY_ID + " INTEGER," +  FOOD_COLUMN_RICE_CEREAL_CATEGORY_ID + " INTEGER )";
   

	final Context context;
	static DatabaseHelper DBHelper;
	static SQLiteDatabase db;
	
	public DBAdapter(Context ctx)
	 {
	 this.context = ctx;
	 DBHelper = new DatabaseHelper(context);
	 }
	
	private static class DatabaseHelper extends SQLiteOpenHelper { 
		private static final String TAG = DatabaseHelper.class.getSimpleName();

		DatabaseHelper(Context aContext) {
			super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase sqLiteDatabase) {
			
			//Log.d(TAG, "onCreate SQL: " + DATABASE_CREATE);

			//sqLiteDatabase.execSQL(DATABASE_CREATE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
			// Database schema upgrade code goes here
            //FOOD 
			String buildSQL = "DROP TABLE IF EXISTS " + FOOD_TABLE_NAME;
			//DIET_DIARY_DETAILS
			String buildSQL2 = "DROP TABLE IF EXISTS " + DIETDIARYDETAILS_TABLE_NAME;
			//ACTIVITY
			String buildSQL3 = "DROP TABLE IF EXISTS " + ACTIVITY_TABLE_NAME;
			//ACTIVITY_DIARY_DETAILS
			String buildSQL4 ="DROP TABLE IF EXISTS " + ACTIVITYDIARYDETAILS_TABLE_NAME;
			//DAILY_SUMMARY
			String buildSQL5 = "DROP TABLE IF EXISTS " + DAILYSUMMARY_TABLE_NAME;

			Log.d(TAG, "onUpgrade SQL: " + buildSQL + buildSQL2 + buildSQL3 + buildSQL4 + buildSQL5);

			sqLiteDatabase.execSQL(buildSQL);       // drop previous table
			sqLiteDatabase.execSQL(buildSQL2);
			sqLiteDatabase.execSQL(buildSQL3);
			sqLiteDatabase.execSQL(buildSQL4);
			sqLiteDatabase.execSQL(buildSQL5);

			onCreate(sqLiteDatabase);               // create the table from the beginning
			
		}

	}
		// this is a wrapper class. that means, from outside world, anyone will communicate with PersonDatabaseHelper,
		// but under the hood actually DatabaseOpenHelper class will perform database CRUD operations 

		
		//---opens the database---
		public DBAdapter open() throws SQLException 
		{
			db = DBHelper.getWritableDatabase();
			return this;
		}
		 //close database
	    public void closeDB(){
	    	DBHelper.close();
	    }
 
	    
//***************************** CREATE/INSERT METHOD******************************//

	  //Create/ insert Food table data
	  		public long insertData ( String aId, String aFoodName,double aCalories, double aCarbohydrates, double aProtein, double aDietary_Fibre, double aSodium, double aFats, double aCholestrol, String aImage ){ 
	  			// we are using ContentValues to avoid sql format errors
	              
	  			ContentValues contentValues = new ContentValues();
	  			contentValues.put(FOOD_COLUMN_ID, aId); 
	  			contentValues.put(FOOD_COLUMN_FOOD_NAME, aFoodName); 
	  			contentValues.put(FOOD_COLUMN_CALORIES, aCalories);
	  			contentValues.put(FOOD_COLUMN_CARBOHYDRATES, aCarbohydrates);
	  		    contentValues.put(FOOD_COLUMN_PROTEIN,  aProtein);
	  		    contentValues.put(FOOD_COLUMN_DIETARY_FIBRE,  aDietary_Fibre);
	  		    contentValues.put(FOOD_COLUMN_SODIUM,  aSodium);
	  		    contentValues.put(FOOD_COLUMN_FATS,  aFats);
	              contentValues.put(FOOD_COLUMN_CHOLESTROL, aCholestrol);
	  		    contentValues.put(FOOD_COLUMN_IMAGE, aImage);
	      	    return db.insert(FOOD_TABLE_NAME, null, contentValues);
	      	
	  		}
		
		//Create/ insert Diet_Diary_Details data
		public long insertDietDiaryData(String cddDate, String cId, double cServingSize, int cCaloriesIntake, String cMealType){
			ContentValues contentValues3 = new ContentValues();
			
			contentValues3.put(DIETDIARYDETAILS_COLUMN_DATE, cddDate);
			contentValues3.put(DIETDIARYDETAILS_COLUMN_FID, cId);
			contentValues3.put(DIETDIARYDETAILS_COLUMN_SERVING_SIZE, cServingSize);
			contentValues3.put(DIETDIARYDETAILS_COLUMN_MEAL_TYPE, cMealType);
			contentValues3.put(DIEDIARYDETAILS_COLUMN_CALORIES_INTAKE, cCaloriesIntake);
			
			return db.insert(DIETDIARYDETAILS_TABLE_NAME, null, contentValues3);
		}
		
		//Create/ insert Activity data
		public long insertActivityData(int dId,  String dActivityCategory, String dActivityName, double dMetValue, byte[] dActivityImage){
			ContentValues contentValues4 = new ContentValues();
			
			contentValues4.put(ACTIVITY_COLUMN_ID, dId);
			contentValues4.put(ACTIVITY_COLUMN_ACTIVITY_CATEGORY, dActivityCategory);
			contentValues4.put(ACTIVITY_COLUMN_ACTIVITY_NAME, dActivityName);
			contentValues4.put(ACTIVITY_COLUMN_MET_VALUE, dMetValue);
			contentValues4.put(ACTIVITY_COLUMN_ACTIVITY_IMAGE, dActivityImage);
			
			return db.insert(ACTIVITY_TABLE_NAME, null, contentValues4);
		}
		
		//Create/ insert ActivityDiaryDetails data
		public long insertActivityDiaryDetailsData(String eDate, int eId, int eDuration, int eCaloriesBurned, int eDistance, double eSpeed){
			ContentValues contentValues5 = new ContentValues();
			
			contentValues5.put(ACTIVITYDIARYDETAILS_COLUMN_DATE, eDate);
			contentValues5.put(ACTIVITYDIARYDETAILS_COLUMN_ID, eId);
			contentValues5.put(ACTIVITYDIARYDETAILS_COLUMN_DURATION, eDuration);
			contentValues5.put(ACTIVITYDIARYDETAILS_COLUMN_CALORIESBURNED, eCaloriesBurned);
			contentValues5.put(ACTIVITYDIARYDETAILS_COLUMN_DISTANCE, eDistance);
			contentValues5.put(ACTIVITYDIARYDETAILS_COLUMN_SPEED, eSpeed);
			return db.insert(ACTIVITYDIARYDETAILS_TABLE_NAME, null, contentValues5);
		}
		
		
		//Create/ insert DailySummary data
		public long insertDailySummaryData(String fDate, int fTotalCaloriesIntake, int fTotalCaloriesBurned, double fOldWeight, double fNewWeight, double fWeightChange, int fRdaRemaining){
			ContentValues contentValues6 = new ContentValues();
			
			contentValues6.put(DAILYSUMMARY_COLUMN_DATE, fDate);
			contentValues6.put(DAILYSUMMARY_COLUMN_TOTAL_CALORIES_INTAKE, fTotalCaloriesIntake);
			contentValues6.put(DAILYSUMMARY_COLUMN_TOTAL_CALORIES_BURNED, fTotalCaloriesBurned);
			contentValues6.put(DAILYSUMMARY_COLUMN_OLD_WEIGHT, fOldWeight);
			contentValues6.put(DAILYSUMMARY_COLUMN_NEW_WEIGHT, fNewWeight);
			contentValues6.put(DAILYSUMMARY_COLUMN_WEIGHT_CHANGE, fWeightChange);
			contentValues6.put(DAILYSUMMARY_COLUMN_RDA_REMAINING, fRdaRemaining);
			
			return db.insert(DAILYSUMMARY_TABLE_NAME, null, contentValues6);
		}
		
	
//******************************** GET ALL ITEM METHOD*********************************//		

		//get all FOOD TABLE data
				public Cursor getAllData() {
					return db.query(FOOD_TABLE_NAME, new String[]{
							FOOD_COLUMN_ID,
							FOOD_COLUMN_FOOD_NAME, 
							FOOD_COLUMN_CALORIES,
							FOOD_COLUMN_CARBOHYDRATES,
							FOOD_COLUMN_PROTEIN,
							FOOD_COLUMN_SODIUM,
							FOOD_COLUMN_FATS,
							FOOD_COLUMN_CHOLESTROL,
							FOOD_COLUMN_IMAGE}, null, null, null, null, null);
		    		
		        }
		
		//get all DietDiaryDetails TABLE data
		public Cursor getAllDietDiaryDetailsData(){
			return db.query(DIETDIARYDETAILS_TABLE_NAME, new String[]{
				DIETDIARYDETAILS_COLUMN_DATE,
				DIETDIARYDETAILS_COLUMN_FID,
				DIETDIARYDETAILS_COLUMN_SERVING_SIZE,
				DIETDIARYDETAILS_COLUMN_MEAL_TYPE,
				DIEDIARYDETAILS_COLUMN_CALORIES_INTAKE},null, null, null, null, null);
			
		}
		
		//get all Activity TABLE data
		public Cursor getAllActivityData(){
			return db.query(ACTIVITY_TABLE_NAME, new String[]{
				ACTIVITY_COLUMN_ID,
				ACTIVITY_COLUMN_ACTIVITY_CATEGORY,
				ACTIVITY_COLUMN_ACTIVITY_NAME,
				ACTIVITY_COLUMN_MET_VALUE,
				ACTIVITY_COLUMN_ACTIVITY_IMAGE}, null,null,null,null,null);
			
		}
		
		//get all ActivityDiaryDetails data
		public Cursor getAllActivityDiaryDetails(){
			return db.query(ACTIVITYDIARYDETAILS_TABLE_NAME, new String[]{
				ACTIVITYDIARYDETAILS_COLUMN_DATE,
				ACTIVITYDIARYDETAILS_COLUMN_ID,
				ACTIVITYDIARYDETAILS_COLUMN_DURATION,
				ACTIVITYDIARYDETAILS_COLUMN_CALORIESBURNED,
				ACTIVITYDIARYDETAILS_COLUMN_DISTANCE,
				ACTIVITYDIARYDETAILS_COLUMN_SPEED}, null,null,null,null,null);

		}
		
		//get all DailySummary Data
		public Cursor getAllDailySummary(){
			return db.query(DAILYSUMMARY_TABLE_NAME, new String[]{		
				DAILYSUMMARY_COLUMN_DATE,
				DAILYSUMMARY_COLUMN_TOTAL_CALORIES_INTAKE,
				DAILYSUMMARY_COLUMN_TOTAL_CALORIES_BURNED,
				DAILYSUMMARY_COLUMN_OLD_WEIGHT,
				DAILYSUMMARY_COLUMN_NEW_WEIGHT,
				DAILYSUMMARY_COLUMN_WEIGHT_CHANGE,
				DAILYSUMMARY_COLUMN_RDA_REMAINING}, null,null,null,null,null);
		}
		
 //************************** GET SINGLE ITEM METHOD******************************//   	
    	
		//get food item data
				public Cursor getFood(String get_foodname) {
			
					String selectQuery = "SELECT * FROM " + FOOD_TABLE_NAME + "WHERE " +FOOD_COLUMN_FOOD_NAME + "= " + get_foodname; 
					Log.e(TAG,selectQuery);
					Cursor c = db.rawQuery(selectQuery, null);
		    
					if(c != null)
						c.moveToFirst();
					Food fod= new Food();
					fod.setCalories(c.getDouble(c.getColumnIndex(FOOD_COLUMN_CALORIES)));
					fod.setCarbohydrates(c.getDouble(c.getColumnIndex(FOOD_COLUMN_CARBOHYDRATES)));
					fod.setProtein(c.getDouble(c.getColumnIndex(FOOD_COLUMN_PROTEIN)));
					fod.setDietary_Fibre(c.getDouble(c.getColumnIndex(FOOD_COLUMN_DIETARY_FIBRE)));
					fod.setSodium(c.getDouble(c.getColumnIndex(FOOD_COLUMN_SODIUM)));
					fod.setFats(c.getDouble(c.getColumnIndex(FOOD_COLUMN_FATS)));
					fod.setCholestrol(c.getDouble(c.getColumnIndex(FOOD_COLUMN_CHOLESTROL)));
					fod.setImage(c.getString(c.getColumnIndex(FOOD_COLUMN_IMAGE)));
					return c;
			
				}

		//get DIETDIARYDETAILS item data
		public Cursor getDietDiaryDetails(String get_DietDiaryDetailsDate){
			String selectQuery = "SELECT * FROM " + DIETDIARYDETAILS_TABLE_NAME + "WHERE " +DIETDIARYDETAILS_COLUMN_DATE + "= " + get_DietDiaryDetailsDate; 
			Log.e(TAG,selectQuery);
			Cursor c = db.rawQuery(selectQuery, null);
			
			if(c!=null)
				c.moveToFirst();
			DietDiaryDetails dd = new DietDiaryDetails();
			dd.setDd_Date(c.getString(c.getColumnIndex(DIETDIARYDETAILS_COLUMN_DATE)));
			dd.setF_ID(c.getString(c.getColumnIndex(DIETDIARYDETAILS_COLUMN_FID)));
			dd.setServing_Size(c.getDouble(c.getColumnIndex(DIETDIARYDETAILS_COLUMN_SERVING_SIZE)));
			dd.setCalories_Intake(c.getInt(c.getColumnIndex(DIEDIARYDETAILS_COLUMN_CALORIES_INTAKE)));
			dd.setMeal_Type(c.getString(c.getColumnIndex(DIETDIARYDETAILS_COLUMN_MEAL_TYPE)));
			return c;
			
		}
		
		//get ACTIVITY item data
		public Cursor getActivity(String get_ActId){
			String selectQuery = "SELECT * FROM " + ACTIVITY_TABLE_NAME + "WHERE " +ACTIVITY_COLUMN_ID + "= " + get_ActId; 
			Log.e(TAG,selectQuery);
			Cursor c = db.rawQuery(selectQuery, null);
			
			if(c!=null)
				c.moveToFirst();
			Activity act = new Activity();
			act.setA_Id(c.getInt(c.getColumnIndex(ACTIVITY_COLUMN_ID)));
			act.setActivity_Category(c.getString(c.getColumnIndex(ACTIVITY_COLUMN_ACTIVITY_CATEGORY)));
			act.setActivity_Name(c.getString(c.getColumnIndex(ACTIVITY_COLUMN_ACTIVITY_NAME)));
			act.setMet_Value(c.getInt(c.getColumnIndex(ACTIVITY_COLUMN_MET_VALUE)));
			act.setActivity_Image(c.getBlob(c.getColumnIndex(ACTIVITY_COLUMN_ACTIVITY_IMAGE)));
			return c;
		}
		
		//get ACTIVITYDIARYDETAILS item data
		public Cursor getActivityDiaryDetails(String get_ActddId){
			String selectQuery = "SELECT * FROM " + ACTIVITYDIARYDETAILS_TABLE_NAME + "WHERE " +ACTIVITYDIARYDETAILS_COLUMN_DATE + "= " + get_ActddId; 
			Log.e(TAG,selectQuery);
			Cursor c = db.rawQuery(selectQuery, null);
			
			if(c!=null)
				c.moveToFirst();
			ActivityDiaryDetails actDD = new ActivityDiaryDetails();
			actDD.setAd_Date(c.getString(c.getColumnIndex(ACTIVITYDIARYDETAILS_COLUMN_DATE)));
			actDD.setA_Id(c.getInt(c.getColumnIndex(ACTIVITYDIARYDETAILS_COLUMN_ID)));
			actDD.setDuration(c.getInt(c.getColumnIndex(ACTIVITYDIARYDETAILS_COLUMN_DURATION)));
			actDD.setCalories_Burned(c.getInt(c.getColumnIndex(ACTIVITYDIARYDETAILS_COLUMN_CALORIESBURNED)));
			actDD.setDistance(c.getInt(c.getColumnIndex(ACTIVITYDIARYDETAILS_COLUMN_DISTANCE)));
			actDD.setSpeed(c.getDouble(c.getColumnIndex(ACTIVITYDIARYDETAILS_COLUMN_SPEED)));
			return c;
		}
		
		//get DAILYSUMMARY item data
		public Cursor getDailySummary(String get_DS){
			String selectQuery = "SELECT * FROM " + DAILYSUMMARY_TABLE_NAME + "WHERE " +DAILYSUMMARY_COLUMN_DATE + "= " + get_DS; 
			Log.e(TAG,selectQuery);
			Cursor c = db.rawQuery(selectQuery, null);
			
			if(c!=null)
				c.moveToFirst();
			DailySummary ds = new DailySummary();
			ds.setDs_Date(c.getString(c.getColumnIndex(DAILYSUMMARY_COLUMN_DATE)));
			ds.setTotal_Calories_Intake(c.getInt(c.getColumnIndex(DAILYSUMMARY_COLUMN_TOTAL_CALORIES_INTAKE)));
			ds.setTotal_Calories_Burned(c.getInt(c.getColumnIndex(DAILYSUMMARY_COLUMN_TOTAL_CALORIES_BURNED)));
			ds.setOld_Weight(c.getDouble(c.getColumnIndex(DAILYSUMMARY_COLUMN_OLD_WEIGHT)));
			ds.setNew_Weight(c.getDouble(c.getColumnIndex(DAILYSUMMARY_COLUMN_NEW_WEIGHT)));
			ds.setWeight_Change(c.getDouble(c.getColumnIndex(DAILYSUMMARY_COLUMN_WEIGHT_CHANGE)));
			ds.setRda_Remaining(c.getInt(c.getColumnIndex(DAILYSUMMARY_COLUMN_RDA_REMAINING)));
			
			return c;
		}
//*********************UPDATE METHOD**********************************************//

		//Update food data
				public int updateFood(Food updateFd){
			
					ContentValues values = new ContentValues();
					values.put(FOOD_COLUMN_FOOD_NAME, updateFd.getFood_Name());
					values.put(FOOD_COLUMN_CALORIES, updateFd.getCalories());
					values.put(FOOD_COLUMN_CARBOHYDRATES, updateFd.getCarbohydrates());
					values.put(FOOD_COLUMN_PROTEIN, updateFd.getProtein());
					values.put(FOOD_COLUMN_DIETARY_FIBRE, updateFd.getDietary_Fibre());
					values.put(FOOD_COLUMN_SODIUM, updateFd.getSodium());
					values.put(FOOD_COLUMN_FATS, updateFd.getFats());
				    values.put(FOOD_COLUMN_CHOLESTROL, updateFd.getCholestrol());
					values.put(FOOD_COLUMN_IMAGE, updateFd.getImage());
			
					//update row
					return db.update(FOOD_TABLE_NAME, values, FOOD_COLUMN_ID + "= ?", new String[] {String.valueOf(updateFd.getF_ID())});
			
				}
		
		//Update DietDiaryDetails data
		public int updateDietDiaryDetails(DietDiaryDetails updateDd){
			ContentValues values3 = new ContentValues();
			values3.put(DIETDIARYDETAILS_COLUMN_DATE, updateDd.getDd_Date());
			values3.put(DIETDIARYDETAILS_COLUMN_FID, updateDd.getF_ID());
			values3.put(DIETDIARYDETAILS_COLUMN_SERVING_SIZE, updateDd.getServing_Size());
			values3.put(DIEDIARYDETAILS_COLUMN_CALORIES_INTAKE, updateDd.getCalories_Intake());
			values3.put(DIETDIARYDETAILS_COLUMN_MEAL_TYPE, updateDd.getMeal_Type());
			
			//update row
			return db.update(DIETDIARYDETAILS_TABLE_NAME, values3, DIETDIARYDETAILS_COLUMN_DATE + "= ?", new String[] { String.valueOf(updateDd.getDd_Date())});
		}
		
		//Update Activity data
		public int updateActivity(Activity updateAct){
			ContentValues values4 = new ContentValues();
			values4.put(ACTIVITY_COLUMN_ID, updateAct.getA_Id());
			values4.put(ACTIVITY_COLUMN_ACTIVITY_CATEGORY, updateAct.getActivity_Category());
			values4.put(ACTIVITY_COLUMN_ACTIVITY_NAME, updateAct.getActivity_Name());
			values4.put(ACTIVITY_COLUMN_MET_VALUE, updateAct.getMet_Value());
			values4.put(ACTIVITY_COLUMN_ACTIVITY_IMAGE, updateAct.getActivity_Image());
			
			//update row
			return db.update(ACTIVITY_TABLE_NAME, values4, ACTIVITY_COLUMN_ID + "= ?", new String[]{String.valueOf(updateAct.getA_Id())});
		}
		
		
		//Update Activity Diary Details
		public int updateActivityDiaryDetails(ActivityDiaryDetails updateActDD){
			ContentValues values5 = new ContentValues();
			values5.put(ACTIVITYDIARYDETAILS_COLUMN_DATE, updateActDD.getAd_Date());
			values5.put(ACTIVITYDIARYDETAILS_COLUMN_ID, updateActDD.getA_Id());
			values5.put(ACTIVITYDIARYDETAILS_COLUMN_DURATION, updateActDD.getDuration());
			values5.put(ACTIVITYDIARYDETAILS_COLUMN_CALORIESBURNED, updateActDD.getCalories_Burned());
			values5.put(ACTIVITYDIARYDETAILS_COLUMN_DISTANCE, updateActDD.getDistance());
			values5.put(ACTIVITYDIARYDETAILS_COLUMN_SPEED, updateActDD.getSpeed());
			
			//update row
			return db.update(ACTIVITYDIARYDETAILS_TABLE_NAME, values5, ACTIVITYDIARYDETAILS_COLUMN_DATE+ "= ?", new String[]{String.valueOf(updateActDD.getAd_Date())});
		}
		
		
		//Update DailySummary Details
		public int updateDailySummary(DailySummary updateDS){
			ContentValues values6 = new ContentValues();
			values6.put(DAILYSUMMARY_COLUMN_DATE, updateDS.getDs_Date());
			values6.put(DAILYSUMMARY_COLUMN_TOTAL_CALORIES_INTAKE, updateDS.getTotal_Calories_Intake());
			values6.put(DAILYSUMMARY_COLUMN_TOTAL_CALORIES_BURNED, updateDS.getTotal_Calories_Burned());
			values6.put(DAILYSUMMARY_COLUMN_OLD_WEIGHT, updateDS.getOld_Weight());
			values6.put(DAILYSUMMARY_COLUMN_NEW_WEIGHT, updateDS.getNew_Weight());
			values6.put(DAILYSUMMARY_COLUMN_WEIGHT_CHANGE, updateDS.getWeight_Change());
			values6.put(DAILYSUMMARY_COLUMN_RDA_REMAINING, updateDS.getRda_Remaining());
			
			//update row
			return db.update(DAILYSUMMARY_TABLE_NAME, values6, DAILYSUMMARY_COLUMN_DATE+ "= ?", new String[]{String.valueOf(updateDS.getDs_Date())});
		}
		
//********************** DELETE METHOD**************************** //

		//Delete FOOD data
		public void deleteFood(long deleteId){
			db.delete(FOOD_TABLE_NAME, FOOD_COLUMN_ID + "= ?", new String[] { String.valueOf(deleteId)});
			db.close();
		}
		
		//Delete DietDiaryDetails data
		public void  deleteDietDiaryDetails(long deleteDd){
			db.delete(DIETDIARYDETAILS_TABLE_NAME, DIETDIARYDETAILS_COLUMN_DATE + "= ?", new String[]{String.valueOf(deleteDd)});
		}
    
	    //Delete Activity data
		public void deleteActivity(long deleteAct){
			db.delete(ACTIVITY_TABLE_NAME, ACTIVITY_COLUMN_ID + "= ?", new String[]{String.valueOf(deleteAct)});
		}
		
		//Delete ActivityDiaryDetails data
		public void deleteActivityDiaryDetails(long deleteActDD){
			db.delete(ACTIVITYDIARYDETAILS_TABLE_NAME, ACTIVITYDIARYDETAILS_COLUMN_DATE + "= ?", new String[]{String.valueOf(deleteActDD)});
		}
		
		//Delete DailySummary data
		public void deleteDailySummary(long deleteDS){
			db.delete(DAILYSUMMARY_TABLE_NAME, DAILYSUMMARY_COLUMN_DATE + "= ?", new String[]{String.valueOf(deleteDS)});
		}
    
		//SEARCH METHOD
				public Cursor searchFood(String search) throws SQLException {
					Log.e(TAG,search);
					String selectQuery = "SELECT " + FOOD_COLUMN_FOOD_NAME + "," + FOOD_COLUMN_IMAGE + "  FROM " + FOOD_TABLE_NAME + "WHERE " +FOOD_COLUMN_FOOD_NAME + "LIKE " + "%"+  search  +"%";
					Log.e(TAG,selectQuery);
					
					Cursor c = db.rawQuery(selectQuery, null);
					if(c!=null)
						c.moveToFirst();
					return c;
				}
    
  }

