package br.com.ifba.usuario.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

/**
 *
 * @author Glauber
 */

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Usuario extends PersistenceEntity implements Serializable {
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "senha", nullable = false)
    private String senha;

    public void setId(Long id) {
        this.id = id;
    }
}