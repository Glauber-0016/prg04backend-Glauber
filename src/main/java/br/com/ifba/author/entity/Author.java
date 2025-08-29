package br.com.ifba.author.entity;

import br.com.ifba.article.entity.Article;
import br.com.ifba.book.entity.Book;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@EqualsAndHashCode(callSuper = true)
public class Author extends PersistenceEntity {

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Lob
    @Column(name = "biography")
    private String biography;

    @Column(name = "author_photo")
    private String authorPhoto;


    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Book> books = new ArrayList<>();

    @ManyToMany(mappedBy = "authors")
    private Set<Article> articles = new HashSet<>();

}