package br.com.ifba.autor.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.livro.entity.Livro;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "autor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Autor extends PersistenceEntity implements Serializable {
    @Column(name = "nome", nullable = false)
    private String nome;

    @JsonManagedReference
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros;

}