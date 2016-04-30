package edu.spring.movielike.dao.springImpl;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import edu.spring.movielike.dao.UserRatingDao;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.User;
import edu.spring.movielike.model.UserRating;

public class JdbcUserRatingDaoS extends JdbcDaoSupport implements UserRatingDao<Movie, User> {

	public void submitVote(Movie movie, User user, int rating) {
		UserRating userRating = getUserRating(movie, user);
		if (userRating.getVotes() == 0) {
			persistRating(movie, user, rating);
		} else {
			updateRating(movie, user, rating, userRating.getRatingSum(), userRating.getVotes());
		}
	}

	private void persistRating(Movie movie, User user, int rating) {
		String sql = "INSERT INTO user_movie_rate values (?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[] {user.getUsername(), movie.getId(), 
				new Timestamp(Calendar.getInstance().getTime().getTime()), rating, 1});					
	}

	private void updateRating(Movie movie, User user, int rating, int currentSum, int currentVotes) {
		String sql = "UPDATE user_movie_rate SET last_rated = ?, rating_sum = ?, votes = ? WHERE movie_id = ? AND username = ?";
		getJdbcTemplate().update(sql, new Object[] {new Timestamp(Calendar.getInstance().getTime().getTime()), 
				currentSum + rating, currentVotes + 1, movie.getId(), user.getUsername()});					
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserRating getUserRating(Movie movie, User user) {
		UserRating userRating;
		try {
			String sql = "SELECT * FROM user_movie_rate WHERE movie_id = ? AND username = ?";
			userRating = (UserRating) getJdbcTemplate().queryForObject(sql, new Object[] 
				{movie.getId(), user.getUsername()}, new BeanPropertyRowMapper(UserRating.class));
		} catch (EmptyResultDataAccessException e) {
			userRating = new UserRating();
		}
		return userRating;
	}
	
}
