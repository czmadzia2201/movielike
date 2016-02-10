package edu.spring.movielike.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
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
		String sql = String.format("SELECT * FROM movie WHERE id IN (SELECT movie_id FROM user_movie_link "
			+ "WHERE username = '%s' AND liked = %s)", user.getUsername(), fav);
		Session session = connectionHandler.openCurrentSession();
		List<Movie> movieListRaw = session.createSQLQuery(sql).addEntity(Movie.class).list();
		ArrayList<Movie> movieList = new ArrayList<Movie>(movieListRaw);
		connectionHandler.closeCurrentSession();		
		return movieList;
	}

}
