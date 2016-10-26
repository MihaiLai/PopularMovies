package com.mihai.movies.data;

import android.net.Uri;

public class MovieContract {
	public static final Uri CONTENT_URI = Uri.parse("content://com.mihai.movies.proivder/movies");
	
	public static final String AUTHORITY = "com.mihai.movies.proivder";
	
	public static final String MOVIES = "movies";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_ID = "movieID";
    public static final String COLUMN_ID = "_id";
	public static final String MOVIE_DATE = "release_date";
	public static final String MOVIE_PICTURE_URL="poster_path";
	public static final String OVERVIEW = "overview";
	public static final String VOTE_AVERAGE= "vote_average";
	public static final String MOVIE_BEAN= "moviedata";
	public static final String MOVIE_POPULARITY= "popularity";
	//图片url的前部分
	public static final String BASE_PICTURE_URL = "https://image.tmdb.org/t/p/w185";
}
