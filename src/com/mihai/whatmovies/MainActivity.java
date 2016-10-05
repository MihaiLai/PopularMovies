package com.mihai.whatmovies;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.mihai.tasks.MoviesTask;
import com.mihai.tasks.MoviesTask.onFinishListener;
import com.mihai.whatmovies.adapter.MyAdapter;

public class MainActivity extends Activity {
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridView = (GridView) findViewById(R.id.grid_view);
		final MyAdapter adapter = new MyAdapter(this);
		gridView.setAdapter(adapter);
		MoviesTask task = new MoviesTask(this, adapter, new onFinishListener() {

			@Override
			public void afterTaskFinish(ArrayList<String> pictureUrlList) {
				adapter.setPictureUrlList(pictureUrlList);
				Log.d("TAG", pictureUrlList.toString() + "finish array");
				adapter.notifyDataSetChanged();
			}
		});
		task.execute("");
	}

}
