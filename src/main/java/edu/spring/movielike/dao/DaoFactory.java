package edu.spring.movielike.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.MovieRejected;
import edu.spring.movielike.model.Review;
import edu.spring.movielike.model.User;

public class DaoFactory {
	
	public enum DaoType {
		HIBERNATE, JDBCTEMPLATE
	}
	
	private DaoType chooseDao = DaoType.JDBCTEMPLATE;
	ApplicationContext context = new ClassPathXmlApplicationContext("spring-database.xml");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MovieDao<Movie, MovieRejected> getMovieDao() {
		MovieDao<Movie, MovieRejected> jdbcMovieObject = null;
		if (chooseDao==DaoType.HIBERNATE) {
			jdbcMovieObject = new JdbcMovieDaoH();
		}
		if (chooseDao==DaoType.JDBCTEMPLATE) {
			jdbcMovieObject = (MovieDao) context.getBean("movieDao");
		}
		return jdbcMovieObject;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserDao<User> getUserDao() {
		UserDao<User> jdbcUserObject = null;
		if (chooseDao==DaoType.HIBERNATE) {
			jdbcUserObject = new JdbcUserDaoH();
		}
		if (chooseDao==DaoType.JDBCTEMPLATE) {
			jdbcUserObject = (UserDao) context.getBean("userDao");
		}
		return jdbcUserObject;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserMovieDao<User, Movie> getUserMovieDao() {
		UserMovieDao<User, Movie> jdbcUserMovieLink = null;
		if (chooseDao==DaoType.HIBERNATE) {
			jdbcUserMovieLink = new JdbcUserMovieDaoH();
		}
		if (chooseDao==DaoType.JDBCTEMPLATE) {
			jdbcUserMovieLink = (UserMovieDao) context.getBean("userMovieDao");		
		}
		return jdbcUserMovieLink;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ReviewDao<Review, Movie, User> getReviewDao() {
		ReviewDao<Review, Movie, User> jdbcReviewObject = null;
		if (chooseDao==DaoType.HIBERNATE) {
			jdbcReviewObject = new JdbcReviewDaoH();		
		}
		if (chooseDao==DaoType.JDBCTEMPLATE) {
			jdbcReviewObject = (ReviewDao) context.getBean("reviewDao");		
		}
		return jdbcReviewObject;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserRatingDao<Movie, User> getUserRatingDao() {
		UserRatingDao<Movie, User> jdbcUserRatingObject = null;
		if (chooseDao==DaoType.HIBERNATE) {
			jdbcUserRatingObject = new JdbcUserRatingDaoH();
		}
		if (chooseDao==DaoType.JDBCTEMPLATE) {
			jdbcUserRatingObject = (UserRatingDao) context.getBean("userRatingDao");
		}
		return jdbcUserRatingObject;
	}

}
