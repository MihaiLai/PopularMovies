package com.mihai.movies.fragment;

import com.mihai.movies.R;
import com.mihai.movies.adapter.MyAdapter;
import com.mihai.movies.data.MovieData;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater
				.inflate(R.layout.fragment_detail, container, false);
		
		Intent intent = getActivity().getIntent();
		MovieData movieData = (MovieData) intent.getSerializableExtra(MoviesFragment.MOVIE_BEAN);
		
		TextView title = (TextView) root.findViewById(R.id.movie_title);
		title.setText(movieData.getMovieTitle());
		
		ImageView moviePicture = (ImageView) root.findViewById(R.id.movie_detial_picture);		
		Picasso.with(getActivity()).load(MyAdapter.BASE_PICTURE_URL + movieData.getMoviePictureUrl()).into(moviePicture);
		
		TextView dateText = (TextView) root.findViewById(R.id.release_date);
		dateText.setText(movieData.getMovieDate());
		
		TextView vote = (TextView) root.findViewById(R.id.vote_average);
		vote.setText("" + movieData.getVoteAverage() + "/10");
		
		TextView overViewText = (TextView) root.findViewById(R.id.over_view_text);
		overViewText.setText(movieData.getMovieOverview());
		
		return root;
	}
}
