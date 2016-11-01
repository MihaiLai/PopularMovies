package com.mihai.movies.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.setting) {
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
