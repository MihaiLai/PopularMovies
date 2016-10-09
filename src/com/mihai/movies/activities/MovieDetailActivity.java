package com.mihai.movies.activities;

import com.mihai.movies.R;
import com.mihai.movies.fragment.MovieDetailFragment;

import android.app.Activity;
import android.os.Bundle;

public class MovieDetailActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);
		getFragmentManager().beginTransaction().add(R.id.container_detail,
				new MovieDetailFragment()).commit();
	}
}
