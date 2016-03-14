package edu.spring.movielike.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "movie_rejected")
public class MovieRejected {
	String title, director, leadActors, genre, country, description, addedBy, reason, genreString;
	int id, year, status;
	Set<String> genreList;

	@Id
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

	@ElementCollection
	@CollectionTable(name="movie_genre", joinColumns=@JoinColumn(name="movie_id"))
	@Column(name="genrelist")
	public Set<String> getGenreList() {
		return genreList;
	}

	public void setGenreList(Set<String> genreList) {
		this.genreList = genreList;
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

	@Column(name = "reason")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Transient
	public String getGenreString() {
		return genreList.toString().replace("[", "").replace("]", "");
	}
	
	public String toString() {
		return "title: " + title + "<br>director: " + director + "<br>genre: "
				+ getGenreString() + "<br>lead actors: " + leadActors + "<br>year: "
				+ year + "<br>country: " + country + "<br>description: "
				+ description;
	}

}