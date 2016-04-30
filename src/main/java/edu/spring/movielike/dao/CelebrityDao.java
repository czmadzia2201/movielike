package edu.spring.movielike.dao;

import java.util.ArrayList;

public interface CelebrityDao<C, R> {

	public ArrayList<C> findAllByRole(R role);
	
}
