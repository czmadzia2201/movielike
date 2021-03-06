package edu.spring.movielike.dao.hibernateImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import edu.spring.movielike.dao.ConnectionHandler;
import edu.spring.movielike.dao.UserMovieDao;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.User;

public class JdbcUserMovieDaoH implements UserMovieDao<User, Movie> {

	private ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

	public void linkUserMovie(User user, Movie movie, int fav) {
		String sql = String.format("INSERT INTO user_movie_link (username, movie_id, liked) VALUES ('%s', %s, %s)", 
			user.getUsername(), movie.getId(), fav);
		Session session = connectionHandler.openCurrentSessionwithTransaction();
		session.createSQLQuery(sql).executeUpdate();
		connectionHandler.closeCurrentSessionwithTransaction();
	}

	public void unlinkUserMovie(User user, Movie movie, int fav) {
		String sql = String.format("DELETE FROM user_movie_link WHERE username = '%s' AND movie_id = %s AND liked = %s", 
				user.getUsername(), movie.getId(), fav);
		Session session = connectionHandler.openCurrentSessionwithTransaction();
		session.createSQLQuery(sql).executeUpdate();
		connectionHandler.closeCurrentSessionwithTransaction();
	}

	@SuppressWarnings("rawtypes")
	public boolean isUserMovieLinked(User user, Movie movie, int fav) {
		String sql = String.format("SELECT * FROM user_movie_link WHERE username = '%s' AND movie_id = %s AND liked = %s", 
				user.getUsername(), movie.getId(), fav);
		Session session = connectionHandler.openCurrentSession();
		List movieList = session.createSQLQuery(sql).list();
		connectionHandler.closeCurrentSession();		
		return movieList.size()!=0;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Movie> getUserLinkedMovies(User user, int fav) {
		String sql = String.format("SELECT * FROM movie AS m LEFT JOIN movie_genre AS mg ON m.id = mg.movie_id "
				+ "LEFT JOIN movie_country AS mc ON m.id = mc.movie_id LEFT JOIN movie_director md ON m.id = md.movie_id "
				+ "LEFT JOIN movie_leadactors mla ON m.id = mla.movie_id WHERE m.id IN (SELECT movie_id FROM user_movie_link "
				+ "WHERE username = '%s' AND liked = %s) GROUP BY m.id", user.getUsername(), fav);
		Session session = connectionHandler.openCurrentSession();
		List<Movie> movieListRaw = session.createSQLQuery(sql).addEntity(Movie.class).list();
		for (Movie movie : movieListRaw) {
			Hibernate.initialize(movie.getDirectors());
			Hibernate.initialize(movie.getLeadActors());			
		}		
		ArrayList<Movie> movieList = new ArrayList<Movie>(movieListRaw);
		connectionHandler.closeCurrentSession();		
		return movieList;
	}

}
