package com.project.restaurant.base.exceptions;

public class DuplicateException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DuplicateException(String message) {
		super(message);
	}
}
