package edu.spring.movielike.model;

import java.io.Serializable;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_movie_rate")
public class UserRating implements Serializable {
	
	private String username;
	private int id, movieId, ratingSum, votes;
	private Timestamp lastRated;
	
	public UserRating() {
	}
	
	public UserRating(int movieId, String username, int ratingSum, int votes, Timestamp lastRated) {
		this.movieId = movieId;
		this.username = username;
		this.ratingSum = ratingSum;
		this.votes = votes;
		this.lastRated = lastRated;
	}

	@Id
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Id
	@Column(name = "movie_id")
	public int getMovieId() {
		return movieId;
	}
	
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	@Column(name = "rating_sum")
	public int getRatingSum() {
		return ratingSum;
	}
	
	public void setRatingSum(int ratingSum) {
		this.ratingSum = ratingSum;
	}
	
	@Column(name = "votes")
	public int getVotes() {
		return votes;
	}
	
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	@Column(name = "last_rated")
	public Timestamp getLastRated() {
		return lastRated;
	}
	
	public void setLastRated(Timestamp lastRated) {
		this.lastRated = lastRated;
	}

	@Transient
	public int getHourDiff() {
		Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());
		int hourDiff = 25;
		if (lastRated!=null) {
			hourDiff = (int) ((now.getTime() - lastRated.getTime()) / (60 * 60 * 1000));
		}
		return hourDiff;
	}
	
	@Transient
	public String getRatingAvg() {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.CEILING);
		String ratingAvg = "–.–";
		if (votes!=0) {
			ratingAvg = df.format((double)ratingSum / votes);
		}
		return ratingAvg;
	}

}
