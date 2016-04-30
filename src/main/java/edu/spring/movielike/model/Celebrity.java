package edu.spring.movielike.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "celebrity")
public class Celebrity {

	private int id; 
	private String name;
	private boolean isDirector, isActor, isScriptwriter;

	@Id
	@GeneratedValue
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "director")
	public boolean getIsDirector() {
		return isDirector;
	}

	public void setIsDirector(boolean isDirector) {
		this.isDirector = isDirector;
	}

	@Column(name = "actor")
	public boolean getIsActor() {
		return isActor;
	}

	public void setIsActor(boolean isActor) {
		this.isActor = isActor;
	}
	
	@Column(name = "scriptwriter")
	public boolean getIsScriptwriter() {
		return isScriptwriter;
	}

	public void setIsScriptwriter(boolean isScriptwriter) {
		this.isScriptwriter = isScriptwriter;
	}
	
	public String toString() {
		return name;
	}

}
