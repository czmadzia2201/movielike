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

import edu.spring.movielike.model.Movie;

public class MovieExtractor implements ResultSetExtractor<Movie> {
	
	private RowMapper<Movie> movieRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Movie.class);
	
	@Override
	public Movie extractData(ResultSet rs) throws SQLException, DataAccessException {
		Set<String> genreList = new HashSet<String>(new ArrayList<String>());
		Set<String> countryList = new HashSet<String>(new ArrayList<String>());
		Movie movie = null;
		while (rs.next()) {
			if (movie == null) {
				movie = movieRowMapper.mapRow(rs, rs.getRow());
			}
			if (rs.getString("genrelist")!=null) {
				genreList.add(rs.getString("genrelist"));
			}
			if (rs.getString("countrylist")!=null) {
				countryList.add(rs.getString("countrylist"));
			}
			movie.setGenreList(genreList);		
			movie.setCountryList(countryList);		
		}
		if (movie==null) {
			throw new EmptyResultDataAccessException(1);
		}
		return movie;
	}

}
