package com.mihai.movies.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MoviesSyncService extends Service {
	private MoviesSyncAdapter moviesSyncAdapter = null;
	
	@Override
	public void onCreate() {
		
		if (moviesSyncAdapter == null) {
			synchronized (this) {
				if (moviesSyncAdapter == null) {
					moviesSyncAdapter = new MoviesSyncAdapter(getApplicationContext(), true);
				}
			}
		}
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		
		return moviesSyncAdapter.getSyncAdapterBinder();
	}

}
