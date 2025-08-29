package br.com.ifba.comment.controller;

import br.com.ifba.comment.dto.CommentCreateDTO;
import br.com.ifba.comment.dto.CommentResponseDTO;
import br.com.ifba.comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar as operações relacionadas a Comentários.
 * <p>
 * Esta classe expõe os endpoints da API para criar e consultar comentários,
 * utilizando uma abordagem com rotas aninhadas e de nível superior.
 */
@RestController
@RequestMapping("/api") // Define o caminho base para os endpoints
public class CommentController {

    /**
     * Injeção de dependência do CommentService, que contém a lógica de negócio para comentários.
     */
    @Autowired
    private CommentService commentService;

    /**
     * Endpoint para buscar todos os comentários de um artigo específico.
     * Acessível via requisição GET para /api/articles/{articleId}/comments.
     *
     * @param articleId O ID do artigo cujos comentários serão buscados.
     * @return um ResponseEntity com a lista de DTOs de comentários e o status HTTP 200 (OK).
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o artigo com o ID especificado não for encontrado.
     */
    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByArticle(@PathVariable Long articleId) {
        List<CommentResponseDTO> comments = commentService.findCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Endpoint para criar um novo comentário.
     * Acessível via requisição POST para /api/comments.
     *
     * @param createDTO DTO contendo os dados para a criação do comentário, validado.
     * @return um ResponseEntity com o DTO do comentário criado e o status HTTP 201 (Created).
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o usuário ou o artigo especificados no DTO não forem encontrados.
     */
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentCreateDTO createDTO) {
        // NOTA: Em uma aplicação real com segurança, o 'userId' viria do usuário LOGADO,
        // e não do corpo da requisição.
        CommentResponseDTO createdComment = commentService.createComment(createDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}