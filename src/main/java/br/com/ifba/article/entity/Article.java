package br.com.ifba.article.entity;

import br.com.ifba.author.entity.Author;
import br.com.ifba.comment.entity.Comment;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.like.entity.Like;
import br.com.ifba.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.com.ifba.book.entity.Book;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "article")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Article extends PersistenceEntity implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate = new Date();

    private String header;

    private String status;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    @OneToMany(mappedBy = "article")
    private List<Like> likes;

    @ManyToMany
    @JoinTable(
            name = "article_authors",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "article_books",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();

}
