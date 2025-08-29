package br.com.ifba.article.controller;

import br.com.ifba.article.dto.ArticleCreateDTO;
import br.com.ifba.article.dto.ArticleResponseDTO;
import br.com.ifba.article.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
/**
 * Controller REST para gerenciar as operações relacionadas a Artigos.
 * Define os endpoints da API para criar, buscar e listar artigos.
 */
@RestController
@RequestMapping("/api/articles") // Define o caminho base para todos os endpoints neste controller
@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do frontend
public class ArticleController {

    /**
     * Injeta a dependência do ArticleService, que contém a lógica de negócio para artigos.
     */
    @Autowired
    private ArticleService articleService;

    /**
     * Endpoint para criar um novo artigo.
     * Acessível via requisição POST para /api/articles.
     *
     * @param createDTO DTO contendo os dados para a criação do artigo, vindo do corpo da requisição.
     * @param authorId  ID do usuário (autor) que está criando o artigo, passado como parâmetro na URL.
     * @return um ResponseEntity contendo o DTO do artigo criado e o status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(
            @Valid @RequestBody ArticleCreateDTO createDTO,
            @RequestParam Long authorId) {

        // NOTA: Em uma aplicação real com segurança, o 'authorId' viria do usuário LOGADO,
        // e não de um parâmetro de requisição.

        ArticleResponseDTO createdArticle = articleService.createArticle(createDTO, authorId);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar uma lista paginada de todos os artigos.
     * Acessível via requisição GET para /api/articles.
     * Suporta parâmetros de paginação e ordenação na URL (ex: ?page=0&size=10&sort=publishDate,desc).
     *
     * @param pageable Objeto que o Spring monta automaticamente com as informações de paginação e ordenação.
     * @return um ResponseEntity contendo um Page de ArticleResponseDTO e o status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<Page<ArticleResponseDTO>> getAllArticles(Pageable pageable) {
        return ResponseEntity.ok(articleService.findAll(pageable));
    }

    /**
     * Endpoint para buscar um artigo específico pelo seu ID.
     * Acessível via requisição GET para /api/articles/{id}.
     *
     * @param id O ID do artigo a ser buscado, vindo da variável de caminho na URL.
     * @return um ResponseEntity contendo o DTO do artigo encontrado e o status HTTP 200 (OK).
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o artigo com o ID especificado não for encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }
}