package edu.spring.movielike.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import edu.spring.movielike.dao.CelebrityDao;
import edu.spring.movielike.dao.DaoFactory;
import edu.spring.movielike.dao.MovieDao;
import edu.spring.movielike.dao.ReviewDao;
import edu.spring.movielike.dao.UserDao;
import edu.spring.movielike.dao.UserMovieDao;
import edu.spring.movielike.dao.UserRatingDao;
import edu.spring.movielike.dataproviders.CelebrityProvider;
import edu.spring.movielike.dataproviders.CelebrityRole;
import edu.spring.movielike.dataproviders.MovieDataProvider;
import edu.spring.movielike.model.Celebrity;
import edu.spring.movielike.model.Movie;
import edu.spring.movielike.model.MovieRejected;
import edu.spring.movielike.model.Review;
import edu.spring.movielike.model.User;
import edu.spring.movielike.model.UserRating;
import edu.spring.movielike.utils.MovieValidator;
import edu.spring.movielike.utils.UserValidator;
import static edu.spring.movielike.dataproviders.CelebrityRole.*;
import static edu.spring.movielike.dataproviders.CelebrityStatus.*;

@Controller
public class MovielikeController {
	
	private DaoFactory daoFactory = new DaoFactory();
	private MovieDao<Movie, MovieRejected> jdbcMovieObject = daoFactory.getMovieDao();
	private UserDao<User> jdbcUserObject = daoFactory.getUserDao();
	private UserMovieDao<User, Movie> jdbcUserMovieLink = daoFactory.getUserMovieDao();
	private ReviewDao<Review, Movie, User> jdbcReviewObject = daoFactory.getReviewDao();
	private UserRatingDao<Movie, User> jdbcUserRatingObject = daoFactory.getUserRatingDao();
	private CelebrityDao<Celebrity, CelebrityRole> jdbcCelebrityObject = daoFactory.getCelebrityDao();

	private MovieDataProvider movieDataProvider = new MovieDataProvider();
	private CelebrityProvider celebrityProvider = new CelebrityProvider();
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	UserValidator userValidator;
	
	@Autowired
	MovieValidator movieValidator;

	// --------- INCLUDES -----------
	
	@RequestMapping(value = "/loginheader")
	public String loginHeader(@RequestParam(required = false) String login_error, ModelMap modelMap) {
		if (login_error!= null) {
			modelMap.addAttribute("loginFailed", messageSource.getMessage("loginFailed", null, null));
		} 
		return "loginHeader";
	}

	@RequestMapping(value = "/footer")
	public String footer() {
		return "footer";
	}

	// --------- ERROR HANDLING ---------
	
	@RequestMapping(value = "/accessdenied")
	public String accessDenied() {
		return "accessDenied"; 
	}	
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public String entityNotFound(HttpServletRequest request) {
		if (request.getRequestURL().toString().contains("displayuser")) {
			return "userNotFound";
		} else {
			return "movieNotFound";
		}
	}

	@ExceptionHandler(NullPointerException.class)
	public String parameterMissing() {
		return "nullPointerError";
	}

	@RequestMapping(value = "/404")
	public String error404(){
		return "404";
	}

	// ---------- MOVIE -------------
	
	@RequestMapping(value = "/searchmovies")
	public String searchMovies(@ModelAttribute("movie") Movie movie, ModelMap modelMap, 
			HttpServletRequest request,@RequestParam(required = false) String searchCriteria) {
		modelMap.addAttribute("emptyValue", messageSource.getMessage("emptyvalue.criteriaValue", null, null));	
		modelMap.addAttribute("formatError", messageSource.getMessage("formaterror.criteriaValue", null, null));	
		modelMap.addAttribute("movie", movie);
		modelMap.addAttribute("genreList", movieDataProvider.getGenreList());
		List<String> countryList = new ArrayList<String>(movieDataProvider.getCountryListMain());
		countryList.addAll(movieDataProvider.getCountryListOther());
		modelMap.addAttribute("countryList", countryList);
		modelMap.addAttribute("actors", celebrityProvider.getCelebrityList(ACTOR));		
		modelMap.addAttribute("directors", celebrityProvider.getCelebrityList(DIRECTOR));
		modelMap.addAttribute("latestMovies", jdbcMovieObject.findLatest());
		modelMap.addAttribute("mostPopular", jdbcMovieObject.findMostPopular());
		if (searchCriteria!=null) {
			modelMap.addAttribute("searchCriteria", searchCriteria);
		}
		return "searchMovies";
	}
	
	@RequestMapping(value = "/findmovies")
	public String findAllMovies(ModelMap modelMap, @RequestParam(required = true) String searchCriteria,
			@RequestParam(required = true) String criteriaValue) {
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		try {
			movieList = jdbcMovieObject.findAllMoviesByProperty(searchCriteria, criteriaValue);
		} catch (ClassCastException e) {
			movieList = jdbcMovieObject.findAllMoviesByProperty(searchCriteria, Integer.parseInt(criteriaValue));
		}
		modelMap.addAttribute("movieList", movieList);
		return "findMovies";
	}
	
	@RequestMapping(value = "/displaymovie")
	public String displayMovie(@RequestParam(required = true) int id, @RequestParam(required = false) Integer add, 
			@RequestParam(required = false) Integer remove, @RequestParam(required = false) Integer rating, 
			ModelMap modelMap, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
		Movie movie = jdbcMovieObject.findMovieByIdWithStatus(id, 1);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		modelMap.addAttribute("isAdmin", request.isUserInRole("admin"));
		modelMap.addAttribute("movie", movie);
		modelMap.addAttribute("reviewList", jdbcReviewObject.getReviewsByMovie(movie));
		session.setAttribute("movieId", movie.getId());
		if (!auth.getName().equals("anonymousUser")) {
			User user = jdbcUserObject.findUser(auth.getName());
			UserRating userRating = jdbcUserRatingObject.getUserRating(movie, user);
			modelMap.addAttribute("user", user);
			modelMap.addAttribute("isMovieFaved", jdbcUserMovieLink.isUserMovieLinked(user, movie, 1));
			modelMap.addAttribute("isMovieDisfaved", jdbcUserMovieLink.isUserMovieLinked(user, movie, -1));
			modelMap.addAttribute("ratingStars", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
			modelMap.addAttribute("userRating", userRating);
			if (add!=null) {
				jdbcUserMovieLink.linkUserMovie(user, movie, add);
				response.sendRedirect(request.getHeader("referer"));
			}
			if (remove!=null) {
				jdbcUserMovieLink.unlinkUserMovie(user, movie, remove);
				response.sendRedirect(request.getHeader("referer"));
			}
			if (rating!=null) {
				jdbcMovieObject.rateMovie(movie, rating);
				jdbcUserRatingObject.submitVote(movie, user, rating);
				response.sendRedirect(request.getHeader("referer"));
			}
		}
		return "displayMovie";
	}
	
	@RequestMapping(value = "/displaypendingmovie")
	public String displayPendingMovie(@RequestParam(required = true) int id, 
			ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getName().equals(username) && !request.isUserInRole("admin")) {
			return "accessDenied";
		}
		Movie movie = jdbcMovieObject.findMovieByIdWithStatus(id, 0);
		modelMap.addAttribute("movie", movie);
		session.setAttribute("movieId", movie.getId());
		return "displayPendingMovie";
	}
	
	@RequestMapping(value = "/displayrejectedmovie")
	public String displayRejectedMovie(@RequestParam(required = true) int id, 
			ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getName().equals(username) && !request.isUserInRole("admin")) {
			return "accessDenied";
		}
		MovieRejected movie = jdbcMovieObject.findRejectedMovieById(id);
		modelMap.addAttribute("movie", movie);
		return "displayPendingMovie";
	}
	
	@RequestMapping(value = "/displaymovietovalidate")
	public String displayMovieToValidate(HttpServletRequest request, @RequestParam(required = true) int id, 
			@RequestParam(required = false) Integer status, @RequestParam(required = false) String reason, 
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		Movie movie = jdbcMovieObject.findMovieById(id);
		modelMap.addAttribute("movie", movie);
		if (status!=null) {
			jdbcMovieObject.updateMovieStatus(movie, status, reason);
			response.sendRedirect(request.getHeader("referer"));
		}
		return "displayMovieToValidate";
	}
	
	@RequestMapping(value = "/moviestovalidate")
	public String moviesToValidate(ModelMap modelMap, @RequestParam(required = false) Integer reject) {
		ArrayList<Movie> movieList = jdbcMovieObject.findAllMovies(0);
		modelMap.addAttribute("movieList", movieList);
		jdbcMovieObject.deleteRejectedMoviesAdmin();
		return "movieIndexToValidate";
	}
	
	@RequestMapping(value = "/pendingandrejected")
	public String pendingAndRejected(User user, @RequestParam(required = false) boolean clearRejected, ModelMap modelMap,  
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getName().equals(user.getUsername()) && !request.isUserInRole("admin")) {
			return "accessDenied";
		}
		session.setAttribute("username", user.getUsername());
		modelMap.addAttribute("pendingMoviesList", jdbcMovieObject.findMoviesAddedBy(user.getUsername(), 0));
		modelMap.addAttribute("rejectedMoviesList", jdbcMovieObject.findRejectedMoviesAddedBy(user.getUsername()));
		if (clearRejected) {
			jdbcMovieObject.deleteRejectedMoviesUser(user.getUsername());
			response.sendRedirect(request.getHeader("referer"));				
		}
		return "pendingAndRejected";
	}
	
	@RequestMapping(value = "/movieindex")
	public String movieIndex(ModelMap modelMap) {
		ArrayList<Movie> movieList = jdbcMovieObject.findAllMovies(1); 
		modelMap.addAttribute("movieList", movieList);
		return "movieIndex";
	}
	
	@RequestMapping(value = "/addmovie")
	public String addMovie(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		session.setAttribute("referrerUrl", request.getHeader("referer"));
		Movie movie = new Movie();
		feedModelMap(modelMap);
		modelMap.addAttribute("movie", movie);
		return "addMovie"; 
	}
	
	@RequestMapping(value = "/addmovie", method = RequestMethod.POST)
	public String submitAddMovie(@ModelAttribute("movie") Movie movie, 
			BindingResult result, ModelMap modelMap, HttpSession session) {
		feedModelMap(modelMap);
		movieValidator.validate(movie, result);
		if (result.hasErrors()) {
			return "addMovie";
		} 
		try {
			movie.setDirectors(celebrityProvider.getCelebrities(movie.getDirectorsNames(), DIRECTOR, ADD_ADDED));
			movie.setLeadActors(celebrityProvider.getCelebrities(movie.getLeadActorsNames(), ACTOR, ADD_ADDED));
			session.setAttribute("movieId", jdbcMovieObject.persistMovie(movie));
			return "redirect:/movieadded";
		} catch (DuplicateKeyException e) {
			modelMap.addAttribute("movieExists", messageSource.getMessage("duplicateentry.movie", null, null));
			return "addMovie";				
		}
	}
	
	@RequestMapping(value = "/movieadded")
	public String movieAdded(HttpSession session, ModelMap modelMap) {
		int movieId = (Integer) session.getAttribute("movieId");
		Movie movie = jdbcMovieObject.findMovieByIdWithStatus(movieId, 0);
		modelMap.addAttribute("movie", movie);
		String referrerUrl = (String) session.getAttribute("referrerUrl");
		modelMap.addAttribute("referrerUrl", referrerUrl);
		return "movieAdded";
	}

	@RequestMapping(value = "/editmovie")
	public String editMovie(ModelMap modelMap, HttpSession session, HttpServletRequest request) {
		Integer movieId = (Integer) session.getAttribute("movieId");
		session.setAttribute("referrerUrl", request.getHeader("referer"));
		Movie movie = jdbcMovieObject.findMovieById(movieId);
		movie.setDirectorsNames(celebrityProvider.setCelebrityNames(movie.getDirectors()));
		movie.setLeadActorsNames(celebrityProvider.setCelebrityNames(movie.getLeadActors()));
		feedModelMap(modelMap);
		modelMap.addAttribute("movie", movie);
		return "editMovie";
	}

	@RequestMapping(value = "/editmovie", method = RequestMethod.POST)
	public String submitEditMovie(@ModelAttribute("movie") Movie movie, 
			BindingResult result, ModelMap modelMap, HttpSession session) {
		feedModelMap(modelMap);
		movieValidator.validate(movie, result);
		if (result.hasErrors()) {
			return "editMovie";
		} 
		movie.setDirectors(celebrityProvider.getCelebrities(movie.getDirectorsNames(), DIRECTOR, EDIT_ADDED));
		movie.setLeadActors(celebrityProvider.getCelebrities(movie.getLeadActorsNames(), ACTOR, EDIT_ADDED));
		jdbcMovieObject.updateMovie(movie);
		String referrerUrl = (String) session.getAttribute("referrerUrl");
		modelMap.addAttribute("referrerUrl", referrerUrl);
		return "movieEdited";
	}

	@RequestMapping(value = "/submitdeletemovie")
	public String deleteMovie(int movieId, HttpSession session, RedirectAttributes redirectAttrs) {
		Movie movie = jdbcMovieObject.findMovieByIdWithStatus(movieId, 0);
		session.setAttribute("movieTitle", movie.getTitle());
		session.setAttribute("movieAddedBy", movie.getAddedBy());
		jdbcMovieObject.deleteMovie(movie);
		return "redirect:/moviedeleted";		
	}
	
	@RequestMapping(value = "/moviedeleted")
	public String movieDeleted(ModelMap modelMap, HttpSession session) {
		String movieTitle = (String) session.getAttribute("movieTitle");
		modelMap.addAttribute("movieTitle", movieTitle);
		String movieAddedBy = (String) session.getAttribute("movieAddedBy");
		modelMap.addAttribute("movieAddedBy", movieAddedBy);
		return "movieDeleted";		
	}	

	private void feedModelMap(ModelMap modelMap) {
		modelMap.addAttribute("genreList", movieDataProvider.getGenreList());		
		modelMap.addAttribute("countryListMain", movieDataProvider.getCountryListMain());		
		modelMap.addAttribute("countryListOther", movieDataProvider.getCountryListOther());		
		modelMap.addAttribute("actors", celebrityProvider.getCelebrityList(ACTOR));		
		modelMap.addAttribute("directors", celebrityProvider.getCelebrityList(DIRECTOR));				
	}
	
	// ------------- CELEBRITY -------------
	
	@RequestMapping(value = "/celebritiestovalidate")
	public String celebritiesToValidate(ModelMap modelMap) {
		ArrayList<Celebrity> editAddedCelebrities = jdbcCelebrityObject.findAllCelebritiesByStatus(-1);
		modelMap.addAttribute("editAddedCelebrities", editAddedCelebrities);
		ArrayList<Celebrity> addAddedCelebrities = jdbcCelebrityObject.findAllCelebritiesByStatus(0);
		modelMap.addAttribute("addAddedCelebrities", addAddedCelebrities);
		return "celebrityIndexToValidate";
	}
		
	@RequestMapping(value = "/displaycelebrity")
	public String displayCelebrity(ModelMap modelMap, @RequestParam(required = true) int id) {
		Celebrity celebrity = jdbcCelebrityObject.findCelebrityById(id);
		modelMap.addAttribute("celebrity", celebrity);
		return "displayCelebrity";
	}
		
	// ------------- USER -------------	
	
	@RequestMapping(value = "/userindex")
	public String userIndex(ModelMap modelMap) {
		ArrayList<User> userList = jdbcUserObject.findAllUsers(); 
		modelMap.addAttribute("userList", userList);
		return "userIndex";
	}

	@RequestMapping(value = "/displayuser")
	public String displayUser(User user, ModelMap modelMap, HttpServletRequest request) {
		jdbcUserObject.findUser(user.getUsername());
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("isAdmin", request.isUserInRole("admin"));
		modelMap.addAttribute("favedMovielist", jdbcUserMovieLink.getUserLinkedMovies(user, 1));
		modelMap.addAttribute("disfavedMovielist", jdbcUserMovieLink.getUserLinkedMovies(user, -1));
		modelMap.addAttribute("reviewList", jdbcReviewObject.getReviewsByUser(user));
		modelMap.addAttribute("addedMovies", jdbcMovieObject.findMoviesAddedBy(user.getUsername(), 1));
		int moviesPendingValidation = jdbcMovieObject.findAllMovies(0).size() + jdbcMovieObject.findAllMovies(-1).size();
		modelMap.addAttribute("moviesPendingValidation", moviesPendingValidation);
		int celebsPendingValidation = jdbcCelebrityObject.findAllCelebritiesByStatus(0).size() 
			+ jdbcCelebrityObject.findAllCelebritiesByStatus(-1).size();
		modelMap.addAttribute("celebsPendingValidation", celebsPendingValidation);
		int pendingAndRejected = jdbcMovieObject.findMoviesAddedBy(user.getUsername(), 0).size() 
			+ jdbcMovieObject.findRejectedMoviesAddedBy(user.getUsername()).size();
		modelMap.addAttribute("pendingAndRejected", pendingAndRejected);
		return "displayUser";
	}

	@RequestMapping(value = "/userdeleted")
	public String deleteUser(User user, ModelMap modelMap, HttpServletRequest request) {
		jdbcUserObject.deleteUser(user);
		modelMap.addAttribute("username", user.getUsername());
		if (user.getUsername()!=null && !request.isUserInRole("admin")) {
			SecurityContextHolder.clearContext();
		}
		return "userDeleted";		
	}
	
	@RequestMapping(value = "/adduser")
	public String addUser(ModelMap modelMap, HttpSession session, HttpServletRequest request) {
		session.setAttribute("referrerUrl", request.getHeader("referer"));
		User user = new User();
		modelMap.addAttribute("user", user);
		return "addUser";
	}
	
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String submitAddUser(@ModelAttribute("user") User user, BindingResult result, ModelMap modelMap, HttpSession session) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "addUser";
		}
		try {
			jdbcUserObject.persistUser(user);
			session.setAttribute("username", user.getUsername());
			return "redirect:/useradded";
		} catch (DuplicateKeyException e) {
			modelMap.addAttribute("userExists", messageSource.getMessage("duplicateentry.username", null, null));
			return "addUser";
		}
	}
	
	@RequestMapping(value = "/useradded")
	public String userAdded(ModelMap modelMap, HttpSession session, SessionStatus status) {
		String username = (String) session.getAttribute("username");
		modelMap.addAttribute("username", username);
		String referrerUrl = (String) session.getAttribute("referrerUrl");
		modelMap.addAttribute("referrerUrl", referrerUrl);
		status.setComplete();
		return "userAdded";
	}
			
	// ------------ REVIEW -------------
	
	@RequestMapping(value = "/addreview")
	public String addReview(ModelMap modelMap, String username, Integer movieId) {
		modelMap.addAttribute("emptyValue", messageSource.getMessage("emptyvalue.content", null, null));	
		Review review = new Review();
		review.setAuthor(username);
		review.setMovieId(movieId);
		review.setContent(review.getContent());
		modelMap.addAttribute("review", review);
		return "addReview";
	}
	
	@RequestMapping(value = "/submitaddreview")
	public String submitAddReview(Review review, HttpSession session) {
		jdbcReviewObject.addReview(review);
		session.setAttribute("movieId", review.getMovieId());
		return "redirect:/reviewadded";
	}
	
	@RequestMapping(value = "/editreview")
	public String editReview(Integer reviewId, ModelMap modelMap, HttpSession session, HttpServletRequest request) {
		modelMap.addAttribute("emptyValue", messageSource.getMessage("emptyvalue.content", null, null));
		session.setAttribute("referrerUrl", request.getHeader("referer"));
		Review review = jdbcReviewObject.findReview(reviewId);
		modelMap.addAttribute("review", review);
		return "editReview";		
	}

	@RequestMapping(value = "/reviewedited")
	public String reviewEdited(@ModelAttribute("review") Review review, ModelMap modelMap, HttpSession session) {
		jdbcReviewObject.updateReview(review);
		String referrerUrl = (String) session.getAttribute("referrerUrl");
		modelMap.addAttribute("referrerUrl", referrerUrl);
		return "reviewEdited";
	}

	@RequestMapping(value = "/reviewadded")
	public String reviewAdded(ModelMap modelMap, HttpSession session) {
		int movieId = (Integer) session.getAttribute("movieId");
		modelMap.addAttribute("movieId", movieId);		
		return "reviewAdded";
	}
	
	@RequestMapping(value = "/submitdeletereview")
	public String deleteReview(int reviewId, ModelMap modelMap, RedirectAttributes redirectAttrs) {
		Review review = jdbcReviewObject.findReview(reviewId);
		jdbcReviewObject.deleteReview(review);
		return "redirect:/reviewdeleted";		
	}

	@RequestMapping(value = "/reviewdeleted")
	public String reviewDeleted(ModelMap modelMap, HttpServletRequest request) {
		modelMap.addAttribute("referrerUrl", request.getHeader("referer"));
		return "reviewDeleted";		
	}

}
