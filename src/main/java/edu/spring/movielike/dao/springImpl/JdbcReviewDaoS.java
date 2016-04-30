package edu.spring.movielike.dao.springImpl;

import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import edu.spring.movielike.dao.ReviewDao;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.Review;
import edu.spring.movielike.model.User;

public class JdbcReviewDaoS extends JdbcDaoSupport implements ReviewDao<Review, Movie, User> {

	public void addReview(Review review) { 
		String sql = "INSERT INTO review (author, movie_id, content) VALUES (?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[] {review.getAuthor(), review.getMovieId(), review.getContent()});				
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Review> getReviewsByMovie(Movie movie) {
		String sql = "SELECT * FROM review WHERE movie_id = ? ORDER BY id DESC";
		ArrayList<Review> reviewList = (ArrayList<Review>) getJdbcTemplate().query(sql, new Object[] 
				{movie.getId()}, new BeanPropertyRowMapper(Review.class));
		return reviewList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Review> getReviewsByUser(User user) {
		String sql = "SELECT * FROM review WHERE author = ? ORDER BY id DESC";
		ArrayList<Review> reviewList = (ArrayList<Review>) getJdbcTemplate().query(sql, new Object[] 
				{user.getUsername()}, new BeanPropertyRowMapper(Review.class));
		return reviewList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Review findReview(int id) {
		String sql = "SELECT * FROM review WHERE id = ?";
		Review review = (Review) getJdbcTemplate().queryForObject(sql, new Object[] {id}, 
				new BeanPropertyRowMapper(Review.class));
		return review;
	}

	public void deleteReview(Review review) {
		String sql = "DELETE FROM review WHERE id = ?";
		getJdbcTemplate().update(sql, new Object[] {review.getId()});				
	}

	public void updateReview(Review review) {
		String sql = "UPDATE review SET content = ? WHERE id = ?";
		getJdbcTemplate().update(sql, new Object[] {review.getContent(), review.getId()});				
	}
	
}
