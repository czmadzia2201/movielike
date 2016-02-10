package edu.spring.movielike.dao;

import java.util.ArrayList;

public interface UserMovieDao<U, M> {
	public void linkUserMovie(U user, M movie, int fav);
	public void unlinkUserMovie(U user, M movie, int fav);
	public boolean isUserMovieLinked(U user, M movie, int fav);
	public ArrayList<M> getUserLinkedMovies(U user, int fav);
}
