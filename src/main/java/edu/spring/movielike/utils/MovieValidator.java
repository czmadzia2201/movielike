package edu.spring.movielike.utils;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.User;

public class MovieValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return Movie.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required.title");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year", "required.year");

		Movie movie = (Movie)target;
		
		if (!Integer.toString(movie.getYear()).matches("^(19|20)\\d{2}$") || (movie.getYear() > Calendar.getInstance().get(Calendar.YEAR))) {
			errors.rejectValue("year", "formaterror.year");
		}

	}
		
}
