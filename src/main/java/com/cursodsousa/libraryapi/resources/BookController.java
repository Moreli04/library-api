package com.cursodsousa.libraryapi.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursodsousa.libraryapi.dto.BookDTO;
import com.cursodsousa.libraryapi.entity.Book;
import com.cursodsousa.libraryapi.exception.ApiErrors;
import com.cursodsousa.libraryapi.exception.BusinessException;
import com.cursodsousa.libraryapi.service.BookService;
import com.cursodsousa.libraryapi.util.ModelUtils;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody @Valid BookDTO dto) {
		Book entity = ModelUtils.convertModel(dto, Book.class);
		entity = service.save(entity);
		return ModelUtils.convertModel(entity, BookDTO.class);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		return new ApiErrors(bindingResult);
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleBusinessException(BusinessException ex) {
		return new ApiErrors(ex);
	}
}
