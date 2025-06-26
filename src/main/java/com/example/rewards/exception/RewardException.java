package com.example.rewards.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardException extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus statusCode;

	public RewardException() {
		super();
	}

	public RewardException(HttpStatus code, String message) {
		super(message);
		this.statusCode = code;
	}

}
