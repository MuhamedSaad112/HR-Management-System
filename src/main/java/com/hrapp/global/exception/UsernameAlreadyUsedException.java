package com.hrapp.global.exception;

public class UsernameAlreadyUsedException extends RuntimeException {

	public UsernameAlreadyUsedException() {
		super("Login name already used!");
	}
}
