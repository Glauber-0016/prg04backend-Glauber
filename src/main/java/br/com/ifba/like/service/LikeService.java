package br.com.ifba.like.service;

import br.com.ifba.article.entity.Article;
import br.com.ifba.article.repository.ArticleRepository;
import br.com.ifba.comment.repository.CommentRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.like.dto.LikeDTO;
import br.com.ifba.like.entity.Like;
import br.com.ifba.like.repository.LikeRepository;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void likeArticle(Long articleId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow();
        Article article = articleRepository.findById(articleId)
                .orElseThrow();

        Like newLike = new Like();

        newLike.setUser(user);
        newLike.setArticle(article);

        likeRepository.save(newLike);
    }

    @Transactional
    public void unlikeArticle(Long articleId, Long userId) {
        Like like = likeRepository.findByUserIdAndArticleId(userId, articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Curtida não encontrada para este usuário e artigo."));

        likeRepository.delete(like);
    }

    @Transactional(readOnly = true)
    public long getArticleLikeCount(Long articleId) {

        if (!articleRepository.existsById(articleId)) {
            throw new ResourceNotFoundException("Artigo não encontrado.");
        }
        return likeRepository.countByArticleId(articleId);
    }
}