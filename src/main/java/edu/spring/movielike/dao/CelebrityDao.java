package edu.spring.movielike.dao;

import java.util.ArrayList;
import java.util.Set;

public interface CelebrityDao<C, R> {

	public ArrayList<C> findAllCelebritiesByRole(R role);
	public ArrayList<C> findAllCelebritiesByStatus(int status);
	public C findCelebrityById(int id);
	public ArrayList<C> findCelebritiesByName(Set<String> names);
	public void persistCelebrity(C celebrity);
	public void deleteCelebrity(int id);
	public void updateCelebrity(C celebrity);
	public C findCelebrityByName(String name);
}
