package edu.spring.movielike.dao.springImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dataproviders.CelebrityRole;
import edu.spring.movielike.model.Celebrity;

public class JdbcCelebrityDaoS extends JdbcDaoSupport implements CelebrityDao<Celebrity, CelebrityRole> {
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Celebrity> findAllCelebritiesByRole(CelebrityRole role) {
		String sql = "SELECT * FROM celebrity WHERE " + role + " = 1";
		ArrayList<Celebrity> celebrityList = (ArrayList<Celebrity>) getJdbcTemplate().query(sql, 
			new BeanPropertyRowMapper(Celebrity.class));
		return celebrityList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Celebrity findCelebrityById(int id) {
		String sql = "SELECT * FROM celebrity WHERE id = ?";
		Celebrity celebrity = (Celebrity) getJdbcTemplate().queryForObject(sql, new Object[] {id}, 
				new BeanPropertyRowMapper(Celebrity.class));
		return celebrity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Celebrity> findCelebritiesByName(Set<String> names) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("namesParam", names);
		String sql = "SELECT * FROM celebrity WHERE name IN (:namesParam)";
		ArrayList<Celebrity> celebrities = (ArrayList<Celebrity>) namedParameterJdbcTemplate.query(sql, parameters,
				new BeanPropertyRowMapper(Celebrity.class));
		return celebrities;
	}

}
