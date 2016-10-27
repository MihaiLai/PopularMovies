package com.mihai.movies.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mihai.movies.R;
import com.mihai.movies.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MyAdapter extends CursorAdapter {

	public MyAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		ImageView moviePicture = (ImageView) view.findViewById(R.id.movies_picture);
		String picturePatch = MovieContract.BASE_PICTURE_URL
				+ cursor.getString(cursor
						.getColumnIndex(MovieContract.MOVIE_PICTURE_URL));
		Picasso.with(context).load(picturePatch).into(moviePicture);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.movies_item,null);
		return view;
	}

}