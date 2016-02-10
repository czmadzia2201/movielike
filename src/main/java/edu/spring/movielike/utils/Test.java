package edu.spring.movielike.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.dao.JdbcMovieDaoH;
import edu.spring.movielike.dao.JdbcUserDaoH;
import edu.spring.movielike.dao.JdbcUserMovieDaoH;
import edu.spring.movielike.dao.MovieDao;
import edu.spring.movielike.dao.ReviewDao;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.MovieRejected;
import edu.spring.movielike.model.Review;
import edu.spring.movielike.model.User;

public class Test {

	static DaoFactory daoFactory = new DaoFactory();
	static MovieDao<Movie, MovieRejected> jdbcMovieObject = daoFactory.getMovieDao();
	
	public static void main(String[] args) {
		
	Movie movie = jdbcMovieObject.findMovieByIdWithStatus(50, 1);
//	Movie movie = jdbcMovieObject.findMovieById(47);
	System.out.println(movie);
	}

}
