package edu.spring.movielike.dao;

import java.util.ArrayList;

public interface CelebrityDao<C, R> {

	public ArrayList<C> findAllCelebritiesByRole(R role);
	public C findCelebrityById(int id);
	public C findCelebrityByName(String name);
	
}
