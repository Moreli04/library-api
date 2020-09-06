package com.cursodsousa.libraryapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cursodsousa.libraryapi.entity.Book;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	BookRepository repository;
	
	@Test
	@DisplayName("Deve retornar verdadeiro quando existir um livro com isbn já utilizado por outro.")
	public void returnTrueWhenIsbnExists() {
		final String isbn = "123";
		Book book = Book.builder().title("Aventuras").author("Fulano").isbn(isbn).build();
		entityManager.persist(book);
		
		final boolean exists = repository.existsByIsbn(isbn);
		assertThat(exists).isTrue();
	}
	
	@Test
	@DisplayName("Deve retornar falso quando existir um livro com isbn já utilizado por outro.")
	public void returnFalseWhenIsbnDoesntNotExists() {
		final String isbn = "123";
		final boolean exists = repository.existsByIsbn(isbn);
		assertThat(exists).isFalse();
	}
}
