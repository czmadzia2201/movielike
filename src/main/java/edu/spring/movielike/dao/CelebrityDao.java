package edu.spring.movielike.dao;

import java.util.ArrayList;
import java.util.Set;

public interface CelebrityDao<C, R> {

	public ArrayList<C> findAllCelebritiesByRole(R role);
	public C findCelebrityById(int id);
	public ArrayList<C> findCelebritiesByName(Set<String> names);
	
}
