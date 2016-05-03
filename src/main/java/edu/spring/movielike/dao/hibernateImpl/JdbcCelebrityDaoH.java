package edu.spring.movielike.dao.hibernateImpl;

import java.util.ArrayList;

import org.hibernate.Session;

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dao.ConnectionHandler;
import edu.spring.movielike.dataproviders.CelebrityRole;
import edu.spring.movielike.model.Celebrity;

public class JdbcCelebrityDaoH implements CelebrityDao<Celebrity, CelebrityRole> {

	private ConnectionHandler connectionHandler = ConnectionHandler.getInstance();
	
	@SuppressWarnings("unchecked")
	public ArrayList<Celebrity> findAllCelebritiesByRole(CelebrityRole role) {
		String sql = "SELECT * FROM celebrity WHERE " + role + " = 1";
		Session session = connectionHandler.openCurrentSession();
		ArrayList<Celebrity> celebrityList = (ArrayList<Celebrity>) session.createSQLQuery(sql).addEntity(Celebrity.class).list();
		connectionHandler.closeCurrentSession();
		return celebrityList;
	}

	public Celebrity findCelebrityById(int id) {
		connectionHandler.openCurrentSession();
		Celebrity celebrity = (Celebrity) connectionHandler.getCurrentSession().get(Celebrity.class, id);
		connectionHandler.closeCurrentSession();
		return celebrity;
	}

	public Celebrity findCelebrityByName(String name) {
		connectionHandler.openCurrentSession();
		Celebrity celebrity = (Celebrity) connectionHandler.getCurrentSession().get(Celebrity.class, name);
		connectionHandler.closeCurrentSession();
		return celebrity;
	}

}
