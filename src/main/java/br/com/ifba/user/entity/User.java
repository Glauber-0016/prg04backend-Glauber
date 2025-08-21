package br.com.ifba.user.entity;

import br.com.ifba.article.entity.Article;
import br.com.ifba.comment.entity.Comment;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.like.entity.Like;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends PersistenceEntity {

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate = new Date();

    private String profilePic;

    @Column(length = 50)
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Article> articles;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Like> likes;

}