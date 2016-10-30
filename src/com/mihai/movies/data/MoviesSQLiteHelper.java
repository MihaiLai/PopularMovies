package com.mihai.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesSQLiteHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "movies.db";
    private static final int DB_VERSION = 1;
   /*
    * TABLE_NAME:
    *               movies
    *
    * COLUMN_NAME:
    *               _id
    *               id
    *               title
    *               release_date
    *               poster_path
    *               vote_average
    *               overview
    *               popularity
    */
    
    
    public static final String CREATTABLE = 
    					"CREATE TABLE " + MovieContract.MOVIES 
    					+ "(" + MovieContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
    					+ MovieContract.MOVIE_ID + " INTEGER UNIQUE, "
    					+ MovieContract.MOVIE_TITLE + " TEXT, "
    					+ MovieContract.MOVIE_DATE + " TEXT, "
    					+ MovieContract.MOVIE_PICTURE_URL + " TEXT, "
    					+ MovieContract.OVERVIEW + " TEXT, "
    					+ MovieContract.VOTE_AVERAGE + " TEXT, "
    					+ MovieContract.MOVIE_POPULARITY + " REAL);";
    					
    					
    
	public MoviesSQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATTABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
