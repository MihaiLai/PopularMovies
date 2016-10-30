package com.mihai.movies.sync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.mihai.movies.R;
import com.mihai.movies.data.MovieContract;

public class MoviesSyncAdapter extends AbstractThreadedSyncAdapter {

	public static final int SYNC_INTERVAL = 3 * 60 * 60; // 三小时更新一次
	public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

	public MoviesSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {

		Log.d("TAG", "Starting sync");

		BufferedReader reader = null;
		HttpURLConnection connection = null;
		try {

			URL url = new URL(
					"http://api.themoviedb.org/3/movie/popular?api_key=955f2bbe709669ee48ca28642355e6be");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			if (inputStream == null) {
				return;
			}
			StringBuffer buffer = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;

			while ((line = reader.readLine()) != null) {
				buffer.append(line + "/n");
			}
			getMoviesDataFromJson(buffer.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return;

	}
	//根据json字符串解释
	public void getMoviesDataFromJson(String json) {
		try {
			ContentResolver contentResolver = getContext().getContentResolver();
			// 获取新的数据前把表清空
			contentResolver.delete(MovieContract.CONTENT_URI, null, null);
			JSONObject popMoviesJson = new JSONObject(json);
			JSONArray moviesArray = popMoviesJson.getJSONArray("results");
			for (int i = 0; i < moviesArray.length(); i++) {
				JSONObject movieObject = moviesArray.getJSONObject(i);
				ContentValues values = new ContentValues();
				values.put(MovieContract.MOVIE_ID,
						movieObject.getInt(MovieContract.MOVIE_ID));
				values.put(MovieContract.MOVIE_TITLE,
						movieObject.getString(MovieContract.MOVIE_TITLE));
				values.put(MovieContract.MOVIE_DATE,
						movieObject.getString(MovieContract.MOVIE_DATE));
				values.put(MovieContract.MOVIE_PICTURE_URL,
						movieObject.getString(MovieContract.MOVIE_PICTURE_URL));
				values.put(MovieContract.OVERVIEW,
						movieObject.getString(MovieContract.OVERVIEW));
				values.put(MovieContract.VOTE_AVERAGE,
						movieObject.getDouble(MovieContract.VOTE_AVERAGE));
				values.put(MovieContract.MOVIE_POPULARITY,
						movieObject.getDouble(MovieContract.MOVIE_POPULARITY));
				contentResolver.insert(MovieContract.CONTENT_URI, values);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static void syncImmediately(Context context) {
		Bundle bundle = new Bundle();
		bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
		bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
		ContentResolver.requestSync(getSyncAccount(context),
				context.getString(R.string.content_authority), bundle);
	}

	public static Account getSyncAccount(Context context) {

		AccountManager accountManager = (AccountManager) context
				.getSystemService(Context.ACCOUNT_SERVICE);

		Account newAccount = new Account(context.getString(R.string.app_name),
				context.getString(R.string.sync_account_type));

		if (null == accountManager.getPassword(newAccount)) {

			if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
				return null;
			}

			onAccountCreated(newAccount, context);
		}
		return newAccount;
	}

	private static void onAccountCreated(Account newAccount, Context context) {

		MoviesSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL,
				SYNC_FLEXTIME);

		ContentResolver.setSyncAutomatically(newAccount,
				context.getString(R.string.content_authority), true);

		syncImmediately(context);
	}

	public static void configurePeriodicSync(Context context, int syncInterval,
			int flexTime) {
		Account account = getSyncAccount(context);
		String authority = context.getString(R.string.content_authority);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			SyncRequest request = new SyncRequest.Builder()
					.syncPeriodic(syncInterval, flexTime)
					.setSyncAdapter(account, authority).setExtras(new Bundle())
					.build();
			ContentResolver.requestSync(request);
		} else {
			ContentResolver.addPeriodicSync(account, authority, new Bundle(),
					syncInterval);
		}
	}
}
