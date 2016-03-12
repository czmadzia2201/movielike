package edu.spring.movielike.controller;

import java.util.ArrayList;
import java.util.List;

public class MovieGenres {
	
	String[] genreArray = {
		"Action",
		"Adventure",
		"Animation",
		"Biography",
		"Comedy",
		"Crime",
		"Documentary",
		"Drama",
		"Erotic",
		"Family",
		"Fantasy",
		"Historical",
		"Horror",
		"Musical",
		"Mystery",
		"Political",
		"Romance",
		"SF",
		"Sport",
		"Thriller",
		"War",
		"Western"
	};

	public List<String> getGenreList() {
		List<String> genreList = new ArrayList<String>();
		for (String genre : genreArray) {
			genreList.add(genre);
		}
		return genreList;
	}

}
