package edu.spring.movielike.dao.springImpl;

import java.util.ArrayList;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import edu.spring.movielike.dao.MovieDao;
import edu.spring.movielike.dao.extractor.MovieExtractor;
import edu.spring.movielike.dao.extractor.MovieListExtractor;
import edu.spring.movielike.dao.extractor.MovieRejectedExtractor;
import edu.spring.movielike.dao.extractor.MovieRejectedListExtractor;
import edu.spring.movielike.model.Celebrity;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.MovieRejected;

public class JdbcMovieDaoS extends JdbcDaoSupport implements MovieDao<Movie, MovieRejected> {
	
	private static final String JOIN_FOR_MOVIE = "LEFT JOIN movie_genre mg ON m.id = mg.movie_id "
			+ "LEFT JOIN movie_country mc ON m.id = mc.movie_id "
			+ "LEFT JOIN movie_director md ON m.id = md.movie_id "
			+ "LEFT JOIN movie_leadactors mla ON m.id = mla.movie_id ";
	private static final String SELECT_MOVIE = "SELECT * FROM movie m " + JOIN_FOR_MOVIE;
	private static final String SELECT_MOVIE_REJECTED = "SELECT * FROM movie_rejected m " + JOIN_FOR_MOVIE;
	
	public Integer persistMovie(Movie movie) { 
		String sql1 = "INSERT INTO movie (title, year, description, genre_other, country_other, added_by) VALUES (?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql1, new Object[] {movie.getTitle(), movie.getYear(), movie.getDescription(), 
				movie.getGenreOther(), movie.getCountryOther(), movie.getAddedBy()});
		String sql2 = "SELECT id FROM movie WHERE title = ? AND year = ? AND added_by = ?";
		Integer movieId = getJdbcTemplate().queryForObject(sql2, new Object[] {movie.getTitle(), 
		movie.getYear(), movie.getAddedBy()}, Integer.class);
		if (movie.getGenreList()!=null) {
			String sql3 = "INSERT INTO movie_genre (movie_id, genrelist) VALUES (?, ?)";
			for (String genre : movie.getGenreList()) {
				getJdbcTemplate().update(sql3, new Object[] {movieId, genre});
			}
		}		
		if (movie.getCountryList()!=null) {
			String sql4 = "INSERT INTO movie_country (movie_id, countrylist) VALUES (?, ?)";
			for (String country : movie.getCountryList()) {
				getJdbcTemplate().update(sql4, new Object[] {movieId, country});
			}
		}
		if (movie.getDirectors()!=null) {
			String sql5 = "INSERT INTO movie_director (movie_id, director_id) VALUES (?, ?)";
			for (Celebrity director : movie.getDirectors()) {
				getJdbcTemplate().update(sql5, new Object[] {movieId, director.getId()});
			}
		}		
		if (movie.getGenreList()!=null) {
			String sql6 = "INSERT INTO movie_leadactors (movie_id, actor_id) VALUES (?, ?)";
			for (Celebrity actor : movie.getLeadActors()) {
				getJdbcTemplate().update(sql6, new Object[] {movieId, actor.getId()});
			}
		}		
		return movieId;
	}
 
	public void updateMovie(Movie movie) {
		String sql1 = "UPDATE movie SET title = ?, director = ?, lead_actors = ?, "
				+ "year = ?, description = ?, genre_other = ?, country_other = ? WHERE id = ?";
		getJdbcTemplate().update(sql1, new Object[] {movie.getTitle(), movie.getDirectors(), movie.getLeadActors(), 
				movie.getYear(), movie.getDescription(), movie.getGenreOther(), movie.getCountryOther(), movie.getId()});
		String sql2 = "DELETE FROM movie_genre WHERE movie_id = ?";
		getJdbcTemplate().update(sql2, new Object[] {movie.getId()});
		String sql3 = "INSERT INTO movie_genre (movie_id, genrelist) VALUES (?, ?)";
		if (movie.getGenreList()!=null) {
			for (String genre : movie.getGenreList()) {
				getJdbcTemplate().update(sql3, new Object[] {movie.getId(), genre});
			}
		}
		String sql4 = "DELETE FROM movie_country WHERE movie_id = ?";
		getJdbcTemplate().update(sql4, new Object[] {movie.getId()});
		String sql5 = "INSERT INTO movie_country (movie_id, countrylist) VALUES (?, ?)";
		if (movie.getCountryList()!=null) {
			for (String country : movie.getCountryList()) {
				getJdbcTemplate().update(sql5, new Object[] {movie.getId(), country});
			}
		}
	}

	public ArrayList<Movie> findAllMoviesByProperty(String searchCriteria, Object criteriaValue) {
		if (searchCriteria.equals("directors")) searchCriteria = "cd.name";
		if (searchCriteria.equals("leadActors")) searchCriteria = "cla.name";		
		String sql = "SELECT m.id, m.title, m.year, m.description, m.genre_other, m.country_other, "
				+ "m.added_by, mg.genrelist, mc.countrylist, md.director_id, mla.actor_id FROM movie m " 
				+ JOIN_FOR_MOVIE + "LEFT JOIN celebrity cd ON md.director_id = cd.id "
				+ "LEFT JOIN celebrity cla ON mla.actor_id = cla.id "
				+ "WHERE m.id IN (SELECT id FROM movie m " + JOIN_FOR_MOVIE 
				+ "WHERE " + searchCriteria + " = ? AND m.status = 1)";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {criteriaValue}, new MovieListExtractor());
		return movieList;
	}
	
	public ArrayList<Movie> findAllMovies(int status) {
		String sql = SELECT_MOVIE + "WHERE m.status = ?";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {status}, new MovieListExtractor());
		return movieList;
	}

	public ArrayList<Movie> findLatest() {
		String sql = SELECT_MOVIE + "WHERE m.id IN "
				+ "(SELECT * FROM (SELECT id FROM movie WHERE status = 1 ORDER BY id DESC LIMIT 10) as t) ORDER BY m.id DESC";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, new MovieListExtractor());
		return movieList;
	}

	public Movie findMovieById(int id) {
		String sql = SELECT_MOVIE + "WHERE m.id = ?";
		Movie movie = (Movie) getJdbcTemplate().query(sql, new Object[] {id}, new MovieExtractor());
		return movie;
	}

	public Movie findMovieByIdWithStatus(int id, int status) {
		String sql = SELECT_MOVIE + "WHERE m.id = ? AND m.status = ?";
		Movie movie = (Movie) getJdbcTemplate().query(sql, new Object[] {id, status}, new MovieExtractor());
		return movie;
	}

	public MovieRejected findRejectedMovieById(int id) {
		String sql = SELECT_MOVIE_REJECTED + "WHERE m.id = ?";
		MovieRejected movie = (MovieRejected) getJdbcTemplate().query(sql, new Object[] {id},
				new MovieRejectedExtractor());
		return movie;
	}
 
	public ArrayList<Movie> findMoviesAddedBy(String addedBy, int status){
		String sql = SELECT_MOVIE + "WHERE m.added_by = ? AND m.status = ?";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, 
			new Object[] {addedBy, status}, new MovieListExtractor());
		return movieList;
	}
	
	public ArrayList<MovieRejected> findRejectedMoviesAddedBy(String addedBy){
		String sql = SELECT_MOVIE_REJECTED + "WHERE m.added_by = ?";
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
		String sql1 = "DELETE FROM movie_genre WHERE movie_id in (SELECT id FROM movie_rejected WHERE added_by = ?)";
		String sql2 = "DELETE FROM movie_country WHERE movie_id in (SELECT id FROM movie_rejected WHERE added_by = ?)";
		String sql3 = "DELETE FROM movie_rejected WHERE added_by = ?";
		getJdbcTemplate().update(sql1, new Object[] {addedBy});		
		getJdbcTemplate().update(sql2, new Object[] {addedBy});		
		getJdbcTemplate().update(sql3, new Object[] {addedBy});		
	}

	public void deleteMovie(Movie movie) {
		String sql1 = "DELETE FROM movie_genre WHERE movie_id = ?";
		String sql2 = "DELETE FROM movie_country WHERE movie_id = ?";
		String sql3 = "DELETE FROM movie_director WHERE movie_id = ?";
		String sql4 = "DELETE FROM movie_leadactors WHERE movie_id = ?";
		String sql5 = "DELETE FROM movie WHERE id = ?";
		getJdbcTemplate().update(sql1, new Object[] {movie.getId()});			
		getJdbcTemplate().update(sql2, new Object[] {movie.getId()});			
		getJdbcTemplate().update(sql3, new Object[] {movie.getId()});			
		getJdbcTemplate().update(sql4, new Object[] {movie.getId()});			
		getJdbcTemplate().update(sql5, new Object[] {movie.getId()});			
	}
	
	public void rateMovie(Movie movie, int rating) {
		String sql = "UPDATE movie SET voters = ?, rating_sum = ?, rating_avg_num = ? WHERE id = ?";
		int ratingSum = movie.getRatingSum()+rating;
		int voters = movie.getVoters()+1;
		double ratingAvg = (double)ratingSum/voters;
		getJdbcTemplate().update(sql, new Object[] {voters, ratingSum, ratingAvg, movie.getId()});			
	}

	public ArrayList<Movie> findMostPopular() {
		String sql = SELECT_MOVIE + "WHERE m.status = 1 AND m.rating_avg_num > 7.0 ORDER BY rating_sum DESC LIMIT 10";
		ArrayList<Movie> movieList = (ArrayList<Movie>) getJdbcTemplate().query(sql, new MovieListExtractor());
		return movieList;
	}
	
}