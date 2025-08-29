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

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByArticle(@PathVariable Long articleId) {
        List<CommentResponseDTO> comments = commentService.findCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentCreateDTO createDTO) {
        CommentResponseDTO createdComment = commentService.createComment(createDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}