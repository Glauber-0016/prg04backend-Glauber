package br.com.ifba.like.repository;

import br.com.ifba.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndArticleId(Long userId, Long articleId);

    Optional<Like> findByUserIdAndCommentId(Long userId, Long commentId);

    long countByArticleId(Long articleId);

    long countByCommentId(Long commentId);
}