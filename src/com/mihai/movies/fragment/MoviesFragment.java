package com.mihai.movies.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.mihai.movies.R;
import com.mihai.movies.activities.MovieDetailActivity;
import com.mihai.movies.adapter.MyAdapter;
import com.mihai.movies.data.MovieData;
import com.mihai.movies.tasks.MoviesTask;
import com.mihai.movies.tasks.MoviesTask.onFinishListener;

public class MoviesFragment extends Fragment {
	private GridView gridView;
	private ArrayList<MovieData> movieDataList;
	public static final String MOVIE_TITLE = "title";
	public static final String MOVIE_DATE = "release_date";
	public static final String MOVIE_PICTURE_URL="poster_path";
	public static final String OVERVIEW = "overview";
	public static final String VOTE_AVERAGE= "vote_average";
	public static final String MOVIE_BEAN= "moviedata";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_main, container, false);
		gridView = (GridView) root.findViewById(R.id.grid_view);
		final MyAdapter adapter = new MyAdapter(getActivity());
		gridView.setAdapter(adapter);
		MoviesTask task = new MoviesTask(new onFinishListener() {

			@Override
			public void afterTaskFinish(ArrayList<MovieData> dataList) {
				// the dataList here store the information of all movies we load from MoviesTask,
				//include movie title ,movie overview,picture url 
				movieDataList = dataList;
				ArrayList<String> pictureUrlList = new ArrayList<String>();
				
				for (int i = 0; i < dataList.size(); i++) {
					pictureUrlList.add(dataList.get(i).getMoviePictureUrl());
				}
				adapter.setPictureUrlList(pictureUrlList);
				Log.d("TAG", pictureUrlList.toString() + "finish array");
				adapter.notifyDataSetChanged();
			}
		});
		task.execute("");
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), MovieDetailActivity.class);
				if (movieDataList != null) {
					MovieData movieData = movieDataList.get(position);					
					i.putExtra(MoviesFragment.MOVIE_BEAN, movieData);
				}
				startActivity(i);
				
			}
		});
		return root;
	}
}
