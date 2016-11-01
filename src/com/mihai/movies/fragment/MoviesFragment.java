package com.mihai.movies.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.mihai.movies.data.MovieContract;
import com.mihai.movies.sync.MoviesSyncAdapter;
import com.mihai.movies.util.Util;

public class MoviesFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<Cursor>, OnSharedPreferenceChangeListener {
	private GridView gridView;
	private MyAdapter adapter;
	private static final int MOVIE_MAIN_LOADER = 0;
    private static final String SORT_POPULARITY = "popular";
    private static final String SORT_VOTE_AVERAGE = "vote_average";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_main, container, false);
		gridView = (GridView) root.findViewById(R.id.grid_view);

		MoviesSyncAdapter.syncImmediately(getActivity());

		adapter = new MyAdapter(getActivity(), null, 1);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Cursor movieCursor = (Cursor) parent.getAdapter().getItem(
						position);
				if (movieCursor != null) {

					int movieIdIndex = movieCursor
							.getColumnIndex(MovieContract.MOVIE_ID);
					long movieId = movieCursor.getLong(movieIdIndex);
					//点击对应电影把电影ID传递给详情的页面，然后详情页面通过电影id查询其他信息
					Intent i = new Intent(getActivity(),MovieDetailActivity.class);
					i.putExtra(MovieContract.MOVIE_ID, movieId);
					startActivity(i);
				}
			}
		});
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		getLoaderManager().initLoader(MOVIE_MAIN_LOADER, null, this);
		
		SharedPreferences sp  = PreferenceManager.getDefaultSharedPreferences(getActivity());
		sp.registerOnSharedPreferenceChangeListener(this);
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void onPause() {
		super.onPause();
		
	}
	@Override
	public void onDestroy() {
		SharedPreferences sp  = PreferenceManager.getDefaultSharedPreferences(getActivity());
		sp.unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		//根据设置偏好，来决定电影的排序-热门程度/评分高低
		String sortType = Util.getPreferenceSort(getActivity());
		String sortOrder = null;
		switch (sortType) {
		case SORT_POPULARITY:
			sortOrder = MovieContract.MOVIE_POPULARITY + " DESC LIMIT 20";
			break;
		case SORT_VOTE_AVERAGE:
			sortOrder = MovieContract.VOTE_AVERAGE + " DESC LIMIT 20";

		default:
			break;
		}
		CursorLoader loader = new CursorLoader(getActivity(),
				MovieContract.CONTENT_URI, null, null, null, sortOrder);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		adapter.swapCursor(null);
	}
	//当设置改变的时候更新重新调用CursorLoader，让ui更新
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		onSortChanged();	
	}
	
	public void onSortChanged(){
		Log.d("TAG", "改变了设置");
        getLoaderManager().restartLoader(MOVIE_MAIN_LOADER,null,this);
    }
	
}
