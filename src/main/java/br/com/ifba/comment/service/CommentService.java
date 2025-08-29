package br.com.ifba.comment.service;

import br.com.ifba.article.entity.Article;
import br.com.ifba.article.repository.ArticleRepository;
import br.com.ifba.comment.dto.CommentCreateDTO;
import br.com.ifba.comment.dto.CommentResponseDTO;
import br.com.ifba.comment.entity.Comment;
import br.com.ifba.comment.repository.CommentRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public CommentResponseDTO createComment(CommentCreateDTO createDTO) {
        User author = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + createDTO.getUserId()));

        Article article = articleRepository.findById(createDTO.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException("Artigo não encontrado com o ID: " + createDTO.getArticleId()));

        Comment newComment = new Comment();
        newComment.setCommentText(createDTO.getCommentText());
        newComment.setUser(author);
        newComment.setArticle(article);
        newComment.setApproved(true);

        Comment savedComment = commentRepository.save(newComment);
        return toResponseDTO(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findCommentsByArticleId(Long articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new ResourceNotFoundException("Artigo não encontrado com o ID: " + articleId);
        }

        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private CommentResponseDTO toResponseDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getCommentText(),
                comment.getCommentDate(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getArticle().getId()
        );
    }
}