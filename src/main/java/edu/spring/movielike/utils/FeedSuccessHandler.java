package edu.spring.movielike.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class FeedSuccessHandler implements AuthenticationSuccessHandler {

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
		
		String referrerUrl = request.getHeader("referer");
		if (referrerUrl.contains("?login_error")) {
			if (referrerUrl.contains("?login_error&")) {
				referrerUrl = referrerUrl.replace("login_error&", "");				
			} else {
				referrerUrl = referrerUrl.replace("?login_error", "");
			}
		}
        response.sendRedirect(referrerUrl);
    }

}