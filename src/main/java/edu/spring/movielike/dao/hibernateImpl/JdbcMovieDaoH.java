package edu.spring.movielike.dao.hibernateImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.EmptyResultDataAccessException;

import edu.spring.movielike.dao.ConnectionHandler;
import edu.spring.movielike.dao.MovieDao;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.MovieRejected;

public class JdbcMovieDaoH implements MovieDao<Movie, MovieRejected> {

	private ConnectionHandler connectionHandler = ConnectionHandler.getInstance();
	
	public Integer persistMovie(Movie movie) {
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().save(movie);		
		connectionHandler.closeCurrentSessionwithTransaction();
		return movie.getId();
	}

	public void updateMovie(Movie movie) {
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().update(movie);
		connectionHandler.closeCurrentSessionwithTransaction();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Movie> findAllMoviesByProperty(String searchCriteria, Object criteriaValue) {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Movie.class);
		ArrayList<Movie> movieList; 
		if (searchCriteria.equals("directors") || searchCriteria.equals("leadActors")) {
			criteria.createAlias(searchCriteria, searchCriteria + "Alias");
			movieList = (ArrayList<Movie>) criteria.add(Restrictions.eq(searchCriteria + "Alias.name", criteriaValue)).list();			
		} else if (searchCriteria.equals("genreList") || searchCriteria.equals("countryList")) {
			criteria.createAlias(searchCriteria, searchCriteria + "Alias");
			movieList = (ArrayList<Movie>) criteria.add(Restrictions.eq(searchCriteria + "Alias.elements", criteriaValue)).list();						
		} else {
			movieList = (ArrayList<Movie>) criteria.add(Restrictions.eq(searchCriteria, criteriaValue)).list();
		}
		connectionHandler.closeCurrentSession();
		return movieList; 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Movie> findAllMovies(int status) {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Movie.class);
		ArrayList<Movie> movieList = (ArrayList<Movie>) criteria.add(Restrictions.eq("status", status)).list();
		connectionHandler.closeCurrentSession();
		return movieList; 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Movie> findLatest() {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Movie.class);
		ArrayList<Movie> movieList = (ArrayList<Movie>) criteria.add(Restrictions.eq("status", 1))
				.addOrder(Order.desc("id")).setMaxResults(10).list();
		connectionHandler.closeCurrentSession();		
		return movieList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Movie> findMoviesAddedBy(String addedBy, int status) {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Movie.class);
		ArrayList<Movie> movieList = (ArrayList<Movie>) criteria
			.add(Restrictions.eq("addedBy", addedBy))
			.add(Restrictions.eq("status", status)).list();
		connectionHandler.closeCurrentSession();
		return movieList; 
	}

	public Movie findMovieById(int id) {
		connectionHandler.openCurrentSession();
		Movie movie = (Movie) connectionHandler.getCurrentSession().get(Movie.class, id);
		connectionHandler.closeCurrentSession();
		if (movie==null) {
			throw new EmptyResultDataAccessException(1);
		}
		return movie; 
	}

	public Movie findMovieByIdWithStatus(int id, int status) {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Movie.class);
		Movie movie = (Movie) criteria.add(Restrictions.eq("id", id)).add(Restrictions.eq("status", status)).uniqueResult();
		connectionHandler.closeCurrentSession();
		if (movie==null) {
			throw new EmptyResultDataAccessException(1);
		}
		return movie; 
	}

	public void updateMovieStatus(Movie movie, int status, String reason) {
		Session session = connectionHandler.openCurrentSessionwithTransaction();
		String sql1 = String.format("UPDATE movie SET status = %s WHERE id = %s", status, movie.getId());
		session.createSQLQuery(sql1).executeUpdate();
		if (status==-1) {
			String sql2 = String.format("INSERT INTO movie_rejected SELECT *, '%s' FROM movie WHERE id = %s", reason, movie.getId());
			session.createSQLQuery(sql2).executeUpdate();
		}
		connectionHandler.closeCurrentSessionwithTransaction();		
	}

	public void deleteRejectedMoviesUser(String addedBy) {
		Session session = connectionHandler.openCurrentSessionwithTransaction();
		String sql1 = String.format("DELETE FROM movie_genre WHERE movie_id in (SELECT id FROM movie_rejected WHERE added_by = '%s')", addedBy);
		String sql2 = String.format("DELETE FROM movie_country WHERE movie_id in (SELECT id FROM movie_rejected WHERE added_by = '%s')", addedBy);
		String sql3 = String.format("DELETE FROM movie_rejected WHERE added_by = '%s'", addedBy);
		session.createSQLQuery(sql1).executeUpdate();
		session.createSQLQuery(sql2).executeUpdate();
		session.createSQLQuery(sql3).executeUpdate();
		connectionHandler.closeCurrentSessionwithTransaction();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<MovieRejected> findRejectedMoviesAddedBy(String addedBy) {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(MovieRejected.class);
		ArrayList<MovieRejected> movieList = (ArrayList<MovieRejected>) criteria.add(Restrictions.eq("addedBy", addedBy)).list();
		connectionHandler.closeCurrentSession();
		return movieList; 
	}

	public MovieRejected findRejectedMovieById(int id) {
		connectionHandler.openCurrentSession();
		MovieRejected movie = (MovieRejected) connectionHandler.getCurrentSession().get(MovieRejected.class, id);
		connectionHandler.closeCurrentSession();
		if (movie==null) {
			throw new EmptyResultDataAccessException(1);
		}
		return movie; 
	}

	public void deleteRejectedMoviesAdmin() {
		Session session = connectionHandler.openCurrentSessionwithTransaction();
		String sql = "DELETE FROM movie WHERE status = -1";
		session.createSQLQuery(sql).executeUpdate();
		connectionHandler.closeCurrentSessionwithTransaction();
	}

	public void deleteMovie(Movie movie) {
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().delete(movie);
		connectionHandler.closeCurrentSessionwithTransaction();
	}
	
	public void rateMovie(Movie movie, int rating) {
		Session session = connectionHandler.openCurrentSessionwithTransaction();
		int ratingSum = movie.getRatingSum()+rating;
		int voters = movie.getVoters()+1;
		double ratingAvg = (double)ratingSum/voters;
		String sql = String.format("UPDATE movie SET voters = %s, rating_sum = %s, "
				+ "rating_avg_num = %s WHERE id = %s", voters, ratingSum, ratingAvg, movie.getId());
		session.createSQLQuery(sql).executeUpdate();
		connectionHandler.closeCurrentSessionwithTransaction();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Movie> findMostPopular() {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Movie.class);
		ArrayList<Movie> movieList = (ArrayList<Movie>) criteria.add(Restrictions.eq("status", 1))
				.add(Restrictions.gt("ratingAvgNum", 7.0)).addOrder(Order.desc("ratingSum")).setMaxResults(10).list();
		connectionHandler.closeCurrentSession();		
		return movieList;
	}
	
}
