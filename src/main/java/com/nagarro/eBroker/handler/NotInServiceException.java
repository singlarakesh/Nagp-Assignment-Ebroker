package com.nagarro.eBroker.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotInServiceException extends RuntimeException {
	private static final long serialVersionUID = 2L;

	public NotInServiceException(String msg) {
		super(msg);
	}
}
