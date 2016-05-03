package edu.spring.movielike.dao.springImpl;

import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
	public Celebrity findCelebrityByName(String name) {
		String sql = "SELECT * FROM celebrity WHERE name = ?";
		Celebrity celebrity = (Celebrity) getJdbcTemplate().queryForObject(sql, new Object[] {name}, 
				new BeanPropertyRowMapper(Celebrity.class));
		return celebrity;
	}

}
