package edu.spring.movielike.dataproviders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.model.Celebrity;

public class CelebrityProvider {

	private DaoFactory daoFactory = new DaoFactory();
	private CelebrityDao<Celebrity, CelebrityRole> jdbcCelebrityObject = daoFactory.getCelebrityDao();

	public ArrayList<String> getCelebrityList(CelebrityRole role) {
		ArrayList<Celebrity> celebrities = jdbcCelebrityObject.findAllCelebritiesByRole(role);
		ArrayList<String> celebritiesNames = new ArrayList<String>();
		for (Celebrity celebrity : celebrities) {
			celebritiesNames.add('"' + celebrity.toString() + '"');
		}
		return celebritiesNames;
	}
	
	public Set<Celebrity> getCelebrities(Set<String> celebrityNames) {
		Set<Celebrity> celebrities = new HashSet<Celebrity>();
		if (!celebrityNames.isEmpty()) {
			celebrities.addAll(jdbcCelebrityObject.findCelebritiesByName(celebrityNames));			
		}
		return celebrities;
	}
	
	public Set<String> setCelebrityNames(Set<Celebrity> celebrities) {
		Set<String> celebrityNames = new HashSet<String>();
		for (Celebrity celebrity : celebrities) {		
			celebrityNames.add(celebrity.getName());
		}
		return celebrityNames;
	}
}
