package br.com.ifba.comment.entity;

import br.com.ifba.article.entity.Article;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.like.entity.Like;
import br.com.ifba.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment extends PersistenceEntity implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Lob
    @Column(nullable = false)
    private String commentText;

    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate = new Date();

    private Boolean approved = false;

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;

}
