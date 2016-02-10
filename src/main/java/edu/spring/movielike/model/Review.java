package edu.spring.movielike.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.dao.MovieDao;

@Entity
@Table(name = "review")
public class Review {
	String author, content, movieTitle;
	int id, movieId;
	
	DaoFactory daoFactory = new DaoFactory();
	MovieDao<Movie, MovieRejected> jdbcMovieObject = daoFactory.getMovieDao();
	
	@Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "movie_id")
	public int getMovieId() {
		return movieId;
	}
	
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	@Column(name = "author")
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Transient
	public String getMovieTitle() {
		Movie movie = jdbcMovieObject.findAllMoviesByProperty("id", movieId).get(0);
		return movie.getTitle();
	}

}