package br.com.ifba.book.entity;

import br.com.ifba.article.entity.Article;
import br.com.ifba.author.entity.Author;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "livro")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Book extends PersistenceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(nullable = false)
    private String bookTitle;

    private String pubYear;

    @Lob
    private String synopsis;

    private String cover;

    @ManyToMany(mappedBy = "books")
    private Set<Article> articles = new HashSet<>();


}