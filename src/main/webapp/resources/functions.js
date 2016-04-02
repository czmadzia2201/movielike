function validateSearchMovies(emptyValue, formatError) {
	var criteriaValue = document.forms["searchMovies"]["criteriaValue"].value;
	var searchCriteria = document.forms["searchMovies"]["searchCriteria"].value;
	if (criteriaValue == null || criteriaValue == "") {
		document.getElementById("errorMsg").innerHTML = emptyValue;
		return false;
	}
	if (searchCriteria == "year" && isNaN(criteriaValue)) {
		document.getElementById("errorMsg").innerHTML = formatError;
		return false;
	}
}

function validateReview(emptyValue) {
	var reviewText = document.forms["addEditReview"]["content"].value;
	if (reviewText == null || reviewText == "") {
		document.getElementById("errorMsg").innerHTML = emptyValue;
		return false;
	}
}

function clearForm() {
	document.forms.reset();
}

function show(target) {
	document.getElementById(target).style.display='block';
	return false;
}

function hide(target) {
	document.getElementById(target).style.display='none';
	return false;
}

function disableRating(userVotes) {
	if (userVotes > 5) {
		document.getElementById("starRating").style.display = 'none';
		document.getElementById("rateMessage").innerHTML = "You have rated this movie."
	}
}

function lightTheStar(id) {
	for (i = 1; i <= id; i++) {
		document.getElementById("star-"+i).className = "starButtonActive";		
	}
}

function unlightTheStar(id) {
	for (i = 1; i <= id; i++) {
		document.getElementById("star-"+i).className = "starButtonInactive";		
	}
}

