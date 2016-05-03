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
import edu.spring.movielike.model.Movie;

public class MovieExtractor implements ResultSetExtractor<Movie> {
	
	private RowMapper<Movie> movieRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Movie.class);
	private DaoFactory daoFactory = new DaoFactory();
	private CelebrityDao<Celebrity, CelebrityRole> jdbcCelebrityObject = daoFactory.getCelebrityDao();
	
	@Override
	public Movie extractData(ResultSet rs) throws SQLException, DataAccessException {
		Set<String> genreList = new HashSet<String>(new ArrayList<String>());
		Set<String> countryList = new HashSet<String>(new ArrayList<String>());
		Set<Celebrity> directors = new HashSet<Celebrity>(new ArrayList<Celebrity>());
		Set<Celebrity> leadActors = new HashSet<Celebrity>(new ArrayList<Celebrity>());
		Movie movie = null;
		while (rs.next()) {
			if (movie == null) {
				movie = movieRowMapper.mapRow(rs, rs.getRow());
			}
			if (rs.getString("mg.genrelist")!=null) {
				genreList.add(rs.getString("mg.genrelist"));
			}
			if (rs.getString("mc.countrylist")!=null) {
				countryList.add(rs.getString("mc.countrylist"));
			}
			if (rs.getInt("md.director_id")!=0) {
				directors.add(jdbcCelebrityObject.findCelebrityById(rs.getInt("md.director_id")));
			}
			if (rs.getInt("mla.actor_id")!=0) {
				leadActors.add(jdbcCelebrityObject.findCelebrityById(rs.getInt("mla.actor_id")));
			}
			movie.setGenreList(genreList);		
			movie.setCountryList(countryList);		
			movie.setDirectors(directors);
			movie.setLeadActors(leadActors);	
		}
		if (movie==null) {
			throw new EmptyResultDataAccessException(1);
		}
		return movie;
	}

}
