package edu.spring.movielike.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;

import edu.spring.movielike.model.User;

public class JdbcUserDaoH implements UserDao<User> {

	private ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

	public void persistUser(User user) { 
		user.setEnabled(1);
		user.setRole("user");
		connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().save(user);		
		connectionHandler.closeCurrentSessionwithTransaction();
	}
 
	public void deleteUser(User user){
		Session session = connectionHandler.openCurrentSessionwithTransaction();
		connectionHandler.getCurrentSession().delete(user);
		String sql2 = String.format("DELETE FROM user_movie_link WHERE username = '%s'", user.getUsername());
		session.createSQLQuery(sql2).executeUpdate();
		String sql3 = String.format("UPDATE review SET author = '" + user.getUsername() + "_deleted' WHERE author = '%s'", user.getUsername());
		session.createSQLQuery(sql3).executeUpdate();
		String sql4 = String.format("UPDATE movie SET added_by = '" + user.getUsername() + "_deleted' WHERE added_by = '%s'", user.getUsername());
		session.createSQLQuery(sql4).executeUpdate();
		connectionHandler.closeCurrentSessionwithTransaction();
	}

	@SuppressWarnings({ "unchecked" })
	public ArrayList<User> findAllUsers() {
		connectionHandler.openCurrentSession();
		Criteria criteria = connectionHandler.getCurrentSession().createCriteria(User.class);
		ArrayList<User> userList = (ArrayList<User>) criteria.list();
		connectionHandler.closeCurrentSession();
		return userList; 
	}

	public User findUser(String username) {
		connectionHandler.openCurrentSession();
		User user = (User) connectionHandler.getCurrentSession().get(User.class, username);
		connectionHandler.closeCurrentSession();
		return user;
	}
 
}
