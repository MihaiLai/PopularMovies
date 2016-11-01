package com.mihai.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MoviesProvider extends ContentProvider {

	private MoviesSQLiteHelper dbHelper;
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	static final int MOVIE = 100;
	static final int MOVIE_ITEM = 101;
	static final int MOVIE_SEARCH_BY_MOVIE_ID = 102;

	static {
		matcher.addURI(MovieContract.AUTHORITY, "movies/", MOVIE);
		matcher.addURI(MovieContract.AUTHORITY, "movies/#", MOVIE_ITEM);
		// 根据电影ID查询的
		matcher.addURI(MovieContract.AUTHORITY, "movies/search/#",
				MOVIE_SEARCH_BY_MOVIE_ID);
	}

	private static final String sMovieIdSelection = MovieContract.MOVIES + "."
			+ MovieContract.MOVIE_ID + " = ? ";

	@Override
	public boolean onCreate() {
		dbHelper = new MoviesSQLiteHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor;
		int code = matcher.match(uri);
		switch (code) {
		case MOVIE:
			cursor = db.query(MovieContract.MOVIES, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case MOVIE_ITEM:
			long id = ContentUris.parseId(uri);
			cursor = db.query(MovieContract.MOVIES, projection, "_id=?",
					new String[] { id + "" }, null, null, null);
			break;
		case MOVIE_SEARCH_BY_MOVIE_ID:
			long movieId = ContentUris.parseId(uri);
			String movieSelection = sMovieIdSelection;
			String[] movieSelectionArgs = new String[] { "" + movieId };
			cursor = db.query(MovieContract.MOVIES, projection, movieSelection,
					movieSelectionArgs, null, null, null);
			break;
		default:
			throw new RuntimeException("uri is not legal");
		}
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		int code = matcher.match(uri);
		switch (code) {
		case MOVIE:
			return MovieContract.CONTENT_TYPE;
		case MOVIE_ITEM:
			return MovieContract.CONTENT_ITEM_TYPE;
		case MOVIE_SEARCH_BY_MOVIE_ID:
			return MovieContract.CONTENT_ITEM_TYPE;
		default:
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int code = matcher.match(uri);
		if (code == MOVIE) {
			long id = db.insert(MovieContract.MOVIES, null, values);
			uri = ContentUris.withAppendedId(uri, id);
			return uri;
		} else {
			db.close();
			throw new RuntimeException("uri is not legal");
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int code = matcher.match(uri);
		int deleteCount = -1;
		if (code == MOVIE) {
			deleteCount = db.delete(MovieContract.MOVIES, selection,
					selectionArgs);
		} else if (code == MOVIE_ITEM) {
			long id = ContentUris.parseId(uri);
			deleteCount = db.delete(MovieContract.MOVIES, "_id" + id, null);
		} else {
			db.close();
			throw new RuntimeException("uri is not legal");
		}
		return deleteCount;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int code = matcher.match(uri);
		int updateCount = -1;
		if (code == MOVIE) {
			updateCount = db.update(MovieContract.MOVIES, values, selection,
					selectionArgs);
		} else if (code == MOVIE_ITEM) {
			long id = ContentUris.parseId(uri);
			updateCount = db.update(MovieContract.MOVIES, values, "_id=?",
					new String[] { id + "" });
		} else {
			db.close();
			throw new RuntimeException("uri is not legal");
		}
		return updateCount;
	}

}
