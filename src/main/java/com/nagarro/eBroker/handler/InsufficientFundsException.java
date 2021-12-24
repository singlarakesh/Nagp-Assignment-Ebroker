package com.nagarro.eBroker.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InsufficientFundsException extends RuntimeException {
	private static final long serialVersionUID = 2L;

	public InsufficientFundsException(String msg) {
		super(msg);
	}
}
