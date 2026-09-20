package com.jsp.foodorder.food_order_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jsp.foodorder.food_order_app.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNFE(IdNotFoundException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("Failure");
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidPhoneNumber.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNFE(InvalidPhoneNumber exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage(exception.getMessage());
		res.setData("Invalid Phonenumber");
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> NRAE(NoRecordAvailableException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setMessage(exception.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData("Failure");
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}

}
