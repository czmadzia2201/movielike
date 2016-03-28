package edu.spring.movielike.dao;

import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import edu.spring.movielike.dao.extractor.MovieListExtractor;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.User;

public class JdbcUserMovieDaoS extends JdbcDaoSupport implements UserMovieDao<User, Movie> {

	public void linkUserMovie(User user, Movie movie, int fav) { 
		String sql = "INSERT INTO user_movie_link (username, movie_id, liked) VALUES (?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[] {user.getUsername(), movie.getId(), fav});				
	}
 
	public void unlinkUserMovie(User user, Movie movie, int fav) { 
		String sql = "DELETE FROM user_movie_link WHERE username = ? AND movie_id = ? AND liked = ?";
		getJdbcTemplate().update(sql, new Object[] {user.getUsername(), movie.getId(), fav});				
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean isUserMovieLinked(User user, Movie movie, int fav) {
		String sql = "SELECT * FROM user_movie_link WHERE username = ? AND movie_id = ? AND liked = ?";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, new Object[] 
				{user.getUsername(), movie.getId(), fav}, new BeanPropertyRowMapper(Movie.class));
		return movieList.size()!=0;
	}

	public ArrayList<Movie> getUserLinkedMovies(User user, int fav) {
		String sql = "SELECT * FROM movie AS m LEFT JOIN movie_genre AS mg ON m.id = mg.movie_id "
				+ "LEFT JOIN movie_country AS mc ON m.id = mc.movie_id WHERE m.id IN "
				+ "(SELECT movie_id FROM user_movie_link WHERE username = ? AND liked = ?)";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, new Object[] 
				{user.getUsername(), fav}, new MovieListExtractor());
		return movieList;
	}

}
