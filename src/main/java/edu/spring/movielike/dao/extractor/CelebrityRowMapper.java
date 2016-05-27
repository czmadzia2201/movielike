package edu.spring.movielike.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.spring.movielike.model.Celebrity;

public class CelebrityRowMapper implements RowMapper<Celebrity> {

	@Override
	public Celebrity mapRow(ResultSet rs, int rowNum) throws SQLException {
		Celebrity celebrity = new Celebrity();
		celebrity.setId(rs.getInt("id"));
		celebrity.setName(rs.getString("name"));
		celebrity.setIsDirector(rs.getBoolean("director"));
		celebrity.setIsActor(rs.getBoolean("actor"));
		celebrity.setIsScriptwriter(rs.getBoolean("scriptwriter"));
		celebrity.setValidationStatus(rs.getInt("validationstatus"));
		return celebrity;
	}

}
