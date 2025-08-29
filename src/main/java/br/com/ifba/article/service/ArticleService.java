package br.com.ifba.article.service;

import br.com.ifba.article.dto.ArticleCreateDTO;
import br.com.ifba.article.dto.ArticleResponseDTO;
import br.com.ifba.article.entity.Article;
import br.com.ifba.article.repository.ArticleRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ArticleResponseDTO createArticle(ArticleCreateDTO createDTO, Long authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autor não encontrado com o ID: " + authorId));

        if (!"ADMIN".equals(author.getRole())) {
            throw new IllegalStateException("Apenas administradores podem criar artigos.");
        }

        Article newArticle = new Article();
        newArticle.setTitle(createDTO.getTitle());
        newArticle.setContent(createDTO.getContent());
        newArticle.setUser(author);
        newArticle.setStatus("RASCUNHO");


        Article savedArticle = articleRepository.save(newArticle);

        return toResponseDTO(savedArticle);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleResponseDTO findById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artigo não encontrado com o ID: " + id));
        return toResponseDTO(article);
    }

    private ArticleResponseDTO toResponseDTO(Article article) {
        return new ArticleResponseDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getPublishDate(),
                article.getStatus(),
                article.getUser().getId(),
                article.getUser().getName()
        );
    }
}