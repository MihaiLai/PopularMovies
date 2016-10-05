package com.mihai.movies.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.mihai.movies.R;
import com.squareup.picasso.Picasso;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> pictureUrlList = new ArrayList<String>();
	//this is the base url of picture,add  relative url you load from   
	public static final String BASE_PICTURE_URL = "https://image.tmdb.org/t/p/w185";

	public MyAdapter(Context context) {
		this.context = context;
	}

	public void setPictureUrlList(ArrayList<String> pictureUrlList) {
		this.pictureUrlList = pictureUrlList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictureUrlList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.movies_item, null);
		ImageButton moviePicture = (ImageButton) convertView
				.findViewById(R.id.movies_picture);
		if (pictureUrlList != null) {
			Picasso.with(context)
					.load(BASE_PICTURE_URL + pictureUrlList.get(position))
					.into(moviePicture);
		}
		return convertView;
	}

}