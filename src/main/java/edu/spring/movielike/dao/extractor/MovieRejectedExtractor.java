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

import edu.spring.movielike.model.MovieRejected;

public class MovieRejectedExtractor implements ResultSetExtractor<MovieRejected> {
	
	private RowMapper<MovieRejected> movieRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(MovieRejected.class);
	
	@Override
	public MovieRejected extractData(ResultSet rs) throws SQLException, DataAccessException {
		Set<String> genreList = new HashSet<String>();
		Set<String> countryList = new HashSet<String>(new ArrayList<String>());
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
			movieRejected.setGenreList(genreList);
			movieRejected.setCountryList(countryList);		
		}
		if (movieRejected==null) {
			throw new EmptyResultDataAccessException(1);
		}
		return movieRejected;
	}

}
