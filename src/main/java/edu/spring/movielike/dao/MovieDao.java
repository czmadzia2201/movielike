package edu.spring.movielike.dao;

import java.util.ArrayList;

public interface MovieDao<M, MR> {
	public Integer persistMovie(M movie);
	public void updateMovie(M movie);
	public ArrayList<M> findAllMoviesByProperty(String searchCriteria, Object criteriaValue);
	public ArrayList<M> findAllMovies(int status);
	public M findMovieById(int id);
	public M findMovieByIdWithStatus(int id, int status);
	public MR findRejectedMovieById(int id);
	public ArrayList<M> findMoviesAddedBy(String addedBy, int status);
	public ArrayList<MR> findRejectedMoviesAddedBy(String addedBy);
	public void updateMovieStatus(M movie, int status, String reason);
	public void deleteMovie(M movie); 
	public void deleteRejectedMoviesAdmin();
	public void deleteRejectedMoviesUser(String addedBy);
}
