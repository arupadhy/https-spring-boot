package com.sapient.app.shop.org.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.mongodb.MongoTimeoutException;

@RestControllerAdvice
public class FutureStoreExceptionalHandler {

	@ExceptionHandler(value=MongoTimeoutException.class)
	public ResponseEntity<String> handleException(MongoTimeoutException ex,WebRequest req) {
		return new ResponseEntity<String>(String.format("MongoDB connection failed %s. Make sure vpn connection is established", ex.toString()), HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgException(IllegalArgumentException ex,WebRequest req) {
		return new ResponseEntity<String>(String.format("Request %s does not look valid %s",req.toString()), HttpStatus.BAD_REQUEST);
	}
	
}
