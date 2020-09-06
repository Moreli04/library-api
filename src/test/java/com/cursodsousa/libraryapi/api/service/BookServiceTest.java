package com.cursodsousa.libraryapi.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cursodsousa.libraryapi.entity.Book;
import com.cursodsousa.libraryapi.exception.BusinessException;
import com.cursodsousa.libraryapi.repository.BookRepository;
import com.cursodsousa.libraryapi.service.impl.BookServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
	
	BookServiceImpl service;
	
	@MockBean
	private BookRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new BookServiceImpl(repository);
	}
	
	@Test
	@DisplayName("Deve salvar um livro")
	public void savedBookTest() {
		Book book = createValidBook();
		Book savedBookMock = Book.builder().id(11L).isbn("123").author("Fulano").title("As aventuras").build();
		Mockito.when(repository.existsByIsbn(book.getIsbn())).thenReturn(false);
		Mockito.when(service.save(book)).thenReturn(savedBookMock);
		
		Book savedBook = service.save(book);
		
		assertThat(savedBook.getId()).isNotNull();
		assertThat(savedBook.getIsbn()).isEqualTo("123");
		assertThat(savedBook.getTitle()).isEqualTo("As aventuras");
		assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
	}
	
	@Test
	@DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
	public void shouldNotSaveABookWithDuplicatedIsbn() {
		Book book = createValidBook();
		
		Mockito.when(repository.existsByIsbn(book.getIsbn())).thenReturn(true);
		Throwable exception = Assertions.catchThrowable(() -> service.save(book));
		
		assertThat(exception)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Isbn já cadastrado.");
		
		Mockito.verify(repository, Mockito.never()).save(book);
		
	}

	private Book createValidBook() {
		return Book.builder().isbn("123").author("Fulano").title("As aventuras").build();
	}

}
