package br.com.ifba.like.entity;

import br.com.ifba.article.entity.Article;
import br.com.ifba.comment.entity.Comment;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Table(name = "like_table")
@Data
@EqualsAndHashCode(callSuper = true)
public class Like extends PersistenceEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(optional = true)
    @JoinColumn(name = "comment_id")
    private Comment comment;

}