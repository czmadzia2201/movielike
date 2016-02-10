package edu.spring.movielike.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class FeedFailureHandler implements AuthenticationFailureHandler {

	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
		
		String referrerUrl = request.getHeader("referer");
		if (referrerUrl.contains("?")) {
			if (!referrerUrl.contains("login_error")) {
				String[] urlSplit = referrerUrl.split("\\?");
				referrerUrl = urlSplit[0] + "?login_error&" + urlSplit[1];
			}
		} else {
			referrerUrl = referrerUrl + "?login_error";
		}
		response.sendRedirect(referrerUrl);
	}
}