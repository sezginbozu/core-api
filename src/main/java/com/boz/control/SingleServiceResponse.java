package com.boz.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class SingleServiceResponse<T> extends ResponseEntity<T> {
	private Builder<String> errorBuilder = new Builder<>();
	
	public SingleServiceResponse() {
		super(HttpStatus.OK);
	}	

	public SingleServiceResponse(T body) {
		super(body, HttpStatus.OK);
	}	
	
	public SingleServiceResponse(T body, HttpStatus status) {
		super(body, status);
	}	
	
	public ImmutableList<String> getErrors() {
		return this.errorBuilder.build();
	}

	public void addError(String error) {
		this.errorBuilder.add(error);		
	}	

}
