package com.mihai.movies.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.mihai.movies.R;
import com.mihai.movies.activities.MovieDetailActivity;
import com.mihai.movies.adapter.MyAdapter;
import com.mihai.movies.data.MovieContract;
import com.mihai.movies.tasks.MoviesTask;
import com.mihai.movies.tasks.MoviesTask.onFinishListener;

public class MoviesFragment extends Fragment {
	private GridView gridView;
	private ArrayList<String> pictureUrlList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_main, container, false);
		gridView = (GridView) root.findViewById(R.id.grid_view);
		final MyAdapter adapter = new MyAdapter(getActivity());
		gridView.setAdapter(adapter);
		MoviesTask task = new MoviesTask(getActivity(), new onFinishListener() {

			@Override
			public void afterTaskFinish() {
				ContentResolver resolver = getActivity().getContentResolver();
				Cursor cursor = resolver.query(MovieContract.CONTENT_URI, null,
						null, null, null);
				while (cursor.moveToNext()) {
					String picURL = cursor.getString(cursor
							.getColumnIndex(MovieContract.MOVIE_PICTURE_URL));
					pictureUrlList.add(picURL);
				}
				adapter.setPictureUrlList(pictureUrlList);
				adapter.notifyDataSetChanged();

			}

		});

		task.execute("");
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), MovieDetailActivity.class);
				i.putExtra("id", position + 1);
				startActivity(i);

			}
		});
		return root;
	}
}
