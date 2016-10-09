package com.mihai.movies.fragment;

import com.mihai.movies.R;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieDetailFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater
				.inflate(R.layout.fragment_detail, container, false);
		TextView title = (TextView) root.findViewById(R.id.movie_title);
		Intent intent = getActivity().getIntent();
		String movieTitle = intent.getStringExtra(MoviesFragment.MOVIE_TITLE);
		String pictureUrl = intent.getStringExtra(MoviesFragment.MOVIE_PICTURE_URL);
		String overview = intent.getStringExtra(MoviesFragment.OVERVIEW);
		double voteAverage = intent.getDoubleExtra(MoviesFragment.VOTE_AVERAGE, 0);
		title.setText("电影名字：" + movieTitle + "  电影概述：" + overview + " 电影评分：" + voteAverage);
		return root;
	}
}
