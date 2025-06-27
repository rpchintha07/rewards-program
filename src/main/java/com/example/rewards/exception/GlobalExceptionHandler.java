package com.example.rewards.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex) {
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now());
		errorDetails.put("error", "Internal Server Error");
		errorDetails.put("message", ex.getMessage());

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<?> handleTransactionNotFoundException(TransactionNotFoundException ex) {
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("timestamp", LocalDateTime.now());
		errorDetails.put("error", "Transaction not found");
		errorDetails.put("message", ex.getMessage());

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

	}

}