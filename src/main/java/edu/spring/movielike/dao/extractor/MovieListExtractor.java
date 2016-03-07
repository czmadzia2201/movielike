package edu.spring.movielike.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import edu.spring.movielike.model.Movie;

public class MovieListExtractor implements ResultSetExtractor<List<Movie>> {
	
	private RowMapper<Movie> movieRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Movie.class);
	
	@Override
	public List<Movie> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Movie> movieList = new ArrayList<Movie>();
		Set<String> genreList = null;
		Movie movie = null;
		Integer movieId = null;
		while (rs.next()) {
			if (movie == null || movieId!=rs.getInt("id")) {
				genreList = new HashSet<String>();
				movieId = rs.getInt("id");
				movie = movieRowMapper.mapRow(rs, rs.getRow());
				movieList.add(movie);
			}
			genreList.add(rs.getString("genre_list"));
			movie.setGenreList(genreList);
		}
		return movieList;
	}

}