package com.mihai.movies.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.mihai.movies.R;
import com.mihai.movies.adapter.MyAdapter;
import com.mihai.movies.tasks.MoviesTask;
import com.mihai.movies.tasks.MoviesTask.onFinishListener;

public class MoviesFragment extends Fragment {
	private GridView gridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_main, container, false);
		gridView = (GridView) root.findViewById(R.id.grid_view);
		final MyAdapter adapter = new MyAdapter(getActivity());
		gridView.setAdapter(adapter);
		MoviesTask task = new MoviesTask(getActivity(), adapter,
				new onFinishListener() {

					@Override
					public void afterTaskFinish(ArrayList<String> pictureUrlList) {
						adapter.setPictureUrlList(pictureUrlList);
						Log.d("TAG", pictureUrlList.toString() + "finish array");
						adapter.notifyDataSetChanged();
					}
				});
		task.execute("");
		return root;
	}
}
