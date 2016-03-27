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

