package com.chandrika.taskmanager.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler { 

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(TaskNotFoundException ex){
		ApiError body = new ApiError(LocalDateTime.now(),404,"Not Found",ex.getMessage(),null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex){
		Map<String,String> fieldErrors=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(e->fieldErrors.put(e.getField(),e.getDefaultMessage()));
		ApiError body = new ApiError(LocalDateTime.now(),400,"Bad Request","Validation Failed",fieldErrors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleUnreadable(HttpMessageNotReadableException ex){
		ApiError body = new ApiError(LocalDateTime.now(),400,"Bad Request","Malformed or inavlid JSON",null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
}
