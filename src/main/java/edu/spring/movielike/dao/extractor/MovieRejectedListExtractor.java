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

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.dataproviders.CelebrityRole;
import edu.spring.movielike.model.Celebrity;
import edu.spring.movielike.model.MovieRejected;

public class MovieRejectedListExtractor implements ResultSetExtractor<List<MovieRejected>> {
	
	private RowMapper<MovieRejected> movieRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(MovieRejected.class);
	private DaoFactory daoFactory = new DaoFactory();
	private CelebrityDao<Celebrity, CelebrityRole> jdbcCelebrityObject = daoFactory.getCelebrityDao();
	
	@Override
	public List<MovieRejected> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<MovieRejected> movieRejectedList = new ArrayList<MovieRejected>();
		Set<String> genreList = null;
		Set<String> countryList = null;
		Set<Celebrity> directors = null;
		Set<Celebrity> leadActors = null;
		MovieRejected movieRejected = null;
		Integer movieRejectedId = null;
		while (rs.next()) {
			if (movieRejected == null || movieRejectedId!=rs.getInt("id")) {
				genreList = new HashSet<String>();
				countryList = new HashSet<String>();
				directors = new HashSet<Celebrity>();
				leadActors = new HashSet<Celebrity>();
				movieRejectedId = rs.getInt("id");
				movieRejected = movieRowMapper.mapRow(rs, rs.getRow());
				movieRejectedList.add(movieRejected);
			}
			genreList.add(rs.getString("genrelist"));
			countryList.add(rs.getString("countrylist"));
			directors.add(jdbcCelebrityObject.findCelebrityById(rs.getInt("director_id")));
			leadActors.add(jdbcCelebrityObject.findCelebrityById(rs.getInt("actor_id")));
			movieRejected.setGenreList(genreList);
			movieRejected.setCountryList(countryList);
			movieRejected.setDirectors(directors);
			movieRejected.setLeadActors(leadActors);	
		}
		return movieRejectedList;
	}

}
