package com.myhealthmemo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.widget.ListView;

import com.myhealthmemo.adapter.ActivityListAdapter;
import com.myhealthmemo.adapter.DBAdapter;
import com.myhealthmemo.model.ActivityListItem;

public class Activity_List_of_Activities extends Activity {

	private ListView lv;
	DBAdapter db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity__list_of__activities);
		db = new DBAdapter(this);	
		ActivityListAdapter sa = new ActivityListAdapter(this, generateData());
		lv = (ListView) findViewById(R.id.activity_list_Layout);
		lv.setAdapter(sa);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity__list_of__activities, menu);
		return true;
	}
	
	private ArrayList<ActivityListItem> generateData(){
		db.open();
		Cursor c = db.getAllActivityData();
        ArrayList<ActivityListItem> si = new ArrayList<ActivityListItem>();
        
        if(c!=null){
        	if(c.moveToFirst()){
        		do {
        			Bitmap image = convertImage(c.getBlob(c.getColumnIndexOrThrow("activity_Image")));
        			si.add(new ActivityListItem(image, c.getString(c.getColumnIndexOrThrow("activity_Name"))));
        		} while (c.moveToNext());
        	}
        }
        db.closeDB();
        return si;
    }
	
	private Bitmap convertImage(byte[] c){
		Bitmap myBitmap = BitmapFactory.decodeByteArray(c, 0, c.length);
		ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
		myBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
		return decodeSampledBitmapFromByte(stream.toByteArray(),50,50);
	}
	
	// please define following two methods in your activity
    public Bitmap decodeSampledBitmapFromByte(byte[] res,
                int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(res, 0, res.length,options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeByteArray(res, 0, res.length,options);
        }
         public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round((float)height / (float)reqHeight);
                } else {
                    inSampleSize = Math.round((float)width / (float)reqWidth);
                }
            }
            return inSampleSize;
        }

}
