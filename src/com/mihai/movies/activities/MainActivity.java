package com.mihai.movies.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.mihai.movies.R;
import com.mihai.movies.fragment.MoviesFragment;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getFragmentManager().beginTransaction()
				.add(R.id.container_main, new MoviesFragment()).commit();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

}
