package br.com.ifba.book.service;

import br.com.ifba.author.entity.Author;
import br.com.ifba.author.repository.AuthorRepository;
import br.com.ifba.book.dto.BookCreateDTO;
import br.com.ifba.book.dto.BookResponseDTO;
import br.com.ifba.book.entity.Book;
import br.com.ifba.book.repository.BookRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public BookResponseDTO createBook(BookCreateDTO createDTO) {
        Author author = authorRepository.findById(createDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com o ID: " + createDTO.getAuthorId()));

        Book newBook = new Book();
        newBook.setBookTitle(createDTO.getBookTitle());
        newBook.setPubYear(createDTO.getPubYear());
        newBook.setSynopsis(createDTO.getSynopsis());
        newBook.setCover(createDTO.getCover());
        newBook.setAuthor(author);

        Book savedBook = bookRepository.save(newBook);
        return toResponseDTO(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private BookResponseDTO toResponseDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getBookTitle(),
                book.getPubYear(),
                book.getSynopsis(),
                book.getCover(),
                book.getAuthor().getId(),
                book.getAuthor().getAuthorName()
        );
    }
}