package com.mihai.movies.tasks;

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

import com.mihai.movies.data.MovieData;

import android.os.AsyncTask;
public class MoviesTask extends AsyncTask<String, Void, String> {
	private String moviesJsonData;
	private ArrayList<MovieData> moviesDataList = new ArrayList<MovieData>();
	
	private onFinishListener listener;

	public MoviesTask(onFinishListener listener) {

		this.listener = listener;
	}

	@Override
	protected String doInBackground(String... params) {
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		try {
			
			URL url = new URL(
					"http://api.themoviedb.org/3/movie/popular?api_key=[your api key here]");
			connection = (HttpURLConnection) url
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
		}finally{
			if (connection != null) {
				connection.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return moviesJsonData;
	}

	public void getMoviesDataFromJson(String json) {
		try {
			JSONObject popMoviesJson = new JSONObject(json);
			JSONArray moviesArray = popMoviesJson.getJSONArray("results");
			for (int i = 0; i < moviesArray.length(); i++) {
				JSONObject movieObject = moviesArray.getJSONObject(i);
				String movieTitle = movieObject.getString("title");
				String moviePictureUrl = movieObject.getString("poster_path");
				String overview = movieObject.getString("overview");
				double voteAverage = movieObject.getDouble("vote_average");
				MovieData movieData = new MovieData(movieTitle, moviePictureUrl, overview, voteAverage);
				moviesDataList.add(movieData);
				
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
			listener.afterTaskFinish(moviesDataList);
		}
	}

	public interface onFinishListener {
		public void afterTaskFinish(ArrayList<MovieData> moviesDataList);
	}

}
