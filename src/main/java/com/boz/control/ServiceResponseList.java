package com.boz.control;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class ServiceResponseList<T> extends PageImpl<T> {
	private static final long serialVersionUID = 1L;
	
	private Builder<String> errorBuilder = new Builder<>();
	
	public ServiceResponseList(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}	
	
	public ServiceResponseList(List<T> content) {
		super(content);
	}
	
	public ImmutableList<String> getErrors() {
		return this.errorBuilder.build();
	}

	public void addError(String error) {
		this.errorBuilder.add(error);		
	}	

}
