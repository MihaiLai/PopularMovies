package com.mihai.movies.activities;

import android.app.Activity;
import android.os.Bundle;
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

}
