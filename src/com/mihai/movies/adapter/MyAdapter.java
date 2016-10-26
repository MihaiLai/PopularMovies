package com.mihai.movies.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.mihai.movies.R;
import com.mihai.movies.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MyAdapter extends BaseAdapter {
	private Context context;
	//获取所有图片的相对路径
	private ArrayList<String> pictureUrlList = new ArrayList<String>();

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
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.movies_item, null);
			viewHolder.moviePicture = (ImageView)convertView.findViewById(R.id.movies_picture);
			convertView.setTag(viewHolder);
		}else {
			viewHolder =(ViewHolder)convertView.getTag();
		}
		
		if (pictureUrlList != null) {
			Picasso.with(context)
					.load(MovieContract.BASE_PICTURE_URL + pictureUrlList.get(position))
					.into(viewHolder.moviePicture);
		}
		return convertView;
	}
	class ViewHolder{
		ImageView moviePicture;
	}

}