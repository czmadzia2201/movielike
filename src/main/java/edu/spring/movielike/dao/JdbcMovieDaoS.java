package edu.spring.movielike.dao;

import java.util.ArrayList;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import edu.spring.movielike.dao.extractor.MovieExtractor;
import edu.spring.movielike.dao.extractor.MovieListExtractor;
import edu.spring.movielike.dao.extractor.MovieRejectedExtractor;
import edu.spring.movielike.dao.extractor.MovieRejectedListExtractor;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.MovieRejected;

public class JdbcMovieDaoS extends JdbcDaoSupport implements MovieDao<Movie, MovieRejected> {
	
	public Integer persistMovie(Movie movie) { 
		String sql1 = "INSERT INTO movie (title, director, lead_actors, genre, "
				+ "year, country, description, added_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql1, new Object[] {movie.getTitle(), movie.getDirector(), 
			movie.getLeadActors(), movie.getGenre(), movie.getYear(), movie.getCountry(), 
			movie.getDescription(), movie.getAddedBy()});
		String sql2 = "SELECT id FROM movie WHERE title = ? AND year = ? AND added_by = ?";
		Integer movieId = getJdbcTemplate().queryForObject(sql2, new Object[] {movie.getTitle(), 
		movie.getYear(), movie.getAddedBy()}, Integer.class);
		String sql3 = "INSERT INTO movie_genre (movie_id, genrelist) VALUES (?, ?)";
		for (String genre : movie.getGenreList()) {
			getJdbcTemplate().update(sql3, new Object[] {movieId, genre});
		}
		return movieId;
	}
 
	public void updateMovie(Movie movie) {
		String sql1 = "UPDATE movie SET title = ?, director = ?, lead_actors = ?, "
				+ "genre = ?, year = ?, country = ?, description = ? WHERE id = ?";
		getJdbcTemplate().update(sql1, new Object[] {movie.getTitle(), movie.getDirector(), movie.getLeadActors(), 
			movie.getGenre(), movie.getYear(), movie.getCountry(), movie.getDescription(), movie.getId()});
		String sql2 = "DELETE FROM movie_genre WHERE movie_id = ?";
		getJdbcTemplate().update(sql2, new Object[] {movie.getId()});
		String sql3 = "INSERT INTO movie_genre (movie_id, genrelist) VALUES (?, ?)";
		for (String genre : movie.getGenreList()) {
			getJdbcTemplate().update(sql3, new Object[] {movie.getId(), genre});
		}
	}

	public ArrayList<Movie> findAllMoviesByProperty(String searchCriteria, Object criteriaValue) {
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE id IN (SELECT id FROM movie "
				+ "LEFT JOIN movie_genre ON id = movie_id WHERE " + searchCriteria + " = ? AND status = 1);";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {criteriaValue}, new MovieListExtractor());
		return movieList;
	}
	
	public ArrayList<Movie> findAllMovies(int status) {
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE status = ?";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {status}, new MovieListExtractor());
		return movieList;
	}

	public Movie findMovieById(int id) {
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE id = ?"; 
		Movie movie = (Movie) getJdbcTemplate().query(sql, new Object[] {id}, new MovieExtractor());
		return movie;
	}

	public Movie findMovieByIdWithStatus(int id, int status) {
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE id = ? AND status = ?";
		Movie movie = (Movie) getJdbcTemplate().query(sql, new Object[] {id, status}, new MovieExtractor());
		return movie;
	}

	public MovieRejected findRejectedMovieById(int id) {
		String sql = "SELECT * FROM movie_rejected LEFT JOIN movie_genre ON id = movie_id WHERE id = ?";
		MovieRejected movie = (MovieRejected) getJdbcTemplate().query(sql, new Object[] {id},
				new MovieRejectedExtractor());
		return movie;
	}
 
	public ArrayList<Movie> findMoviesAddedBy(String addedBy, int status){
		String sql = "SELECT * FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE added_by = ? AND status = ?";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {addedBy, status}, new MovieListExtractor());
		return movieList;
	}
	
	public ArrayList<MovieRejected> findRejectedMoviesAddedBy(String addedBy){
		String sql = "SELECT * FROM movie_rejected LEFT JOIN movie_genre ON id = movie_id WHERE added_by = ?";
		ArrayList<MovieRejected> movieList = (ArrayList<MovieRejected>) getJdbcTemplate().query(sql, 
			new Object[] {addedBy}, new MovieRejectedListExtractor());
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
		String sql = "DELETE movie_rejected, movie_genre FROM movie_rejected "
				+ "LEFT JOIN movie_genre ON id = movie_id WHERE added_by = ?";
		getJdbcTemplate().update(sql, new Object[] {addedBy});		
	}

	public void deleteMovie(Movie movie){
		String sql = "DELETE movie, movie_genre FROM movie LEFT JOIN movie_genre ON id = movie_id WHERE id = ?";
		getJdbcTemplate().update(sql, new Object[] {movie.getId()});		
	}

}