package com.mihai.movies.fragment;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mihai.movies.R;
import com.mihai.movies.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {
	private String titleText;
	private String pictureUrl;
	private String movieDate;
	private String voteAverage;
	private String movieOverView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater
				.inflate(R.layout.fragment_detail, container, false);

		Intent intent = getActivity().getIntent();
		long movieId = intent.getLongExtra(MovieContract.MOVIE_ID, -1);
		Uri detailUriPath = MovieContract.CONTENT_URI.buildUpon()
				.appendPath("search").build();
		Uri uri = ContentUris.withAppendedId(detailUriPath, movieId);
		ContentResolver contentResolver = getActivity().getContentResolver();
		Cursor cursor = contentResolver.query(uri, null, null, null, null);

		if (cursor.moveToFirst()) {
			titleText = cursor.getString(cursor
					.getColumnIndex(MovieContract.MOVIE_TITLE));
			pictureUrl = cursor.getString(cursor
					.getColumnIndex(MovieContract.MOVIE_PICTURE_URL));
			movieDate = cursor.getString(cursor
					.getColumnIndex(MovieContract.MOVIE_DATE));
			voteAverage = cursor.getString(cursor
					.getColumnIndex(MovieContract.VOTE_AVERAGE));
			movieOverView = cursor.getString(cursor
					.getColumnIndex(MovieContract.OVERVIEW));

		}

		TextView title = (TextView) root.findViewById(R.id.movie_title);
		title.setText(titleText);

		ImageView moviePicture = (ImageView) root
				.findViewById(R.id.movie_detial_picture);
		Picasso.with(getActivity())
				.load(MovieContract.BASE_PICTURE_URL + pictureUrl)
				.into(moviePicture);

		TextView dateText = (TextView) root.findViewById(R.id.release_date);
		dateText.setText(movieDate);

		TextView vote = (TextView) root.findViewById(R.id.vote_average);
		vote.setText("" + voteAverage + "/10");

		TextView overViewText = (TextView) root
				.findViewById(R.id.over_view_text);
		overViewText.setText(movieOverView);

		return root;
	}
}
