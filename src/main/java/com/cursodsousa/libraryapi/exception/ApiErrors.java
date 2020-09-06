package com.cursodsousa.libraryapi.exception;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class ApiErrors {
	private List<String> errors;
	
	public ApiErrors(BindingResult bindingResult) {
		super();
		this.errors = extrairErros(bindingResult);
	}

	public ApiErrors(BusinessException ex) {
		super();
		this.errors = Collections.singletonList(ex.getMessage());
	}

	private List<String> extrairErros(BindingResult bindingResult) {
		return bindingResult.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());
	}
}
