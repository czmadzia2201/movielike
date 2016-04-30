package edu.spring.movielike.dao.hibernateImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import edu.spring.movielike.dao.ConnectionHandler;
import edu.spring.movielike.dao.ReviewDao;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.Review;
import edu.spring.movielike.model.User;

public class JdbcReviewDaoH implements ReviewDao<Review, Movie, User> {

	private ConnectionHandler connectionHandler = ConnectionHandler.getInstance();
	
	public void addReview(Review review) { 
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().save(review);		
		connectionHandler.closeCurrentSessionwithTransaction();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Review> getReviewsByMovie(Movie movie) {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Review.class);
		List<Review> reviewListRaw = criteria.add(Restrictions.eq("movieId", movie.getId())).list();
		ArrayList<Review> reviewList = new ArrayList<Review>(reviewListRaw);
		connectionHandler.closeCurrentSession();
		return reviewList; 
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Review> getReviewsByUser(User user) {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Review.class);
		List<Review> reviewListRaw = criteria.add(Restrictions.eq("author", user.getUsername())).list();
		ArrayList<Review> reviewList = new ArrayList<Review>(reviewListRaw);
		connectionHandler.closeCurrentSession();
		return reviewList; 
	}

	public void deleteReview(Review review) {
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().delete(review);
		connectionHandler.closeCurrentSessionwithTransaction();
	}

	public Review findReview(int id) {
		connectionHandler.openCurrentSession();
		Review review = (Review) connectionHandler.getCurrentSession().get(Review.class, id);
		connectionHandler.closeCurrentSession();
		return review;
	}

	public void updateReview(Review review) {
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().update(review);		
		connectionHandler.closeCurrentSessionwithTransaction();
	}
	
}
