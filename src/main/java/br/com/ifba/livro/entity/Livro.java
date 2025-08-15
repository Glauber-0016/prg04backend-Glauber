package br.com.ifba.livro.entity;

import br.com.ifba.autor.entity.Autor;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "livro")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Livro extends PersistenceEntity implements Serializable {
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "ano_publicacao")
    private int anoPublicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    @JsonBackReference
    private Autor autor;


}