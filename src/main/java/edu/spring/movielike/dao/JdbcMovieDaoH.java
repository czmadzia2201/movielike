package edu.spring.movielike.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.EmptyResultDataAccessException;

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
		List<Object> criteriaValueList = new ArrayList<Object>();
		criteriaValueList.add(criteriaValue);
		String sql = String.format("SELECT * FROM movie AS m LEFT JOIN movie_genre AS mg ON m.id = mg.movie_id "
				+ "LEFT JOIN movie_country mc ON m.id = mc.movie_id "
				+ "WHERE " + searchCriteria + " = '%s' AND m.status = 1 GROUP BY m.id", criteriaValue);
		Session session = connectionHandler.openCurrentSession();
		ArrayList<Movie> movieList = (ArrayList<Movie>) session.createSQLQuery(sql).addEntity(Movie.class).list();
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

}
