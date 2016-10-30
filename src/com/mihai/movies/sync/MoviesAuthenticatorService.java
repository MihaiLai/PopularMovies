package com.mihai.movies.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MoviesAuthenticatorService extends Service {
	private MoviesAuthenticator moviesAuthenticator;
	@Override
	public void onCreate() {
		
		moviesAuthenticator = new MoviesAuthenticator(this);
	}
	@Override
	public IBinder onBind(Intent intent) {
		
		return moviesAuthenticator.getIBinder();
	}

}
