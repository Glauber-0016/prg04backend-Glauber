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
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(
            @Valid @RequestBody ArticleCreateDTO createDTO,
            @RequestParam Long authorId) {


        ArticleResponseDTO createdArticle = articleService.createArticle(createDTO, authorId);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ArticleResponseDTO>> getAllArticles(Pageable pageable) {
        return ResponseEntity.ok(articleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }
}