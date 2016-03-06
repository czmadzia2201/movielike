package edu.spring.movielike.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import edu.spring.movielike.dao.extractor.MovieExtractor;
import edu.spring.movielike.dao.extractor.MovieListExtractor;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.MovieRejected;

public class JdbcMovieDaoS extends JdbcDaoSupport implements MovieDao<Movie, MovieRejected> {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Movie persistMovie(Movie movie) { 
		String sql1 = "INSERT INTO movie (title, director, lead_actors, genre, "
				+ "year, country, description, added_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql1, new Object[] {movie.getTitle(), movie.getDirector(), 
			movie.getLeadActors(), movie.getGenre(), movie.getYear(), movie.getCountry(), 
			movie.getDescription(), movie.getAddedBy()});
		String sql2 = "SELECT * FROM movie WHERE title = ? AND year = ? AND added_by = ?";
		Movie movieAdded = getJdbcTemplate().queryForObject(sql2, new Object[] {movie.getTitle(), 
				movie.getYear(), movie.getAddedBy()}, new BeanPropertyRowMapper(Movie.class));
		return movieAdded;
	}
 
	public void updateMovie(Movie movie){
		String sql = "UPDATE movie SET title = ?, director = ?, lead_actors = ?, "
				+ "genre = ?, year = ?, country = ?, description = ? WHERE id = ?";
		getJdbcTemplate().update(sql, new Object[] {movie.getTitle(), movie.getDirector(), movie.getLeadActors(), 
			movie.getGenre(), movie.getYear(), movie.getCountry(), movie.getDescription(), movie.getId()});				
	}

	public ArrayList<Movie> findAllMoviesByProperty(String searchCriteria, Object criteriaValue){
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE " + searchCriteria + " = ? AND status = 1";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {criteriaValue}, new MovieListExtractor());
		return movieList;
	}
	
	public ArrayList<Movie> findAllMovies(int status){
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE status = ?";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {status}, new MovieListExtractor());
		return movieList;
	}

	public Movie findMovieById(int id){
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE id = ?"; 
		Movie movie = (Movie) getJdbcTemplate().query(sql, new Object[] {id}, new MovieExtractor());
		return movie;
	}

	public Movie findMovieByIdWithStatus(int id, int status){
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE id = ? AND status = ?";
		Movie movie = (Movie) getJdbcTemplate().query(sql, new Object[] {id, status}, new MovieExtractor());
		return movie;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MovieRejected findRejectedMovieById(int id) {
		String sql = "SELECT * FROM movie_rejected WHERE id = ?";
		MovieRejected movie = (MovieRejected) getJdbcTemplate().queryForObject(sql, new Object[] {id},
			new BeanPropertyRowMapper(MovieRejected.class));
		return movie;
	}
 
	public ArrayList<Movie> findMoviesAddedBy(String addedBy, int status){
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE added_by = ? AND status = ?";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {addedBy, status}, new MovieListExtractor());
		return movieList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<MovieRejected> findRejectedMoviesAddedBy(String addedBy){
		String sql = "SELECT * FROM movie_rejected WHERE added_by = ?";
		ArrayList<MovieRejected> movieList = (ArrayList<MovieRejected>) getJdbcTemplate().query(sql, 
			new Object[] {addedBy}, new BeanPropertyRowMapper(MovieRejected.class));
		return movieList;
	}

	public void updateMovieStatus(Movie movie, int status, String reason){
		String sql1 = "UPDATE movie SET status = ? WHERE id = ?";
		getJdbcTemplate().update(sql1, new Object[] {status, movie.getId()});	
		if (status==-1) {
			String sql2 = "INSERT INTO movie_rejected SELECT *, ? FROM movie WHERE id = ?";
			getJdbcTemplate().update(sql2, new Object[] {reason, movie.getId()});	
		}
	}

	public void deleteRejectedMoviesAdmin(){
		String sql = "DELETE FROM movie WHERE status = -1";
		getJdbcTemplate().update(sql);		
	}

	public void deleteRejectedMoviesUser(String addedBy) {
		String sql = "DELETE FROM movie_rejected WHERE added_by = ?";
		getJdbcTemplate().update(sql, new Object[] {addedBy});		
	}

	public void deleteMovie(Movie movie){
		String sql = "DELETE FROM movie WHERE id = ?";
		getJdbcTemplate().update(sql, new Object[] {movie.getId()});		
	}

}