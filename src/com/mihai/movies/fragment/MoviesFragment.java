package com.mihai.movies.fragment;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
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
import com.mihai.movies.tasks.MoviesTask;

public class MoviesFragment extends Fragment {
	private GridView gridView;
	private MyAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_main, container, false);
		gridView = (GridView) root.findViewById(R.id.grid_view);
		
		MoviesTask task = new MoviesTask(getActivity());
		task.execute("");
		
		ContentResolver resolver = getActivity().getContentResolver();
		Cursor cursor = resolver.query(MovieContract.CONTENT_URI, null,
				null, null, null);
		if (cursor !=null) {
			//使用CursorAdapter，如果有数据自动查询
			adapter = new MyAdapter(getActivity(), cursor, 1);
			gridView.setAdapter(adapter);
		}
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), MovieDetailActivity.class);
				i.putExtra("id", position + 1);
				startActivity(i);

			}
		});
		return root;
	}
}
