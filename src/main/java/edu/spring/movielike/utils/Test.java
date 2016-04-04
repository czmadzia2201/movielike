package edu.spring.movielike.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.dao.JdbcMovieDaoH;
import edu.spring.movielike.dao.JdbcUserDaoH;
import edu.spring.movielike.dao.JdbcUserMovieDaoH;
import edu.spring.movielike.dao.MovieDao;
import edu.spring.movielike.dao.ReviewDao;
import edu.spring.movielike.dao.UserDao;
import edu.spring.movielike.dao.UserRatingDao;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.MovieRejected;
import edu.spring.movielike.model.Review;
import edu.spring.movielike.model.User;
import edu.spring.movielike.model.UserRating;

public class Test {

	static DaoFactory daoFactory = new DaoFactory();
	static MovieDao<Movie, MovieRejected> jdbcMovieObject = daoFactory.getMovieDao();
	static UserDao<User> jdbcUserObject = daoFactory.getUserDao();
	static UserRatingDao<Movie, User> jdbcUserRatingObject = daoFactory.getUserRatingDao();
	
	public static void main(String[] args) {
//		Movie movie = jdbcMovieObject.findMovieById(19);
		ArrayList<Movie> movieList = jdbcMovieObject.findMostPopular();
		System.out.println(movieList);
	}

}
