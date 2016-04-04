package edu.spring.movielike.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;

import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.User;
import edu.spring.movielike.model.UserRating;

public class JdbcUserRatingDaoH implements UserRatingDao<Movie, User> {

	private ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

	private Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());

	public void submitVote(Movie movie, User user, int rating) {
		UserRating userRating = getUserRating(movie, user);
		if (userRating.getVotes() == 0) {
			persistRating(movie, user, rating);
		} else {
			updateRating(movie, user, rating, userRating.getRatingSum(), userRating.getVotes());
		}		
	}

	private void persistRating(Movie movie, User user, int rating) {
		UserRating userRating = new UserRating(movie.getId(), user.getUsername(), rating, 1, now);
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().save(userRating);		
		connectionHandler.closeCurrentSessionwithTransaction();		
	}
	
	private void updateRating(Movie movie, User user, int rating, int currentSum, int currentVotes) {
		UserRating userRating = new UserRating(movie.getId(), user.getUsername(), currentSum + rating, currentVotes + 1, now);
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().update(userRating);		
		connectionHandler.closeCurrentSessionwithTransaction();				
	}
	
	@SuppressWarnings("unchecked")
	public UserRating getUserRating(Movie movie, User user) {
		UserRating userRating;
		String sql = String.format("SELECT * FROM user_movie_rate WHERE "
				+ "movie_id = %s AND username = '%s'", movie.getId(), user.getUsername());
		Session session = connectionHandler.openCurrentSession();
		List<UserRating> userRatingList = session.createSQLQuery(sql).addEntity(UserRating.class).list();
		connectionHandler.closeCurrentSession();
		try {
			userRating = userRatingList.get(0);
		} catch (IndexOutOfBoundsException e) {
			userRating = new UserRating();
		}
		return userRating;
	}

}
