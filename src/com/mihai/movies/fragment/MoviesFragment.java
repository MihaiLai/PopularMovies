package com.mihai.movies.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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
import com.mihai.movies.sync.MoviesSyncAdapter;

public class MoviesFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private GridView gridView;
	private MyAdapter adapter;
	private static final int MVOIE_LOADER = 0;

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
		getLoaderManager().initLoader(MVOIE_LOADER, null, this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(getActivity(),
				MovieContract.CONTENT_URI, null, null, null, null);
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
}
