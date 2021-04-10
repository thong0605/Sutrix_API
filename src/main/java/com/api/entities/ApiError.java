package com.api.entities;

import java.util.ArrayList;
import java.util.List;

public class ApiError {

	private String message;
	private List<String> errors;

	public ApiError(String message, List<String> errors) {
		super();
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			return new ArrayList<String>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
