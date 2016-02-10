package edu.spring.movielike.dao;

import java.util.ArrayList;

public interface ReviewDao<R, M, U> {
	public void addReview(R review);
	public ArrayList<R> getReviewsByMovie(M movie);
	public ArrayList<R> getReviewsByUser(U user);
	public void deleteReview(R review);
	public R findReview(int id);
	public void updateReview(R review);
}
