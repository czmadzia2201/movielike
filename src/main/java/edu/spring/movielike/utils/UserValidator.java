package edu.spring.movielike.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.spring.movielike.model.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required.confirmPassword");
		
		User user = (User)target;
		
		if (!(user.getPassword().equals(user.getConfirmPassword()))) {
			errors.rejectValue("confirmPassword", "notmatch.confirmPassword");
		}
		if (user.getUsername().equals("anonymousUser")) {
			errors.rejectValue("username", "restricted.username");			
		}
		if (user.getUsername().contains("_deleted")) {
			errors.rejectValue("username", "restrictedPhrase.username");			
		}
		if (user.getUsername().length()<4) {
			errors.rejectValue("username", "tooshort.username");			
		}
		
	}

}
