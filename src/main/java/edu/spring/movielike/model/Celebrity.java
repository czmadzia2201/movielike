package edu.spring.movielike.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "celebrity")
public class Celebrity {

	private int id, validationStatus; 
	private String name;
	private boolean isDirector, isActor, isScriptwriter;
	private Set<Movie> moviesActed;
	private Set<Movie> moviesDirected;

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
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "leadActors")
	public Set<Movie> getMoviesActed() {
		return moviesActed;
	}

	public void setMoviesActed(Set<Movie> moviesActed) {
		this.moviesActed = moviesActed;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "directors")
	public Set<Movie> getMoviesDirected() {
		return moviesDirected;
	}

	public void setMoviesDirected(Set<Movie> moviesDirected) {
		this.moviesDirected = moviesDirected;
	}
	
	@Column(name = "validationstatus")
	public int getValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(int validationStatus) {
		this.validationStatus = validationStatus;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (isActor ? 1231 : 1237);
		result = prime * result + (isDirector ? 1231 : 1237);
		result = prime * result + (isScriptwriter ? 1231 : 1237);
		result = prime * result + ((moviesActed == null) ? 0 : moviesActed.hashCode());
		result = prime * result + ((moviesDirected == null) ? 0 : moviesDirected.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + validationStatus;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celebrity other = (Celebrity) obj;
		if (id != other.id)
			return false;
		if (isActor != other.isActor)
			return false;
		if (isDirector != other.isDirector)
			return false;
		if (isScriptwriter != other.isScriptwriter)
			return false;
		if (moviesActed == null) {
			if (other.moviesActed != null)
				return false;
		} else if (!moviesActed.equals(other.moviesActed))
			return false;
		if (moviesDirected == null) {
			if (other.moviesDirected != null)
				return false;
		} else if (!moviesDirected.equals(other.moviesDirected))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (validationStatus != other.validationStatus)
			return false;
		return true;
	}

	public String toString() {
		return name;
	}

}
