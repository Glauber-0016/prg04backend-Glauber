package br.com.ifba.infrastructure.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Glauber
 */

@MappedSuperclass
@Getter @Setter
public class PersistenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

}