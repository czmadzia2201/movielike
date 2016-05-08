package edu.spring.movielike.dao.hibernateImpl;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dao.ConnectionHandler;
import edu.spring.movielike.dataproviders.CelebrityRole;
import edu.spring.movielike.model.Celebrity;
import edu.spring.movielike.model.Movie;

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

	@SuppressWarnings("unchecked")
	public ArrayList<Celebrity> findCelebritiesByName(Set<String> names) {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(Celebrity.class);
		ArrayList<Celebrity> celebrities = (ArrayList<Celebrity>) criteria.add(Restrictions.in("name", names)).list();
		connectionHandler.closeCurrentSession();
		return celebrities;
	}

}
