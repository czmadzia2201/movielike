package edu.spring.movielike.dataproviders;

import java.util.ArrayList;

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.model.Celebrity;

public enum CelebrityRole {
	
	ACTOR, DIRECTOR, SCRIPTWRITER;
	
	private static DaoFactory daoFactory = new DaoFactory();
	private static CelebrityDao<Celebrity, CelebrityRole> jdbcCelebrityObject = daoFactory.getCelebrityDao();

	public static ArrayList<String> getCelebrityList(CelebrityRole role) {
		ArrayList<Celebrity> celebrities = jdbcCelebrityObject.findAllByRole(role);
		ArrayList<String> celebritiesNames = new ArrayList<String>();
		for (Celebrity celebrity : celebrities) {
			celebritiesNames.add('"' + celebrity.toString() + '"');
		}
		return celebritiesNames;
	}
	
}
