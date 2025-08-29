package br.com.ifba.book.controller;

import br.com.ifba.book.dto.BookCreateDTO;
import br.com.ifba.book.dto.BookResponseDTO;
import br.com.ifba.book.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar as operações relacionadas a Livros.
 * <p>
 * Esta classe expõe os endpoints da API para criar e listar livros.
 */
@RestController
@RequestMapping("/api/books") // Define o caminho base para todos os endpoints deste controller
public class BookController {

    /**
     * Injeção de dependência do BookService, que contém a lógica de negócio para livros.
     */
    @Autowired
    private BookService bookService;

    /**
     * Endpoint para criar um novo livro.
     * Acessível via requisição POST para /api/books.
     *
     * @param createDTO DTO contendo os dados para a criação do livro, validado.
     * @return um ResponseEntity com o DTO do livro criado e o status HTTP 201 (Created).
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o autor especificado no DTO não for encontrado.
     */
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookCreateDTO createDTO) {
        BookResponseDTO createdBook = bookService.createBook(createDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar uma lista de todos os livros cadastrados.
     * Acessível via requisição GET para /api/books.
     *
     * @return um ResponseEntity com a lista de DTOs de livros e o status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }
}