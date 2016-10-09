package com.mihai.movies.data;

import java.io.Serializable;
/*
 * save somthing useful information for every moive.
 */
public class MovieData implements Serializable{
	private String movieTitle;
	private String moviePictureUrl;
	private String movieOverview;
	private double voteAverage;
	public MovieData(){
		
	}
	public MovieData(String movieTitle,String moviePictureUrl,String movieOverview,double voteAverage){
		this.movieTitle = movieTitle;
		this.moviePictureUrl = moviePictureUrl;
		this.movieOverview = movieOverview;
		this.voteAverage = voteAverage;		
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public String getMoviePictureUrl() {
		return moviePictureUrl;
	}
	public void setMoviePictureUrl(String moviePictureUrl) {
		this.moviePictureUrl = moviePictureUrl;
	}
	public String getMovieOverview() {
		return movieOverview;
	}
	public void setMovieOverview(String movieOverview) {
		this.movieOverview = movieOverview;
	}
	public double getVoteAverage() {
		return voteAverage;
	}
	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}
	
}
