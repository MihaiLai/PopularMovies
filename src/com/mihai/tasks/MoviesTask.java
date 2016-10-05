package com.mihai.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mihai.whatmovies.R;
import com.mihai.whatmovies.adapter.MyAdapter;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.AsyncTask;
import android.text.GetChars;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

public class MoviesTask extends AsyncTask<String, Void, String> {
	private String moviesJsonData;
	private ArrayList<String> pictureUrlList = new ArrayList<String>();
	private Context context;
	private MyAdapter adapter;
	private onFinishListener listener;

	public MoviesTask(Context context, MyAdapter adapter,
			onFinishListener listener) {
		this.context = context;
		this.adapter = adapter;
		this.listener = listener;
	}

	@Override
	protected String doInBackground(String... params) {
		BufferedReader reader;
		try {
			
			URL url = new URL(
					"http://api.themoviedb.org/3/movie/popular?api_key=<your api key here>");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			if (inputStream == null) {
				return null;
			}
			StringBuffer buffer = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;

			while ((line = reader.readLine()) != null) {
				buffer.append(line + "/n");
			}
			// Log.d("TAG", buffer.toString());
			moviesJsonData = buffer.toString();
			getMoviesDataFromJson(moviesJsonData);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moviesJsonData;
	}

	public void getMoviesDataFromJson(String json) {
		try {
			JSONObject popMoviesJson = new JSONObject(json);
			JSONArray moviesArray = popMoviesJson.getJSONArray("results");
			for (int i = 0; i < moviesArray.length(); i++) {
				JSONObject movieObject = moviesArray.getJSONObject(i);
				String moviePictureUrl = movieObject.getString("poster_path");
				pictureUrlList.add(moviePictureUrl);
			}
			// Log.d("TAG", pictureUrlList.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null & listener != null) {
			//when finish loading data ,deliver it to adapter on MainActivity
			listener.afterTaskFinish(pictureUrlList);
		}
	}

	public interface onFinishListener {
		public void afterTaskFinish(ArrayList<String> pictureUrlList);
	}

}
