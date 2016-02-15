package edu.spring.movielike.model;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "movie")
public class Movie {
	String title, director, leadActors, genre, country, description, addedBy, statusValue;
	int id, year, status;

	@Id
	@GeneratedValue
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "director")
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Column(name = "lead_actors")
	public String getLeadActors() {
		return leadActors;
	}

	public void setLeadActors(String leadActors) {
		this.leadActors = leadActors;
	}

	@Column(name = "genre")
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Column(name = "year")
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Column(name = "country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "added_by")
	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	@Transient
	public String getStatusValue() {
		HashMap<Integer, String> statusValues = new HashMap<Integer, String>();
		statusValues.put(1, "validated");
		statusValues.put(0, "pending validation");
		statusValues.put(-1, "rejected");
		return statusValues.get(getStatus());
	}

	public String toString() {
		return "title: " + title + "<br>director: " + director + "<br>genre: "
				+ genre + "<br>lead actors: " + leadActors + "<br>year: "
				+ year + "<br>country: " + country + "<br>description: "
				+ description;
	}

}