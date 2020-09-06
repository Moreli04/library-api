package com.cursodsousa.libraryapi.service.impl;

import org.springframework.stereotype.Service;

import com.cursodsousa.libraryapi.entity.Book;
import com.cursodsousa.libraryapi.exception.BusinessException;
import com.cursodsousa.libraryapi.repository.BookRepository;
import com.cursodsousa.libraryapi.service.BookService;

@Service
public class BookServiceImpl implements BookService{

	private BookRepository repository;

	public BookServiceImpl(BookRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Book save(Book book) {
		if(repository.existsByIsbn(book.getIsbn())) {
			throw new BusinessException("Isbn já cadastrado.");
		}
		return repository.save(book);
	}

}
