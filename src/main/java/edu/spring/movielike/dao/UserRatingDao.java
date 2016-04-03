package edu.spring.movielike.dao;

import edu.spring.movielike.model.UserRating;

public interface UserRatingDao<M, U> {
	public void submitVote(M movie, U user, int rating);
	public UserRating getUserRating(M movie, U user);
}
