package edu.spring.movielike.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.dao.MovieDao;
import edu.spring.movielike.dao.ReviewDao;
import edu.spring.movielike.dao.UserDao;
import edu.spring.movielike.dao.UserRatingDao;
import edu.spring.movielike.dao.hibernateImpl.JdbcMovieDaoH;
import edu.spring.movielike.dao.hibernateImpl.JdbcUserDaoH;
import edu.spring.movielike.dao.hibernateImpl.JdbcUserMovieDaoH;
import edu.spring.movielike.dataproviders.CelebrityRole;
import edu.spring.movielike.model.Celebrity;
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
	static CelebrityDao<Celebrity, CelebrityRole> jdbcCelebrityObject = daoFactory.getCelebrityDao();
	
	public static void main(String[] args) {
		Set<Celebrity> director = new HashSet<Celebrity>();
		director.add(jdbcCelebrityObject.findCelebrityById(1));
		director.add(jdbcCelebrityObject.findCelebrityById(1));
		director.add(jdbcCelebrityObject.findCelebrityById(1));
		director.add(jdbcCelebrityObject.findCelebrityById(3));
		director.add(jdbcCelebrityObject.findCelebrityById(5));
		System.out.println(director);
		HashSet hs = new HashSet();
		hs.add("hi");
		hs.add("hi");
		System.out.println(hs);
	}

}
