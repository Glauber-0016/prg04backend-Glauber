package br.com.ifba.author.controller;

import br.com.ifba.author.dto.AuthorCreateDTO;
import br.com.ifba.author.dto.AuthorResponseDTO;
import br.com.ifba.author.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar as operações relacionadas aos Autores (literários).
 * <p>
 * Esta classe expõe os endpoints da API para criar e consultar autores.
 */
@RestController
@RequestMapping("/api/authors") // Define o caminho base para todos os endpoints deste controller
public class AuthorController {

    /**
     * Injeção de dependência do AuthorService, que contém a lógica de negócio para autores.
     */
    @Autowired
    private AuthorService authorService;

    /**
     * Endpoint para criar um novo autor.
     * Acessível via requisição POST para /api/authors.
     *
     * @param createDTO DTO contendo os dados para a criação do autor, validado.
     * @return um ResponseEntity com o DTO do autor criado e o status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@Valid @RequestBody AuthorCreateDTO createDTO) {
        AuthorResponseDTO createdAuthor = authorService.createAuthor(createDTO);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar uma lista de todos os autores cadastrados.
     * Acessível via requisição GET para /api/authors.
     *
     * @return um ResponseEntity com a lista de DTOs de autores e o status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.findAllAuthors());
    }

    /**
     * Endpoint para buscar um autor específico pelo seu ID.
     * Acessível via requisição GET para /api/authors/{id}.
     *
     * @param id O ID do autor a ser buscado, extraído da URL.
     * @return um ResponseEntity com o DTO do autor encontrado e o status HTTP 200 (OK).
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o autor não for encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findAuthorById(id));
    }
}