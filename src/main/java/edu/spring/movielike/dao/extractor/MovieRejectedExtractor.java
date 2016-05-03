package edu.spring.movielike.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.dataproviders.CelebrityRole;
import edu.spring.movielike.model.Celebrity;
import edu.spring.movielike.model.MovieRejected;

public class MovieRejectedExtractor implements ResultSetExtractor<MovieRejected> {
	
	private RowMapper<MovieRejected> movieRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(MovieRejected.class);
	private DaoFactory daoFactory = new DaoFactory();
	private CelebrityDao<Celebrity, CelebrityRole> jdbcCelebrityObject = daoFactory.getCelebrityDao();
	
	@Override
	public MovieRejected extractData(ResultSet rs) throws SQLException, DataAccessException {
		Set<String> genreList = new HashSet<String>();
		Set<String> countryList = new HashSet<String>(new ArrayList<String>());
		Set<Celebrity> directors = new HashSet<Celebrity>(new ArrayList<Celebrity>());
		Set<Celebrity> leadActors = new HashSet<Celebrity>(new ArrayList<Celebrity>());
		MovieRejected movieRejected = null;
		while (rs.next()) {
			if (movieRejected == null) {
				movieRejected = movieRowMapper.mapRow(rs, rs.getRow());
			}
			if (rs.getString("genrelist")!=null) {
				genreList.add(rs.getString("genrelist"));
			}
			if (rs.getString("countrylist")!=null) {
				countryList.add(rs.getString("countrylist"));
			}
			if (rs.getInt("director_id")!=0) {
				directors.add(jdbcCelebrityObject.findCelebrityById(rs.getInt("director_id")));
			}
			if (rs.getInt("actor_id")!=0) {
				leadActors.add(jdbcCelebrityObject.findCelebrityById(rs.getInt("actor_id")));
			}
			movieRejected.setGenreList(genreList);		
			movieRejected.setCountryList(countryList);		
			movieRejected.setDirectors(directors);
			movieRejected.setLeadActors(leadActors);	
		}
		if (movieRejected==null) {
			throw new EmptyResultDataAccessException(1);
		}
		return movieRejected;
	}

}
