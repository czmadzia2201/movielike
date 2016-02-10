package edu.spring.movielike.dao;

import java.util.ArrayList;

public interface UserDao<U> {
	public void persistUser(U user);	
	public void deleteUser(U user);
	public ArrayList<U> findAllUsers();
	public U findUser(String username);
}
