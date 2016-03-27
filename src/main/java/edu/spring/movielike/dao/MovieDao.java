package edu.spring.movielike.dao;

import java.util.ArrayList;

public interface MovieDao<M, MR> {
	public Integer persistMovie(M movie); // ok
	public void updateMovie(M movie); // ok
	public ArrayList<M> findAllMoviesByProperty(String searchCriteria, Object criteriaValue); // ok
	public ArrayList<M> findAllMovies(int status); // ok
	public M findMovieById(int id); // ok
	public M findMovieByIdWithStatus(int id, int status); // ok
	public MR findRejectedMovieById(int id); // ok
	public ArrayList<M> findMoviesAddedBy(String addedBy, int status); // ok
	public ArrayList<MR> findRejectedMoviesAddedBy(String addedBy); // ok
	public void updateMovieStatus(M movie, int status, String reason); // ok
	public void deleteMovie(M movie); // ok
	public void deleteRejectedMoviesAdmin(); // ok
	public void deleteRejectedMoviesUser(String addedBy); // ok
}
