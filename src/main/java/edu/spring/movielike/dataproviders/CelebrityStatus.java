package edu.spring.movielike.dataproviders;

public enum CelebrityStatus {
	
	VALID(1), EDIT_ADDED(-1), ADD_ADDED(0);
	
	int status;
	
	CelebrityStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

}
