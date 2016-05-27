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
			celebritiesNames.add('"' + celebrity.getName() + '"');
		}
		return celebritiesNames;
	}
	
	public Set<Celebrity> getCelebrities(Set<String> celebrityNames, CelebrityRole role, CelebrityStatus status) {
		Set<Celebrity> celebrities = new HashSet<Celebrity>();
		if (!celebrityNames.isEmpty()) {
			for (String celebrityName : celebrityNames) {
				Celebrity celebrity = new Celebrity();
				if (jdbcCelebrityObject.findCelebrityByName(celebrityName) != null) {
					celebrity = jdbcCelebrityObject.findCelebrityByName(celebrityName);
					updateCelebrityRole(celebrity, role);
					jdbcCelebrityObject.updateCelebrity(celebrity);
				} else if (!celebrityName.equals(" ".trim())) {
					celebrity.setName(celebrityName);
					celebrity.setValidationStatus(status.getStatus());
					setCelebrityRole(celebrity, role);
					jdbcCelebrityObject.persistCelebrity(celebrity);
				}
			}
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
	
	private void setCelebrityRole(Celebrity celebrity, CelebrityRole role) {
		celebrity.setIsActor(false);
		celebrity.setIsDirector(false);
		celebrity.setIsScriptwriter(false);
		updateCelebrityRole(celebrity, role);
	}
	
	private void updateCelebrityRole(Celebrity celebrity, CelebrityRole role) {
		if (role == CelebrityRole.ACTOR && celebrity.getIsActor()==false) celebrity.setIsActor(true);
		if (role == CelebrityRole.DIRECTOR && celebrity.getIsDirector()==false) celebrity.setIsDirector(true);
		if (role == CelebrityRole.SCRIPTWRITER && celebrity.getIsScriptwriter()==false) celebrity.setIsScriptwriter(true);		
	}
}
